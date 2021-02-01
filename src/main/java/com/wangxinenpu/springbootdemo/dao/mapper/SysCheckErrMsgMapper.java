package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.po.SysCheckErrMsg;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 对业务异常消息表操作
 * @author haoxz11MyBatis 
 * @created Tue May 28 01:26:21 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysCheckErrMsgMapper {
	/**
	 * 插入业务异常消息表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertSysCheckErrMsg(SysCheckErrMsg record);

	/**
	 * 根据主键得到业务异常消息表表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysCheckErrMsg getByPrimaryKey(Long id);

	/**
	 * 更新业务异常消息表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysCheckErrMsg(SysCheckErrMsg record);

	/**
	 * 搜索业务异常消息表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysCheckErrMsg> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索业务异常消息表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysCheckErrMsg> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索业务异常消息表的记录数量
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
}