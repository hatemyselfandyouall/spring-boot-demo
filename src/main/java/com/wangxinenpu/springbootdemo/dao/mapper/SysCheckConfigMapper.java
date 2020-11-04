package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.po.SysCheckConfig;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 对审核权限配置表操作
 * @author haoxz11MyBatis 
 * @created Tue May 28 01:26:21 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysCheckConfigMapper {
	/**
	 * 插入审核权限配置表记录
	 *
	 * @haoxz11MyBatis
	 */
	Long insertSysCheckConfig(SysCheckConfig record);

	/**
	 * 根据主键得到审核权限配置表表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysCheckConfig getByPrimaryKey(Long id);

	/**
	 * 更新审核权限配置表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysCheckConfig(SysCheckConfig record);

	/**
	 * 搜索审核权限配置表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysCheckConfig> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索审核权限配置表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysCheckConfig> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索审核权限配置表的记录数量
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
	
	/**
	 * 批量修改
	 * @param list
	 */
	void updateByBatch(List<SysCheckConfig> list);
}