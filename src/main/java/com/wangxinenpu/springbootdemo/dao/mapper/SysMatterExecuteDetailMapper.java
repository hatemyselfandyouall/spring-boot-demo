package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.po.SysMatterExecuteDetail;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 对事项执行详细表操作
 * @author haoxz11MyBatis 
 * @created Tue May 28 01:26:21 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysMatterExecuteDetailMapper {
	/**
	 * 插入事项执行详细表记录
	 *
	 * @haoxz11MyBatis
	 */
	Long insertSysMatterExecuteDetail(SysMatterExecuteDetail record);

	/**
	 * 根据主键得到事项执行详细表表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysMatterExecuteDetail getByPrimaryKey(Long id);

	/**
	 * 更新事项执行详细表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysMatterExecuteDetail(SysMatterExecuteDetail record);

	/**
	 * 搜索事项执行详细表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysMatterExecuteDetail> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索事项执行详细表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysMatterExecuteDetail> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索事项执行详细表的记录数量
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