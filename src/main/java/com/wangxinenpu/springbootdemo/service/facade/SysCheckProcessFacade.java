package com.wangxinenpu.springbootdemo.service.facade;

import com.wangxinenpu.springbootdemo.dataobject.vo.*;
import star.bizbase.exception.BizRuleException;

import java.util.HashMap;
import java.util.List;

/**
 * 审核流程服务接口
 * 
 * @author Administrator
 *
 */
public interface SysCheckProcessFacade {
	/**
	 * 新增审核流程po信息
	 * 
	 * @param po
	 * @return
	 * @throws BizRuleException
	 */
	public Long addSysCheckProcess(SysCheckProcessDTO po) throws BizRuleException;

	/**
	 * 根据主键得到审核流程表记录
	 * 
	 * @param id
	 * @return
	 */
	public SysCheckProcessDTO getByPrimaryKey(Long id);

	/**
	 * 更新po信息
	 * 
	 * @param po
	 * @return
	 */
	public int updatepo(SysCheckProcessDTO po);

	/**
	 * 根据参数查询 获取审核流程列表 带分页
	 * 
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysCheckProcessDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size);

	/**
	 * 根据参数查询审核流程列表
	 * 
	 * @param searchMap
	 * @return
	 */
	public List<SysCheckProcessDTO> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 根据参数查询审核流程的记录数量
	 * 
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap);
	
    /**
     * 根据审核流程id删除
     * @param id
     */
    public void deleteByPrimaryKey(Long id)throws BizRuleException;
    
    /**
     * 根据审核配置Id查询最大版本号
     * @param configId
     * @return
     */
    public String findVersionByConfigId(Long configId);
    
    /**
     * 根据审核配置Id和步骤查询最大流程ID
     * @param searchMap
     * @return
     */
    public String findIdByConfigIdAndStep(HashMap<String, Object> searchMap);
    
    /**
     * 根据配置ID查询最大版本流程
     * @param configId
     * @return
     */
    public List<SysCheckProcessDTO> getListByConfigId(Long configId);
    
    /**
     * 根据流程ID查找
     * @param proIds
     * @return
     */
    public List<SysCheckProcessDTO> queryProListByProIds(String proIds);

	List<String> queryProStepByUserId(Long orgId, Long funId, String userId);
	
	/**
	 * 批量新增
	 * @param list
	 */
	public void insertBatch(List<SysCheckProcessDTO> list);

}
