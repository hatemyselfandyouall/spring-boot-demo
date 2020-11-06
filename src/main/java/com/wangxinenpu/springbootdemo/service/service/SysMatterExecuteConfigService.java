package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysMatterExecuteConfigDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysMatterExecuteConfigMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysMatterExecuteConfig;
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
 * 事项执行配置service
 * @author Administrator
 *
 */
@Service
public class SysMatterExecuteConfigService {
	private static Logger logger = LoggerFactory.getLogger(SysMatterExecuteConfigService.class);
	@Resource
	private SysMatterExecuteConfigMapper sysMatterExecuteConfigMapper;
	
	/**
	 * 新增事项执行配置po信息
	 * @param po
	 * @return
	 */
	public Long addSysMatterExecuteConfig(SysMatterExecuteConfigDTO dto){
		if(dto == null) {
			logger.info("SysMatterExecuteConfigService.addSysMatterExecuteConfig dto={}",dto);
			return 0L;
		}
		SysMatterExecuteConfig sc = dto.copyTo(SysMatterExecuteConfig.class);
		sysMatterExecuteConfigMapper.insertSysMatterExecuteConfig(sc);
		return sc.getId();
	}

	/**
	 *  根据主键得到事项执行配置表记录
	 * @param id
	 * @return
	 */
	public SysMatterExecuteConfigDTO getByPrimaryKey(Long id){
		if(id == null || id == 0) {
			logger.info("SysMatterExecuteConfigService.getByPrimaryKey error:id={}",id);
			return null;
		}
		SysMatterExecuteConfig po = sysMatterExecuteConfigMapper.getByPrimaryKey(id);
		if(po==null) return null;
		return po.copyTo(SysMatterExecuteConfigDTO.class);
	}
	
	/**
	 * 更新po信息
	 * @param po
	 * @return
	 */
	public int updatepo(SysMatterExecuteConfigDTO dto){
		if(dto == null || dto.getId() == null || dto.getId() == 0) {
			logger.info("SysMatterExecuteConfigService.updatepo dto={}",dto);
			return 0;
		}
		return sysMatterExecuteConfigMapper.updateSysMatterExecuteConfig(dto.copyTo(SysMatterExecuteConfig.class));//object转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数查询 获取事项执行配置列表  带分页
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysMatterExecuteConfigDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size){
		if(searchMap==null || searchMap.isEmpty() || size<1) {
			logger.info("SysMatterExecuteConfigService.getListByWhere searchMap={}，start={},size={}",searchMap,start,size);
			return Collections.emptyList();
		}
		List<SysMatterExecuteConfig> SysMatterExecuteConfigList = sysMatterExecuteConfigMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(SysMatterExecuteConfigList)) return Collections.emptyList();
		return BaseVo.copyListTo(SysMatterExecuteConfigList, SysMatterExecuteConfigDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询事项执行配置列表 
	 * @param searchMap
	 * @return
	 */
	public List<SysMatterExecuteConfigDTO> getListByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("SysMatterExecuteConfigService.getListByWhere searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysMatterExecuteConfig> SysMatterExecuteConfigList = sysMatterExecuteConfigMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(SysMatterExecuteConfigList)) return Collections.emptyList();
		return BaseVo.copyListTo(SysMatterExecuteConfigList, SysMatterExecuteConfigDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数获取事项执行配置数量
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			return 0;
		}
		return sysMatterExecuteConfigMapper.getCountByWhere(searchMap);
	}
	
	/**
	 * 根据ID删除
	 * @param id
	 * @return
	 */
	public int deleteById(Long id){
		return sysMatterExecuteConfigMapper.deleteById(id);
	}

}