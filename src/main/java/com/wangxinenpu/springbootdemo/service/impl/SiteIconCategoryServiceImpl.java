package com.wangxinenpu.springbootdemo.service.impl;

import com.github.pagehelper.PageHelper;

import com.wangxinenpu.springbootdemo.dao.mapper.selfmachine.SiteIconCategoryMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.selfmachine.SiteMattersMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteMatters;
import com.wangxinenpu.springbootdemo.service.facade.SiteIconCategoryFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.wangxinenpu.springbootdemo.dataobject.dto.sitematters.SiteIconCategoryDto;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteIconCategory;
@Slf4j
@Service
public class SiteIconCategoryServiceImpl implements SiteIconCategoryFacade {

	@Autowired
	private SiteIconCategoryMapper siteIconCategoryMapper;

	@Autowired
	private SiteMattersMapper siteMattersMapper;

	@Override
	public List<SiteIconCategoryDto> getSiteIconCategoryList(Integer bussType) {
		List<SiteIconCategory> siteIconCategoryList = siteIconCategoryMapper.getSiteIconCategoryList(bussType, null);
		List<SiteIconCategoryDto> siteIconCategoryDtos = new ArrayList<>();
		for (SiteIconCategory siteIconCategory : siteIconCategoryList) {
			SiteIconCategoryDto siteIconCategoryDto = new SiteIconCategoryDto();
			BeanUtils.copyProperties(siteIconCategory, siteIconCategoryDto);
			Long siteIconCategoryId = siteIconCategory.getId();
			Example example1 = new Example(SiteMatters.class);
			example1.createCriteria().andEqualTo("iconCategoryId", siteIconCategoryId);
			siteIconCategoryDto.setMattersCount(siteMattersMapper.selectCountByExample(example1));
			siteIconCategoryDtos.add(siteIconCategoryDto);
		}
		return siteIconCategoryDtos;
	}

	@Override
	public List<SiteIconCategoryDto> getSSMSiteIconCategoryList(Integer bussType, String name) {
		List<SiteIconCategory> siteIconCategoryList = siteIconCategoryMapper.getSiteIconCategoryList(bussType, name);
		List<SiteIconCategoryDto> siteIconCategoryDtos = new ArrayList<>();
		for (SiteIconCategory siteIconCategory : siteIconCategoryList) {
			SiteIconCategoryDto siteIconCategoryDto = new SiteIconCategoryDto();
			BeanUtils.copyProperties(siteIconCategory, siteIconCategoryDto);
			Long siteIconCategoryId = siteIconCategory.getId();
			Example example1 = new Example(SiteMatters.class);
			Example.Criteria criteria1 = example1.createCriteria();
			criteria1.andEqualTo("iconCategoryId", siteIconCategoryId);
			criteria1.andIsNotNull("blockSsmId");
			siteIconCategoryDto.setMattersCount(siteMattersMapper.selectCountByExample(example1));
			// 子类数量
			Example example2 = new Example(SiteIconCategory.class);
			Example.Criteria criteria2 = example2.createCriteria();
			criteria2.andEqualTo("parentId", siteIconCategoryId);
			siteIconCategoryDto.setCategoryCount(siteIconCategoryMapper.selectCountByExample(example2));
			siteIconCategoryDtos.add(siteIconCategoryDto);
		}
		return siteIconCategoryDtos;
	}
	
