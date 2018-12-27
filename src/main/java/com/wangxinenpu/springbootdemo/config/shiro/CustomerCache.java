package com.wangxinenpu.springbootdemo.config.shiro;

import com.wangxinenpu.springbootdemo.dao.RedisDao;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;


import java.util.Collection;
import java.util.Set;

/**
 * Created by panjj on 2017/6/5.
 */

public class CustomerCache<K,V> implements Cache<K,V> {
    private static  String redisShiroCache;
    private static RedisDao redisDao;
    private String prefix;

    public CustomerCache() {
    }

    public CustomerCache(String prefix) {
        this.prefix = redisShiroCache+"-"+prefix+":";
    }

    public static void setRedisShiroCache(String redisShiroCache) {
        CustomerCache.redisShiroCache = redisShiroCache;
    }

    public static void setRedisDao(RedisDao redisDao) {
        CustomerCache.redisDao = redisDao;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(K key) throws CacheException {
        return (V) redisDao.get(buildCacheKey(key));
    }

    @Override
    public V put(K key, V value) throws CacheException {
        V previos = get(key);

        redisDao.put(buildCacheKey(key), value, 3600);
        return previos;
    }

    @Override
    public V remove(K key) throws CacheException {
        V previos = get(key);
        redisDao.remove(buildCacheKey(key));
        return previos;
    }

    @Override
    public void clear() throws CacheException {
        Set<K> kSet = keys();
        for (K k : kSet) {
            redisDao.remove(k);
        }
    }

    @Override
    public int size() {
        if (keys() == null)
            return 0;
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        return redisDao.getRedisTemplate().keys(String.format("*%s*", prefix));
    }

    @Override
    public Collection<V> values() {
        //TODO
        return null;
    }

    private String buildCacheKey(Object key) {
        return prefix + key;
    }
}
