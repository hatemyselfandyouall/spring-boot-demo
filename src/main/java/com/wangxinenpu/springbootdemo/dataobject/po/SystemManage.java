package com.wangxinenpu.springbootdemo.dataobject.po;

import star.vo.BaseVo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 
 * 系统
 * @author haoxz11MyBatis 
 * @created Sat Mar 30 01:44:16 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SystemManage extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段：系统id
	 *
	 * @haoxz11MyBatis
	 */

	@Id
	@GeneratedValue(generator="JDBC")
	@Column( name="id")
	private String id;


	/**
	 * 字段：系统名称
	 *
	 * @haoxz11MyBatis
	 */
	@Column( name="system_Name")
	private String systemName;


	/**
	 * 字段：菜单同步地址
	 *
	 * @haoxz11MyBatis
	 */
	@Column( name="menu_Address")
	private String menuAddress;



	/**
	 * 字段：系统地址
	 *
	 * @haoxz11MyBatis
	 */
	@Column( name="system_Address")
	private String systemAddress;


	/**
	 * 字段：渠道标识码
	 *
	 * @haoxz11MyBatis
	 */
	@Column( name="channel_Code")
	private String channelCode;

	/**
	 * 字段：系统接入方式; 1:统一接入；2同步模式
	 *
	 * @haoxz11MyBatis
	 */
	@Column( name="access_Way")
	private String accessWay;

	/**
	 * 字段:sys_role.area_id
	 *
	 * @haoxz11MyBatis
	 */
	@Column( name="area_Id")
	private String areaId;


	/**
	 * 字段:sys_role.area_id
	 *
	 * @haoxz11MyBatis
	 */
	@Column( name="area_Name")
	private String areaName;


	/**
	 * 字段:sys_role.parent_Arae_Id
	 *
	 * @haoxz11MyBatis
	 */
	@Column( name="parent_Area_Id")
	private String parentAreaId;

	/**
	 * 字段：创建人id
	 *
	 * @haoxz11MyBatis
	 */
	@Column( name="creator_Id")
	private String creatorId;

	/**
	 * 字段：创建日期
	 *
	 * @haoxz11MyBatis
	 */
	@Column( name="create_Time")
	private Date createTime;

	/**
	 * 字段：修改时间
	 *
	 * @haoxz11MyBatis
	 */
	@Column( name="modify_time")
	private Date modifyTime;
	
	/**
	 * 字段：是否有效 1 有效，0 无效
	 *
	 * @haoxz11MyBatis
	 */
	@Column( name="active")
	private String active;

	/**
	 * 字段：是否经办 0 否 ， 1 是
	 *
	 * @haoxz11MyBatis
	 */
	private String isAgency;


	/**
	 * 字段：图标
	 *
	 * @haoxz11MyBatis
	 */
	private String icon;


	public String getIsAgency() {
		return isAgency;
	}

	public void setIsAgency(String isAgency) {
		this.isAgency = isAgency;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getParentAreaId() {
		return parentAreaId;
	}

	public void setParentAreaId(String parentAreaId) {
		this.parentAreaId = parentAreaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSystemAddress() {
		return systemAddress;
	}

	public void setSystemAddress(String systemAddress) {
		this.systemAddress = systemAddress;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getAccessWay() {
		return accessWay;
	}

	public void setAccessWay(String accessWay) {
		this.accessWay = accessWay;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getMenuAddress() {
		return menuAddress;
	}

	public void setMenuAddress(String menuAddress) {
		this.menuAddress = menuAddress;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
}