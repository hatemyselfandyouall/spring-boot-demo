package com.wangxinenpu.springbootdemo.dataobject.vo;

import star.vo.BaseVo;

public class SysFunctionYthDTO extends BaseVo {
	transient private static final long serialVersionUID = -1L;
	private Long id;
	
	private String text;
	
	private Long pid;
	
	private String url;
	
	private String bak;
	private Integer sysmanagerView;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBak() {
		return bak;
	}

	public void setBak(String bak) {
		this.bak = bak;
	}


	public Integer getSysmanagerView() {
		return sysmanagerView;
	}

	public void setSysmanagerView(Integer sysmanagerView) {
		this.sysmanagerView = sysmanagerView;
	}
}
