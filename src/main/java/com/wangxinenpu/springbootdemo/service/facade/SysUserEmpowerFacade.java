package com.wangxinenpu.springbootdemo.service.facade;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysUserEmpowerDTO;
import star.bizbase.exception.BizRuleException;

import java.util.HashMap;
import java.util.List;

/**
 * 用户授权服务接口
 * 
 * @author Administrator
 *
 */
public interface SysUserEmpowerFacade {
	/**
	 * 新增用户授权po信息
	 * 
	 * @param po
	 * @return
	 * @throws BizRuleException
	 */
	public Long addSysUserEmpower(SysUserEmpowerDTO po) throws BizRuleException;

	/**
	 * 根据主键得到用户授权表记录
	 * 
	 * @param id
	 * @return
	 */
	public SysUserEmpowerDTO getByPrimaryKey(Long id);

	/**
	 * 更新po信息
	 * 
	 * @param po
	 * @return
	 */
	public int updatepo(SysUserEmpowerDTO po);

	/**
	 * 根据参数查询 获取用户授权列表 带分页
	 * 
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysUserEmpowerDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size);

	/**
	 * 根据参数查询用户授权列表
	 * 
	 * @param searchMap
	 * @return
	 */
	public List<SysUserEmpowerDTO> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 根据参数查询用户授权的记录数量
	 * 
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap);
	
    /**
     * 根据用户授权id删除
     * @param id
     */
    public void deleteByPrimaryKey(Long id)throws BizRuleException;
    
    /**
	 * 
	 * 缓存中 根据天正ythUserId 获取系统userid
	 * @author xhy
	 * @since:2019年3月21日下午3:03:53
	 * @param id
	 * @return
	 */
	public String getCacheEmpowerUserIdById(String ythUserId);
	
	/**
	 * 查询时间段内的一体化用户id
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<SysUserEmpowerDTO> getlistByUserIds(String startTime, String endTime);
    
}
