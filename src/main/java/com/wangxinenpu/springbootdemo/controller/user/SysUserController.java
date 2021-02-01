package com.wangxinenpu.springbootdemo.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.wangxinenpu.springbootdemo.config.aop.OperLog;
import com.wangxinenpu.springbootdemo.config.component.LoginComponent;
import com.wangxinenpu.springbootdemo.dataobject.po.SysOrgDepartment;
import com.wangxinenpu.springbootdemo.dataobject.vo.*;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysOrgDepartment.SysOrgDepartmentListVO;
import com.wangxinenpu.springbootdemo.service.facade.*;
import com.wangxinenpu.springbootdemo.service.facade.SysOrgFacade;
import com.wangxinenpu.springbootdemo.service.facade.SysRoleFacade;
import com.wangxinenpu.springbootdemo.service.facade.SysUserFacade;
import com.wangxinenpu.springbootdemo.util.HttpUtil;
import com.wangxinenpu.springbootdemo.util.JavaBeanUtils;
import com.wangxinenpu.springbootdemo.util.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import star.bizbase.exception.BizRuleException;
import star.bizbase.vo.result.Results;
import star.util.StringUtil;
import star.vo.result.ResultVo;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 系统用户 管理
 *
 * @author Administrator
 *
 */
@Controller
@RequestMapping("sys/user")
@Api(tags = "系统用户管理")
@SuppressWarnings("all")
public class SysUserController  {

	@Autowired
	private SysUserFacade sysUserFacade;
	@Autowired
	private SysOrgFacade sysOrgFacade;
	@Autowired
	private SysAreaFacade sysAreaFacade;
	@Autowired
	private SysRoleFacade sysRoleFacade;
	@Autowired
	private LoginComponent loginComonent;

	@Autowired
	private SysUserEmpowerFacade sysUserEmpowerFacade;

	@Autowired
	private SysOrgDepartmentFacade sysOrgDepartmentFacade;

	protected Logger logger = LoggerFactory.getLogger(SysUserController.class);

