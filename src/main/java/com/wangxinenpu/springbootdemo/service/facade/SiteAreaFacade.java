 
package com.wangxinenpu.springbootdemo.service.facade;

import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteArea;
import com.wangxinenpu.springbootdemo.dataobject.vo.SiteArea.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.TreeVO;


public interface SiteAreaFacade {

	PageInfo<SiteArea> getSiteAreaList(SiteAreaListVO listVO);

    SiteArea getSiteAreaDetail(SiteAreaDetailVO detailVO);

    Integer saveSiteArea(SiteAreaSaveVO saveVO);

    Integer deleteSiteArea(SiteAreaDeleteVO deleteVO);

    PageInfo<TreeVO> getSiteAreaTree(SiteAreaListVO siteAreaListVO);

    PageInfo<SiteAreaTreeVORequest> getSiteAreaTreeParentId(SiteAreaListVO siteAreaListVO);
}

 
