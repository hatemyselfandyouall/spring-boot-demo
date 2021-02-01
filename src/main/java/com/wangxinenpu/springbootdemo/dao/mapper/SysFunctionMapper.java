package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysFunctionDTO;
import com.wangxinenpu.springbootdemo.dataobject.po.SysFunction;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 对系统功能表操作
 * 
 * @author haoxz11MyBatis
 * @created Mon Mar 25 14:59:55 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11
 *          Exp $
 * @haoxz11MyBatis
 */
public interface SysFunctionMapper {
	/**
	 * 插入系统功能表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertSysFunction(SysFunction record);

	/**
	 * 根据主键得到系统功能表表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysFunction getByPrimaryKey(Long id);

	/**
	 * 更新系统功能表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysFunction(SysFunction record);

	/**
	 * 搜索系统功能表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysFunction> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索系统功能表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysFunction> getListByWhere(HashMap<String, Object> searchMap);


	List<SysFunction> queryRbFlagListById(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索系统功能表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);

	/**
	 * 查询角色所拥有的功能
	 * 
	 * @param roleIds
	 * @return
	 */
	List<SysFunction> queryFunctionListByRoleId(@Param("roleIds") String roleIds);

	/**
	 * 根据用户拥有权限和菜单类型查询
	 * @param roleIds
	 * @param funType
	 * @return
	 */
	List<SysFunction> findByFunTypeAndUserList(@Param("roleIds") String roleIds, @Param("funType") String funType);

	/**
	 * 根据角色和是否接入流程查询菜单
	 * @param roleIds
	 * @param isBus
	 * @return
	 */
	List<SysFunction> queryFunctionListByRoleIdsAndIsBus(@Param("roleIds") String roleIds, @Param("isBus") String isBus, @Param("funType") String funType);

	/**
	 * 查询所有功能
	 * @return
	 */
	List<SysFunction> getFunctionList();

	/**
	 * 根据角色类型查询可授权菜单
	 * @param roleType
	 * @return
	 */
	List<SysFunction> findTreesByRoleType(@Param("roleType") String roleType);

	/**
	 * 查询所有菜单
	 * @return
	 */
	List<SysFunction> queryAllFunctionList();

	/**
	 * 根据是否接入流程查询所有菜单
	 * @return
	 */
	List<SysFunction> queryFunctionListByIsBus(String isBus);

	/**
	 * 根据是否接入流程和菜单类型查询
	 * @param isBus
	 * @param funType
	 * @return
	 */
	List<SysFunction> queryFunctionListByIsBusAndFunType(@Param("isBus") String isBus, @Param("funType") String funType);

	/**
	 * 根据id和链接查询
	 * @param id
	 * @param location
	 * @return
	 */
	List<SysFunction> checkLocation(@Param("id") Long id, @Param("location") String location);

	/**
	 * 根据菜单ID查询下级菜单
	 *
	 * @haoxz11MyBatis
	 */
	List<SysFunction> getListByParentId(Long functionId);

	/**
	 * 根据主键ID删除
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * 根据菜单类型查询所有功能类型菜单
	 * @param funType
	 * @return
	 */
	List<SysFunction> queryNodeTypeListByFunType(@Param("funType") String funType);

	/**
	 * 根据菜单类型查询菜单
	 * @param funType
	 * @return
	 */
	List<SysFunction> findByFunTypeList(@Param("funType") String funType);


	List<SysFunction> findAccessList(@Param("channelCode") String funType);

	/**
	 * 根据菜单类型查询管理员菜单
	 * @param funType
	 * @return
	 */
	List<SysFunction> findAdminByFunTypeList(@Param("funType") String funType);

	/**
	 * 根据当前排序值和父节点修改排序
	 * @param funOrder
	 * @param parentId
	 * @return
	 */
	int updateFunOrderAdd(Map<String, Object> map);

	int updateFunOrderSmall(Map<String, Object> map);

	int updateFunOrderBig(Map<String, Object> map);

	/**
	 * 根据菜单链接查询菜单
	 * @param location
	 * @return
	 */
	List<SysFunction> findByLocation(@Param("location") String location);

	/**
	 * 修改菜单所属节点
	 * @param map
	 * @return
	 */
	int updateFunNode(Map<String, Object> map);

	/**
	 * 根据父节点查询最大排序值
	 * @param parentId
	 * @return
	 */
	int findMaxFunOrder(Long parentId);

	/**
	 * 根据业务类型编号查询菜单
	 * @param number
	 * @return
	 */
	SysFunction findFunctionByBussiness(@Param("number") String number);

	SysFunction findByTitle(@Param("title") String title, @Param("funType") String funType);


	public List<SysFunction> selectByOrgIdAndSystemId(@Param("systemId") Long systemId, @Param("orgId") Long orgId);

	public List<SysFunction> selectByUserIdAndSystemId(@Param("channelCode") String channelCode, @Param("userId") Long userId);

	/**
	 * 查询角色所拥有的功能(不包含管理系统)
	 * @param roleIds
	 * @return
	 */
	List<SysFunction> queryFunListByRoleId(@Param("roleIds") List<String> roleIds);


	/**
	 * 根据菜单标题查询菜单
	 * @param title
	 * @return
	 */
	List<SysFunction> findByFunctitle(@Param("title") String title);

	/**
	 * 查询组名
	 * @param title
	 * @return
	 */
	List<SysFunction> selectByGroupName();


	public int deleteByFunType(@Param("funType") String funType);


	List<SysFunctionDTO> findFunctionInUuid(@Param("uuids") List<String> uuids);

	List<SysFunction> tranList();
	
	/**
	 * 查询所有省级菜单和市级菜单
	 * @return
	 */
	List<SysFunction> getFunLevelFunctionList();
}