	/**
	 * 分页查询数据
	 * @param logonName
	 * @param displayName
	 * @param orgId
	 * @param userState
	 * @param userType
	 * @param areaId
	 * @param cardId
	 * @param page
	 * @param size
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/queryTable" })
	public Map queryTable(HttpServletRequest request, Model model,
                          @RequestParam(name = "logonName") String logonName,
                          @RequestParam(name = "displayName") String displayName,
                          @RequestParam(name = "orgId") String orgId,
                          @RequestParam(name = "userState") String userState,
                          @RequestParam(name = "userType") String userType,
                          @RequestParam(name = "page") Integer page,
                          @RequestParam(name = "size") Integer size) {
		ResultVo<List<SysUserDTO>> result = Results.newResultVo();
		Map<String, Object> resultMap=new HashMap<>();
		//计算当前页
		page = (page -1)*size;
		//从缓存获取用户信息
		Long userId = loginComonent.getLoginUserId();
		SysUserDTO user = sysUserFacade.getCacheByPrimaryKey(userId);

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("logonName", logonName);
		searchMap.put("displayName", displayName);
		searchMap.put("userState", userState);
		searchMap.put("user", user);
		searchMap.put("userType", userType);
		searchMap.put("uState", 3);//不展示注销用户
		if(StringUtils.isNotBlank(orgId)){
			logger.info("admin走了么？？？orgId="+orgId);
			List<SysUserOrgDTO> uoList = sysUserFacade.queryUserOrgByOrgId(Long.parseLong(orgId));
			if(uoList.size()>0){
				Set<Long> s = new HashSet<>();
				for(SysUserOrgDTO uo :uoList){
					s.add(uo.getUserId());
				}
				String userIds = "'" + StringUtils.join(s, "','") + "'";
				searchMap.put("userIds", userIds);
				logger.info("admin走了么？？？userIds="+userIds);
			}else{
				result.setSuccess(false);
				result.setResultDes("未查询到结果");
				result.setCode("-1");
				result.setResult(null);
				resultMap.put("user",user);
				resultMap.put("count", 0);
				resultMap.put("pageList",result);
				return resultMap;
			}
		}else{
			logger.info("admin不知道走了没有");
			if(!user.getUserType().equals("1") && !user.getUserType().equals("2")){
				List<SysUserOrgDTO> uoList = sysUserFacade.queryUserOrgByOrgIds(userId);
				if(uoList.size()>0){
					Set<Long> s = new HashSet<>();
					for(SysUserOrgDTO uo :uoList){
						s.add(uo.getUserId());
					}
					String userIds = "'" + StringUtils.join(s, "','") + "'";
					searchMap.put("userIds", userIds);
					logger.info("admin走了么？？？userIds============="+userIds);
				}
			}
		}
        logger.info("searchMap==============="+searchMap);
		List<SysUserDTO> list = sysUserFacade.getListByWhere(searchMap,page,size);
		logger.info("list==============="+list);
		int count = sysUserFacade.getCountByWhere(searchMap);
		List<SysUserDTO> uList = new ArrayList<SysUserDTO>();
		for(SysUserDTO u : list){
			//获取所属区域
			List<String> orgNameList = new ArrayList<>();
			List<SysUserOrgDTO> userOrgList = sysUserFacade.queryUserOrg(u.getId());
			for(SysUserOrgDTO uo : userOrgList){
				orgNameList.add(uo.getOrgName());
			}
			u.setOrgNameList(orgNameList);
			//查询角色
			List<SysRoleDTO> rList = sysRoleFacade.queryRoleByUserId(String.valueOf(u.getId()));
			List<String> roleNameList = new ArrayList<>();
			for(SysRoleDTO r : rList){
				roleNameList.add(r.getRoleName());
			}
			u.setRoleName(roleNameList);
			uList.add(u);
		}
		result.setSuccess(true);
		result.setResultDes("查询成功");
		result.setCode("0");
		result.setResult(uList);
		resultMap.put("user",user);
		resultMap.put("count", count);
		resultMap.put("pageList",result);
		return resultMap;
	}

	/**
	 * 保存用户
	 * @param pageData
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/doSave" },method = RequestMethod.POST)
	@ApiOperation(value = "保存用户")
	@OperLog(systemName="后管系统")
	public ResultVo<SysUserDTO> save(@RequestBody JSONObject pageData, HttpServletRequest servlet){
		ResultVo<SysUserDTO> result = Results.newResultVo();
		result.setSuccess(false);
		Long opseno = null;
		try {
			SysUserDTO sysUser = JavaBeanUtils.pageElementToBean(pageData,SysUserDTO.class);
			//记录回退日志
			Long userId = loginComonent.getLoginUserId();//获取当前登录用户ID
			SysUserDTO user = sysUserFacade.getCacheByPrimaryKey(userId);//获取当前登录用户信息
			sysUser.setOpseno(1L);
			sysUser.setPrseno(1L);
			boolean bool = true;// 校验是否通过
			bool = sysUserFacade.checkLogonName(sysUser.getLogonName(), sysUser.getId());
			if (bool) {
				if(sysUser.getOrgId() != null){
				    logger.info("登录名="+sysUser.getLogonName());
                    SysUserDTO userDTO =  sysUserFacade.getByLoginName(sysUser.getLogonName());
					logger.info("user的机构id======"+userDTO.getOrgId());
					SysOrgDTO orgDTO = sysOrgFacade.getByPrimaryKey(userDTO.getOrgId());
					if(orgDTO != null){
					    logger.info("机构名称==="+orgDTO.getOrgName());
						result.setResultDes("在机构"+orgDTO.getOrgName()+"下，存在相同登录名");
					}else{
						result.setResultDes("存在相同登录名");
					}
				}else{
					result.setResultDes("该用户信息缺失不存在机构");
				}
				result.setSuccess(false);
				return result;
			}

			if( null == sysUser.getHeadUrl() || "".equals(sysUser.getHeadUrl()) ){
				String url = "web" + File.separator + "template" + File.separator + "user_head_default.svg";
				String key = HttpUtil.upload(url);
				sysUser.setHeadUrl(key);
			}

			if(null == sysUser.getId() || 0==sysUser.getId()){
				//一个用户只能绑定一个电话号码
				List<SysUserDTO> findUserByPhone = sysUserFacade.findUserByPhone(pageData.get("tel").toString());
				logger.info("电话号码集合findUserByPhone={}"+findUserByPhone);
				if (findUserByPhone!=null && findUserByPhone.size()>0) {
					result.setResultDes("存在相同的联系电话");
					result.setSuccess(false);
					return result;
				}
			}

			//获取一体化id是否已经绑定
			if(StringUtils.isNotBlank(sysUser.getYthUserId())){
				HashMap<String, Object> searchMap = new HashMap<>();
				searchMap.put("ythUserId", sysUser.getYthUserId());
				List<SysUserEmpowerDTO> userEmpList=sysUserEmpowerFacade.getListByWhere(searchMap);
				if(userEmpList.size()>0){
					if(null == sysUser.getId()){
						result.setResultDes("天正用户ID已被绑定");
						result.setSuccess(false);
						return result;
					}else{
						String userid = userEmpList.get(0).getuserId();
						if(Long.parseLong(userid) != sysUser.getId()){
							result.setResultDes("天正用户ID已被绑定");
							result.setSuccess(false);
							return result;
						}
					}
				}
			}

			//获取关联机构
			JSONArray orgList = pageData.getJSONArray("orgList");
			List<SysUserOrgDTO> uoList = new ArrayList<>();
			List<SysUserRoleDTO> roleList = new ArrayList<>();
			for(int i=0;i<orgList.size();i++){
   			 	JSONObject job = orgList.getJSONObject(i);
   			 	SysUserOrgDTO sysUserOrg = new SysUserOrgDTO();
   			 	sysUserOrg.setOrgId(Long.parseLong(job.get("orgId").toString()));
   			 	sysUserOrg.setAreaId(Long.parseLong(job.get("areaId").toString()));
   			 	if(job.containsKey("departmentId")) {
					sysUserOrg.setDepartmentId(job.getString("departmentId").toString());
				}
                uoList.add(sysUserOrg);
                //如果有多机构,默认保存一个
                sysUser.setOrgId(Long.parseLong(job.get("orgId").toString()));

                //获取角色
                JSONArray roleIds = (JSONArray) job.get("roleList");
                for(int j=0;j<roleIds.size();j++){
       			 	SysUserRoleDTO sysUserRole = new SysUserRoleDTO();
                    sysUserRole.setRoleId(roleIds.get(j).toString());
                    sysUserRole.setOrgId(Long.parseLong(job.get("orgId").toString()));
                    roleList.add(sysUserRole);
                }
            }
			sysUser.setSysUserOrgList(uoList);
            sysUser.setSysUserRoleList(roleList);
            //区域树值
            JSONArray areaIds = pageData.getJSONArray("areaIds");
            List<String> addAreaIds = new ArrayList<>();
            for (int i = 0; i < areaIds.size(); i++) {
                addAreaIds.add(areaIds.get(i).toString());
            }
            Map<String, List<String>> map = new ConcurrentHashMap<>();
            map.put("addAreaIds", addAreaIds);
            sysUserFacade.saveUser(sysUser, map);
            result.setCode("0");
            result.setSuccess(true);
            result.setResultDes("保存成功");
            result.setResult(user);
		} catch (Exception e) {
			result.setResultDes("保存失败，失败原因："+e.getMessage());
			logger.error("用户保存失败异常",e);
		}

		return result;
	}


	/**
	 * 修改密码
	 *
	 * @param model
	 * @param sysUserId
	 * @param oldPasswd
	 * @param newPasswd
	 * @return
	 */
	@RequestMapping("/updatePasswd")
	@ResponseBody
	@ApiOperation(value = "修改密码")
	@OperLog(systemName="后管系统")
	public Model updatePasswd(HttpServletRequest request, Model model, String oldPasswd, String newPasswd) {
		ResultVo<SysUserDTO> result = Results.newResultVo();
		//从缓存获取用户信息
		Long userId = loginComonent.getLoginUserId();
		SysUserDTO user = sysUserFacade.getByPrimaryKey(userId);
		result.setSuccess(false);
		if (StringUtil.isNotEmpty(oldPasswd) && StringUtil.isNotEmpty(newPasswd)) {
			if (StringUtil.getMD5(oldPasswd).equals(user.getPasswd())) {
				user.setPasswd(StringUtil.getMD5(newPasswd));
				int ret = sysUserFacade.updatepo(user);
				if (ret > 0) {
					result.setCode("0");
					result.setResultDes("密码修改成功");
					result.setSuccess(true);
				}
			} else {
				result.setResultDes("原密码不正确");
				result.setSuccess(false);
			}
		} else {
			result.setResultDes("信息不全");
			result.setSuccess(false);
		}
		model.addAttribute("result", result);
		return model;
	}

