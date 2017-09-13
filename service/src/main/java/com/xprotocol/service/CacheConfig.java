/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.DefaultRedisCachePrefix;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Note: spring-boot-devtools has known issue with using cachemanager so caching is only turned on for production profile
 * @author zhao0677
 */
@Configuration
@EnableCaching
@Profile("prod")
public class CacheConfig extends CachingConfigurerSupport {
    
    @Autowired
    @Qualifier("redisTemplate")
    RedisTemplate<Object, Object> redisOperations;
    
    @Bean
    RedisCacheManager redisCacheManager () {
        // redisOperations will be injected if it is configured as a bean or create it: new RedisTemplate()...
        redisOperations.setDefaultSerializer(new KryoRedisSerializer<>());
        redisOperations.setEnableDefaultSerializer(true);
        redisOperations.setStringSerializer(new KryoRedisSerializer<>());
        redisOperations.setValueSerializer(new KryoRedisSerializer<>());
        redisOperations.setKeySerializer(new KryoRedisSerializer<>());
        redisOperations.setHashKeySerializer(new KryoRedisSerializer<>());
        redisOperations.setHashValueSerializer(new KryoRedisSerializer<>());

        redisOperations.afterPropertiesSet();

        RedisCacheManager redisCacheManager = new RedisCacheManager(redisOperations);
        redisCacheManager.setCachePrefix(new DefaultRedisCachePrefix("xprotocol"));
        redisCacheManager.setTransactionAware(true);
        redisCacheManager.setDefaultExpiration(1200);
        return redisCacheManager;
    }
}
