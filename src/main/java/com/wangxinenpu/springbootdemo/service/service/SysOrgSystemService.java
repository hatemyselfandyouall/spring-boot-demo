package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.orgSystem.SysOrgSystemDto;
import com.wangxinenpu.springbootdemo.dataobject.vo.systemManage.SystemManageListVo;
import com.wangxinenpu.springbootdemo.dao.mapper.SysOrgSystemMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysOrgSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import star.vo.BaseVo;

import java.util.List;

@Service
public class SysOrgSystemService {

    private static Logger logger = LoggerFactory.getLogger(SysOrgSystemService.class);
    @Autowired
    SysOrgSystemMapper sysOrgSystemMapper;


    public int insertOrgSystem(SysOrgSystemDto record) {
        if(record == null) {
            logger.info("SysOrginstypeService.addSysOrginstype dto={}",record);
            return 0;
        }
        return sysOrgSystemMapper.insertOrgSystem(record.copyTo(SysOrgSystem.class));
    }

    public void deleteByOrgId(Long orgId) {
        sysOrgSystemMapper.deleteByOrgId(orgId);
    }

    /**
     * 根据机构ID查询
     * @param orgId
     * @return
     */
    public List<SysOrgSystemDto> findByOrgId(Long orgId){
        List<SysOrgSystem> byOrgId = sysOrgSystemMapper.findByOrgId(orgId);
        return BaseVo.copyListTo(byOrgId, SysOrgSystemDto.class);
    }

    /**
     * 根据机构ID查询
     * @param orgId
     * @return
     */
    public List<SystemManageListVo> findListVoByOrgId(Long orgId){
        List<SystemManageListVo> listVoByOrgId = sysOrgSystemMapper.findListVoByOrgId(orgId);
        return listVoByOrgId;
    }
}
