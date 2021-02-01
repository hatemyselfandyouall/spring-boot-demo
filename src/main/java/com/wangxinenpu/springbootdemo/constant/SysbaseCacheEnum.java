package com.wangxinenpu.springbootdemo.constant;

import star.modules.cache.enumerate.BaseCacheEnum;

/**
 * redis 缓存key
 * （按业务名称命名定义，大家不要重复了）
 * @author xhy
 * @since:2019年3月21日下午3:01:14
 */
public enum SysbaseCacheEnum implements BaseCacheEnum {

	/**
	 * 用户基础信息
	 */
	SYSUSER_BY_ID("sysuser_by_id"),
	/**
	 * 组织基础信息
	 */
	SYSORG_BY_ID("sysorg_by_id"),
	/**
	 * 区域基础信息
	 */
	SYSAREA_BY_ID("sysarea_by_id"),
	
	/**
	 * 天正userid---》系统userid
	 */
	GETCACHE_EMPOWERUSERID("getCacheEmpowerUserIdById"),
	
	/**
	 * 天正userid---》系统userid
	 */
	GETCACHESYSUSER_YTHUSERID("getCacheSysUserByYthUserId"),
	
	CACHEMYCHECKINGBUSIDLISTBYPARAM("CacheMyCheckingBusIdListByParam"),
	
	CACHEMYCHECKINGPROCESSRESPDTOBYPARAM("CacheMyCheckingProcessRespDTOByParam"),

	SYSINNEROPERATE_LOGMENUS("SysInnerOperate_LogMenus"),

	;

	private String type;

	SysbaseCacheEnum(String type) {
		this.type = type;
	}

	@Override
	public String getAnchor() {
		return this.type;
	}

	@Override
	public BaseCacheEnum get(String key) {
		if (key != null && key.length() > 0) {
			for (SysbaseCacheEnum type : SysbaseCacheEnum.values()) {
				if (key.equals(type.getAnchor())) {
					return type;
				}
			}
		}
		return null;
	}

}
