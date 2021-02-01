package com.wangxinenpu.springbootdemo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.wangxinenpu.springbootdemo.service.facade.SysFunctionFacade;
import com.wangxinenpu.springbootdemo.service.facade.SysOrgFacade;
import com.wangxinenpu.springbootdemo.service.facade.SysRoleFacade;
import com.wangxinenpu.springbootdemo.service.facade.SysUserFacade;
import com.wangxinenpu.springbootdemo.dataobject.po.SysMenuChangeLog;
import com.wangxinenpu.springbootdemo.dataobject.vo.*;
import com.wangxinenpu.springbootdemo.dao.mapper.*;
import com.wangxinenpu.springbootdemo.service.service.SysFunctionService;
import com.wangxinenpu.springbootdemo.service.service.SysRoleFunctionService;
import com.wangxinenpu.springbootdemo.dataobject.po.*;
import com.wangxinenpu.springbootdemo.util.ListUtil;
import com.wangxinenpu.springbootdemo.util.TreeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import star.bizbase.exception.BizRuleException;
import star.bizbase.util.RuleCheck;
import star.util.StringUtil;
import star.vo.BaseVo;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 系统菜单facade服务实现类
 * 
 * @author Administrator
 *
 */
@Service
public class SysFunctionImpl implements SysFunctionFacade{
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SysFunctionService sysFunctionService;
	@Autowired
	private SysRoleFunctionService sysRoleFunctionService;
	@Autowired
	private SysOrgFacade sysOrgFacade;
	@Autowired
	private SysFunctionMapper sysFunctionMapper;
	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysRoleFunctionMapper sysRoleFunctionMapper;
	@Autowired
	private SysMenuChangeLogMapper sysMenuChangeLogMapper;
	@Autowired
	private SysFunctionScopeMapper sysFunctionScopeMapper;
	@Autowired
	private SysUserFacade sysUserFacade;
	@Autowired
	private SysRoleFacade sysRoleFacade;
//	@Autowired
//	private ComOperateLogFacade comOperateLogFacade;


	@Override
	public List<SysFunctionDTO> queryFunctionListByRoleId(SysUserDTO sysUser) {
		return sysFunctionService.getFunctionList(sysUser);
	}

	@Override
	public SysFunctionDTO getByPrimaryKey(Long id) {
		return sysFunctionService.getByPrimaryKey(id);
	}

	@Override
	public List<SysFunctionDTO> findTreesByRoleType(String roleType) {
		return sysFunctionService.findTreesByRoleType(roleType);
	}

	@Override
	public List<SysFunctionDTO> queryAllFunctionList() {
		return sysFunctionService.queryAllFunctionList();
	}
	
	@Override
	public List<SysFunctionYthDTO> queryFunctionListByIsBus(String isBus, String userId, String funType) {
		logger.info("queryFunctionListByIsBus 一体化菜单查询 isBus={},userId={},funType={}}"
				,isBus,userId,funType);
		List<SysFunctionYthDTO> ythFunctionList = new ArrayList<SysFunctionYthDTO>();
		SysUserDTO sysUser = new SysUserDTO();
		if(StringUtils.isNotEmpty(userId)){
			sysUser.setId(Long.parseLong(userId));
		}
		List<SysFunctionDTO> functionList = sysFunctionService.queryFunctionListByRoleIdsAndIsBus(sysUser,isBus,funType);
		if(CollectionUtils.isEmpty(functionList)) return Collections.emptyList();
		List<String> parentIds = new ArrayList<>();
		for(SysFunctionDTO function : functionList){
			 if(function.getParentId()==null) continue;
			 parentIds.add(function.getParentId().toString());
		}
		//去除重复父节点ID
		List<String> listIds = new ArrayList<>(new HashSet<String>(parentIds));
		for(int i =0;i<listIds.size();i++){
			if(!"0".equals(listIds.get(i))){
				SysFunctionDTO f= sysFunctionService.getByPrimaryKey(Long.parseLong(listIds.get(i)));
				SysFunctionDTO ff= sysFunctionService.getByPrimaryKey(f.getParentId());
				if(null != f && null != ff ){
					f.setFunOrder(Integer.valueOf(ff.getFunOrder().toString()+f.getFunOrder().toString()));
				}
				functionList.add(f);

			}
		}
			//排序
			functionList.sort((SysFunctionDTO fun1, SysFunctionDTO fun2)->fun1.getFunOrder().compareTo(fun2.getFunOrder()));
		for (SysFunctionDTO sysFunctionDTO : functionList) {
			String url;
			if("/".equals(sysFunctionDTO.getLocation())) {
				url = "";
			}else {
				url = sysFunctionDTO.getLocation()+"?wk=0";
			}
			SysFunctionYthDTO sysFunctionYthDTO = new SysFunctionYthDTO();
			sysFunctionYthDTO.setId(sysFunctionDTO.getId());
			sysFunctionYthDTO.setText(sysFunctionDTO.getTitle());
			sysFunctionYthDTO.setPid(sysFunctionDTO.getParentId());
			sysFunctionYthDTO.setUrl(url);
			sysFunctionYthDTO.setBak("");
			sysFunctionYthDTO.setSysmanagerView(sysFunctionDTO.getSysmanagerView());
			ythFunctionList.add(sysFunctionYthDTO);
			//排序
		}

		return ythFunctionList;
	}

