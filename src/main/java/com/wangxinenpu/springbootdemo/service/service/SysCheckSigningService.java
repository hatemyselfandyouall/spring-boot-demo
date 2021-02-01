package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckSigningDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysCheckSigningMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysCheckSigning;
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
 * 待签领任务service
 * @author Administrator
 *
 */
@Service
public class SysCheckSigningService {
	private static Logger logger = LoggerFactory.getLogger(SysCheckSigningService.class);
	@Resource
	private SysCheckSigningMapper sysCheckSigningMapper;
	
	/**
	 * 新增待签领任务po信息
	 * @param po
	 * @return
	 */
	public Long addSysCheckSigning(SysCheckSigningDTO dto){
		if(dto == null) {
			logger.info("sysCheckSigningService.addSysCheckSigning dto={}",dto);
			return 0L;
		}
		SysCheckSigning sc = dto.copyTo(SysCheckSigning.class);
		sysCheckSigningMapper.insertSysCheckSigning(sc);
		return sc.getId();
	}

	/**
	 *  根据主键得到待签领任务表记录
	 * @param id
	 * @return
	 */
	public SysCheckSigningDTO getByPrimaryKey(Long id){
		if(id == null || id == 0) {
			logger.info("sysCheckSigningService.getByPrimaryKey error:id={}",id);
			return null;
		}
		SysCheckSigning po = sysCheckSigningMapper.getByPrimaryKey(id);
		if(po==null) return null;
		return po.copyTo(SysCheckSigningDTO.class);
	}
	
	/**
	 * 更新po信息
	 * @param po
	 * @return
	 */
	public int updatepo(SysCheckSigningDTO dto){
		if(dto == null || dto.getId() == null || dto.getId() == 0) {
			logger.info("sysCheckSigningService.updatepo dto={}",dto);
			return 0;
		}
		return sysCheckSigningMapper.updateSysCheckSigning(dto.copyTo(SysCheckSigning.class));//object转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数查询 获取待签领任务列表  带分页
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysCheckSigningDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size){
		if(searchMap==null || searchMap.isEmpty() || size<1) {
			logger.info("sysCheckSigningService.getListByWhere searchMap={}，start={},size={}",searchMap,start,size);
			return Collections.emptyList();
		}
		List<SysCheckSigning> sysCheckSigningList = sysCheckSigningMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(sysCheckSigningList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCheckSigningList, SysCheckSigningDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询待签领任务列表 
	 * @param searchMap
	 * @return
	 */
	public List<SysCheckSigningDTO> getListByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("sysCheckSigningService.getListByWhere searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysCheckSigning> sysCheckSigningList = sysCheckSigningMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(sysCheckSigningList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCheckSigningList, SysCheckSigningDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 *待签领列表
	 * @param searchMap
	 * @return
	 */
	public List<SysCheckSigningDTO> getListByCondition(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("sysCheckSigningService.getListByCondition searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysCheckSigning> sysCheckSigningList = sysCheckSigningMapper.getListByCondition(searchMap);
		if(CollectionUtils.isEmpty(sysCheckSigningList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCheckSigningList, SysCheckSigningDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	
	/**
	 * 根据参数获取待签领任务数量
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			return 0;
		}
		return sysCheckSigningMapper.getCountByWhere(searchMap);
	}

	public int getCountByCondition(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			return 0;
		}
		return sysCheckSigningMapper.getCountByCondition(searchMap);
	}


	/**
	 * 根据ID删除
	 * @param id
	 * @return
	 */
	public int deleteById(Long id){
		return sysCheckSigningMapper.deleteById(id);
	}
	
	/**
	 * 根据流程ID和业务ID删除
	 * @param searchMap
	 * @return
	 */
	public int deleteByProIdAndBusId(HashMap<String, Object> searchMap){
		return sysCheckSigningMapper.deleteByProIdAndBusId(searchMap);
	}
	
}