	/**
	 * 用户注销
	 *
	 * @param request
	 * @param userId
	 * @return
	 */
	@RequestMapping("/logoutUser")
	@ResponseBody
	@ApiOperation(value = "用户注销")
	@OperLog(systemName="后管系统")
	public ResultVo<String> logoutUser(HttpServletRequest request, String userId) {
		ResultVo<String> result = Results.newResultVo();
		result.setSuccess(false);
		try {
			//从缓存获取用户信息
			Long loninUserId = loginComonent.getLoginUserId();
			logger.info("缓存获取用户loninUserId={}"+loninUserId);
			if(userId.equals(loninUserId)){
				result.setSuccess(false);
				result.setResultDes("当前用户不可注销");
				return result;
			}
			sysUserFacade.logoutUser(Long.parseLong(userId));
			result.setResultDes("注销成功");
			result.setCode("0");
			result.setSuccess(true);
		} catch (BizRuleException e) {
			result.setResultDes("注销失败，失败原因：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 用户解锁
	 *
	 * @param request
	 * @param userId
	 * @return
	 */
	@RequestMapping("/unlockUser")
	@ResponseBody
	@ApiOperation(value = "用户解锁")
	@OperLog(systemName="后管系统")
	public ResultVo<String> unlockUser(HttpServletRequest request, String userId) {
		ResultVo<String> result = Results.newResultVo();
		result.setSuccess(false);
		try {
			//从缓存获取用户信息
			Long loninUserId = loginComonent.getLoginUserId();
			if(loninUserId.equals(userId)){
				result.setSuccess(false);
				result.setResultDes("当前用户不可操作");
				return result;
			}
			sysUserFacade.unlockUser(Long.parseLong(userId));
			result.setResultDes("解锁成功");
			result.setCode("0");
			result.setSuccess(true);
		} catch (BizRuleException e) {
			result.setResultDes("解锁失败，失败原因：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 锁定用户
	 *
	 * @param request
	 * @param userId
	 * @return
	 */
	@RequestMapping("/lockUser")
	@ResponseBody
	@ApiOperation(value = "锁定用户")
	@OperLog(systemName="后管系统")
	public ResultVo<String> lockUser(HttpServletRequest request, String userId) {
		ResultVo<String> result = Results.newResultVo();
		result.setSuccess(false);
		try {
			//从缓存获取用户信息
			Long loninUserId = loginComonent.getLoginUserId();
			if(loninUserId.equals(userId)){
				result.setSuccess(false);
				result.setResultDes("当前用户不可锁定");
				return result;
			}
			sysUserFacade.lockUser(Long.parseLong(userId));
			result.setResultDes("锁定成功");
			result.setCode("0");
			result.setSuccess(true);
		} catch (BizRuleException e) {
			result.setResultDes("锁定失败，失败原因：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 初始化密码
	 *
	 * @param userId
	 * @return
	 */
	@RequestMapping("/initPasswd")
	@ResponseBody
	@ApiOperation(value = "初始化密码")
	@OperLog(systemName="后管系统")
	public ResultVo<String> initPasswd(String userId) {
		ResultVo<String> result = Results.newResultVo();
		result.setSuccess(false);
		int ret;
		try {
			ret = sysUserFacade.resetPassWD(Long.parseLong(userId));
			if (ret > 0) {
				result.setCode("0");
				result.setSuccess(true);
				result.setResultDes("初始化密码成功，默认密码为：000000");
			}
		} catch (BizRuleException e) {
			result.setResultDes("初始化密码失败，失败原因：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 校验用户登录名是否重复
	 * @param request
	 * @param userId
	 * @param logonName
	 * @return
	 */
	@RequestMapping("/doCheck")
	@ResponseBody
	public ResultVo<String> doCheck(HttpServletRequest request, String userId, String logonName) {
		boolean bool = true;// 校验是否通过
		ResultVo<String> result = Results.newResultVo();
		result.setSuccess(false);
		bool = sysUserFacade.checkLogonName(logonName, Long.parseLong(userId));
		if (bool) {
			result.setResultDes("存在相同登录名");
			result.setSuccess(false);
		} else {
			result.setResultDes("登录名可用");
			result.setSuccess(true);
		}
		return result;
	}

	/**
	 *根据用户ID查询用户所有信息
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = { "/queryOneUser" })
	@ResponseBody
    public ResultVo<Map<String, Object>> queryOneUser(String userId){
		ResultVo<Map<String, Object>> result = Results.newResultVo();
		result.setSuccess(false);
		SysUserDTO sysUser = sysUserFacade.getByPrimaryKey(Long.parseLong(userId));
        List<SysUserAreaDTO> areaList;
        List<SysUserOrgDTO> orgList;

		try {
			orgList = sysUserFacade.queryUserOrg(Long.parseLong(userId));

			List<SysUserOrgDTO> userRoleList = new ArrayList<SysUserOrgDTO>();
			for(SysUserOrgDTO uo :orgList){
				List<String> roleIds = new ArrayList<>();
				List<String> roleNames = new ArrayList<>();
				SysUserOrgDTO userOrg = new SysUserOrgDTO();
				//获取机构下的角色
				List<SysUserRoleDTO> roleList = sysUserFacade.queryUserRole(Long.parseLong(userId),uo.getOrgId());
				for(SysUserRoleDTO ur :roleList){
					SysRoleDTO role = sysRoleFacade.getByPrimaryKey(ur.getRoleId());
					roleIds.add(ur.getRoleId());
					roleNames.add(role.getRoleName());
				}
				SysOrgDTO org = sysOrgFacade.getByPrimaryKey(uo.getOrgId());
				userOrg.setIdpath(org.getIdpath());
				userOrg.setRoleIds(roleIds);
				userOrg.setRoleNames(roleNames);
				userRoleList.add(userOrg);
			}
			//获取一体化id
			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("userId", userId);
			List<SysUserEmpowerDTO> userEmpList=sysUserEmpowerFacade.getListByWhere(searchMap);
			if(userEmpList.size()>0){
				sysUser.setYthUserId(userEmpList.get(0).getythUserId());
			}

			areaList = sysUserFacade.queryUserArea(Long.parseLong(userId));
			SysAreaDTO area = null;
			List<SysOrgDTO> userOrgList = new ArrayList<>();
			if (sysUser.getAreaId()!=null){
				area = sysAreaFacade.getByPrimaryKey(sysUser.getAreaId());
				userOrgList  = sysOrgFacade.queryOrgNodes(sysUser.getAreaId().toString());
			}
	        //查询自己所属区下机构

	        Map<String, Object> map = new ConcurrentHashMap<>();
	        map.put("sysUser", sysUser);
	        map.put("areaList", areaList);//查询用户绑定的区域
	        map.put("orgList", orgList);//查询用户关联机构
	        map.put("userOrgList", userOrgList);//当前可选择的机构
	        map.put("userRoleList", userRoleList);//查询用户关联结构对应的角色

	        List<String> areaArray =new ArrayList<String>();
	        Stack<String> areaNameArray=new Stack<>();
	        if (area != null){
	        	String pid = area.getParentId();
				areaNameArray.push(area.getAreaName());
	        	if(null != pid && !"".equals(pid)){
	        		SysAreaDTO area2 = sysAreaFacade.getByPrimaryKey(Long.parseLong(pid));
					areaNameArray.push(area2.getAreaName());
					if(null !=area2.getParentId() && !"".equals(area2.getParentId())){
	        			areaArray.add(area2.getParentId());
						SysAreaDTO area3 = sysAreaFacade.getByPrimaryKey(Long.parseLong(area2.getParentId()));
						areaNameArray.push(area3.getAreaName());
					}
	        		areaArray.add(area.getParentId());
	        	}
	        	areaArray.add(area.getId().toString());
	        }
	        String areaNameWithParent="";
	        while (!areaNameArray.empty()){
				areaNameWithParent=areaNameWithParent+"/"+areaNameArray.pop();
			}
			map.put("areaNameWithParent", areaNameWithParent);
			map.put("areaArray", areaArray);
	        result.setResult(map);
	        result.setCode("0");
	        result.setSuccess(true);
		} catch (BizRuleException e) {
			result.setResultDes("信息查询失败，失败原因："+e.getMessage());
		}
        return result;
    }

	/**
	 *根据用户ID查询用户所有信息
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = { "/getMyUserInfo" })
	@ResponseBody
	public ResultVo<Map<String, Object>> getMyUserInfo(){
		ResultVo<Map<String, Object>> result = Results.newResultVo();
		result.setSuccess(false);
		String userId=loginComonent.getLoginUserId()+"";
		logger.info("userId"+userId);
		SysUserDTO sysUser = sysUserFacade.getByPrimaryKey(Long.parseLong(userId));
		List<SysUserAreaDTO> areaList;
		List<SysUserOrgDTO> orgList;

		try {
			orgList = sysUserFacade.queryUserOrg(Long.parseLong(userId));

			List<SysUserOrgDTO> userRoleList = new ArrayList<SysUserOrgDTO>();
			for(SysUserOrgDTO uo :orgList){
				List<String> roleIds = new ArrayList<>();
				List<String> roleNames = new ArrayList<>();
				SysUserOrgDTO userOrg = new SysUserOrgDTO();
				//获取机构下的角色
				List<SysUserRoleDTO> roleList = sysUserFacade.queryUserRole(Long.parseLong(userId),uo.getOrgId());
				for(SysUserRoleDTO ur :roleList){
					SysRoleDTO role = sysRoleFacade.getByPrimaryKey(ur.getRoleId());
					roleIds.add(ur.getRoleId());
					roleNames.add(role.getRoleName());
				}
				SysOrgDTO org = sysOrgFacade.getByPrimaryKey(uo.getOrgId());
				userOrg.setIdpath(org.getIdpath());
				userOrg.setRoleIds(roleIds);
				userOrg.setRoleNames(roleNames);
				userRoleList.add(userOrg);
			}
			//获取一体化id
			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("userId", userId);
			List<SysUserEmpowerDTO> userEmpList=sysUserEmpowerFacade.getListByWhere(searchMap);
			if(userEmpList.size()>0){
				sysUser.setYthUserId(userEmpList.get(0).getythUserId());
			}

			areaList = sysUserFacade.queryUserArea(Long.parseLong(userId));
			SysAreaDTO area = null;
			List<SysOrgDTO> userOrgList = new ArrayList<>();
			if (sysUser.getAreaId()!=null){
				area = sysAreaFacade.getByPrimaryKey(sysUser.getAreaId());
				userOrgList  = sysOrgFacade.queryOrgNodes(sysUser.getAreaId().toString());
			}
			//查询自己所属区下机构

			Map<String, Object> map = new ConcurrentHashMap<>();
			map.put("sysUser", sysUser);
			map.put("areaList", areaList);//查询用户绑定的区域
			map.put("orgList", orgList);//查询用户关联机构
			map.put("userOrgList", userOrgList);//当前可选择的机构
			map.put("userRoleList", userRoleList);//查询用户关联结构对应的角色

			List<String> areaArray =new ArrayList<String>();
			Stack<String> areaNameArray=new Stack<>();
			if (area != null){
				String pid = area.getParentId();
				areaNameArray.push(area.getAreaName());
				if(null != pid && !"".equals(pid)){
					SysAreaDTO area2 = sysAreaFacade.getByPrimaryKey(Long.parseLong(pid));
					areaNameArray.push(area2.getAreaName());
					if(null !=area2.getParentId() && !"".equals(area2.getParentId())){
						areaArray.add(area2.getParentId());
						SysAreaDTO area3 = sysAreaFacade.getByPrimaryKey(Long.parseLong(area2.getParentId()));
						areaNameArray.push(area3.getAreaName());
					}
					areaArray.add(area.getParentId());
				}
				areaArray.add(area.getId().toString());
			}
			String areaNameWithParent="";
			while (!areaNameArray.empty()){
				areaNameWithParent=areaNameWithParent+"/"+areaNameArray.pop();
			}
			areaNameWithParent=areaNameWithParent.substring(1);
			map.put("areaNameWithParent", areaNameWithParent);
			map.put("areaArray", areaArray);
			result.setResult(map);
			result.setCode("0");
			result.setSuccess(true);
		} catch (BizRuleException e) {
			result.setResultDes("信息查询失败，失败原因："+e.getMessage());
		}
		return result;
	}


	/**
	 * 获取组织机构列表
	 * @param areaId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getOrgTreeNodes" })
	public ResultVo<JSONArray> getOrgTreeNodes(String areaId) {
		//从缓存获取用户信息
		Long userId = loginComonent.getLoginUserId();
		SysUserDTO sysUser = sysUserFacade.getCacheByPrimaryKey(userId);
		ResultVo<JSONArray> result = Results.newResultVo();
		JSONArray orgNodes = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(sysUserFacade.queryOrgNodes(areaId,sysUser))), "id", "parentId", "children");
		result.setCode("0");
		result.setSuccess(true);
		result.setResult(orgNodes);
		return result;
	}

	/**
	 * 关联机构
	 * @param areaId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getRelationOrgTreeNodes" })
	public ResultVo<JSONArray> getRelationOrgTreeNodes(String areaId) {
		//从缓存获取用户信息
		Long userId = loginComonent.getLoginUserId();
		SysUserDTO sysUser = sysUserFacade.getCacheByPrimaryKey(userId);
		ResultVo<JSONArray> result = Results.newResultVo();
		JSONArray orgNodes = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(sysUserFacade.queryRelationOrgNodes(areaId,sysUser))), "id", "parentId", "children");
		result.setCode("0");
		result.setSuccess(true);
		result.setResult(orgNodes);
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
	 * 获取管辖行政区域
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getAreaList" })
	public ResultVo<JSONArray> getAreaList(String areaId) {
		ResultVo<JSONArray> result = Results.newResultVo();
		List<SysAreaDTO> areaList= sysAreaFacade.findByIdpath(areaId);
		JSONArray orgNodes = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(areaList)), "id", "parentId", "children");
		result.setCode("0");
		result.setSuccess(true);
		result.setResult(orgNodes);
		return result;
	}

	/**
	 *  根据机构获取角色列表
	 * @param userType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getRoleTreeNodes" })
	public ResultVo<JSONArray> getRoleTreeNodes(String orgId, String userType) {
		ResultVo<JSONArray> result = Results.newResultVo();
		HashMap<String, Object> map = new HashMap<>();
        map.put("orgId", orgId);
        map.put("roleType", userType);
        map.put("active", "1");
        List<SysRoleDTO> roleList = sysRoleFacade.getListByWhere(map);

		JSONArray orgNodes = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(roleList)), "id", "parentId", "children");
		result.setCode("0");
		result.setSuccess(true);
		result.setResult(orgNodes);
		return result;
	}

	/**
	 * 获取用户关联机构信息
	 * @return
	 * @throws BizRuleException
	 */
	@ResponseBody
	@RequestMapping(value = { "/getOrgListByUserId" })
	public ResultVo<List<SysUserOrgDTO>> getOrgListByUserId() throws BizRuleException {
		ResultVo<List<SysUserOrgDTO>> result = Results.newResultVo();
		//从缓存获取用户信息
		Long userId = loginComonent.getLoginUserId();
        List<SysUserOrgDTO> userOrgList = sysUserFacade.queryUserOrg(userId);
        List<SysUserOrgDTO> orgList = new ArrayList<>();
        for(SysUserOrgDTO userOrg : userOrgList){
        	SysOrgDTO org= sysOrgFacade.getByPrimaryKey(userOrg.getOrgId());
			if(null !=org){
				userOrg.setOrgName(org.getOrgName());
			}
			orgList.add(userOrg);
        }

		result.setCode("0");
		result.setSuccess(true);
		result.setResult(orgList);
		return result;
	}

	/**
	 * 更新历史数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateUserList", method = RequestMethod.GET)
	@ApiOperation(value = "更新用户")
	@OperLog(systemName="后管系统")
	public ResultVo<JSONArray> updateUserList() {
		ResultVo<JSONArray> result = Results.newResultVo();
		List<SysUserDTO> userList= sysUserFacade.getAllList();
		if(userList != null && userList.size()>0){
			for(SysUserDTO user : userList){
				SysOrgDepartmentListVO listVO = new SysOrgDepartmentListVO();
				listVO.setOrgId(user.getOrgId());
				if("1".equals(user.getDepartment())){
					listVO.setName("养老核发科");
				}
				if("2".equals(user.getDepartment())){
					listVO.setName("工伤核发科");
				}
				if("3".equals(user.getDepartment())){
					listVO.setName("关系转移科");
				}
				if("4".equals(user.getDepartment())){
					listVO.setName("参保征缴科");
				}
				List<SysOrgDepartment> listDepartId = sysOrgDepartmentFacade.getSysOrgDepartmentList(listVO);
                if(listDepartId != null && listDepartId.size()==1){
					SysUserDTO dto = new SysUserDTO();
					dto.setId(user.getId());
					//获取科室id
					dto.setDepartment(listDepartId.get(0).getId()+"");
					int ret = sysUserFacade.updatepo(dto);
					if (ret > 0) {
						result.setCode("0");
						result.setSuccess(true);
					}
				}
			}
		}
		return result;
	}
}
