package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.po.SysOrginstype;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 对机构险种表操作
 * @author haoxz11MyBatis 
 * @created Fri Apr 19 10:59:43 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysOrginstypeMapper {
	/**
	 * 插入机构险种表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertSysOrginstype(SysOrginstype record);

	/**
	 * 根据主键得到机构险种表表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysOrginstype getByPrimaryKey(Long id);

	/**
	 * 更新机构险种表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysOrginstype(SysOrginstype record);

	/**
	 * 搜索机构险种表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysOrginstype> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索机构险种表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysOrginstype> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索机构险种表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
	
	/**
	 * 根据机构ID查询
	 * @param orgId
	 * @return
	 */
	List<SysOrginstype> findByOrgId(Long orgId);
	
	/**
	 * 根据角色ID删除
	 * @param orgId
	 * @return
	 */
	int deleteByOrgId(Long orgId);
	
	/**
	 * 根据机构ID和险种ID查询是否绑定
	 * @param searchMap
	 * @return
	 */
	List<SysOrginstype> existenceByOrgIdAndInsId(HashMap<String, Object> searchMap);
}