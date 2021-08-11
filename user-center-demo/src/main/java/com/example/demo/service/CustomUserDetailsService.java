package com.example.demo.service;


import com.example.demo.domain.User;
import com.example.demo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component("customUserDetailsService")
@Slf4j
public class CustomUserDetailsService extends AbstractUserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    User findSysUser(String usernameOrMobile){
        log.info("请求认证的用户名：{}" , usernameOrMobile);
        User user = userMapper.selectByUsername(usernameOrMobile);
        if (user == null){
            user =  userMapper.selectByPhone(usernameOrMobile);
        }
        return user;
    }

}