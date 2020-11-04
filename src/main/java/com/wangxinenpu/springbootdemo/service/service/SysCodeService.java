package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysCodeDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysCodeMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysCode;
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
 * 代码维护service
 * @author Administrator
 *
 */
@Service
public class SysCodeService {
	private static Logger logger = LoggerFactory.getLogger(SysCodeService.class);
	@Resource
	private SysCodeMapper sysCodeMapper;
	
	/**
	 * 新增代码po信息
	 * @param po
	 * @return
	 */
	public int addSysCode(SysCodeDTO dto){
		if(dto == null) {
			logger.info("sysCodeService.addSysCode dto={}",dto);
			return 0;
		}
		return sysCodeMapper.insertSysCode(dto.copyTo(SysCode.class));
	}

	/**
	 *  根据主键得到系统代码表记录
	 * @param id
	 * @return
	 */
	public SysCodeDTO getByPrimaryKey(Long id){
		if(id == null || id == 0) {
			logger.info("sysCodeService.getByPrimaryKey error:id={}",id);
			return null;
		}
		SysCode po = sysCodeMapper.getByPrimaryKey(id);
		if(po==null) return null;
		return po.copyTo(SysCodeDTO.class);
	}
	
	/**
	 * 更新po信息
	 * @param po
	 * @return
	 */
	public int updatepo(SysCodeDTO dto){
		if(dto == null || dto.getId() == null || dto.getId() == 0) {
			logger.info("sysCodeService.updatepo dto={}",dto);
			return 0;
		}
		return sysCodeMapper.updateSysCode(dto.copyTo(SysCode.class));//object转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数查询 获取代码列表  带分页
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysCodeDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size){
		if(searchMap==null || searchMap.isEmpty() || size<1) {
			logger.info("sysCodeService.getListByWhere searchMap={}，start={},size={}",searchMap,start,size);
			return Collections.emptyList();
		}
		List<SysCode> sysCodeList = sysCodeMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(sysCodeList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCodeList, SysCodeDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询代码列表 
	 * @param searchMap
	 * @return
	 */
	public List<SysCodeDTO> getListByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("sysCodeService.getListByWhere searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysCode> sysCodeList = sysCodeMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(sysCodeList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCodeList, SysCodeDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数获取系统代码数量
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			return 0;
		}
		return sysCodeMapper.getCountByWhere(searchMap);
	}
	
	/**
	 * 根据代码类型查询
	 * @param codeType
	 * @return
	 */
	public List<SysCodeDTO> findByCodeType(String codeType){
		List<SysCode> sysCodeList = sysCodeMapper.findByCodeType(codeType);
		return BaseVo.copyListTo(sysCodeList, SysCodeDTO.class);
	}
	
	/**
	 * 根据主键ID删除
	 * @param id
	 */
	public void deleteByPrimaryKey(Long id) {
		sysCodeMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 查询相同类型代码值是否重复
	 * @param searchMap
	 * @return
	 */
	public List<SysCodeDTO> findByCodeTypeAndCodeValue(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("sysCodeService.findByCodeTypeAndCodeValue searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysCode> sysCodeList = sysCodeMapper.findByCodeTypeAndCodeValue(searchMap);
		if(CollectionUtils.isEmpty(sysCodeList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCodeList, SysCodeDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	

}
