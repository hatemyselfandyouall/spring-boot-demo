package com.wangxinenpu.springbootdemo.service.facade;


import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckInformationDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysReqBusInfoDTO;
import star.bizbase.exception.BizRuleException;

import java.util.HashMap;
import java.util.List;

/**
 * 审核信息服务接口
 * 
 * @author Administrator
 *
 */
public interface SysCheckInformationFacade {
	/**
	 * 新增审核信息po信息
	 * 
	 * @param po
	 * @return
	 * @throws BizRuleException
	 */
	public Long addSysCheckInformation(SysCheckInformationDTO po) throws BizRuleException;

	/**
	 * 根据主键得到审核信息表记录
	 * 
	 * @param id
	 * @return
	 */
	public SysCheckInformationDTO getByPrimaryKey(Long id);

	/**
	 * 更新po信息
	 * 
	 * @param po
	 * @return
	 */
	public int updatepo(SysCheckInformationDTO po);

	/**
	 * 根据参数查询 获取审核信息列表 带分页
	 * 
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysCheckInformationDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size);

	/**
	 * 根据参数查询审核信息列表
	 * 
	 * @param searchMap
	 * @return
	 */
	public List<SysCheckInformationDTO> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 根据参数查询审核信息的记录数量
	 * 
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap);
	
    /**
     * 根据审核信息id删除
     * @param id
     */
    public void deleteByPrimaryKey(Long id)throws BizRuleException;

	/**
	 * 根据流程ID和业务ID删除
	 * @param searchMap
	 * @return
	 */
	public void deleteByProIdAndBusId(HashMap<String, Object> searchMap)throws BizRuleException;
    
    /**
     * 根据业务ID查询
     * @param busId
     * @return
     */
    public List<SysCheckInformationDTO> queryListByBusId(HashMap<String, Object> searchMap);
    
    /**
     * 查询由我审核的信息
     * @param
     * @return
     */
    public List<SysCheckInformationDTO> getListByCheckPeople(HashMap<String, Object> searchMap);
    /**
     * 查询由我审核的信息数量
     * @param searchMap
     * @return
     */
    public int getCountByCheckPeople(HashMap<String, Object> searchMap);
    
    /**
     * 根据当前登录人和状态查询由我审核的业务ID
     * @param userId
     * @return
     */
    public List<Long> findBusIdByUserId(Long userId, String checkResult);

    /**
     * 根据当前登录人和状态查询当前由我审核的且上级审核通过的业务列表 opesonId list
     * @throws BizRuleException
     * @user xxh
     * @since 2020年6月8日下午2:41:00
     */
    public List<Long> getCacheMyCheckingBusIdListByPrePass(Long userId, String checkResult) throws BizRuleException;
    
    /**
     * 接收业务返回信息
     * @param po
     * @return
     * @throws BizRuleException
     */
    public Long addSysReqBusInfo(SysReqBusInfoDTO po);
}
