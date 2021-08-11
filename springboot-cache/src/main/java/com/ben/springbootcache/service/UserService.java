package com.ben.springbootcache.service;


import com.ben.springbootcache.pojo.User;

public interface UserService {
    User findById(Integer id);
    User updateUser(User user);
    void deleteUserById(Integer id);
    User addUser(User user);
}
