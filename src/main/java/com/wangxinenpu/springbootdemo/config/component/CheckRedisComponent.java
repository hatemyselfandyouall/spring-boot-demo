package com.wangxinenpu.springbootdemo.config.component;


import com.wangxinenpu.springbootdemo.constant.SysCheckCacheEnum;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysRoleDTO;
import com.wangxinenpu.springbootdemo.service.facade.SysRoleFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import star.bizbase.exception.BizRuleException;
import star.bizbase.util.constant.SysCacheTimeDMO;
import star.modules.cache.CachesKeyService;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 审核业务缓存类
 * @author Administrator
 *
 */
@Component
public class CheckRedisComponent {
	@Resource
	public CachesKeyService cachesKeyService;
	@Autowired
	private SysRoleFacade sysRoleFacade;
	
	/**
	 * 存入缓存
	 * @param bus_id
	 * @param map
	 */
	public void putRedis(String busId, Map<String, Object> map){
		cachesKeyService.putInCache(SysCheckCacheEnum.SYS_CHECK_BUSINESS_ID, busId, map, SysCacheTimeDMO.CACHETIMEOUT_90D);//缓存90天
	}
	
	/**
	 * 获取缓存，执行任务
	 * @param bus_id
	 * @return
	 * @throws BizRuleException 
	 */
	public boolean getRedis(String busId) throws BizRuleException{
		
		Map<String, Object> map = cachesKeyService.getFromCache(SysCheckCacheEnum.SYS_CHECK_BUSINESS_ID, busId);
		if(null != map){
			String type = map.get("type").toString();//操作类型
			String bus = map.get("bus").toString();//操作业务
			if("add".equals(type)){
				if("role".equals(bus)){
					SysRoleDTO sysRole = (SysRoleDTO)map.get("object");//业务对象
					sysRoleFacade.addSysRole(sysRole);
					cachesKeyService.deleteCache(SysCheckCacheEnum.SYS_CHECK_BUSINESS_ID, busId);//清除缓存
				}
			}
			return true;
		}
		return false;
	}

}
