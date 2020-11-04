package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysOrginstypeDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysOrginstypeMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysOrginstype;
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
 * 机构险种service
 * @author Administrator
 *
 */
@Service
public class SysOrginstypeService {
	private static Logger logger = LoggerFactory.getLogger(SysOrginstypeService.class);
	@Resource
	private SysOrginstypeMapper SysOrginstypeMapper;
	
	/**
	 * 新增机构险种po信息
	 * @param po
	 * @return
	 */
	public int addSysOrginstype(SysOrginstypeDTO dto){
		if(dto == null) {
			logger.info("SysOrginstypeService.addSysOrginstype dto={}",dto);
			return 0;
		}
		return SysOrginstypeMapper.insertSysOrginstype(dto.copyTo(SysOrginstype.class));
	}

	/**
	 *  根据主键得到系统机构险种表记录
	 * @param id
	 * @return
	 */
	public SysOrginstypeDTO getByPrimaryKey(Long id){
		if(id == null || id == 0) {
			logger.info("SysOrginstypeService.getByPrimaryKey error:id={}",id);
			return null;
		}
		SysOrginstype po = SysOrginstypeMapper.getByPrimaryKey(id);
		if(po==null) return null;
		return po.copyTo(SysOrginstypeDTO.class);
	}
	
	/**
	 * 更新po信息
	 * @param po
	 * @return
	 */
	public int updatepo(SysOrginstypeDTO dto){
		if(dto == null || dto.getId() == null || dto.getId() == 0) {
			logger.info("SysOrginstypeService.updatepo dto={}",dto);
			return 0;
		}
		return SysOrginstypeMapper.updateSysOrginstype(dto.copyTo(SysOrginstype.class));//object转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数查询 获取机构险种列表  带分页
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysOrginstypeDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size){
		if(searchMap==null || searchMap.isEmpty() || size<1) {
			logger.info("SysOrginstypeService.getListByWhere searchMap={}，start={},size={}",searchMap,start,size);
			return Collections.emptyList();
		}
		List<SysOrginstype> SysOrginstypeList = SysOrginstypeMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(SysOrginstypeList)) return Collections.emptyList();
		return BaseVo.copyListTo(SysOrginstypeList, SysOrginstypeDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询机构险种列表 
	 * @param searchMap
	 * @return
	 */
	public List<SysOrginstypeDTO> getListByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("SysOrginstypeService.getListByWhere searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysOrginstype> SysOrginstypeList = SysOrginstypeMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(SysOrginstypeList)) return Collections.emptyList();
		return BaseVo.copyListTo(SysOrginstypeList, SysOrginstypeDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数获取机构险种数量
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			return 0;
		}
		return SysOrginstypeMapper.getCountByWhere(searchMap);
	}
	
	/**
	 * 根据机构ID查询
	 * @param orgId
	 * @return
	 */
	public List<SysOrginstypeDTO> findByOrgId(Long orgId){
		List<SysOrginstype> SysOrginstypeList = SysOrginstypeMapper.findByOrgId(orgId);
		return BaseVo.copyListTo(SysOrginstypeList, SysOrginstypeDTO.class);
	}
	
	/**
	 * 根据机构ID删除
	 * @param orgId
	 */
	public void deleteByOrgId(Long orgId){
		SysOrginstypeMapper.deleteByOrgId(orgId);
	}
	
	/**
	 * 根据机构ID和险种ID查询是否绑定
	 * @param searchMap
	 * @return
	 */
	public List<SysOrginstypeDTO> existenceByOrgIdAndInsId(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("SysOrginstypeService.existenceByOrgIdAndInsId searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysOrginstype> SysOrginstypeList = SysOrginstypeMapper.existenceByOrgIdAndInsId(searchMap);
		if(CollectionUtils.isEmpty(SysOrginstypeList)) return Collections.emptyList();
		return BaseVo.copyListTo(SysOrginstypeList, SysOrginstypeDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
}
