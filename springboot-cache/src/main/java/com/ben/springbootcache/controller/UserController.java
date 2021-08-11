package com.ben.springbootcache.controller;

import com.ben.springbootcache.pojo.User;
import com.ben.springbootcache.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('sys:user:manager','sys:user:query','sys:user:password')")
    public User findById(@PathVariable("id") Integer id){
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
