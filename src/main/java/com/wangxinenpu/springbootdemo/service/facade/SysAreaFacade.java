package com.wangxinenpu.springbootdemo.service.facade;


import com.wangxinenpu.springbootdemo.dataobject.vo.*;

import java.util.List;


/**
 * 系统区域服务接口
 * 
 * @author Administrator
 *
 */
public interface SysAreaFacade {

    
	/**
	 * 根据主键得到系统区域表记录
	 * 
	 * @param id
	 * @return
	 */
	public SysAreaDTO getByPrimaryKey(Long id);
	
    /**
     * 根据区域ID查询下级
     * @param areaId
     * @return
     */
    public List<SysAreaDTO> findByParentId(Long areaId);

	/**
	 * 根据区域ID查询下级
	 * @param areaId
	 * @return
	 */
	public List<String> findAllByParentId(String areaId);

	/**
	 * 根据区域ID查询下级
	 * @param areaId
	 * @return
	 */

    /**
     * 查询管辖机构
     * @param areaId
     * @return
     */
    public List<SysAreaDTO> findByIdpath(String areaId);

	/**
	 * 查询管辖机构
	 * @return
	 */
	 List<SysAreaDTO> findAllByParentIdNull();

	/**
	 * 查询管辖机构
	 * @return
	 */
	List<SysAreaDTO> getAreaCityList();

	/**
	 * 查询管辖机构
	 * @return
	 */
	List<SysAreaDTO> getAreaRegionList(String parentId);
	/**
	 * 查询管辖机构
	 * @return
	 */
	SysAreaNameDTO getAreaInfo(Long id);
}
