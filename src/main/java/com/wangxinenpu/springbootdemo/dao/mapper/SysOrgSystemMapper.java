package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.vo.systemManage.SystemManageListVo;
import com.wangxinenpu.springbootdemo.dataobject.po.SysOrgSystem;

import java.util.List;

/**
 * 
 * 对机构系统表操作
 * @author haoxz11MyBatis 
 * @created Fri Apr 19 10:59:43 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysOrgSystemMapper {

		int insertOrgSystem(SysOrgSystem record);

		int deleteByOrgId(Long orgId);

		List<SysOrgSystem> findByOrgId(Long orgId);

		List<SystemManageListVo> findListVoByOrgId(Long orgId);
}