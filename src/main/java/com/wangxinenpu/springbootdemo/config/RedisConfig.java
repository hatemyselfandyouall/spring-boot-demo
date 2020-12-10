package com.wangxinenpu.springbootdemo.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {

    @Bean(name = "redisTemplate")
    RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

}
//<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><ns1:F2F_PayResponse xmlns:ns1="http://server.remotting.hundsun.com/"><return>&lt;?xml version="1.0" encoding="GBK" standalone="yes"?&gt;&lt;ROOT&gt;&lt;IS_SUCCESS CODE="99"&gt;其他错误&lt;/IS_SUCCESS&gt;&lt;ERRMSG&gt;公共支付平台：缴款单异常，请稍后重试或联系服务热线&lt;/ERRMSG&gt;&lt;/ROOT&gt;</return></ns1:F2F_PayResponse></soap:Body></soap:Envelope>