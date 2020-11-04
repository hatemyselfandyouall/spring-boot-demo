package com.wangxinenpu.springbootdemo.dataobject.vo;

import star.vo.BaseVo;

public class OrgIdPathVo extends BaseVo {

	private static final long serialVersionUID = -950768691117508783L;

	public String getIdPath() {
		return idPath;
	}

	public void setIdPath(String idPath) {
		this.idPath = idPath;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * 字段：父机构id
	 *
	 * @haoxz11MyBatis

	 */
	private Long parentId;
	
	//机构层级
	private String idPath;


}
