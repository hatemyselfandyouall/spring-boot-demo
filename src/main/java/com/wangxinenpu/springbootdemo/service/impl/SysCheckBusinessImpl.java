package com.wangxinenpu.springbootdemo.service.impl;

import com.wangxinenpu.springbootdemo.service.facade.SysCheckBusinessFacade;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckBusinessDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckBusinessHistoryDTO;
import com.wangxinenpu.springbootdemo.service.service.SysCheckBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import star.bizbase.exception.BizRuleException;

import java.util.HashMap;
import java.util.List;

/**
 * 审核业务facade服务实现类
 * 
 * @author Administrator
 *
 */
@Service
public class SysCheckBusinessImpl implements SysCheckBusinessFacade {

	@Autowired
	private SysCheckBusinessService sysCheckBusinessService;

	@Override
	@Transactional
	public Long addSysCheckBusiness(SysCheckBusinessDTO po) throws BizRuleException {
		return sysCheckBusinessService.addSysCheckBusiness(po);
	}

	@Override
	public SysCheckBusinessDTO getByPrimaryKey(Long id) {
		return sysCheckBusinessService.getByPrimaryKey(id);
	}

	@Override
	@Transactional
	public int updatepo(SysCheckBusinessDTO po) {
		return sysCheckBusinessService.updatepo(po);
	}

	@Override
	public List<SysCheckBusinessDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size) {
		return sysCheckBusinessService.getListByWhere(searchMap, start, size);
	}

	@Override
	public List<SysCheckBusinessDTO> getListByWhere(HashMap<String, Object> searchMap) {
		return sysCheckBusinessService.getListByWhere(searchMap);
	}

	@Override
	public int getCountByWhere(HashMap<String, Object> searchMap) {
		return sysCheckBusinessService.getCountByWhere(searchMap);
	}

	@Override
	@Transactional
	public void deleteByPrimaryKey(Long id) throws BizRuleException {
		sysCheckBusinessService.deleteById(id);
	}

	@Override
	public List<SysCheckBusinessDTO> getBusinessList(HashMap<String, Object> searchMap) {
		return sysCheckBusinessService.getBusinessList(searchMap);
	}

	@Override
	public List<SysCheckBusinessHistoryDTO> getHosListByBusId(Long busId, Integer page, Integer size) {
		return sysCheckBusinessService.getHosListByBusId(busId, page, size);
	}

	@Override
	public SysCheckBusinessHistoryDTO getHistoryById(Long hisId) {
		return sysCheckBusinessService.getHistoryById(hisId);
	}

	@Override
	public Long insertCheckBusinessHistory(SysCheckBusinessHistoryDTO po) {
		return sysCheckBusinessService.insertCheckBusinessHistory(po);
	}

}
