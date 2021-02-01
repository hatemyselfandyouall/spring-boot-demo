package com.wangxinenpu.springbootdemo.dao.mapper;


import com.wangxinenpu.springbootdemo.dataobject.po.SysArea;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysAreaNameDTO;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 对区域表操作
 * @author haoxz11MyBatis 
 * @created Sat Mar 30 01:44:16 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysAreaMapper {
	/**
	 * 插入区域表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertSysArea(SysArea record);

	/**
	 * 根据主键得到区域表表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysArea getByPrimaryKey(Long id);

	/**
	 * 更新区域表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysArea(SysArea record);

	/**
	 * 搜索区域表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysArea> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索区域表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysArea> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索区域表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
	
	/**
	 * 查询所有区域
	 * @return
	 */
	List<SysArea> findAll();
	
	/**
	 * 根据id查询下级区域
	 * @param id
	 * @return
	 */
	List<SysArea> findByParentId(Long id);
	
	/**
	 * 查询关联区域
	 * @param idpath
	 * @return
	 */
	List<SysArea> findByIdpath(String idpath);
	
	/**
	 * 查询用户关联区域（医保用）
	 * @param userId
	 * @return
	 */
	List<SysArea> queryAreaListByUserId(Long userId);

	SysArea findByAreaName(String areaName);

	List<SysArea> listByAreaName(String areaName);

	List<SysArea> findAllByParentIdNull();

	List<SysArea> findAllByParentId(String parentId);

	SysAreaNameDTO getAreaInfo(Long id);

}