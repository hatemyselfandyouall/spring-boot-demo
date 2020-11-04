package com.wangxinenpu.springbootdemo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.wangxinenpu.springbootdemo.dao.mapper.cachemapper.SysUserCacheMapper;
import com.wangxinenpu.springbootdemo.service.facade.SysOrgFacade;
import com.wangxinenpu.springbootdemo.dataobject.vo.*;
import com.wangxinenpu.springbootdemo.service.service.*;
import com.wangxinenpu.springbootdemo.util.TreeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import star.bizbase.exception.BizRuleException;
import star.bizbase.util.RuleCheck;
import star.vo.BaseVo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统机构facade服务实现类
 * 
 * @author Administrator
 *
 */
@Service
public class SysOrgImpl implements SysOrgFacade{
	
	@Autowired
	private SysOrgService sysOrgService;
	@Autowired
	private SysOrginstypeService sysOrginstypeService;
	@Autowired
	private SysOrgSystemService sysOrgSystemService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserOrgService sysUserOrgService;
	@Autowired
    private SysUserCacheMapper sysUserCacheMapper;

	@Override
	@Transactional
	public Long addSysOrg(SysOrgDTO po) throws BizRuleException {
		/* 非空校验 */
		RuleCheck.validateByAnnotation(po);
		return sysOrgService.addSysOrg(po);
	}

	@Override
	public SysOrgDTO getByPrimaryKey(Long id) {
		return sysOrgService.getByPrimaryKey(id);
	}

	@Override
	@Transactional
	public int updatepo(SysOrgDTO po) throws BizRuleException{
		return sysOrgService.updatepo(po);
	}