	@Override
	public SysFunctionDTO getSysFunctionBean(Map<String, Object> map) {
		SysFunctionDTO function =new SysFunctionDTO();
        function.setTitle(map.get("title").toString());
        function.setLocation(map.get("location").toString());
        function.setFunType(map.get("funType").toString());
        function.setNodeType(map.get("nodeType").toString());
        function.setActive(map.get("active").toString());
        function.setDescription(map.get("description").toString());
        function.setDevEloper(map.get("devEloper").toString());
        if (!map.get("funOrder").toString().equals("")){
            function.setFunOrder(Integer.parseInt(map.get("funOrder").toString()));
        }else {
            function.setFunOrder(0);
        }
        function.setFunCode(map.get("funCode").toString());
        function.setIcon(map.get("icon").toString());
        function.setIsLog(map.get("isLog").toString());
        function.setAuFlag(map.get("auFlag").toString());
        function.setRbFlag(map.get("rbFlag").toString());
        function.setParentId((long) Integer.parseInt(map.get("parentId").toString()));
        if (!map.get("id").toString().equals("")){
            function.setId((long) Integer.parseInt(map.get("id").toString()));
        }
        return function;
	}

	@Override
	public boolean checkLocation(Long id, String location, String funType) {
		List<SysFunctionDTO> functionList  = sysFunctionService.checkLocation(id, location);
		if(functionList.size()>0){
			String type = functionList.get(0).getFunType();
			if(type.equals(funType)){//同一系统路径不能重复
				return true;
			}else{
				return false;
			}
		}
		return false;
	}

	@Override
	@Transactional
	public int addSysFunction(SysFunctionDTO sysFunction) throws BizRuleException {
		/* 非空校验 */
		RuleCheck.validateByAnnotation(sysFunction);
		if(sysFunction.getAuditHierarchy() == null){
			logger.info("测试"+sysFunction.getAuditHierarchy());
			sysFunction.setAuditHierarchy(new Long((long)0));
		}
		return sysFunctionService.addSysFunction(sysFunction);
	}


	@Override
	@Transactional
	public Long addSysFunctionReturnId(SysFunctionDTO sysFunction) throws BizRuleException {
		/* 非空校验 */
		RuleCheck.validateByAnnotation(sysFunction);
		if(sysFunction.getAuditHierarchy() == null){
			logger.info("测试"+sysFunction.getAuditHierarchy());
			sysFunction.setAuditHierarchy(new Long((long)0));
		}
		return sysFunctionService.addSysFunctionReturnId(sysFunction);
	}


	@Override
	@Transactional
	public int updateSysFunction(SysFunctionDTO sysFunction) throws BizRuleException {
		return sysFunctionService.updateSysFunction(sysFunction);
	}

	@Override
	@Transactional
	public void deleteMenu(SysFunctionDTO sysFunction) throws BizRuleException{
		sysFunctionService.deleteByPrimaryKey(sysFunction.getId());
		List<SysRoleFunctionDTO> rfList = sysRoleFunctionService.findByFunctionId(sysFunction.getId());
		if(null != rfList && rfList.size()>0){//有关联关系
			for(SysRoleFunctionDTO rf :rfList){
				sysRoleFunctionService.deleteByFunctionId(rf.getFunctionId());
			}
		}
	}

