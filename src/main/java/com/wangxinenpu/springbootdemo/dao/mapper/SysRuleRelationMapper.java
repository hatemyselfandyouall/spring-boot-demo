package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.po.SysRuleRelation;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 对规则关系表操作
 * @author haoxz11MyBatis 
 * @created Mon Mar 25 14:59:55 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysRuleRelationMapper {
	/**
	 * 插入规则关系表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertSysRuleRelation(SysRuleRelation record);

	/**
	 * 搜索规则关系表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysRuleRelation> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索规则关系表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysRuleRelation> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索规则关系表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
}