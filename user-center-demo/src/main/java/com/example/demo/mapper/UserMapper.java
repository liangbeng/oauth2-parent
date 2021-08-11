package com.example.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.domain.User;

public interface UserMapper extends BaseMapper<User> {

    /**根据用户名查找用户信息**/
    User selectByUsername(String username);

    /**根据手机号查找用户信息**/
    User selectByPhone(String string);

}