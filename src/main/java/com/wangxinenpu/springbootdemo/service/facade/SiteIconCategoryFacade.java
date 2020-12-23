 
package com.wangxinenpu.springbootdemo.service.facade;


import com.wangxinenpu.springbootdemo.dataobject.dto.sitematters.SiteIconCategoryDto;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteIconCategory;

import java.util.List;


public interface SiteIconCategoryFacade {

    List<SiteIconCategoryDto> getSiteIconCategoryList(Integer bussType);

    List<SiteIconCategoryDto> getSSMSiteIconCategoryList(Integer bussType, String name);

    SiteIconCategory saveSiteIconCategory(SiteIconCategory siteIconCategory);

    Integer deleteSiteIconCategory(Integer id);

    Integer deleteByParentId(Integer parentId);

    Integer ZjybappSaveSiteIconCategory(SiteIconCategory siteIconCategory);

    SiteIconCategory getByPrimaryKey(Integer id);

    List<SiteIconCategory> getByParentId(Integer parentId);

    List<SiteIconCategoryDto> getSSMSiteIconCategoryPageList(Integer bussType, String name, int start, int size);

    List<SiteIconCategory> getListAll();

}

 
