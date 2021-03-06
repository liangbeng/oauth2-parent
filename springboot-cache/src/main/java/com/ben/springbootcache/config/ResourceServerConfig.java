package com.ben.springbootcache.config;


import com.ben.springbootcache.exception.AuthExceptionEntryPoint;
import com.ben.springbootcache.exception.CustomAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;


@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Resource
    private TokenStore tokenStore;

    @Resource
    private AuthExceptionEntryPoint authExceptionEntryPoint;

    @Resource
    private CustomAccessDeniedHandler customAccessDeniedHandler;


    @Override
    public void configure(final ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("oauth2_resource");
        resources.tokenStore(tokenStore);
        resources.tokenServices(tokenService());
        resources.authenticationEntryPoint(authExceptionEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/oauth/**","/captcha/get").permitAll()
                .anyRequest()
                .authenticated()
                ;

    }

    @Bean
    public ResourceServerTokenServices tokenService() {
        // ????????????????????????????????????????????? token ????????????
        RemoteTokenServices service = new RemoteTokenServices();
        // ???????????????????????????URL??????????????????????????????????????????????????????????????????????????????
        service.setCheckTokenEndpointUrl("http://localhost:8094/user-center/oauth/check_token");
        // ????????????????????????????????????id
        service.setClientId("demo-pc");
        // ??????????????????????????????????????????
        service.setClientSecret("demo-secret");
        return service;
    }
}