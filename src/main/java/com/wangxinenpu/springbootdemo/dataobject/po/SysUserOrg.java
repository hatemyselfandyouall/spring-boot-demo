package com.wangxinenpu.springbootdemo.dataobject.po;

import com.wangxinenpu.springbootdemo.dataobject.vo.root.LogBaseVo;

import java.util.Date;


/**
 * 
 * 用户机构关系表
 * @author haoxz11MyBatis 
 * @created Sat Mar 30 01:44:16 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysUserOrg extends LogBaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;
	
	private Long id;

	/**
	 * 字段：区划id
	 *
	 * @haoxz11MyBatis
	 */
	private Long areaId;

	/**
	 * 字段：用户id
	 *
	 * @haoxz11MyBatis
	 */
	private Long userId;
	
	/**
	 * 机构ID
	 */
	private Long orgId;

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * 多个科室ID
	 */
	private String departmentId;

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
	 * 读取：区划id
	 *
	 *
	 * @haoxz11MyBatis
	 */
	public Long getAreaId() {
		return areaId;
	}

	/**
	 * 设置：区划id
	 *
	 *
	 * @haoxz11MyBatis
	 */
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	/**
	 * 读取：用户id
	 *
	 *
	 * @haoxz11MyBatis
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置：用户id
	 *
	 * @param userId sys_user_area.user_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 读取：创建日期
	 *
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建日期
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
}