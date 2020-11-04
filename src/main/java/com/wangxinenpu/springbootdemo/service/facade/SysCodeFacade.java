package com.wangxinenpu.springbootdemo.service.facade;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysCodeDTO;
import star.bizbase.exception.BizRuleException;

import java.util.HashMap;
import java.util.List;

/**
 * 代码维护服务接口
 * 
 * @author Administrator
 *
 */
public interface SysCodeFacade {

	/**
	 * 新增代码维护po信息
	 * 
	 * @param po
	 * @return
	 * @throws BizRuleException
	 */
	public int addSysCode(SysCodeDTO po) throws BizRuleException;

	/**
	 * 根据主键得到系统代码维护表记录
	 * 
	 * @param id
	 * @return
	 */
	public SysCodeDTO getByPrimaryKey(Long id);

	/**
	 * 更新po信息
	 * 
	 * @param po
	 * @return
	 */
	public int updatepo(SysCodeDTO po);

	/**
	 * 根据参数查询 获取代码维护列表 带分页
	 * 
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysCodeDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size);

	/**
	 * 根据参数查询代码维护列表
	 * 
	 * @param searchMap
	 * @return
	 */
	public List<SysCodeDTO> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 根据参数查询系统代码维护的记录数量
	 * 
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap);

	/**
	 * 根据代码类型查询
	 * 
	 * @param codeType
	 * @return
	 */
	public List<SysCodeDTO> findByCodeType(String codeType);
	
	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	void deleteByPrimaryKey(Long id) throws BizRuleException;
	
	/**
	 * 查询相同类型代码值是否重复
	 * @param searchMap
	 * @return
	 */
	public List<SysCodeDTO> findByCodeTypeAndCodeValue(HashMap<String, Object> searchMap);

}
