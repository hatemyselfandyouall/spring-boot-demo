package com.wangxinenpu.springbootdemo.service.facade;


import com.wangxinenpu.springbootdemo.dataobject.vo.*;
import star.bizbase.exception.BizRuleException;

import java.util.HashMap;
import java.util.List;

/**
 * 系统机构服务接口
 * 
 * @author Administrator
 *
 */
public interface SysOrgFacade {
	/**
	 * 新增机构po信息
	 * 
	 * @param po
	 * @return
	 * @throws BizRuleException
	 */
	public Long addSysOrg(SysOrgDTO po) throws BizRuleException;

	/**
	 * 根据主键得到系统机构表记录
	 * 
	 * @param id
	 * @return
	 */
	public SysOrgDTO getByPrimaryKey(Long id);

	/**
	 * 更新po信息
	 * 
	 * @param po
	 * @return
	 */
	public int updatepo(SysOrgDTO po) throws BizRuleException;

	/**
	 * 根据参数查询 获取机构列表 带分页
	 * 
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysOrgDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size);

	/**
	 * 根据参数查询机构列表
	 * 
	 * @param searchMap
	 * @return
	 */
	public List<SysOrgDTO> getListByWhere(HashMap<String, Object> searchMap);


	/**
	 * 根据参数获取用户所在机构
	 *
	 * @return
	 */
	public List<SysOrgDTO> getListByWhereTwo(Long userId);


	/**
	 * 根据参数查询系统机构的记录数量
	 * 
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap);
	
	/**
	 * 校验用机构名和机构代码是否重复
	 * @return
	 */
    boolean checkOrgNameOrgenterCode(SysOrgDTO sysOrgDTO);
    
    /**
     * 根据机构id删除
     * @param id
     */
    public void deleteByPrimaryKey(Long id) throws BizRuleException;

	/**
	 * 根据天正一体化id删除机构
	 * @param uuid
	 */
	public void deleteByUUid(String uuid) throws BizRuleException;


    /**
     * 根据机构名称查询
     * @param orgName
     * @return
     */
    public SysOrgDTO findByName(String orgName);
    
    /**
     * 根据机构名称模糊查询
     * @param orgName
     * @return
     */
    public List<SysOrgDTO> findByNameLike(String orgName);
    
    /**
     * 根据区域ID查询机构列表
     * @param areaId
     * @return
     */
    public List<SysOrgDTO> queryOrgNodes(String areaId);
    
    
    /**
     * 通过行政区和险种获取机构信息
     * @param areaId
     * @param insId
     * @return
     */
    public SysOrgDTO findByAreaIdAndInsId(String areaId, String insId);

    /**
     * 获取所有市下机构信息
     * @param regionCode
     * @return
     */
    public List<SysOrgDTO> queryOrgByRegionCode(String regionCode);

    /**
     * 查询所有机构不包含省本级下机构
     * @return
     */
    public List<SysOrgDTO> queryOrgListAll();

    /**
     * 查询市下所有机构
     * @param searchMap
     * @return
     */
    public List<SysOrgDTO> queryOrgListArea(HashMap<String, Object> searchMap);

    /**
     * 查询省本级下机构
     * @param searchMap
     * @return
     */
    public List<SysOrgDTO> queryOrgListShenbenji(HashMap<String, Object> searchMap);


	/**
	 * 获取区域下所有机构
	 * @return
	 */
	public List<SysOrgDTO> queryOrgListAll(String areaId);

    /**
     * 根据orgIds查询机构树
     * @param orgIds
     * @return
     */
    public List<SysOrgDTO> queryOrgListByOrgIds(String orgIds);

    /**
     * 根据机构ID和险种ID查询是否绑定
     * @param orgId
     * @param insId
     * @return
     */
    public boolean existenceByOrgIdAndInsId(Long orgId, Long insId);

	/**
	 * 根据信用统一代码查询机构名称
	 * @param orgCode
	 * @return
	 */
    public SysOrgDTO queryOrgNameByOrgCode(String orgCode);



	/**
	 * 查询所有机构
	 *
	 *
	 * @return List<SysOrgDTO>
	 */
	public List<SysOrgDTO> getListByAll();


    List<SysOrgDTO> getListWithParent(Long orgId);

    List<SysOrgDTO> getOrgBySystemId(String channelCode);
    /*
    * 根据父级id查询子集
    * */

    List<SysOrgDTO> selectByParentId(Long orgId);

    List<SysOrgDTOwithSystemTreeVo> selectByTree(String areaId);

	/**
	 * 同步org稽核风控
	 * @param maxOrgId
	 * @param modifyTime
	 * @return
	 */
    List<SysOrgDTO> synchOrgList(String modifyTime);


    SysOrgDTO getOrgByRegionCode(String areaId);

    /**
     * 查询时间段内的天正机构内码
     * @param startTime
     * @param endTime
     * @return
     */
    public List<SysOrgDTO> getlistByOrgUuids(String startTime, String endTime);

    List<Long> getOrgIdsForParent(String orgId);
}
