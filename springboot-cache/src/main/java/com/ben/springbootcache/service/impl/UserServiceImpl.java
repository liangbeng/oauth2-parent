package com.ben.springbootcache.service.impl;

import com.ben.springbootcache.mapepr.UserMapper;
import com.ben.springbootcache.pojo.User;
import com.ben.springbootcache.service.UserService;
import com.ben.springbootcache.util.PropertiesCfg;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {


    @Resource
    private UserMapper userMapper;

    /**
     * cacheNames:缓存名字  一个CacheManager对应多个Cache,CacheManager根据名字管理Cache,缓存的名字是一个数组，也就是说可以将一个缓存
     * * Entry分到多个Cache里面
     * key: Entry的key
     *
     * @param id id
     * @return
     */
    @Override
    @Cacheable(key = "#id", condition = "#id > 0", unless = "#result == null")
    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    @Override
    @Transactional
    @CachePut(key = "#user.id", condition = "#user.id > 0", unless = "#result == null")
    public User updateUser(User user) {
        userMapper.updateUser(user);
        return user;
    }

    @Override
    @Transactional
    @CacheEvict(key = "#id", condition = "#id > 0", beforeInvocation = true)
    public void deleteUserById(Integer id) {
        userMapper.deleteUserById(id);
    }

    @Override
    @Transactional
    public User addUser(User user) {
        userMapper.addUser(user);
        return user;

    }
}
