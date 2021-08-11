package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String,Object> redisTemplate(@Lazy RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redis = new RedisTemplate<>();
        GenericToStringSerializer<String> genericToStringSerializer = new GenericToStringSerializer<>(String.class);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        redis.setKeySerializer(genericToStringSerializer);
        redis.setHashKeySerializer(genericToStringSerializer);
        redis.setValueSerializer(jackson2JsonRedisSerializer);
        redis.setHashValueSerializer(jackson2JsonRedisSerializer);
        redis.setConnectionFactory(redisConnectionFactory);
        return redis;

    }
}
