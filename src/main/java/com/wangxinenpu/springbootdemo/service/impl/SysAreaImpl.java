package com.wangxinenpu.springbootdemo.service.impl;


import com.wangxinenpu.springbootdemo.dataobject.vo.*;
import com.wangxinenpu.springbootdemo.service.facade.SysAreaFacade;
import com.wangxinenpu.springbootdemo.service.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统区域facade服务实现类
 * 
 * @author Administrator
 *
 */
@Service
public class SysAreaImpl implements SysAreaFacade {
	
	@Autowired
	private SysAreaService sysAreaService;


	@Override
	public SysAreaDTO getByPrimaryKey(Long id) {
		return sysAreaService.getByPrimaryKey(id);
	}

	@Override
	public List<SysAreaDTO> findByParentId(Long areaId) {
		return sysAreaService.findByParentId(areaId);
	}

	@Override
	public List<String> findAllByParentId(String areaId) {
		List<SysAreaDTO> sysAreaDTOS=sysAreaService.findAll();
		List<String> areas=getChildrenOrgIds(sysAreaDTOS,areaId);
		return areas;
	}

	private List<String> getChildrenOrgIds(List<SysAreaDTO> sysAreaDTOS, String orgId) {
		List<String> list=new ArrayList<>();
		list.add(orgId);
		return getChildrenIterator(sysAreaDTOS,list);
	}

	private List<String> getChildrenIterator(List<SysAreaDTO> sysAreaDTOS, List<String> parents){
		List<String> children=sysAreaDTOS.stream().filter(i->i.getParentId()!=null&&parents.contains(i.getParentId()))
				.map(i->i.getId()+"").collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(children)){
			parents.addAll(getChildrenIterator(sysAreaDTOS,children));
		}
		return parents;
	}

	@Override
	public List<SysAreaDTO> findByIdpath(String areaId) {
		return sysAreaService.findByIdpath(areaId);
	}

	@Override
	public List<SysAreaDTO> findAllByParentIdNull() {
		return sysAreaService.findAllByParentIdNull();
	}

	@Override
	public List<SysAreaDTO> getAreaCityList() {
		return sysAreaService.getAreaCityList();
	}

	@Override
	public List<SysAreaDTO> getAreaRegionList(String parentId) {
		return sysAreaService.getAreaRegionList(parentId);
	}

	@Override
	public SysAreaNameDTO getAreaInfo(Long id) {
		return sysAreaService.getAreaInfo(id);
	}

}
