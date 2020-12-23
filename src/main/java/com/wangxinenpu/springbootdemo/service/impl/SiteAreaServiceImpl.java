package com.wangxinenpu.springbootdemo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.wangxinenpu.springbootdemo.dao.mapper.selfmachine.SiteAccountTypeMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.selfmachine.SiteAreaMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteAccountType;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteArea;
import com.wangxinenpu.springbootdemo.dataobject.vo.SiteArea.*;
import com.wangxinenpu.springbootdemo.service.facade.SiteAreaFacade;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteArea;
import com.wangxinenpu.springbootdemo.dataobject.vo.DataListResultDto;
import com.wangxinenpu.springbootdemo.dataobject.vo.SiteArea.SiteAreaListVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.TreeVO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SiteAreaServiceImpl implements SiteAreaFacade {

    @Autowired
    SiteAreaMapper siteAreaMapper;
    @Autowired
    SiteAccountTypeMapper siteAccountTypeMapper;

    @Override
    public PageInfo<SiteArea> getSiteAreaList(SiteAreaListVO siteAreaListVO) {
        if (siteAreaListVO==null||siteAreaListVO.getPageNum()==null||siteAreaListVO.getPageSize()==null|| StringUtil.isEmpty(siteAreaListVO.getBussType())) {
            return null;
        }
        PageHelper.startPage(siteAreaListVO.getPageNum().intValue(),siteAreaListVO.getPageSize().intValue());
        SiteArea exampleObeject=new SiteArea();
        exampleObeject.setBussType(siteAreaListVO.getBussType());
        List<SiteArea> siteAreaList=siteAreaMapper.select(exampleObeject);
        PageInfo<SiteArea> siteAreaPageInfo=new PageInfo<>(siteAreaList);
        return siteAreaPageInfo;
    }

    @Override
    public SiteArea getSiteAreaDetail(SiteAreaDetailVO siteAreaDetailVO) {
        if (siteAreaDetailVO==null||siteAreaDetailVO.getId()==null) {
            return null;
        };
        SiteArea siteArea=siteAreaMapper.selectByPrimaryKey(siteAreaDetailVO.getId());
        return siteArea;
    }

    @Override
    public Integer saveSiteArea(SiteAreaSaveVO siteAreaSaveVO) {
        if (siteAreaSaveVO==null){
            return 0;
        }
        SiteArea siteArea= new SiteArea();
        BeanUtils.copyProperties(siteArea,siteAreaSaveVO);
        if (siteArea.getId()==null){
            return siteAreaMapper.insertSelective(siteArea);
        }else {
            Example example=new Example(SiteArea.class);
            example.createCriteria().andEqualTo("id",siteArea.getId());
            return siteAreaMapper.updateByExampleSelective(siteArea,example);
        }
    }

    @Override
    public Integer deleteSiteArea(SiteAreaDeleteVO siteAreaDeleteVO) {
        if (siteAreaDeleteVO==null||siteAreaDeleteVO.getId()==null){
            return 0;
        }
        SiteArea siteArea=new SiteArea();
        Example example=new Example(SiteArea.class);
        example.createCriteria().andEqualTo("id",siteAreaDeleteVO.getId());
        return siteAreaMapper.updateByExampleSelective(siteArea,example);
    }

    @Override
    public PageInfo<TreeVO> getSiteAreaTree(SiteAreaListVO siteAreaListVO) {
        if (siteAreaListVO==null||siteAreaListVO.getPageNum()==null||siteAreaListVO.getPageSize()==null|| StringUtil.isEmpty(siteAreaListVO.getBussType())) {
            return null;
        }
//        PageHelper.startPage(siteAreaListVO.getPageNum().intValue(),siteAreaListVO.getPageSize().intValue());
        List<TreeVO> accountTypes=getAccountTypesTree();
        List<SiteAreaTreeVO> siteAreaList=siteAreaMapper.getSiteAreaTree(siteAreaListVO.getBussType());
        List<TreeVO> tree=treeConvert(siteAreaList,accountTypes);
        PageInfo<TreeVO> siteAreaPageInfo=new PageInfo<>(tree);
        return siteAreaPageInfo;
    }

    private List<TreeVO> treeConvert(List<SiteAreaTreeVO> siteAreaList, List<TreeVO> accountTypes) {
        List<TreeVO> treeVOS=new ArrayList<>();
        for (SiteAreaTreeVO siteAreaTreeVO:siteAreaList){
            TreeVO treeVO=iteraToTree(siteAreaTreeVO,accountTypes);
            treeVOS.add(treeVO);
        }
        return treeVOS;
    }

    private TreeVO iteraToTree(SiteAreaTreeVO siteAreaTreeVO, List<TreeVO> accountTypes) {
        TreeVO child;
        child=new TreeVO();
        child.setId(siteAreaTreeVO.getId() + "");
        child.setLabel(siteAreaTreeVO.getName());
        child.setPid(siteAreaTreeVO.getId() + "");
        if (org.springframework.util.CollectionUtils.isEmpty(siteAreaTreeVO.getChildren())){
            child.setChildren(accountTypes);
        }else {
            child.setChildren(treeConvert(siteAreaTreeVO.getChildren(),accountTypes));
        }
        return child;
    }

    private List<TreeVO> getAccountTypesTree() {
       List<SiteAccountType> siteAccountTypes=siteAccountTypeMapper.selectAll();
        List<TreeVO> treeVOS=siteAccountTypes.stream().map(i->{
            TreeVO treeVO=new TreeVO();
            treeVO.setId(i.getId()+"");
            treeVO.setLabel(i.getName());
            return treeVO;
        }).collect(Collectors.toList());
        return treeVOS;
    }




    public PageInfo<SiteAreaTreeVORequest> getSiteAreaTreeParentId(SiteAreaListVO siteAreaListVO) {
        if (siteAreaListVO==null||siteAreaListVO.getPageNum()==null||siteAreaListVO.getPageSize()==null|| StringUtil.isEmpty(siteAreaListVO.getBussType())) {
            return null;
        }
//        PageHelper.startPage(siteAreaListVO.getPageNum().intValue(),siteAreaListVO.getPageSize().intValue());
        List<TreeVO> accountTypes=getAccountTypesTree();
        List<SiteAreaTreeVORequest> siteAreaList=siteAreaMapper.getSiteAreaTreeParentId(siteAreaListVO.getBussType());
        for (SiteAreaTreeVORequest list: siteAreaList
             ) {
            if (list.getParentId().intValue()==100000){
                List<SiteAreaTreeVORequest> childrenList = siteAreaMapper.selectNodeParentId(list.getValue());
                siteAreaList = getTree(childrenList);
            }
        }
        PageInfo<SiteAreaTreeVORequest> siteAreaPageInfo=new PageInfo<>(siteAreaList);
        return siteAreaPageInfo;
    }


    public List<SiteAreaTreeVORequest> getTree(List<SiteAreaTreeVORequest> siteAreaListVO){

        if (siteAreaListVO.size()!=0){
            for (SiteAreaTreeVORequest s
             : siteAreaListVO) {
                List<SiteAreaTreeVORequest> childList = siteAreaMapper.selectNodeParentId(s.getValue());
                s.setChildren(childList);
                getTree(childList);
            }
        }
        return  siteAreaListVO;
    }
}
