package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysMatterExecuteDetailDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysMatterExecuteDetailMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysMatterExecuteDetail;
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
 * 事项执行详细service
 * @author Administrator
 *
 */
@Service
public class SysMatterExecuteDetailService {
	private static Logger logger = LoggerFactory.getLogger(SysMatterExecuteDetailService.class);
	@Resource
	private SysMatterExecuteDetailMapper sysMatterExecuteDetailMapper;
	
	/**
	 * 新增事项执行详细po信息
	 * @param po
	 * @return
	 */
	public Long addSysMatterExecuteDetail(SysMatterExecuteDetailDTO dto){
		if(dto == null) {
			logger.info("SysMatterExecuteDetailService.addSysMatterExecuteDetail dto={}",dto);
			return 0L;
		}
		SysMatterExecuteDetail sc = dto.copyTo(SysMatterExecuteDetail.class);
		sysMatterExecuteDetailMapper.insertSysMatterExecuteDetail(sc);
		return sc.getId();
	}

	/**
	 *  根据主键得到事项执行详细表记录
	 * @param id
	 * @return
	 */
	public SysMatterExecuteDetailDTO getByPrimaryKey(Long id){
		if(id == null || id == 0) {
			logger.info("SysMatterExecuteDetailService.getByPrimaryKey error:id={}",id);
			return null;
		}
		SysMatterExecuteDetail po = sysMatterExecuteDetailMapper.getByPrimaryKey(id);
		if(po==null) return null;
		return po.copyTo(SysMatterExecuteDetailDTO.class);
	}
	
	/**
	 * 更新po信息
	 * @param po
	 * @return
	 */
	public int updatepo(SysMatterExecuteDetailDTO dto){
		if(dto == null || dto.getId() == null || dto.getId() == 0) {
			logger.info("SysMatterExecuteDetailService.updatepo dto={}",dto);
			return 0;
		}
		return sysMatterExecuteDetailMapper.updateSysMatterExecuteDetail(dto.copyTo(SysMatterExecuteDetail.class));//object转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数查询 获取事项执行详细列表  带分页
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysMatterExecuteDetailDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size){
		if(searchMap==null || searchMap.isEmpty() || size<1) {
			logger.info("SysMatterExecuteDetailService.getListByWhere searchMap={}，start={},size={}",searchMap,start,size);
			return Collections.emptyList();
		}
		List<SysMatterExecuteDetail> SysMatterExecuteDetailList = sysMatterExecuteDetailMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(SysMatterExecuteDetailList)) return Collections.emptyList();
		return BaseVo.copyListTo(SysMatterExecuteDetailList, SysMatterExecuteDetailDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询事项执行详细列表 
	 * @param searchMap
	 * @return
	 */
	public List<SysMatterExecuteDetailDTO> getListByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("SysMatterExecuteDetailService.getListByWhere searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysMatterExecuteDetail> SysMatterExecuteDetailList = sysMatterExecuteDetailMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(SysMatterExecuteDetailList)) return Collections.emptyList();
		return BaseVo.copyListTo(SysMatterExecuteDetailList, SysMatterExecuteDetailDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数获取事项执行详细数量
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			return 0;
		}
		return sysMatterExecuteDetailMapper.getCountByWhere(searchMap);
	}
	
	/**
	 * 根据ID删除
	 * @param id
	 * @return
	 */
	public int deleteById(Long id){
		return sysMatterExecuteDetailMapper.deleteById(id);
	}
	
	public int deleteByConfigId(Long configId){
		return sysMatterExecuteDetailMapper.deleteByConfigId(configId);
	}

}
