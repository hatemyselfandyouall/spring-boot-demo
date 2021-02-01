package com.wangxinenpu.springbootdemo.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.io.ByteStreams;

import com.wangxinenpu.springbootdemo.config.aop.OperLog;
import com.wangxinenpu.springbootdemo.config.component.CheckComponent;
import com.wangxinenpu.springbootdemo.config.component.CheckRedisComponent;
import com.wangxinenpu.springbootdemo.config.component.LoginComponent;
import com.wangxinenpu.springbootdemo.dataobject.po.SysOrgDepartment;
import com.wangxinenpu.springbootdemo.dataobject.vo.*;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysOrgDepartment.SysOrgDepartmentListVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysOrgDepartment.SysOrgDepartmentVO;
import com.wangxinenpu.springbootdemo.service.facade.*;
import com.wangxinenpu.springbootdemo.util.ExcelUtil;
import com.wangxinenpu.springbootdemo.util.JavaBeanUtils;
import com.wangxinenpu.springbootdemo.util.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import star.bizbase.exception.BizRuleException;
import star.bizbase.vo.result.Results;
import star.util.ExceptionUtil;
import star.vo.result.ResultVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 系统角色 管理
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("sys/role")
@RestController
@Api(tags = "统角色管理")
public class SysRoleController  {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SysRoleFacade sysRoleFacade;
	@Autowired
	private SysFunctionFacade sysFunctionFacade;
	@Autowired
	private LoginComponent loginComonent;
	@Autowired
	private SysUserFacade sysUserFacade;
	@Autowired
	private CheckComponent checkComponent;
	@Autowired
	private CheckRedisComponent checkRedisComponent;
	@Autowired
	private SysOrgFacade sysOrgFacade;
	@Autowired
	SysOrgDepartmentFacade sysOrgDepartmentFacade;

	/**
	 * 查询角色详情
	 * 
	 * @param request
	 * @param roleId
	 * @return
	 * @throws BizRuleException
	 */
	@ResponseBody
	@RequestMapping(value = { "/detail" })
	public ResultVo<SysRoleDTO> detail(HttpServletRequest request, String roleId) throws BizRuleException {
		ResultVo<SysRoleDTO> result = Results.newResultVo();
		SysRoleDTO sysRole = sysRoleFacade.getByPrimaryKey(roleId);
		SysOrgDTO org = sysOrgFacade.getByPrimaryKey(sysRole.getOrgId());
		sysRole.setIdpath(org.getIdpath());
		result.setSuccess(true);
		result.setCode("0");
		result.setResult(sysRole);
		return result;

	}

	/**
	 * 分页查询数据
	 * 
	 * @param request
	 * @param roleName
	 * @param roleDesc
	 * @param roleType
	 * @param page
	 * @param size
	 * @return
	 * @throws BizRuleException
	 */
	@ResponseBody
	@RequestMapping(value = { "/queryTable" })
	public Map queryTable(HttpServletRequest request, Model model, @RequestParam(name = "roleName") String roleName,
                          @RequestParam(name = "roleDesc") String roleDesc, @RequestParam(name = "roleType") String roleType,
                          @RequestParam(name = "orgId") String orgId, @RequestParam(name = "page") Integer page,
                          @RequestParam(name = "size") Integer size) {
		ResultVo<List<SysRoleDTO>> result = Results.newResultVo();
		Map<String, Object> resultMap = new HashMap<>();
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("roleName", roleName);
		searchMap.put("orgId", orgId);
		searchMap.put("roleDesc", roleDesc);
		searchMap.put("active", "1");
		// 从缓存获取用户信息
		Long userId = loginComonent.getLoginUserId();
		SysUserDTO user = sysUserFacade.getCacheByPrimaryKey(userId);
		String userType = user.getUserType();
		if (null != userType && !"".equals(userType)) {
			if ("1".equals(userType) || "2".equals(userType)) {// 超级管理员
				if (!StringUtils.isEmpty(orgId)) {
					List<Long> orgIdList = sysOrgFacade.getOrgIdsForParent(orgId);
					String orgIds = "'" + StringUtils.join(orgIdList, "','") + "'";
					searchMap.put("orgIds", orgIds);
					searchMap.remove("orgId");
				}
				searchMap.put("roleType", roleType);
			}
			if ("2".equals(userType)) {//系统管理员登录查询到的是所有的机构管理员和业务操作员
				//searchMap.put("areaId", user.getAreaId());
				if (!StringUtils.isEmpty(orgId)) {
					List<Long> orgIdList = sysOrgFacade.getOrgIdsForParent(orgId);
					String orgIds = "'" + StringUtils.join(orgIdList, "','") + "'";
					searchMap.put("orgIds", orgIds);
					searchMap.remove("orgId");
				}
				String roleTypes = "3,4";
				searchMap.put("roleTypes", roleTypes);
			}
			if ("3".equals(userType)) {// 机构管理员
				// searchMap.put("orgId", user.getOrgId());
				List<SysUserOrgDTO> uoList = sysUserFacade.queryUserOrg(userId);
				Set<Long> s = new HashSet<>();
				for (SysUserOrgDTO uo : uoList) {
					s.add(uo.getOrgId());
				}
				String orgIds = "'" + StringUtils.join(s, "','") + "'";
				searchMap.put("orgIds", orgIds);
				if (null != roleType && !"".equals(roleType)) {
					searchMap.put("roleType", roleType);
				} else {
					searchMap.put("roleType", "4");
				}
			}
			// 计算当前页
			page = (page - 1) * size;
			List<SysRoleDTO> list = sysRoleFacade.getListByWhere(searchMap, page, size);
			List<SysRoleDTO> roleList = new ArrayList<>();
			for (SysRoleDTO r : list) {
				SysOrgDTO org = sysOrgFacade.getByPrimaryKey(r.getOrgId());
				if (null != org) {
					r.setOrgName(org.getOrgName());
				}
				roleList.add(r);
			}
			int count = sysRoleFacade.getCountByWhere(searchMap);
			result.setCode("0");
			result.setSuccess(true);
			result.setResult(roleList);
			resultMap.put("count", count);
		} else {
			result.setResultDes("非管理员角色，不能看到任何角色");
			result.setCode("-1");
		}
		resultMap.put("pageList", result);
		return resultMap;
	}

