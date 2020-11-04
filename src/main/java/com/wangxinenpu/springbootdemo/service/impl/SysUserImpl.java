package com.wangxinenpu.springbootdemo.service.impl;

import com.wangxinenpu.springbootdemo.constant.DataBaseEnum;
import com.wangxinenpu.springbootdemo.constant.SysbaseCacheEnum;
import com.wangxinenpu.springbootdemo.dao.mapper.cachemapper.SysUserCacheMapper;
import com.wangxinenpu.springbootdemo.service.facade.SysFunctionFacade;
import com.wangxinenpu.springbootdemo.service.facade.SysOrgFacade;
import com.wangxinenpu.springbootdemo.service.facade.SysUserFacade;
import com.wangxinenpu.springbootdemo.dataobject.po.SysTestBj;
import com.wangxinenpu.springbootdemo.dataobject.po.SysUserHistory;
import com.wangxinenpu.springbootdemo.dataobject.vo.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.response.SysUserDisplayNameRespDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysTestBjMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.SysUserHistoryMapper;
import com.wangxinenpu.springbootdemo.service.service.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import star.bizbase.exception.BizRuleException;
import star.bizbase.util.RuleCheck;
import star.modules.cache.CachesKeyService;
import star.util.StringUtil;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统用户facade服务实现类
 *
 * @Author:xhy
 * @since：2019年3月19日 下午1:46:43
 * @return:
 */
@Service
public class SysUserImpl implements SysUserFacade {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserCacheMapper sysUserCacheMapper;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysUserOrgService sysUserOrgService;
	@Autowired
	private SysUserAreaService sysUserAreaService;
	@Autowired
	private SysOrgService sysOrgService;
	@Autowired
	private SysAreaService sysAreaService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysFunctionFacade sysFunctionFacade;
	@Resource
	private CachesKeyService cachesKeyService;
	@Autowired
	private SysUserEmpowerService sysUserEmpowerService;
	@Resource
	private SysUserHistoryMapper sysUserHistoryMapper;

	@Autowired
	private SysTestBjMapper sysTestBjMapper;

	@Autowired
	private SysOrgFacade sysOrgFacade;
	protected Logger logger = LoggerFactory.getLogger(SysUserImpl.class);

	@Override
	@Transactional
	public Long addSysUser(SysUserDTO po) throws BizRuleException {
		/* 非空校验 */
		RuleCheck.validateByAnnotation(po);
		Long id = sysUserService.addSysUser(po);
		
		SysUserDTO byPrimaryKey = sysUserService.getByPrimaryKey(id);
		
		if (byPrimaryKey!=null) {
			//保存用户机构关系表
			SysUserOrgDTO userOrg = new SysUserOrgDTO();
			userOrg.setUserId(id);
			userOrg.setOrgId(byPrimaryKey.getOrgId());
			userOrg.setAreaId(byPrimaryKey.getAreaId());
			userOrg.setCreateTime(new Date());
			userOrg.setModifyTime(new Date());
			sysUserOrgService.addSysUserOrg(userOrg);
		}
		return id;
	}

