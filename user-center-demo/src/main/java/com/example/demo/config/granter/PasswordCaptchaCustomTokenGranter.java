package com.example.demo.config.granter;

import cn.hutool.core.util.StrUtil;
import com.example.demo.common.AuthorizedGrantTypes;
import com.example.demo.common.Constant;
import com.example.demo.vo.LoginUser;
import com.example.demo.service.CustomUserDetailsService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

public class PasswordCaptchaCustomTokenGranter extends AbstractCustomTokenGranter {

    private RedisTemplate<String,Object> redisTemplate;

    protected CustomUserDetailsService userDetailsService;

    public PasswordCaptchaCustomTokenGranter(CustomUserDetailsService userDetailsService, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, AuthorizedGrantTypes.PWD_CAPTCHA.getVal());
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected LoginUser getOauthUser(Map<String, String> parameters) {
        String username = parameters.get(Constant.USER_NAME);
        String captcha = parameters.get(Constant.CAPTCHA);
        String captchaCreateTime = parameters.get(Constant.CAPTCHA_CREATE_TIME);
        String captchaInRedis = (String) redisTemplate.opsForValue().get(captcha);
        if (StrUtil.isEmpty(captcha) || StrUtil.isEmpty(captchaCreateTime)){
           if (StrUtil.isEmpty(captchaCreateTime)){
               redisTemplate.delete(captcha);
           }
            throw new InvalidGrantException("captcha or captcha_create_time is empty");
        }
        if (!captchaCreateTime.equals(captchaInRedis)){
            redisTemplate.delete(captcha);
            throw new InvalidGrantException("captcha  is not right");
        }
        return (LoginUser)userDetailsService.loadUserByUsername(username);
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
