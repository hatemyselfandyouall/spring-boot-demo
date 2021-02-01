package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckConfigDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysCheckConfigMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysCheckConfig;
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
 * 审核配置service
 * @author Administrator
 *
 */
@Service
public class SysCheckConfigService {
	private static Logger logger = LoggerFactory.getLogger(SysCheckConfigService.class);
	@Resource
	private SysCheckConfigMapper sysCheckConfigMapper;
	
	/**
	 * 新增审核配置po信息
	 * @param po
	 * @return
	 */
	public Long addSysCheckConfig(SysCheckConfigDTO dto){
		if(dto == null) {
			logger.info("sysCheckConfigService.addSysCheckConfig dto={}",dto);
			return 0L;
		}
		SysCheckConfig sc = dto.copyTo(SysCheckConfig.class);
		sysCheckConfigMapper.insertSysCheckConfig(sc);
		return sc.getId();
	}

	/**
	 *  根据主键得到审核配置表记录
	 * @param id
	 * @return
	 */
	public SysCheckConfigDTO getByPrimaryKey(Long id){
		if(id == null || id == 0) {
			logger.info("sysCheckConfigService.getByPrimaryKey error:id={}",id);
			return null;
		}
		SysCheckConfig po = sysCheckConfigMapper.getByPrimaryKey(id);
		if(po==null) return null;
		return po.copyTo(SysCheckConfigDTO.class);
	}
	
	/**
	 * 更新po信息
	 * @param po
	 * @return
	 */
	public int updatepo(SysCheckConfigDTO dto){
		if(dto == null || dto.getId() == null || dto.getId() == 0) {
			logger.info("sysCheckConfigService.updatepo dto={}",dto);
			return 0;
		}
		return sysCheckConfigMapper.updateSysCheckConfig(dto.copyTo(SysCheckConfig.class));//object转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数查询 获取审核配置列表  带分页
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysCheckConfigDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size){
		if(searchMap==null || searchMap.isEmpty() || size<1) {
			logger.info("sysCheckConfigService.getListByWhere searchMap={}，start={},size={}",searchMap,start,size);
			return Collections.emptyList();
		}
		List<SysCheckConfig> sysCheckConfigList = sysCheckConfigMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(sysCheckConfigList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCheckConfigList, SysCheckConfigDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询审核配置列表 
	 * @param searchMap
	 * @return
	 */
	public List<SysCheckConfigDTO> getListByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("sysCheckConfigService.getListByWhere searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysCheckConfig> sysCheckConfigList = sysCheckConfigMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(sysCheckConfigList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCheckConfigList, SysCheckConfigDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数获取审核配置数量
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			return 0;
		}
		return sysCheckConfigMapper.getCountByWhere(searchMap);
	}
	
	/**
	 * 根据ID删除
	 * @param id
	 * @return
	 */
	public int deleteById(Long id){
		return sysCheckConfigMapper.deleteById(id);
	}
	
	/**
	 * 批量修改
	 * @param list
	 */
	public void updateByBatch(List<SysCheckConfigDTO> list){
		sysCheckConfigMapper.updateByBatch(BaseVo.copyListTo(list,SysCheckConfig.class));
	}

}
