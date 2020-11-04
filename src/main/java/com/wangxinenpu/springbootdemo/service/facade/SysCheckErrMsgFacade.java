package com.wangxinenpu.springbootdemo.service.facade;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckErrMsgDTO;
import star.bizbase.exception.BizRuleException;

import java.util.HashMap;
import java.util.List;

/**
 * 业务异常消息服务接口
 * 
 * @author Administrator
 *
 */
public interface SysCheckErrMsgFacade {
	/**
	 * 新增业务异常消息po信息
	 * 
	 * @param po
	 * @return
	 * @throws BizRuleException
	 */
	public Long addSysCheckErrMsg(SysCheckErrMsgDTO po) throws BizRuleException;

	/**
	 * 根据主键得到业务异常消息表记录
	 * 
	 * @param id
	 * @return
	 */
	public SysCheckErrMsgDTO getByPrimaryKey(Long id);

	/**
	 * 更新po信息
	 * 
	 * @param po
	 * @return
	 */
	public int updatepo(SysCheckErrMsgDTO po);

	/**
	 * 根据参数查询 获取业务异常消息列表 带分页
	 * 
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysCheckErrMsgDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size);

	/**
	 * 根据参数查询业务异常消息列表
	 * 
	 * @param searchMap
	 * @return
	 */
	public List<SysCheckErrMsgDTO> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 根据参数查询业务异常消息的记录数量
	 * 
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap);
	
    /**
     * 根据业务异常消息id删除
     * @param id
     */
    public void deleteByPrimaryKey(Long id)throws BizRuleException;
    

}
