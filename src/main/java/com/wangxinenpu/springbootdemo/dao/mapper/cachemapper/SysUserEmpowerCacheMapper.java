package com.wangxinenpu.springbootdemo.dao.mapper.cachemapper;


import com.wangxinenpu.springbootdemo.constant.SysbaseCacheEnum;
import com.wangxinenpu.springbootdemo.dao.mapper.SysUserEmpowerMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysUserEmpower;
import com.wangxinenpu.springbootdemo.service.impl.SysUserImpl;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import star.bizbase.util.constant.SysCacheTimeDMO;
import star.modules.cache.CacheKeyLock;
import star.modules.cache.CachesKeyService;
import star.modules.cache.enumerate.BaseCacheEnum;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 *  与天正一体化对接 cache  mapper
 * @author xhy
 * @since:2019年3月21日下午2:15:50
 */
@Component
public class SysUserEmpowerCacheMapper {
	private static Logger logger = LoggerFactory.getLogger(SysUserImpl.class);
	@Resource
	private SysUserEmpowerMapper sysUserEmpowerMapper;
	@Resource
	private CachesKeyService cachesKeyService;
	
	/**
	 * 
	 * 缓存中 根据天正ythUserId 获取系统userid
	 * @author xhy
	 * @since:2019年3月21日下午3:03:53
	 * @param id
	 * @return
	 */
	public String getCacheEmpowerUserIdById(String ythUserId){
		if(StringUtil.isEmpty(ythUserId)) {
			logger.info("SysUserEmpowerCacheMapper.getCacheEmpowerUserIdById error:ythUserId={}",ythUserId);
			return null;
		}
		
		String cacheKey = ythUserId;
		return new CacheKeyLock(cachesKeyService, SysCacheTimeDMO.CACHETIMEOUT_24H) {//缓存1tian
			@Override
			protected Object doGetList(BaseCacheEnum type, String key) {
				HashMap<String, Object> searchMap = new HashMap<>();
				searchMap.put("ythUserId", ythUserId);
				List<SysUserEmpower> ulist = sysUserEmpowerMapper.getListByWhere(searchMap);
				if(CollectionUtils.isEmpty(ulist)) {
					return null;
				}
				SysUserEmpower u = ulist.get(0);
				return u.getuserId();
			}
		}.getCache(SysbaseCacheEnum.GETCACHE_EMPOWERUSERID, cacheKey);
		
	}
	
	public String getCacheSysUserIdByYthUserId(String ythUserId){
		if(StringUtil.isEmpty(ythUserId)) {
			logger.info("SysUserEmpowerCacheMapper.getCacheEmpowerUserIdById error:ythUserId={}",ythUserId);
			return null;
		}
		
		String cacheKey = ythUserId;
		return new CacheKeyLock(cachesKeyService, SysCacheTimeDMO.CACHETIMEOUT_24H) {//缓存1tian
			@Override
			protected Object doGetList(BaseCacheEnum type, String key) {
				HashMap<String, Object> searchMap = new HashMap<>();
				searchMap.put("ythUserId", ythUserId);
				List<SysUserEmpower> ulist = sysUserEmpowerMapper.getListByWhere(searchMap);
				if(CollectionUtils.isEmpty(ulist)) {
					return null;
				}
				SysUserEmpower u = ulist.get(0);
				return u.getuserId();
			}
		}.getCache(SysbaseCacheEnum.GETCACHE_EMPOWERUSERID, cacheKey);
		
	}
	
	
	
	
	

}
