package com.wangxinenpu.springbootdemo.dataobject.vo.SiteMatters;

import com.wangxinenpu.springbootdemo.dataobject.po.SiteIconCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SSMSiteIconCategorySaveVO implements Serializable {
    private static final long serialVersionUID = 1L;

    SiteIconCategory siteIconCategory;

    List<SiteIconCategory> siteIconCategoryList;
    List<Integer> categoryIds;


}
