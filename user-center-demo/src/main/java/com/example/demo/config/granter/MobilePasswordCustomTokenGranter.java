package com.example.demo.config.granter;

import com.example.demo.common.AuthorizedGrantTypes;
import com.example.demo.common.Constant;
import com.example.demo.vo.LoginUser;
import com.example.demo.service.CustomUserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

public class MobilePasswordCustomTokenGranter extends AbstractCustomTokenGranter {

    protected CustomUserDetailsService userDetailsService;

    public MobilePasswordCustomTokenGranter(CustomUserDetailsService userDetailsService, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, AuthorizedGrantTypes.PWD.getVal());
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected LoginUser getOauthUser(Map<String, String> parameters) {
        String mobile = parameters.get(Constant.PHONE);
        return (LoginUser)userDetailsService.loadUserByUsername(mobile);
    }

}