	@Override
	public Long synceSaveSysUser(SysUserDTO sysUserDTO) throws BizRuleException {
		//根据一体化id查询用户id
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("ythUserId",sysUserDTO.getYthUserId());
		List<SysUserEmpowerDTO> sysUserEmpowerDTOS = sysUserEmpowerService.getListByWhere(searchMap);
		logger.info("sysUserDTO"+sysUserDTO);
		if(sysUserEmpowerDTOS!=null&&sysUserEmpowerDTOS.size()>0) {
			//找到了是修改
			return synceUpdateSysUserCom(sysUserDTO,sysUserEmpowerDTOS);
		}else {
			return synceSaveSysUserCom(sysUserDTO);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long synceDeleteSysUser(String ythUserId) throws BizRuleException {
		//根据一体化id查询用户id
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("ythUserId",ythUserId);
		List<SysUserEmpowerDTO> sysUserEmpowerDTOS = sysUserEmpowerService.getListByWhere(searchMap);
		Long userId = null;
		if(sysUserEmpowerDTOS!=null&&sysUserEmpowerDTOS.size()>0) {
			String userIdStr = sysUserEmpowerDTOS.get(0).getuserId();
			if (StringUtils.isNotEmpty(userIdStr)) {
				userId = Long.parseLong(userIdStr);
				SysUserDTO sysUser = getByPrimaryKey(userId);
				sysUser.setUserState("3");
				//在历史表添加要删除的用户
				HashMap<String, Object> map = new HashMap();
				logger.info("在历史表添加要删除的用户="+userId);
				map.put("userId",userId);
				List<SysUserEmpowerDTO> empowerList = sysUserEmpowerService.getListByWhere(map,0,9999);
				logger.info("empowerList长度="+empowerList.size());
				SysUserHistory sysUserHistory = new SysUserHistory();
				BeanUtils.copyProperties(sysUser,sysUserHistory);
				if(empowerList != null && empowerList.size()>0){
					logger.info("天正一体化id====="+empowerList.get(0).getythUserId());
					sysUserHistory.setYthUserId(empowerList.get(0).getythUserId());
				}else{
					logger.info("天正一体化id为空，存入空字符串");
					sysUserHistory.setYthUserId("");
				}
				sysUserHistoryMapper.insertSelective(sysUserHistory);
				//删除用户与天正用户的关系
				sysUserEmpowerService.deleteById(userId);
				cachesKeyService.deleteCache(SysbaseCacheEnum.SYSUSER_BY_ID,userId);
				if (!StringUtils.isEmpty(ythUserId)){
					cachesKeyService.deleteCache(SysbaseCacheEnum.GETCACHESYSUSER_YTHUSERID,ythUserId);
				}
				//删除用户
				sysUserService.deletepo(sysUser);
			}
		}
		return userId;
	}

	@Override
	public SysUserDTO getCacheByPrimaryKey(Long id) {
		SysUserDTO user = sysUserCacheMapper.getCacheSysUserById(id);// 实时cache中获取数据
		if(user!=null) {
			SysAreaDTO area = sysAreaService.getByPrimaryKey(user.getAreaId());
			if (null != area) {
				user.setAreaName(area.getAreaName());
			}
		}
		return user;
	}

	@Override
	public SysUserDTO getByPrimaryKey(Long id) {
		SysUserDTO user = sysUserService.getByPrimaryKey(id);// 实时db获取数据
		if(user!=null) {
			SysAreaDTO area = sysAreaService.getByPrimaryKey(user.getAreaId());
			if (null != area) {
				user.setAreaName(area.getAreaName());
			}
		}
		return user;
	}

	@Override
	public int updatepo(SysUserDTO dto) {
		return sysUserService.updatepo(dto);
	}

	@Override
	@Transactional
	public Long synceUpdateSysUser(SysUserDTO sysUserDTO) {
		//根据一体化id查询用户id
		HashMap<String, Object> searchMap = new HashMap<>();
		logger.info("synceUpdateSysUser更新用户一体化id="+sysUserDTO.getYthUserId());
		searchMap.put("ythUserId",sysUserDTO.getYthUserId());
		List<SysUserEmpowerDTO> sysUserEmpowerDTOS = sysUserEmpowerService.getListByWhere(searchMap);
        logger.info("synceUpdateSysUser更新用户sysUserEmpowerDTOS="+sysUserEmpowerDTOS+"sysUserEmpowerDTOS.size()="+sysUserEmpowerDTOS.size());
		if(sysUserEmpowerDTOS!=null&&sysUserEmpowerDTOS.size()>0) {
		    logger.info("更新用户=====");
			return synceUpdateSysUserCom(sysUserDTO,sysUserEmpowerDTOS);
		}else {
            logger.info("添加用户=====");
			//找不到就新增
			return synceSaveSysUserCom(sysUserDTO);
		}
	}

	/**
	 * 分页查询
	 */
	@Override
	public List<SysUserDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size) {
		SysUserDTO currentUser = (SysUserDTO)searchMap.get("user");
		if (!"1".equals(currentUser.getUserType())) {    // 超级管理员
			if ("2".equals(currentUser.getUserType())) {// 系统管理员
//				searchMap.put("areaId", currentUser.getAreaId());
//				searchMap.put("notUserType", 1);
                String userTypes = "3,4";   //查询机构管理员和业务操作员
                searchMap.put("userTypes", userTypes);
			}
			if ("3".equals(currentUser.getUserType())) {// 机构管理员
//				searchMap.put("orgId", currentUser.getOrgId());
				searchMap.put("userType", 4);
//				searchMap.put("notUserType", 1);
//				searchMap.put("notUserType2", 2);
			}
			if ("4".equals(currentUser.getUserType())) {// 业务操作员
				throw new RuntimeException("业务操作人员没权限查看用户列表");
			}
		}
		return sysUserService.getListByWhere(searchMap, start, size);
	}

	@Override
	public List<SysUserDTO> getListByWhere(HashMap<String, Object> searchMap) {
		return sysUserService.getListByWhere(searchMap);
	}

	@Override
	public int getCountByWhere(HashMap<String, Object> searchMap) {
		SysUserDTO currentUser = (SysUserDTO)searchMap.get("user");
		if (!"1".equals(currentUser.getUserType())) {// 超级管理员
			if ("2".equals(currentUser.getUserType())) {// 行政区管理员
//				searchMap.put("areaId", currentUser.getAreaId());
//				searchMap.put("notUserType", 1);
                String userTypes = "3,4";   //查询机构管理员和业务操作员
                searchMap.put("userTypes", userTypes);
			}
			if ("3".equals(currentUser.getUserType())) {// 机构管理员
//				searchMap.put("orgId", currentUser.getOrgId());
				searchMap.put("userType", 4);
//				searchMap.put("notUserType", 1);
//				searchMap.put("notUserType2", 2);
			}
			if ("4".equals(currentUser.getUserType())) {// 业务操作员
				throw new RuntimeException("业务操作人员没权限查看用户列表");
			}
		}
		return sysUserService.getCountByWhere(searchMap);
	}

	@Override
	public SysUserDTO getByLoginName(String loginName) {
		return sysUserService.getByLoginName(loginName);
	}

	/**
	 * 重置密码
	 */
	@Override
	@Transactional
	public int resetPassWD(Long userId) throws BizRuleException {
		SysUserDTO sysUser = getByPrimaryKey(userId);
		if (null == sysUser) {
			throw new BizRuleException("所选用户不存在");
		}
		if ("3".equals(sysUser.getUserState())) {
			throw new BizRuleException("所选用户已注销");
		}
		sysUser.setPasswd(StringUtil.getMD5("000000"));
		int ret = sysUserService.updatepo(sysUser);
		return ret;
	}

	/***
	 * 注销用户
	 *
	 * @param userId
	 */
	@Override
	@Transactional
	public void logoutUser(Long userId) throws BizRuleException {
		SysUserDTO sysUser = getByPrimaryKey(userId);
		if (null == sysUser) {
			throw new BizRuleException("所选用户不存在");
		}
		sysUser.setUserState("3");
		//在历史表添加要删除的用户
		HashMap<String, Object> map = new HashMap();
		logger.info("在历史表添加要删除的用户="+userId);
        map.put("userId",userId);
		List<SysUserEmpowerDTO> empowerList = sysUserEmpowerService.getListByWhere(map,0,9999);
		logger.info("empowerList长度="+empowerList.size());
		SysUserHistory sysUserHistory = new SysUserHistory();
		BeanUtils.copyProperties(sysUser,sysUserHistory);
		if(empowerList != null && empowerList.size()>0){
			logger.info("天正一体化id====="+empowerList.get(0).getythUserId());
			sysUserHistory.setYthUserId(empowerList.get(0).getythUserId());
			if (!StringUtils.isEmpty(empowerList.get(0).getythUserId())){
				cachesKeyService.deleteCache(SysbaseCacheEnum.GETCACHESYSUSER_YTHUSERID,empowerList.get(0).getythUserId());
			}
		}else{
			logger.info("天正一体化id为空，存入空字符串");
			sysUserHistory.setYthUserId("");
		}
		sysUserHistoryMapper.insertSelective(sysUserHistory);
		cachesKeyService.deleteCache(SysbaseCacheEnum.SYSUSER_BY_ID,userId);
        //删除用户与天正用户的关系
        sysUserEmpowerService.deleteById(userId);
        //删除用户
		sysUserService.deletepo(sysUser);
	}

	/***
	 * 解锁用户
	 *
	 * @param userId
	 */
	@Override
	@Transactional
	public void unlockUser(Long userId) throws BizRuleException {
		SysUserDTO sysUser = getByPrimaryKey(userId);
		if (null == sysUser) {
			throw new BizRuleException("所选用户不存在");
		}
		if (!"2".equals(sysUser.getUserState())) {
			throw new BizRuleException("所选用户未被锁定");
		}
		sysUser.setUserState("1");
		sysUser.setUnlockTime(new Date());
		sysUserService.updatepo(sysUser);

	}

	/***
	 * 锁定用户
	 *
	 * @param userId
	 */
	@Override
	@Transactional
	public void lockUser(Long userId) throws BizRuleException {
		SysUserDTO sysUser = getByPrimaryKey(userId);
		if (null == sysUser) {
			throw new BizRuleException("所选用户不存在");
		}
		if (!"1".equals(sysUser.getUserState())) {
			throw new BizRuleException("所选用户不是正常用户");
		}
		sysUser.setUserState("2");
		sysUser.setLockTime(new Date());
		sysUserService.updatepo(sysUser);
	}

	/**
	 * 校验用户登录名是否重复
	 *
	 * @param logonName
	 * @param userId
	 * @return
	 */
	@Override
	public boolean checkLogonName(String logonName, Long userId) {
		SysUserDTO sysuser;
		if (userId == null) {
			sysuser = sysUserService.getByLoginName(logonName);
		} else {
			sysuser = sysUserService.getByLogonNameAndUserIdNot(logonName, userId);
		}
		if (null == sysuser) {// 查询结果为空，则表示没有登录名称重复
			return false;
		}
		return true;
	}

	/**
	 * 校验用户是否有效
	 *
	 * @param userId
	 */
	private boolean checkUser(Long userId) {
		SysUserDTO sysUser = getByPrimaryKey(userId);
		if (sysUser == null) {
			return false;
		}
		if ("3".equals(sysUser.getUserState())) {
			return false;
		}
		return true;
	}

	/**
	 * 查询用户绑定的角色
	 */
	@Override
	public List<SysUserRoleDTO> queryUserRole(Long userId) throws BizRuleException {
		if (!checkUser(userId)) {
			throw new BizRuleException("该用户无效");
		}
		return sysUserRoleService.findByUserId(String.valueOf(userId));
	}
	
	@Override
	public List<SysUserRoleDTO> queryUserRole(Long userId, Long orgId) throws BizRuleException {
		if (!checkUser(userId)) {
			throw new BizRuleException("该用户无效");
		}
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("userId", userId);
		searchMap.put("orgId", orgId);
		return sysUserRoleService.getListByWhere(searchMap);
	}

	/**
	 * 查询用户绑定的区域
	 */
	@Override
	public List<SysUserAreaDTO> queryUserArea(Long userId) throws BizRuleException {
		if (!checkUser(userId)) {
			throw new BizRuleException("该用户无效");
		}
		return sysUserAreaService.findByUserId(String.valueOf(userId));
	}
	
	/**
	 * 查询用户关联机构
	 */
	@Override
	public List<SysUserOrgDTO> queryUserOrg(Long userId){
		List<SysUserOrgDTO> uoList = sysUserOrgService.findByUserId(userId);
		List<SysUserOrgDTO> list = new ArrayList<>();
		if(uoList.size()>0){
			for(SysUserOrgDTO uo : uoList){
				SysOrgDTO org = sysOrgService.getByPrimaryKey(uo.getOrgId());
				if(null !=org){
					uo.setOrgName(org.getOrgName());
				}
				list.add(uo);
			}
		}
		return list;
	}
	
	/**
	 * 查询用户关联机构下的用户
	 */
	@Override
	public List<SysUserOrgDTO> queryUserOrgByOrgIds(Long userId){
		List<SysUserOrgDTO> uoList = sysUserOrgService.queryUserOrgByOrgIds(userId);
		return uoList;
	}
	
	/**
	 * 查询机构下的用户
	 */
	@Override
	public List<SysUserOrgDTO> queryUserOrgByOrgId(Long orgId){
		HashMap<String, Object> searchMap = new HashMap<>();
//		searchMap.put("orgId", orgId);
		List<SysOrgDTO> sysOrgDTOS=sysOrgFacade.getListByAll();
		logger.info(sysOrgDTOS+"");
		List<Long> orgIds=getChildrenOrgIds(sysOrgDTOS,orgId);
		logger.info(orgIds+"");
		searchMap.put("orgIds",orgIds);
		List<SysUserOrgDTO> list = sysUserOrgService.getListByWhere(searchMap);
		return list;
	}

	private List<Long> getChildrenOrgIds(List<SysOrgDTO> sysOrgDTOS, Long orgId) {
//		List<Long> result= Arrays.asList(orgId);
//		List<Long> children=sysOrgDTOS.stream().filter(i->i.getParentId()!=null&&i.getParentId().equals(orgId))
//				.map(SysOrgDTO::getId).collect(Collectors.toList());
//		for(Long child:children){
//			children.addAll(getChildrenOrgIds(sysOrgDTOS,child));
//		}
//		result.addAll(children);
		List<Long> list=new ArrayList<>();
		list.add(orgId);
		return getChildrenIterator(sysOrgDTOS,list);
	}
	private List<Long> getChildrenIterator(List<SysOrgDTO> sysOrgDTOS, List<Long> parents){
		List<Long> children=sysOrgDTOS.stream().filter(i->i.getParentId()!=null&&parents.contains(i.getParentId()))
				.map(SysOrgDTO::getId).collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(children)){
			parents.addAll(getChildrenIterator(sysOrgDTOS,children));
		}
		return parents;
	}

