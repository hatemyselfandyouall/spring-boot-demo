package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysUserDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysRoleFunctionMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.SysUserEmpowerMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.SysUserMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysRoleFunction;
import com.wangxinenpu.springbootdemo.dataobject.po.SysUser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import star.vo.BaseVo;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统用户service
 * @author xhy
 * @since:2019年3月21日下午2:15:50
 */
@Service
public class SysUserService {
	private static Logger logger = LoggerFactory.getLogger(SysUserService.class);
	@Resource
	private SysUserMapper sysUserMapper;
	@Resource
	private SysUserEmpowerMapper sysUserEmpowerMapper;
	@Resource
	private SysRoleFunctionMapper sysRoleFunctionMapper;

	/**
	 * 新增用户po信息
	 * @param dto
	 * @return
	 */
	public Long addSysUser(SysUserDTO dto){
		if(dto == null) {
			logger.info("sysuserService.addSysUser dto={}",dto);
			return 0L;
		}
		SysUser user = dto.copyTo(SysUser.class);
		sysUserMapper.insertSysUser(user);
		return user.getId();
	}

	/**
	 *  根据主键得到系统用户表记录
	 * @param id
	 * @return
	 */
	public SysUserDTO getByPrimaryKey(Long id){
		if(id == null) {
			logger.info("sysuserService.getByPrimaryKey error:id={}",id);
			return null;
		}
		SysUser po = sysUserMapper.getByPrimaryKey(id);
		if(po==null) return null;
		return po.copyTo(SysUserDTO.class);
	}

	/**
	 * 更新po信息
	 * @param dto
	 * @return
	 */
	public int updatepo(SysUserDTO dto){
		if(dto == null || dto.getId() == null) {
			logger.info("sysuserService.updatepo dto={}",dto);
			return 0;
		}
		return sysUserMapper.updateSysUser(dto.copyTo(SysUser.class));//object转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 删除po信息
	 * @param dto
	 * @return
	 */
	public int deletepo(SysUserDTO dto){
		if(dto == null || dto.getId() == null) {
			logger.info("sysuserService.deletepo dto={}",dto);
			return 0;
		}
		return sysUserMapper.deleteByUserId(dto.getId());//object转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	/**
	 * 根据参数查询 获取用户列表  带分页
	 * @author xhy
	 * @since:2019年3月21日下午2:23:30
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysUserDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size){
		if(searchMap==null || searchMap.isEmpty() || size<1) {
			logger.info("sysuserService.getListByWhere searchMap={}，start={},size={}",searchMap,start,size);
			return Collections.emptyList();
		}
		List<SysUser> sysUserList = sysUserMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(sysUserList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysUserList, SysUserDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询用户列表 
	 * @author xhy
	 * @since:2019年3月21日下午2:23:52
	 * @param searchMap
	 * @return
	 */
	public List<SysUserDTO> getListByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("sysuserService.getListByWhere searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysUser> sysUserList = sysUserMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(sysUserList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysUserList, SysUserDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数获取系统用户数量
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			return 0;
		}
		return sysUserMapper.getCountByWhere(searchMap);
	}
	
	/**
	 * 根据登录名查找用户
	 * @param loginName
	 * @return
	 */
	public SysUserDTO getByLoginName(String loginName){
		logger.info("sysuserService.getByLoginName loginName={}",loginName);
		SysUser po = sysUserMapper.getByLoginName(loginName);
		if(null == po){
			return null;
		}
		return po.copyTo(SysUserDTO.class);
	}
	
	/**
     * 根据用户名和用户Id查询用户
     * @param loginName
     * @param userId
     * @return
     */
	public SysUserDTO getByLogonNameAndUserIdNot(String loginName, Long userId){
		logger.info("sysuserService.getByLogonNameAndUserIdNot loginName={},userId={}",loginName,userId);
		SysUser po = sysUserMapper.getByLogonNameAndUserIdNot(loginName,userId);
		if(null == po){
			return null;
		}
		return po.copyTo(SysUserDTO.class);
	}
	
	/**
	 * 根据功能ID查询用户
	 * @param functionId
	 * @return
	 */
	public List<SysUserDTO> getUserListByFunctionId(Long functionId) {
		logger.info("sysuserService.getUserListByFunctionId functionId={}",functionId);
		List<SysRoleFunction> roleFunctionList= sysRoleFunctionMapper.findByFunctionId(functionId);
		if(CollectionUtils.isEmpty(roleFunctionList)) return Collections.emptyList();
		List<String> ids1 = roleFunctionList.stream().map(role -> role.getRoleId()).collect(Collectors.toList());
		String roleIds = "'" + StringUtils.join(ids1, "','") + "'";
		List<SysUser> userList = sysUserMapper.queryUserListByRoleIds(roleIds);
		return BaseVo.copyListTo(userList, SysUserDTO.class);
	}
	
	public List<SysUserDTO> getListByIds(String ids) {
		List<SysUser> userList = sysUserMapper.getListByIds(ids);
		return BaseVo.copyListTo(userList, SysUserDTO.class);
	}
	
	
	public List<SysUserDTO> getUserListByProId(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("sysuserService.getListByWhere searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysUser> sysUserList = sysUserMapper.getUserListByProId(searchMap);
		if(CollectionUtils.isEmpty(sysUserList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysUserList, SysUserDTO.class);
	}

	/**
	 * 根据系统菜单Id查询用户
	 * @param functionId
	 * @return
	 */
	public List<SysUserDTO> selectByFunction(Long functionId, String orgenterCode){
		List<SysUser> sysUserList = sysUserMapper.selectByFunction(functionId,orgenterCode);
		if(CollectionUtils.isEmpty(sysUserList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysUserList, SysUserDTO.class);
	}

	/**
	 * 根据userId 修改密码
	 * @param userId
	 * @param password
	 * @return
	 */
	public boolean updatePSDByUserId(Long userId, String password){
		Integer status = sysUserMapper.updatePSDByUserId(userId, password);
		if (status>0){
			return true;
		}else {
			return false;
		}
	}


	/**
	 * 根据电话查询用户
	 * @param phone
	 * @return
	 */
	public List<SysUserDTO> findUserByPhone(String phone){
		List<SysUser> sysUserList = sysUserMapper.findUserByPhone(phone);
		if(CollectionUtils.isEmpty(sysUserList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysUserList, SysUserDTO.class);
	}

	/**
	 * 根据角色和科室查询用户
	 * @param department
	 * @return
	 */
	public List<SysUserDTO> getUserListByDepartmentId(String department, String roleType) {
		logger.info("sysuserService.getUserListByDepartmentId department={},roleType={}",department,roleType);
		List<SysUser> userList = sysUserMapper.queryUserListByDeparment(department,roleType);
		return BaseVo.copyListTo(userList, SysUserDTO.class);
	}

	/**
	 * 根据科室为空和用户类型查询用户
	 * @param userType
	 * @return
	 */
	public List<SysUserDTO> queryUserListByUserType(Long orgId, String userType) {
		logger.info("sysuserService.queryUserListByUserType userType={}orgId={}",userType,orgId);
		List<SysUser> userList = sysUserMapper.queryUserListByUserType(orgId,userType);
		return BaseVo.copyListTo(userList, SysUserDTO.class);
	}

	public List<SysUserDTO> getAllList(){
		List<SysUser> sysUserList = sysUserMapper.getAllList();
		if(CollectionUtils.isEmpty(sysUserList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysUserList, SysUserDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

}
