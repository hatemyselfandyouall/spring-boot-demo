package com.wangxinenpu.springbootdemo.dataobject.vo;

import star.vo.BaseVo;

import java.util.List;

public class OrgVo extends BaseVo {

	private static final long serialVersionUID = 1L;
	
	private SysOrgDTO sysOrgDTO;
	
	private List<String> insList;

	private List<String> systemList;

	public List<String> getSystemList() {
		return systemList;
	}

	public void setSystemList(List<String> systemList) {
		this.systemList = systemList;
	}

	public SysOrgDTO getSysOrgDTO() {
		return sysOrgDTO;
	}

	public void setSysOrgDTO(SysOrgDTO sysOrgDTO) {
		this.sysOrgDTO = sysOrgDTO;
	}

	public List<String> getInsList() {
		return insList;
	}

	public void setInsList(List<String> insList) {
		this.insList = insList;
	}
	
	

}
