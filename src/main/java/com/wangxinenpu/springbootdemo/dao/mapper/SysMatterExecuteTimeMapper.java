package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.po.SysMatterExecuteTime;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 对办理时间表操作
 * @author haoxz11MyBatis 
 * @created Tue May 28 01:26:21 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysMatterExecuteTimeMapper {
	/**
	 * 插入办理时间表记录
	 *
	 * @haoxz11MyBatis
	 */
	Long insertSysMatterExecuteTime(SysMatterExecuteTime record);

	/**
	 * 根据主键得到办理时间表表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysMatterExecuteTime getByPrimaryKey(Long id);

	/**
	 * 更新办理时间表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysMatterExecuteTime(SysMatterExecuteTime record);

	/**
	 * 搜索办理时间表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysMatterExecuteTime> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索办理时间表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysMatterExecuteTime> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索办理时间表的记录数量
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
	
	int deleteByConfigId(Long configId);
}