	/**
	 *
	 * 根据区域ID查询机构列表
	 */
	@Override
	public List<SysOrgDTO> queryOrgNodes(String areaId, SysUserDTO sysUser) {
		List<SysOrgDTO> orgTree = new ArrayList<>();
		if ("1".equals(sysUser.getUserType())) {// 超级管理员可以根据区域选择机构
			List<SysOrgDTO> orgList = sysOrgService.findByRegioncode(areaId);
			for (SysOrgDTO sysOrg : orgList) {
				orgTree.add(sysOrg);// 添加自身节点
//				orgTree.addAll(sysOrgService.findByIdpath(sysOrg.getIdpath()+"/"));
			}
		}else{//其他类型只能选择当前用户默认机构
			List<SysOrgDTO> orgList = sysOrgService.findByRegioncode(areaId);
//			SysOrgDTO sysOrg = sysOrgService.getByPrimaryKey(sysUser.getOrgId());
//			orgTree.add(sysOrg);// 添加自身节点
			for (SysOrgDTO org : orgList) {
				orgTree.add(org);
			}

		}
		return orgTree;
	}
	
	@Override
	public List<SysOrgDTO> queryRelationOrgNodes(String areaId, SysUserDTO sysUser) {
		List<SysOrgDTO> orgList = new ArrayList<>();
		if ("1".equals(sysUser.getUserType())) {// 超级管理员可以根据区域选择机构
			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("orgState", "1");
			orgList = sysOrgService.getListByWhere(searchMap);
			
		}else{//其他类型只能选择当前用户默认机构
			orgList = sysOrgService.findByRegioncode(areaId);

		}
		return orgList;
	}

