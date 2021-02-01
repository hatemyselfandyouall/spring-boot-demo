package com.wangxinenpu.springbootdemo.dao.mapper.cachemapper;


import com.wangxinenpu.springbootdemo.constant.SysbaseCacheEnum;
import com.wangxinenpu.springbootdemo.dao.mapper.*;
import com.wangxinenpu.springbootdemo.dataobject.po.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.*;
import com.wangxinenpu.springbootdemo.service.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import star.bizbase.util.constant.SysCacheTimeDMO;
import star.modules.cache.CacheKeyLock;
import star.modules.cache.CachesKeyService;
import star.modules.cache.enumerate.BaseCacheEnum;

import javax.annotation.Resource;

/**
 * 组织机构
 * @author Administrator
 *
 */
@Component
public class SysOrgCacheMapper {
	private static Logger logger = LoggerFactory.getLogger(SysOrgImpl.class);
	@Resource
	private SysOrgMapper sysOrgMapper;
	@Resource
	private CachesKeyService cachesKeyService;
	
	/**
	 * 
	 * 缓存中 获取用户信息数据。。。
	 * 根据主键得到系统用户表记录 
	 * @author xhy
	 * @since:2019年3月21日下午3:03:53
	 * @param id
	 * @return
	 */
	public SysOrgDTO getCacheSysOrgById(Long id){
		if(id == null) {
			logger.info("sysOrgService.getCacheSysOrgById error:id={}",id);
			return null;
		}
		
		String cacheKey = String.valueOf(id);
		return new CacheKeyLock(cachesKeyService, SysCacheTimeDMO.CACHETIMEOUT_10M) {//缓存10分钟
			@Override
			protected Object doGetList(BaseCacheEnum type, String key) {
				SysOrg po = sysOrgMapper.getByPrimaryKey(id);
				if(po==null) return null;
				return po.copyTo(SysOrgDTO.class);
			}
		}.getCache(SysbaseCacheEnum.SYSORG_BY_ID, cacheKey);
		
	}
	
	
	
	
	/**
	 * test
	 * 缓存中 获取用户信息数据。。。
	 * 根据主键得到系统用户表记录 
	 * @author xhy
	 * @since:2019年3月21日下午3:03:53
	 * @param id
	 * @return
	 */
	public SysOrgDTO getPriCacheSysOrgById(Long id){
		if(id == null) {
			logger.info("sysOrgService.getPriCacheSysOrgById error:id={}",id);
			return null;
		}
		
		String cacheKey = String.valueOf(id);
	
		SysOrgDTO retvo = cachesKeyService.getFromCache(SysbaseCacheEnum.SYSUSER_BY_ID, cacheKey);
		if(retvo==null) {
			SysOrg po = sysOrgMapper.getByPrimaryKey(id);
			if(po==null) return null;
			retvo =  po.copyTo(SysOrgDTO.class);
			cachesKeyService.putInCache(SysbaseCacheEnum.SYSUSER_BY_ID, cacheKey, retvo);
		}
		
		return retvo;
		
		
		
	}
	
	

}
