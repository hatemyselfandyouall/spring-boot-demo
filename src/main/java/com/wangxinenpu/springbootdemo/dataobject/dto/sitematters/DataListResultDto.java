package com.wangxinenpu.springbootdemo.dataobject.dto.sitematters;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;
import java.util.List;


public class DataListResultDto<T> implements Serializable {

    private static final long serialVersionUID = -172905342446394384L;

    public DataListResultDto() {

    }
    public DataListResultDto(List<T> dataList, int totalCount) {
        this.totalCount = totalCount;
        this.dataList=dataList;
    }

    private int totalCount;//总行数

    private List<T> dataList;
    
    private JSONArray dataJson;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
	public JSONArray getDataJson() {
		return dataJson;
	}
	public void setDataJson(JSONArray dataJson) {
		this.dataJson = dataJson;
	}
}
