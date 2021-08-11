package com.ben.springbootcache.controller;

import com.ben.springbootcache.pojo.User;
import com.ben.springbootcache.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RefreshScope
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Value("${test.word}")
    private String test;

    @Resource
    private UserService userService;


    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('sys:user:manager','sys:user:query','sys:user:password')")
    public User findById(@PathVariable("id") Integer id){
       log.info("----------------{}",test);
        return userService.findById(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public User updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('sys:file:del')")
    public void deleteUser(@PathVariable("id") Integer id){
        userService.deleteUserById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }
}