	@Override
	public List<SysRoleFunctionDTO> findByRoleId(String roleId) {
		return sysRoleFunctionService.findByRoleId(roleId);
	}

	@Override
	public List<SysFunctionDTO> queryNodeTypeListByFunType(String funType) {
		return sysFunctionService.queryNodeTypeListByFunType(funType);
	}

	@Override
	public List<SysBusinessTypeDTO> queryBusinessList(String busPar) {
		return sysFunctionService.queryBusinessList(busPar);
	}

	@Override
	public SysBusinessTypeDTO findBusinessById(Long id) {
		return sysFunctionService.findBusinessById(id);
	}

	@Override
	public List<SysFunctionDTO> findByFunTypeList(String funType) {
		return sysFunctionService.findByFunTypeList(funType);
	}

	@Override
	public List<SysFunctionDTO> findAccessList(String channelCode) {
		return sysFunctionService.findAccessList(channelCode);
	}

	@Override
	public List<SysFunctionDTO> getListByParentId(Long id) {
		return sysFunctionService.getListByParentId(id);
	}

	@Override
	@Transactional
	public int updateFunOrder(Map<String, Object> map) throws BizRuleException {
		return sysFunctionService.updateFunOrder(map);
	}

	@Override
	public List<SysFunctionDTO> findByLocation(String location) {
		return sysFunctionService.findByLocation(location);
	}

	@Override
	public SysBusinessTypeDTO findBusinessByFunId(Long funId) {
		return sysFunctionService.findBusinessByFunId(funId);
	}

	@Override
	public List<SysFunctionDTO> getListByWhere(HashMap<String, Object> searchMap) {
		return sysFunctionService.getListByWhere(searchMap);
	}

	@Override
	public List<SysFunctionDTO> queryRbFlagListById(HashMap<String, Object> searchMap) {
		return sysFunctionService.queryRbFlagListById(searchMap);
	}

	@Override
	public List<SysFunctionDTO> findByFunTypeAndUserList(String funType, SysUserDTO sysUser) {
		return sysFunctionService.findByFunTypeAndUserList(funType, sysUser);
	}

	@Override
	public int updateFunNode(Map<String, Object> map) throws BizRuleException {
		return sysFunctionService.updateFunNode(map);
	}

	@Override
	public int findMaxFunOrder(Long parentId) {
		return sysFunctionService.findMaxFunOrder(parentId);
	}

	@Override
	public SysFunctionDTO findFunctionByBussiness(String number) {
		return sysFunctionService.findFunctionByBussiness(number);
	}

	@Override
	public List<SysRoleFunctionDTO> getRoleFunListByWhere(HashMap<String, Object> searchMap) {
		return sysRoleFunctionService.getListByWhere(searchMap);
	}

	@Override
	public List<SysRoleFunctionDTO> findByFunctionId(Long functionId) {
		List<SysRoleFunctionDTO> rfList = sysRoleFunctionService.findByFunctionId(functionId);
		return rfList;
	}

	@Override
	public int updateRoleFunction(SysRoleFunctionDTO roleFunction) throws BizRuleException {
		return sysRoleFunctionService.updatepo(roleFunction);
	}

	@Override
	public List<SysFunctionDTO> selectByOrgIdAndSystemId(Long systemId, Long orgId) {
		return sysFunctionService.selectByOrgIdAndSystemId(systemId,orgId);
	}

	@Override
	public List<SysFunctionDTO> selectByUserIdAndSystemId(String channelCode, Long userId) {
		return sysFunctionService.selectByUserIdAndSystemId(channelCode,userId);
	}

	public SysFunctionDTO queryRbFlagById(Long functionId) {
		SysFunctionDTO sysFunctionDTO = new SysFunctionDTO();
		if(functionId != null){
			sysFunctionDTO = sysFunctionService.getByPrimaryKey(functionId);
		}
		return sysFunctionDTO;
	}

