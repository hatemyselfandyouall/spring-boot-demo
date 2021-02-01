package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysUserRoleDTO;
import com.wangxinenpu.springbootdemo.dataobject.po.SysUserRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 对用户角色对照操作
 * @author haoxz11MyBatis 
 * @created Mon Mar 25 14:59:55 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysUserRoleMapper {
	/**
	 * 插入用户角色对照记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertSysUserRole(SysUserRole record);

	/**
	 * 搜索用户角色对照列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysUserRole> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索用户角色对照列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysUserRole> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索用户角色对照的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
	
	/**
	 * 根据角色ID删除
	 * @param roleId
	 * @return
	 */
	int deleteByRoleId(String roleId);
	
	/**
	 * 根据用户ID删除
	 * @return
	 */
	int deleteByUserId(SysUserRole sysUserRole);
	
	/**
	 * 查询用户绑定的角色
	 * @param userId
	 * @return
	 */
	List<SysUserRole> findByUserId(String userId);
	
	/**
	 * 根据角色ID查询
	 * @param roleId
	 * @return
	 */
	List<SysUserRole> findByRoleId(String roleId);
	
	/**
	 * 批量新增
	 * @param list
	 */
	void insertByBatch(List<SysUserRole> list);

	void saveUserRole(@Param("sysUserRoleDTOS") List<SysUserRoleDTO> sysUserRoleDTOS);
}