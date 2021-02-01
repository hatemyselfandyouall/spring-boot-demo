package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.po.SysBusinessType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 对业务类型表操作
 * @author haoxz11MyBatis 
 * @created Tue May 07 11:54:06 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysBusinessTypeMapper {
	/**
	 * 插入业务类型表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertSysBusinessType(SysBusinessType record);

	/**
	 * 根据主键得到业务类型表表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysBusinessType getByPrimaryKey(Long id);

	/**
	 * 更新业务类型表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysBusinessType(SysBusinessType record);

	/**
	 * 搜索业务类型表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysBusinessType> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索业务类型表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysBusinessType> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索业务类型表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
	
	/**
	 * 根据渠道、区域、大类获取业务类型
	 * @param busPar
	 * @return
	 */
	List<SysBusinessType> getListByBusPar(@Param("busPar") String busPar);
}