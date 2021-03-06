package com.example.demo.config;

import com.example.demo.config.granter.CustomRefreshTokenGranter;
import com.example.demo.config.granter.MobilePasswordCustomTokenGranter;
import com.example.demo.config.granter.PasswordCaptchaCustomTokenGranter;
import com.example.demo.config.granter.SmsCodeTokenGranter;
import com.example.demo.exception.CustomWebResponseExceptionTranslator;
import com.example.demo.mapper.PermissionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private AuthenticationManager authenticationManager;

    /**
     * ????????????
     */
    @Resource
    private CustomUserDetailsService customUserDetailsService;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private UserMapper userMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private TokenStore tokenStore;

    @Resource
    private CustomWebResponseExceptionTranslator webResponseExceptionTranslator;

    @Resource
    private CustomAuthenticationFilter customAuthenticationFilter;

    @Resource
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Resource
    private CustomTokenEnhancer customTokenEnhancer;

    @Resource
    private DataSource dataSource;

    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetails());
    }

    private TokenGranter tokenGranter(final AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenGranter> granters = new ArrayList<>();
//        AuthorizationCodeServices authorizationCodeServices = endpoints.getAuthorizationCodeServices();
        AuthorizationServerTokenServices tokenServices = endpoints.getTokenServices();
        ClientDetailsService clientDetailsService = endpoints.getClientDetailsService();
        OAuth2RequestFactory oAuth2RequestFactory = endpoints.getOAuth2RequestFactory();

//        AuthorizationCodeTokenGranter authorizationCodeTokenGranter = new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetailsService, oAuth2RequestFactory);
        CustomRefreshTokenGranter customRefreshTokenGranter = new CustomRefreshTokenGranter(true, tokenStore, tokenServices, clientDetailsService, oAuth2RequestFactory);
        MobilePasswordCustomTokenGranter mobilePasswordCustomTokenGranter = new MobilePasswordCustomTokenGranter(customUserDetailsService, tokenServices, clientDetailsService, oAuth2RequestFactory);
        ResourceOwnerPasswordTokenGranter resourceOwnerPasswordTokenGranter = new ResourceOwnerPasswordTokenGranter(authenticationManager(), tokenServices, clientDetailsService, oAuth2RequestFactory);
        PasswordCaptchaCustomTokenGranter passwordCaptchaCustomTokenGranter = new PasswordCaptchaCustomTokenGranter(customUserDetailsService,tokenServices,clientDetailsService,oAuth2RequestFactory);
        passwordCaptchaCustomTokenGranter.setRedisTemplate(redisTemplate);

        SmsCodeTokenGranter smsCodeTokenGranter = new SmsCodeTokenGranter(tokenServices,clientDetailsService,oAuth2RequestFactory);
        smsCodeTokenGranter.setRedisTemplate(redisTemplate);
        smsCodeTokenGranter.setUserMapper(userMapper);
        smsCodeTokenGranter.setPermissionMapper(permissionMapper);

//        granters.add(authorizationCodeTokenGranter);
        granters.add(customRefreshTokenGranter);
        granters.add(mobilePasswordCustomTokenGranter);
        granters.add(resourceOwnerPasswordTokenGranter);
        granters.add(smsCodeTokenGranter);
        granters.add(passwordCaptchaCustomTokenGranter);

        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.POST);
        return new CompositeTokenGranter(granters);
    }

    public AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(daoAuthenticationProvider());
        return new ProviderManager(providers, authenticationManager);
    }

    /**
     * password ??????????????????
     *
     * @return
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    /**
     * ?????????????????????????????????
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(customTokenEnhancer, jwtAccessTokenConverter));

        endpoints.tokenGranter(tokenGranter(endpoints));
        endpoints.accessTokenConverter(jwtAccessTokenConverter);
        endpoints.tokenStore(tokenStore);
        endpoints.tokenEnhancer(enhancerChain);
        // password ????????? AuthenticationManager ??????
        endpoints.authenticationManager(authenticationManager);
        // ???????????????????????????
        endpoints.userDetailsService(customUserDetailsService);
        // ?????????????????????
        endpoints.exceptionTranslator(webResponseExceptionTranslator);
    }



    /**
     * ???????????????????????????
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // ?????????????????? /oauth/token_key , ??????????????????
        security.tokenKeyAccess("permitAll()");
        // ?????????????????? /oauth/check_token , ??????????????????
        security.checkTokenAccess("isAuthenticated()");
        security.allowFormAuthenticationForClients();
        security.addTokenEndpointAuthenticationFilter(customAuthenticationFilter);
    }



}
