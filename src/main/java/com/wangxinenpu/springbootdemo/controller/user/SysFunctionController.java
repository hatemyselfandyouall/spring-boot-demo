package com.wangxinenpu.springbootdemo.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.wangxinenpu.springbootdemo.config.aop.OperLog;
import com.wangxinenpu.springbootdemo.config.component.LoginComponent;
import com.wangxinenpu.springbootdemo.dataobject.po.SysMenuChangeLog;
import com.wangxinenpu.springbootdemo.dataobject.vo.*;

import com.wangxinenpu.springbootdemo.service.facade.*;
import com.wangxinenpu.springbootdemo.util.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import star.bizbase.exception.BizRuleException;
import star.bizbase.vo.result.Results;
import star.util.StringUtil;
import star.vo.result.ResultVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 菜单管理
 *
 * @author Administrator
 */
@Controller
@RequestMapping("sys/function")
@Api(tags = "菜单管理")
@Slf4j
public class SysFunctionController  {

    @Autowired
    private SysFunctionFacade sysFunctionFacade;
    @Autowired
    private LoginComponent loginComonent;
    @Autowired
    private SysUserFacade sysUserFacade;

    @Autowired
    private SystemManageFacade systemManageFacade;


    @Autowired
    private SysUserEmpowerFacade sysUserEmpowerFacade;


