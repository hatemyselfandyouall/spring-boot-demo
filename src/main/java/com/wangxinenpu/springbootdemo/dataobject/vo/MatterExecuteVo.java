package com.wangxinenpu.springbootdemo.dataobject.vo;

import lombok.Data;
import star.vo.BaseVo;

import java.util.List;
import java.util.Map;

@Data
public class MatterExecuteVo extends BaseVo{
	private static final long serialVersionUID = 1L;
	
	String status;
	List<String> areaList;
	List<Map<String, Object>> matterList;
	List<Map<String, Object>> blTimeList;
}
