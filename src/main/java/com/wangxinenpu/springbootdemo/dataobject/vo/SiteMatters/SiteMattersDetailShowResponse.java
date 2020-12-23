package com.wangxinenpu.springbootdemo.dataobject.vo.SiteMatters;


import com.wangxinenpu.springbootdemo.dataobject.po.SiteArea;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteBlock;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteMatters;
import lombok.Data;

import java.io.Serializable;

@Data
public class SiteMattersDetailShowResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    SiteMatters siteMatters;

    SiteArea personAreas;

    SiteBlock pcBlock;

    SiteBlock appBlock;
}