	@Override
	public List<SiteIconCategoryDto> getSSMSiteIconCategoryPageList(Integer bussType, String name, int start, int size) {
		log.info("入参：start={},size={},bussType={},name={}",start,size,bussType,name);
		Example example = new Example(SiteIconCategory.class);
		example.setOrderByClause("sort_no asc");
		example.setOrderByClause("id desc");
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("bussType", bussType);
		if(StringUtils.isNotBlank(name)){
			criteria.andLike("name", "%"+ name +"%");
		}
		criteria.andEqualTo("parentId", "0");
		PageHelper.startPage(start,size);
		List<SiteIconCategory> siteIconCategoryList = siteIconCategoryMapper.selectByExample(example);
//		List<SiteIconCategory> siteIconCategoryList = siteIconCategoryMapper.selectByExampleAndRowBounds(example,new RowBounds(start,size));
//		List<SiteIconCategory> siteIconCategoryList = siteIconCategoryMapper.getSiteIconCategoryList(bussType, name);
		
		log.info("分类查询：start={},size={},count={},list={}",start,size,siteIconCategoryList.size(),siteIconCategoryList);
		List<SiteIconCategoryDto> siteIconCategoryDtos = new ArrayList<>();
		for (SiteIconCategory siteIconCategory : siteIconCategoryList) {
			SiteIconCategoryDto siteIconCategoryDto = new SiteIconCategoryDto();
			BeanUtils.copyProperties(siteIconCategory, siteIconCategoryDto);
			Long siteIconCategoryId = siteIconCategory.getId();
			Example example1 = new Example(SiteMatters.class);
			Example.Criteria criteria1 = example1.createCriteria();
			criteria1.andEqualTo("iconCategoryId", siteIconCategoryId);
			criteria1.andIsNotNull("blockSsmId");
			siteIconCategoryDto.setMattersCount(siteMattersMapper.selectCountByExample(example1));
			// 子类数量
			Example example2 = new Example(SiteIconCategory.class);
			Example.Criteria criteria2 = example2.createCriteria();
			criteria2.andEqualTo("parentId", siteIconCategoryId);
			siteIconCategoryDto.setCategoryCount(siteIconCategoryMapper.selectCountByExample(example2));
			siteIconCategoryDtos.add(siteIconCategoryDto);
		}
		return siteIconCategoryDtos;
	}
	
	@Override
	public SiteIconCategory saveSiteIconCategory(SiteIconCategory siteIconCategory) {
		Date date = new Date();
		if (siteIconCategory.getId() == null) {
			siteIconCategory.setCreateTime(date);
			siteIconCategory.setModifyTime(date);
			siteIconCategoryMapper.insertSelective(siteIconCategory);
		} else {
			siteIconCategory.setModifyTime(date);
			siteIconCategoryMapper.updateByPrimaryKeySelective(siteIconCategory);
			return siteIconCategory;
		}
		return siteIconCategory;
	}

	@Override
	public Integer ZjybappSaveSiteIconCategory(SiteIconCategory siteIconCategory) {
		Date date = new Date();
		SiteIconCategory selectSiteIconCategory = new SiteIconCategory();
		selectSiteIconCategory.setName(siteIconCategory.getName());
		List<SiteIconCategory> siteIconCategoryList = siteIconCategoryMapper.select(selectSiteIconCategory);
		if (siteIconCategory.getId() == null) {
			siteIconCategory.setCreateTime(date);
			siteIconCategory.setModifyTime(date);
			if (siteIconCategoryList.size() != 0) {
				return -1;
			}
			siteIconCategoryMapper.insertSelective(siteIconCategory);
			return 1;
		} else {
			Example example = new Example(SiteIconCategory.class);
			example.createCriteria().andNotEqualTo("id", siteIconCategory.getId()).andEqualTo("name",
					siteIconCategory.getName());
			List<SiteIconCategory> list = siteIconCategoryMapper.selectByExample(example);
			if (list.size() != 0) {
				return -1;
			}
			siteIconCategory.setModifyTime(date);
			siteIconCategoryMapper.updateByPrimaryKeySelective(siteIconCategory);
			return 1;

		}
	}

	@Override
	public Integer deleteSiteIconCategory(Integer id) {
		Example example1 = new Example(SiteMatters.class);
		Example.Criteria criteria1 = example1.createCriteria();
		criteria1.andEqualTo("iconCategoryId", id);
//		criteria1.andIsNotNull("blockSsmId");
		int count = siteMattersMapper.selectCountByExample(example1);
		if (count > 0) {
			return -1;
		}
		
		Example example2 = new Example(SiteIconCategory.class);
		Example.Criteria criteria2 = example2.createCriteria();
		criteria2.andEqualTo("parentId", id);
		int count2 = siteIconCategoryMapper.selectCountByExample(example2);
		if (count2 > 0) {
			return -2;
		}
		return siteIconCategoryMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public Integer deleteByParentId(Integer parentId) {
		Example example1 = new Example(SiteIconCategory.class);
		Example.Criteria criteria1 = example1.createCriteria();
		criteria1.andEqualTo("parentId", parentId);
		return siteIconCategoryMapper.deleteByExample(example1);
	}

	@Override
	public SiteIconCategory getByPrimaryKey(Integer id) {
		return siteIconCategoryMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<SiteIconCategory> getByParentId(Integer parentId) {
		Example example1 = new Example(SiteIconCategory.class);
		example1.createCriteria().andEqualTo("parentId", parentId);
		return siteIconCategoryMapper.selectByExample(example1);
	}

	@Override
	public List<SiteIconCategory> getListAll() {
		return siteIconCategoryMapper.selectAll();
	}
}
