package com.wangxinenpu.springbootdemo.service.impl;

import com.wangxinenpu.springbootdemo.service.facade.SysOrgSystemFacade;
import com.wangxinenpu.springbootdemo.dataobject.vo.orgSystem.SysOrgSystemDto;
import com.wangxinenpu.springbootdemo.service.service.SysOrgSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SysOrgSystemImpl implements SysOrgSystemFacade {
    @Autowired
    SysOrgSystemService sysOrgSystemService;

    @Override
    public int insertOrgSystem(SysOrgSystemDto record) {
        return sysOrgSystemService.insertOrgSystem(record);
    }

    @Override
    public void deleteByOrgId(Long orgId) {
       sysOrgSystemService.deleteByOrgId(orgId);
    }

    /**
     * 根据机构ID查询
     * @param orgId
     * @return
     */
    @Override
    public List<SysOrgSystemDto> findByOrgId(Long orgId){
        return sysOrgSystemService.findByOrgId(orgId);
    }

}
