package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckMaterialDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysCheckMaterialMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysCheckMaterial;
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
 * 审核资料service
 * @author Administrator
 *
 */
@Service
public class SysCheckMaterialService {
	private static Logger logger = LoggerFactory.getLogger(SysCheckMaterialService.class);
	@Resource
	private SysCheckMaterialMapper sysCheckMaterialMapper;
	
	/**
	 * 新增审核资料po信息
	 * @param po
	 * @return
	 */
	public Long addSysCheckMaterial(SysCheckMaterialDTO dto){
		if(dto == null) {
			logger.info("sysCheckMaterialService.addSysCheckMaterial dto={}",dto);
			return 0L;
		}
		return sysCheckMaterialMapper.insertSysCheckMaterial(dto.copyTo(SysCheckMaterial.class));
	}

	/**
	 *  根据主键得到审核资料表记录
	 * @param id
	 * @return
	 */
	public SysCheckMaterialDTO getByPrimaryKey(Long id){
		if(id == null || id == 0) {
			logger.info("sysCheckMaterialService.getByPrimaryKey error:id={}",id);
			return null;
		}
		SysCheckMaterial po = sysCheckMaterialMapper.getByPrimaryKey(id);
		if(po==null) return null;
		return po.copyTo(SysCheckMaterialDTO.class);
	}
	
	/**
	 * 更新po信息
	 * @param po
	 * @return
	 */
	public int updatepo(SysCheckMaterialDTO dto){
		if(dto == null || dto.getId() == null || dto.getId() == 0) {
			logger.info("sysCheckMaterialService.updatepo dto={}",dto);
			return 0;
		}
		return sysCheckMaterialMapper.updateSysCheckMaterial(dto.copyTo(SysCheckMaterial.class));//object转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数查询 获取审核资料列表  带分页
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysCheckMaterialDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size){
		if(searchMap==null || searchMap.isEmpty() || size<1) {
			logger.info("sysCheckMaterialService.getListByWhere searchMap={}，start={},size={}",searchMap,start,size);
			return Collections.emptyList();
		}
		List<SysCheckMaterial> sysCheckMaterialList = sysCheckMaterialMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(sysCheckMaterialList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCheckMaterialList, SysCheckMaterialDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询审核资料列表 
	 * @param searchMap
	 * @return
	 */
	public List<SysCheckMaterialDTO> getListByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("sysCheckMaterialService.getListByWhere searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysCheckMaterial> sysCheckMaterialList = sysCheckMaterialMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(sysCheckMaterialList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCheckMaterialList, SysCheckMaterialDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数获取审核资料数量
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			return 0;
		}
		return sysCheckMaterialMapper.getCountByWhere(searchMap);
	}
	
	/**
	 * 根据ID删除
	 * @param id
	 * @return
	 */
	public int deleteById(Long id){
		return sysCheckMaterialMapper.deleteById(id);
	}
	
	public int deleteByProId(Long proId){
		return sysCheckMaterialMapper.deleteByProId(proId);
	}

}
