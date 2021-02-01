package com.wangxinenpu.springbootdemo.dataobject.vo;

import star.vo.BaseVo;

import java.util.Date;

/**
 * 
 * 单点登录用户授权
 * @author haoxz11MyBatis 
 * @created Tue May 28 01:26:21 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysUserEmpowerDTO extends BaseVo {
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
	 * 字段：一体化平台用户ID
	 *
	 * @haoxz11MyBatis
	 */
	private String ythUserId;

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
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：主键ID
	 *
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：用户ID
	 *
	 *
	 * @haoxz11MyBatis
	 */
	public String getuserId() {
		return userId;
	}

	/**
	 * 设置：用户ID
	 *
	 *
	 * @haoxz11MyBatis
	 */
	public void setuserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 读取：一体化平台用户ID
	 *
	 *
	 * @haoxz11MyBatis
	 */
	public String getythUserId() {
		return ythUserId;
	}

	/**
	 * 设置：一体化平台用户ID
	 *
	 *
	 * @haoxz11MyBatis
	 */
	public void setythUserId(String ythUserId) {
		this.ythUserId = ythUserId;
	}

	/**
	 * 读取：创建时间
	 *
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}