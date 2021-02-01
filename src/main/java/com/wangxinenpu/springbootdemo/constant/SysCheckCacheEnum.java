package com.wangxinenpu.springbootdemo.constant;

import star.modules.cache.enumerate.BaseCacheEnum;
import star.modules.cache.enumerate.CacheResourceEnum;

/**
 * 审核模块 redis 缓存key
 * @author Administrator
 *
 */
public enum SysCheckCacheEnum implements BaseCacheEnum{
	/**
	 * 审核业务ID
	 */
	SYS_CHECK_BUSINESS_ID("sys_check_business_id"),
	
	/**
	 * 角色导入
	 */
	SYS_IMPORT_ROLE_CATCHE("sys_import_role_cache"),
	
	/**
	 * 用户导入
	 */
	SYS_IMPORT_USER_CATCHE("sys_import_user_cache"),
	
	/**
	 * 审核业务，终审数据缓存
	 */
	SYS_CHECK_OPSENO("sys_check_opseno"),
	
	/**
	 * 
	 */
	SYS_CHECK("sys_check");

	 private String type;

	 SysCheckCacheEnum(String type) {
	        this.type = type;
	}
	
	@Override
	public String getAnchor() {
	    return this.type;
	}
	
	@Override
	public BaseCacheEnum get(String key) {
	    if (key != null && key.length() > 0) {
	        for (CacheResourceEnum type : CacheResourceEnum.values()) {
	            if (key.equals(type.getAnchor())) {
	                return type;
	            }
	        }
	    }
	    return null;
	}

}
