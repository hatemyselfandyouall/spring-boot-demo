package com.wangxinenpu.springbootdemo.dataobject.vo.SiteMattersArea;

import lombok.Data;

import java.io.Serializable;

@Data
public class SiteAreaListResponse implements Serializable {

    private Long areaId;

    private String areaName;

    private String status;

    private Long mattersAreaId;
}
