package com.wangxinenpu.springbootdemo.dao.mapper.cachemapper;


import com.wangxinenpu.springbootdemo.constant.SysbaseCacheEnum;
import com.wangxinenpu.springbootdemo.dao.mapper.SysAreaMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.SysOrgDepartmentMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.SysOrgMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.SysUserMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.*;
import com.wangxinenpu.springbootdemo.service.impl.SysUserImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import star.bizbase.util.constant.SysCacheTimeDMO;
import star.modules.cache.CacheKeyLock;
import star.modules.cache.CachesKeyService;
import star.modules.cache.enumerate.BaseCacheEnum;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统用户service
 * @author xhy
 * @since:2019年3月21日下午2:15:50
 */
@Component
public class SysUserCacheMapper {
	private static Logger logger = LoggerFactory.getLogger(SysUserImpl.class);
	@Resource
	private SysUserMapper sysUserMapper;
	@Resource
	private CachesKeyService cachesKeyService;
	@Autowired
	SysAreaMapper sysAreaMapper;
	@Autowired
	SysOrgMapper sysOrgMapper;

	@Autowired
	SysOrgDepartmentMapper sysOrgDepartmentMapper;
	
	/**
	 * 
	 * 缓存中 获取用户信息数据。。。
	 * 根据主键得到系统用户表记录 
	 * @author xhy
	 * @since:2019年3月21日下午3:03:53
	 * @param id
	 * @return
	 */
	public SysUserDTO getCacheSysUserById(Long id){
		if(id == null) {
			logger.info("sysuserService.getByPrimaryKey error:id={}",id);
			return null;
		}
		
		String cacheKey = String.valueOf(id);
		return new CacheKeyLock(cachesKeyService, SysCacheTimeDMO.CACHETIMEOUT_10M) {//缓存10分钟
			@Override
			protected Object doGetList(BaseCacheEnum type, String key) {
				SysUser po = sysUserMapper.getByPrimaryKey(id);
				if(po==null) return null;
				SysUserDTO sysUserDTO = new SysUserDTO();
				sysUserDTO = po.copyTo(SysUserDTO.class);
					if (sysUserDTO.getOrgId()!=null){
						SysOrg sysOrg =  sysOrgMapper.getByPrimaryKey(sysUserDTO.getOrgId());
						if (sysOrg!=null&&sysOrg.getOrgCode()!=null){
							sysUserDTO.setOrgCode(sysOrg.getOrgCode());
						}
						if (sysOrg!=null&&sysOrg.getOrgName()!=null){
							sysUserDTO.setOrgName(sysOrg.getOrgName());
						}
						if (sysOrg!=null&&sysOrg.getOrgenterCode()!=null){
							sysUserDTO.setOrgenterCode(sysOrg.getOrgenterCode());
						}
						if (sysOrg!=null&&sysOrg.getRegionCode()!=null){
							sysUserDTO.setAreaId(Long.parseLong(sysOrg.getRegionCode()));
						}
						if (sysUserDTO.getAreaId()!=null){
							SysArea sysArea= sysAreaMapper.getByPrimaryKey(sysUserDTO.getAreaId());
							if(sysArea!=null) {
								sysUserDTO.setAreaName(sysArea.getAreaName());
							}
						}
						logger.info("【sysOrg】：{}",sysOrg);
						logger.info("【sysUserDTO】：{}",sysUserDTO);
					}
					if (sysUserDTO.getDepartment()!=null){
						String[] deptmentIds = sysUserDTO.getDepartment().split(",");
						String deptmentId = "'" + StringUtils.join(deptmentIds, "','") + "'";
						List<SysOrgDepartment> sysOrgDepartments = sysOrgDepartmentMapper.queryDepartmentListByDepartmentIds(deptmentId);
						sysUserDTO.setSysOrgDepartmentList(sysOrgDepartments);
						logger.info("【sysOrgs】：{}",sysOrgDepartments);
					}

				return sysUserDTO;
			}
		}.getCache(SysbaseCacheEnum.SYSUSER_BY_ID, cacheKey);
		
	}
	
	/**
	 * 根据外部天正id 获取sysuser对象信息
	 * @param ythUserId
	 * @return
	 */
	public SysUserDTO getCacheSysUserByYthUserId(String ythUserId){
		if(StringUtil.isEmpty(ythUserId)) {
			logger.info("sysuserService.getCacheSysUserIdByYthUserId error:ythUserId={}",ythUserId);
			return null;
		}
		
		String cacheKey = ythUserId;
		return new CacheKeyLock(cachesKeyService, SysCacheTimeDMO.CACHETIMEOUT_24H) {//缓存24h
			@Override
			protected Object doGetList(BaseCacheEnum type, String key) {
				SysUser po = sysUserMapper.getCacheSysUserByYthUserId(ythUserId);
				if(po==null) return null;
				return po.copyTo(SysUserDTO.class);
			}
		}.getCache(SysbaseCacheEnum.GETCACHESYSUSER_YTHUSERID, cacheKey);
		
	}



	/**
	 * 清除缓存
	 * @param id
	 * @return
	 */
	public void deleteCache(Long id){
		if(id == null) {
			logger.info("sysuserService.getByPrimaryKey error:id={}",id);
		}
		String cacheKey = String.valueOf(id);
		cachesKeyService.deleteCache(SysbaseCacheEnum.SYSUSER_BY_ID,cacheKey);

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
	public SysUserDTO getPriCacheSysUserById(Long id){
		if(id == null) {
			logger.info("sysuserService.getByPrimaryKey error:id={}",id);
			return null;
		}
		
		String cacheKey = String.valueOf(id);
	
		SysUserDTO retvo = cachesKeyService.getFromCache(SysbaseCacheEnum.SYSUSER_BY_ID, cacheKey);
		if(retvo==null) {
			SysUser po = sysUserMapper.getByPrimaryKey(id);
			if(po==null) return null;
			retvo =  po.copyTo(SysUserDTO.class);
			cachesKeyService.putInCache(SysbaseCacheEnum.SYSUSER_BY_ID, cacheKey, retvo);
		}
		
		return retvo;
		
		
		
	}



}
