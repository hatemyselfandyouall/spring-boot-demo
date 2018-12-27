package com.wangxinenpu.springbootdemo.config.shiro;

import com.wangxinenpu.springbootdemo.config.shiro.CustomerCache;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by panjj on 2017/6/6.
 */
public class CustomerCacheManager implements CacheManager, Destroyable {

    private Map<String, Cache> cacheMap = new HashMap<>();//无需加锁
    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        if (cacheMap.containsKey(s)) {
            return cacheMap.get(s);
        }else {
            synchronized (cacheMap){
                if (cacheMap.containsKey(s)){
                    return cacheMap.get(s);
                }else {
                    CustomerCache cache = new CustomerCache(s);
                    cacheMap.put(s, cache);
                    return cache;
                }
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        for (Cache cache:cacheMap.values()){
            cache.clear();
        }
        cacheMap.clear();
    }
}
