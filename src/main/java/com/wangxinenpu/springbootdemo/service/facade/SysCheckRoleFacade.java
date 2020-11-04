package com.wangxinenpu.springbootdemo.service.facade;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckRoleDTO;
import star.bizbase.exception.BizRuleException;

import java.util.HashMap;
import java.util.List;

/**
 * 审核角色服务接口
 * 
 * @author Administrator
 *
 */
public interface SysCheckRoleFacade {
	/**
	 * 新增审核角色po信息
	 * 
	 * @param po
	 * @return
	 * @throws BizRuleException
	 */
	public Long addSysCheckRole(SysCheckRoleDTO po) throws BizRuleException;

	/**
	 * 根据主键得到审核角色表记录
	 * 
	 * @param id
	 * @return
	 */
	public SysCheckRoleDTO getByPrimaryKey(Long id);

	/**
	 * 更新po信息
	 * 
	 * @param po
	 * @return
	 */
	public int updatepo(SysCheckRoleDTO po);

	/**
	 * 根据参数查询 获取审核角色列表 带分页
	 * 
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysCheckRoleDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size);

	/**
	 * 根据参数查询审核角色列表
	 * 
	 * @param searchMap
	 * @return
	 */
	public List<SysCheckRoleDTO> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 根据参数查询审核角色的记录数量
	 * 
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap);
	
    /**
     * 根据审核角色id删除
     * @param id
     */
    public void deleteByPrimaryKey(Long id)throws BizRuleException;
    
    /**
     * 根据流程id删除
     * @param proId
     */
    public void deleteByProId(Long proId)throws BizRuleException;
    

}
