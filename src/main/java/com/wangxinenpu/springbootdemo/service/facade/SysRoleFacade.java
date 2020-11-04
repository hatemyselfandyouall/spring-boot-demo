package com.wangxinenpu.springbootdemo.service.facade;

import com.wangxinenpu.springbootdemo.dataobject.vo.*;

import star.bizbase.exception.BizRuleException;

import java.util.HashMap;
import java.util.List;

/**
 * 系统角色服务接口
 * 
 * @author Administrator
 *
 */
public interface SysRoleFacade {
	/**
	 * 新增角色po信息
	 * 
	 * @param po
	 * @return
	 * @throws BizRuleException
	 */
	public int addSysRole(SysRoleDTO po) throws BizRuleException;

	/**
	 * 根据主键得到系统角色表记录
	 * 
	 * @param id
	 * @return
	 */
	public SysRoleDTO getByPrimaryKey(String id);

	/**
	 * 更新po信息
	 * 
	 * @param po
	 * @return
	 */
	public int updatepo(SysRoleDTO po);

	/**
	 * 根据参数查询 获取角色列表 带分页
	 * 
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysRoleDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size);

	/**
	 * 根据参数查询角色列表
	 * 
	 * @param searchMap
	 * @return
	 */
	public List<SysRoleDTO> getListByWhere(HashMap<String, Object> searchMap);

	List<SysRoleDTO> findRoleInRoleCode(List<String> roleCodes);


	/**
	 * 根据参数查询系统角色的记录数量
	 * 
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap);
	
	/**
	 * 校验用角色名是否重复
	 * @param roleName
	 * @param roleId
	 * @return
	 */
    boolean checkRoleName(String roleName, String roleId, Long orgId);

    /**
     * 根据角色id删除
     * @param id
     */
    public void deleteByPrimaryKey(String id)throws BizRuleException;

    /**
     * 查询当前用户拥有的角色
     * @param userId
     * @return
     */
    public List<SysRoleDTO> queryRoleByUserId(String userId);

    /**
     * 授权
     * @param roleId
     * @param list
     */
    public void deleteRoleFunctionRefAndAddNewRef(String roleId, List<SysRoleFunctionDTO> list);

	/**
	 * 批量给角色授权
	 * @param roleIdList
	 * @param list
	 */
	public void patchAddRoleFunctionRefAndAddNewRef(List<String> roleIdList, List<SysRoleFunctionDTO> list);

    /**
     * 根据系统类型和机构获取角色
     * @param funType
     * @return
     */
    public List<SysRoleDTO> findRoleByFunType(String funType, Long orgId);
    
    /**
     * 批量新增角色
     * @param list
     */
    public void insertByBatch(List<SysRoleDTO> list);

	/**
	 * 根据角色id删除关联关系
	 * @param roleId
	 */
	public void deleteRelationByRoleId(String roleId);


	/**
	 * 保存用户角色关系
	 */
	void saveUserRole(List<SysUserRoleDTO> sysUserRoleDTOS);

	void roleAdd(int tag);
}
