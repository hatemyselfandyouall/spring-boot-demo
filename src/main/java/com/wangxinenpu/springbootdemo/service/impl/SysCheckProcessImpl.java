package com.wangxinenpu.springbootdemo.service.impl;

import com.wangxinenpu.springbootdemo.service.facade.SysCheckProcessFacade;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckProcessDTO;
import com.wangxinenpu.springbootdemo.service.service.SysCheckProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import star.bizbase.exception.BizRuleException;

import java.util.HashMap;
import java.util.List;

/**
 * 审核流程facade服务实现类
 * 
 * @author Administrator
 *
 */
@Service
public class SysCheckProcessImpl implements SysCheckProcessFacade {

	@Autowired
	private SysCheckProcessService sysCheckProcessService;

	@Override
	@Transactional
	public Long addSysCheckProcess(SysCheckProcessDTO po) throws BizRuleException {
		return sysCheckProcessService.addSysCheckProcess(po);
	}

	@Override
	public SysCheckProcessDTO getByPrimaryKey(Long id) {
		return sysCheckProcessService.getByPrimaryKey(id);
	}

	@Override
	@Transactional
	public int updatepo(SysCheckProcessDTO po) {
		return sysCheckProcessService.updatepo(po);
	}

	@Override
	public List<SysCheckProcessDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size) {
		return sysCheckProcessService.getListByWhere(searchMap, start, size);
	}

	@Override
	public List<SysCheckProcessDTO> getListByWhere(HashMap<String, Object> searchMap) {
		return sysCheckProcessService.getListByWhere(searchMap);
	}

	@Override
	public int getCountByWhere(HashMap<String, Object> searchMap) {
		return sysCheckProcessService.getCountByWhere(searchMap);
	}

	@Override
	@Transactional
	public void deleteByPrimaryKey(Long id) throws BizRuleException {
		 sysCheckProcessService.deleteById(id);
	}

	@Override
	public String findVersionByConfigId(Long configId) {
		return sysCheckProcessService.findVersionByConfigId(configId);
	}

	@Override
	public String findIdByConfigIdAndStep(HashMap<String, Object> searchMap) {
		return sysCheckProcessService.findIdByConfigIdAndStep(searchMap);
	}

	@Override
	public List<SysCheckProcessDTO> getListByConfigId(Long configId) {
		return sysCheckProcessService.getListByConfigId(configId);
	}

	@Override
	public List<SysCheckProcessDTO> queryProListByProIds(String proIds) {
		return sysCheckProcessService.queryProListByProIds(proIds);
	}

	@Override
	public List<String> queryProStepByUserId(Long orgId, Long funId, String userId) {
		return sysCheckProcessService.queryProStepByUserId(orgId,funId,userId);
	}

	@Override
	@Transactional
	public void insertBatch(List<SysCheckProcessDTO> list) {
		sysCheckProcessService.insertBatch(list);
	}
}
