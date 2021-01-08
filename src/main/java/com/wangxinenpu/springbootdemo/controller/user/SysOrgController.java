package com.wangxinenpu.springbootdemo.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.wangxinenpu.springbootdemo.config.aop.OperLog;
import com.wangxinenpu.springbootdemo.config.component.LoginComponent;
import com.wangxinenpu.springbootdemo.dataobject.vo.*;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysOrgDepartment.SysOrgDepartmentSaveVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.orgSystem.SysOrgSystemDto;
import com.wangxinenpu.springbootdemo.service.facade.*;
import com.wangxinenpu.springbootdemo.util.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import star.bizbase.exception.BizRuleException;
import star.bizbase.vo.result.Results;
import star.util.StringUtil;
import star.vo.result.ResultVo;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 组织机构管理
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("sys/org")
@Api(tags = "组织机构管理")
@SuppressWarnings("all")
@Slf4j
public class SysOrgController  {

	@Autowired
	private SysOrgFacade sysOrgFacade;
	@Autowired
	private SysAreaFacade sysAreaFacade;
	@Autowired
	private SysUserFacade sysUserFacade;
	@Autowired
	private LoginComponent loginComonent;
	@Autowired
	private SysCodeFacade sysCodeFacade;
	@Autowired
	private SysOrginstypeFacade sysOrginstypeFacade;
	@Autowired
	private SysOrgSystemFacade sysOrgSystemFacade;
	@Autowired
	SysOrgDepartmentFacade sysOrgDepartmentFacade;
	@Autowired
	SysFunctionFacade sysFunctionFacade;
	private SysRoleFacade sysRoleFacade;
	@Autowired
	private SysCheckConfigFacade sysCheckConfigFacade;
	@Autowired
	private SysCheckProcessFacade sysCheckProcessFacade;
	