	/**
	 * 修改角色信息
	 * 
	 * @param sysRole
	 * @param sysRole
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/updateRole" }, method = RequestMethod.POST)
	@ApiOperation(value = "修改角色信息")
	@OperLog(systemName = "后管系统")
	public ResultVo<SysRoleDTO> updateRole(@RequestBody SysRoleDTO sysRole) {
		ResultVo<SysRoleDTO> result = Results.newResultVo();
		boolean bool = true;// 校验是否通过
		bool = sysRoleFacade.checkRoleName(sysRole.getRoleName(), sysRole.getId(), sysRole.getOrgId());
		if (bool) {
			result.setResultDes("当前机构下该角色名已存在");
			result.setSuccess(false);
		} else {
			sysRoleFacade.updatepo(sysRole);
			result.setCode("0");
			result.setSuccess(true);
			result.setResultDes("修改成功");
			result.setResult(sysRole);
		}
		return result;

	}

	/**
	 * 新增角色信息
	 * 
	 * @param sysRole
	 * @param sysRole
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/addSysRole" }, method = RequestMethod.POST)
	@ApiOperation(value = "新增角色信息")
	@OperLog(systemName = "后管系统")
	public ResultVo<Map<String, Object>> addSysRole(@RequestBody SysRoleDTO sysRole) {
		ResultVo<Map<String, Object>> result = Results.newResultVo();
		result.setSuccess(false);
		// 生成主键ID
		String uuid = UUID.randomUUID().toString();
		String id = uuid.replace("-", "");
		sysRole.setId(id);

		// 从缓存获取用户信息
		Long userId = loginComonent.getLoginUserId();
		// SysUserDTO sysUser = sysUserFacade.getCacheByPrimaryKey(userId);
		// //机构管理员获取自身区域和机构
		// if(!"1".equals(sysUser.getUserType())){
		// long areaId = sysUser.getAreaId();
		// long orgId = sysUser.getOrgId();
		// sysRole.setAreaId(areaId);
		// sysRole.setOrgId(orgId);
		// }
		sysRole.setCreatorId(userId.toString());
		sysRole.setActive("1");

		try {
			boolean bool = true;// 校验是否通过
			bool = sysRoleFacade.checkRoleName(sysRole.getRoleName(), null, sysRole.getOrgId());
			if (bool) {
				result.setResultDes("当前机构下该角色名已存在");
				result.setSuccess(false);
			} else {
				String location = "sys/role/addSysRole";
				HashMap<String, Object> map = checkComponent.createCheckBusiness(location, "新增角色信息");
				logger.info("addSysRole 是否配置审核 map={}", map);
				boolean flag = (boolean) map.get("flag");
				String opseno = "";
				if (null != map.get("opseno")) {
					opseno = map.get("opseno").toString();
				}

				String msg = "";
				if (flag) {// 有审核配置
					String busId = map.get("busId").toString();
					Map<String, Object> checkMap = new HashMap<>();
					checkMap.put("type", "add");
					checkMap.put("object", sysRole);
					checkMap.put("bus", "role");
					checkRedisComponent.putRedis(busId, checkMap);
					msg = "新增成功，审核通过后数据生效";
				} else {
					sysRoleFacade.addSysRole(sysRole);
					msg = "新增成功";
				}
				Map<String, Object> retmap = new HashMap<>();
				retmap.put("opseno", opseno);
				result.setResultDes(msg);
				result.setResult(retmap);
				result.setCode("0");
				result.setSuccess(true);
			}
		} catch (BizRuleException e) {
			result.setResultDes("新增失败，失败原因：" + e.getMessage());
		}
		return result;

	}

	/**
	 * 角色删除
	 * 
	 * @param request
	 * @param roleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/deleteSysRole" })
	@ApiOperation(value = "角色删除")
	@OperLog(systemName = "后管系统")
	public ResultVo<SysRoleDTO> addSysRole(HttpServletRequest request, String roleId) {
		ResultVo<SysRoleDTO> result = Results.newResultVo();
		SysRoleDTO sysRole = sysRoleFacade.getByPrimaryKey(roleId);
		if (null == sysRole) {
			result.setResultDes("角色不存在");
			result.setSuccess(false);
		} else {
			try {
				sysRoleFacade.deleteByPrimaryKey(roleId);
				result.setResultDes("删除成功");
				result.setCode("0");
				result.setSuccess(true);
			} catch (BizRuleException e) {
				result.setSuccess(false);
				result.setResultDes("删除失败，失败原因：" + e.getMessage());
			}

		}
		return result;
	}

	/**
	 * 查询菜单树和已授权菜单
	 * 
	 * @param roleType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/queryTree" })
	public Map queryTree(Model model, String roleType, String roleId) {
		ResultVo<Map<String, JSONArray>> result = Results.newResultVo();
		Map<String, Object> map = new ConcurrentHashMap<>();
		if (StringUtils.isEmpty(roleType) || StringUtils.isEmpty(roleId)) {
			result.setCode("-1");
			result.setSuccess(false);
			result.setResultDes("参数缺失");
			map.put("result", result);
			return map;
		}
		Map<String, JSONArray> mapArray = new HashMap<>();
		List<SysFunctionDTO> functionList = new ArrayList<SysFunctionDTO>();
		if (roleType.equals("4") || roleType.equals("3") || roleType.equals("2")) { // 查询业务操作员,机构管理员,系统管理员可分配哪些系统的权限
			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("location", "/sys/function/queryTree");
			List<SysFunctionDTO> funList = sysFunctionFacade.getListByWhere(searchMap);
			// 从缓存获取用户信息
			Long userId = loginComonent.getLoginUserId();
			String userType = sysUserFacade.getByPrimaryKey(userId).getUserType();
			// admin账户给机构管理员分配权限 或 admin给系统管理员分配权限
			logger.info("打印userType=" + userType + "roleType=" + roleType);
			if (("1".equals(userType) && roleType.equals("3")) || ("1".equals(userType) && roleType.equals("2"))) {
				logger.info("进入行政区划方法");
				for (SysFunctionDTO f : funList) {
					functionList = sysFunctionFacade.findByFunTypeList(f.getSystemType());
					JSONArray jsonArray = TreeUtil.listToTree(
							JSONArray.parseArray(JSONArray.toJSONString(functionList)), "id", "parentId", "children");
					mapArray.put(f.getSystemType(), jsonArray);
				}
			} else {
				logger.info("admin授权业务管理员======" + roleType);
				List<SysRoleDTO> roleList = sysRoleFacade.queryRoleByUserId(String.valueOf(userId));
				String rId = null;
				List<String> roleIds = new ArrayList<>();
				if (roleList.size() > 0) {
					rId = roleList.get(0).getId();
					for (SysRoleDTO sysRoleDTO : roleList) {
						roleIds.add(sysRoleDTO.getId());
					}
				}
				// 机构管理员用户给业务操作员分配权限 或系统管理员给机构管理员分配权限
				if ("3".equals(userType) || ("2".equals(userType) && roleType.equals("3"))) {
					List<SysFunctionDTO> list = sysFunctionFacade.queryFunListByRoleId(roleIds);
					if (list.size() > 0) {// 只展示已授权的系统
						Map<String, List<SysFunctionDTO>> stringListMap = list.stream()
								.collect(Collectors.groupingBy(SysFunctionDTO::getFunType, Collectors.toList()));
						Set<String> functypes = stringListMap.keySet();
						for (String type : functypes) {
							List<SysFunctionDTO> subList = stringListMap.get(type);
							JSONArray jsonArray = TreeUtil.listToTree(
									JSONArray.parseArray(JSONArray.toJSONString(subList)), "id", "parentId",
									"children");
							mapArray.put(type, jsonArray);
						}
					}
				} else {
					// 系统管理员给业务操作员配置权限
					if ("2".equals(userType) && roleType.equals("4")) {
						functionList = sysFunctionFacade.findTreesByRoleType("2");
						JSONArray jsonArray = TreeUtil.listToTree(
								JSONArray.parseArray(JSONArray.toJSONString(functionList)), "id", "parentId",
								"children");
						mapArray.put("1", jsonArray);
					} else {
						logger.info("admin授权业务管理员会走么===" + roleType + funList);
						for (SysFunctionDTO f : funList) {
							searchMap.clear();
							searchMap.put("roleId", rId);
							searchMap.put("functionId", f.getId());
							List<SysRoleFunctionDTO> roleFunList = sysFunctionFacade.getRoleFunListByWhere(searchMap);
							logger.info("有权限的===" + roleFunList);
							if (roleFunList.size() > 0) {// 只展示已授权的系统
								functionList = sysFunctionFacade.findByFunTypeList(f.getSystemType());
								JSONArray jsonArray = TreeUtil.listToTree(
										JSONArray.parseArray(JSONArray.toJSONString(functionList)), "id", "parentId",
										"children");
								mapArray.put(f.getSystemType(), jsonArray);
							}
						}
					}
				}
			}
		} else {
			functionList = sysFunctionFacade.findTreesByRoleType(roleType);
			JSONArray jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(functionList)), "id",
					"parentId", "children");
			mapArray.put("1", jsonArray);

		}
		// 查询角色拥有的功能列表
		List<SysRoleFunctionDTO> rfList = sysFunctionFacade.findByRoleId(roleId);
		result.setCode("0");
		result.setSuccess(true);
		result.setResult(mapArray);
		map.put("rfList", rfList);
		map.put("result", result);
		return map;
	}

	/**
	 * 批量查询菜单树和已授权菜单
	 *
	 * @param queryTreeDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/pitchQueryTree" }, method = RequestMethod.POST)
	public Map pitchQueryTree(@RequestBody QueryTreeDTO queryTreeDTO) {
		ResultVo<Map<String, JSONArray>> result = Results.newResultVo();
		List<SysRoleDTO> queryTreeList = queryTreeDTO.getQueryTreeList();
		Map<String, Object> map = new ConcurrentHashMap<>();
		List<SysRoleFunctionDTO> listNew = new ArrayList<SysRoleFunctionDTO>();
		// 有哪些菜单
		Map<String, JSONArray> mapArray = new HashMap<>();
		for (SysRoleDTO sysRoleDTO : queryTreeList) {

			if (StringUtils.isEmpty(sysRoleDTO.getRoleType()) || StringUtils.isEmpty(sysRoleDTO.getId())) {
				result.setCode("-1");
				result.setSuccess(false);
				result.setResultDes("参数缺失");
				map.put("result", result);
				return map;
			}

			List<SysFunctionDTO> functionList = new ArrayList<SysFunctionDTO>();
			if (sysRoleDTO.getRoleType().equals("4") || sysRoleDTO.getRoleType().equals("3")) {// 查询业务操作员可分配哪些系统的权限
				HashMap<String, Object> searchMap = new HashMap<>();
				searchMap.put("location", "/sys/function/queryTree");
				List<SysFunctionDTO> funList = sysFunctionFacade.getListByWhere(searchMap);
				// 从缓存获取用户信息
				Long userId = loginComonent.getLoginUserId();
				String userType = sysUserFacade.getByPrimaryKey(userId).getUserType();
				// admin账户给机构管理员分配权限
				if ("1".equals(userType) && sysRoleDTO.getRoleType().equals("3")) {
					for (SysFunctionDTO f : funList) {
						functionList = sysFunctionFacade.findByFunTypeList(f.getSystemType());
						JSONArray jsonArray = TreeUtil.listToTree(
								JSONArray.parseArray(JSONArray.toJSONString(functionList)), "id", "parentId",
								"children");
						mapArray.put(f.getSystemType(), jsonArray);
					}
				} else {
					List<SysRoleDTO> roleList = sysRoleFacade.queryRoleByUserId(String.valueOf(userId));
					String rId = null;
					List<String> roleIds = new ArrayList<>();
					if (roleList.size() > 0) {
						rId = roleList.get(0).getId();
						for (SysRoleDTO sysRoleDTO2 : roleList) {
							roleIds.add(sysRoleDTO2.getId());
						}
					}
					// 机构管理员用户给业务操作员分配权限
					if ("3".equals(userType)) {
						List<SysFunctionDTO> list = sysFunctionFacade.queryFunListByRoleId(roleIds);
						if (list.size() > 0) {// 只展示已授权的系统
							Map<String, List<SysFunctionDTO>> stringListMap = list.stream()
									.collect(Collectors.groupingBy(SysFunctionDTO::getFunType, Collectors.toList()));
							Set<String> functypes = stringListMap.keySet();
							for (String type : functypes) {
								List<SysFunctionDTO> subList = stringListMap.get(type);
								JSONArray jsonArray = TreeUtil.listToTree(
										JSONArray.parseArray(JSONArray.toJSONString(subList)), "id", "parentId",
										"children");
								mapArray.put(type, jsonArray);
							}
						}
					} else {
						for (SysFunctionDTO f : funList) {
							searchMap.clear();
							searchMap.put("roleId", rId);
							searchMap.put("functionId", f.getId());
							List<SysRoleFunctionDTO> roleFunList = sysFunctionFacade.getRoleFunListByWhere(searchMap);
							if (roleFunList.size() > 0) {// 只展示已授权的系统
								functionList = sysFunctionFacade.findByFunTypeList(f.getSystemType());
								JSONArray jsonArray = TreeUtil.listToTree(
										JSONArray.parseArray(JSONArray.toJSONString(functionList)), "id", "parentId",
										"children");
								mapArray.put(f.getSystemType(), jsonArray);
							}
						}
					}
				}
			} else {
				functionList = sysFunctionFacade.findTreesByRoleType(sysRoleDTO.getRoleType());
				JSONArray jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(functionList)),
						"id", "parentId", "children");
				mapArray.put("1", jsonArray);

			}
			// 查询角色拥有的功能列表
			List<SysRoleFunctionDTO> rfList = sysFunctionFacade.findByRoleId(sysRoleDTO.getId());
			if (CollectionUtils.isEmpty(listNew)) {
				listNew = rfList;
			}
			if (rfList.size() != listNew.size()) {
				result.setCode("0");
				result.setSuccess(true);
				result.setResult(mapArray);
				map.put("rfList", new ArrayList<>());
				map.put("result", result);
				return map;
			}

			result.setCode("0");
			result.setSuccess(true);
			result.setResult(mapArray);
			map.put("rfList", listNew);
			map.put("result", result);
		}

		return map;
	}

	/**
	 * 授权
	 * 
	 * @param jsonObject
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/addRoleRef" }, method = RequestMethod.POST)
	@ApiOperation(value = "角色授权")
	@OperLog(systemName = "后管系统")
	public ResultVo<String> addRoleRef(@RequestBody JSONObject jsonObject) {
		ResultVo<String> result = Results.newResultVo();
		String roleId = (String) jsonObject.get("roleId");
		// 判断角色有效性
		SysRoleDTO role = sysRoleFacade.getByPrimaryKey(roleId);
		JSONArray jsonArray = null;
		try {
			if (null != role) {
				jsonArray = jsonObject.getJSONArray("treeInfo");
				// JSONObject jsonObject1 = null;
				List<SysRoleFunctionDTO> list = new ArrayList<>();
				for (int i = 0; i < jsonArray.size(); i++) {
					SysRoleFunctionDTO sysRoleFunction = JavaBeanUtils.pageElementToBean((JSONObject) jsonArray.get(i),
							SysRoleFunctionDTO.class);
					// jsonObject1 = (JSONObject)
					// JSONObject.parse(jsonArray.get(i).toString());
					// Long functionId = jsonObject1.getLong("functionId");
					// list.add(functionId);
					list.add(sysRoleFunction);
				}
				sysRoleFacade.deleteRoleFunctionRefAndAddNewRef(roleId, list);
				result.setSuccess(true);
				result.setResultDes("授权成功");
			} else {
				result.setSuccess(false);
				result.setResultDes("角色不存在");
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResultDes("授权失败，失败原因：" + e.getMessage());
		}

		return result;
	}

	/**
	 * 批量角色授权
	 *
	 * @param sysPatchRoleDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/patchAddRoleRef" }, method = RequestMethod.POST)
	@ApiOperation(value = "批量角色授权")
	@OperLog(systemName = "后管系统")
	public ResultVo<String> patchAddRoleRef(@RequestBody SysPatchRoleDTO sysPatchRoleDTO) {
		ResultVo<String> result = Results.newResultVo();
		List<String> roleIdList = sysPatchRoleDTO.getRoleIdList();
		if (CollectionUtils.isEmpty(roleIdList)) {
			result.setSuccess(false);
			result.setResultDes("参数角色ID为空");
			return result;
		}
		try {
			// 判断角色有效性
			for (String roleId : roleIdList) {
				SysRoleDTO role = sysRoleFacade.getByPrimaryKey(roleId);
				if (role == null) {
					result.setSuccess(false);
					result.setResultDes("角色不存在");
					return result;
				}
			}
			List<SysRoleFunctionDTO> list = sysPatchRoleDTO.getTreeInfo();
			// if(CollectionUtils.isEmpty(list)){
			// result.setSuccess(false);
			// result.setResultDes("参数角色功能对照为空");
			// return result;
			// }
			sysRoleFacade.patchAddRoleFunctionRefAndAddNewRef(roleIdList, list);
			result.setSuccess(true);
			result.setResultDes("授权成功");

		} catch (Exception e) {
			result.setSuccess(false);
			result.setResultDes("授权失败，失败原因：" + e.getMessage());
		}

		return result;
	}

	/**
	 * 下载模板
	 */
	@ResponseBody
	@RequestMapping(value = "/downloadTemplate", produces = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) {
		try {
			String realPath = request.getServletContext().getRealPath("/");
			String fileSeperator = File.separator;
			realPath += fileSeperator + "template" + fileSeperator;
			String localFileName = "角色信息导入模板.xlsx";
			String localFilePath = realPath + localFileName;
			java.io.File file = Paths.get(localFilePath).toFile();

			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
			String fileName = URLEncoder.encode(localFileName, "UTF-8");
			response.setHeader("Content-disposition", "attachment; filename=" + fileName);
			OutputStream stream = response.getOutputStream();
			stream.write(ByteStreams.toByteArray(new FileInputStream(file)));
			stream.flush();
			stream.close();
		} catch (Exception e) {
			logger.error("userId={},error={}", loginComonent.getLoginUserId(), ExceptionUtil.getMessage(e));// 打印全部异常日志
		}
	}

