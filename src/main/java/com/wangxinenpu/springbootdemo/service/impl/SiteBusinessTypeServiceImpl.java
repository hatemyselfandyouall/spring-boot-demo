package com.wangxinenpu.springbootdemo.service.impl;


import com.wangxinenpu.springbootdemo.dao.mapper.selfmachine.SiteBusinessTypeMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteBusinessType;
import com.wangxinenpu.springbootdemo.service.facade.SiteBusinessTypeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SiteBusinessTypeServiceImpl implements SiteBusinessTypeFacade {

    @Autowired
    private SiteBusinessTypeMapper siteBusinessTypeMapper;

    @Override
    public List<SiteBusinessType> getSiteBusinessTypeList(Integer type) {
        if(type!=null) {
            Example example = new Example(SiteBusinessType.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("type", type);
            return siteBusinessTypeMapper.selectByExample(example);
        }else{
            return siteBusinessTypeMapper.selectAll();
        }
    }
}
