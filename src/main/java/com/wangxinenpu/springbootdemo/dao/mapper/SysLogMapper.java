package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.po.SysLog;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 对系统日志操作
 * @author haoxz11MyBatis 
 * @created Mon Mar 25 14:59:55 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysLogMapper {
	/**
	 * 插入系统日志记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertSysLog(SysLog record);

	/**
	 * 根据主键得到系统日志表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysLog getByPrimaryKey(String id);

	/**
	 * 更新系统日志记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysLog(SysLog record);

	/**
	 * 搜索系统日志列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysLog> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索系统日志列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysLog> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索系统日志的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
}