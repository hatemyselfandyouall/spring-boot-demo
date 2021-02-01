package com.wangxinenpu.springbootdemo.service.facade;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckUserDTO;
import star.bizbase.exception.BizRuleException;

import java.util.HashMap;
import java.util.List;

/**
 * 审核用户服务接口
 * 
 * @author Administrator
 *
 */
public interface SysCheckUserFacade {
	/**
	 * 新增审核用户po信息
	 * 
	 * @param po
	 * @return
	 * @throws BizRuleException
	 */
	public Long addSysCheckUser(SysCheckUserDTO po) throws BizRuleException;

	/**
	 * 根据主键得到审核用户表记录
	 * 
	 * @param id
	 * @return
	 */
	public SysCheckUserDTO getByPrimaryKey(Long id);

	/**
	 * 更新po信息
	 * 
	 * @param po
	 * @return
	 */
	public int updatepo(SysCheckUserDTO po);

	/**
	 * 根据参数查询 获取审核用户列表 带分页
	 * 
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysCheckUserDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size);

	/**
	 * 根据参数查询审核用户列表
	 * 
	 * @param searchMap
	 * @return
	 */
	public List<SysCheckUserDTO> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 根据参数查询审核用户的记录数量
	 * 
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap);
	
    /**
     * 根据审核用户id删除
     * @param id
     */
    public void deleteByPrimaryKey(Long id)throws BizRuleException;
    
    /**
     * 根据流程id删除
     * @param proId
     */
    public void deleteByProId(Long proId)throws BizRuleException;
    

}