	@Override
	public List<SysOrgDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size) {
		return sysOrgService.getListByWhere(searchMap, start, size);
	}

	@Override
	public List<SysOrgDTO> getListByWhere(HashMap<String, Object> searchMap) {
		return sysOrgService.getListByWhere(searchMap);
	}

	@Override
	public List<SysOrgDTO> getListByWhereTwo(Long userId) {
        List<SysOrgDTO> orgList = new ArrayList<>();
        SysUserDTO user = sysUserCacheMapper.getCacheSysUserById(userId);
        if(user.getUserType().equals("1")) {//管理员获取所有机构
            HashMap<String, Object> searchMap = new HashMap<>();
            searchMap.put("orgState", "1");
            searchMap.put("types","2");
            orgList = sysOrgService.getListByWhere(searchMap);
        }else {
            List<SysUserOrgDTO> userOrgList = sysUserOrgService.findByUserId(userId);
            if(userOrgList.size()>0) {
                Set<String> s = new HashSet<>();
                for (SysUserOrgDTO uo : userOrgList) {
                    SysOrgDTO org = sysOrgService.getByPrimaryKey(uo.getOrgId());
                    String idpath = org.getIdpath();
//                    String[] splitAddress = idpath.split("/"); //如果以竖线为分隔符，则split的时候需要加上两个斜杠【\\】进行转义
//                    for (int i = 0; i < splitAddress.length; i++) {
//                        s.add(splitAddress[i]);
//                    }
					orgList.add(org);
                }
//                String orgIds = "'" + StringUtils.join(s, "','") + "'";

            }
        }
		return  orgList;
	}

	@Override
	public int getCountByWhere(HashMap<String, Object> searchMap) {
		return sysOrgService.getCountByWhere(searchMap);
	}

	@Override
	public boolean checkOrgNameOrgenterCode(SysOrgDTO sysOrgDTO) {
		Boolean flag=false;
		List<SysOrgDTO> sysOrgList= sysOrgService.checkOrgNameOrgenterCode(sysOrgDTO.getOrgName(), sysOrgDTO.getOrgenterCode());
		if(sysOrgList.size()>0){
//			for(SysOrgDTO org : sysOrgList){
//				if(null != sysOrgDTO.getId() || sysOrgDTO.getId() == org.getId()){
//					flag=false;
//				}else{
//					flag=true;
//					break;
//				}
//			}
			flag=true;
		}
		return flag;
	}

	/**
	 * 根据主键ID删除
	 */
	@Override
	@Transactional
	public void deleteByPrimaryKey(Long id) throws BizRuleException{
		sysOrgService.deleteByPrimaryKey(id);
		//删除机构险种
		sysOrginstypeService.deleteByOrgId(id);
	}

	@Override
	public void deleteByUUid(String uuid) throws BizRuleException {
		sysOrgService.deleteByUUid(uuid);
	}

	/**
	 * 
	 * 根据区域ID查询机构列表
	 */
	@Override
	public List<SysOrgDTO> queryOrgNodes(String areaId) {
		List<SysOrgDTO> orgList = sysOrgService.findByRegioncode(areaId);
		List<SysOrgDTO> orgTree = new ArrayList<>();
		for (SysOrgDTO sysOrg : orgList) {
			orgTree.add(sysOrg);// 添加自身节点
//			orgTree.addAll(sysOrgService.findByIdpath(sysOrg.getIdpath()+"/"));
		}
		return orgTree;
	}

	/**
	 * 根据机构名称查询
	 */
	@Override
	public SysOrgDTO findByName(String orgName) {
		return sysOrgService.findByName(orgName);
	}

	/**
     * 通过行政区和险种获取机构信息
     * @param areaId
     * @param insId
     * @return
     */
	@Override
	public SysOrgDTO findByAreaIdAndInsId(String areaId, String insId) {
		return sysOrgService.findByAreaIdAndInsId(areaId, insId);
	}

	/**
	 * 获取市下所有机构信息
	 */
	@Override
	public List<SysOrgDTO> queryOrgByRegionCode(String regionCode) {
		List<SysOrgDTO> cityList = null;
		if("330000".equals(regionCode)){//获取浙江省下地市
			cityList = sysOrgService.findCityByRegionCode(regionCode);
		}else{//获取当前市
			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("regionCode", regionCode);
			searchMap.put("type", 0);
			cityList = sysOrgService.getListByWhere(searchMap);
		}
		
		if(cityList.size()>0){
			List<SysOrgDTO> list = new ArrayList<>();
			for(SysOrgDTO city : cityList){
				SysOrgDTO orgDto = new SysOrgDTO();
				HashMap<String, Object> searchMap = new HashMap<>();
				searchMap.put("parentId", city.getId());
				searchMap.put("type", 1);
				 List<SysOrgDTO> orgList = sysOrgService.getListByWhere(searchMap);
				 if(orgList.size()>0){
					 orgDto.setRegionCode(city.getRegionCode());
					 for(SysOrgDTO org : orgList){
						 orgDto.setOrgName(org.getOrgName());
						 orgDto.setId(org.getId());
					 }
					 list.add(orgDto);
				 }
			}
			return list;
		}
		return null;
	}

	@Override
	public List<SysOrgDTO> queryOrgListByOrgIds(String orgIds) {
		return sysOrgService.queryOrgListByOrgIds(orgIds);
	}

	@Override
	public boolean existenceByOrgIdAndInsId(Long orgId, Long insId) {
		boolean falg = false;
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("orgId", orgId);
		searchMap.put("insId", insId);
		List<SysOrginstypeDTO> list = sysOrginstypeService.existenceByOrgIdAndInsId(searchMap);
		if(list.size()>0){
			falg =true;
		}
		return falg;
	}

	@Override
	public SysOrgDTO queryOrgNameByOrgCode(String orgCode) {
		return sysOrgService.queryOrgNameByOrgCode(orgCode);
	}

	@Override
	public List<SysOrgDTO> getListByAll() {
		HashMap map = new HashMap();
		map.put("orgState", "1");
		List listByWhere = sysOrgService.getListByWhere(map);
		return listByWhere;
	}

	@Override
	public List<SysOrgDTO> getListWithParent(Long orgId) {
		return   sysOrgService.getListWithParent(orgId);
	}

	@Override
	public List<SysOrgDTO> getOrgBySystemId(String channelCode) {
		return null;
	}

	@Override
	public List<SysOrgDTO> selectByParentId(Long orgId) {
		return null;
	}

	@Override
	public List<SysOrgDTOwithSystemTreeVo> selectByTree(String areaId) {
		String areas = areaId;
		if (StringUtils.isNotBlank(areaId) && areaId.endsWith("00")){
			areas = areaId.substring(0,4);
		}
		if (StringUtils.isNotBlank(areaId) && areaId.endsWith("0000")){
			areas = areaId.substring(0,2);
		}
		HashMap hashMap = new HashMap();
		hashMap.put("orgState", "1");
		hashMap.put("regionCodeLike", areas);
		List<SysOrgDTO> listByWhere = sysOrgService.getListByWhere(hashMap);
		List<SysOrgDTOwithSystemTreeVo> sysOrgDTOwithSystemTreeVos = BaseVo.copyListTo(listByWhere, SysOrgDTOwithSystemTreeVo.class);
		for (SysOrgDTOwithSystemTreeVo treeVo : sysOrgDTOwithSystemTreeVos){
			treeVo.setSystemList(sysOrgSystemService.findListVoByOrgId(treeVo.getId()));
		}
		JSONArray jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(sysOrgDTOwithSystemTreeVos)),"id","parentId","children");
		List<SysOrgDTOwithSystemTreeVo> systemAreaTrees = JSONArray.parseArray(JSONArray.toJSONString(jsonArray), SysOrgDTOwithSystemTreeVo.class);
		return systemAreaTrees;
	}

	@Override
	public SysOrgDTO getOrgByRegionCode(String areaId) {
		return sysOrgService.getOrgByRegionCode(areaId);
	}

	@Override
	public List<SysOrgDTO> findByNameLike(String orgName) {
		return sysOrgService.findByNameLike(orgName);
	}

	@Override
	public List<SysOrgDTO> queryOrgListAll() {
		return sysOrgService.queryOrgListAll();
	}

	@Override
	public List<SysOrgDTO> queryOrgListArea(HashMap<String, Object> searchMap) {
		return sysOrgService.queryOrgListArea(searchMap);
	}

	@Override
	public List<SysOrgDTO> queryOrgListAll(String areaId) {
		return sysOrgService.queryOrgListAllForArea(areaId);
	}

	@Override
	public List<SysOrgDTO> getlistByOrgUuids(String startTime, String endTime) {
		return sysOrgService.getlistByOrgUuids(startTime, endTime);
	}

	@Override
	public List<Long> getOrgIdsForParent(String orgId) {
		List<SysOrgDTO> sysOrgDTOS=getListByAll();
		List<Long> orgIds=getChildrenOrgIds(sysOrgDTOS, Long.valueOf(orgId));
		return orgIds;
	}

	private List<Long> getChildrenOrgIds(List<SysOrgDTO> sysOrgDTOS, Long orgId) {
//		List<Long> result= Arrays.asList(orgId);
//		List<Long> children=sysOrgDTOS.stream().filter(i->i.getParentId()!=null&&i.getParentId().equals(orgId))
//				.map(SysOrgDTO::getId).collect(Collectors.toList());
//		for(Long child:children){
//			children.addAll(getChildrenOrgIds(sysOrgDTOS,child));
//		}
//		result.addAll(children);
		List<Long> list=new ArrayList<>();
		list.add(orgId);
		return getChildrenIterator(sysOrgDTOS,list);
	}
	private List<Long> getChildrenIterator(List<SysOrgDTO> sysOrgDTOS, List<Long> parents){
		List<Long> children=sysOrgDTOS.stream().filter(i->i.getParentId()!=null&&parents.contains(i.getParentId()))
				.map(SysOrgDTO::getId).collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(children)){
			parents.addAll(getChildrenIterator(sysOrgDTOS,children));
		}
		return parents;
	}
	@Override
	public List<SysOrgDTO> synchOrgList(String modifyTime){
		return sysOrgService.synchOrgList(modifyTime);
	}

	@Override
	public List<SysOrgDTO> queryOrgListShenbenji(HashMap<String, Object> searchMap) {
		return sysOrgService.queryOrgListShenbenji(searchMap);
	}

}
