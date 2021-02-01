package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckBusinessDTO;
import com.wangxinenpu.springbootdemo.dataobject.po.SysCheckBusiness;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 对审核业务表操作
 * @author haoxz11MyBatis 
 * @created Tue May 28 01:26:21 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysCheckBusinessMapper {
	/**
	 * 插入审核业务表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertSysCheckBusiness(SysCheckBusiness record);

	/**
	 * 根据主键得到审核业务表表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysCheckBusiness getByPrimaryKey(Long id);

	/**
	 * 更新审核业务表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysCheckBusiness(SysCheckBusiness record);

	/**
	 * 搜索审核业务表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysCheckBusinessDTO> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索审核业务表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysCheckBusinessDTO> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索审核业务表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
	
	/**
	 * 根据ID删除
	 * @param id
	 * @return
	 */
	int deleteById(Long id);
	
	/**
	 * 根据条件查询审核业务列表
	 * @param searchMap
	 * @return
	 */
	List<SysCheckBusinessDTO> getBusinessList(HashMap<String, Object> searchMap);
}