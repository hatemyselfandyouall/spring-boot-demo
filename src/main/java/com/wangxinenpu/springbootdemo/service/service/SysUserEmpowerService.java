package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysUserEmpowerDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysUserEmpowerMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysUserEmpower;
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
 * 用户授权service
 * @author Administrator
 *
 */
@Service
public class SysUserEmpowerService {
	private static Logger logger = LoggerFactory.getLogger(SysUserEmpowerService.class);
	@Resource
	private SysUserEmpowerMapper sysUserEmpowerMapper;
	
	/**
	 * 新增用户授权po信息
	 * @param po
	 * @return
	 */
	public Long addSysUserEmpower(SysUserEmpowerDTO dto){
		if(dto == null) {
			logger.info("sysUserEmpowerService.addSysUserEmpower dto={}",dto);
			return 0L;
		}
		SysUserEmpower sc = dto.copyTo(SysUserEmpower.class);
		sysUserEmpowerMapper.insertSysUserEmpower(sc);
		return sc.getId();
	}

	/**
	 *  根据主键得到用户授权表记录
	 * @param id
	 * @return
	 */
	public SysUserEmpowerDTO getByPrimaryKey(Long id){
		if(id == null || id == 0) {
			logger.info("sysUserEmpowerService.getByPrimaryKey error:id={}",id);
			return null;
		}
		SysUserEmpower po = sysUserEmpowerMapper.getByPrimaryKey(id);
		if(po==null) return null;
		return po.copyTo(SysUserEmpowerDTO.class);
	}
	
	/**
	 * 更新po信息
	 * @param po
	 * @return
	 */
	public int updatepo(SysUserEmpowerDTO dto){
		if(dto == null || dto.getId() == null || dto.getId() == 0) {
			logger.info("sysUserEmpowerService.updatepo dto={}",dto);
			return 0;
		}
		return sysUserEmpowerMapper.updateSysUserEmpower(dto.copyTo(SysUserEmpower.class));//object转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数查询 获取用户授权列表  带分页
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysUserEmpowerDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size){
		if(searchMap==null || searchMap.isEmpty() || size<1) {
			logger.info("sysUserEmpowerService.getListByWhere searchMap={}，start={},size={}",searchMap,start,size);
			return Collections.emptyList();
		}
		List<SysUserEmpower> sysUserEmpowerList = sysUserEmpowerMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(sysUserEmpowerList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysUserEmpowerList, SysUserEmpowerDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询用户授权列表 
	 * @param searchMap
	 * @return
	 */
	public List<SysUserEmpowerDTO> getListByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("sysUserEmpowerService.getListByWhere searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysUserEmpower> sysUserEmpowerList = sysUserEmpowerMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(sysUserEmpowerList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysUserEmpowerList, SysUserEmpowerDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数获取用户授权数量
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			return 0;
		}
		return sysUserEmpowerMapper.getCountByWhere(searchMap);
	}
	
	/**
	 * 根据ID删除
	 * @param id
	 * @return
	 */
	public int deleteById(Long id){
		return sysUserEmpowerMapper.deleteById(id);
	}
	
	public List<SysUserEmpowerDTO> getlistByUserIds(String startTime, String endTime){
		return sysUserEmpowerMapper.getlistByUserIds(startTime, endTime);
	}

}
