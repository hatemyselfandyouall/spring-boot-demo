package com.wangxinenpu.springbootdemo.service.service;


import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.dataobject.vo.BaseCodeEnum;
import com.wangxinenpu.springbootdemo.dataobject.vo.systemManage.SystemAreaTree;
import com.wangxinenpu.springbootdemo.dataobject.vo.systemManage.SystemManageListVo;
import com.wangxinenpu.springbootdemo.dataobject.vo.systemManage.SystemManageQueryVo;
import com.wangxinenpu.springbootdemo.dao.mapper.SystemManageMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SystemManage;
import com.wangxinenpu.springbootdemo.util.BusException;
import com.wangxinenpu.springbootdemo.util.TreeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import star.vo.BaseVo;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SystemManageService {

    @Autowired
    SystemManageMapper systemManageMapper;


    public PageInfo<SystemManageListVo> selectByMap(SystemManageQueryVo systemManageQueryVo) {
        if (systemManageQueryVo==null || systemManageQueryVo.getPageNum()==null || systemManageQueryVo.getPageSize()==null){
                    throw  new BusException(BaseCodeEnum.ERROR,BaseCodeEnum.PAGE_ERROR.getMsg());
        }
        PageHelper.startPage(systemManageQueryVo.getPageNum().intValue(),systemManageQueryVo.getPageSize().intValue());
        Example example = new Example(SystemManage.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(systemManageQueryVo.getSystemName())){
            criteria.andLike("systemName","%"+systemManageQueryVo.getSystemName()+"%");
        }
        if (StringUtils.isNotBlank(systemManageQueryVo.getAccessWay())){
            criteria.andEqualTo("accessWay",systemManageQueryVo.getAccessWay());
        }
        if (systemManageQueryVo.getAreaId()!=null){
            criteria.andEqualTo("areaId",systemManageQueryVo.getAreaId());
        }
        if (StringUtils.isNotBlank(systemManageQueryVo.getIsAgency())){
            criteria.andEqualTo("isAgency",systemManageQueryVo.getIsAgency());
        }
        criteria.andNotEqualTo("active","0");
        if(systemManageQueryVo.getIsShowManagementSystem()==0) {
            criteria.andNotEqualTo("id", "1");
        }
        List<SystemManage> systemManages = systemManageMapper.selectByExample(example);
        List<SystemManageListVo> systemManageListVos = BaseVo.copyListTo(systemManages,SystemManageListVo.class);
        PageInfo pageInfo = new PageInfo(systemManageListVos);
        return pageInfo;
    }

    public int deleteById(String id) {
        SystemManage systemManage = new SystemManage();
        systemManage.setId(id);
        systemManage.setActive("0");
        return systemManageMapper.updateByPrimaryKeySelective(systemManage);
    }


    public int updateAndInsert(SystemManage systemInsertVo) {
        SystemManage checkName = new SystemManage();
        Example example = new Example(SystemManage.class);
        if (StringUtils.isNotBlank(systemInsertVo.getId())){
            example.createCriteria().
                    andEqualTo("systemName",systemInsertVo.getSystemName()).
                    andNotEqualTo("id",systemInsertVo.getId());
            List<SystemManage> systemManages = systemManageMapper.selectByExample(example);
            if (systemManages.size()!=0)
                    return -2;
            return systemManageMapper.updateByPrimaryKeySelective(systemInsertVo);
        }else {
            example.createCriteria().
                    andEqualTo("systemName",systemInsertVo.getSystemName());
            List<SystemManage> systemManages = systemManageMapper.selectByExample(example);
            if (systemManages.size()!=0)
                return -2;
            systemInsertVo.setActive("1");
            systemInsertVo.setChannelCode(systemInsertVo.getChannelCode());
            return systemManageMapper.insertSelective(systemInsertVo);
        }
    }


    public JSONArray selectByCode(String areaId) {
        if (StringUtils.isNotBlank(areaId) && areaId.endsWith("00")){
            areaId = areaId.substring(0,4);
        }
        List<SystemManage> systemManages = systemManageMapper.selectByCode(Long.parseLong(areaId));
        JSONArray jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(systemManages)), "areaId",
                "parentAreaId", "children");
       return jsonArray;
    }

    public List<SystemAreaTree> selectByTree(String areaId) {
        if (StringUtils.isNotBlank(areaId) && areaId.endsWith("00")){
            areaId = areaId.substring(0,4);
        }
        List<SystemManageListVo> systemManageListVos = systemManageMapper.selectByTree(Long.parseLong(areaId));
        JSONArray jsonArray = TreeUtil.listToTree2(JSONArray.parseArray(JSONArray.toJSONString(systemManageListVos)), "areaId",
                "parentId", "children");
        JSONArray returnList = TreeUtil.listToTree(jsonArray,"areaId","parentId","children");
        List<SystemAreaTree> systemAreaTrees = JSONArray.parseArray(JSONArray.toJSONString(returnList), SystemAreaTree.class);
        return systemAreaTrees;
    }

    public List<SystemManageListVo> getListByOrgId(Long orgId, String channelCode) {

        List<SystemManageListVo> systemManageListVos = systemManageMapper.selectByOrgId(orgId,channelCode);
        return systemManageListVos;
    }

    public SystemManageListVo selectById(String id) {
        SystemManage systemManage = new SystemManage();
        systemManage.setId(id);
        return  systemManageMapper.selectByPrimaryKey(systemManage).copyTo(SystemManageListVo.class);
    }

    public SystemManageListVo selectBychannelCode(String id) {
        SystemManageListVo result = new SystemManageListVo();
        SystemManage systemManage = new SystemManage();
        systemManage.setChannelCode(id);
          Example example = new Example(SystemManage.class);
          Example.Criteria criteria = example.createCriteria();
          criteria.andEqualTo("channelCode",id);
        List<SystemManage> systemManages = systemManageMapper.selectByExample(example);
        if (systemManages.size()!=0){
            result = systemManages.get(0).copyTo(SystemManageListVo.class);
        }
        return result;
    }
}
