 
package com.wangxinenpu.springbootdemo.dao.mapper.selfmachine;


import com.wangxinenpu.springbootdemo.dataobject.po.SiteArea;
import com.wangxinenpu.springbootdemo.dataobject.vo.SiteArea.SiteAreaTreeVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SiteArea.SiteAreaTreeVORequest;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface SiteAreaMapper extends Mapper<SiteArea>{


    List<SiteAreaTreeVO> getSiteAreaTree(@Param("bussType") String bussType);

    List<SiteAreaTreeVORequest> getSiteAreaTreeParentId(@Param("bussType") String bussType);

    List<SiteAreaTreeVORequest> selectNodeParentId(@Param("id") Long id);
}
