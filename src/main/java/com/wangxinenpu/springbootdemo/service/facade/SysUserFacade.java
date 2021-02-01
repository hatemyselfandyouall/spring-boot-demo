package com.wangxinenpu.springbootdemo.service.facade;


import com.wangxinenpu.springbootdemo.dataobject.vo.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.response.SysUserDisplayNameRespDTO;
import star.bizbase.exception.BizRuleException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户服务接口
 *
 * @Author:xhy
 * @since：2019年3月19日 下午1:41:37
 * @return:
 */
public interface SysUserFacade {
	/**
	 * 新增用户po信息
	 *
	 * @param po
	 * @return
	 * @throws BizRuleException
	 */
	public Long addSysUser(SysUserDTO po) throws BizRuleException;

	public Long synceSaveSysUser(SysUserDTO sysUserDTO) throws BizRuleException;

	public Long synceDeleteSysUser(String ythUserId) throws BizRuleException;

	/**
	 * 根据主键得到系统用户表记录
	 *
	 * @param id
	 * @return
	 */
	public SysUserDTO getCacheByPrimaryKey(Long id);

    /**
     * 根据id 清除缓存
     *
     * @param id
     * @return
     */
    public void deleteCache(Long id);

	/**
	 *
	 * @author xhy
	 * @since:2019年3月22日下午4:16:45
	 * @param id
	 * @return
	 */
	public SysUserDTO getByPrimaryKey(Long id);

	/**
	 * 更新po信息
	 *
	 * @param po
	 * @return
	 */
	public int updatepo(SysUserDTO po);


	Long synceUpdateSysUser(SysUserDTO SysUserDTO);

