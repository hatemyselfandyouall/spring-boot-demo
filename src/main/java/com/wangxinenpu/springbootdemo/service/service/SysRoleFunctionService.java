package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysRoleFunctionDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysRoleFunctionMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysRoleFunction;
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
 * 角色功能对照service
 * 
 * @author Administrator
 *
 */
@Service
public class SysRoleFunctionService {
	private static Logger logger = LoggerFactory.getLogger(SysRoleFunctionService.class);
	@Resource
	private SysRoleFunctionMapper sysRoleFunctionMapper;

	/**
	 * 新增角色功能对照
	 * 
	 * @param po
	 * @return
	 */
	public int addSysRoleFunction(SysRoleFunctionDTO dto) {
		if (dto == null) {
			logger.info("SysRoleFunctionService.addSysRoleFunction dto={}", dto);
			return 0;
		}
		return sysRoleFunctionMapper.insertSysRoleFunction(dto.copyTo(SysRoleFunction.class));
	}
	
	public int updatepo(SysRoleFunctionDTO dto){
		if (dto == null) {
			logger.info("SysRoleFunctionService.updatepo dto={}", dto);
			return 0;
		}
		return sysRoleFunctionMapper.updateSysRoleFunction(dto.copyTo(SysRoleFunction.class));
	}

	/**
	 * 根据参数查询角色功能对照信息带分页
	 * 
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysRoleFunctionDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size) {
		if (searchMap == null || searchMap.isEmpty() || size < 1) {
			logger.info("SysRoleFunctionService.getListByWhere searchMap={}，start={},size={}", searchMap, start, size);
			return Collections.emptyList();
		}
		List<SysRoleFunction> SysRoleFunctionList = sysRoleFunctionMapper.getListByWhere(searchMap,
				new RowBounds(start, size));
		if (CollectionUtils.isEmpty(SysRoleFunctionList))
			return Collections.emptyList();
		return BaseVo.copyListTo(SysRoleFunctionList, SysRoleFunctionDTO.class);// 列表转换
																				// 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询角色功能对照信息
	 * 
	 * @param searchMap
	 * @return
	 */
	public List<SysRoleFunctionDTO> getListByWhere(HashMap<String, Object> searchMap) {
		if (searchMap == null || searchMap.isEmpty()) {
			logger.info("SysRoleFunctionService.getListByWhere searchMap={}", searchMap);
			return Collections.emptyList();
		}
		List<SysRoleFunction> SysRoleFunctionList = sysRoleFunctionMapper.getListByWhere(searchMap);
		if (CollectionUtils.isEmpty(SysRoleFunctionList))
			return Collections.emptyList();
		return BaseVo.copyListTo(SysRoleFunctionList, SysRoleFunctionDTO.class);// 列表转换
																				// 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数 获取角色功能对照
	 * 
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap) {
		if (searchMap == null || searchMap.isEmpty()) {
			return 0;
		}
		return sysRoleFunctionMapper.getCountByWhere(searchMap);
	}

	/**
	 * 根据角色ID删除
	 * 
	 * @param roleId
	 */
	public void deleteByRoleId(String roleId) {
		sysRoleFunctionMapper.deleteByRoleId(roleId);
	}
	
	/**
	 * 根据功能ID删除
	 * 
	 * @param functionId
	 */
	public void deleteByFunctionId(Long functionId) {
		sysRoleFunctionMapper.deleteByFunctionId(functionId);
	}
	
	/**
	 * 根据功能ID查询
	 * @param functionId
	 * @return
	 */
	public List<SysRoleFunctionDTO> findByFunctionId(Long functionId){
		List<SysRoleFunction> roleFunctionList = sysRoleFunctionMapper.findByFunctionId(functionId);
		if (CollectionUtils.isEmpty(roleFunctionList))
			return Collections.emptyList();
		return BaseVo.copyListTo(roleFunctionList, SysRoleFunctionDTO.class);
	}
	
	/**
	 * 根据功能ID查询
	 * @param roleId
	 * @return
	 */
	public List<SysRoleFunctionDTO> findByRoleId(String roleId){
		List<SysRoleFunction> roleFunctionList = sysRoleFunctionMapper.findByRoleId(roleId);
		if (CollectionUtils.isEmpty(roleFunctionList))
			return Collections.emptyList();
		return BaseVo.copyListTo(roleFunctionList, SysRoleFunctionDTO.class);
	}


}
