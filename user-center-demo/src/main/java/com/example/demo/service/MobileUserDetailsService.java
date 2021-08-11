package com.example.demo.service;


import com.example.demo.domain.User;
import com.example.demo.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component("mobileUserDetailsService") // 一定不要少了
public class MobileUserDetailsService extends AbstractUserDetailsService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private UserMapper userMapper;

    @Override
    User findSysUser(String mobile){
        logger.info("请求认证的手机号：" + mobile);
        return userMapper.selectByPhone(mobile);
    }
}
