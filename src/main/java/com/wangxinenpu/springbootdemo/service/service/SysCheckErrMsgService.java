package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckErrMsgDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysCheckErrMsgMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysCheckErrMsg;
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
 * 业务异常消息service
 * @author Administrator
 *
 */
@Service
public class SysCheckErrMsgService {
	private static Logger logger = LoggerFactory.getLogger(SysCheckErrMsgService.class);
	@Resource
	private SysCheckErrMsgMapper sysCheckErrMsgMapper;
	
	/**
	 * 新增业务异常消息po信息
	 * @param po
	 * @return
	 */
	public Long addSysCheckErrMsg(SysCheckErrMsgDTO dto){
		if(dto == null) {
			logger.info("sysCheckErrMsgService.addSysCheckErrMsg dto={}",dto);
			return 0L;
		}
		SysCheckErrMsg sc = dto.copyTo(SysCheckErrMsg.class);
		sysCheckErrMsgMapper.insertSysCheckErrMsg(sc);
		return sc.getId();
	}

	/**
	 *  根据主键得到业务异常消息表记录
	 * @param id
	 * @return
	 */
	public SysCheckErrMsgDTO getByPrimaryKey(Long id){
		if(id == null || id == 0) {
			logger.info("sysCheckErrMsgService.getByPrimaryKey error:id={}",id);
			return null;
		}
		SysCheckErrMsg po = sysCheckErrMsgMapper.getByPrimaryKey(id);
		if(po==null) return null;
		return po.copyTo(SysCheckErrMsgDTO.class);
	}
	
	/**
	 * 更新po信息
	 * @param po
	 * @return
	 */
	public int updatepo(SysCheckErrMsgDTO dto){
		if(dto == null || dto.getId() == null || dto.getId() == 0) {
			logger.info("sysCheckErrMsgService.updatepo dto={}",dto);
			return 0;
		}
		return sysCheckErrMsgMapper.updateSysCheckErrMsg(dto.copyTo(SysCheckErrMsg.class));//object转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数查询 获取业务异常消息列表  带分页
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysCheckErrMsgDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size){
		if(searchMap==null || searchMap.isEmpty() || size<1) {
			logger.info("sysCheckErrMsgService.getListByWhere searchMap={}，start={},size={}",searchMap,start,size);
			return Collections.emptyList();
		}
		List<SysCheckErrMsg> sysCheckErrMsgList = sysCheckErrMsgMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(sysCheckErrMsgList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCheckErrMsgList, SysCheckErrMsgDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询业务异常消息列表 
	 * @param searchMap
	 * @return
	 */
	public List<SysCheckErrMsgDTO> getListByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("sysCheckErrMsgService.getListByWhere searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysCheckErrMsg> sysCheckErrMsgList = sysCheckErrMsgMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(sysCheckErrMsgList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCheckErrMsgList, SysCheckErrMsgDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数获取业务异常消息数量
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			return 0;
		}
		return sysCheckErrMsgMapper.getCountByWhere(searchMap);
	}
	
	/**
	 * 根据ID删除
	 * @param id
	 * @return
	 */
	public int deleteById(Long id){
		return sysCheckErrMsgMapper.deleteById(id);
	}

}
