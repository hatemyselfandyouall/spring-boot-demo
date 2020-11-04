package com.wangxinenpu.springbootdemo.service.impl;

import com.wangxinenpu.springbootdemo.service.facade.SysCheckRoleFacade;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckRoleDTO;
import com.wangxinenpu.springbootdemo.service.service.SysCheckRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import star.bizbase.exception.BizRuleException;

import java.util.HashMap;
import java.util.List;

/**
 * 审核角色facade服务实现类
 * 
 * @author Administrator
 *
 */
@Service
public class SysCheckRoleImpl implements SysCheckRoleFacade {

	@Autowired
	private SysCheckRoleService sysCheckRoleService;

	@Override
	@Transactional
	public Long addSysCheckRole(SysCheckRoleDTO po) throws BizRuleException {
		return sysCheckRoleService.addSysCheckRole(po);
	}

	@Override
	public SysCheckRoleDTO getByPrimaryKey(Long id) {
		return sysCheckRoleService.getByPrimaryKey(id);
	}

	@Override
	@Transactional
	public int updatepo(SysCheckRoleDTO po) {
		return sysCheckRoleService.updatepo(po);
	}

	@Override
	public List<SysCheckRoleDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size) {
		return sysCheckRoleService.getListByWhere(searchMap, start, size);
	}

	@Override
	public List<SysCheckRoleDTO> getListByWhere(HashMap<String, Object> searchMap) {
		return sysCheckRoleService.getListByWhere(searchMap);
	}

	@Override
	public int getCountByWhere(HashMap<String, Object> searchMap) {
		return sysCheckRoleService.getCountByWhere(searchMap);
	}

	@Override
	@Transactional
	public void deleteByPrimaryKey(Long id) throws BizRuleException {
		 sysCheckRoleService.deleteById(id);
	}

	@Override
	public void deleteByProId(Long proId) throws BizRuleException {
		sysCheckRoleService.deleteByProId(proId);
	}

}
