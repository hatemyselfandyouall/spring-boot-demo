package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysBusinessTypeDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysFunctionDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysUserDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysBusinessTypeMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.SysFunctionMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.SysRoleMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysBusinessType;
import com.wangxinenpu.springbootdemo.dataobject.po.SysFunction;
import com.wangxinenpu.springbootdemo.dataobject.po.SysRole;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import star.vo.BaseVo;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统功能service
 * 
 * @author Administrator
 *
 */
@Service
@SuppressWarnings("all")
public class SysFunctionService {
	private static Logger logger = LoggerFactory.getLogger(SysFunctionService.class);
	@Resource
	private SysFunctionMapper sysFunctionMapper;
	@Resource
	private SysRoleMapper sysRoleMapper;
	@Resource
	private SysBusinessTypeMapper sysBusinessTypeMapper;

	/**
	 * 新增系统功能
	 *
	 * @param po
	 * @return
	 */
	public int addSysFunction(SysFunctionDTO dto) {
		if (dto == null) {
			logger.info("SysFunctionService.addSysFunction dto={}", dto);
			return 0;
		}
		SysFunction sysFunction = dto.copyTo(SysFunction.class);
        int i = sysFunctionMapper.insertSysFunction(sysFunction);
        return i;
    }


	/**
	 * 新增系统功能
	 *
	 * @param po
	 * @return
	 */
	public Long addSysFunctionReturnId (SysFunctionDTO dto) {
		if (dto == null) {
			logger.info("SysFunctionService.addSysFunction dto={}", dto);
			return 0L;
		}
		SysFunction sysFunction = dto.copyTo(SysFunction.class);
		int i = sysFunctionMapper.insertSysFunction(sysFunction);
		return sysFunction.getId();
	}

	/**
	 * 修改系统功能
	 *
	 * @param po
	 * @return
	 */
	public int updateSysFunction(SysFunctionDTO dto) {
		if (dto == null) {
			logger.info("SysFunctionService.updateSysFunction dto={}", dto);
			return 0;
		}
		return sysFunctionMapper.updateSysFunction(dto.copyTo(SysFunction.class));
	}

