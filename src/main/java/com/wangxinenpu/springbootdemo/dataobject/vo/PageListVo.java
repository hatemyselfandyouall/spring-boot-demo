package com.wangxinenpu.springbootdemo.dataobject.vo;


import io.swagger.annotations.ApiModelProperty;
import star.vo.BaseVo;

import java.util.List;

public class PageListVo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="总数",name="pageTotal")
	private Integer pageTotal = 0;
	@ApiModelProperty(value="列表信息",name="list")
	private List<?> list;



	public Integer getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(Integer pageTotal) {
		this.pageTotal = pageTotal;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	
	
	

}
