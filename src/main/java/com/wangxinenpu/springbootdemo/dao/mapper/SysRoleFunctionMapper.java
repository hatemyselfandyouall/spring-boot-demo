package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.po.SysRole;
import com.wangxinenpu.springbootdemo.dataobject.po.SysRoleFunction;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 角色功能对照操作
 * @author haoxz11MyBatis 
 * @created Mon Mar 25 14:59:55 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysRoleFunctionMapper {
	/**
	 * 插入角色功能对照记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertSysRoleFunction(SysRoleFunction record);

	/**
	 * 根据主键得到角色功能对照表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysRoleFunction getByPrimaryKey(String relationId);

	/**
	 * 更新角色功能对照记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysRoleFunction(SysRoleFunction record);

	/**
	 * 搜索角色功能对照列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysRoleFunction> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索角色功能对照列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysRoleFunction> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索角色功能对照的记录数量
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
	 * 根据功能ID删除
	 * @param functionId
	 * @return
	 */
	int deleteByFunctionId(Long functionId);
	
	/**
	 * 根据功能ID查询
	 * @param functionId
	 * @return
	 */
	List<SysRoleFunction> findByFunctionId(Long functionId);
	
	/**
	 * 根据角色ID查询
	 * @param roleId
	 * @return
	 */
	List<SysRoleFunction> findByRoleId(String roleId);

	void deleteByRoleIdsAndFunctionId(@Param("functionId") Long functionId, @Param("getPermissionRoleList") List<SysRole> getPermissionRoleList);

	void insertSysRoleFunctionList(@Param("list") List<SysRoleFunction> sysRoleFunctions);

    void changeSelectStatesByRoleListAndFunction(@Param("functionId") Long id, @Param("getPermissionRoleList") List<SysRole> losePermissionRoleList);
}