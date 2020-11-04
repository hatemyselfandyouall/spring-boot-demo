package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.po.SysUserOrg;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 对用户机构关系对照操作
 * @author haoxz11MyBatis 
 * @created Mon Mar 25 14:59:55 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysUserOrgMapper {
	/**
	 * 插入用户机构关系对照记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertSysUserOrg(SysUserOrg record);

	/**
	 * 搜索用户机构关系对照列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysUserOrg> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索用户机构关系对照列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysUserOrg> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索用户机构关系对照的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
	
	/**
	 * 根据用户ID删除
	 * @param userId
	 * @return
	 */
	int deleteByUserId(Long userId);
	
	/**
	 * 查询用户绑定的机构
	 * @param userId
	 * @return
	 */
	List<SysUserOrg> findByUserId(Long userId);
	
	/**
	 * 查询用户关联机构下的用户
	 * @param userId
	 * @return
	 */
	List<SysUserOrg> queryUserOrgByOrgIds(Long userId);
	
	
}