	/**
	 *
	 * 查找区域树结构数据
	 */
	@Override
	public List<SysAreaDTO> queryAreaNodes(SysUserDTO sysUser) {
		List<SysAreaDTO> list = new ArrayList<>();
		if ("1".equals(sysUser.getUserType())||"2".equals(sysUser.getUserType())) {// 超级管理员
			list = sysAreaService.findAll();
		}
//		if ("2".equals(sysUser.getUserType())) {// 区域管理员
//			SysAreaDTO area = sysAreaService.getByPrimaryKey(sysUser.getAreaId());
//			list = sysAreaService.findByIdpath(area.getIdpath());
//		}
		if ("3".equals(sysUser.getUserType())) {// 机构管理员不查询所属行政区，默认跟随自身机构
			SysAreaDTO sysArea3 = sysAreaService.getByPrimaryKey(sysUser.getAreaId());
			list.add(sysArea3);
			if(StringUtil.isNotEmpty(sysArea3.getParentId())){
				SysAreaDTO sysArea2 = sysAreaService.getByPrimaryKey(Long.parseLong(sysArea3.getParentId()));
				list.add(sysArea2);
				if(StringUtil.isNotEmpty(sysArea2.getParentId())){
					SysAreaDTO sysArea1 = sysAreaService.getByPrimaryKey(Long.parseLong(sysArea2.getParentId()));
					list.add(sysArea1);
				}
			}
		}
		return list;
	}

