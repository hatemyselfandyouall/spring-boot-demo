package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.po.SysCheckUser;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 对审核流程对应用户表操作
 * @author haoxz11MyBatis 
 * @created Tue May 28 01:26:21 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysCheckUserMapper {
	/**
	 * 插入审核流程对应用户表记录
	 *
	 * @haoxz11MyBatis
	 */
	Long insertSysCheckUser(SysCheckUser record);

	/**
	 * 根据主键得到审核流程对应用户表表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysCheckUser getByPrimaryKey(Long id);

	/**
	 * 更新审核流程对应用户表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysCheckUser(SysCheckUser record);

	/**
	 * 搜索审核流程对应用户表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysCheckUser> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索审核流程对应用户表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysCheckUser> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索审核流程对应用户表的记录数量
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
	 * 根据流程ID删除
	 * @param proId
	 * @return
	 */
	int deleteByProId(Long proId);
}