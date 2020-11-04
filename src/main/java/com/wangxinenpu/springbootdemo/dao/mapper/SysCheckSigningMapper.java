package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.po.SysCheckSigning;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 对待签领任务操作
 * @author haoxz11MyBatis 
 * @created Tue May 28 01:26:21 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysCheckSigningMapper {
	/**
	 * 插入待签领任务记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertSysCheckSigning(SysCheckSigning record);

	/**
	 * 根据主键得到待签领任务表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysCheckSigning getByPrimaryKey(Long id);

	/**
	 * 更新待签领任务记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysCheckSigning(SysCheckSigning record);

	/**
	 * 搜索待签领任务列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysCheckSigning> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索待签领任务列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysCheckSigning> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 自主签领列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysCheckSigning> getListByCondition(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索待签领任务的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);


	int getCountByCondition(HashMap<String, Object> searchMap);


	/**
	 * 根据ID删除
	 * @param id
	 * @return
	 */
	int deleteById(Long id);
	
	/**
	 * 根据流程ID和业务ID删除
	 * @param searchMap
	 * @return
	 */
	int deleteByProIdAndBusId(HashMap<String, Object> searchMap);
	
}