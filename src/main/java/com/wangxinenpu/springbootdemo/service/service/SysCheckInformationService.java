package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckInformationDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.response.SysCheckProcessRespDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysCheckInformationMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysCheckInformation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import star.bizbase.exception.BizRuleException;
import star.modules.cache.CacheKeyLock;
import star.modules.cache.CachesKeyService;
import star.modules.cache.enumerate.BaseCacheEnum;
import star.util.ExceptionUtil;
import star.vo.BaseVo;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 审核信息service
 * @author Administrator
 *
 */
@Service
public class SysCheckInformationService {
	private static Logger logger = LoggerFactory.getLogger(SysCheckInformationService.class);
	@Resource
	private SysCheckInformationMapper sysCheckInformationMapper;
	@Resource
	private CachesKeyService cachesKeyService;
	@Autowired
	private SysCheckProcessService sysCheckProcessService;
	
	/**
	 * 新增审核信息po信息
	 * @param po
	 * @return
	 */
	public Long addSysCheckInformation(SysCheckInformationDTO dto){
		if(dto == null) {
			logger.info("sysCheckInformationService.addSysCheckInformation dto={}",dto);
			return 0L;
		}
		SysCheckInformation sc = dto.copyTo(SysCheckInformation.class);
		sysCheckInformationMapper.insertSysCheckInformation(sc);
		return sc.getId();
	}

	/**
	 *  根据主键得到审核信息表记录
	 * @param id
	 * @return
	 */
	public SysCheckInformationDTO getByPrimaryKey(Long id){
		if(id == null || id == 0) {
			logger.info("sysCheckInformationService.getByPrimaryKey error:id={}",id);
			return null;
		}
		SysCheckInformation po = sysCheckInformationMapper.getByPrimaryKey(id);
		if(po==null) return null;
		return po.copyTo(SysCheckInformationDTO.class);
	}
	
