package com.wangxinenpu.springbootdemo.service.facade;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysFunctionDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.*;
import star.bizbase.exception.BizRuleException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统功能服务接口
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("all")
public interface SysFunctionFacade {
	
	/**
	 * 根据主键查询功能信息
	 * @param id
	 * @return
	 */
	public SysFunctionDTO getByPrimaryKey(Long id);

	/**
	 * 查询角用户对应色所拥有的功能
	 * 
	 * @param roleIds
	 * @return
	 */
	public List<SysFunctionDTO> queryFunctionListByRoleId(SysUserDTO sysUser);
	
	/**
	 * 根据角色类型查询可授权菜单
	 * @param roleType
	 * @return
	 */
	List<SysFunctionDTO> findTreesByRoleType(String roleType);
	
	/**
	 * 查询所有菜单
	 * @return
	 */
	List<SysFunctionDTO> queryAllFunctionList();
	
	/**
	 * 根据是否接入流程查询所有菜单
	 * @return
	 */
	List<SysFunctionYthDTO> queryFunctionListByIsBus(String isBus, String userId, String funType);

	//将pagedata转化为SysFunctionbean
	SysFunctionDTO getSysFunctionBean(Map<String, Object> map);

	/**
	 * 根据id和链接查询是否存在
	 * @param id
	 * @param location
	 * @return
	 */
	boolean checkLocation(Long id, String location, String funType);

	/**
	 * 新增菜单
	 * @param sysFunction
	 * @return
	 * @throws BizRuleException
	 */
	public int addSysFunction(SysFunctionDTO sysFunction)throws BizRuleException;


	/**
	 * 新增菜单并返回新增id
	 * @param sysFunction
	 * @return functionId
	 * @throws BizRuleException
	 */
	public Long addSysFunctionReturnId(SysFunctionDTO sysFunction) throws BizRuleException;
	/**
	 * 修改菜单
	 * @param sysFunction
	 * @return
	 * @throws BizRuleException
	 */
	public int updateSysFunction(SysFunctionDTO sysFunction)throws BizRuleException;

	/**
	 * 菜单删除
	 * @param sysFunction
	 */
	public void deleteMenu(SysFunctionDTO sysFunction) throws BizRuleException;

	/**
	 * 查询角色拥有的功能
	 * @param roleId
	 * @return
	 */
	List<SysRoleFunctionDTO> findByRoleId(String roleId);

	/**
	 * 根据菜单类型查询所有功能类型菜单
	 * @param funType
	 * @return
	 */
	List<SysFunctionDTO> queryNodeTypeListByFunType(String funType);

	/**
	 * 根据渠道、区域、大类获取业务类型
	 * @return
	 */
	List<SysBusinessTypeDTO> queryBusinessList(String busPar);

	/**
	 * 根据id查询业务类型
	 * @param id
	 * @return
	 */
	public SysBusinessTypeDTO findBusinessById(Long id);

	/**
	 * 根据菜单类型查询菜单
	 * @param funType
	 * @return
	 */
	List<SysFunctionDTO> findByFunTypeList(String funType);


	/**
	 * 根据系统标识码查询菜单
	 * @param channelCode
	 * @return
	 */
	List<SysFunctionDTO> findAccessList(String channelCode);

	/**
	 * 根据用户和菜单类型查询
	 * @param funType
	 * @param sysUser
	 * @return
	 */
	List<SysFunctionDTO> findByFunTypeAndUserList(String funType, SysUserDTO sysUser);

	/**
	 *
	 * 根据id查询是否有下级菜单
	 * @param id
	 * @return
	 */
	List<SysFunctionDTO> getListByParentId(Long id);

	/**
	 * 根据当前排序值和父节点修改排序
	 * @param map
	 * @return
	 * @throws BizRuleException
	 */
	public int updateFunOrder(Map<String, Object> map)throws BizRuleException;

	/**
	 * 根据菜单链接查询菜单
	 * @param location
	 * @return
	 */
	List<SysFunctionDTO> findByLocation(String location);

	/**
	 * 根据菜单id获取业务类型
	 * @param funId
	 * @return
	 */
	SysBusinessTypeDTO findBusinessByFunId(Long funId);

