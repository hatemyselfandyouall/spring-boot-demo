package com.wangxinenpu.springbootdemo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.taobao.diamond.extend.DynamicProperties;
import com.wangxinenpu.springbootdemo.constant.DataConstant;
import com.wangxinenpu.springbootdemo.dao.mapper.selfmachine.SiteBlockMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.selfmachine.SiteMattersAreaMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.selfmachine.SiteMattersMapper;
import com.wangxinenpu.springbootdemo.dataobject.dto.sitematters.SiteMattersDto;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteBlock;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteMatters;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteMattersArea;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteSidMatters;
import com.wangxinenpu.springbootdemo.dataobject.vo.SiteMatters.SSMSiteMattersDetailShowVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SiteMatters.SSMSiteMattersSaveVO;
import com.wangxinenpu.springbootdemo.service.facade.SiteMattersFacade;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import star.util.StringUtil;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

import static star.util.BeanUtils.copyProperties;

@Slf4j
@Service
public class SiteMattersServiceImpl implements SiteMattersFacade {

    @Autowired
    private SiteMattersMapper siteMattersMapper;
    @Autowired
    private SiteMattersAreaMapper siteMattersAreaMapper;

    @Autowired
    private SiteBlockMapper siteBlockMapper;




    private static final String basicUrl = DynamicProperties.staticProperties.getProperty("oss.basicUrl");


    @Override
    public SSMSiteMattersDetailShowVO siteSSMMattersDetail(Long id) {
        SSMSiteMattersDetailShowVO ssmSiteMattersDetailShowVO=new SSMSiteMattersDetailShowVO();
        SiteMatters siteMatters=siteMattersMapper.selectByPrimaryKey(id);
        SiteMattersDto siteMattersDto = new SiteMattersDto();
        BeanUtils.copyProperties(siteMatters,siteMattersDto);
        SiteSidMatters siteSidMatters= null;
        if(siteSidMatters!=null){
            siteMattersDto.setInsideCode(siteSidMatters.getInsideCode());
        }
        SiteMattersArea siteMattersArea=new SiteMattersArea();
        siteMattersArea.setMattersId(siteMatters.getId());
        siteMattersArea.setIsDelete(DataConstant.NO_DELETE);
        List<SiteMattersArea> siteMattersAreas=siteMattersAreaMapper.select(siteMattersArea);
        SiteBlock block=siteBlockMapper.selectByPrimaryKey(siteMatters.getBlockSsmId());
        if (block!=null) {
            block.setBasieUrl(basicUrl);
        }
        ssmSiteMattersDetailShowVO.setSiteBlock(block);
        ssmSiteMattersDetailShowVO.setSiteMattersAreas(siteMattersAreas);
        ssmSiteMattersDetailShowVO.setSiteMattersDto(siteMattersDto);
        return ssmSiteMattersDetailShowVO;
    }

    @Override
    public PageInfo<SiteMatters> getSiteMattersLikeList(Integer pageNum, Integer pageSize, String name) {
        PageHelper.startPage(pageNum,pageSize);
        List<SiteMatters> siteMatters = siteMattersMapper.SelectByLike(name);

        PageInfo<SiteMatters> siteBannerPageInfo=new PageInfo<>(siteMatters);
        return siteBannerPageInfo;
    }




    @Override
    public List<SiteMatters> getSiteMattersByCategoryId(Long iconCategoryId, Integer bussType) {
        Example example=new Example(SiteMatters.class);
        example.createCriteria().andEqualTo("iconCategoryId",iconCategoryId);
        example.createCriteria().andEqualTo("bussType",bussType);
        return siteMattersMapper.selectByExample(example);
    }
    @Override
    public List<SiteMatters> getSSMSiteMattersByCategoryId(Long iconCategoryId, Integer bussType) {
        Example example=new Example(SiteMatters.class);
        Example.Criteria criteria =  example.createCriteria();
        criteria.andEqualTo("iconCategoryId", iconCategoryId);
        if(bussType!=null) {
            criteria.andEqualTo("bussType", bussType);
        }
        criteria.andIsNotNull("blockSsmId");
        return siteMattersMapper.selectByExample(example);
    }


    @Override
    public List<SiteMatters> getSiteMattersByCategoryIdNull(Integer bussType) {
        return siteMattersMapper.getSiteMattersByCategoryIdNull(bussType);
    }

    @Override
    public List<SiteMatters> getSSMSiteMattersByCategoryIdNull(Integer bussType) {
        Example example=new Example(SiteMatters.class);
        Example.Criteria criteria =  example.createCriteria();
        if(bussType!=null) {
            criteria.andEqualTo("bussType", bussType);
        }
        criteria.andIsNull("iconCategoryId");
        criteria.andIsNotNull("blockSsmId");
        return siteMattersMapper.selectByExample(example);
    }



    @Override
    public Integer checkSSMSiteMatters(String name) {
        return siteMattersMapper.checkSSMSiteMatters(name);
    }



    @Override
    public List<SiteMatters> getSSMSiteMattersList(Long areaId) {
        return siteMattersMapper.getSSMSiteMattersList(areaId);
    }

    @Override
    public Integer saveSSMSiteMatters(SSMSiteMattersSaveVO sSMSiteMattersSaveVO) {
        return null;
    }


}