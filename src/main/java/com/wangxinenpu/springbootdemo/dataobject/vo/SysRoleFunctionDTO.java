package com.wangxinenpu.springbootdemo.dataobject.vo;

import star.vo.BaseVo;

import java.util.Date;

/**
 * 
 * 角色功能对照
 * @author haoxz11MyBatis 
 * @created Mon Mar 25 14:59:55 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysRoleFunctionDTO extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段：关联id
	 *
	 * @haoxz11MyBatis
	 */
	private String relationId;

	/**
	 * 字段：角色id
	 *
	 * @haoxz11MyBatis
	 */
	private String roleId;

	/**
	 * 字段：功能id
	 *
	 * @haoxz11MyBatis
	 */
	private Long functionId;

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
	 * 选择状态 0：全选 1：半选
	 */
	private String selectState;

	/**
	 * 读取：关联id
	 *
	 * @return sys_role_function.relation_id
	 *
	 * @haoxz11MyBatis
	 */
	public String getRelationId() {
		return relationId;
	}

	/**
	 * 设置：关联id
	 *
	 * @param relationId sys_role_function.relation_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	/**
	 * 读取：角色id
	 *
	 * @return sys_role_function.role_id
	 *
	 * @haoxz11MyBatis
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * 设置：角色id
	 *
	 * @param roleId sys_role_function.role_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * 读取：功能id
	 *
	 * @return sys_role_function.function_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getFunctionId() {
		return functionId;
	}

	/**
	 * 设置：功能id
	 *
	 * @param functionId sys_role_function.function_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	/**
	 * 读取：创建日期
	 *
	 * @return sys_role_function.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建日期
	 *
	 * @param createTime sys_role_function.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return sys_role_function.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime sys_role_function.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getSelectState() {
		return selectState;
	}

	public void setSelectState(String selectState) {
		this.selectState = selectState;
	}
	
}