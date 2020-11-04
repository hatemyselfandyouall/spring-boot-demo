package com.wangxinenpu.springbootdemo.service.impl;

import com.wangxinenpu.springbootdemo.service.facade.SysCheckInformationFacade;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckInformationDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysReqBusInfoDTO;
import com.wangxinenpu.springbootdemo.service.service.SysCheckInformationService;
import com.wangxinenpu.springbootdemo.service.service.SysReqBusInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import star.bizbase.exception.BizRuleException;

import java.util.HashMap;
import java.util.List;

/**
 * 审核信息facade服务实现类
 * 
 * @author Administrator
 *
 */
@Service
public class SysCheckInformationImpl implements SysCheckInformationFacade {

	@Autowired
	private SysCheckInformationService sysCheckInformationService;
	@Autowired
	private SysReqBusInfoService SysReqBusInfoService;

	@Override
	@Transactional
	public Long addSysCheckInformation(SysCheckInformationDTO po) throws BizRuleException {
		return sysCheckInformationService.addSysCheckInformation(po);
	}

	@Override
	public SysCheckInformationDTO getByPrimaryKey(Long id) {
		return sysCheckInformationService.getByPrimaryKey(id);
	}

	@Override
	@Transactional
	public int updatepo(SysCheckInformationDTO po) {
		return sysCheckInformationService.updatepo(po);
	}

	@Override
	public List<SysCheckInformationDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size) {
		return sysCheckInformationService.getListByWhere(searchMap, start, size);
	}

	@Override
	public List<SysCheckInformationDTO> getListByWhere(HashMap<String, Object> searchMap) {
		return sysCheckInformationService.getListByWhere(searchMap);
	}

	@Override
	public int getCountByWhere(HashMap<String, Object> searchMap) {
		return sysCheckInformationService.getCountByWhere(searchMap);
	}

	@Override
	@Transactional
	public void deleteByPrimaryKey(Long id) throws BizRuleException {
		 sysCheckInformationService.deleteById(id);
	}

	@Override
	public List<SysCheckInformationDTO> queryListByBusId(HashMap<String, Object> searchMap) {
		return sysCheckInformationService.queryListByBusId(searchMap);
	}

	@Override
	public List<SysCheckInformationDTO> getListByCheckPeople(HashMap<String, Object> searchMap) {
		return sysCheckInformationService.getListByCheckPeople(searchMap);
	}

	@Override
	public int getCountByCheckPeople(HashMap<String, Object> searchMap) {
		return sysCheckInformationService.getCountByCheckPeople(searchMap);
	}

	@Override
	public List<Long> findBusIdByUserId(Long userId, String checkResult) {
		return sysCheckInformationService.findBusIdByUserId(userId,checkResult);
	}

	@Override
	public void deleteByProIdAndBusId(HashMap<String, Object> searchMap) throws BizRuleException {
		sysCheckInformationService.deleteByProIdAndBusId(searchMap);
	}

	@Override
	public List<Long> getCacheMyCheckingBusIdListByPrePass(Long userId, String checkResult) throws BizRuleException {
		return sysCheckInformationService.getCacheMyCheckingBusIdListByPrePass(userId, checkResult);
	}

	@Override
	public Long addSysReqBusInfo(SysReqBusInfoDTO po){
		return SysReqBusInfoService.addSysReqBusInfo(po);
	}

}
