package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysRoleDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysRoleMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysRole;
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
 * 系统角色service
 * 
 * @author Administrator
 *
 */
@Service
public class SysRoleService {
	private static Logger logger = LoggerFactory.getLogger(SysRoleService.class);
	@Resource
	private SysRoleMapper sysRoleMapper;

	/**
	 * 新增角色po信息
	 *
	 * @return
	 */
	public int addSysRole(SysRoleDTO dto) {
		if (dto == null) {
			logger.info("sysRoleService.addSysRole dto={}", dto);
			return 0;
		}
		return sysRoleMapper.insertSysRole(dto.copyTo(SysRole.class));
	}

	/**
	 * 根据主键得到系统角色表记录
	 * 
	 * @param id
	 * @return
	 */
	public SysRoleDTO getByPrimaryKey(String id) {
		if (id == null || id == "") {
			logger.info("sysRoleService.getByPrimaryKey error:id={}", id);
			return null;
		}
		SysRole po = sysRoleMapper.getByPrimaryKey(id);
		if (po == null)
			return null;
		return po.copyTo(SysRoleDTO.class);
	}

	/**
	 * 更新po信息
	 * 
	 * @return
	 */
	public int updatepo(SysRoleDTO dto) {
		if (dto == null || dto.getId() == null || dto.getId() == "") {
			logger.info("sysRoleService.updatepo dto={}", dto);
			return 0;
		}
		return sysRoleMapper.updateSysRole(dto.copyTo(SysRole.class));// object转换
																		// 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询 获取角色列表 带分页
	 * 
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysRoleDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size) {
		if (searchMap == null || searchMap.isEmpty() || size < 1) {
			logger.info("sysRoleService.getListByWhere searchMap={}，start={},size={}", searchMap, start, size);
			return Collections.emptyList();
		}
		List<SysRole> sysRoleList = sysRoleMapper.getListByWhere(searchMap, new RowBounds(start, size));
		if (CollectionUtils.isEmpty(sysRoleList))
			return Collections.emptyList();
		return BaseVo.copyListTo(sysRoleList, SysRoleDTO.class);// 列表转换
																// 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询角色列表
	 * 
	 * @param searchMap
	 * @return
	 */
	public List<SysRoleDTO> getListByWhere(HashMap<String, Object> searchMap) {
		if (searchMap == null || searchMap.isEmpty()) {
			logger.info("sysRoleService.getListByWhere searchMap={}", searchMap);
			return Collections.emptyList();
		}
		List<SysRole> sysRoleList = sysRoleMapper.getListByWhere(searchMap);
		if (CollectionUtils.isEmpty(sysRoleList))
			return Collections.emptyList();
		return BaseVo.copyListTo(sysRoleList, SysRoleDTO.class);// 列表转换
																// 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数 获取买家的系统角色列表 以RoleId为主
	 * 
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap) {
		if (searchMap == null || searchMap.isEmpty()) {
			return 0;
		}
		return sysRoleMapper.getCountByWhere(searchMap);
	}

	/**
	 * 根据角色名和id查询
	 *
	 * @return
	 */
	public SysRoleDTO getByRoleNameAndRoleId(HashMap<String, Object> searchMap) {
		SysRole sysRole = sysRoleMapper.getByRoleNameAndRoleId(searchMap);
		if (null == sysRole) {
			return null;
		}
		return sysRole.copyTo(SysRoleDTO.class);
	}

	/**
	 * 根据角色id删除
	 * 
	 * @param id
	 */
	public void deleteByPrimaryKey(String id) {
		sysRoleMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 查询当前用户拥有的角色
	 * 
	 * @param userId
	 * @return
	 */
	public List<SysRoleDTO> queryRoleByUserId(String userId) {
		List<SysRole> sysRoleList = sysRoleMapper.queryRoleByUserId(userId);
		if (CollectionUtils.isEmpty(sysRoleList))
			return Collections.emptyList();
		return BaseVo.copyListTo(sysRoleList, SysRoleDTO.class);// 列表转换
																// 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 查询管理员角色
	 * 
	 * @return
	 */
	public List<SysRoleDTO> queryByAdmin() {
		List<SysRole> sysRoleList = sysRoleMapper.queryByAdmin();
		if (CollectionUtils.isEmpty(sysRoleList))
			return Collections.emptyList();
		return BaseVo.copyListTo(sysRoleList, SysRoleDTO.class);
	}

	/**
	 * 根据区域查询角色
	 * 
	 * @param areaId
	 * @return
	 */
	public List<SysRoleDTO> queryRoleByArea(Long areaId) {
		List<SysRole> sysRoleList = sysRoleMapper.queryRoleByArea(areaId);
		if (CollectionUtils.isEmpty(sysRoleList))
			return Collections.emptyList();
		return BaseVo.copyListTo(sysRoleList, SysRoleDTO.class);
	}

	/**
	 * 根据机构查询角色
	 * 
	 * @param orgId
	 * @return
	 */
	public List<SysRoleDTO> queryByOrgId(Long orgId) {
		List<SysRole> sysRoleList = sysRoleMapper.queryByOrgId(orgId);
		if (CollectionUtils.isEmpty(sysRoleList))
			return Collections.emptyList();
		return BaseVo.copyListTo(sysRoleList, SysRoleDTO.class);
	}

	/**
	 * 查询普通用户角色
	 * 
	 * @param roleType
	 * @return
	 */
	public List<SysRoleDTO> findAllByRoletype(String roleType) {
		List<SysRole> sysRoleList = sysRoleMapper.findAllByRoletype(roleType);
		if (CollectionUtils.isEmpty(sysRoleList))
			return Collections.emptyList();
		return BaseVo.copyListTo(sysRoleList, SysRoleDTO.class);
	}

	/**
	 * 查询机构普通用户角色
	 * 
	 * @param orgTd
	 * @return
	 */
	public List<SysRoleDTO> findByRoletypeAndOrgid(Long orgTd) {
		List<SysRole> sysRoleList = sysRoleMapper.findByRoletypeAndOrgid(orgTd);
		if (CollectionUtils.isEmpty(sysRoleList))
			return Collections.emptyList();
		return BaseVo.copyListTo(sysRoleList, SysRoleDTO.class);
	}

	/**
	 * 查询区域普通用户角色
	 * 
	 * @param areaId
	 * @return
	 */
	public List<SysRoleDTO> queryRoleByRoletypeAndAreaid(Long areaId) {
		List<SysRole> sysRoleList = sysRoleMapper.queryRoleByRoletypeAndAreaid(areaId);
		if (CollectionUtils.isEmpty(sysRoleList))
			return Collections.emptyList();
		return BaseVo.copyListTo(sysRoleList, SysRoleDTO.class);
	}

	/**
	 * 查询所有角色
	 * 
	 * @return
	 */
	public List<SysRoleDTO> findAll() {
		List<SysRole> sysRoleList = sysRoleMapper.findAll();
		if (CollectionUtils.isEmpty(sysRoleList))
			return Collections.emptyList();
		return BaseVo.copyListTo(sysRoleList, SysRoleDTO.class);
	}
	
	/**
	 * 根据系统类型查询角色
	 * 
	 * @param funType
	 * @return
	 */
	public List<SysRoleDTO> findRoleByFunType(String funType, Long orgId) {
		List<SysRole> sysRoleList = sysRoleMapper.findRoleByFunType(funType,orgId);
		if (CollectionUtils.isEmpty(sysRoleList))
			return Collections.emptyList();
		return BaseVo.copyListTo(sysRoleList, SysRoleDTO.class);
	}
	
	
	/**
     * 批量新增角色
     * @param list
     */
    public void insertByBatch(List<SysRoleDTO> list){
    	sysRoleMapper.insertByBatch(BaseVo.copyListTo(list,SysRole.class));
    }


	/**
	 * 根据天正的角色id查询本系统的角色
	 *
	 * @param roleCodes
	 * @returnid
	 */
	public List<SysRoleDTO> findRoleInRoleCode(List<String> roleCodes) {
		return  sysRoleMapper.findRoleInRoleCode(roleCodes);
	}
}
