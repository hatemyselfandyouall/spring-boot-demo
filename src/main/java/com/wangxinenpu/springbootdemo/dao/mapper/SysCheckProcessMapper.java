package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.vo.response.SysCheckProcessRespDTO;
import com.wangxinenpu.springbootdemo.dataobject.po.SysCheckProcess;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 对审核流程表操作
 * @author haoxz11MyBatis 
 * @created Tue May 28 01:26:21 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysCheckProcessMapper {
	/**
	 * 插入审核流程表记录
	 *
	 * @haoxz11MyBatis
	 */
	Long insertSysCheckProcess(SysCheckProcess record);

	/**
	 * 根据主键得到审核流程表表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysCheckProcess getByPrimaryKey(Long id);

	/**
	 * 更新审核流程表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysCheckProcess(SysCheckProcess record);

	/**
	 * 搜索审核流程表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysCheckProcess> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索审核流程表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysCheckProcess> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索审核流程表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
	
	/**
	 * 根据ID删除
	 * @param id
	 * @return
	 */
	int deleteById(Long id);
	
	String findVersionByConfigId(Long configId);
	
	String findIdByConfigIdAndStep(HashMap<String, Object> searchMap);
	
	List<SysCheckProcess> getListByConfigId(Long configId);
	
	List<SysCheckProcess> queryProListByProIds(@Param("proIds") String proIds);

	List<String> queryProStepByUserId(@Param("orgId") Long orgId, @Param("funId") Long funId, @Param("userId") String userId);

	/**
	 * 根据当前登录人和状态查询当前由我审核的流程列表
	 * @user xxh
	 * @since 2020年6月8日下午4:05:33
	 */
	public List<SysCheckProcessRespDTO> getMyCheckingProcessListByParam(@Param("userId") Long userId, @Param("checkResult") String checkResult);
	
	/**
	 * 批量新增
	 * @param list
	 */
	void insertBatch(List<SysCheckProcess> list);

}