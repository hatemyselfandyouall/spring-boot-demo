package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysAreaDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysAreaNameDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysAreaMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysArea;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import star.vo.BaseVo;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 行政区域service
 * @author Administrator
 *
 */
@Service
public class SysAreaService {
	private static Logger logger = LoggerFactory.getLogger(SysAreaService.class);
	@Resource
	private SysAreaMapper sysAreaMapper;
	
	/**
	 * 新增行政区域po信息
	 * @return
	 */
	public int addSysArea(SysAreaDTO dto){
		if(dto == null) {
			logger.info("sysAreaService.addSysArea dto={}",dto);
			return 0;
		}
		return sysAreaMapper.insertSysArea(dto.copyTo(SysArea.class));
	}

	/**
	 *  根据主键得到系统行政区域表记录
	 * @param id
	 * @return
	 */
	public SysAreaDTO getByPrimaryKey(Long id){
		if(id == null || id == 0) {
			logger.info("sysAreaService.getByPrimaryKey error:id={}",id);
			return null;
		}
		SysArea po = sysAreaMapper.getByPrimaryKey(id);
		if(po==null) return null;
		return po.copyTo(SysAreaDTO.class);
	}
	
	/**
	 * 更新po信息
	 * @return
	 */
	public int updatepo(SysAreaDTO dto){
		if(dto == null || dto.getId() == null || dto.getId() == 0) {
			logger.info("sysAreaService.updatepo dto={}",dto);
			return 0;
		}
		return sysAreaMapper.updateSysArea(dto.copyTo(SysArea.class));//object转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数查询 获取行政区域列表  带分页
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysAreaDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size){
		if(searchMap==null || searchMap.isEmpty() || size<1) {
			logger.info("sysAreaService.getListByWhere searchMap={}，start={},size={}",searchMap,start,size);
			return Collections.emptyList();
		}
		List<SysArea> sysAreaList = sysAreaMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(sysAreaList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysAreaList, SysAreaDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询行政区域列表 
	 * @param searchMap
	 * @return
	 */
	public List<SysAreaDTO> getListByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("sysAreaService.getListByWhere searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysArea> sysAreaList = sysAreaMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(sysAreaList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysAreaList, SysAreaDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数获取系统行政区域数量
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			return 0;
		}
		return sysAreaMapper.getCountByWhere(searchMap);
	}
	
	/**
	 * 查询所有区域信息
	 * @return
	 */
	public List<SysAreaDTO> findAll(){
		List<SysArea> sysAreaList = sysAreaMapper.findAll();
		return BaseVo.copyListTo(sysAreaList, SysAreaDTO.class);
	}
	
	/**
	 * 根据id查询下级区域
	 * @param id
	 * @return
	 */
	public List<SysAreaDTO> findByParentId(Long id){
		List<SysArea> sysAreaList = sysAreaMapper.findByParentId(id);
		return BaseVo.copyListTo(sysAreaList, SysAreaDTO.class);
	}
	
	public List<SysAreaDTO> findByIdpath(String idpath){
		List<SysArea> sysAreaList = sysAreaMapper.findByIdpath(idpath);
		return BaseVo.copyListTo(sysAreaList, SysAreaDTO.class);
	}
	
	/**
	 * 查询用户关联区域（医保用）
	 * @param userId
	 * @return
	 */
	public List<SysAreaDTO> queryAreaListByUserId(Long userId){
		List<SysArea> sysAreaList = sysAreaMapper.queryAreaListByUserId(userId);
		return BaseVo.copyListTo(sysAreaList, SysAreaDTO.class);
	}
	public List<SysAreaDTO> findAllByParentIdNull() {
		List<SysArea> sysAreaList = sysAreaMapper.findAllByParentIdNull();
		return BaseVo.copyListTo(sysAreaList, SysAreaDTO.class);
	}
	public List<SysAreaDTO> getAreaCityList() {
		List<SysArea> sysAreaList = sysAreaMapper.findAllByParentId("330000");
		return BaseVo.copyListTo(sysAreaList, SysAreaDTO.class);
	}

	public List<SysAreaDTO> getAreaRegionList(String parentId) {
		List<SysArea> sysAreaList = sysAreaMapper.findAllByParentId(parentId);
		return BaseVo.copyListTo(sysAreaList, SysAreaDTO.class);
	}
	public SysAreaNameDTO getAreaInfo(Long id) {
		return sysAreaMapper.getAreaInfo(id);
	}
}
