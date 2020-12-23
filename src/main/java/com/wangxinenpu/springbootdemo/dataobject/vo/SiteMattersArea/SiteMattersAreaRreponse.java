package com.wangxinenpu.springbootdemo.dataobject.vo.SiteMattersArea;


import com.wangxinenpu.springbootdemo.dataobject.po.SiteBlock;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteMatters;
import lombok.Data;

import java.io.Serializable;

@Data
public class SiteMattersAreaRreponse implements Serializable {
    private SiteBlock pcBlock;

    private SiteBlock appBlock;

    private Long SittersAreaId;

    private SiteMatters matters;

    private Long areaId;

    private String areaName;
}
