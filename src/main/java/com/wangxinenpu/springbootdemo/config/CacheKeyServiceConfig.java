package com.wangxinenpu.springbootdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;
import star.modules.cache.*;

@Configuration
public class CacheKeyServiceConfig {


    @Bean
    CachesKeyService cachesKeyService(){
        return new CachesKeyService();
    }

    @Bean
    JedisKeyService jedisKeyService(){
        return new JedisKeyService();
    }

    @Bean
    JedisPoolConfig jedisPoolConfig(){
        return new JedisPoolConfig();
    }
    @Bean
    JedisPool jedisPool(){
        return new JedisPool("",jedisPoolConfig(),"");
    }
    @Bean
    JedisService jedisService(){
        return new JedisService();
    }

    @Bean
    CachesService cachesService(){return new CachesService();}
}