    /**
     * 菜单列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/queryTree"})
    public ResultVo<JSONArray> queryTree(String funType) {
        ResultVo<JSONArray> result = Results.newResultVo();
        List<SysFunctionDTO> functionList = sysFunctionFacade.findByFunTypeList(funType);
        JSONArray jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(functionList)), "id",
                "parentId", "children");
        result.setCode("0");
        result.setSuccess(true);
        result.setResult(jsonArray);

        return result;

    }

    /**
     * 首页菜单树
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/queryFunctionList"})
    public ResultVo<JSONArray> queryFunctionList(String funType) {
        ResultVo<JSONArray> result = Results.newResultVo();
        //从缓存获取用户信息
        Long userId = loginComonent.getLoginUserId();
        SysUserDTO sysUser = sysUserFacade.getCacheByPrimaryKey(userId);
        List<SysFunctionDTO> functionList = new ArrayList<SysFunctionDTO>();
        if (null != funType) {
            functionList = sysFunctionFacade.findByFunTypeAndUserList(funType, sysUser);
        }
        JSONArray jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(functionList)), "id",
                "parentId", "children");
        result.setCode("0");
        result.setSuccess(true);
        result.setResult(jsonArray);
        return result;

    }

    /**
     * 保存菜单
     *
     * @param sysFunction
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/saveMenu"}, method = RequestMethod.POST)
    @ApiOperation(value = "保存菜单")
    @OperLog(systemName="后管系统")
    public ResultVo<JSONArray> saveMenu(@RequestBody SysFunctionDTO sysFunction) {
        JSONArray array = new JSONArray();
        List<SystemIconDTO> iconList = sysFunction.getIconList();
        SysMenuChangeLog sysMenuChangeLog=new SysMenuChangeLog();
        if (iconList != null) {
            for (SystemIconDTO systemIconDTO : iconList) {
                boolean status = systemManageFacade.checkName(systemIconDTO.getIconName());
                if (status) systemManageFacade.insert(systemIconDTO);
                array.add(systemIconDTO.getIconName());
            }
            sysFunction.setGroupName(JSONArray.toJSONString(array));
        }

        ResultVo<JSONArray> result = Results.newResultVo();
        result.setSuccess(false);
        Boolean flag = false;
        if ("1".equals(sysFunction.getNodeType()) || "2".equals(sysFunction.getNodeType())) {//父菜单，子节点的节点链接不校验
            flag = false;
        } else {
            flag = sysFunctionFacade.checkLocation(sysFunction.getId(), sysFunction.getLocation(), sysFunction.getFunType());
        }
        try {
            if (flag == false) {
                Map<String, Object> map = new ConcurrentHashMap<>();
                if (null == sysFunction.getId() || "".equals(sysFunction.getId())) {
                    sysFunction.setAuFlag("0");//自动审核
                    sysFunction.setSystemType("0");
                    //从缓存获取用户信息
                    Long userId = loginComonent.getLoginUserId();
                    sysFunction.setCreatePeople(userId);
                    //修改排序
                    Long parentId = sysFunction.getParentId();
                    if (parentId == null) {
                        parentId = 0l;
                    }
                    map.put("funOrder", sysFunction.getFunOrder());
                    map.put("parentId", parentId);
                    map.put("funType", sysFunction.getFunType());
                    map.put("type", "add");
                    sysFunctionFacade.updateFunOrder(map);
                    sysFunctionFacade.addSysFunction(sysFunction);


                    //修改全选半选状态
                    List<SysRoleFunctionDTO> rfList = sysFunctionFacade.findByFunctionId(sysFunction.getParentId());
                    if (rfList.size() > 0) {
                        for (SysRoleFunctionDTO rf : rfList) {
                            if ("0".equals(rf.getSelectState())) {//新加菜单，如果父节点是全选则改为半选
                                rf.setSelectState("1");
                                sysFunctionFacade.updateRoleFunction(rf);
                            }
                        }
                    }
                    //修改所有父节点全选状态
                    SysFunctionDTO fun = sysFunctionFacade.getByPrimaryKey(sysFunction.getParentId());
                    if (null != fun) {
                        if (!"0".equals(fun.getParentId())) {//不是最上级
                            List<SysRoleFunctionDTO> list = sysFunctionFacade.findByFunctionId(fun.getParentId());
                            if (list.size() > 0) {
                                for (SysRoleFunctionDTO rf : list) {
                                    if ("0".equals(rf.getSelectState())) {//新加菜单，如果父节点是全选则改为半选
                                        rf.setSelectState("1");
                                        sysFunctionFacade.updateRoleFunction(rf);
                                    }
                                }
                            }
                        }
                    }


                } else {
                    if (sysFunction.getFunType().equals("1")) {
                        result.setResultDes("此菜单不能修改");
                        return result;
                    }
                    
                    //匹配配置规则，是否与模板一致
//            		boolean isRule  = auditConfigRuleFacade.getAuditConfigRuleYesOrNo(sysFunction.getId(), sysFunction.getIsBus(), sysFunction.getFunLevel(), null, null, null);
//            		log.info("匹配配置规则  isRule={},funId={},IsBus={},funLv={},AuFlag={},isLeading={}",isRule,sysFunction.getId(),sysFunction.getIsBus(),sysFunction.getFunLevel(),null,null);
//            		if(!isRule){
//            			result.setCode("-1");
//                        result.setSuccess(false);
//                        result.setResultDes("与规则配置不一致，请检查");
//                        return result;
//            		}
                    
                    SysFunctionDTO fun = sysFunctionFacade.getByPrimaryKey(sysFunction.getId());
                    if (!fun.getFunOrder().equals(sysFunction.getFunOrder())) {//排序有改变
                        if (fun.getFunOrder() > sysFunction.getFunOrder()) {
                            map.put("type", "updateSmall");//向小改
                        } else {
                            map.put("type", "updateBig");
                        }
                        Long parentId = sysFunction.getParentId();
                        if (parentId == null) {
                            parentId = 0l;
                        }
                        //修改排序
                        map.put("funType", sysFunction.getFunType());
                        map.put("funOrder", sysFunction.getFunOrder());
                        map.put("oldOrder", fun.getFunOrder());
                        map.put("parentId", parentId);
                        sysFunctionFacade.updateFunOrder(map);
                    }
                    sysFunctionFacade.updateSysFunction(sysFunction);
                }
                //如果要设置权限的话
                if(sysFunction.getSysFunctionSetPermissionForOrgsVO()!=null){
                    sysFunctionFacade.sysFunctionSetPermissionForOrgs(sysFunction.getSysFunctionSetPermissionForOrgsVO().setFunctionId(sysFunction.getId()));
                }
                sysMenuChangeLog.setEditorId(loginComonent.getLoginUserId()+"").setChuangeJson(JSONObject.toJSONString(sysFunction))
                        .setMatterId(sysFunction.getId());
                result.setCode("0");
                result.setSuccess(true);
                result.setResultDes("保存成功");
            } else {
                result.setResultDes("路径重复");
            }
        } catch (Exception e) {
            result.setResultDes("菜单保存失败，失败原因：" + e.getMessage());
        }
        return result;
    }

    /**
     * 查询详情
     *
     * @param functionId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/nodeClick"})
    public ResultVo<Map<String, Object>> nodeClick(String functionId) {
        ResultVo<Map<String, Object>> result = Results.newResultVo();
        Map<String, Object> map = new ConcurrentHashMap<>();
        result.setSuccess(false);
        if (null != functionId && !"".equals(functionId)) {
            SysFunctionDTO sysFunction = sysFunctionFacade.getByPrimaryKey(Long.parseLong(functionId));
            List<Object> bussArray = new ArrayList<Object>();

            //可修改事项层级的权限
            //从缓存获取用户信息
            Long userId = loginComonent.getLoginUserId();
            SysUserDTO sysUser = sysUserFacade.getCacheByPrimaryKey(userId);
            //管理员和创建人可以修改事项层级
            boolean isOper = true;
//            if (sysUser.getUserType().equals("1")) {
//                isOper = true;
//            } else {
//                if (sysFunction.getCreatePeople().equals(userId)) {
//                    isOper = true;
//                }
//            }
            map.put("isOper", isOper);
            if (sysFunction.getCreatePeople() != null) {
                SysUserDTO opUser = sysUserFacade.getCacheByPrimaryKey(sysFunction.getCreatePeople());
                String createOperName = "";
                if (null != opUser) {
                    createOperName = opUser.getLogonName();
                }
                map.put("createOperName", createOperName);
            }
            SysBusinessTypeDTO sysBusinessTypeDTO = null;
            if (null != sysFunction) {
                if (null != sysFunction.getBusinessId()) {
                    sysBusinessTypeDTO = sysFunctionFacade.findBusinessById(sysFunction.getBusinessId());
                    if (null != sysBusinessTypeDTO) {
                        String number = sysBusinessTypeDTO.getNumber();
                        String a = number.substring(0, 3);
                        char[] c = a.toCharArray();
                        for (int i = 0; i < c.length; i++) {
                            bussArray.add(c[i]);
                        }
                        bussArray.add(sysBusinessTypeDTO.getId());
                        map.put("buss", sysBusinessTypeDTO);
                    }
                }
            }
            map.put("sysFunction", sysFunction);
            map.put("bussArray", bussArray);
            result.setResult(map);
            result.setCode("0");
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 菜单删除
     *
     * @param functionId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/deleteMenu"})
    @ApiOperation(value = "菜单删除")
    @OperLog(systemName="后管系统")
    public ResultVo<SysFunctionDTO> deleteMenu(String functionId) {
        ResultVo<SysFunctionDTO> result = Results.newResultVo();
        result.setSuccess(false);
        if (null != functionId && !"".equals(functionId)) {
            SysFunctionDTO sysFunction = sysFunctionFacade.getByPrimaryKey(Long.parseLong(functionId));
            if (null == sysFunction) {
                result.setResultDes("菜单不存在");
            } else {
                try {
                    if (sysFunction.getFunType().equals("1")) {
                        result.setResultDes("此菜单不能删除");
                        return result;
                    }
                    List<SysFunctionDTO> funList = sysFunctionFacade.getListByParentId(Long.parseLong(functionId));
                    if (!funList.isEmpty()) {
                        result.setResultDes("存在下级菜单，不能删除");
                        return result;
                    }
                    sysFunctionFacade.deleteMenu(sysFunction);
                    result.setResultDes("删除成功");
                    result.setCode("0");
                    result.setSuccess(true);
                } catch (BizRuleException e) {
                    result.setResultDes("删除失败，失败原因：" + e.getMessage());
                }
            }
        }
        return result;
    }

    /**
     * 根据渠道、区域、大类获取业务类型
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/queryBusinessList"})
    public ResultVo<Object> queryBusinessList(String busPar) {
        ResultVo<Object> result = Results.newResultVo();
        List<SysBusinessTypeDTO> list = new ArrayList<SysBusinessTypeDTO>();
        if (StringUtil.isNotEmpty(busPar)) {
            List<SysBusinessTypeDTO> bussList = sysFunctionFacade.queryBusinessList(busPar);
            for (SysBusinessTypeDTO buss : bussList) {
                buss.setNumberName(buss.getNumber() + buss.getName());
                list.add(buss);
            }
        }
        result.setCode("0");
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }


    /**
     * 查询组名
     *该用户没有所属的角色
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/queryGroupName"})
    public ResultVo<Object> queryGroupName() {
        ResultVo<Object> result = Results.newResultVo();
        List<String> groupLists = new ArrayList<>();
        List<SystemIconDTO> systemIconDTOS = systemManageFacade.selectAll();
        if (systemIconDTOS.size() != 0) {
            for (SystemIconDTO buss : systemIconDTOS) {
                groupLists.add(buss.getIconName());
            }
        }
        result.setCode("0");
        result.setSuccess(true);
        result.setResult(groupLists);
        return result;
    }


    /**
     * 菜单拖拽
     *
     * @param id
     * @param parentId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/updateFunNode"})
    @ApiOperation(value = "修改菜单")
    @OperLog(systemName="后管系统")
    public ResultVo<Object> updateFunNode(String id, String parentId) {
        ResultVo<Object> result = Results.newResultVo();
        if (StringUtil.isNotEmpty(id) && StringUtil.isNotEmpty(parentId)) {
            int funOrder = 0;
            List<SysFunctionDTO> funList = sysFunctionFacade.getListByParentId(Long.parseLong(parentId));
            if (funList.size() > 0) {
                funOrder = sysFunctionFacade.findMaxFunOrder(Long.parseLong(parentId));
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            map.put("parentId", parentId);
            map.put("funOrder", funOrder + 1);
            try {
                sysFunctionFacade.updateFunNode(map);

                result.setCode("0");
                result.setSuccess(true);
                result.setResultDes("拖拽成功");
            } catch (BizRuleException e) {
                result.setResultDes("拖拽失败,失败原因：" + e.getMessage());
                result.setSuccess(false);
            }
        }
        return result;
    }

    /**
     * 根据业务组操作日志id获取回退标志
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/queryRbFlagById"})
    public ResultVo<Object> queryRbFlagById(Long functionId) {
        ResultVo<Object> result = Results.newResultVo();
        result.setResult(sysFunctionFacade.queryRbFlagById(functionId));
        return result;
    }

    /**
     * 根据业务组操作日志id获取回退标志(多条)
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/queryRbFlagListById"})
    public ResultVo<Object> queryRbFlagListById(String functionId) {
        ResultVo<Object> result = Results.newResultVo();
        result.setResult(sysFunctionFacade.queryRbFlagResultById(functionId));
        return result;
    }



    /**
     * 设置菜单权限到各机构管理员
     *
     * @param ticket
     */
    @ResponseBody
    @RequestMapping(value = "/sysFunctionSetPermissionForOrgs",method = RequestMethod.POST)
    public ResultVo sysFunctionSetPermissionForOrgs(@RequestBody SysFunctionSetPermissionForOrgsVO sysFunctionSetPermissionForOrgsVO) throws BizRuleException {
        ResultVo resultVo = new ResultVo();
        try {
            Integer flag=  sysFunctionFacade.sysFunctionSetPermissionForOrgs(sysFunctionSetPermissionForOrgsVO);
            if (1==flag){
                resultVo.setSuccess(true);
                resultVo.setResultDes("设置菜单权限到各机构管理员成功");
            }else {
                resultVo.setResultDes("设置菜单权限到各机构管理员失败");
            }
        } catch (Exception e) {
            resultVo.setResultDes("设置菜单权限到各机构管理员异常");
            log.error("设置菜单权限到各机构管理员异常",e);
        }
        resultVo.setSuccess(true);
        return resultVo;
    }

    /**
     * 根据各机构管理员情况，获取权限树
     *
     * @param ticket
     */
    @ResponseBody
    @RequestMapping(value = "/sysFunctionSetPermissionForOrgs",method = RequestMethod.GET)
    public ResultVo<PermissTreeByFunctionIdVO> getPermissTreeByFunctionId(@RequestParam(value = "functionId",required = false) Long functionID) throws BizRuleException {
        ResultVo<PermissTreeByFunctionIdVO>  resultVo = new ResultVo();
        try {
            PermissTreeByFunctionIdVO permissTreeByFunctionIdVO=  sysFunctionFacade.getPermissTreeByFunctionId(functionID);
            if (permissTreeByFunctionIdVO!=null){
                resultVo.setSuccess(true);
                resultVo.setResult(permissTreeByFunctionIdVO);
                resultVo.setResultDes("根据各机构管理员情况，获取权限树成功");
            }else {
                resultVo.setResultDes("根据各机构管理员情况，获取权限树失败");
            }
        } catch (Exception e) {
            resultVo.setResultDes("根据各机构管理员情况，获取权限树异常");
            log.error("根据各机构管理员情况，获取权限树异常",e);
        }
        resultVo.setSuccess(true);
        return resultVo;
    }
}
