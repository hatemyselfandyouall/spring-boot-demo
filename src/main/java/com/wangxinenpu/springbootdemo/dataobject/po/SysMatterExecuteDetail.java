package com.wangxinenpu.springbootdemo.dataobject.po;

import star.vo.BaseVo;

import java.util.Date;

/**
 * 
 * 事项执行详细表
 * 
 * @author haoxz11MyBatis
 * @created Tue May 28 01:26:21 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11
 *          Exp $
 * @haoxz11MyBatis
 */
public class SysMatterExecuteDetail extends BaseVo {
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
	 * 字段：配置表ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long configId;

	/**
	 * 字段：事项ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long matterId;

	/**
	 * 字段：sid
	 *
	 * @haoxz11MyBatis
	 */
	private String sid;

	/**
	 * 字段：事项名称
	 *
	 * @haoxz11MyBatis
	 */
	private String matterName;

	/**
	 * 字段：事项编码
	 *
	 * @haoxz11MyBatis
	 */
	private String manageCode;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getConfigId() {
		return configId;
	}

	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	public Long getMatterId() {
		return matterId;
	}

	public void setMatterId(Long matterId) {
		this.matterId = matterId;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getMatterName() {
		return matterName;
	}

	public void setMatterName(String matterName) {
		this.matterName = matterName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getManageCode() {
		return manageCode;
	}

	public void setManageCode(String manageCode) {
		this.manageCode = manageCode;
	}

}