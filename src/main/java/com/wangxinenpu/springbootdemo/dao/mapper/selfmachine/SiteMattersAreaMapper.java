 
package com.wangxinenpu.springbootdemo.dao.mapper.selfmachine;


import com.wangxinenpu.springbootdemo.dataobject.dto.sitematters.MattersDto;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteMattersArea;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.List;


public interface SiteMattersAreaMapper extends Mapper<SiteMattersArea>,InsertListMapper<SiteMattersArea>{
    List<MattersDto> getSiteAreaMattersList(@Param("areaId") Long areaId, @Param("accountTypeId") Long accountTypeId, @Param("bussType") Integer bussType, @Param("name") String name, @Param("businessTypeId") Long businessTypeId);

}