	/**
	 * 根据参数查询系统功能信息带分页
	 *
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysFunctionDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size) {
		if (searchMap == null || searchMap.isEmpty() || size < 1) {
			logger.info("SysFunctionService.getListByWhere searchMap={}，start={},size={}", searchMap, start, size);
			return Collections.emptyList();
		}
		List<SysFunction> SysFunctionList = sysFunctionMapper.getListByWhere(searchMap, new RowBounds(start, size));
		if (CollectionUtils.isEmpty(SysFunctionList))
			return Collections.emptyList();
		return BaseVo.copyListTo(SysFunctionList, SysFunctionDTO.class);// 列表转换
		// 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询系统功能信息
	 *
	 * @param searchMap
	 * @return
	 */
	public List<SysFunctionDTO> getListByWhere(HashMap<String, Object> searchMap) {
		if (searchMap == null || searchMap.isEmpty()) {
			logger.info("SysFunctionService.getListByWhere searchMap={}", searchMap);
			return Collections.emptyList();
		}
		List<SysFunction> SysFunctionList = sysFunctionMapper.getListByWhere(searchMap);
		if (CollectionUtils.isEmpty(SysFunctionList))
			return Collections.emptyList();
		return BaseVo.copyListTo(SysFunctionList, SysFunctionDTO.class);// 列表转换
		// 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询系统功能信息
	 *
	 * @param searchMap
	 * @return
	 */
	public List<SysFunctionDTO> queryRbFlagListById(HashMap<String, Object> searchMap) {
		if (searchMap == null || searchMap.isEmpty()) {
			logger.info("SysFunctionService.queryRbFlagListById searchMap={}", searchMap);
			return Collections.emptyList();
		}
 		List<SysFunction> SysFunctionList = sysFunctionMapper.queryRbFlagListById(searchMap);
		if (CollectionUtils.isEmpty(SysFunctionList))
			return Collections.emptyList();
		return BaseVo.copyListTo(SysFunctionList, SysFunctionDTO.class);// 列表转换
		// 如果数量不多，尽量自己采用assember方法进行转换处理。
	}


	/**
	 * 根据参数 获取系统功能
	 *
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap) {
		if (searchMap == null || searchMap.isEmpty()) {
			return 0;
		}
		return sysFunctionMapper.getCountByWhere(searchMap);
	}

	/**
	 * 根据主键查询功能信息
	 *
	 * @param id
	 * @return
	 */
	public SysFunctionDTO getByPrimaryKey(Long id) {
		if (id == null) {
			logger.info("sysFunctionService.getByPrimaryKey error:id={}", id);
			return null;
		}
		SysFunction sysFunction = sysFunctionMapper.getByPrimaryKey(id);
		if (sysFunction == null)
			return null;
		return sysFunction.copyTo(SysFunctionDTO.class);
	}

	/**
	 * 查询当前用户所拥有的功能
	 *
	 * @param sysUser
	 * @return
	 */
	public List<SysFunctionDTO> getFunctionList(SysUserDTO sysUser) {
		List<SysFunction> functionList;
		if ("1".equals(sysUser.getUserType())) {
			functionList = sysFunctionMapper.getFunctionList();
		} else {
			List<SysRole> roleList = sysRoleMapper.queryRoleByUserId(String.valueOf(sysUser.getId()));
			List<String> ids1 = roleList.stream().map(role -> role.getId()).collect(Collectors.toList());
			String roleIds = "'" + StringUtils.join(ids1, "','") + "'";
			functionList = sysFunctionMapper.queryFunctionListByRoleId(roleIds);
		}
		return BaseVo.copyListTo(functionList, SysFunctionDTO.class);
	}

	/**
	 * 根据角色和是否接入流程查询菜单
	 *
	 * @param sysUser
	 * @param isBus
	 * @return
	 */
	public List<SysFunctionDTO> queryFunctionListByRoleIdsAndIsBus(SysUserDTO sysUser, String isBus, String funType) {
		List<SysFunction> functionList;
		if (sysUser.getId() == null) {
//			functionList = sysFunctionMapper.queryFunctionListByIsBusAndFunType(isBus, funType);
//			return BaseVo.copyListTo(functionList, SysFunctionDTO.class);
			return null;
		}
		if ("1".equals(sysUser.getUserType())) {
			functionList = sysFunctionMapper.getFunctionList();
		} else {
			List<SysRole> roleList = sysRoleMapper.queryRoleByUserId(String.valueOf(sysUser.getId()));
			List<String> ids1 = roleList.stream().map(role -> role.getId()).collect(Collectors.toList());
			String roleIds = "'" + StringUtils.join(ids1, "','") + "'";
			functionList = sysFunctionMapper.queryFunctionListByRoleIdsAndIsBus(roleIds, isBus, funType);
		}
		if(CollectionUtils.isNotEmpty(functionList)) {
			  return BaseVo.copyListTo(functionList, SysFunctionDTO.class);
		}
		return null;
	}

	/**
	 * 根据角色类型查询可授权菜单
	 *
	 * @param funType
	 * @return
	 */
	public List<SysFunctionDTO> findTreesByRoleType(String roleType) {
		List<SysFunction> functionList = sysFunctionMapper.findTreesByRoleType(roleType);
		return BaseVo.copyListTo(functionList, SysFunctionDTO.class);
	}

	/**
	 * 查询所有菜单
	 *
	 * @return
	 */
	public List<SysFunctionDTO> queryAllFunctionList() {
		List<SysFunction> functionList = sysFunctionMapper.queryAllFunctionList();
		return BaseVo.copyListTo(functionList, SysFunctionDTO.class);
	}

	/**
	 * 根据是否接入流程查询所有菜单
	 *
	 * @return
	 */
	public List<SysFunctionDTO> queryFunctionListByIsBus(String isBus) {
		List<SysFunction> functionList = sysFunctionMapper.queryFunctionListByIsBus(isBus);
		return BaseVo.copyListTo(functionList, SysFunctionDTO.class);
	}

	/**
	 * 检查链接是否存在
	 *
	 * @param id
	 * @param location
	 * @return
	 */
	public List<SysFunctionDTO> checkLocation(Long id, String location) {
		List<SysFunction> function = sysFunctionMapper.checkLocation(id, location);
		if (CollectionUtils.isEmpty(function)) {
			return Collections.emptyList();
		}
		return BaseVo.copyListTo(function, SysFunctionDTO.class);
	}

	/**
	 * 根据菜单ID查询下级菜单
	 *
	 * @param functionId
	 * @return
	 */
	public List<SysFunctionDTO> getListByParentId(Long functionId) {
		if (functionId == null || "".equals(functionId)) {
			logger.info("SysFunctionService.getListByParentId functionId={}", functionId);
			return Collections.emptyList();
		}
		List<SysFunction> SysFunctionList = sysFunctionMapper.getListByParentId(functionId);
		if (CollectionUtils.isEmpty(SysFunctionList))
			return Collections.emptyList();
		return BaseVo.copyListTo(SysFunctionList, SysFunctionDTO.class);
	}

	/**
	 * 根据主键ID删除
	 *
	 * @param id
	 */
	public void deleteByPrimaryKey(Long id) {
		sysFunctionMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 根据菜单类型查询所有功能类型菜单
	 *
	 * @param funType
	 * @return
	 */
	public List<SysFunctionDTO> queryNodeTypeListByFunType(String funType) {
		List<SysFunction> functionList = sysFunctionMapper.queryNodeTypeListByFunType(funType);
		return BaseVo.copyListTo(functionList, SysFunctionDTO.class);
	}

	/**
	 * 获取业务类型
	 *
	 * @return
	 */
	public List<SysBusinessTypeDTO> queryBusinessList(String busPar) {
		List<SysBusinessType> businessTypeList = sysBusinessTypeMapper.getListByBusPar(busPar);
		return BaseVo.copyListTo(businessTypeList, SysBusinessTypeDTO.class);
	}

	/**
	 * 根据id查询业务类型
	 *
	 * @param id
	 * @return
	 */
	public SysBusinessTypeDTO findBusinessById(Long id) {
		if (id == null) {
			logger.info("sysFunctionService.findBusinessById error:id={}", id);
			return null;
		}
		SysBusinessType businessType = sysBusinessTypeMapper.getByPrimaryKey(id);
		if (businessType == null)
			return null;
		return businessType.copyTo(SysBusinessTypeDTO.class);
	}

	public List<SysFunctionDTO> findByFunTypeList(String funType) {
		List<SysFunction> functionList = sysFunctionMapper.findByFunTypeList(funType);
		return BaseVo.copyListTo(functionList, SysFunctionDTO.class);
	}

	public List<SysFunctionDTO> findAccessList(String channelCode) {
		List<SysFunction> functionList = sysFunctionMapper.findAccessList(channelCode);
		return BaseVo.copyListTo(functionList, SysFunctionDTO.class);
	}

	public int updateFunOrder(Map<String, Object> map) {
		if ("add".equals(map.get("type"))) {
			return sysFunctionMapper.updateFunOrderAdd(map);
		}
		if ("updateSmall".equals(map.get("type"))) {
			return sysFunctionMapper.updateFunOrderSmall(map);
		}
		if ("updateBig".equals(map.get("type"))) {
			return sysFunctionMapper.updateFunOrderBig(map);
		}
		return 0;
	}

	/**
	 * 根据菜单链接查询菜单
	 *
	 * @param location
	 * @return
	 */
	public List<SysFunctionDTO> findByLocation(String location) {
		List<SysFunction> functionList = sysFunctionMapper.findByLocation(location);
		return BaseVo.copyListTo(functionList, SysFunctionDTO.class);
	}

	/**
	 * 根据菜单id获取业务类型
	 *
	 * @param funId
	 * @return
	 */
	public SysBusinessTypeDTO findBusinessByFunId(Long funId) {
		SysFunction sysFunction = sysFunctionMapper.getByPrimaryKey(funId);
		if (null != sysFunction) {
			Long busId = sysFunction.getBusinessId();
			SysBusinessType SysBusinessType = sysBusinessTypeMapper.getByPrimaryKey(busId);
			if(SysBusinessType==null)
				return null;
			return SysBusinessType.copyTo(SysBusinessTypeDTO.class);
		}
		return null;
	}

	/**
	 * 根据用户和菜单类型查询
	 *
	 * @param funType
	 * @param sysUser
	 * @return
	 */
	public List<SysFunctionDTO> findByFunTypeAndUserList(String funType, SysUserDTO sysUser) {
		List<SysFunction> functionList;
		if ("1".equals(sysUser.getUserType())) {
			functionList = sysFunctionMapper.findAdminByFunTypeList(funType);
		} else {
			List<SysRole> roleList = sysRoleMapper.queryRoleByUserId(String.valueOf(sysUser.getId()));
			List<String> ids1 = roleList.stream().map(role -> role.getId()).collect(Collectors.toList());
			String roleIds = "'" + StringUtils.join(ids1, "','") + "'";
			functionList = sysFunctionMapper.findByFunTypeAndUserList(roleIds, funType);
		}
		return BaseVo.copyListTo(functionList, SysFunctionDTO.class);
	}

	/**
	 * 修改菜单所属节点
	 *
	 * @param map
	 * @return
	 */
	public int updateFunNode(Map<String, Object> map) {
		return sysFunctionMapper.updateFunNode(map);
	}

	/**
	 * 根据父节点查询最大排序值
	 *
	 * @param parentId
	 * @return
	 */
	public int findMaxFunOrder(Long parentId) {
		return sysFunctionMapper.findMaxFunOrder(parentId);
	}

	/**
	 * 根据业务类型编号查询菜单
	 *
	 * @param number
	 * @return
	 */
	public SysFunctionDTO findFunctionByBussiness(String number) {
		SysFunction sysFunction = sysFunctionMapper.findFunctionByBussiness(number);
		if (sysFunction == null)
			return null;
		return sysFunction.copyTo(SysFunctionDTO.class);
	}

	public List<SysFunctionDTO> selectByOrgIdAndSystemId(Long systemId, Long orgId) {
		List<SysFunction> functionList = sysFunctionMapper.selectByOrgIdAndSystemId(systemId, orgId);
		return BaseVo.copyListTo(functionList, SysFunctionDTO.class);
	}


	public List<SysFunctionDTO> selectByUserIdAndSystemId(String channelCode, Long userId) {
		List<SysFunction> functionList = sysFunctionMapper.selectByUserIdAndSystemId(channelCode, userId);
		return BaseVo.copyListTo(functionList, SysFunctionDTO.class);
	}

	/**
	 * 查询当前用户所拥有的功能(不包含管理系统)
	 *
	 * @param roleId
	 * @return
	 */
	public List<SysFunctionDTO> getFunctionList(List<String> roleIds) {
		List<SysFunction> funcList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(roleIds)){
			funcList = sysFunctionMapper.queryFunListByRoleId(roleIds);
		}
		return BaseVo.copyListTo(funcList, SysFunctionDTO.class);
  }

	/**
	 * 根据菜单标题查询菜单
	 *
	 * @param location
	 * @return
	 */
	public List<SysFunctionDTO> findByFunctitle(String title) {
		List<SysFunction> functionList = sysFunctionMapper.findByFunctitle(title);
		return BaseVo.copyListTo(functionList, SysFunctionDTO.class);
	}

	/**
	 * 查询组名
	 *
	 * @param location
	 * @return
	 */
	public List<SysFunctionDTO> selectByGroupName() {
		List<SysFunction> functionList = sysFunctionMapper.selectByGroupName();
		if (functionList == null)
				return  new ArrayList<>();
		if (functionList.size()==0)
				return new ArrayList<>();
		return BaseVo.copyListTo(functionList, SysFunctionDTO.class);
	}

	public int deleteByFunType(String funType) {
		return sysFunctionMapper.deleteByFunType(funType);
	}
	
	public List<SysFunctionDTO> findFunctionInUuid(List<String> uuids) {
		return sysFunctionMapper.findFunctionInUuid(uuids);
	}

    public List<SysFunctionDTO> queryFunListByRoleIdForInnerMenu(List<String> roleIds) {
		List<SysFunction> funcList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(roleIds)){
			funcList = sysFunctionMapper.queryFunListByRoleId(roleIds);
		}
		return BaseVo.copyListTo(funcList, SysFunctionDTO.class);
    }
    
    public List<SysFunctionDTO> getFunLevelFunctionList() {
		List<SysFunction> funcList = new ArrayList<>();
		funcList = sysFunctionMapper.getFunLevelFunctionList();
		return BaseVo.copyListTo(funcList, SysFunctionDTO.class);
    }
}
