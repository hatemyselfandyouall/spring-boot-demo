package com.wangxinenpu.springbootdemo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.service.facade.SystemManageFacade;
import com.wangxinenpu.springbootdemo.dataobject.vo.SystemIconDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.systemManage.*;
import com.wangxinenpu.springbootdemo.service.service.SystemIconService;
import com.wangxinenpu.springbootdemo.service.service.SystemManageService;
import com.wangxinenpu.springbootdemo.dataobject.po.SystemManage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SystemManageImpl implements SystemManageFacade {

    @Autowired
    SystemManageService systemManageService;
    @Autowired
    private SystemIconService systemIconService;

    @Override
    public PageInfo<SystemManageListVo> selectByMap(SystemManageQueryVo systemManageQueryVo) {
        return systemManageService.selectByMap(systemManageQueryVo);
    }

    @Override
    public int deleteById(String id) {
        return systemManageService.deleteById(id);
    }

    @Override
    public int updateAndInsert(SystemInsertVo systemInsertVo) {
        SystemManage systemManage = new SystemManage();
        BeanUtils.copyProperties(systemInsertVo,systemManage);
        if (StringUtils.isBlank(systemManage.getId())){
            systemManage.setActive("1");
            systemManage.setCreateTime(new Date());
            systemManage.setModifyTime(new Date());
        }else {
            systemManage.setModifyTime(new Date());
        }

        return systemManageService.updateAndInsert(systemManage);
    }

    @Override
    public SystemManageListVo selectById(String id) {
        return systemManageService.selectById(id);
    }

    @Override
    public SystemManageListVo selectBychannelCode(String channelCode) {
        return systemManageService.selectBychannelCode(channelCode);
    }

    @Override
    public JSONArray selectByCode(String areaId) {
        return systemManageService.selectByCode(areaId);
    }

    @Override
    public List<SystemAreaTree> selectByTree(String areaId) {
        return systemManageService.selectByTree(areaId);
    }

    @Override
    public List<SystemManageListVo> getListByOrgId(Long orgId, String channelCode) {
        return systemManageService.getListByOrgId(orgId,channelCode);
    }

    @Override
    public int insert(SystemIconDTO systemIcon) {
        return systemIconService.insert(systemIcon);
    }

    @Override
    public boolean checkName(String name) {
        return systemIconService.checkName(name);
    }

    @Override
    public List<SystemIconDTO> selectAll() {
        return systemIconService.selectAll();
    }
}
