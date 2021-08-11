package com.example.demo.config.granter;


import com.example.demo.common.Constant;
import com.example.demo.vo.LoginUser;
import com.example.demo.domain.Permission;
import com.example.demo.domain.User;
import com.example.demo.mapper.PermissionMapper;
import com.example.demo.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.List;
import java.util.Map;

/**
 * 验证码过滤器
 * <p>
 * OncePerRequestFilter确保过滤器每次只被调用一次
 *
 * @author ：
 */
public class SmsCodeTokenGranter extends AbstractTokenGranter {


    private RedisTemplate<String, Object> redisTemplate;

    private UserMapper userMapper;

    private PermissionMapper permissionMapper;

    public SmsCodeTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, Constant.GRANT_TYPE_SMS);
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = tokenRequest.getRequestParameters();
        String mobile = parameters.get(Constant.PHONE);
        String code = parameters.get(Constant.SMS_CODE_PARAM);
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(code)){
            throw new InvalidGrantException("phone or sms code is empty");
        }
        String smsCodeKey = Constant.SMS_CODE_KEY + mobile;
        String realCode = (String) redisTemplate.opsForValue().get(smsCodeKey);
        if (StringUtils.isEmpty(realCode)){
            throw new InvalidGrantException("sms code is expire or not exist");
        }
        if (code.equals(realCode)){
            redisTemplate.delete(smsCodeKey);
            User user = userMapper.selectByPhone(mobile);
            LoginUser loginUser = new LoginUser();
            BeanUtils.copyProperties(user,loginUser);
            List<Permission> permissions = permissionMapper.selectPermissionByUserId(user.getId());
            loginUser.setPermissions(permissions);
            AbstractAuthenticationToken userAuth =
                    new UsernamePasswordAuthenticationToken(user, null, loginUser.getAuthorities());
            userAuth.setDetails(parameters);
            OAuth2Request oAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
            return new OAuth2Authentication(oAuth2Request, userAuth);
        }else {
            throw new InvalidGrantException("sms code is not correct");
        }
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public PermissionMapper getPermissionMapper() {
        return permissionMapper;
    }

    public void setPermissionMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }
}
