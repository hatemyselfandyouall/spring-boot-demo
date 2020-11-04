package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.po.SysRule;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 对系统规则表操作
 * @author haoxz11MyBatis 
 * @created Mon Mar 25 14:59:55 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysRuleMapper {
	/**
	 * 插入系统规则表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertSysRule(SysRule record);

	/**
	 * 根据主键得到系统规则表表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysRule getByPrimaryKey(Integer id);

	/**
	 * 更新系统规则表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysRule(SysRule record);

	/**
	 * 搜索系统规则表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysRule> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索系统规则表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysRule> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索系统规则表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
}