package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysUserDTO;
import com.wangxinenpu.springbootdemo.dataobject.po.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 对系统用户表操作
 * @author haoxz11MyBatis 
 * @created Thu Mar 21 14:10:27 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysUserMapper {
	/**
	 * 插入系统用户表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertSysUser(SysUser record);

	/**
	 * 根据主键得到系统用户表表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysUser getByPrimaryKey(Long id);

	/**
	 * 更新系统用户表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysUser(SysUser record);

	/**
	 * 搜索系统用户表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysUser> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索系统用户表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysUser> getListByWhere(HashMap<String, Object> searchMap);

	List<SysUser> getAllList();

	/**
	 * 得到搜索系统用户表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
	
	/**
	 * 查找用户对象
	 * @param loginName
	 * @return
	 */
	SysUser getByLoginName(@Param("loginName") String loginName);

	SysUser getByLogonNameAndUserIdNot(@Param("loginName") String loginName, @Param("userId") Long userId);

	List<SysUser> queryUserListByRoleIds(@Param("roleIds") String roleIds);

	List<SysUser> queryUserListByUserType(@Param("orgId") Long orgId, @Param("userType") String userType);

	List<SysUser> queryUserListByDeparment(@Param("department") String department, @Param("roleType") String roleType);

	List<SysUser> getListByIds(@Param("ids") String ids);
	/**
	 * @param ythUserId
	 * @return
	 */
	SysUser getCacheSysUserByYthUserId(@Param("ythUserId") String ythUserId);

	/**
	 * 根据流程获取可指派人员
	 * @return
	 */
	List<SysUser> getUserListByProId(HashMap<String, Object> searchMap);

	/**
	 * 根据流程获取可指派人员
	 * @return
	 */
	List<SysUserDTO> findUserAndRole(@Param("logonName") String logonName, @Param("displayName") String displayName, @Param("orgId") Long orgId, @Param("userState") String userState);

	List<SysUser> selectByFunction(@Param("functionId") Long functionId, @Param("orgenterCode") String orgenterCode);


	/**
	 * 根据userId 修改密码
	 * @param userId
	 * @param password
	 * @return
	 */
	public Integer updatePSDByUserId(@Param("userId") Long userId, @Param("password") String password);


	/**
	 * 根据电话查询用户
	 * @param phone
	 * @return
	 */
	public List<SysUser> findUserByPhone(@Param("phone") String phone);

	/**
	 * 根据用户ID删除用户
	 * @param userId
	 * @return
	 */
	int deleteByUserId(Long userId);
}