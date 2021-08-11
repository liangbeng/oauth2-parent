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
        // 资源服务器去远程认证服务器验证 token 是否有效
        RemoteTokenServices service = new RemoteTokenServices();
        // 请求认证服务器验证URL，注意：默认这个端点是拒绝访问的，要设置认证后可访问
        service.setCheckTokenEndpointUrl("http://localhost:8094/user-center/oauth/check_token");
        // 在认证服务器配置的客户端id
        service.setClientId("demo-pc");
        // 在认证服务器配置的客户端密码
        service.setClientSecret("demo-secret");
        return service;
    }
}