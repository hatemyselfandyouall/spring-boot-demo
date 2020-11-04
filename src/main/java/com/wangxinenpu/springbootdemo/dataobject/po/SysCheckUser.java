package com.wangxinenpu.springbootdemo.dataobject.po;

import star.vo.BaseVo;

import java.util.Date;

/**
 * 
 * 审核流程对应用户表
 * @author haoxz11MyBatis 
 * @created Tue May 28 01:26:21 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysCheckUser extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段：主键ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long id;

	/**
	 * 字段：用户ID
	 *
	 * @haoxz11MyBatis
	 */
	private String userId;

	/**
	 * 字段：用户名称
	 *
	 * @haoxz11MyBatis
	 */
	private String userName;

	/**
	 * 字段：流程ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long proId;

	/**
	 * 字段：创建时间
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
	 * 读取：主键ID
	 *
	 * @return sys_check_user.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：主键ID
	 *
	 * @param id sys_check_user.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：用户ID
	 *
	 * @return sys_check_user.user_id
	 *
	 * @haoxz11MyBatis
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置：用户ID
	 *
	 * @param userId sys_check_user.user_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 读取：用户名称
	 *
	 * @return sys_check_user.user_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 设置：用户名称
	 *
	 * @param userName sys_check_user.user_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 读取：流程ID
	 *
	 * @return sys_check_user.pro_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getProId() {
		return proId;
	}

	/**
	 * 设置：流程ID
	 *
	 * @param proId sys_check_user.pro_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setProId(Long proId) {
		this.proId = proId;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return sys_check_user.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param createTime sys_check_user.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return sys_check_user.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime sys_check_user.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}