	/**
	 * 查询树
	 * @return
	 * @throws BizRuleException 
	 */
	@ResponseBody
	@RequestMapping(value = { "/queryTree" })
	public ResultVo<JSONArray> queryTree() throws BizRuleException {
		//获取当前登录用户的所属区域
		//从缓存获取用户信息
		Long userId = loginComonent.getLoginUserId();
		SysUserDTO user = sysUserFacade.getCacheByPrimaryKey(userId);
		ResultVo<JSONArray> result = Results.newResultVo();
		List<SysOrgDTO> orgList = new ArrayList<>();
		if(user.getUserType().equals("1") || user.getUserType().equals("2")){//管理员获取所有机构
			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("orgState", "1");
			orgList = sysOrgFacade.getListByWhere(searchMap);
		}else{
//			orgList = sysOrgFacade.queryOrgNodes(user.getAreaId().toString());
			List<SysUserOrgDTO> userOrgList = sysUserFacade.queryUserOrg(userId);
			if(userOrgList.size()>0){
				Set<String> s = new HashSet<>();
				for(SysUserOrgDTO uo :userOrgList){
					SysOrgDTO org = sysOrgFacade.getByPrimaryKey(uo.getOrgId());
					String idpath = org.getIdpath();
					String[] splitAddress=idpath.split("/"); //如果以竖线为分隔符，则split的时候需要加上两个斜杠【\\】进行转义
					 for(int i=0;i<splitAddress.length;i++){
				    	 s.add(splitAddress[i]);
				     }
				}
				String orgIds = "'" + StringUtils.join(s, "','") + "'";
				orgList = sysOrgFacade.queryOrgListByOrgIds(orgIds);
			}
		}
		JSONArray jsonArray = null;
		if(orgList.size()>0){
			jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(orgList)), "id",
					"parentId", "children");
		}
		
		result.setCode("0");
		result.setSuccess(true);
		result.setResult(jsonArray);
		return result;

	}

	/**
	 * 查询用户机构所在的区域列表
	 * @return
	 * @throws BizRuleException
	 */
	@ResponseBody
	@RequestMapping(value = { "/queryTreeArea" })
	public ResultVo<JSONArray> queryTreeArea() throws BizRuleException {
		//获取当前登录用户的所属区域
		//从缓存获取用户信息
		Long userId = loginComonent.getLoginUserId();
		SysUserDTO user = sysUserFacade.getCacheByPrimaryKey(userId);
		ResultVo<JSONArray> result = Results.newResultVo();
		List<SysOrgDTO> orgList = new ArrayList<>();
		if(user.getUserType().equals("1")){//管理员获取所有机构
			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("orgState", "1");
			orgList = sysOrgFacade.getListByWhere(searchMap);
		}else{
//			orgList = sysOrgFacade.queryOrgNodes(user.getAreaId().toString());
			List<SysUserOrgDTO> userOrgList = sysUserFacade.queryUserOrg(userId);
			if(userOrgList.size()>0){
				Set<String> s = new HashSet<>();
				for(SysUserOrgDTO uo :userOrgList){
					SysOrgDTO org = sysOrgFacade.getByPrimaryKey(uo.getOrgId());
					String idpath = org.getIdpath();
					String[] splitAddress=idpath.split("/"); //如果以竖线为分隔符，则split的时候需要加上两个斜杠【\\】进行转义
					for(int i=0;i<splitAddress.length;i++){
						s.add(splitAddress[i]);
					}
				}
				String orgIds = "'" + StringUtils.join(s, "','") + "'";
				orgList = sysOrgFacade.queryOrgListByOrgIds(orgIds);
			}
		}
		JSONArray jsonArray = null;
		for(int i=0;i<orgList.size();i++){
			SysOrgDTO sysOrgDTO = orgList.get(i);
			if(sysOrgDTO.getType()==1){
				orgList.remove(i);
				i--;
			}
		}
		if(orgList.size()>0){
			jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(orgList)), "id",
					"parentId", "children");
		}

		result.setCode("0");
		result.setSuccess(true);
		result.setResult(jsonArray);
		return result;

	}
	
	/**
	 * 保存组织机构
	 * @param sysOrg
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/saveOrg" },method = RequestMethod.POST)
	@ApiOperation(value = "保存组织机构")
	@OperLog(systemName="后管系统")
	public ResultVo<JSONArray> saveOrg(@RequestBody OrgVo orgVo) {
		ResultVo<JSONArray> result = Results.newResultVo();
		result.setSuccess(false);
		SysOrgDTO sysOrg = orgVo.getSysOrgDTO();
		List<String> insList = orgVo.getInsList();
		// 获取机构系统id列表
		List<String> systemList = orgVo.getSystemList();
		Boolean flag = sysOrgFacade.checkOrgNameOrgenterCode(sysOrg);
		try {

				if(null ==sysOrg.getId() || "".equals(sysOrg.getId())){
					if(flag == false){ //没有重复
						sysOrg.setOrgState("1");

						// 判断 是否为用户认证中心传来的
						if (sysOrg.getType()!=null){
							sysOrg.setType(sysOrg.getType()+1);
						}else {
							sysOrg.setType(1);
						}
						Long id = sysOrgFacade.addSysOrg(sysOrg);
						sysOrg.setId(id);
						SysOrgDTO org = sysOrgFacade.findByName(sysOrg.getOrgName());
						if(null != org.getParentId() && !"".equals(org.getParentId())){
							//填充idpath
							SysOrgDTO org1 = sysOrgFacade.getByPrimaryKey(org.getParentId());
							org.setIdpath(org1.getIdpath()+"/"+org.getId());
						}else{
							org.setIdpath(org.getId().toString());
						}
						sysOrgFacade.updatepo(org);

						String msg ="";
						ResultVo result2 = saveCheckConfig(id.toString());
						if(result2.getClass().equals("-1")){
							msg = result2.getResultDes();
						}
						result.setCode("0");
						result.setSuccess(true);
						result.setResultDes("保存成功"+msg);
					}else{
						result.setResultDes("机构名称或机构代码重复");
					}
				}else{
					sysOrgFacade.updatepo(sysOrg);
					result.setCode("0");
					result.setSuccess(true);
					result.setResultDes("修改成功");
				}

			//保存机构险种
			sysOrginstypeFacade.deleteByOrgId(sysOrg.getId());
			SysOrginstypeDTO ins= new SysOrginstypeDTO();
			Long insId = null;
			if(insList.size()>0){
				for(int i=0;i<insList.size();i++){
					insId = Long.parseLong(insList.get(i));
					ins.setInsId(insId);
					ins.setOrgId(sysOrg.getId());
					sysOrginstypeFacade.addSysOrginstype(ins);
				}
			}

			//保存机构系统
			sysOrgSystemFacade.deleteByOrgId(sysOrg.getId());
			SysOrgSystemDto systemDto= new SysOrgSystemDto();
			Long systemId = null;
			if(null != systemList && systemList.size()>0){
				for(int i=0;i<systemList.size();i++){
					systemId = Long.parseLong(systemList.get(i));
					systemDto.setSystemId(systemId);
					systemDto.setOrgId(sysOrg.getId());
					sysOrgSystemFacade.insertOrgSystem(systemDto);
				}
			}
		} catch (Exception e) {
			result.setResultDes("组织机构保存失败，失败原因："+e.getMessage());
		}
		return result;
	}

	/**
	 * 保存组织机构
	 * @param sysOrg
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/saveOrgV2" },method = RequestMethod.POST)
	@ApiOperation(value = "保存组织机构(柜面)")
	@OperLog(systemName="柜面后管系统")
	public ResultVo<JSONArray> saveOrg2(@RequestBody OrgVo orgVo) {
		ResultVo result = Results.newResultVo();
		result.setSuccess(false);
		SysOrgDTO sysOrg = orgVo.getSysOrgDTO();
		List<String> insList = orgVo.getInsList();
		// 获取机构系统id列表
		List<String> systemList = orgVo.getSystemList();
		Boolean flag = sysOrgFacade.checkOrgNameOrgenterCode(sysOrg);
		try {

			if(null ==sysOrg.getId() || "".equals(sysOrg.getId())){
				if(flag == false){//没有重复
					sysOrg.setOrgState("1");

					// 判断 是否为用户认证中心传来的
					if (sysOrg.getType()!=null){
						sysOrg.setType(sysOrg.getType()+1);
					}else {
						sysOrg.setType(1);
						Long id = sysOrgFacade.addSysOrg(sysOrg);
						sysOrg.setId(id);
						Random random = new Random();

						String loginName = "admin"+random.nextInt(1000);
						boolean b = sysUserFacade.checkLogonName(loginName, null);
						while (b){
							loginName = "admin"+random.nextInt(1000);
							b = sysUserFacade.checkLogonName(loginName,null);
						}
						SysUserDTO sysUserDTO = new SysUserDTO();
						sysUserDTO.setUserType("3");
						sysUserDTO.setUserState("1");
						sysUserDTO.setLogonName(loginName);
						sysUserDTO.setPasswd(StringUtil.getMD5("000000"));
						sysUserDTO.setAreaId(330000l);
						sysUserDTO.setOrgId(id);
						sysUserDTO.setPrseno(1l);
						sysUserDTO.setDisplayName("机构管理员");
						HashMap map = new HashMap();
						map.put("roleType","3");
						log.info("【sysUserDTO】：{}",sysUserDTO);
						List<SysUserRoleDTO> roleList = new ArrayList<>();
						try {
							Long aLong = sysUserFacade.addSysUser(sysUserDTO);

							List<SysRoleDTO> listByWhere = sysRoleFacade.getListByWhere(map);
							log.info("【listByWhere】：{},【map】：{}",listByWhere,map);
							if (listByWhere.size()!=0){
								SysUserRoleDTO sysUserRole = new SysUserRoleDTO();
								sysUserRole.setRoleId(listByWhere.get(0).getId());
								sysUserRole.setOrgId(listByWhere.get(0).getOrgId());
								sysUserRole.setUserId(aLong.toString());
								roleList.add(sysUserRole);
							}
							log.info("【roleList】：{}",roleList);
							sysUserFacade.insertByBatch(roleList);
							JSONObject out = new JSONObject();
							out.put("userId",aLong);
							out.put("username",loginName);
							out.put("password","000000");
							result.setResult(out);
						} catch (BizRuleException e) {
							e.printStackTrace();
						}



					}

//					Long id = sysOrgFacade.addSysOrg(sysOrg);
//					sysOrg.setId(id);
					SysOrgDTO org = sysOrgFacade.findByName(sysOrg.getOrgName());
					if(null != org.getParentId() && !"".equals(org.getParentId())){
						//填充idpath
						SysOrgDTO org1 = sysOrgFacade.getByPrimaryKey(org.getParentId());
						org.setIdpath(org1.getIdpath()+"/"+org.getId());
					}else{
						org.setIdpath(org.getId().toString());
					}
					sysOrgFacade.updatepo(org);

						result.setCode("0");
						result.setSuccess(true);
						result.setResultDes("保存成功");
					}else{
						result.setResultDes("机构名称或机构代码重复");
					}
				}else{
					sysOrgFacade.updatepo(sysOrg);
					result.setCode("0");
					result.setSuccess(true);
					result.setResultDes("修改成功");
				}


			//保存机构系统
			sysOrgSystemFacade.deleteByOrgId(sysOrg.getId());
			SysOrgSystemDto systemDto= new SysOrgSystemDto();
			Long systemId = null;
			if(null != systemList && systemList.size()>0){
				for(int i=0;i<systemList.size();i++){
					systemId = Long.parseLong(systemList.get(i));
					systemDto.setSystemId(systemId);
					systemDto.setOrgId(sysOrg.getId());
					sysOrgSystemFacade.insertOrgSystem(systemDto);
				}
			}
		} catch (Exception e) {
			result.setResultDes("组织机构保存失败，失败原因："+e.getMessage());
		}
		return result;
	}



	/**
	 * 根据id查询详情
	 * @param orgId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/findOrgById" })
	public ResultVo<Map<String, Object>> findOrgById(Model model, String orgId) {
		ResultVo<Map<String, Object>> result = Results.newResultVo();
		result.setSuccess(false);
		Map<String, Object> map = new ConcurrentHashMap<>();
		if(null !=orgId && !"".equals(orgId)){
			SysOrgDTO sysOrg = sysOrgFacade.getByPrimaryKey(Long.parseLong(orgId));
			//查询险种
			List<SysOrginstypeDTO> instypeList = sysOrginstypeFacade.findByOrgId(Long.parseLong(orgId));
			//查询机构
			List<SysOrgSystemDto> orgSystemList = sysOrgSystemFacade.findByOrgId(Long.parseLong(orgId));
			List<Object> insList=new ArrayList<Object>();
			List<Object> systemList=new ArrayList<Object>();
			if(instypeList.size()>0){
				for(SysOrginstypeDTO ins :instypeList){
					insList.add(ins.getInsId());
				}
			}
			if(orgSystemList.size()>0){
				for(SysOrgSystemDto systemDto :orgSystemList){
					systemList.add(systemDto.getSystemId());
				}
			}
			map.put("sysOrg", sysOrg);
		    map.put("insList", insList);
		    map.put("systemList", systemList);
			result.setResult(map);
			result.setCode("0");
			result.setSuccess(true);
		}
		return result;
	}

	/**
	 * 根据id查询详情
	 * @param orgId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/findParentOrgById" })
	public ResultVo findParentOrgById(Model model, String orgId) {
		ResultVo result = Results.newResultVo();
		result.setSuccess(false);
		Map<String, Object> map = new ConcurrentHashMap<>();
		if(null !=orgId && !"".equals(orgId)){
			HashMap<String, Object> map1 = new HashMap();
			map1.put("parentId",orgId);
			map1.put("types","0");
			map1.put("orgState","1");
			List<SysOrgDTO> listByWhere = sysOrgFacade.getListByWhere(map1);
			result.setResult(listByWhere);
			result.setCode("0");
			result.setSuccess(true);
		}
		return result;
	}



	/**
	 * 组织机构删除
	 * @param orgId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/deleteOrg" })
	@ApiOperation(value = "组织机构删除")
	@OperLog(systemName="后管系统")
	public ResultVo<SysOrgDTO> deleteOrg(String orgId) {
		ResultVo<SysOrgDTO> result = Results.newResultVo();
		result.setSuccess(false);
		if(null !=orgId && !"".equals(orgId)){
			SysOrgDTO sysOrg = sysOrgFacade.getByPrimaryKey(Long.parseLong(orgId));
			if(null == sysOrg){
				result.setResultDes("组织机构不存在");
			}
			else{
				try {
					//查找是否有用户关联
					HashMap<String, Object> searchMap = new HashMap<String, Object>();
					searchMap.put("orgId", orgId);
					List<SysUserDTO> userList = sysUserFacade.getListByWhere(searchMap);
					if(userList.size()>0){
						result.setResultDes("有关联用户不能删除");
						return result;
					}
					
					sysOrgFacade.deleteByPrimaryKey(Long.parseLong(orgId));
					result.setResultDes("删除成功");
					result.setCode("0");
					result.setSuccess(true);
				} catch (BizRuleException e) {
					result.setResultDes("删除失败，失败原因："+e.getMessage());
				}
			}
		}
		return result;
	}
	
	
	/**
	 * 获取区域列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getAreaTreeNodes" })
	public ResultVo<JSONArray> getAreaTreeNodes() {
		ResultVo<JSONArray> result = Results.newResultVo();
		//从缓存获取用户信息
		Long userId = loginComonent.getLoginUserId();
		SysUserDTO sysUser = sysUserFacade.getCacheByPrimaryKey(userId);
		JSONArray orgNodes = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(sysUserFacade.queryAreaNodes(sysUser))), "id", "parentId", "children");
		result.setCode("0");
		result.setSuccess(true);
		result.setResult(orgNodes);
		return result;
	}

	/**
	 * 获取全部区域列表-不论用户是谁
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getAllAreaTreeNodes" })
	public ResultVo<JSONArray> getAllAreaTreeNodes() {
		Long userId = 2l;
		SysUserDTO user = sysUserFacade.getCacheByPrimaryKey(userId);
		ResultVo<JSONArray> result = Results.newResultVo();
		List<SysOrgDTO> orgList = new ArrayList<>();
		if(user.getUserType().equals("1")){//管理员获取所有机构
			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("orgState", "1");
			orgList = sysOrgFacade.getListByWhere(searchMap);
		}else{
//			orgList = sysOrgFacade.queryOrgNodes(user.getAreaId().toString());
			List<SysUserOrgDTO> userOrgList = sysUserFacade.queryUserOrg(userId);
			if(userOrgList.size()>0){
				Set<String> s = new HashSet<>();
				for(SysUserOrgDTO uo :userOrgList){
					SysOrgDTO org = sysOrgFacade.getByPrimaryKey(uo.getOrgId());
					String idpath = org.getIdpath();
					String[] splitAddress=idpath.split("/"); //如果以竖线为分隔符，则split的时候需要加上两个斜杠【\\】进行转义
					for(int i=0;i<splitAddress.length;i++){
						s.add(splitAddress[i]);
					}
				}
				String orgIds = "'" + StringUtils.join(s, "','") + "'";
				orgList = sysOrgFacade.queryOrgListByOrgIds(orgIds);
			}
		}
		JSONArray jsonArray = null;
		if(orgList.size()>0){
			jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(orgList)), "id",
					"parentId", "children");
		}

		result.setCode("0");
		result.setSuccess(true);
		result.setResult(jsonArray);
		return result;
	}

	
	/**
	 * 获取险种列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getInsTypeList" })
	public ResultVo<List<SysCodeDTO>> getInsTypeList() {
		ResultVo<List<SysCodeDTO>> result = Results.newResultVo();
		List<SysCodeDTO> codeList = sysCodeFacade.findByCodeType("insurance");
		result.setCode("0");
		result.setSuccess(true);
		result.setResult(codeList);
		return result;
	}


    /**
     * 获取机构列表--取该id及其父系所有的机构
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/getListWithParent"})
    public ResultVo<Map<String, Object>> getListWithParent(Model model, Long orgId) {
        ResultVo<Map<String, Object>> result = Results.newResultVo();
        result.setSuccess(false);
        Map<String, Object> map = new ConcurrentHashMap<>();
        if (null != orgId) {
            List<SysOrgDTO> sysOrgs = sysOrgFacade.getListWithParent(orgId);
            map.put("sysOrgs", sysOrgs);
            result.setResult(map);
            result.setCode("0");
            result.setSuccess(true);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/findOrgList", method = RequestMethod.GET)
    public ResultVo findOrgList() {
        ResultVo result = Results.newResultVo();
        List list = new ArrayList();
        list.add("养老核发科");
        list.add("工伤核发科");
        list.add("关系转移科");
        list.add("参保征缴科");
        Map<String, Object> map = new ConcurrentHashMap<>();
        HashMap<String, Object> map1 = new HashMap();
        map1.put("type", "1");  //机构
        map1.put("orgState", "1");  //有效
        //获取到所有机构id
        List<SysOrgDTO> orgList = sysOrgFacade.getListByWhere(map1);
        if (orgList != null && orgList.size() > 0) {
            for (SysOrgDTO org : orgList) {
                SysOrgDepartmentSaveVO saveVO = new SysOrgDepartmentSaveVO();
                saveVO.setOrgId(org.getId());
                for (int i = 0; i < list.size(); i++) {
                    saveVO.setName(list.get(i).toString());
                    sysOrgDepartmentFacade.saveSysOrgDepartment(saveVO);
                }
            }
        }
        result.setCode("0");
        result.setSuccess(true);
        return result;
    }
    
    /**
     * 机构批量同步审核配置
     * @param orgIds
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/batchSaveCheckConfig"})
    public ResultVo batchSaveCheckConfig(String orgIds) {
    	ResultVo result = Results.newResultVo();
    	if(StringUtils.isEmpty(orgIds)){
    		result.setCode("-1");
            result.setSuccess(false);
            result.setResultDes("参数缺失");
            return result;
    	}
    	ResultVo result2 = saveCheckConfig(orgIds);
    	result.setCode(result2.getCode());
        result.setSuccess(result2.isSuccess());
    	result.setResultDes(result2.getResultDes());
        return result;
    }
    
    public ResultVo saveCheckConfig(String orgIds){
    	ResultVo result = Results.newResultVo();
    	//查询所有省级菜单和市级菜单
    	HashMap<String, Object> searchMap = new HashMap<>();
    	List<SysFunctionDTO> functionList = sysFunctionFacade.getFunLevelFunctionList();
    	String[] orgall = orgIds.split(",");
    	for(String orgId :orgall){
    		SysOrgDTO org = sysOrgFacade.getByPrimaryKey(Long.parseLong(orgId));
    		for(SysFunctionDTO f :functionList){
    			if("1".equals(f.getFunLevel())){
    				searchMap.clear();
            		searchMap.put("funId", f.getId());
            		List<SysCheckConfigDTO> cList= sysCheckConfigFacade.getListByWhere(searchMap);
            		if(CollectionUtils.isNotEmpty(cList)){
            			if("1".equals(cList.get(0).getAuFlag())){//手动审核才去配置
            				searchMap.clear();
                    		searchMap.put("funId", f.getId());
                			searchMap.put("orgId", orgId);
                			List<SysCheckConfigDTO> conList= sysCheckConfigFacade.getListByWhere(searchMap);
                			if(CollectionUtils.isEmpty(conList)){//如果不存在则新增
                				SysCheckConfigDTO config = new SysCheckConfigDTO();
                				SysCheckConfigDTO c=cList.get(0);
                				config.setOrgId(Long.parseLong(orgId));
                				config.setFunId(f.getId());
                				config.setFunName(f.getTitle());
                				config.setIsLeading(c.getIsLeading());
                				config.setIsProRepeat(c.getIsProRepeat());
                				config.setAuFlag("1");
                				try {
            						Long cid = sysCheckConfigFacade.addSysCheckConfig(config);
            						if("0".equals(c.getIsLeading())){//已柜面为准
            							SysCheckProcessDTO checkProcess = new SysCheckProcessDTO();
                		                checkProcess.setName("初审");
                		                checkProcess.setDispatchRule("4");
                		                checkProcess.setIsMaterial("0");
                		                checkProcess.setStep("0");
                		                checkProcess.setPollingNum(0);
                		                checkProcess.setConfigId(cid);
                		                checkProcess.setVersion("1");
                		                sysCheckProcessFacade.addSysCheckProcess(checkProcess);
            						}else{//按页面配置
            							List<SysCheckProcessDTO> proList = sysCheckProcessFacade.getListByConfigId(c.getId());
            							for(SysCheckProcessDTO p : proList){
            								SysCheckProcessDTO checkProcess = new SysCheckProcessDTO();
                    		                checkProcess.setName(p.getName());
                    		                checkProcess.setDispatchRule(p.getDispatchRule());
                    		                checkProcess.setIsMaterial(p.getIsMaterial());
                    		                checkProcess.setStep(p.getStep());
                    		                checkProcess.setPollingNum(p.getPollingNum());
                    		                checkProcess.setConfigId(cid);
                    		                checkProcess.setVersion("1");
                    		                sysCheckProcessFacade.addSysCheckProcess(checkProcess);
            							}
            						}
            						
            					} catch (BizRuleException e) {
            						result.setCode("-1");
            						result.setSuccess(false);
            						result.setResultDes("机构批量同步审核配置,失败原因："+e);
            						return result;
            					}
                			}
            			}
            		}
    			}else if("2".equals(f.getFunLevel())){
    				List<SysOrgDTO> orgList = new ArrayList<>();
					if("339900".equals(org.getRegionCode())){
						searchMap.clear();
	    				searchMap.put("regionCode", org.getRegionCode());
						orgList = sysOrgFacade.queryOrgListShenbenji(searchMap);
					}else{
						//查询机构所在的市
						SysAreaDTO areaDto= sysAreaFacade.getByPrimaryKey(Long.parseLong(org.getRegionCode()));
						//查询市下所有机构
						searchMap.clear();
	    				searchMap.put("regionCode", areaDto.getParentId());
						orgList = sysOrgFacade.queryOrgListArea(searchMap);
					}
					if(CollectionUtils.isNotEmpty(orgList)){
						searchMap.clear();
                		searchMap.put("funId", f.getId());
            			searchMap.put("orgId", orgList.get(0).getId());
            			//查询同市下其他机构配置
            			List<SysCheckConfigDTO> cList= sysCheckConfigFacade.getListByWhere(searchMap);
            			if(CollectionUtils.isNotEmpty(cList)){
            				searchMap.clear();
                    		searchMap.put("funId", f.getId());
                			searchMap.put("orgId", orgId);
                			List<SysCheckConfigDTO> conList= sysCheckConfigFacade.getListByWhere(searchMap);
                			if(CollectionUtils.isEmpty(conList)){//如果不存在则新增
                				SysCheckConfigDTO c=cList.get(0);
                				if("1".equals(c.getAuFlag())){
                					SysCheckConfigDTO config = new SysCheckConfigDTO();
                    				config.setOrgId(Long.parseLong(orgId));
                    				config.setFunId(f.getId());
                    				config.setFunName(f.getTitle());
                    				config.setIsLeading(c.getIsLeading());
                    				config.setIsProRepeat(c.getIsProRepeat());
                    				config.setAuFlag("1");
                    				try {
                						Long cid = sysCheckConfigFacade.addSysCheckConfig(config);
                						
                						List<SysCheckProcessDTO> proList = sysCheckProcessFacade.getListByConfigId(c.getId());
                						for(SysCheckProcessDTO pro :proList){
                							SysCheckProcessDTO checkProcess = new SysCheckProcessDTO();
                    		                checkProcess.setName(pro.getName());
                    		                checkProcess.setDispatchRule(pro.getDispatchRule());
                    		                checkProcess.setIsMaterial(pro.getIsMaterial());
                    		                checkProcess.setStep(pro.getStep());
                    		                checkProcess.setPollingNum(0);
                    		                checkProcess.setConfigId(cid);
                    		                checkProcess.setVersion("1");
                    		                sysCheckProcessFacade.addSysCheckProcess(checkProcess);
                						}
                					} catch (BizRuleException e) {
                						result.setCode("-1");
                						result.setSuccess(false);
                						result.setResultDes("机构批量同步审核配置,失败原因："+e);
                						return result;
                					}
                				}
                			}
            			}
					}
    			}
    		}
    		org.setIsSynchronization(1);
    		try {
				sysOrgFacade.updatepo(org);
			} catch (BizRuleException e) {
				result.setCode("-1");
				result.setSuccess(false);
				result.setResultDes("机构同步配置状态失败,失败原因："+e);
				return result;
			}
    	}
    	result.setCode("0");
		result.setSuccess(true);
		result.setResultDes("机构批量同步审核配置成功");
		return result;
    }
    
    
    /**
     * 设置同步状态
     * @param orgIds
     * @return
     * @throws BizRuleException 
     */
    @ResponseBody
    @RequestMapping(value = {"/setSyncStatus"})
    public ResultVo setSyncStatus() throws BizRuleException {
    	ResultVo result = Results.newResultVo();
    	//查询所有机构
    	HashMap<String, Object> searchMap = new HashMap<>();
    	searchMap.put("type", "1");
    	List<SysOrgDTO> orgList = sysOrgFacade.getListByWhere(searchMap);
    	//查询所有省级菜单和市级菜单
    	List<SysFunctionDTO> functionList = sysFunctionFacade.getFunLevelFunctionList();
    	for(SysOrgDTO org :orgList){
    		org.setIsSynchronization(1);
    		for(SysFunctionDTO f :functionList){
    			if("1".equals(f.getFunLevel())){//省级事项
    				searchMap.clear();
            		searchMap.put("funId", f.getId());
            		List<SysCheckConfigDTO> cList= sysCheckConfigFacade.getListByWhere(searchMap);
            		if(CollectionUtils.isNotEmpty(cList)){
            			if("1".equals(cList.get(0).getAuFlag())){//手动审核才去配置
            				searchMap.clear();
                    		searchMap.put("funId", f.getId());
                			searchMap.put("orgId", org.getId());
                			List<SysCheckConfigDTO> conList= sysCheckConfigFacade.getListByWhere(searchMap);
                			if(CollectionUtils.isEmpty(conList)){//如果不存在返回未同步
                				org.setIsSynchronization(0);
                				break;
                			}
            			}
            		}
    			}else if("2".equals(f.getFunLevel())){
    				List<SysOrgDTO> oList = new ArrayList<>();
    				searchMap.put("regionCode", org.getRegionCode());
					if("339900".equals(org.getRegionCode())){
						oList = sysOrgFacade.queryOrgListShenbenji(searchMap);
					}else{
						oList = sysOrgFacade.queryOrgListArea(searchMap);
					}
					if(CollectionUtils.isNotEmpty(oList)){
						searchMap.clear();
                		searchMap.put("funId", f.getId());
            			searchMap.put("orgId", org.getId());
            			List<SysCheckConfigDTO> conList= sysCheckConfigFacade.getListByWhere(searchMap);
            			if(CollectionUtils.isEmpty(conList)){//如果不存在返回未同步
            				org.setIsSynchronization(0);
            				break;
            			}
					}
    			}
        	}
    		sysOrgFacade.updatepo(org);
    	}
    	result.setCode("0");
        result.setSuccess(true);
    	result.setResult("初始化完成");
        return result;
    }


}
