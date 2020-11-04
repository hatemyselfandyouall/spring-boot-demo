package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.constant.SysbaseCacheEnum;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckProcessDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.response.SysCheckProcessRespDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysCheckProcessMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysCheckProcess;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import star.bizbase.exception.BizRuleException;
import star.modules.cache.CacheKeyLock;
import star.modules.cache.CachesKeyService;
import star.modules.cache.enumerate.BaseCacheEnum;
import star.vo.BaseVo;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 审核流程service
 * @author Administrator
 *
 */
@Service
public class SysCheckProcessService {
	private static Logger logger = LoggerFactory.getLogger(SysCheckProcessService.class);
	@Resource
	private SysCheckProcessMapper sysCheckProcessMapper;
	@Resource
	private CachesKeyService cachesKeyService;
	
	/**
	 * 新增审核流程po信息
	 * @return
	 */
	public Long addSysCheckProcess(SysCheckProcessDTO dto){
		if(dto == null) {
			logger.info("sysCheckProcessService.addSysCheckProcess dto={}",dto);
			return 0L;
		}
		SysCheckProcess sc = dto.copyTo(SysCheckProcess.class);
		sysCheckProcessMapper.insertSysCheckProcess(sc);
		return sc.getId();
	}

	/**
	 *  根据主键得到审核流程表记录
	 * @param id
	 * @return
	 */
	public SysCheckProcessDTO getByPrimaryKey(Long id){
		if(id == null || id == 0) {
			logger.info("sysCheckProcessService.getByPrimaryKey error:id={}",id);
			return null;
		}
		SysCheckProcess po = sysCheckProcessMapper.getByPrimaryKey(id);
		if(po==null) return null;
		return po.copyTo(SysCheckProcessDTO.class);
	}
	
	/**
	 * 更新po信息
	 * @param po
	 * @return
	 */
	public int updatepo(SysCheckProcessDTO dto){
		if(dto == null || dto.getId() == null || dto.getId() == 0) {
			logger.info("sysCheckProcessService.updatepo dto={}",dto);
			return 0;
		}
		return sysCheckProcessMapper.updateSysCheckProcess(dto.copyTo(SysCheckProcess.class));//object转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数查询 获取审核流程列表  带分页
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysCheckProcessDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size){
		if(searchMap==null || searchMap.isEmpty() || size<1) {
			logger.info("sysCheckProcessService.getListByWhere searchMap={}，start={},size={}",searchMap,start,size);
			return Collections.emptyList();
		}
		List<SysCheckProcess> sysCheckProcessList = sysCheckProcessMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(sysCheckProcessList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCheckProcessList, SysCheckProcessDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询审核流程列表 
	 * @param searchMap
	 * @return
	 */
	public List<SysCheckProcessDTO> getListByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("sysCheckProcessService.getListByWhere searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysCheckProcess> sysCheckProcessList = sysCheckProcessMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(sysCheckProcessList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCheckProcessList, SysCheckProcessDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}
	
	
	/**
	 * 根据参数获取审核流程数量
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			return 0;
		}
		return sysCheckProcessMapper.getCountByWhere(searchMap);
	}
	
	/**
	 * 根据ID删除
	 * @param id
	 * @return
	 */
	public int deleteById(Long id){
		return sysCheckProcessMapper.deleteById(id);
	}
	
	 /**
     * 根据审核配置Id查询最大版本号
     * @param configId
     * @return
     */
	public String findVersionByConfigId(Long configId){
		return sysCheckProcessMapper.findVersionByConfigId(configId);
	}
	
	/**
     * 根据审核配置Id和步骤查询最大流程ID
     * @param searchMap
     * @return
     */
	public String findIdByConfigIdAndStep(HashMap<String, Object> searchMap){
		return sysCheckProcessMapper.findIdByConfigIdAndStep(searchMap);
	}
	
	/**
	 * 根据配置id取最新流程
	 * @param configId
	 * @return
	 */
	public List<SysCheckProcessDTO> getListByConfigId(Long configId){
		List<SysCheckProcess> sysCheckProcessList = sysCheckProcessMapper.getListByConfigId(configId);
		if(CollectionUtils.isEmpty(sysCheckProcessList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCheckProcessList, SysCheckProcessDTO.class);
	}
	
	public List<SysCheckProcessDTO> queryProListByProIds(String proIds){
		List<SysCheckProcess> sysCheckProcessList = sysCheckProcessMapper.queryProListByProIds(proIds);
		if(CollectionUtils.isEmpty(sysCheckProcessList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysCheckProcessList, SysCheckProcessDTO.class);
	}

	public List<String> queryProStepByUserId(Long orgId, Long funId, String userId) {
		return sysCheckProcessMapper.queryProStepByUserId(orgId,funId,userId);
	}
	
	

	 /**
    *根据当前登录人和状态查询当前由我审核的流程SysCheckProcessRespDTO列表）
	 * @throws BizRuleException 
    * @user xxh
    * @since 2020年6月8日下午2:41:00
    */
   public List<SysCheckProcessRespDTO> getCacheMyCheckingProcessRespDTOByParam(Long userId, String checkResult) throws BizRuleException{
   		if(userId == null || StringUtil.isEmpty(checkResult)) {
			logger.info("getMyCheckingBusIdListByParam error:userId = {},checkResult={}",userId,checkResult);
			throw new BizRuleException("-2009","获取由我审核业务列表参数异常");
		}
		
		String cacheKey = userId+"_"+checkResult;
		return new CacheKeyLock(cachesKeyService, 10) {//缓存10秒
			@Override
			protected Object doGetList(BaseCacheEnum type, String key) {
				return sysCheckProcessMapper.getMyCheckingProcessListByParam(userId, checkResult);
			}
		}.getCache(SysbaseCacheEnum.CACHEMYCHECKINGPROCESSRESPDTOBYPARAM, cacheKey);
		
   }
   
   public void insertBatch(List<SysCheckProcessDTO> list){
		sysCheckProcessMapper.insertBatch(BaseVo.copyListTo(list, SysCheckProcess.class));
	}
}
