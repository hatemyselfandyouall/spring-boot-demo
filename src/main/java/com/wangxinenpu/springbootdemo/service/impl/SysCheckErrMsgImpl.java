package com.wangxinenpu.springbootdemo.service.impl;

import com.wangxinenpu.springbootdemo.service.facade.SysCheckErrMsgFacade;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckErrMsgDTO;
import com.wangxinenpu.springbootdemo.service.service.SysCheckErrMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import star.bizbase.exception.BizRuleException;

import java.util.HashMap;
import java.util.List;

/**
 * 业务异常消息facade服务实现类
 * 
 * @author Administrator
 *
 */
@Service
public class SysCheckErrMsgImpl implements SysCheckErrMsgFacade {

	@Autowired
	private SysCheckErrMsgService sysCheckErrMsgService;

	@Override
	@Transactional
	public Long addSysCheckErrMsg(SysCheckErrMsgDTO po) throws BizRuleException {
		return sysCheckErrMsgService.addSysCheckErrMsg(po);
	}

	@Override
	public SysCheckErrMsgDTO getByPrimaryKey(Long id) {
		return sysCheckErrMsgService.getByPrimaryKey(id);
	}

	@Override
	@Transactional
	public int updatepo(SysCheckErrMsgDTO po) {
		return sysCheckErrMsgService.updatepo(po);
	}

	@Override
	public List<SysCheckErrMsgDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size) {
		return sysCheckErrMsgService.getListByWhere(searchMap, start, size);
	}

	@Override
	public List<SysCheckErrMsgDTO> getListByWhere(HashMap<String, Object> searchMap) {
		return sysCheckErrMsgService.getListByWhere(searchMap);
	}

	@Override
	public int getCountByWhere(HashMap<String, Object> searchMap) {
		return sysCheckErrMsgService.getCountByWhere(searchMap);
	}

	@Override
	@Transactional
	public void deleteByPrimaryKey(Long id) throws BizRuleException {
		 sysCheckErrMsgService.deleteById(id);
	}

}
