package com.wangxinenpu.springbootdemo.dataobject.vo;

import star.vo.BaseVo;

/**
 *
 *@Author:xhy
 *@since：2019年3月18日 下午7:42:45
 *@return:
 */
public class ManagerVo extends BaseVo {
	private static final long serialVersionUID = 1L;
	/**
	 * 字段：用户ID
	 */
	private Long id;
	private String name;
	private String passwd;
	private Long areaId;
	private Long orgId;
	private String systemId;
	private String orgCode;
	private String orgenterCode;

	public Long getAreaId() {
		return areaId;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgenterCode() {
		return orgenterCode;
	}

	public void setOrgenterCode(String orgenterCode) {
		this.orgenterCode = orgenterCode;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * 真实姓名
	 */
	private String realname;



	public ManagerVo() {
		super();
	}
	public ManagerVo(Long id, String name, String realname, Long areaId, Long orgId, String systemId, String orgCode, String orgenterCode) {
		super();
		this.id = id;
		this.name = name;
		this.realname = realname;
		this.areaId = areaId;
		this.orgId = orgId;
		this.systemId = systemId;
		this.orgCode = orgCode;
		this.orgenterCode = orgenterCode;
	}

	public ManagerVo(Long id, String name, String passwd, String realname) {
		super();
		this.id = id;
		this.name = name;
		this.passwd = passwd;
		this.realname = realname;
	}

	public ManagerVo(Long id, String name, String realname) {
		super();
		this.id = id;
		this.name = name;
		this.realname = realname;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}




}
