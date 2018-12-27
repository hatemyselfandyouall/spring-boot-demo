package com.wangxinenpu.springbootdemo.dao;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisDao<K,V> {

    private RedisTemplate<K, V> redisTemplate;

    public RedisDao(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void put(K k, V v, long time) {
        redisTemplate.opsForValue().set(k,v,time, TimeUnit.SECONDS);
    }

    @Deprecated
    public void put(K k, V v) {
        redisTemplate.opsForValue().set(k,v);
    }

    public V get(K k){
        return redisTemplate.opsForValue().get(k);
    }

    public boolean exist(K k) {
        return redisTemplate.hasKey(k);
    }

    public void remove(K k){
        if (exist(k)) {
            redisTemplate.delete(k);
        }
    }

    public void remove(K... ks) {
        for (K k : ks) {
            remove(k);
        }
    }

    public void push(K k,V v){
        redisTemplate.opsForList().rightPush(k, v);
    }

    public V pop(K k) {
        return redisTemplate.opsForList().leftPop(k);
    }

    public long size(K k){
        return redisTemplate.opsForList().size(k);
    }

    public RedisTemplate<K, V> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
