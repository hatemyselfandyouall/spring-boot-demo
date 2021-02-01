package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckUserDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysCheckUserMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysCheckUser;
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
 * 审核用户service
 * @author Administrator
 *
 */
@Service
public class SysCheckUserService {
	private static Logger logger = LoggerFactory.getLogger(SysCheckUserService.class);
	@Resource
	private SysCheckUserMapper sysCheckUserMapper;
	
	/**
	 * 新增审核用户po信息
	 * @param po
	 * @return
	 */
	public Long addSysCheckUser(SysCheckUserDTO dto){
		if(dto == null) {
			logger.info("sysCheckUserService.addSysCheckUser dto={}",dto);
			return 0L;
		}
		return sysCheckUserMapper.insertSysCheckUser(dto.copyTo(SysCheckUser.class));
	}

	/**
	 *  根据主键得到审核用户表记录
	 * @param id
	 * @return
	 */
	public SysCheckUserDTO getByPrimaryKey(Long id){
		if(id == null || id == 0) {
			logger.info("sysCheckUserService.getByPrimaryKey error:id={}",id);
			return null;
		}
		SysCheckUser po = sysCheckUserMapper.getByPrimaryKey(id);
		if(po==null) return null;
		return po.copyTo(SysCheckUserDTO.class);
	}
	
	/**
	 * 更新po信息
	 * @param po
	 * @return
	 */
	public int updatepo(SysCheckUserDTO dto){
		if(dto == null || dto.getId() == null || dto.getId() == 0) {
			logger.info("sysCheckUserService.updatepo dto={}",dto);
			return 0;
		}
		return sysCheckUserMapper.updateSysCheckUser(dto.copyTo(SysCheckUser.class));//object转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数查询 获取审核用户列表  带分页
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysCheckUserDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size){
		if(searchMap==null || searchMap.isEmpty() || size<1) {
			logger.info("sysCheckUserService.getListByWhere searchMap={}，start={},size={}",searchMap,start,size);
			return Collections.emptyList();
		}
		List<SysCheckUser> sysCheckUserList = sysCheckUserMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(sysCheckUserList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCheckUserList, SysCheckUserDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询审核用户列表 
	 * @param searchMap
	 * @return
	 */
	public List<SysCheckUserDTO> getListByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("sysCheckUserService.getListByWhere searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysCheckUser> sysCheckUserList = sysCheckUserMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(sysCheckUserList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCheckUserList, SysCheckUserDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数获取审核用户数量
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			return 0;
		}
		return sysCheckUserMapper.getCountByWhere(searchMap);
	}
	
	/**
	 * 根据ID删除
	 * @param id
	 * @return
	 */
	public int deleteById(Long id){
		return sysCheckUserMapper.deleteById(id);
	}
	
	public int deleteByProId(Long proId){
		return sysCheckUserMapper.deleteByProId(proId);
	}

}
