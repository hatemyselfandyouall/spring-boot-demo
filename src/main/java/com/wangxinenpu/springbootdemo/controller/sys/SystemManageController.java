package com.wangxinenpu.springbootdemo.controller.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.config.aop.OperLog;
import com.wangxinenpu.springbootdemo.config.component.LoginComponent;
import com.wangxinenpu.springbootdemo.dataobject.vo.*;

import com.wangxinenpu.springbootdemo.dataobject.vo.systemManage.SystemAreaTree;
import com.wangxinenpu.springbootdemo.dataobject.vo.systemManage.SystemInsertVo;
import com.wangxinenpu.springbootdemo.dataobject.vo.systemManage.SystemManageListVo;
import com.wangxinenpu.springbootdemo.dataobject.vo.systemManage.SystemManageQueryVo;
import com.wangxinenpu.springbootdemo.service.facade.*;
import com.wangxinenpu.springbootdemo.util.TreeUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import star.vo.result.ResultVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("manage")
@Controller
@SuppressWarnings("all")
@Slf4j
public class SystemManageController {

    @Autowired
    SystemManageFacade systemManageFacade;
    @Autowired
    SysUserFacade sysUserFacade;
    @Autowired
    SysFunctionFacade sysFunctionFacade;
    @Autowired
    private LoginComponent loginComonent;
    @Autowired
    private SysOrgFacade sysOrgFacade;
    @Autowired
    private SysRoleFacade sysRoleFacade;

    @ResponseBody
    @ApiOperation(value = "系统列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo getSysOperateUserList(@RequestBody SystemManageQueryVo systemManageQueryVo){
        ResultVo resultVo=new ResultVo();
        PageInfo<SystemManageListVo> systemManageListVoPageInfo = systemManageFacade.selectByMap(systemManageQueryVo);
        JSONObject obj = new JSONObject();
        obj.put("list",systemManageListVoPageInfo.getList());
        obj.put("count",systemManageListVoPageInfo.getTotal());
        resultVo.setSuccess(true);
        resultVo.setResult(obj);
        resultVo.setCode("200");
        return resultVo;
    }

//    @ResponseBody
//    @ApiOperation(value = "根据登录人所在机构查询系统")
//    @RequestMapping(value = "/getSystemList",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<SysOperateUser> getSystemList(){
//        ResultVo resultVo=new ResultVo();
//        try {
//            Long userId = loginComonent.getLoginUserId();
//            SysUserDTO sysUserDTO = sysUserFacade.getCacheByPrimaryKey(userId);
//            List<SystemManageListVo> systemList = systemManageFacade.getListByOrgId(sysUserDTO.getOrgId(),null);
//            resultVo.setSuccess(true);
//            resultVo.setResult(systemList);
//            resultVo.setCode("200");
//        } catch (Exception e){
//            resultVo.setResultDes(e.toString());
//        }
//        return resultVo;
//    }
//
//
//    @ResponseBody
//    @ApiOperation(value = "根据系统渠道码和用户id查询菜单权限")
//    @RequestMapping(value = "/getFunctionList",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<SysOperateUser> getFunctionList(@RequestParam("channelCode") String channelCode){
//        ResultVo resultVo=new ResultVo();
//        try {
//            Long userId = loginComonent.getLoginUserId();
//            List<SysFunctionDTO> sysFunctionDTOList = sysFunctionFacade.selectByUserIdAndSystemId(channelCode, userId);
//            JSONArray array = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(sysFunctionDTOList)),"id","parentId","children");
//            resultVo.setSuccess(true);
//            resultVo.setResult(array);
//            resultVo.setCode("200");
//        } catch (Exception e){
//            resultVo.setResultDes(e.toString());
//        }
//        return resultVo;
//    }


