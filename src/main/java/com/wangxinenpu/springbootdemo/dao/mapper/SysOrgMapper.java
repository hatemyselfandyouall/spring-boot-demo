package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysOrgDTO;
import com.wangxinenpu.springbootdemo.dataobject.po.SysOrg;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

/**
 *
 * 对系统机构表操作
 * @author haoxz11MyBatis
 * @created Mon Mar 25 14:59:55 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface SysOrgMapper {
	/**
	 * 插入系统机构表记录
	 *
	 * @haoxz11MyBatis
	 */
	Long insertSysOrg(SysOrg record);

	/**
	 * 根据主键得到系统机构表表记录
	 *
	 * @haoxz11MyBatis
	 */
	SysOrg getByPrimaryKey(Long id);

	/**
	 * 更新系统机构表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateSysOrg(SysOrg record);

	/**
	 * 搜索系统机构表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<SysOrg> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索系统机构表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<SysOrg> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索系统机构表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);

	/**
	 * 根据区域ID查询机构列表
	 * @param regionCode
	 * @return
	 */
	List<SysOrg> findByRegioncode(String regionCode);

	/**
	 * 根据id查询下级机构
	 * @param id
	 * @return
	 */
	List<SysOrg> findByParentId(Long id);

	/**
	 * 根据idpath查询下级机构
	 * @param idpath
	 * @return
	 */
	List<SysOrg> findByIdpath(String idpath);

	/**
	 * 根据机构名称，机构代码查询是否存在
	 * @param orgName
	 * @param orgenterCode
	 * @return
	 */
	List<SysOrg> checkOrgNameOrgenterCode(@Param("orgName") String orgName, @Param("orgenterCode") String orgenterCode);

	/**
	 * 根据机构名称查询
	 * @param orgName
	 * @return
	 */
	SysOrg findByName(String orgName);

	/**
	 * 根据机构名称模糊查询
	 * @param orgName
	 * @return
	 */
	List<SysOrg> findByNameLike(String orgName);

	/**
	 * 根据主键ID删除
	 * @param id
	 */
	void deleteByPrimaryKey(Long id);

	/**
	 * 通过天正一体化id删除机构
	 * @param uuid
	 */
	void deleteByUUid(String uuid);


	/**
	 * 通过行政区和险种获取机构信息
	 * @param areaId
	 * @param insId
	 * @return
	 */
	public List<SysOrg> findByAreaIdAndInsId(HashMap<String, Object> searchMap);

	/**
	 * 获取所有市
	 * @param regionCode
	 * @return
	 */
	public List<SysOrg> findCityByRegionCode(@Param("regionCode") String regionCode);

	 /**
     * 查询所有机构不包含省本级下机构
     * @return
     */
	public List<SysOrg> queryOrgListAll();

	/**
     * 查询市下所有机构
     * @param searchMap
     * @return
     */
	List<SysOrg> queryOrgListArea(HashMap<String, Object> searchMap);

	/**
     * 查询省本级下机构
     * @param searchMap
     * @return
     */
	List<SysOrg> queryOrgListShenbenji(HashMap<String, Object> searchMap);

	/**
     * 根据orgIds查询机构树
     * @param orgIds
     * @return
     */
	public List<SysOrg> queryOrgListByOrgIds(@Param("orgIds") String orgIds);

	/**
	 * 根据机构类型查询机构
	 * @return
	 */
	List<SysOrgDTO> findByType(Integer type);

	public SysOrg queryOrgNameByOrgCode(@Param("orgCode") String orgCode);

	List<SysOrg> getByRegionCode(@Param("regionCode") String areaId);

	List<SysOrg> findCityByParentId(@Param("parentId") Long parentId);

	List<SysOrgDTO> getlistByOrgUuids(@Param("startTime") String startTime, @Param("endTime") String endTime);

    int getCountByMaxOrgId(@Param("modifyTime") String modifyTime);

	List<SysOrg> synchOrgListByMaxOrgId(@Param("modifyTime") String modifyTime);
}