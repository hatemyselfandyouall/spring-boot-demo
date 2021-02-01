package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.po.SysReqBusInfo;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

public interface SysReqBusInfoMapper {
	/**
	 * 插入审核结果返回信息表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertSysReqBusInfo(SysReqBusInfo record);

	/**
	 * 根据主键得到审核结果返回信息表表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysReqBusInfo getByPrimaryKey(Long id);

	/**
	 * 更新审核结果返回信息表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysReqBusInfo(SysReqBusInfo record);

	/**
	 * 搜索审核结果返回信息表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysReqBusInfo> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索审核结果返回信息表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysReqBusInfo> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索审核结果返回信息表的记录数量
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
	
}