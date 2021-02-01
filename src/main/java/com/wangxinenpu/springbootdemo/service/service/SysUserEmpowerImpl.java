package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dao.mapper.cachemapper.SysUserEmpowerCacheMapper;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysUserEmpowerDTO;
import com.wangxinenpu.springbootdemo.service.facade.SysUserEmpowerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import star.bizbase.exception.BizRuleException;

import java.util.HashMap;
import java.util.List;

/**
 * 用户授权facade服务实现类
 * 
 * @author Administrator
 *
 */
@Service
public class SysUserEmpowerImpl implements SysUserEmpowerFacade {

	@Autowired
	private SysUserEmpowerService sysUserEmpowerService;
	@Autowired
	private SysUserEmpowerCacheMapper sysUserEmpowerCacheMapper;

	@Override
	@Transactional
	public Long addSysUserEmpower(SysUserEmpowerDTO po) throws BizRuleException {
		return sysUserEmpowerService.addSysUserEmpower(po);
	}

	@Override
	public SysUserEmpowerDTO getByPrimaryKey(Long id) {
		return sysUserEmpowerService.getByPrimaryKey(id);
	}

	@Override
	@Transactional
	public int updatepo(SysUserEmpowerDTO po) {
		return sysUserEmpowerService.updatepo(po);
	}

	@Override
	public List<SysUserEmpowerDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size) {
		return sysUserEmpowerService.getListByWhere(searchMap, start, size);
	}

	@Override
	public List<SysUserEmpowerDTO> getListByWhere(HashMap<String, Object> searchMap) {
		return sysUserEmpowerService.getListByWhere(searchMap);
	}

	@Override
	public int getCountByWhere(HashMap<String, Object> searchMap) {
		return sysUserEmpowerService.getCountByWhere(searchMap);
	}

	@Override
	@Transactional
	public void deleteByPrimaryKey(Long id) throws BizRuleException {
		 sysUserEmpowerService.deleteById(id);
	}

	@Override
	public String getCacheEmpowerUserIdById(String ythUserId) {
		return sysUserEmpowerCacheMapper.getCacheEmpowerUserIdById(ythUserId);
	}

	@Override
	public List<SysUserEmpowerDTO> getlistByUserIds(String startTime, String endTime) {
		return sysUserEmpowerService.getlistByUserIds(startTime, endTime);
	}

}
