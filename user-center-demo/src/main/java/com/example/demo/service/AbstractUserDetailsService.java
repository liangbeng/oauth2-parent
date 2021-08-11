package com.example.demo.service;


import com.alibaba.fastjson.JSON;
import com.example.demo.vo.LoginUser;
import com.example.demo.domain.Permission;
import com.example.demo.domain.User;
import com.example.demo.mapper.PermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
public abstract class AbstractUserDetailsService implements UserDetailsService {

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 通过请求的用户名去数据库中查询用户信息
        User sysUser = findSysUser(username);
        LoginUser loginUserDto = new LoginUser();
        BeanUtils.copyProperties(sysUser, loginUserDto);
        findSysPermission(loginUserDto);
        return loginUserDto;
    }

    /**
     * @param usernameOrMobile 用户或手机号
     * @return
     * @throws UsernameNotFoundException
     */
    abstract User findSysUser(String usernameOrMobile);

    /**
     * 查询认证信息
     * @param loginUser  带权限的用户信息对象
     * @throws UsernameNotFoundException
     */
    public void findSysPermission(LoginUser loginUser) throws UsernameNotFoundException{
        if(loginUser == null) {
            throw new UsernameNotFoundException("未查询到有效用户信息");
        }

        // 2. 查询该用户有哪一些权限
        List<Permission> sysPermissions =
                permissionMapper.selectPermissionByUserId(loginUser.getId());

        // 无权限
        if(CollectionUtils.isEmpty(sysPermissions)) {
            log.warn("loginUser : {} :does not have any permission", JSON.toJSONString(loginUser));
//            return;
        }

        // 存入权限,认证通过后用于渲染左侧菜单
        loginUser.setPermissions(sysPermissions);


    }


}
