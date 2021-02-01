package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.po.SysCode;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 对代码维护表操作
 * @author haoxz11MyBatis 
 * @created Fri Apr 19 10:59:43 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysCodeMapper {
	/**
	 * 插入代码维护表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertSysCode(SysCode record);

	/**
	 * 根据主键得到代码维护表表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysCode getByPrimaryKey(Long id);

	/**
	 * 更新代码维护表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysCode(SysCode record);

	/**
	 * 搜索代码维护表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysCode> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索代码维护表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysCode> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索代码维护表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
	
	/**
	 * 根据代码类型查询
	 * @param codeType
	 * @return
	 */
	List<SysCode> findByCodeType(String codeType);

	/**
	 * 根据代码类型查询
	 * @param codeType
	 * @return
	 */
	SysCode findByCodeName(String codeType);
	
	/**
	 * 根据主键ID删除
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Long id);
	
	/**
	 * 查询相同类型代码值是否重复
	 * @param searchMap
	 * @return
	 */
	List<SysCode> findByCodeTypeAndCodeValue(HashMap<String, Object> searchMap);
}