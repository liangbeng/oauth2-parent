package com.example.demo.config;


import com.example.demo.exception.AuthExceptionEntryPoint;
import com.example.demo.exception.CustomAccessDeniedHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    @Resource
    private TokenStore tokenStore;

    @Resource
    private LogoutSuccessHandler logoutSuccessHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .csrf().disable()
                .authorizeRequests()
                // 所有用户均可访问的资源
                .antMatchers("/sms/**","/oauth/**","/captcha/get").permitAll()
                .antMatchers().access("#oauth2.hasScope('all')")
                .anyRequest()
                .authenticated()
        ;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        //自定义资源访问认证异常，没有token，或token错误，使用MyAuthenticationEntryPoint
        resources.authenticationEntryPoint(new AuthExceptionEntryPoint())
        .accessDeniedHandler(new CustomAccessDeniedHandler())
        .resourceId("oauth2-resource")
        .tokenStore(tokenStore);
    }


    /**
     * 所有请求都会被权限拦截，默认需要登录后才可以访问。
     * <p>
     * 若需要匿名访问，可在此处增加。
     * <p>
     * 原则上不要随意增加
     */
    public enum Config {

        GET_SMS_CODE("/sms/**"),
        OAUTH("/oauth/**")
        ;

        private String url;

        Config(String url) {
            this.url = url;
        }

        public static String[] getValues() {
            try {
                List<String> stringList = new ArrayList<>();
                Arrays.asList(Config.values()).forEach(strings ->{
                    stringList.add(strings.url);
                });
                return stringList.toArray(new String[stringList.size()]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
