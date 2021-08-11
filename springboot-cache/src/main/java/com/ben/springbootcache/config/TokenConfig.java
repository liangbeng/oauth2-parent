package com.ben.springbootcache.config;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.io.IOException;

/**
 *
 */
@Configuration
public class TokenConfig {

    public static final String SIGNING_KEY = "demo-key";

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        // 测试用,资源服务使用相同的字符达到一个对称加密的效果,生产时候使用RSA非对称加密方式
        accessTokenConverter.setSigningKey(SIGNING_KEY);
//        Resource resource = new ClassPathResource("fline88.com_keystore.jks");
//        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource,"fline_secret".toCharArray());
//        //此处传入的是创建jks文件时的别名-alias ims.abc.com
//        accessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("fline88.com"));
//        ClassPathResource publicResource = new ClassPathResource("public.txt");
//        String publicKey = "";
//        try {
//            publicKey = IOUtils.toString(publicResource.getInputStream());
//        } catch (final IOException e) {
//            throw new RuntimeException(e);
//        }
//        accessTokenConverter.setVerifierKey(publicKey);
        return accessTokenConverter;
    }



}
