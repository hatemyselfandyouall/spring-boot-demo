package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysUserOrgDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysUserOrgMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysUserOrg;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import star.vo.BaseVo;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 用户机构关系对照service
 * @author Administrator
 *
 */
@Service
public class SysUserOrgService {
	private static Logger logger = LoggerFactory.getLogger(SysUserOrgService.class);
	@Resource
	private SysUserOrgMapper sysUserOrgMapper;
	
	/**
	 * 新增用户机构关系对照
	 * @param po
	 * @return
	 */
	public int addSysUserOrg(SysUserOrgDTO dto){
		if(dto == null) {
			logger.info("SysUserOrgService.addSysUserOrg dto={}",dto);
			return 0;
		}
		return sysUserOrgMapper.insertSysUserOrg(dto.copyTo(SysUserOrg.class));
	}

	/**
	 * 根据参数查询用户机构关系对照信息带分页
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysUserOrgDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size){
		if(searchMap==null || searchMap.isEmpty() || size<1) {
			logger.info("SysUserOrgService.getListByWhere searchMap={}，start={},size={}",searchMap,start,size);
			return Collections.emptyList();
		}
		List<SysUserOrg> SysUserOrgList = sysUserOrgMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(SysUserOrgList)) return Collections.emptyList();
		return BaseVo.copyListTo(SysUserOrgList, SysUserOrgDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询用户机构关系对照信息
	 * @param searchMap
	 * @return
	 */
	public List<SysUserOrgDTO> getListByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("SysUserOrgService.getListByWhere searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysUserOrg> SysUserOrgList = sysUserOrgMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(SysUserOrgList)) return Collections.emptyList();
		return BaseVo.copyListTo(SysUserOrgList, SysUserOrgDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数 获取用户机构关系对照
	 * 以userId为主
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			return 0;
		}
		return sysUserOrgMapper.getCountByWhere(searchMap);
	}
	
	/**
	 * 根据用户id查询
	 * @param userId
	 * @return
	 */
	public List<SysUserOrgDTO> findByUserId(Long userId){
		List<SysUserOrg> sysUserOrgList = sysUserOrgMapper.findByUserId(userId);
		if(CollectionUtils.isEmpty(sysUserOrgList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysUserOrgList, SysUserOrgDTO.class);
	}
	
	/**
	 * 根据用户ID删除
	 * @param userId
	 */
	public void deleteByUserId(Long userId){
		sysUserOrgMapper.deleteByUserId(userId);
	}
	
	/**
	 * 查询用户关联机构下的用户
	 * @param userId
	 * @return
	 */
	public List<SysUserOrgDTO> queryUserOrgByOrgIds(Long userId){
		List<SysUserOrg> sysUserOrgList = sysUserOrgMapper.queryUserOrgByOrgIds(userId);
		if(CollectionUtils.isEmpty(sysUserOrgList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysUserOrgList, SysUserOrgDTO.class);
	}
}
