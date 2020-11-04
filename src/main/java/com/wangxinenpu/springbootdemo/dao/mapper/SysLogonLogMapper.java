package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.po.SysLogonLog;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 对系统登录日志操作
 * @author haoxz11MyBatis 
 * @created Mon Mar 25 14:59:55 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysLogonLogMapper {
	/**
	 * 插入系统登录日志记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertSysLogonLog(SysLogonLog record);

	/**
	 * 根据主键得到系统登录日志表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysLogonLog getByPrimaryKey(String id);

	/**
	 * 更新系统登录日志记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysLogonLog(SysLogonLog record);

	/**
	 * 搜索系统登录日志列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysLogonLog> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索系统登录日志列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysLogonLog> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索系统登录日志的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
}