package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysUserEmpowerDTO;
import com.wangxinenpu.springbootdemo.dataobject.po.SysUserEmpower;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 对用户授权操作
 * @author haoxz11MyBatis 
 * @created Tue May 28 01:26:21 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysUserEmpowerMapper {
	/**
	 * 插入用户授权记录
	 *
	 * @haoxz11MyBatis
	 */
	Long insertSysUserEmpower(SysUserEmpower record);

	/**
	 * 根据主键得到用户授权表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysUserEmpower getByPrimaryKey(Long id);

	/**
	 * 更新用户授权记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysUserEmpower(SysUserEmpower record);

	/**
	 * 搜索用户授权列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysUserEmpower> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索用户授权列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysUserEmpower> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索用户授权的记录数量
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
	
	List<SysUserEmpowerDTO> getlistByUserIds(@Param("startTime") String startTime, @Param("endTime") String endTime);
}