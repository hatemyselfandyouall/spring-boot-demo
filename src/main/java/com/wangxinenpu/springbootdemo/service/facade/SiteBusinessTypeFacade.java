 
package com.wangxinenpu.springbootdemo.service.facade;


import com.wangxinenpu.springbootdemo.dataobject.po.SiteBusinessType;

import java.util.List;

public interface SiteBusinessTypeFacade {

    List<SiteBusinessType> getSiteBusinessTypeList(Integer type);

}

 
