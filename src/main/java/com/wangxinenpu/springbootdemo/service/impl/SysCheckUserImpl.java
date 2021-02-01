package com.wangxinenpu.springbootdemo.service.impl;

import com.wangxinenpu.springbootdemo.service.facade.SysCheckUserFacade;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckUserDTO;
import com.wangxinenpu.springbootdemo.service.service.SysCheckUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import star.bizbase.exception.BizRuleException;

import java.util.HashMap;
import java.util.List;

/**
 * 审核用户facade服务实现类
 * 
 * @author Administrator
 *
 */
@Service
public class SysCheckUserImpl implements SysCheckUserFacade {

	@Autowired
	private SysCheckUserService sysCheckUserService;

	@Override
	@Transactional
	public Long addSysCheckUser(SysCheckUserDTO po) throws BizRuleException {
		return sysCheckUserService.addSysCheckUser(po);
	}

	@Override
	public SysCheckUserDTO getByPrimaryKey(Long id) {
		return sysCheckUserService.getByPrimaryKey(id);
	}

	@Override
	@Transactional
	public int updatepo(SysCheckUserDTO po) {
		return sysCheckUserService.updatepo(po);
	}

	@Override
	public List<SysCheckUserDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size) {
		return sysCheckUserService.getListByWhere(searchMap, start, size);
	}

	@Override
	public List<SysCheckUserDTO> getListByWhere(HashMap<String, Object> searchMap) {
		return sysCheckUserService.getListByWhere(searchMap);
	}

	@Override
	public int getCountByWhere(HashMap<String, Object> searchMap) {
		return sysCheckUserService.getCountByWhere(searchMap);
	}

	@Override
	@Transactional
	public void deleteByPrimaryKey(Long id) throws BizRuleException {
		 sysCheckUserService.deleteById(id);
	}

	@Override
	public void deleteByProId(Long proId) throws BizRuleException {
		sysCheckUserService.deleteByProId(proId);
	}

}