	/**
	 * 更新po信息
	 * @param po
	 * @return
	 */
	public int updatepo(SysCheckInformationDTO dto){
		if(dto == null || dto.getId() == null || dto.getId() == 0) {
			logger.info("sysCheckInformationService.updatepo dto={}",dto);
			return 0;
		}
		return sysCheckInformationMapper.updateSysCheckInformation(dto.copyTo(SysCheckInformation.class));//object转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数查询 获取审核信息列表  带分页
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysCheckInformationDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size){
		if(searchMap==null || searchMap.isEmpty() || size<1) {
			logger.info("sysCheckInformationService.getListByWhere searchMap={}，start={},size={}",searchMap,start,size);
			return Collections.emptyList();
		}
		List<SysCheckInformation> sysCheckInformationList = sysCheckInformationMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(sysCheckInformationList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCheckInformationList, SysCheckInformationDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询审核信息列表 
	 * @param searchMap
	 * @return
	 */
	public List<SysCheckInformationDTO> getListByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("sysCheckInformationService.getListByWhere searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysCheckInformation> sysCheckInformationList = sysCheckInformationMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(sysCheckInformationList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCheckInformationList, SysCheckInformationDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数获取审核信息数量
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			return 0;
		}
		return sysCheckInformationMapper.getCountByWhere(searchMap);
	}
	
	/**
	 * 根据ID删除
	 * @param id
	 * @return
	 */
	public int deleteById(Long id){
		return sysCheckInformationMapper.deleteById(id);
	}
	
	public List<SysCheckInformationDTO> queryListByBusId(HashMap<String, Object> searchMap){
		List<SysCheckInformation> sysCheckInformationList = sysCheckInformationMapper.queryListByBusId(searchMap);
		if(CollectionUtils.isEmpty(sysCheckInformationList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCheckInformationList, SysCheckInformationDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	public List<SysCheckInformationDTO> getListByCheckPeople(HashMap<String, Object> searchMap){
		List<SysCheckInformation> sysCheckInformationList = sysCheckInformationMapper.getListByCheckPeople(searchMap);
		if(CollectionUtils.isEmpty(sysCheckInformationList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCheckInformationList, SysCheckInformationDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	public int getCountByCheckPeople(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			return 0;
		}
		return sysCheckInformationMapper.getCountByCheckPeople(searchMap);
	}
	
	public List<Long> findBusIdByUserId(Long userId, String checkResult) {
		return sysCheckInformationMapper.findBusIdByUserId(userId,checkResult);
	}
	/**
	 * 根据流程ID和业务ID删除
	 * @param searchMap
	 * @return
	 */
	public int deleteByProIdAndBusId(HashMap<String, Object> searchMap){
		return sysCheckInformationMapper.deleteByProIdAndBusId(searchMap);
	}
	
	 /**
     * 根据当前登录人和状态查询当前由我审核的且上级审核通过的业务列表）
	 * @throws BizRuleException 
     * @user xxh
     * @since 2020年6月8日下午2:41:00
     */
    public List<Long> getCacheMyCheckingBusIdListByPrePass(Long userId, String checkResult) throws BizRuleException{
    	if(userId == null || StringUtil.isEmpty(checkResult)) {
			logger.info("getMyCheckingBusIdListByParam error:userId = {},checkResult={}",userId,checkResult);
			throw new BizRuleException("-2009","获取由我审核业务列表参数异常");
		}
    	logger.info("getMyCheckingBusIdListByParam+userId={},checkResult={}",userId,checkResult);
		String cacheKey = userId+"_"+checkResult;
		return null;
//		return new CacheKeyLock(cachesKeyService, 10) {//缓存10S
//			@Override
//			protected Object doGetList(BaseCacheEnum type, String key) {
//				// 1由我审核的流程列表
//				List<SysCheckProcessRespDTO> mycpresplist = new ArrayList<SysCheckProcessRespDTO>();
//				//查询待审核以外其他我审核过或作废的业务
//				if(!"0".equals(checkResult)){
//					List<Long> myretlist = sysCheckInformationMapper.findBusIdByUserId(userId, checkResult);
//					logger.info("查询待审核以外其他我审核过或作废的业务 myretlist={}",myretlist);
//					return myretlist;
//				}
//				try {
//					mycpresplist = sysCheckProcessService.getCacheMyCheckingProcessRespDTOByParam(userId, checkResult);
//
//				} catch (BizRuleException e) {
//					logger.info("error:{}",ExceptionUtil.getMessage(e));
//				}
//				logger.info("根据当前登录人和状态查询当前由我审核的流程 mycpresplist={}",mycpresplist);
//				if(CollectionUtils.isEmpty(mycpresplist))  return null;//无流程审核中事项
//				//由我审核的流程，且上级审核审核通过的事项列表
//				List<SysCheckProcessRespDTO> prebuslist = SysCheckProcessPresAss.getPreSysCheckProcessRespList(mycpresplist);
//				List<Long> retlist = null;
//				List<Long> ret0list = null;
//				if(CollectionUtils.isNotEmpty(prebuslist)) {
//					retlist = sysCheckInformationMapper.getMyCheckingBusIdListByPrePass(userId, checkResult,prebuslist);
//					if(retlist.size()>0){
//						List<Long> busids =sysCheckInformationMapper.getCheckResult2BusId(retlist);
//						if(CollectionUtils.isNotEmpty(busids)){
//							for(int i = 0;i<retlist.size();i++){
//								for(int j= 0;j<busids.size();j++){
//									if(retlist.get(i)==busids.get(j)){//把存在已经驳回的业务剔除掉
//										retlist.remove(i);
//										break;
//									}
//								}
//							}
//						}
//					}
//					logger.info("根据当前登录人和状态查询当前由我审核的业务id retlist={}",retlist);
//				}
//				List<SysCheckProcessRespDTO> myStep0buslist = SysCheckProcessPresAss.getMyStep0SysCheckProcessRespList(mycpresplist);
//				if(CollectionUtils.isNotEmpty(myStep0buslist)) {
//					 ret0list = sysCheckInformationMapper.getMyStep0CheckingBusIdListByPrePass(userId, checkResult,myStep0buslist);
//					 logger.info("getCacheMyCheckingBusIdListByPrePass ret0list={}",ret0list);
//				}
//
//				List<Long> myretlist = SysCheckProcessPresAss.getMyCheckBusidList(retlist, ret0list);
//				logger.info("返回的opsenoList列表 myretlist={}",myretlist);
//				if(CollectionUtils.isEmpty(myretlist)) return null;
//				return myretlist;
//			}
//		}.getCache(SysbaseCacheEnum.CACHEMYCHECKINGBUSIDLISTBYPARAM, cacheKey);
		
    }
}