	/**
	 * 根据用户类型查询角色列表
	 */
	@Override
	public List<SysRoleDTO> findByRoleType(String orgId) {
		return sysRoleService.queryByOrgId(Long.valueOf(orgId));
	}

	/**
	 * 用户保存和修改
	 */
	@Override
	@Transactional
	public void saveUser(SysUserDTO sysUser, Map<String, List<String>> map) throws BizRuleException {
		Long userId = null;
		// 如果是更新操作，将原密码查询到新的更新对象中
		if (sysUser.getId() != null) {
			userId = sysUser.getId();
			SysUserDTO temUser = sysUserService.getByPrimaryKey(userId);
			if (temUser == null) {
				throw new BizRuleException("当前用户不存在");
			}
			if (!temUser.getPasswd().equals(sysUser.getPasswd())) {
				 // 密码有更新，需要重新加密
				 sysUser.setPasswd(StringUtil.getMD5(sysUser.getPasswd()));
			}
			//设置日志信息
			sysUser.putLogBaseVo(DataBaseEnum.SYSBASE.getVal(), sysUser.getOpseno());
			//修改用户
			sysUserService.updatepo(sysUser);
		} else {
			sysUser.setPasswd(StringUtil.getMD5(sysUser.getPasswd()));
			//生成主键ID
			String uuid = UUID.randomUUID().toString();
			String code = uuid.replace("-", "");
			sysUser.setCode(code);
			sysUser.setUserState("1");
			//保存用户
			userId = sysUserService.addSysUser(sysUser);
		}
		
		//保存修改一体化用户id
		String ythUserId = sysUser.getYthUserId();
		if(StringUtils.isNotEmpty(ythUserId)){
			logger.info("saveUser关联一体化user={},ythUserId={}"+userId,ythUserId);
			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("ythUserId", ythUserId);
			searchMap.put("userId", userId);
			List<SysUserEmpowerDTO> userEmpList=sysUserEmpowerService.getListByWhere(searchMap);
			logger.info("saveUser列表数量"+userEmpList.size());
			if(userEmpList.size()>0){
				SysUserEmpowerDTO userEmp = userEmpList.get(0);
				userEmp.setythUserId(ythUserId);
				sysUserEmpowerService.updatepo(userEmp);
			}else{
				sysUserEmpowerService.deleteById(userId);
				logger.info("新增关联一体化user={},ythUserId={}"+userId,ythUserId);
				SysUserEmpowerDTO userEmp = new SysUserEmpowerDTO();
				userEmp.setuserId(userId.toString());
				userEmp.setythUserId(ythUserId);
				sysUserEmpowerService.addSysUserEmpower(userEmp);
			}
		}else{
			//删除一体化ID
			logger.info("删除一体化ID="+userId);
			sysUserEmpowerService.deleteById(userId);
		}

		//保存用户角色关系 sys_user_role
		SysUserRoleDTO sysUserRoleDTO = new SysUserRoleDTO();
		sysUserRoleDTO.setUserId(userId.toString());
		sysUserRoleService.deleteByUserId(sysUserRoleDTO);
		List<SysUserRoleDTO> list = sysUser.getSysUserRoleList();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setUserId(userId.toString());
        }
        if (list.size()!=0){
			sysUserRoleService.insertByBatch(list);
		}

