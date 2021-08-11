package com.example.demo.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {


    @PostMapping("/saveUser")
    @PreAuthorize("hasAnyAuthority('sys:user:manager','sys:user:query','sys:user:password')")
    public String saveUser(){
        //throw new RuntimeException("我错了");
        return "saveUser";
    }


    @PostMapping("/delUser")
    @PreAuthorize("hasAuthority('sys:file:del')")
    public String deleteUser(){
        return "delUser";
    }







}
