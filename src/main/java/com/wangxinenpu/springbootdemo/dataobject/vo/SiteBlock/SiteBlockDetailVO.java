package com.wangxinenpu.springbootdemo.dataobject.vo.SiteBlock;


import com.wangxinenpu.springbootdemo.dataobject.po.SiteBlock;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteCornerMarker;
import lombok.Data;

import java.io.Serializable;

@Data
public class SiteBlockDetailVO implements Serializable {

    private static final long serialVersionUID = 1L;

    SiteBlock siteBlock;

    SiteCornerMarker upperSiteCornerMarker;

    SiteCornerMarker lowSiteCornerMarker;

}
