package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.po.SysUserArea;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 对用户管辖行政区划表操作
 * @author haoxz11MyBatis 
 * @created Mon Mar 25 14:59:55 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysUserAreaMapper {
	/**
	 * 插入用户管辖行政区划表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertSysUserArea(SysUserArea record);

	/**
	 * 根据主键得到用户管辖行政区划表表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysUserArea getByPrimaryKey(@Param("areaId") String areaId, @Param("userId") String userId);

	/**
	 * 更新用户管辖行政区划表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysUserArea(SysUserArea record);

	/**
	 * 搜索用户管辖行政区划表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysUserArea> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索用户管辖行政区划表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysUserArea> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索用户管辖行政区划表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
	
	/**
	 * 查询用户绑定的区域
	 * @param userId
	 * @return
	 */
	List<SysUserArea> findByUserId(String userId);
	
	/**
	 * 根据用户ID删除
	 * @param userId
	 * @return
	 */
	int deleteByUserId(SysUserArea sysUser);
	
	/**
	 * 批量新增
	 * @param list
	 */
	void insertByBatch(List<SysUserArea> list);
	
}