package com.wangxinenpu.springbootdemo.controller.sys;


import com.alibaba.fastjson.JSONArray;
import com.wangxinenpu.springbootdemo.config.component.LoginComponent;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysFunctionDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysRoleDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysRoleFunctionDTO;
import com.wangxinenpu.springbootdemo.service.facade.SysFunctionFacade;
import com.wangxinenpu.springbootdemo.service.facade.SysRoleFacade;
import com.wangxinenpu.springbootdemo.service.facade.SysRoleInnertranFunctionFacade;
import com.wangxinenpu.springbootdemo.service.facade.SysUserFacade;
import com.wangxinenpu.springbootdemo.util.TreeUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import star.bizbase.vo.result.Results;
import star.vo.result.ResultVo;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@RestController
@RequestMapping("sysRoleInnertranFunction")
@Api(tags ="角色-内部事项功能对照管理")
@Slf4j
public class SysRoleInnertranFunctionController  {

    @Autowired
    SysRoleInnertranFunctionFacade sysRoleInnertranFunctionFacade;
    @Autowired
    SysFunctionFacade sysFunctionFacade;
    @Autowired
    LoginComponent loginComonent;
    @Autowired
    SysUserFacade sysUserFacade;
    @Autowired
    SysRoleFacade sysRoleFacade;

    /**
     * 查询菜单树和已授权菜单
     *
     * @param roleType
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/queryTree" })
    public Map queryTree(Model model, String roleType, String roleId) {
        log.info("进入方法");
        ResultVo<Map<String, JSONArray>> result = Results.newResultVo();
        Map<String, Object> map = new ConcurrentHashMap<>();
        if(StringUtils.isEmpty(roleType)|| StringUtils.isEmpty(roleId)){
            result.setCode("-1");
            result.setSuccess(false);
            result.setResultDes("参数缺失");
            map.put("result", result);
            return map;
        }
        Map<String,JSONArray> mapArray = new HashMap<>();
        List<SysFunctionDTO> functionList = new ArrayList<SysFunctionDTO>();
        if(roleType.equals("4") || roleType.equals("3")) {//查询业务操作员可分配哪些系统的权限
            HashMap<String, Object> searchMap = new HashMap<>();
            searchMap.put("location", "/sys/function/queryTree");
            List<SysFunctionDTO> funList = sysFunctionFacade.getListByWhere(searchMap);
            //从缓存获取用户信息
            Long userId = loginComonent.getLoginUserId();
            log.info(userId+"");
            String userType = sysUserFacade.getByPrimaryKey(userId).getUserType();
            //admin账户给机构管理员分配权限
            if("1".equals(userType) && roleType.equals("3")) {
                for (SysFunctionDTO f : funList) {
                    functionList = sysFunctionFacade.findByFunTypeList(f.getSystemType());
                    JSONArray jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(functionList)), "id",
                            "parentId", "children");
                    mapArray.put(f.getSystemType(), jsonArray);
                }
            }else {
                List<SysRoleDTO> roleList = sysRoleFacade.queryRoleByUserId(String.valueOf(userId));
                String rId = null;
                List<String> roleIds = new ArrayList<>();
                if (roleList.size() > 0) {
                    rId = roleList.get(0).getId();
                    for(SysRoleDTO sysRoleDTO : roleList){
                        roleIds.add(sysRoleDTO.getId());
                    }
                }
                //机构管理员用户给业务操作员分配权限
                if ("3".equals(userType)) {
                    List<SysFunctionDTO> list =  sysFunctionFacade.queryFunListByRoleIdForInnerMenu(roleIds);
                    if (list.size() > 0) {//只展示已授权的系统
                        Map<String, List<SysFunctionDTO>> stringListMap=list.stream().collect(Collectors.groupingBy(SysFunctionDTO::getFunType, Collectors.toList()));
                        Set<String> functypes=stringListMap.keySet();
                        for (String type:functypes) {
                            List<SysFunctionDTO> subList=stringListMap.get(type);
                            JSONArray jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(subList)), "id",
                                    "parentId", "children");
                            mapArray.put(type, jsonArray);
                        }
                    }
                } else {
                    for (SysFunctionDTO f : funList) {
                        searchMap.clear();
                        searchMap.put("roleId", rId);
                        searchMap.put("functionId", f.getId());
                        List<SysRoleFunctionDTO> roleFunList = sysFunctionFacade.getRoleFunListByWhere(searchMap);
                        if (roleFunList.size() > 0) {//只展示已授权的系统
                            functionList = sysFunctionFacade.findByFunTypeList(f.getSystemType());
                            JSONArray jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(functionList)), "id",
                                    "parentId", "children");
                            mapArray.put(f.getSystemType(), jsonArray);
                        }
                    }
                }
            }
        } else
        {
            functionList = sysFunctionFacade.findTreesByRoleType(roleType);
            JSONArray jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(functionList)), "id",
                    "parentId", "children");
            mapArray.put("1", jsonArray);

        }
        //查询角色拥有的功能列表
        List<SysRoleFunctionDTO> rfList = sysFunctionFacade.findByRoleId(roleId);
        result.setCode("0");
        result.setSuccess(true);
        result.setResult(mapArray);
        map.put("rfList",rfList);
        map.put("result", result);
//        log.info(map+"");
        return map;
    }


}
