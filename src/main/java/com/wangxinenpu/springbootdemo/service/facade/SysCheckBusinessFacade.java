package com.wangxinenpu.springbootdemo.service.facade;


import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckBusinessDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckBusinessHistoryDTO;
import star.bizbase.exception.BizRuleException;

import java.util.HashMap;
import java.util.List;

/**
 * 审核业务服务接口
 * 
 * @author Administrator
 *
 */
public interface SysCheckBusinessFacade {
	/**
	 * 新增审核业务po信息
	 * 
	 * @param po
	 * @return
	 * @throws BizRuleException
	 */
	public Long addSysCheckBusiness(SysCheckBusinessDTO po) throws BizRuleException;

	/**
	 * 根据主键得到审核业务表记录
	 * 
	 * @param id
	 * @return
	 */
	public SysCheckBusinessDTO getByPrimaryKey(Long id);

	/**
	 * 更新po信息
	 * 
	 * @param po
	 * @return
	 */
	public int updatepo(SysCheckBusinessDTO po);

	/**
	 * 根据参数查询 获取审核业务列表 带分页
	 * 
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysCheckBusinessDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size);

	/**
	 * 根据参数查询审核业务列表
	 * 
	 * @param searchMap
	 * @return
	 */
	public List<SysCheckBusinessDTO> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 根据参数查询审核业务的记录数量
	 * 
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap);
	
    /**
     * 根据审核业务id删除
     * @param id
     */
    public void deleteByPrimaryKey(Long id)throws BizRuleException;
    
    /**
	 * 根据条件查询审核业务列表
	 * @param searchMap
	 * @return
	 */
	List<SysCheckBusinessDTO> getBusinessList(HashMap<String, Object> searchMap);


	List<SysCheckBusinessHistoryDTO> getHosListByBusId(Long busId, Integer page, Integer size);

    SysCheckBusinessHistoryDTO getHistoryById(Long hisId);
    
    /**
     * 新增审核历史记录
     * @param po
     * @return
     */
    public Long insertCheckBusinessHistory(SysCheckBusinessHistoryDTO po) ;
    
}
