package com.wangxinenpu.springbootdemo.dataobject.po;

import com.wangxinenpu.springbootdemo.dataobject.vo.root.LogBaseVo;

import java.util.Date;


/**
 * 
 * 用户角色对照
 * @author haoxz11MyBatis 
 * @created Sat Mar 30 01:44:16 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysUserRole extends LogBaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段：用户id
	 *
	 * @haoxz11MyBatis
	 */
	private String userId;

	/**
	 * 字段：角色id
	 *
	 * @haoxz11MyBatis
	 */
	private String roleId;
	
	/**
	 * 机构ID
	 */
	private Long orgId;

	/**
	 * 字段：创建日期
	 *
	 * @haoxz11MyBatis
	 */
	private Date createTime;

	/**
	 * 字段：修改时间
	 *
	 * @haoxz11MyBatis
	 */
	private Date modifyTime;

	/**
	 * 字段:sys_user_role.dispatchauth
	 *
	 * @haoxz11MyBatis
	 */
	private String dispatchauth;

	/**
	 * 读取：用户id
	 *
	 * @return sys_user_role.user_id
	 *
	 * @haoxz11MyBatis
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置：用户id
	 *
	 * @param userId sys_user_role.user_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 读取：角色id
	 *
	 * @return sys_user_role.role_id
	 *
	 * @haoxz11MyBatis
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * 设置：角色id
	 *
	 * @param roleId sys_user_role.role_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * 读取：创建日期
	 *
	 * @return sys_user_role.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建日期
	 *
	 * @param createTime sys_user_role.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return sys_user_role.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime sys_user_role.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 读取：sys_user_role.dispatchauth
	 *
	 * @return sys_user_role.dispatchauth
	 *
	 * @haoxz11MyBatis
	 */
	public String getDispatchauth() {
		return dispatchauth;
	}

	/**
	 * 设置：sys_user_role.dispatchauth
	 *
	 * @param dispatchauth sys_user_role.dispatchauth
	 *
	 * @haoxz11MyBatis
	 */
	public void setDispatchauth(String dispatchauth) {
		this.dispatchauth = dispatchauth;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
}