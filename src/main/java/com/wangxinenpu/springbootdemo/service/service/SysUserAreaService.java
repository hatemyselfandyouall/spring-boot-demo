package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysUserAreaDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysUserAreaMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysUserArea;
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
 * 用户区域对照service
 * @author Administrator
 *
 */
@Service
public class SysUserAreaService {
	private static Logger logger = LoggerFactory.getLogger(SysUserAreaService.class);
	@Resource
	private SysUserAreaMapper sysUserAreaMapper;
	
	/**
	 * 新增用户区域对照
	 * @param po
	 * @return
	 */
	public int addSysUserArea(SysUserAreaDTO dto){
		if(dto == null) {
			logger.info("SysUserAreaService.addSysUserArea dto={}",dto);
			return 0;
		}
		return sysUserAreaMapper.insertSysUserArea(dto.copyTo(SysUserArea.class));
	}

	/**
	 * 根据参数查询用户区域对照信息带分页
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysUserAreaDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size){
		if(searchMap==null || searchMap.isEmpty() || size<1) {
			logger.info("SysUserAreaService.getListByWhere searchMap={}，start={},size={}",searchMap,start,size);
			return Collections.emptyList();
		}
		List<SysUserArea> SysUserAreaList = sysUserAreaMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(SysUserAreaList)) return Collections.emptyList();
		return BaseVo.copyListTo(SysUserAreaList, SysUserAreaDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询用户区域对照信息
	 * @param searchMap
	 * @return
	 */
	public List<SysUserAreaDTO> getListByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("SysUserAreaService.getListByWhere searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysUserArea> SysUserAreaList = sysUserAreaMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(SysUserAreaList)) return Collections.emptyList();
		return BaseVo.copyListTo(SysUserAreaList, SysUserAreaDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数 获取用户区域对照
	 * 以userId为主
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			return 0;
		}
		return sysUserAreaMapper.getCountByWhere(searchMap);
	}
	
	/**
	 * 根据用户id查询
	 * @param userId
	 * @return
	 */
	public List<SysUserAreaDTO> findByUserId(String userId){
		List<SysUserArea> sysUserAreaList = sysUserAreaMapper.findByUserId(userId);
		if(CollectionUtils.isEmpty(sysUserAreaList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysUserAreaList, SysUserAreaDTO.class);
	}
	
	/**
	 * 根据用户ID删除
	 * @param userId
	 */
	public void deleteByUserId(SysUserAreaDTO sysUserDTO){
		sysUserAreaMapper.deleteByUserId(sysUserDTO.copyTo(SysUserArea.class));
	}
	
	/**
	 * 批量新增
	 * @param list
	 */
	public void insertByBatch(List<SysUserAreaDTO> list){
		sysUserAreaMapper.insertByBatch(BaseVo.copyListTo(list, SysUserArea.class));
	}

}
