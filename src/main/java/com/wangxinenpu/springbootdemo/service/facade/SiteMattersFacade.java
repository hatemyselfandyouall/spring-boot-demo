 
package com.wangxinenpu.springbootdemo.service.facade;

import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteMatters;
import com.wangxinenpu.springbootdemo.dataobject.vo.SiteMatters.*;


import java.util.List;

public interface SiteMattersFacade {






    SSMSiteMattersDetailShowVO siteSSMMattersDetail(Long id);

    PageInfo<SiteMatters> getSiteMattersLikeList(Integer pageNum, Integer pageSize, String name);

    Integer checkSSMSiteMatters(String name);



    List<SiteMatters> getSiteMattersByCategoryId(Long iconCategoryId, Integer bussType);

    List<SiteMatters> getSSMSiteMattersByCategoryId(Long iconCategoryId, Integer bussType);



    List<SiteMatters> getSiteMattersByCategoryIdNull(Integer bussType);

    List<SiteMatters> getSSMSiteMattersByCategoryIdNull(Integer bussType);



    List<SiteMatters> getSSMSiteMattersList(Long areaId);

    Integer saveSSMSiteMatters(SSMSiteMattersSaveVO sSMSiteMattersSaveVO);


}

 
