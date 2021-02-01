package com.wangxinenpu.springbootdemo.dataobject.vo;

import io.swagger.annotations.ApiModelProperty;
import star.vo.BaseVo;

import java.util.Date;

/**
 * 
 * 系统角色
 * @author haoxz11MyBatis 
 * @created Mon Mar 25 14:59:55 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysRoleDTO extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段：角色id
	 *
	 * @haoxz11MyBatis
	 */
	private String id;

	/**
	 * 字段：角色名称
	 *
	 * @haoxz11MyBatis
	 */
	private String roleName;

	/**
	 * 字段：角色描述
	 *
	 * @haoxz11MyBatis
	 */
	private String roleDesc;

	/**
	 * 字段：角色类型 1:超级管理员; 2行政区管理员，3机构管理员，4业务操作员
	 *
	 * @haoxz11MyBatis
	 */
	private String roleType;

	/**
	 * 字段：机构id(系统生成,唯一关键字)
	 *
	 * @haoxz11MyBatis
	 */
	private Long orgId;

	/**
	 * 字段:sys_role.area_id
	 *
	 * @haoxz11MyBatis
	 */
	private Long areaId;

	/**
	 * 字段：创建人id
	 *
	 * @haoxz11MyBatis
	 */
	private String creatorId;

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
	 * 字段：是否有效
	 *
	 * @haoxz11MyBatis
	 */
	private String active;
	
	/**
	 * 组织机构
	 */
	private String idpath;
	
	//机构名称
	private String orgName;
	
	/**
	 * 职位 1：经办人 2：科长 3：主任
	 */
	private String position;


	/**
	 * 管理系统菜单
	 */
	private String managementSystem;


	/**
	 * 经办系统
	 */
	private String handlingSystem;

	/**
	 * 工伤系统
	 */
	private String injurySystem;

	/**
	 * 网报系统
	 */
	private String netWorkSystem;

	/**
	 * 城乡系统
	 */
	private String urbanRuralSystem;

	/**
	 * 城乡系统
	 */
	private String title;

	private String funType;
	
	/**
	 * 用户ID
	 */
	private Long userId;
	
	

	/**
	 * 读取：角色id
	 *
	 * @return sys_role.id
	 *
	 * @haoxz11MyBatis
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置：角色id
	 *
	 * @param id sys_role.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 读取：角色名称
	 *
	 * @return sys_role.role_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * 设置：角色名称
	 *
	 * @param roleName sys_role.role_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * 读取：角色描述
	 *
	 * @return sys_role.role_desc
	 *
	 * @haoxz11MyBatis
	 */
	public String getRoleDesc() {
		return roleDesc;
	}

	/**
	 * 设置：角色描述
	 *
	 * @param roleDesc sys_role.role_desc
	 *
	 * @haoxz11MyBatis
	 */
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	/**
	 * 读取：角色类型 1:系统管理员; 2:业务操作员；3安全管理员。可扩展其它类型
	 *
	 * @return sys_role.role_type
	 *
	 * @haoxz11MyBatis
	 */
	public String getRoleType() {
		return roleType;
	}

	/**
	 * 设置：角色类型 1:系统管理员; 2:业务操作员；3安全管理员。可扩展其它类型
	 *
	 * @param roleType sys_role.role_type
	 *
	 * @haoxz11MyBatis
	 */
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	/**
	 * 读取：机构id(系统生成,唯一关键字)
	 *
	 * @return sys_role.org_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * 设置：机构id(系统生成,唯一关键字)
	 *
	 * @param orgId sys_role.org_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * 读取：sys_role.area_id
	 *
	 * @return sys_role.area_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getAreaId() {
		return areaId;
	}

	/**
	 * 设置：sys_role.area_id
	 *
	 * @param areaId sys_role.area_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	/**
	 * 读取：创建人id
	 *
	 * @return sys_role.creator_id
	 *
	 * @haoxz11MyBatis
	 */
	public String getCreatorId() {
		return creatorId;
	}

	/**
	 * 设置：创建人id
	 *
	 * @param creatorId sys_role.creator_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	/**
	 * 读取：创建日期
	 *
	 * @return sys_role.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建日期
	 *
	 * @param createTime sys_role.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return sys_role.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}


	@ApiModelProperty("天正一体化角色id")
	private String roleCode ;

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime sys_role.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getIdpath() {
		return idpath;
	}

	public void setIdpath(String idpath) {
		this.idpath = idpath;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getManagementSystem() {
		return managementSystem;
	}

	public void setManagementSystem(String managementSystem) {
		this.managementSystem = managementSystem;
	}

	public String getHandlingSystem() {
		return handlingSystem;
	}

	public void setHandlingSystem(String handlingSystem) {
		this.handlingSystem = handlingSystem;
	}

	public String getInjurySystem() {
		return injurySystem;
	}

	public void setInjurySystem(String injurySystem) {
		this.injurySystem = injurySystem;
	}

	public String getNetWorkSystem() {
		return netWorkSystem;
	}

	public void setNetWorkSystem(String netWorkSystem) {
		this.netWorkSystem = netWorkSystem;
	}

	public String getUrbanRuralSystem() {
		return urbanRuralSystem;
	}

	public void setUrbanRuralSystem(String urbanRuralSystem) {
		this.urbanRuralSystem = urbanRuralSystem;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFunType() {
		return funType;
	}

	public void setFunType(String funType) {
		this.funType = funType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}