package com.ben.springbootcache.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String,Object> redisTemplate(@Lazy RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redis = new RedisTemplate<>();
        GenericToStringSerializer<String> genericToStringSerializer = new GenericToStringSerializer<>(String.class);
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        redis.setKeySerializer(genericToStringSerializer);
        redis.setHashKeySerializer(genericToStringSerializer);
        redis.setValueSerializer(jackson2JsonRedisSerializer);
        redis.setHashValueSerializer(jackson2JsonRedisSerializer);
        redis.setConnectionFactory(redisConnectionFactory);
        return redis;

    }
}