	public List<SysFunctionDTO> queryRbFlagResultById(String functionId) {
		List<SysFunctionDTO> list = new ArrayList<>();
		// SysFunctionDTO sysFunctionDTO = new SysFunctionDTO();
		//String functionIdStr="";
		if(StringUtil.isNotEmpty(functionId)){
//			String [] strOpseno = functionId.split(",");
//			for(int i=0;i<strOpseno.length;i++){
//				if(comOperateLogFacade.getByPrimaryKey(Long.parseLong(strOpseno[i])) != null){
//					functionIdStr+= comOperateLogFacade.getByPrimaryKey(Long.parseLong(strOpseno[i])).getFunctionId()+",";
//				}
//			}
			HashMap<String, Object> searchMap = new HashMap();
			//searchMap.put("ids",functionIdStr.substring(0,functionIdStr.length()-1));
			searchMap.put("ids",functionId);
			list = sysFunctionService.queryRbFlagListById(searchMap);
//			if(list != null && list.size()>0){
//				for(int i=0;i<strOpseno.length;i++) {
//					for (SysFunctionDTO sysFunction : list) {
//						if (comOperateLogFacade.getByPrimaryKey(Long.parseLong(strOpseno[i])) != null) {
//							if (comOperateLogFacade.getByPrimaryKey(Long.parseLong(strOpseno[i])).getFunctionId().equals(sysFunction.getId())) {
//								sysFunction.setOpseno(Long.parseLong(strOpseno[i]));
//
//							}
//						}
//					}
//				}
//			}
		}
		return list;
	}

	@Override
	public List<SysFunctionDTO> queryFunListByRoleId(@Param("roleIds") List<String> roleIds) {
		return sysFunctionService.getFunctionList(roleIds);
	}

	@Override
	public List<SysFunctionDTO> findByFunctitle(String title) {
		return sysFunctionService.findByFunctitle(title);
	}

	@Override
	public List<SysFunctionDTO> selectByGroupName() {
		return sysFunctionService.selectByGroupName();
	}

	@Override
	public int deleteByFunType(String funType) {
		return sysFunctionService.deleteByFunType(funType);
	}
	
