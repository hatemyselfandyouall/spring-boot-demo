package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysUserRoleDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysUserRoleMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysUserRole;
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
 * 用户角色对照service
 * @author Administrator
 *
 */
@Service
public class SysUserRoleService {
	private static Logger logger = LoggerFactory.getLogger(SysUserRoleService.class);
	@Resource
	private SysUserRoleMapper sysUserRoleMapper;
	
	/**
	 * 新增用户角色对照
	 * @param po
	 * @return
	 */
	public int addSysUserRole(SysUserRoleDTO dto){
		if(dto == null) {
			logger.info("SysUserRoleService.addSysUserRole dto={}",dto);
			return 0;
		}
		return sysUserRoleMapper.insertSysUserRole(dto.copyTo(SysUserRole.class));
	}

	/**
	 * 根据参数查询用户角色对照信息带分页
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysUserRoleDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size){
		if(searchMap==null || searchMap.isEmpty() || size<1) {
			logger.info("SysUserRoleService.getListByWhere searchMap={}，start={},size={}",searchMap,start,size);
			return Collections.emptyList();
		}
		List<SysUserRole> SysUserRoleList = sysUserRoleMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(SysUserRoleList)) return Collections.emptyList();
		return BaseVo.copyListTo(SysUserRoleList, SysUserRoleDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询用户角色对照信息
	 * @param searchMap
	 * @return
	 */
	public List<SysUserRoleDTO> getListByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("SysUserRoleService.getListByWhere searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysUserRole> SysUserRoleList = sysUserRoleMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(SysUserRoleList)) return Collections.emptyList();
		return BaseVo.copyListTo(SysUserRoleList, SysUserRoleDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数 获取用户角色对照
	 * 以userId为主
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			return 0;
		}
		return sysUserRoleMapper.getCountByWhere(searchMap);
	}
	
	/**
	 * 根据角色ID删除
	 * @param roleId
	 */
	public void deleteByRoleId(String roleId){
		sysUserRoleMapper.deleteByRoleId(roleId);
	}
	
	/**
	 * 根据用户ID删除
	 * @param userId
	 */
	public void deleteByUserId(SysUserRoleDTO sysUserRoleDTO){
		sysUserRoleMapper.deleteByUserId(sysUserRoleDTO.copyTo(SysUserRole.class));
	}
	
	/**
	 * 根据用户id查询
	 * @param userId
	 * @return
	 */
	public List<SysUserRoleDTO> findByUserId(String userId){
		List<SysUserRole> sysUserRoleList = sysUserRoleMapper.findByUserId(userId);
		if(CollectionUtils.isEmpty(sysUserRoleList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysUserRoleList, SysUserRoleDTO.class);
	}
	
	/**
	 * 根据角色ID查询
	 * @param roleId
	 * @return
	 */
	public List<SysUserRoleDTO> findByRoleId(String roleId){
		List<SysUserRole> sysUserRoleList = sysUserRoleMapper.findByRoleId(roleId);
		if(CollectionUtils.isEmpty(sysUserRoleList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysUserRoleList, SysUserRoleDTO.class);
	}
	
	/**
	 * 批量新增
	 * @param list
	 */
	public void insertByBatch(List<SysUserRoleDTO> list){
		sysUserRoleMapper.insertByBatch(BaseVo.copyListTo(list, SysUserRole.class));
	}

	public void saveUserRole(List<SysUserRoleDTO> sysUserRoleDTOS) {
		sysUserRoleMapper.saveUserRole(sysUserRoleDTOS);
	}
}
