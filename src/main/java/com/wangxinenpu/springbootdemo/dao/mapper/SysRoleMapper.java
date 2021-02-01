package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysRoleDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysRoleFunctionDTO;
import com.wangxinenpu.springbootdemo.dataobject.po.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 对系统角色操作
 * @author haoxz11MyBatis 
 * @created Mon Mar 25 14:59:55 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysRoleMapper {
	/**
	 * 插入系统角色记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertSysRole(SysRole record);

	/**
	 * 根据主键得到系统角色表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysRole getByPrimaryKey(String id);

	/**
	 * 更新系统角色记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysRole(SysRole record);

	/**
	 * 搜索系统角色列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysRole> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索系统角色列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysRole> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索系统角色的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
	
	/**
	 * 根据角色名和id查询
	 *
	 * @haoxz11MyBatis
	 */
	SysRole getByRoleNameAndRoleId(HashMap<String, Object> searchMap);
	
	/**
	 * 根据主键删除角色
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(String id);
	
	/**
	 * 查询当前用户拥有的角色
	 * @param userId
	 * @return
	 */
	List<SysRole> queryRoleByUserId(String userId);
	
	/**
	 * 查询管理员角色
	 * @return
	 */
	List<SysRole> queryByAdmin();
	
	/**
	 * 根据区域查询角色
	 * @param areaId
	 * @return
	 */
	List<SysRole> queryRoleByArea(Long areaId);
	
	/**
	 * 根据机构查询角色
	 * @param orgId
	 * @return
	 */
	List<SysRole> queryByOrgId(Long orgId);
	
	List<SysRole> findAllByRoletype(String roleType);
	List<SysRole> findByRoletypeAndOrgid(Long orgTd);
    List<SysRole> queryRoleByRoletypeAndAreaid(Long areaId);
    /**
     * 查询所有角色
     * @return
     */
    List<SysRole> findAll();
    
    /**
     * 根据系统类型获取角色
     * @param funType
     * @return
     */
    List<SysRole> findRoleByFunType(@Param("funType") String funType, @Param("orgId") Long orgId);

	List<SysRoleDTO> findRoleInRoleCode(@Param("RoleCodes") List<String> roleCodes);

    /**
     * 批量新增角色
     * @param list
     */
    void insertByBatch(List<SysRole> list);

	SysRole findByRoleName(String roleName);

	List<SysRoleDTO> findRoleAndFunction(@Param("roleName") String roleName, @Param("roleDesc") String roleDesc, @Param("roleType") String roleType, @Param("orgId") Long orgId);

	List<SysRoleDTO> fidno();
	List<SysRoleFunctionDTO> fidyes(@Param("roleName") String roleName);

    List<SysRole> findByRoletypeAndOrgIds(@Param("type") Integer type, @Param("orgIds") List<Long> orgIds);
}