	@Override
	public List<SysFunctionDTO> findFunctionInUuid(List<String> uuids) {
		return sysFunctionService.findFunctionInUuid(uuids);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer sysFunctionSetPermissionForOrgs(SysFunctionSetPermissionForOrgsVO sysFunctionSetPermissionForOrgsVO) {
		if (sysFunctionSetPermissionForOrgsVO==null||sysFunctionSetPermissionForOrgsVO.getFunctionId()==null) {
			return null;
		}
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("orgState", "1");
		List<SysOrgDTO> orgList = new ArrayList<>();
		orgList = sysOrgFacade.getListByWhere(searchMap);
		logger.info(orgList+"");
		orgList=orgList.parallelStream().filter(i->i!=null&&i.getType()!=null&&1==i.getType()).collect(Collectors.toList());
		List<SysRole> getPermissionRoleList=null;
		if (!CollectionUtils.isEmpty(sysFunctionSetPermissionForOrgsVO.getOrgIds())){
			getPermissionRoleList=sysRoleMapper.findByRoletypeAndOrgIds(3,sysFunctionSetPermissionForOrgsVO.getOrgIds());
		}
		List<SysRole> losePermissionRoleList=sysRoleMapper.findByRoletypeAndOrgIds(null,orgList.parallelStream().map(SysOrgDTO::getId)
				.filter(i->sysFunctionSetPermissionForOrgsVO.getOrgIds()==null||!sysFunctionSetPermissionForOrgsVO.getOrgIds().contains(i)).collect(Collectors.toList()));
		if (!CollectionUtils.isEmpty(getPermissionRoleList)) {
			sysRoleFunctionMapper.deleteByRoleIdsAndFunctionId(sysFunctionSetPermissionForOrgsVO.getFunctionId(), getPermissionRoleList);
		}
		if (!CollectionUtils.isEmpty(losePermissionRoleList)) {
			deleteLostPermissionListsandTheirFatherPermissions(sysFunctionSetPermissionForOrgsVO.getFunctionId(),losePermissionRoleList);
		}
		List<SysRoleFunction> sysRoleFunctions =null;
		if (!CollectionUtils.isEmpty(getPermissionRoleList)) {
			sysRoleFunctions=getPermissionRoleList.parallelStream().map(i -> {
				SysRoleFunction sysRoleFunction = new SysRoleFunction();
				sysRoleFunction.setRelationId(UUID.randomUUID() + "");
				sysRoleFunction.setRoleId(i.getId());
				sysRoleFunction.setCreateTime(new Date());
				sysRoleFunction.setModifyTime(new Date());
				sysRoleFunction.setSelectState("0");
				sysRoleFunction.setFunctionId(sysFunctionSetPermissionForOrgsVO.getFunctionId());
				return sysRoleFunction;
			}).collect(Collectors.toList());
		}
		if (!CollectionUtils.isEmpty(sysRoleFunctions)) {
			sysRoleFunctionMapper.insertSysRoleFunctionList(sysRoleFunctions);
		}
		return 1;
	}

	/**
	 * 删除子的同时，也要删除父
	 * @param functionId
	 * @param losePermissionRoleList
	 */
	private void deleteLostPermissionListsandTheirFatherPermissions(Long functionId, List<SysRole> losePermissionRoleList) {
		SysFunction sysFunction=sysFunctionMapper.getByPrimaryKey(functionId);
		if (sysFunction==null){
			return;
		}
		SysFunction parentSysfunction=sysFunctionMapper.getByPrimaryKey(sysFunction.getParentId());
		if (parentSysfunction==null){
			return;
		}
		sysRoleFunctionMapper.changeSelectStatesByRoleListAndFunction(parentSysfunction.getId(),losePermissionRoleList);
		sysRoleFunctionMapper.deleteByRoleIdsAndFunctionId(functionId,losePermissionRoleList );
	}

	@Override
	public PermissTreeByFunctionIdVO getPermissTreeByFunctionId(Long functionID) {
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("orgState", "1");
		List<SysOrgDTO> orgList = new ArrayList<>();
		orgList = sysOrgFacade.getListByWhere(searchMap);
		List<Long> hasPermissionOrgList=new ArrayList<>();
		List<SysOrgWithPermissionVO> sysOrgWithPermissionVOS=orgList.parallelStream().filter(i->i!=null&&i.getType()!=null).map(i->{
			SysOrgWithPermissionVO sysOrgWithPermissionVO=new SysOrgWithPermissionVO();
			if (1==i.getType()){
				Integer checkflag=checkPermission(functionID,i);
				sysOrgWithPermissionVO.setHasPermission(checkflag);
				if (1==checkflag){
					hasPermissionOrgList.add(i.getId());
				}
			}
			BeanUtils.copyProperties(i,sysOrgWithPermissionVO);
			return sysOrgWithPermissionVO;
		}).collect(Collectors.toList());
		JSONArray jsonArray = null;
		if(orgList.size()>0){
			jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(sysOrgWithPermissionVOS)), "id",
					"parentId", "children");
		}
		return new PermissTreeByFunctionIdVO().setOrgTree(jsonArray).setHasPermissionList(hasPermissionOrgList);
	}

	@Override
	public List<SysFunctionDTO> queryFunListByRoleIdForInnerMenu(List<String> roleIds) {
		return sysFunctionService.queryFunListByRoleIdForInnerMenu(roleIds);
	}



	@Override
	public List<SysFunctionDTO> tranList() {
		List<SysFunction> sysFunctions=sysFunctionMapper.tranList();
		List<SysFunctionDTO> sysFunctionDTOS= BaseVo.copyListTo(sysFunctions, SysFunctionDTO.class);
		for (SysFunctionDTO sysFunctisonDTO:sysFunctionDTOS){
			String systemName=getSystemName(sysFunctisonDTO);
			sysFunctisonDTO.setGroupName(systemName);
		}
		return sysFunctionDTOS;
	}

	private String getSystemName(SysFunctionDTO sysFunctionDTO) {
		if (sysFunctionDTO==null||sysFunctionDTO.getParentId()==null){
			return "";
		}
		if (sysFunctionDTO.getParentId()==0){
			return sysFunctionDTO.getTitle();
		}
		return getSystemName(getByPrimaryKey(sysFunctionDTO.getParentId()));
	}

	@Override
	public PermissTreeByFunctionIdVO sysFunctionTsysFunctionScopeTreeForOrgseeForOrgs(Long userId, Long functionId, String belongRoleId) {
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("orgState", "1");
		List<SysOrgDTO> orgList = new ArrayList<>();
		orgList = sysOrgFacade.getListByWhere(searchMap);
		List<Long> hasPermissionOrgList=new ArrayList<>();
		SysUserDTO sysUser=sysUserFacade.getByPrimaryKey(userId);
		List<SysOrgWithPermissionVO> sysOrgWithPermissionVOS=new ArrayList<>();
		orgList = orgList.parallelStream().filter(i -> i != null && i.getType() != null && i.getType() != 1 && i.getId() != 1).collect(Collectors.toList());
		if (StringUtils.isNotEmpty(belongRoleId)) {
			SysRoleDTO sysRole = sysRoleFacade.getByPrimaryKey(belongRoleId);
			SysOrgDTO sysOrg = sysOrgFacade.getByPrimaryKey(sysRole.getOrgId());
			sysOrgWithPermissionVOS = orgList.stream().map(i -> {
				SysOrgWithPermissionVO sysOrgWithPermissionVO = new SysOrgWithPermissionVO();
				Integer count = sysFunctionScopeMapper.selectCount(new SysFunctionScope().setFuncId(functionId).setOrgId(i.getId()).setBelongRoleId(belongRoleId).setIsDelete(0));
				if (count > 0) {
					hasPermissionOrgList.add(i.getId());
				}
				BeanUtils.copyProperties(i, sysOrgWithPermissionVO);
				if (!StringUtils.isEmpty(i.getRegionCode()) && i.getRegionCode().equals(sysOrg.getRegionCode())) {
					sysOrgWithPermissionVO.setDisabled(true);
					hasPermissionOrgList.add(sysOrgWithPermissionVO.getId());
				}
				return sysOrgWithPermissionVO;
			}).collect(Collectors.toList());
		}else {
			sysOrgWithPermissionVOS=orgList.stream().map(i -> {
				SysOrgWithPermissionVO sysOrgWithPermissionVO = new SysOrgWithPermissionVO();
				BeanUtils.copyProperties(i, sysOrgWithPermissionVO);
				return sysOrgWithPermissionVO;
			}).collect(Collectors.toList());
		}
		JSONArray jsonArray = null;
		if(!CollectionUtils.isEmpty(sysOrgWithPermissionVOS)){
			jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(sysOrgWithPermissionVOS)), "id",
					"parentId", "children");
		}
		//根据类型定义能给子人员赋予的权限
		if (sysUser!=null&&sysUser.getUserType()!=null){
			if("1".equals(sysUser.getUserType())){
				//系统管理员的话什么都不做
				return new PermissTreeByFunctionIdVO().setOrgTree(jsonArray).setHasPermissionList(hasPermissionOrgList).setSysOrgWithPermissionVOS(sysOrgWithPermissionVOS);
			}
			if ("2".equals(sysUser.getUserType())||"3".equals(sysUser.getUserType())){
				//区域操作员和机构管理员的话，就过滤到只返回自己有权限的机构
				List<SysRoleDTO> sysRoleDTOS= sysRoleFacade.queryRoleByUserId(sysUser.getId()+"");
				if (sysRoleDTOS==null){
					return new PermissTreeByFunctionIdVO();
				}
				Example example=new Example(SysFunctionScope.class);
				example.createCriteria().andIn("belongRoleId",sysRoleDTOS.stream().map(SysRoleDTO::getId).collect(Collectors.toList()));
				List<SysFunctionScope> sysFunctionScopes=sysFunctionScopeMapper.selectByExample(example);
				List<Long> parentHasPermissionList=sysFunctionScopes.stream().map(SysFunctionScope::getOrgId).collect(Collectors.toList());
				sysOrgWithPermissionVOS=sysOrgWithPermissionVOS.stream().filter(i->parentHasPermissionList.contains(i.getId())).collect(Collectors.toList());
				for (SysOrgDTO sysOrgDTO:orgList){
					List<Long> parentIds=sysOrgWithPermissionVOS.stream().map(SysOrgWithPermissionVO::getParentId).distinct().collect(Collectors.toList());
					List<Long> allIds=sysOrgWithPermissionVOS.stream().map(SysOrgWithPermissionVO::getId).distinct().collect(Collectors.toList());
					List<Long> parentIdsNotInList=parentIds.stream().filter(i->!allIds.contains(i)).collect(Collectors.toList());
					if (parentIdsNotInList.contains(sysOrgDTO.getId())){
						SysOrgWithPermissionVO sysOrgWithPermissionVO=new SysOrgWithPermissionVO();
						BeanUtils.copyProperties(sysOrgDTO,sysOrgWithPermissionVO);
						sysOrgWithPermissionVOS.add(sysOrgWithPermissionVO);
					}
				}
				if(sysOrgWithPermissionVOS.size()>0){
					jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(sysOrgWithPermissionVOS)), "id",
							"parentId", "children");
				}
				return new PermissTreeByFunctionIdVO().setOrgTree(jsonArray).setHasPermissionList(hasPermissionOrgList).setSysOrgWithPermissionVOS(sysOrgWithPermissionVOS);
			}
		}
		//其他的用户类型，就返回个空菜单，以表示不允许操作吧
		return new PermissTreeByFunctionIdVO();
	}

	@Override
	public PermissTreeByFunctionIdVO sysFunctionTsysFunctionScopeTreeForOrgseeForOrgs(Long userId, String functionIds, String belongRoleId) {
		if (StringUtils.isEmpty(functionIds)){
			return sysFunctionTsysFunctionScopeTreeForOrgseeForOrgs( userId,0l,  belongRoleId);
		}
		List<Long> functionIdList= Arrays.asList(functionIds.split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
		List<PermissTreeByFunctionIdVO> permissTreeByFunctionIdVOS=functionIdList.stream().map(i->sysFunctionTsysFunctionScopeTreeForOrgseeForOrgs(userId,i,belongRoleId)).collect(Collectors.toList());
		List<SysOrgWithPermissionVO> sysOrgWithPermissionVOS= ListUtil.intersectionList(permissTreeByFunctionIdVOS.stream().map(PermissTreeByFunctionIdVO::getSysOrgWithPermissionVOS).collect(Collectors.toList()));
		List<Long> hasPermissionList= ListUtil.intersectionList(permissTreeByFunctionIdVOS.stream().map(PermissTreeByFunctionIdVO::getHasPermissionList).collect(Collectors.toList()));
		JSONArray jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(sysOrgWithPermissionVOS)), "id",
				"parentId", "children");
		return new PermissTreeByFunctionIdVO().setHasPermissionList(hasPermissionList).setSysOrgWithPermissionVOS(sysOrgWithPermissionVOS).setOrgTree(jsonArray);
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer sysFunctionTreeForOrgs(SaveSysFunctionScopeForOrgsSaveVO saveSysFunctionScopeForOrgsSaveVO) {
		if(saveSysFunctionScopeForOrgsSaveVO==null||CollectionUtils.isEmpty(saveSysFunctionScopeForOrgsSaveVO.getFuncIds())
				||CollectionUtils.isEmpty(saveSysFunctionScopeForOrgsSaveVO.getOrgIds())
		||saveSysFunctionScopeForOrgsSaveVO.getBelongRoleId()==null){
			return 0;
		}
		List<Long> funcIds= saveSysFunctionScopeForOrgsSaveVO.getFuncIds();
        for(Long funcId:funcIds) {
            Example example = new Example(SysFunctionScope.class);
            example.createCriteria().andEqualTo("funcId", funcId)
                    .andEqualTo("belongRoleId", saveSysFunctionScopeForOrgsSaveVO.getBelongRoleId());
            sysFunctionScopeMapper.updateByExampleSelective(new SysFunctionScope().setIsDelete(1), example);
            saveSysFunctionScopeForOrgsSaveVO.getOrgIds().forEach(i -> {
                sysFunctionScopeMapper.insertSelective(new SysFunctionScope().setBelongRoleId(saveSysFunctionScopeForOrgsSaveVO.getBelongRoleId()).setFuncId(funcId).setOrgId(i));
            });
        }
		return 1;
	}
	@Override
	public List<SysOrgDTO> getFunctionScopeOrgsByFunctionIdandUserId(Long userId, Long functionId) throws Exception {
		if (userId==null||functionId==null){
			throw new Exception("不传入userId和functionI的情况下，不能获取事项权限信息"+userId+"|"+functionId);
		}
		SysUserDTO sysUser=sysUserFacade.getByPrimaryKey(userId);
		List<SysRoleDTO> sysRoleDTOS=sysRoleFacade.queryRoleByUserId(userId+"");
		if (CollectionUtils.isEmpty(sysRoleDTOS)){
			throw new Exception("该用户没有所属的角色，无法根据角色获取事项权限"+userId+"|"+sysUser);
		}
		Example example=new Example(SysFunctionScope.class);
		example.createCriteria().andIn("belongRoleId",sysRoleDTOS.stream().map(SysRoleDTO::getId).collect(Collectors.toList()))
				.andEqualTo("isDelete",0)
				.andEqualTo("funcId",functionId);
		List<SysFunctionScope> sysFunctionScopes=sysFunctionScopeMapper.selectByExample(example);
		List<SysOrgDTO> sysOrgDTOS=new ArrayList<>();
		if (!CollectionUtils.isEmpty(sysFunctionScopes)){
			sysOrgDTOS=sysFunctionScopes.stream().map(i->{ return sysOrgFacade.getByPrimaryKey(i.getOrgId());
			}).distinct().collect(Collectors.toList());
		}
//		//如果没有用户所在机构，那就补上
		if (!sysOrgDTOS.stream().map(SysOrgDTO::getOrgenterCode).collect(Collectors.toList()).contains(sysUser.getAreaId())){
			sysOrgDTOS.add(sysOrgFacade.getOrgByRegionCode(sysUser.getAreaId()+""));
		}
		//去重能生效吗
		sysOrgDTOS=sysOrgDTOS.stream().filter(i->i!=null&&i.getOrgenterCode()!=null).filter(distinctByKey(SysOrgDTO::getOrgenterCode))
				.collect(Collectors.toList());
//		logger.info("该用户有权限的区划为"+sysOrgDTOS);
		return sysOrgDTOS;
	}

	private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	private Integer checkPermission(Long functionID, SysOrgDTO sysOrgDTO) {
		if (sysOrgDTO!=null){
			List<SysRole> sysRoles=sysRoleMapper.findByRoletypeAndOrgIds(3, Arrays.asList(sysOrgDTO.getId()));
			if(CollectionUtils.isEmpty(sysRoles)||functionID==null){
			    return 2;
            }
			for (SysRole sysRole:sysRoles){
				List<SysRoleFunction> sysFunctionList=sysRoleFunctionMapper.findByRoleId(sysRole.getId());
				if (sysFunctionList.parallelStream().map(SysRoleFunction::getFunctionId).filter(i->i.equals(functionID)).count()>0l){
					return 1;
				}
			}
			return 0;
		}
		return 0;
	}
	
	@Override
	public List<SysFunctionDTO> getFunLevelFunctionList() {
		return sysFunctionService.getFunLevelFunctionList();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public synchronized Integer batchSaveSysFunctionScopeForOrgs(BatchSaveSysFunctionScopeForOrgsSaveVO saveSysFunctionScopeForOrgsSaveVO) {
		if(saveSysFunctionScopeForOrgsSaveVO==null||CollectionUtils.isEmpty(saveSysFunctionScopeForOrgsSaveVO.getFuncIds())
				||CollectionUtils.isEmpty(saveSysFunctionScopeForOrgsSaveVO.getOrgIds())
				||CollectionUtils.isEmpty(saveSysFunctionScopeForOrgsSaveVO.getBelongRoleIds())){
			logger.info(saveSysFunctionScopeForOrgsSaveVO+"");
			return 0;
		}
		for (Long functionId:saveSysFunctionScopeForOrgsSaveVO.getFuncIds()){
            batchSaveSysFunctionScopeForOrgs(functionId,saveSysFunctionScopeForOrgsSaveVO.getOrgIds(),saveSysFunctionScopeForOrgsSaveVO.getBelongRoleIds());
		}
		return 1;
	}

    public synchronized Integer batchSaveSysFunctionScopeForOrgs(Long functionId, List<Long> orgIds, List<String> belongRoleIds) {
        for (String belongRoleId:belongRoleIds){
//			Example example=new Example(SysFunctionScope.class);
//			example.createCriteria().andEqualTo("funcId",funcId)
//					.andEqualTo("belongRoleId",belongRoleId);
//			sysFunctionScopeMapper.updateByExampleSelective(new SysFunctionScope().setIsDelete(1),example);
            orgIds.forEach(i->{
                sysFunctionScopeMapper.insertSelective(new SysFunctionScope().setBelongRoleId(belongRoleId).setFuncId(functionId).setOrgId(i));
            });
        }
        return 1;
    }

}
