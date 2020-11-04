package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckBusinessDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckBusinessHistoryDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysCheckBusinessHistoryMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.SysCheckBusinessMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysCheckBusiness;
import com.wangxinenpu.springbootdemo.dataobject.po.SysCheckBusinessHistory;
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
 * 审核业务service
 * @author Administrator
 *
 */
@Service
public class SysCheckBusinessService {
	private static Logger logger = LoggerFactory.getLogger(SysCheckBusinessService.class);
	@Resource
	private SysCheckBusinessMapper sysCheckBusinessMapper;
	@Resource
	private SysCheckBusinessHistoryMapper checkBusinessHistoryMapper;

	/**
	 * 新增审核业务po信息
	 * @param po
	 * @return
	 */
	public Long addSysCheckBusiness(SysCheckBusinessDTO dto){
		if(dto == null) {
			logger.info("sysCheckBusinessService.addSysCheckBusiness dto={}",dto);
			return 0L;
		}
		SysCheckBusiness sc = dto.copyTo(SysCheckBusiness.class);
		sysCheckBusinessMapper.insertSysCheckBusiness(sc);
		return sc.getId();
	}

	/**
	 *  根据主键得到审核业务表记录
	 * @param id
	 * @return
	 */
	public SysCheckBusinessDTO getByPrimaryKey(Long id){
		if(id == null || id == 0) {
			logger.info("sysCheckBusinessService.getByPrimaryKey error:id={}",id);
			return null;
		}
		SysCheckBusiness po = sysCheckBusinessMapper.getByPrimaryKey(id);
		if(po==null) return null;
		return po.copyTo(SysCheckBusinessDTO.class);
	}
	
	/**
	 * 更新po信息
	 * @param po
	 * @return
	 */
	public int updatepo(SysCheckBusinessDTO dto){
		if(dto == null || dto.getId() == null || dto.getId() == 0) {
			logger.info("sysCheckBusinessService.updatepo dto={}",dto);
			return 0;
		}
		return sysCheckBusinessMapper.updateSysCheckBusiness(dto.copyTo(SysCheckBusiness.class));//object转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数查询 获取审核业务列表  带分页
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysCheckBusinessDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size){
		if(searchMap==null || searchMap.isEmpty() || size<1) {
			logger.info("sysCheckBusinessService.getListByWhere searchMap={}，start={},size={}",searchMap,start,size);
			return Collections.emptyList();
		}
		List<SysCheckBusinessDTO> sysCheckBusinessList = sysCheckBusinessMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(sysCheckBusinessList)) return Collections.emptyList();
		return sysCheckBusinessList;
	}

	/**
	 * 根据参数查询审核业务列表 
	 * @param searchMap
	 * @return
	 */
	public List<SysCheckBusinessDTO> getListByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("sysCheckBusinessService.getListByWhere searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysCheckBusinessDTO> sysCheckBusinessList = sysCheckBusinessMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(sysCheckBusinessList)) return Collections.emptyList();
		return sysCheckBusinessList;
	}
	
	
	/**
	 * 根据参数获取审核业务数量
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			return 0;
		}
		return sysCheckBusinessMapper.getCountByWhere(searchMap);
	}
	
	/**
	 * 根据ID删除
	 * @param id
	 * @return
	 */
	public int deleteById(Long id){
		return sysCheckBusinessMapper.deleteById(id);
	}
	
	/**
	 * 根据参数查询审核业务列表 
	 * @param searchMap
	 * @return
	 */
	public List<SysCheckBusinessDTO> getBusinessList(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("sysCheckBusinessService.getBusinessList searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysCheckBusinessDTO> sysCheckBusinessList = sysCheckBusinessMapper.getBusinessList(searchMap);
		if(CollectionUtils.isEmpty(sysCheckBusinessList)) return Collections.emptyList();
		return sysCheckBusinessList;
	}

	public List<SysCheckBusinessHistoryDTO> getHosListByBusId(Long busId, Integer page, Integer size) {
		SysCheckBusinessHistory  examHistory=new SysCheckBusinessHistory();
		examHistory.setBusId(busId);
		List<SysCheckBusinessHistory> sysCheckBusinessHistory= checkBusinessHistoryMapper.selectByRowBounds(examHistory,new RowBounds((page-1)*size,size));
		if(CollectionUtils.isEmpty(sysCheckBusinessHistory)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCheckBusinessHistory,SysCheckBusinessHistoryDTO.class);
	}

    public SysCheckBusinessHistoryDTO getHistoryById(Long hisId) {
        SysCheckBusinessHistory sysCheckBusinessHistory=checkBusinessHistoryMapper.selectByPrimaryKey(hisId);
        SysCheckBusinessHistoryDTO sysCheckBusinessHistoryDTO=sysCheckBusinessHistory.copyTo(SysCheckBusinessHistoryDTO.class);
        return sysCheckBusinessHistoryDTO;
    }
    
    public Long insertCheckBusinessHistory(SysCheckBusinessHistoryDTO po)  {
    	SysCheckBusinessHistory history = po.copyTo(SysCheckBusinessHistory.class);
        checkBusinessHistoryMapper.insertSelective(history);
        Long id = history.getId();
        return id;
    }
}
