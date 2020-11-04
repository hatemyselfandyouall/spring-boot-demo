package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.vo.systemManage.SystemManageListVo;
import com.wangxinenpu.springbootdemo.dataobject.po.SystemManage;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SystemManageMapper extends Mapper<SystemManage> {

    List<SystemManageListVo> selectByOrgId(@Param("orgId") Long orgId, @Param("channelCode") String channelCode);

    List<SystemManageListVo> selectByTree(@Param("areaId") Long areaId);
    List<SystemManage> selectByCode(@Param("areaId") Long areaId);
}
