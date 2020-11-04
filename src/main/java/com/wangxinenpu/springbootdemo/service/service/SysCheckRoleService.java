package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckRoleDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysCheckRoleMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysCheckRole;
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
 * 审核角色service
 * @author Administrator
 *
 */
@Service
public class SysCheckRoleService {
	private static Logger logger = LoggerFactory.getLogger(SysCheckRoleService.class);
	@Resource
	private SysCheckRoleMapper sysCheckRoleMapper;
	
	/**
	 * 新增审核角色po信息
	 * @param po
	 * @return
	 */
	public Long addSysCheckRole(SysCheckRoleDTO dto){
		if(dto == null) {
			logger.info("sysCheckRoleService.addSysCheckRole dto={}",dto);
			return 0L;
		}
		return sysCheckRoleMapper.insertSysCheckRole(dto.copyTo(SysCheckRole.class));
	}

	/**
	 *  根据主键得到审核角色表记录
	 * @param id
	 * @return
	 */
	public SysCheckRoleDTO getByPrimaryKey(Long id){
		if(id == null || id == 0) {
			logger.info("sysCheckRoleService.getByPrimaryKey error:id={}",id);
			return null;
		}
		SysCheckRole po = sysCheckRoleMapper.getByPrimaryKey(id);
		if(po==null) return null;
		return po.copyTo(SysCheckRoleDTO.class);
	}
	
	/**
	 * 更新po信息
	 * @param po
	 * @return
	 */
	public int updatepo(SysCheckRoleDTO dto){
		if(dto == null || dto.getId() == null || dto.getId() == 0) {
			logger.info("sysCheckRoleService.updatepo dto={}",dto);
			return 0;
		}
		return sysCheckRoleMapper.updateSysCheckRole(dto.copyTo(SysCheckRole.class));//object转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数查询 获取审核角色列表  带分页
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysCheckRoleDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size){
		if(searchMap==null || searchMap.isEmpty() || size<1) {
			logger.info("sysCheckRoleService.getListByWhere searchMap={}，start={},size={}",searchMap,start,size);
			return Collections.emptyList();
		}
		List<SysCheckRole> sysCheckRoleList = sysCheckRoleMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(sysCheckRoleList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCheckRoleList, SysCheckRoleDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询审核角色列表 
	 * @param searchMap
	 * @return
	 */
	public List<SysCheckRoleDTO> getListByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("sysCheckRoleService.getListByWhere searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysCheckRole> sysCheckRoleList = sysCheckRoleMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(sysCheckRoleList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCheckRoleList, SysCheckRoleDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数获取审核角色数量
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			return 0;
		}
		return sysCheckRoleMapper.getCountByWhere(searchMap);
	}
	
	/**
	 * 根据ID删除
	 * @param id
	 * @return
	 */
	public int deleteById(Long id){
		return sysCheckRoleMapper.deleteById(id);
	}
	
	public int deleteByProId(Long proId){
		return sysCheckRoleMapper.deleteByProId(proId);
	}

}
