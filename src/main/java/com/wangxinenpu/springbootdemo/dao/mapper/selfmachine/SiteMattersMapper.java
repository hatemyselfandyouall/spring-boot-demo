 
package com.wangxinenpu.springbootdemo.dao.mapper.selfmachine;

import com.wangxinenpu.springbootdemo.dataobject.dto.sitematters.SiteMattersInfoDto;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteMatters;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface SiteMattersMapper extends Mapper<SiteMatters>{
    void updateSiteMattersCategoryIdNull(SiteMatters siteMatters);
    void updateSiteMattersCategoryId(SiteMatters siteMatters);
    List<SiteMatters> getSiteMattersByCategoryIdNull(Integer bussType);

    List<SiteMatters> SelectByLike(@Param("name") String name);


    List<SiteMatters> getSiteMattersList(@Param("bussType") Integer bussType, @Param("openStatus") Integer openStatus);

    List<SiteMatters> getSSMSiteMattersList(@Param("areaId") Long areaId);

    Integer checkSSMSiteMatters(@Param("name") String name);

    List<SiteMattersInfoDto> getSiteIconCategoryAndMatters(@Param("areaId") Long areaId, @Param("machineNumber") String machineNumber, @Param("isTakesEffect") Integer isTakesEffect);

}
