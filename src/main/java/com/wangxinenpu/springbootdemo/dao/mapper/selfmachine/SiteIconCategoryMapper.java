 
package com.wangxinenpu.springbootdemo.dao.mapper.selfmachine;

import com.wangxinenpu.springbootdemo.dataobject.po.SiteIconCategory;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface SiteIconCategoryMapper extends Mapper<SiteIconCategory>{
    List<SiteIconCategory> getSiteIconCategoryList(@Param("bussType") Integer bussType, @Param("name") String name);
}
