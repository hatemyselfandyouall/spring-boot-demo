package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysReqBusInfoDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysReqBusInfoMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysReqBusInfo;
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
 * 审核结果返回信息service
 * @author Administrator
 *
 */
@Service
public class SysReqBusInfoService {
	private static Logger logger = LoggerFactory.getLogger(SysReqBusInfoService.class);
	@Resource
	private SysReqBusInfoMapper SysReqBusInfoMapper;
	/**
	 * 新增审核结果返回信息po信息
	 * @param po
	 * @return
	 */
	public Long addSysReqBusInfo(SysReqBusInfoDTO dto){
		if(dto == null) {
			logger.info("SysReqBusInfoService.addSysReqBusInfo dto={}",dto);
			return 0L;
		}
		SysReqBusInfo sc = dto.copyTo(SysReqBusInfo.class);
		SysReqBusInfoMapper.insertSysReqBusInfo(sc);
		return sc.getId();
	}

	/**
	 *  根据主键得到审核结果返回信息表记录
	 * @param id
	 * @return
	 */
	public SysReqBusInfoDTO getByPrimaryKey(Long id){
		if(id == null || id == 0) {
			logger.info("SysReqBusInfoService.getByPrimaryKey error:id={}",id);
			return null;
		}
		SysReqBusInfo po = SysReqBusInfoMapper.getByPrimaryKey(id);
		if(po==null) return null;
		return po.copyTo(SysReqBusInfoDTO.class);
	}
	
	/**
	 * 更新po信息
	 * @param po
	 * @return
	 */
	public int updatepo(SysReqBusInfoDTO dto){
		if(dto == null || dto.getId() == null || dto.getId() == 0) {
			logger.info("SysReqBusInfoService.updatepo dto={}",dto);
			return 0;
		}
		return SysReqBusInfoMapper.updateSysReqBusInfo(dto.copyTo(SysReqBusInfo.class));//object转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数查询 获取审核结果返回信息列表  带分页
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysReqBusInfoDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size){
		if(searchMap==null || searchMap.isEmpty() || size<1) {
			logger.info("SysReqBusInfoService.getListByWhere searchMap={}，start={},size={}",searchMap,start,size);
			return Collections.emptyList();
		}
		List<SysReqBusInfo> SysReqBusInfoList = SysReqBusInfoMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(SysReqBusInfoList)) return Collections.emptyList();
		return BaseVo.copyListTo(SysReqBusInfoList, SysReqBusInfoDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询审核结果返回信息列表 
	 * @param searchMap
	 * @return
	 */
	public List<SysReqBusInfoDTO> getListByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("SysReqBusInfoService.getListByWhere searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysReqBusInfo> SysReqBusInfoList = SysReqBusInfoMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(SysReqBusInfoList)) return Collections.emptyList();
		return BaseVo.copyListTo(SysReqBusInfoList, SysReqBusInfoDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数获取审核结果返回信息数量
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			return 0;
		}
		return SysReqBusInfoMapper.getCountByWhere(searchMap);
	}
	
	/**
	 * 根据ID删除
	 * @param id
	 * @return
	 */
	public int deleteById(Long id){
		return SysReqBusInfoMapper.deleteById(id);
	}
	
}