	/**
	 * 根据参数查询 获取用户列表 带分页
	 *
	 * @author xhy
	 * @since:2019年3月21日下午2:23:30
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysUserDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size);

	/**
	 * 根据参数查询用户列表
	 *
	 * @author xhy
	 * @since:2019年3月21日下午2:23:52
	 * @param searchMap
	 * @return
	 */
	public List<SysUserDTO> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 根据参数 获取买家的系统用户列表 以userId为主
	 *
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap);

	/**
	 * 根据登录名查找用户
	 *
	 * @param loginName
	 * @return
	 */
	public SysUserDTO getByLoginName(String loginName);

	/**
	 * 重置用户密码，重置密码为000000
	 *
	 * @param userId
	 */
	int resetPassWD(Long userId) throws BizRuleException;

	/**
	 * 注销用户
	 *
	 * @param userId
	 */
	void logoutUser(Long userId) throws BizRuleException;

	/**
	 * 解锁用户
	 *
	 * @param userId
	 */
	void unlockUser(Long userId) throws BizRuleException;

	/**
	 * 锁定用户
	 *
	 * @param userId
	 */
	void lockUser(Long userId) throws BizRuleException;

	/**
	 * 校验用户登录名是否重复
	 *
	 * @param logonName
	 * @param userId
	 * @return
	 */
	boolean checkLogonName(String logonName, Long userId);

	/**
	 * 查询用户绑定的角色
	 *
	 * @param userId
	 * @return
	 */
	List<SysUserRoleDTO> queryUserRole(Long userId) throws BizRuleException;
	
	List<SysUserRoleDTO> queryUserRole(Long userId, Long orgId) throws BizRuleException;

	/**
	 * 查询用户绑定的区域
	 *
	 * @param userId
	 * @return
	 */
	List<SysUserAreaDTO> queryUserArea(Long userId) throws BizRuleException;

	/**
	 * 查询机构下的用户
	 *
	 * @param orgId
	 * @return
	 */
	List<SysUserOrgDTO> queryUserOrgByOrgId(Long orgId);

	/**
	 * 查询用户关联机构
	 *
	 * @param userId
	 * @return
	 */
	List<SysUserOrgDTO> queryUserOrg(Long userId);

	/**
	 * 查询用户关联机构下的用户
	 * @param userId
	 * @return
	 */
	List<SysUserOrgDTO> queryUserOrgByOrgIds(Long userId);

	/**
     * 根据区域查询区域下的机构
     * @param areaId
     * @return
     */
    List<SysOrgDTO> queryOrgNodes(String areaId, SysUserDTO sysUser);

    /**
     * 查询机构列表树
     * @param areaId
     * @param sysUser
     * @return
     */
    List<SysOrgDTO> queryRelationOrgNodes(String areaId, SysUserDTO sysUser);

    /**
     * 获取全部区域
     * @return
     */
    List<SysAreaDTO> queryAreaNodes(SysUserDTO sysUser);

    /**
     * 根据机构ID获取角色
     * @return
     */
    List<SysRoleDTO> findByRoleType(String orgId);

    /**
     * 保存和修改用户
     * @param sysUser
     * @param map
     * @throws BizRuleException
     */
    void saveUser(SysUserDTO sysUser, Map<String, List<String>> map) throws BizRuleException;

    /**
     * 根据菜单ID查询用户
     * @param functionId
     * @return
     */
    public List<SysUserDTO> getUserListByFunctionId(Long functionId);

    /**
     * 判断用户请求路径权限
     * @param url
     * @param userId
     * @return
     */
    public boolean getAuthByUrlAndUserId(String url, Long userId);

    /**
     * 根据角色查询关联用户
     * @param roleId
     * @return
     * @throws BizRuleException
     */
    List<SysUserRoleDTO> findUserByRoleId(String roleId) throws BizRuleException;

    /**
     * 根据id集合查询用户list
     * @param ids
     * @return
     */
    public List<SysUserDTO> getListByIds(String ids);

    /**
     * 根据用户ID获取操作员姓名，为空则返回id
     * @param id
     * @return
     */
    public String findUserNameById(Long id);

    /**
     * 根据显示名称查询，多个或没有原值返回，有则返回id
     * @param displayName
     * @return
     */
    public String findUserNameById(String displayName)throws BizRuleException;

    /**
	 * 根据外部天正id 获取sysuser对象信息
	 * @param ythUserId
	 * @return
	 */
	public SysUserDTO getCacheSysUserByYthUserId(String ythUserId);

	/**
	 * 根据流程获取可指派人员
	 * @param map
	 * @return
	 */
	public List<SysUserDTO> getUserListByProId(HashMap<String, Object> map);

	/**
	 * 查询用户关联区域（医保用）
	 * @param userId
	 * @return
	 */
	public List<SysAreaDTO> queryAreaListByUserId(Long userId);

	/**
	 * 根据用户ID和行政区编码查询是否有权限
	 * @param userId
	 * @param areaId
	 * @return
	 */
	public boolean queryPowerByUserIdAndAreaCode(Long userId, String areaId, String sysNumber)throws BizRuleException;


	/**
	 * 根据系统菜单Id查询用户
	 * @param functionId
	 * @return
	 */
	public List<SysUserDTO> selectByFunction(Long functionId, String orgenterCode);

    /**
     * 根据userId 修改密码
     * @param userId
     * @param password
     * @return
     */
    public boolean updatePSDByUserId(Long userId, String password);


    /**
     * 根据电话查询用户
     * @param phone
     * @return
     */
    public List<SysUserDTO> findUserByPhone(String phone);


	public void insertByBatch(List<SysUserRoleDTO> list);

	/**
	 * 根据角色和科室查询用户
	 * @param department
	 * @return
	 */
	public List<SysUserDTO> getUserListByDepartmentId(String department, String roleType);

	/**
	 * 根据科室为空和用户类型查询用户
	 * @param userType
	 * @return
	 */
	public List<SysUserDTO> queryUserListByUserType(Long orgId, String userType);

	public List<SysUserDTO> getAllList();
	
	/**
	 * 通过用户idlist 获取用户信息列表
	 * @user xxh
	 * @since 2020年6月10日下午5:23:47
	 */
	public List<SysUserDisplayNameRespDTO> getSysUserDisplayNameRListByIdList(List<Long> idlist);


}
