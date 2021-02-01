package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.po.SysCheckAssign;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 对审核任务指派表操作
 * @author haoxz11MyBatis 
 * @created Tue May 28 01:26:21 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysCheckAssignMapper {
	/**
	 * 插入审核任务指派表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertSysCheckAssign(SysCheckAssign record);

	/**
	 * 根据主键得到审核任务指派表表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysCheckAssign getByPrimaryKey(Long id);

	/**
	 * 更新审核任务指派表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysCheckAssign(SysCheckAssign record);

	/**
	 * 搜索审核任务指派表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysCheckAssign> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索审核任务指派表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysCheckAssign> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索审核任务指派表的记录数量
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
}