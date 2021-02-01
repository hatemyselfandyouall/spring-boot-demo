package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysMatterExecuteTimeDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysMatterExecuteTimeMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysMatterExecuteTime;
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
 * 办理时间service
 * @author Administrator
 *
 */
@Service
public class SysMatterExecuteTimeService {
	private static Logger logger = LoggerFactory.getLogger(SysMatterExecuteTimeService.class);
	@Resource
	private SysMatterExecuteTimeMapper sysMatterExecuteTimeMapper;
	
	/**
	 * 新增办理时间po信息
	 * @param po
	 * @return
	 */
	public Long addSysMatterExecuteTime(SysMatterExecuteTimeDTO dto){
		if(dto == null) {
			logger.info("SysMatterExecuteTimeService.addSysMatterExecuteTime dto={}",dto);
			return 0L;
		}
		SysMatterExecuteTime sc = dto.copyTo(SysMatterExecuteTime.class);
		sysMatterExecuteTimeMapper.insertSysMatterExecuteTime(sc);
		return sc.getId();
	}

	/**
	 *  根据主键得到办理时间表记录
	 * @param id
	 * @return
	 */
	public SysMatterExecuteTimeDTO getByPrimaryKey(Long id){
		if(id == null || id == 0) {
			logger.info("SysMatterExecuteTimeService.getByPrimaryKey error:id={}",id);
			return null;
		}
		SysMatterExecuteTime po = sysMatterExecuteTimeMapper.getByPrimaryKey(id);
		if(po==null) return null;
		return po.copyTo(SysMatterExecuteTimeDTO.class);
	}
	
	/**
	 * 更新po信息
	 * @param po
	 * @return
	 */
	public int updatepo(SysMatterExecuteTimeDTO dto){
		if(dto == null || dto.getId() == null || dto.getId() == 0) {
			logger.info("SysMatterExecuteTimeService.updatepo dto={}",dto);
			return 0;
		}
		return sysMatterExecuteTimeMapper.updateSysMatterExecuteTime(dto.copyTo(SysMatterExecuteTime.class));//object转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数查询 获取办理时间列表  带分页
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysMatterExecuteTimeDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size){
		if(searchMap==null || searchMap.isEmpty() || size<1) {
			logger.info("SysMatterExecuteTimeService.getListByWhere searchMap={}，start={},size={}",searchMap,start,size);
			return Collections.emptyList();
		}
		List<SysMatterExecuteTime> SysMatterExecuteTimeList = sysMatterExecuteTimeMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(SysMatterExecuteTimeList)) return Collections.emptyList();
		return BaseVo.copyListTo(SysMatterExecuteTimeList, SysMatterExecuteTimeDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询办理时间列表 
	 * @param searchMap
	 * @return
	 */
	public List<SysMatterExecuteTimeDTO> getListByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("SysMatterExecuteTimeService.getListByWhere searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysMatterExecuteTime> SysMatterExecuteTimeList = sysMatterExecuteTimeMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(SysMatterExecuteTimeList)) return Collections.emptyList();
		return BaseVo.copyListTo(SysMatterExecuteTimeList, SysMatterExecuteTimeDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数获取办理时间数量
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			return 0;
		}
		return sysMatterExecuteTimeMapper.getCountByWhere(searchMap);
	}
	
	/**
	 * 根据ID删除
	 * @param id
	 * @return
	 */
	public int deleteById(Long id){
		return sysMatterExecuteTimeMapper.deleteById(id);
	}
	
	public int deleteByConfigId(Long configId){
		return sysMatterExecuteTimeMapper.deleteByConfigId(configId);
	}

}
