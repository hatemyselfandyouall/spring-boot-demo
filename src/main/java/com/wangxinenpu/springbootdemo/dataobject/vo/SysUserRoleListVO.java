package com.wangxinenpu.springbootdemo.dataobject.vo;

import com.wangxinenpu.springbootdemo.dataobject.vo.root.LogBaseVo;

import java.util.List;


/**
 * 
 * 用户角色对照
 * @author haoxz11MyBatis 
 * @created Mon Mar 25 14:59:55 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysUserRoleListVO extends LogBaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public List<SysUserRoleDTO> getUserlist() {
		return userlist;
	}

	public void setUserlist(List<SysUserRoleDTO> userlist) {
		this.userlist = userlist;
	}

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


	private List<SysUserRoleDTO> userlist;

}