	/**
	 * 角色信息导入
	 * 
	 * @param file
	 * @param orgId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	@ApiOperation(value = "角色信息导入")
	@OperLog(systemName = "后管系统")
	public ResultVo<Object> doImport(@RequestParam("file") MultipartFile file, Long orgId) {
		ResultVo<Object> resultVo = Results.newResultVo();
		resultVo.setSuccess(false);

		if (orgId == null) {
			resultVo.setCode("-1");
			resultVo.setResultDes("导入失败：机构id为空");
			return resultVo;
		}
		// 从缓存获取用户信息
		Long userId = loginComonent.getLoginUserId();
		SysUserDTO user = sysUserFacade.getCacheByPrimaryKey(userId);

		String roleName = null;
		String roleDesc = null;
		String position = null;

		SysRoleDTO sysRole = new SysRoleDTO();
		List<SysRoleDTO> roleList = new ArrayList<>();
		try {
			List<String[][]> excel_list = ExcelUtil.getData(file, 1);
			if (excel_list == null || excel_list.isEmpty()) {
				resultVo.setCode("-1");
				resultVo.setResultDes("导入失败：传入的文件内容为空");
				return resultVo;
			}

			for (String[][] Sheets : excel_list) {// 第一个循环遍历Sheet
				for (String[] rows : Sheets) {// 第二个循环遍历row
					if (rows != null && rows.length > 1) {
						roleName = rows[1];
						roleDesc = rows[2];
						position = rows[3];
						if (roleName.equals("角色名称") || roleDesc.equals("角色描述")) {
							resultVo.setCode("-1");
							resultVo.setResultDes("导入失败：传入的文件内容为空");
							return resultVo;
						}
						sysRole = new SysRoleDTO();
						// 生成主键ID
						String uuid = UUID.randomUUID().toString();
						String id = uuid.replace("-", "");
						sysRole.setId(id);
						sysRole.setOrgId(orgId);
						sysRole.setCreatorId(userId.toString());
						sysRole.setActive("1");
						if (user.getUserType().equals("1")) {
							sysRole.setRoleType("3");
						} else {
							sysRole.setRoleType("4");
						}
						sysRole.setRoleName(roleName);
						sysRole.setRoleDesc(roleDesc);
						sysRole.setPosition(position);

						boolean bool = true;// 校验是否通过
						bool = sysRoleFacade.checkRoleName(roleName, null, orgId);
						if (bool) {
							resultVo.setCode("-1");
							resultVo.setResultDes("导入失败：当前机构下该角色名已存在,角色名：" + roleName);
							return resultVo;
						}
						roleList.add(sysRole);
					}
				}
			}
			sysRoleFacade.insertByBatch(roleList);
		} catch (Exception e) {
			resultVo.setCode("-1");
			resultVo.setResultDes("导入失败：" + e.getLocalizedMessage());
			e.printStackTrace();
			return resultVo;
		}

		resultVo.setCode("0");
		resultVo.setResultDes("导入成功");
		resultVo.setSuccess(true);
		return resultVo;
	}

	/**
	 * 查询机构角色下的用户
	 * 
	 * @param orgId
	 * @param roleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/querySysUserByRoleId2", method = RequestMethod.GET)
	public ResultVo<SysUserTreeVO> queryUserByRole2(@RequestParam(name = "orgId") Long orgId,
                                                    @RequestParam(name = "roleId") String roleId, @RequestParam(name = "roleType") String roleType) {
		ResultVo<SysUserTreeVO> result = Results.newResultVo();
		try {
			SysUserTreeVO treeVO = new SysUserTreeVO();
			List<SysOrgDepartmentVO> listTreeVO = new ArrayList<>();
			// 根据角色类型去查询科室为空的用户
			if (StringUtils.isEmpty(roleType)) {
				result.setCode("-1");
				result.setSuccess(false);
				result.setResultDes("请先选择角色!");
			}
			List<SysUserDTO> userListNoDepartment = sysUserFacade.queryUserListByUserType(orgId, roleType);
			if (userListNoDepartment != null && userListNoDepartment.size() > 0) {
				// 没有科室的用户
				treeVO.setUserListNoDepartment(userListNoDepartment);
			}
			// 根据角色id查询出来有关联关系的用户
			List<SysUserRoleDTO> userAndRoleList = sysUserFacade.findUserByRoleId(roleId);
			treeVO.setUserAndRoleList(userAndRoleList);
			// 根据orgId查询科室
			SysOrgDepartmentListVO listVO = new SysOrgDepartmentListVO();
			listVO.setOrgId(orgId);
			// 查询出所有的科室list
			List<SysOrgDepartment> list = sysOrgDepartmentFacade.getSysOrgDepartmentList(listVO);

			if (list != null && list.size() > 0) {
				for (SysOrgDepartment department : list) {
					SysOrgDepartmentVO departmentVO = new SysOrgDepartmentVO();
					departmentVO.setId(department.getId());
					departmentVO.setName(department.getName());
					if (department.getId() != null) {
						// 根据科室id和角色类型查询所有用户
						List<SysUserDTO> listUserByAll = sysUserFacade
								.getUserListByDepartmentId(department.getId() + "", roleType);
						departmentVO.setList(listUserByAll);
						listTreeVO.add(departmentVO);
					}
				}
				treeVO.setUserListByDepartment(listTreeVO);
			}
			result.setCode("0");
			result.setSuccess(true);
			result.setResult(treeVO);
			result.setResultDes("查询成功");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResultDes("查询失败，失败原因：" + e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/querySysUserByRoleId", method = RequestMethod.GET)
	public ResultVo<SysUserTreeVO> queryUserByRole(@RequestParam(name = "orgId") Long orgId,
                                                   @RequestParam(name = "roleId") String roleId, @RequestParam(name = "roleType") String roleType) {
		ResultVo<SysUserTreeVO> result = Results.newResultVo();
		try {
			SysUserTreeVO treeVO = new SysUserTreeVO();
			// 根据角色类型去查询科室为空的用户
			if (StringUtils.isEmpty(roleType)) {
				result.setCode("-1");
				result.setSuccess(false);
				result.setResultDes("请先选择角色!");
			}

			// 根据角色id查询出来有关联关系的用户
			List<SysUserRoleDTO> userAndRoleList = sysUserFacade.findUserByRoleId(roleId);
			treeVO.setUserAndRoleList(userAndRoleList);

			// 获取当前登录用户的所属区域
			// 从缓存获取用户信息
			Long userId = loginComonent.getLoginUserId();
			SysUserDTO user = sysUserFacade.getCacheByPrimaryKey(userId);
			List<Object> arrList = new ArrayList<>();
			List<SysOrgDTO> orgList = new ArrayList<>();
			if (user.getUserType().equals("1") || user.getUserType().equals("2")) {// 管理员获取所有机构
				HashMap<String, Object> searchMap = new HashMap<>();
				//根据角色类型查询所有用户
				searchMap.put("userType", roleType);
				List<SysUserDTO> userList = sysUserFacade.getListByWhere(searchMap);
				searchMap.clear();
				searchMap.put("orgState", "1");
				orgList = sysOrgFacade.getListByWhere(searchMap);
				//把用户机构ID循环放到ParentId中
				userList.forEach(i->{
					i.setParentId(i.getOrgId()+"");
					i.setUserCode(i.getId());
				});
				//两个list组装
				arrList.addAll(userList);
				arrList.addAll(orgList);
			} else {
				List<SysUserOrgDTO> userOrgList = sysUserFacade.queryUserOrg(userId);
				if (userOrgList.size() > 0) {
					Set<String> s = new HashSet<>();
					List<SysUserDTO> userList = new ArrayList<SysUserDTO>();
					for (SysUserOrgDTO uo : userOrgList) {
						HashMap<String, Object> searchMap = new HashMap<>();
						//根据角色类型和机构ID查询所有用户
						searchMap.put("userType", roleType);
						searchMap.put("orgId", uo.getOrgId());
						List<SysUserDTO> usList = sysUserFacade.getListByWhere(searchMap);
						userList.addAll(usList);
						
						SysOrgDTO org = sysOrgFacade.getByPrimaryKey(uo.getOrgId());
						String idpath = org.getIdpath();
						String[] splitAddress = idpath.split("/"); // 如果以竖线为分隔符，则split的时候需要加上两个斜杠【\\】进行转义
						for (int i = 0; i < splitAddress.length; i++) {
							s.add(splitAddress[i]);
						}
					}
					String orgIds = "'" + StringUtils.join(s, "','") + "'";
					orgList = sysOrgFacade.queryOrgListByOrgIds(orgIds);
					//把用户机构ID循环放到ParentId中
					userList.forEach(i->{
						i.setParentId(i.getOrgId()+"");
						i.setUserCode(i.getId());
					});
					//两个list组装
					arrList.addAll(userList);
					arrList.addAll(orgList);
				}
			}
			JSONArray jsonArray = null;
			if (arrList.size() > 0) {
				jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(arrList)), "id", "parentId",
						"children");
				treeVO.setUserListTreeArray(jsonArray);
			}

			result.setCode("0");
			result.setResultDes("查询成功");
			result.setSuccess(true);
			result.setResult(treeVO);
			return result;

		} catch (Exception e) {
			result.setSuccess(false);
			result.setResultDes("查询失败，失败原因：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 复制角色信息
	 * 
	 * @param roleVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/addCopySysRole" }, method = RequestMethod.POST)
	@ApiOperation(value = "复制角色信息")
	@OperLog(systemName = "后管系统")
	public ResultVo<Map<String, Object>> addCopySysRole(@RequestBody SysRoleListVo roleVo) {
		ResultVo<Map<String, Object>> result = Results.newResultVo();
		try {
			// result.setSuccess(false);
			if (roleVo == null || roleVo.getId() == null) {
				result.setResultDes("请选择要复制的数据！");
				result.setSuccess(false);
			}
			// 根据id查询要复制的数据
			SysRoleDTO oldRoleVo = sysRoleFacade.getByPrimaryKey(roleVo.getId());
			SysRoleDTO sysRole = new SysRoleDTO();
			BeanUtils.copyProperties(oldRoleVo, sysRole);
			// 生成主键ID
			String uuid = UUID.randomUUID().toString();
			String id = uuid.replace("-", "");
			sysRole.setId(id);
			Long userId = loginComonent.getLoginUserId();
			sysRole.setCreatorId(userId.toString());
			sysRole.setCreateTime(new Date());
			if (sysRole.getRoleName() != null) {
				HashMap<String, Object> searchMap = new HashMap<>();
				if (StringUtils.isNotEmpty(sysRole.getRoleName())) {
					if (sysRole.getRoleName().contains("副本")) {
						searchMap.put("roleName",
								sysRole.getRoleName().substring(0, sysRole.getRoleName().length() - 1));
					} else {
						searchMap.put("roleName", sysRole.getRoleName() + "-副本");
					}
				}
				int flag = sysRoleFacade.getCountByWhere(searchMap);
				if (sysRole.getRoleName().contains("副本")) {
					flag = flag + 1;
					sysRole.setRoleName(sysRole.getRoleName().substring(0, sysRole.getRoleName().length() - 1) + flag);
				} else {
					flag = flag + 1;
					sysRole.setRoleName(sysRole.getRoleName() + "-副本" + flag);
				}

				// 添加角色信息
				sysRoleFacade.addSysRole(sysRole);
				// 添加复制用户的菜单权限
				List<SysRoleFunctionDTO> sysFunctionList = sysFunctionFacade.findByRoleId(roleVo.getId());
				sysRoleFacade.deleteRoleFunctionRefAndAddNewRef(sysRole.getId(), sysFunctionList);
				result.setResultDes("新增成功");
				result.setCode("0");
				result.setSuccess(true);
			}

		} catch (BizRuleException e) {
			result.setResultDes("新增失败，失败原因：" + e.getMessage());
		}
		return result;

	}

	/**
	 * 在角色下添加用户
	 * 
	 * @param listVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/addUserInRole" }, method = RequestMethod.POST)
	@ApiOperation(value = "在角色下添加用户")
	@OperLog(systemName = "后管系统")
	public ResultVo<Map<String, Object>> addUserInRole(@RequestBody SysUserRoleListVO listVo) {
		ResultVo<Map<String, Object>> result = Results.newResultVo();
		try {
			// 有添加角色保存删除关联关系
			if (StringUtils.isNotEmpty(listVo.getRoleId())) {
				// 删除角色下的关联关系
				sysRoleFacade.deleteRelationByRoleId(listVo.getRoleId());
			} else {
				result.setCode("-1");
				result.setResultDes("角色不能为空！");
				result.setSuccess(false);
				return result;
			}
			Set<String> userIdSet = new HashSet<>();
			List<SysUserRoleDTO> sysUserRoleDTOS = listVo.getUserlist();
			for (SysUserRoleDTO dto : sysUserRoleDTOS) {
				userIdSet.add(dto.getUserId());
			}
			List<SysUserRoleDTO> sysUserRoleDTOSNew = new ArrayList<>();
			for (Iterator<String> userIdIter = userIdSet.iterator(); userIdIter.hasNext();) {
				String userIdNew = userIdIter.next();
				for (SysUserRoleDTO dto : sysUserRoleDTOS) {
					String userIdOld = dto.getUserId();
					if (userIdNew.equals(userIdOld)) {
						dto.setOrgId(listVo.getOrgId());
						dto.setRoleId(listVo.getRoleId());
						dto.setCreateTime(new Date());
						sysUserRoleDTOSNew.add(dto);
						break;
					}
				}
			}
			if (sysUserRoleDTOSNew != null && sysUserRoleDTOSNew.size() > 0) {
				sysUserFacade.insertByBatch(listVo.getUserlist());
			}
			result.setResultDes("保存成功");
			result.setCode("0");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setResultDes("保存失败，失败原因：" + e.getMessage());
		}
		return result;
	}
}
