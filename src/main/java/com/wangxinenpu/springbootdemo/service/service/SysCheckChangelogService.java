package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckChangelogDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysCheckChangelogMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysCheckChangelog;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import star.bizbase.exception.BizRuleException;
import star.vo.BaseVo;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class SysCheckChangelogService {
	private static Logger logger = LoggerFactory.getLogger(SysCheckChangelogService.class);
	
	@Resource
	private SysCheckChangelogMapper sysCheckChangelogMapper;
	
	public Integer saveSysCheckChangelog(SysCheckChangelogDTO dto) throws BizRuleException {
		if(dto == null ) {
			logger.info("SysCheckChangelogDTO.saveSysCheckChangelog={}",dto);
		}
		return sysCheckChangelogMapper.insertSelective(dto.copyTo(SysCheckChangelog.class));
	}
	
	 public List<SysCheckChangelogDTO> getSysCheckChangelogList(Map<String, Object> map, int start, int size)  throws BizRuleException {
		 if(map == null || map.size()<=0) {
			 logger.info("参数不能为空");
			 return Collections.emptyList();
		 }
		 List<SysCheckChangelog> list = sysCheckChangelogMapper.getSysCheckChangelogList(map,new RowBounds(start,size));
		 if(CollectionUtils.isEmpty(list)) return Collections.emptyList();
		  return BaseVo.copyListTo(list, SysCheckChangelogDTO.class);
	  }
}