    @ResponseBody
    @ApiOperation(value = "系统查询列表")
    @RequestMapping(value = "/select",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<SystemManageListVo> select(@RequestBody SystemManageListVo systemManageListVo){
        ResultVo resultVo=new ResultVo();
        try {
            SystemManageListVo systemManageListVo1 = systemManageFacade.selectById(systemManageListVo.getId());
            resultVo.setSuccess(true);
            JSONObject out = JSONObject.parseObject(JSONObject.toJSONString(systemManageListVo1));
            List<String> arr = new ArrayList<>();
           if (out.containsKey("areaId")){
               String areaId = out.getString("areaId");
               if (areaId.endsWith("00")){
                   if (areaId.endsWith("0000")){
                       arr.add(areaId.substring(0,2)+"0000");
                   }
               }else {
                   arr.add(areaId.substring(0,2)+"0000");
                   arr.add(areaId.substring(0,4)+"00");
               }
               arr.add(areaId);
           }
           out.put("arrList",arr);
            resultVo.setResult(out);
            resultVo.setCode("200");
        } catch (Exception e){
            resultVo.setResultDes(e.toString());
        }
        return resultVo;
    }

//    @ResponseBody
//    @ApiOperation(value = "系统查询列表")
//    @RequestMapping(value = "/user",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<SysOperateUser> code(){
//        ResultVo resultVo=new ResultVo();
//        try {
//            SysUserDTO sysUserDTO = new SysUserDTO();
//            sysUserDTO.setLastLoadIp("111");
//            sysUserDTO.setLastLoadTime(new Date());
//            sysUserDTO.setId(21L);
//            int updatepo = sysUserFacade.updatepo(sysUserDTO);
//            resultVo.setSuccess(true);
//            resultVo.setResult(updatepo);
//            resultVo.setCode("200");
//        } catch (Exception e){
//            resultVo.setResultDes(e.toString());
//        }
//
//        return resultVo;
//    }



//    @ResponseBody
//    @ApiOperation(value = "系统查询列表")
//    @RequestMapping(value = "/area",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<SysOperateUser> area(@RequestBody SystemManageQueryVo systemManageQueryVo){
//        ResultVo resultVo=new ResultVo();
//        try {
//            List<SystemAreaTree> systemAreaTrees = systemManageFacade.selectByTree(systemManageQueryVo.getAreaId().toString());
//            resultVo.setSuccess(true);
//            resultVo.setResult(systemAreaTrees);
//            resultVo.setCode("200");
//        } catch (Exception e){
//            resultVo.setResultDes(e.toString());
//        }
//
//        return resultVo;
//    }
//
//    @ResponseBody
//    @ApiOperation(value = "系统查询列表")
//    @RequestMapping(value = "/TreeToList",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<SysOperateUser> area(@RequestBody JSONArray array){
//        ResultVo resultVo=new ResultVo();
//        try {
//            JSONArray jsonArray = TreeUtil.TreeToList(array, "id", "parentId", "children");
//            resultVo.setSuccess(true);
//            resultVo.setResult(jsonArray);
//            resultVo.setCode("200");
//        } catch (Exception e){
//            resultVo.setResultDes(e.toString());
//        }
//
//        return resultVo;
//    }
//
////
////
//   @ResponseBody
////    @ApiOperation(value = "test")
//    @RequestMapping(value = "/test",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<SysOperateUser> test(@RequestParam String areaId){
//        ResultVo resultVo=new ResultVo();
//        try {
//            List<SysOrgDTOwithSystemTreeVo> sysOrgDTOwithSystemTreeVos = sysOrgFacade.selectByTree(areaId);
//            sysFunctionFacade.selectByGroupName();
//            resultVo.setSuccess(true);
//            resultVo.setResult(sysOrgDTOwithSystemTreeVos);
//            resultVo.setCode("200");
//        } catch (Exception e){
//            resultVo.setResultDes(e.toString());
//        }
//
//        return resultVo;
//    }


    @ResponseBody
    @ApiOperation(value = "系统新增或修改.")
    @OperLog(systemName="后管系统")
    @RequestMapping(value = "/updateAndInsert",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo updateAndInsert(@RequestBody SystemInsertVo systemManageQueryVo){
        ResultVo resultVo=new ResultVo();
//        todo 要加上用户id
//        Long loginUserId = loginComonent.getLoginUserId();
//        systemManageQueryVo.setCreatorId(loginUserId.toString());
        systemManageQueryVo.setChannelCode(UUID.randomUUID().toString());
        int i = systemManageFacade.updateAndInsert(systemManageQueryVo);
        if (i == -2){
            resultVo.setSuccess(false);
            resultVo.setResultDes("系统名称重复");
            resultVo.setCode("400");
        }else {
//            Random random = new Random();
//
//            String loginName = "admin"+random.nextInt(1000);
//            boolean b = sysUserFacade.checkLogonName(loginName, null);
//            while (b){
//                loginName = "admin"+random.nextInt(1000);
//                b = sysUserFacade.checkLogonName(loginName,null);
//            }
//            SysUserDTO sysUserDTO = new SysUserDTO();
//            sysUserDTO.setUserType("3");
//            sysUserDTO.setUserState("1");
//            sysUserDTO.setLogonName(loginName);
//            sysUserDTO.setPasswd(StringUtil.getMD5("000000"));
//            sysUserDTO.setAreaId(330000l);
//            sysUserDTO.setOrgId(271l);
//            sysUserDTO.setPrseno(1l);
//            sysUserDTO.setDisplayName("系统管理员");
//            HashMap map = new HashMap();
//            map.put("roleType","2");
//            log.info("【sysUserDTO】：{}",sysUserDTO);
//            List<SysUserRoleDTO> roleList = new ArrayList<>();
//            try {
//                Long aLong = sysUserFacade.addSysUser(sysUserDTO);
//                List<SysRoleDTO> listByWhere = sysRoleFacade.getListByWhere(map);
//                log.info("【listByWhere】：{},【map】：{}",listByWhere,map);
//                if (listByWhere.size()!=0){
//                    SysUserRoleDTO sysUserRole = new SysUserRoleDTO();
//                    sysUserRole.setRoleId(listByWhere.get(0).getId());
//                    sysUserRole.setOrgId(listByWhere.get(0).getOrgId());
//                    sysUserRole.setUserId(aLong.toString());
//                    roleList.add(sysUserRole);
//                }
//                log.info("【roleList】：{}",roleList);
//                sysUserFacade.insertByBatch(roleList);
//                JSONObject out = new JSONObject();
//                out.put("userId",aLong);
//                out.put("username",loginName);
//                out.put("password","000000");
//                resultVo.setResult(out);
//            } catch (BizRuleException e) {
//                e.printStackTrace();
//            }
            resultVo.setSuccess(true);
            resultVo.setResultDes("新增或修改成功");
            resultVo.setCode("200");
        }
        return resultVo;
    }

    @ResponseBody
    @ApiOperation(value = "系统删除.")
    @OperLog(systemName="后管系统")
    @RequestMapping(value = "/delete",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo updateAndInsert(@RequestBody Map map){
        ResultVo resultVo=new ResultVo();
        systemManageFacade.deleteById((String) map.get("id"));
        resultVo.setSuccess(true);
        resultVo.setResultDes("删除成功");
        resultVo.setCode("200");
        return resultVo;
    }
}
