package com.wangxinenpu.springbootdemo.service.facade;


import com.wangxinenpu.springbootdemo.dataobject.vo.orgSystem.SysOrgSystemDto;

import java.util.List;

public interface SysOrgSystemFacade {

    int insertOrgSystem(SysOrgSystemDto record);

    void deleteByOrgId(Long orgId);

    /**
     * 根据机构ID查询
     * @param orgId
     * @return
     */
   List<SysOrgSystemDto> findByOrgId(Long orgId);

}
