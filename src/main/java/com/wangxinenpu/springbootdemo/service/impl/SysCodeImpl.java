package com.wangxinenpu.springbootdemo.service.impl;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysCodeDTO;
import com.wangxinenpu.springbootdemo.service.facade.SysCodeFacade;
import com.wangxinenpu.springbootdemo.service.service.SysCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import star.bizbase.exception.BizRuleException;

import java.util.HashMap;
import java.util.List;

/**
 * 代码维护facade服务实现类
 * 
 * @author Administrator
 *
 */
@Service
public class SysCodeImpl implements SysCodeFacade {

	@Autowired
	private SysCodeService sysCodeService;

	@Override
	@Transactional
	public int addSysCode(SysCodeDTO po) throws BizRuleException {
		return sysCodeService.addSysCode(po);
	}

	@Override
	public SysCodeDTO getByPrimaryKey(Long id) {
		return sysCodeService.getByPrimaryKey(id);
	}

	@Override
	@Transactional
	public int updatepo(SysCodeDTO po) {
		return sysCodeService.updatepo(po);
	}

	@Override
	public List<SysCodeDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size) {
		return sysCodeService.getListByWhere(searchMap, start, size);
	}

	@Override
	public List<SysCodeDTO> getListByWhere(HashMap<String, Object> searchMap) {
		return sysCodeService.getListByWhere(searchMap);
	}

	@Override
	public int getCountByWhere(HashMap<String, Object> searchMap) {
		return sysCodeService.getCountByWhere(searchMap);
	}

	@Override
	public List<SysCodeDTO> findByCodeType(String codeType) {
		return sysCodeService.findByCodeType(codeType);
	}

	@Override
	@Transactional
	public void deleteByPrimaryKey(Long id) throws BizRuleException{
		 sysCodeService.deleteByPrimaryKey(id);
	}

	@Override
	public List<SysCodeDTO> findByCodeTypeAndCodeValue(HashMap<String, Object> searchMap) {
		return sysCodeService.findByCodeTypeAndCodeValue(searchMap);
	}

}