	/**
	 * 根据参数获取菜单列表
	 * @param searchMap
	 * @return
	 */
	List<SysFunctionDTO> getListByWhere(HashMap<String, Object> searchMap);


	/**
	 * 根据参数获取菜单列表
	 * @param searchMap
	 * @return
	 */
	List<SysFunctionDTO> queryRbFlagListById(HashMap<String, Object> searchMap);

	List<SysFunctionDTO> queryRbFlagResultById(String functionId);

	SysFunctionDTO queryRbFlagById(Long functionId);


	/**
	 * 修改菜单所属节点和排序
	 * @param map
	 * @return
	 * @throws BizRuleException
	 */
	public int updateFunNode(Map<String, Object> map)throws BizRuleException;

	/**
	 * 根据父节点查询最大排序值
	 * @param parentId
	 * @return
	 */
	public int findMaxFunOrder(Long parentId);

	/**
	 * 根据业务类型编号查询菜单
	 * @param number
	 * @return
	 */
	SysFunctionDTO findFunctionByBussiness(String number);

	/**
	 * 根据条件查询角色权限
	 * @param searchMap
	 * @return
	 */
	List<SysRoleFunctionDTO> getRoleFunListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 根据菜单ID查询授权
	 * @param functionId
	 * @return
	 */
	List<SysRoleFunctionDTO> findByFunctionId(Long functionId);

	/**
	 * 修改角色授权，全选半选
	 * @param roleFunction
	 * @return
	 * @throws BizRuleException
	 */
	public int updateRoleFunction(SysRoleFunctionDTO roleFunction)throws BizRuleException;


	/**
	 * 根据机构Id和系统id查询菜单
	 * @param systemId
	 * @param orgId
	 * @return
	 * @throws BizRuleException
	 */
	List<SysFunctionDTO> selectByOrgIdAndSystemId(Long systemId, Long orgId);


	/**
	 * 根据渠道标识码和用户id去查询菜单
	 * @param channelCode
	 * @param userId
	 * @return
	 * @throws BizRuleException
	 */
	public List<SysFunctionDTO> selectByUserIdAndSystemId(String channelCode, Long userId);

	/**
	 * 查询角用户对应色所拥有的功能
	 *
	 * @param roleIds
	 * @return
	 */
	public List<SysFunctionDTO> queryFunListByRoleId(List<String> roleIds);

	/**
	 * 根据菜单标题查询菜单
	 * @param title
	 * @return
	 */
	List<SysFunctionDTO> findByFunctitle(String title);


	/**
	 * 查询组名
	 *9dia
	 * @param location
	 * @return
	 */
	public List<SysFunctionDTO> selectByGroupName();


	/**
	 * 查询组名
	 *
	 * @param location
	 * @return
	 */
	public int deleteByFunType(String funType);

	/**
	 * 根据天正菜单id集合查询我们的菜单id
	 * @param uuids
	 * @return
	 */
	public List<SysFunctionDTO> findFunctionInUuid(List<String> uuids);

    Integer sysFunctionSetPermissionForOrgs(SysFunctionSetPermissionForOrgsVO sysFunctionSetPermissionForOrgsVO);

	PermissTreeByFunctionIdVO getPermissTreeByFunctionId(Long functionID);

	List<SysFunctionDTO> queryFunListByRoleIdForInnerMenu(List<String> roleIds);


    List<SysFunctionDTO> tranList();

	PermissTreeByFunctionIdVO sysFunctionTsysFunctionScopeTreeForOrgseeForOrgs(Long userId, Long functionId, String belongRoleId);

    PermissTreeByFunctionIdVO sysFunctionTsysFunctionScopeTreeForOrgseeForOrgs(Long userId, String functionIds, String belongRoleId);

    Integer sysFunctionTreeForOrgs(SaveSysFunctionScopeForOrgsSaveVO saveSysFunctionScopeForOrgsSaveVO);

	List<SysOrgDTO> getFunctionScopeOrgsByFunctionIdandUserId(Long userId, Long functionId) throws Exception;
	
	List<SysFunctionDTO> getFunLevelFunctionList();

    Integer batchSaveSysFunctionScopeForOrgs(BatchSaveSysFunctionScopeForOrgsSaveVO saveSysFunctionScopeForOrgsSaveVO);
}
