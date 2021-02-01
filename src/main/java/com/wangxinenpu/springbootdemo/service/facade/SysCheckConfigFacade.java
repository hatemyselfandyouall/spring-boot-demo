package com.wangxinenpu.springbootdemo.service.facade;


import com.wangxinenpu.springbootdemo.dataobject.vo.*;
import star.bizbase.exception.BizRuleException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 审核配置服务接口
 * 
 * @author Administrator
 *
 */
public interface SysCheckConfigFacade {
	/**
	 * 新增审核配置po信息
	 * 
	 * @param po
	 * @return
	 * @throws BizRuleException
	 */
	public Long addSysCheckConfig(SysCheckConfigDTO po) throws BizRuleException;

	/**
	 * 根据主键得到审核配置表记录
	 * 
	 * @param id
	 * @return
	 */
	public SysCheckConfigDTO getByPrimaryKey(Long id);

	/**
	 * 更新po信息
	 * 
	 * @param po
	 * @return
	 */
	public int updatepo(SysCheckConfigDTO po);

	/**
	 * 根据参数查询 获取审核配置列表 带分页
	 * 
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysCheckConfigDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size);

	/**
	 * 根据参数查询审核配置列表
	 * 
	 * @param searchMap
	 * @return
	 */
	public List<SysCheckConfigDTO> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 根据参数查询审核配置的记录数量
	 * 
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap);
	
    /**
     * 根据审核配置id删除
     * @param id
     */
    public void deleteByPrimaryKey(Long id)throws BizRuleException;
    
    /**
     * 查询是否配置通用审核
     * @param funId
     * @param busId
     * @return
     */
    public boolean isCheckConfig(Long funId, Long busId, Long userId, List<SysCheckErrMsgDTO> checkErrMsgList);

    /**
     * 查询是否配置通用审核、带层级
     * @param funId
     * @param busId
     * @param hierarchy
     * @return
     */
    public boolean isCheckConfig(Long funId, Long busId, Long userId, List<SysCheckErrMsgDTO> checkErrMsgList, String hierarchy);


    /**
     * 财务审核，是否配置通用审核
     * @param funId
     * @param busId
     * @return
     */
    public boolean isFinanceCheckConfig(Long funId, Long busId, Long userId);

    /**
     * 保存审核日志
     * @param po
     * @return
     * @throws BizRuleException
     */
    public Integer saveSysCheckChangelog(SysCheckChangelogDTO po) throws BizRuleException;

    /**
     * 审核变更日志列表
     * @param map
     * @return
     * @throws BizRuleException
     */
    public List<SysCheckChangelogDTO> getSysCheckChangelogList(Map<String, Object> map, int start, int size) throws BizRuleException;

    /**
     * 查询是否配置审核人员
     * @param funId
     * @param orgId
     * @return
     */
    public boolean getCheckPeople(Long funId, Long orgId)throws BizRuleException;
    
    /**
     * 批量更新
     * @param list
     * @return
     */
    public void  updateByBatch(List<SysCheckConfigDTO> list);
    
}
