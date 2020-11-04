package com.wangxinenpu.springbootdemo.service.impl;

import com.wangxinenpu.springbootdemo.service.facade.SysOrginstypeFacade;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysOrginstypeDTO;
import com.wangxinenpu.springbootdemo.service.service.SysOrginstypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import star.bizbase.exception.BizRuleException;

import java.util.HashMap;
import java.util.List;

/**
 * 机构险种facade服务实现类
 * 
 * @author Administrator
 *
 */
@Service
public class SysOrginstypeImpl implements SysOrginstypeFacade {

	@Autowired
	private SysOrginstypeService sysOrginstypeService;

	/**
	 * 根据机构ID查询险种
	 */
	@Override
	public List<SysOrginstypeDTO> findByOrgId(Long orgId) {
		return sysOrginstypeService.findByOrgId(orgId);
	}

	/**
	 * 新增机构险种
	 */
	@Override
	@Transactional
	public int addSysOrginstype(SysOrginstypeDTO po) throws BizRuleException {
		return sysOrginstypeService.addSysOrginstype(po);
	}

	/**
	 * 根据机构ID删除
	 */
	@Override
	@Transactional
	public void deleteByOrgId(Long orgId) throws BizRuleException {
		sysOrginstypeService.deleteByOrgId(orgId);		
	}

	/**
	 * 根据参数查询列表
	 */
	@Override
	public List<SysOrginstypeDTO> getListByWhere(HashMap<String, Object> searchMap) {
		return sysOrginstypeService.getListByWhere(searchMap);
	}

}