        //保存关联机构 sys_user_org
  		SysUserOrgDTO sysUserOrgDTO = new SysUserOrgDTO();
  		sysUserOrgDTO.setUserId(userId);
  		sysUserOrgService.deleteByUserId(userId);
  		List<SysUserOrgDTO> orgList = sysUser.getSysUserOrgList();
  		for(SysUserOrgDTO userOrg : orgList){
  			userOrg.setUserId(userId);
  			sysUserOrgService.addSysUserOrg(userOrg);
  		}

        //保存添加打钩的区域 sys_user_area
        SysUserAreaDTO sysUserAreaDTO = new SysUserAreaDTO();
        sysUserAreaDTO.setUserId(userId.toString());
        sysUserAreaService.deleteByUserId(sysUserAreaDTO);
        List<SysUserAreaDTO> areaAddList = new ArrayList<>();
        if (map.get("addAreaIds").size() > 0) {
            for (String areaId : map.get("addAreaIds")) {
            	SysUserAreaDTO sysUserArea = new SysUserAreaDTO();
                sysUserArea.setUserId(userId.toString());
                sysUserArea.setAreaId(Long.parseLong(areaId));
                areaAddList.add(sysUserArea);
            }
            sysUserAreaService.insertByBatch(areaAddList);
        }

        //删除用户缓存对象
        cachesKeyService.deleteCache(SysbaseCacheEnum.SYSUSER_BY_ID, userId);
	}

	@Override
	public List<SysUserDTO> getUserListByFunctionId(Long functionId) {
		return sysUserService.getUserListByFunctionId(functionId);
	}

    public void deleteCache(Long id){
       sysUserCacheMapper.deleteCache(id);
    }


	@Override
	public boolean getAuthByUrlAndUserId(String uri, Long userId) {
//		if(StringUtil.isEmpty(uri) || null ==userId) return false;
//		SysUserDTO sysUser = sysUserService.getByPrimaryKey(userId);
//		if(null != sysUser){
//			if("1".equals(sysUser.getUserType())){//超级管理员
//				return true;
//			}
//			List<SysFunctionDTO> functionList =sysFunctionFacade.queryFunctionListByRoleId(sysUser);
//			if(functionList.size()>0){
//				for(SysFunctionDTO fun : functionList){
//					if(uri.equals(fun.getLocation())){
//						return true;
//					}
//				}
//			}
//		}
//		return false;
		return true;
	}

	@Override
	public List<SysUserRoleDTO> findUserByRoleId(String roleId) throws BizRuleException {
		return sysUserRoleService.findByRoleId(roleId);
	}

	@Override
	public List<SysUserDTO> getListByIds(String ids) {
		return sysUserService.getListByIds(ids);
	}

	@Override
	public String findUserNameById(Long id) {
		logger.info("Long id="+id);
		SysUserDTO user = sysUserService.getByPrimaryKey(id);
		if(null != user){
			return user.getDisplayName();
		}else{
			return id.toString();
		}
	}

	@Override
	public SysUserDTO getCacheSysUserByYthUserId(String ythUserId) {
		return sysUserCacheMapper.getCacheSysUserByYthUserId(ythUserId);
	}

	@Override
	public List<SysUserDTO> getUserListByProId(HashMap<String, Object> map) {
		return sysUserService.getUserListByProId(map);
	}

	@Override
	public String findUserNameById(String displayName){
		logger.info("String displayName="+displayName);
		if(ObjectUtils.isEmpty(displayName))
			return displayName;
//		String reg="^\\d+$";
//		boolean result = displayName.matches(reg);
//
//		if(result){
		try {
			logger.info("String displayName="+displayName);
			SysUserDTO user = sysUserService.getByPrimaryKey(Long.parseLong(displayName));
			logger.info("user="+user);
			return user.getDisplayName();
		}catch (Exception e){
			return displayName;
		}

//			if(null != user){
//				return user.getDisplayName();
//			}else{
//				return displayName;
//			}
//		}else{
//
//		}

	}

	@Override
	public List<SysAreaDTO> queryAreaListByUserId(Long userId) {
		return sysAreaService.queryAreaListByUserId(userId);
	}

	@Override
	public boolean queryPowerByUserIdAndAreaCode(Long userId, String areaId, String sysNumber)throws BizRuleException {
		if (StringUtils.isBlank(areaId) || null ==userId ) {
			throw new BizRuleException("用户ID或区域编码参数不能为空");
		}
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("areaId", areaId);
		searchMap.put("userId", userId);
		List<SysUserAreaDTO> list = sysUserAreaService.getListByWhere(searchMap);
		if(list.size()>0){
			return true;
		}
		return false;
	}

	/**
	 * 根据系统菜单Id查询用户
	 * @param functionId
	 * @return
	 */
	@Override
	public List<SysUserDTO> selectByFunction(Long functionId, String orgenterCode){

		return sysUserService.selectByFunction(functionId,orgenterCode);
	}


	/**
	 * 根据userId 修改密码
	 * @param userId
	 * @param password
	 * @return	
	 */
	public boolean updatePSDByUserId(Long userId, String password){
			return sysUserService.updatePSDByUserId(userId, password);
	}


	/**
	 * 根据电话查询用户
	 * @param phone
	 * @return
	 */
	public List<SysUserDTO> findUserByPhone(String phone){
		return sysUserService.findUserByPhone(phone);
	}


	@Override
	public void insertByBatch(List<SysUserRoleDTO> list){
		sysUserRoleService.insertByBatch(list);
	}

	@Override
	public List<SysUserDTO> getUserListByDepartmentId(String department, String roleType) {
		return sysUserService.getUserListByDepartmentId(department,roleType);
	}

	@Override
	public List<SysUserDTO> queryUserListByUserType(Long orgId, String userType) {
		return sysUserService.queryUserListByUserType(orgId,userType);
	}

	@Override
	public List<SysUserDTO> getAllList() {
		return sysUserService.getAllList();
	}

	@Override
	public List<SysUserDisplayNameRespDTO> getSysUserDisplayNameRListByIdList(List<Long> idlist) {
		// TODO Auto-generated method stub
		return null;
	}

	public Long synceSaveSysUserCom(SysUserDTO sysUserDTO) {
		//保存用户
		Long id = sysUserService.addSysUser(sysUserDTO);

		//保存用户机构关系表
		SysUserOrgDTO userOrg = new SysUserOrgDTO();
		userOrg.setUserId(id);
		userOrg.setOrgId(sysUserDTO.getOrgId());
		userOrg.setAreaId(sysUserDTO.getAreaId());
		userOrg.setCreateTime(sysUserDTO.getCreateTime());
		userOrg.setModifyTime(sysUserDTO.getModifyTime());
		sysUserOrgService.addSysUserOrg(userOrg);

		//保存用户天正用户关系
		SysUserEmpowerDTO sysUserEmpowerDTO = new SysUserEmpowerDTO();
		sysUserEmpowerDTO.setuserId(id+"");
		sysUserEmpowerDTO.setythUserId(sysUserDTO.getYthUserId());
		sysUserEmpowerDTO.setCreateTime(sysUserDTO.getCreateTime());
		sysUserEmpowerDTO.setModifyTime(sysUserDTO.getModifyTime());
		sysUserEmpowerService.addSysUserEmpower(sysUserEmpowerDTO);

		return id;
	}

	public Long synceUpdateSysUserCom(SysUserDTO sysUserDTO, List<SysUserEmpowerDTO> sysUserEmpowerDTOS) {
		Long userId = null;
		String userIdStr = sysUserEmpowerDTOS.get(0).getuserId();
		logger.info("synceUpdateSysUserCom//userIdStr="+userIdStr);
		if (StringUtils.isNotEmpty(userIdStr)) {
			userId = Long.parseLong(userIdStr);
            logger.info("synceUpdateSysUserCom//userId="+userId);
			//删除用户与机构的关系
			//todo 更新情况不改机构
//			sysUserOrgService.deleteByUserId(userId);
//			//重新建立用户机构关系表
//			SysUserOrgDTO userOrg = new SysUserOrgDTO();
//			userOrg.setUserId(userId);
//			userOrg.setOrgId(sysUserDTO.getOrgId());
//			userOrg.setAreaId(sysUserDTO.getAreaId());
//			userOrg.setCreateTime(sysUserDTO.getCreateTime());
//			userOrg.setModifyTime(sysUserDTO.getModifyTime());
//			sysUserOrgService.addSysUserOrg(userOrg);

			//删除用户与天正用户的关系
			sysUserEmpowerService.deleteById(userId);
			//保存用户天正用户关系
			SysUserEmpowerDTO sysUserEmpowerDTO = new SysUserEmpowerDTO();
			sysUserEmpowerDTO.setuserId(userId + "");
			sysUserEmpowerDTO.setythUserId(sysUserDTO.getYthUserId());
			sysUserEmpowerDTO.setCreateTime(sysUserDTO.getCreateTime());
			sysUserEmpowerDTO.setModifyTime(sysUserDTO.getModifyTime());
			sysUserEmpowerService.addSysUserEmpower(sysUserEmpowerDTO);
			//更新用户表
			sysUserDTO.setId(userId);
			sysUserDTO.setOrgId(null);
			sysUserDTO.setAreaId(null);
			sysUserService.updatepo(sysUserDTO);
		}
		return userId;
	}


}
