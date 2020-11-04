package com.wangxinenpu.springbootdemo.service.impl;

import com.wangxinenpu.springbootdemo.service.facade.SysRoleFacade;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysRoleDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysRoleFunctionDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysUserRoleDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysRoleFunctionMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.SysRoleMapper;
import com.wangxinenpu.springbootdemo.service.service.SysRoleFunctionService;
import com.wangxinenpu.springbootdemo.service.service.SysRoleService;
import com.wangxinenpu.springbootdemo.service.service.SysUserRoleService;
import com.wangxinenpu.springbootdemo.dataobject.po.SysRoleFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import star.bizbase.exception.BizRuleException;
import star.bizbase.util.RuleCheck;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * 系统角色facade服务实现类
 * 
 * @author Administrator
 *
 */
@Slf4j
@Service
public class SysRoleImpl implements SysRoleFacade {

	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleFunctionService sysRoleFunctionService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Autowired
	private SysRoleFunctionMapper sysRoleFunctionMapper;


	@Override
	public int addSysRole(SysRoleDTO po) throws BizRuleException {
		/* 非空校验 */
		RuleCheck.validateByAnnotation(po);
		return sysRoleService.addSysRole(po);
	}

	@Override
	public SysRoleDTO getByPrimaryKey(String id) {
		return sysRoleService.getByPrimaryKey(id);
	}

	@Override
	public int updatepo(SysRoleDTO po) {
		return sysRoleService.updatepo(po);
	}

	@Override
	public List<SysRoleDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size) {
		return sysRoleService.getListByWhere(searchMap, start, size);
	}

	@Override
	public List<SysRoleDTO> getListByWhere(HashMap<String, Object> searchMap) {
		return sysRoleService.getListByWhere(searchMap);
	}

	@Override
	public List<SysRoleDTO> findRoleInRoleCode(List<String> roleCodes) {
		return sysRoleService.findRoleInRoleCode(roleCodes);
	}

	@Override
	public int getCountByWhere(HashMap<String, Object> searchMap) {
		return sysRoleService.getCountByWhere(searchMap);
	}

	@Override
	public boolean checkRoleName(String roleName, String roleId, Long orgId) {
		HashMap<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("roleName", roleName);
		searchMap.put("roleId", roleId);
		searchMap.put("orgId", orgId);
		SysRoleDTO sysRole = sysRoleService.getByRoleNameAndRoleId(searchMap);
		if (null == sysRole) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public void deleteByPrimaryKey(String roleId) throws BizRuleException{
		List<SysRoleFunctionDTO> userRoleList = sysRoleFunctionService.findByRoleId(roleId);
		List<SysUserRoleDTO> sysUserRoleList = sysUserRoleService.findByRoleId(roleId);
		if(userRoleList.size()>0 || sysUserRoleList.size()>0){
			throw new BizRuleException("该角色正在使用中不能删除");
		}
		sysRoleService.deleteByPrimaryKey(roleId);
		//删除角色功能关系
		sysRoleFunctionService.deleteByRoleId(roleId);
	}

	@Override
	public List<SysRoleDTO> queryRoleByUserId(String userId) {
		return sysRoleService.queryRoleByUserId(userId);
	}

	@Override
	@Transactional
	public void deleteRoleFunctionRefAndAddNewRef(String roleId, List<SysRoleFunctionDTO> list) {
		 /**
         * 先删除老的角色资源关系，后增加新的角色资源关系
         */
		try {
			//删除角色功能关系
			sysRoleFunctionService.deleteByRoleId(roleId);
			SysRoleFunctionDTO sysRoleFunction=null;
            for(int i=0;i<list.size();i++){
            	sysRoleFunction =list.get(i);
                //生成主键ID
    			String uuid = UUID.randomUUID().toString();
    			String relationId = uuid.replace("-", "");
    			sysRoleFunction.setRelationId(relationId);
                sysRoleFunction.setRoleId(roleId);
                sysRoleFunctionService.addSysRoleFunction(sysRoleFunction);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void patchAddRoleFunctionRefAndAddNewRef(List<String> roleIdList, List<SysRoleFunctionDTO> list) {
		for (String roleId:roleIdList){
			deleteRoleFunctionRefAndAddNewRef(roleId,list);
		}
	}

	@Override
	public List<SysRoleDTO> findRoleByFunType(String funType, Long orgId) {
		return sysRoleService.findRoleByFunType(funType,orgId);
	}

	@Override
	public void insertByBatch(List<SysRoleDTO> list) {
		sysRoleService.insertByBatch(list);
		
	}

	@Override
	public void deleteRelationByRoleId(String roleId) {
		sysUserRoleService.deleteByRoleId(roleId);
	}

	@Override
	@Transactional
	public void saveUserRole(List<SysUserRoleDTO> sysUserRoleDTOS) {
		sysUserRoleService.deleteByUserId(sysUserRoleDTOS.get(0));
		sysUserRoleService.saveUserRole(sysUserRoleDTOS);
	}

	@Override
	public void roleAdd(int tag) {
		//查询丽水未授权的角色
		List<SysRoleDTO> SysRoleDTOs= sysRoleMapper.fidno();
		log.info("SysRoleDTOs="+SysRoleDTOs);
		for(SysRoleDTO sysRoleDTO:SysRoleDTOs){
			List<SysRoleFunctionDTO> SysRoleFunctionDTOs = sysRoleMapper.fidyes(sysRoleDTO.getRoleName());
			log.info("SysRoleFunctionDTOs="+SysRoleFunctionDTOs);
			for(SysRoleFunctionDTO sysRoleFunctionDTO:SysRoleFunctionDTOs) {
				sysRoleFunctionMapper.deleteByRoleId(sysRoleDTO.getId());
				SysRoleFunction sysRoleFunction = new SysRoleFunction();
				sysRoleFunction.setRelationId(UUID.randomUUID().toString().replace("-",""));
				sysRoleFunction.setRoleId(sysRoleDTO.getId());
				sysRoleFunction.setFunctionId(sysRoleFunctionDTO.getFunctionId());
				sysRoleFunction.setCreateTime(new Date());
				sysRoleFunction.setModifyTime(new Date());
				sysRoleFunction.setSelectState(sysRoleFunctionDTO.getSelectState());
				log.info("sysRoleFunction="+sysRoleFunction);
				if(tag==1){
					sysRoleFunctionMapper.insertSysRoleFunction(sysRoleFunction);
				}
			}
		}
	}

}
