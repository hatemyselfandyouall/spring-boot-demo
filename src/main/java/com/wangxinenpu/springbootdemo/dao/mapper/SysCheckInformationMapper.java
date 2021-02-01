package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.vo.response.SysCheckProcessRespDTO;
import com.wangxinenpu.springbootdemo.dataobject.po.SysCheckInformation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 对审核信息表操作
 * @author haoxz11MyBatis 
 * @created Tue May 28 01:26:21 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysCheckInformationMapper {
	/**
	 * 插入审核信息表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertSysCheckInformation(SysCheckInformation record);

	/**
	 * 根据主键得到审核信息表表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysCheckInformation getByPrimaryKey(Long id);

	/**
	 * 更新审核信息表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysCheckInformation(SysCheckInformation record);

	/**
	 * 搜索审核信息表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysCheckInformation> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索审核信息表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysCheckInformation> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索审核信息表的记录数量
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
     * 根据业务ID查询
     * @param
     * @return
     */
	List<SysCheckInformation> queryListByBusId(HashMap<String, Object> searchMap);
	
	/**
     * 查询由我审核的信息
     * @param
     * @return
     */
	List<SysCheckInformation> getListByCheckPeople(HashMap<String, Object> searchMap);
	
	/**
	 * 查询由我审核的信息记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByCheckPeople(HashMap<String, Object> searchMap);
	
	/**
     * 根据当前登录人和状态查询由我审核的业务ID
     * @param userId
     * @return
     */
	List<Long> findBusIdByUserId(@Param("userId") Long userId, @Param("checkResult") String checkResult);

	/**
	 * 根据流程ID和业务ID删除
	 * @param searchMap
	 * @return
	 */
	int deleteByProIdAndBusId(HashMap<String, Object> searchMap);
	 /**
     * 根据当前登录人和状态查询当前由我审核的业务id list-（上级审核通过，由我审核的所有opeson id 列表）
     * @param mycpresplist-由我审核流程中的对象列表
     * @user xxh
     * @since 2020年6月8日下午2:41:00
     */
    public List<Long> getMyCheckingBusIdListByPrePass(@Param("userId") Long userId, @Param("checkResult") String checkResult, @Param("mycpresplist") List<SysCheckProcessRespDTO> mycpresplist);
    /**
     * 根据当前登录人和状态查询当前由我审核的MyStep0业务id list-（上级审核通过，由我审核的所有opeson id 列表）
     * @param mycpresplist-由我审核流程中的对象列表
     * @user xxh
     * @since 2020年6月8日下午2:41:00
     */
    public List<Long> getMyStep0CheckingBusIdListByPrePass(@Param("userId") Long userId, @Param("checkResult") String checkResult, @Param("mycpresplist") List<SysCheckProcessRespDTO> mycpresplist);

    /**
     * 查询审核信息中有不通过的业务
     * @param busIdList
     * @return
     */
    public List<Long> getCheckResult2BusId(@Param("busIdList") List<Long> busIdList);
    
}