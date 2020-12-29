package com.wangxinenpu.springbootdemo.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wangxinenpu.springbootdemo.constant.SysbaseCacheEnum;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysOrgDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysUserDTO;
import org.springframework.context.annotation.Configuration;
import star.bizbase.util.constant.SysCacheTimeDMO;
import star.modules.cache.enumerate.BaseCacheEnum;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Configuration
public class MyCacheConfig {

    public static final Map<Integer,Cache<Object,Object>> CACHE_MAP=new ConcurrentHashMap<>();
    public static void main(String[] args)throws Exception {
        Cache<Integer, String> cache = CacheBuilder.newBuilder()
                //设置cache的初始大小为10，要合理设置该值
//                .initialCapacity(10)
//                //设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
//                .concurrencyLevel(5)
                //设置cache中的数据在写入之后的存活时间为10秒
                .expireAfterWrite(10, TimeUnit.SECONDS)
                //构建cache实例
                .build();
        cache.put(1, "Hi");

        for(int i=0 ;i<100 ;i++) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            System.out.println(sdf.format(new Date())
                    + "  key:1 ,value:"+cache.getIfPresent(1));
            Thread.sleep(1000);
        }
    }

    public static void putInCache(BaseCacheEnum type, Object key, Object value, int seconds){
      putInCache(type.getAnchor(),key,value,seconds);
    }

    public static void putInCache(String type, Object key, Object value, int seconds){
        Cache cache=CACHE_MAP.get(seconds);
        if (cache==null){
            cache = CacheBuilder.newBuilder()
                    //设置cache的初始大小为10，要合理设置该值
//                .initialCapacity(10)
//                //设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
//                .concurrencyLevel(5)
                    //设置cache中的数据在写入之后的存活时间为10秒
                    .expireAfterWrite(seconds, TimeUnit.SECONDS)
                    //构建cache实例
                    .build();
            CACHE_MAP.put(seconds,cache);
        }
        String keyString=type+":"+key;
        cache.put(keyString,value);
    }

    public static void putInCache(BaseCacheEnum sysuserById, String cacheKey, Object retvo) {
        putInCache(sysuserById.getAnchor(),cacheKey,retvo, SysCacheTimeDMO.CACHETIMEOUT_30M);
    }

    public static Object getFromCache(BaseCacheEnum sysuserById, String cacheKey) {
        return getFromCache(SysUserDTO.class,sysuserById.getAnchor(),cacheKey);
    }

    public static <T> T getFromCache(Class<T> clazz, String type, Object key) {
        Set<Integer> cacheKeySet=CACHE_MAP.keySet();
        for (Integer cacheKey:cacheKeySet){
            Cache cache=CACHE_MAP.get(cacheKey);
            Object value=cache.getIfPresent(key);
            if (value!=null){
                return (T)value;
            }
        }
        return null;
    }
}
