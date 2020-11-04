package com.wangxinenpu.springbootdemo.controller.sys;


import com.wangxinenpu.springbootdemo.config.component.LoginComponent;
import com.wangxinenpu.springbootdemo.dataobject.vo.*;
import com.wangxinenpu.springbootdemo.service.facade.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import star.bizbase.exception.BizRuleException;
import star.vo.result.ResultVo;

import java.util.List;

@RestController
@RequestMapping("sysTranScopeManage")

@Api(tags ="事项生效范围管理")
@Slf4j
public class SysTranScopeManageContoller {

    @Autowired
    private SysFunctionFacade sysFunctionFacade;
    @Autowired
    private LoginComponent loginComponent;

    @ResponseBody
    @ApiOperation(value = "事项列表")
    @RequestMapping(value = "/tranList",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo tranList(){
        ResultVo resultVo=new ResultVo();
        try {
            List<SysFunctionDTO> sysFunctionDTOPageInfo = sysFunctionFacade.tranList();
            resultVo.setSuccess(true);
            resultVo.setResult(sysFunctionDTOPageInfo);
            resultVo.setCode("200");
        }catch (Exception e){
            log.error("获取事项列表异常",e);
        }
        return resultVo;
    }

    /**
     * 根据各机构管理员情况，获取权限树
     *
     */
    @ResponseBody
    @RequestMapping(value = "/sysFunctionScopeTreeForOrgs",method = RequestMethod.GET)
    public ResultVo<PermissTreeByFunctionIdVO> sysFunctionTreeForOrgs(@RequestParam(value = "functionIds",required = false) String functionIds, @RequestParam(value = "belongRoleId",required = false) String belongRoleId) throws BizRuleException {
        ResultVo<PermissTreeByFunctionIdVO>  resultVo = new ResultVo();
        try {
            Long userId=loginComponent.getLoginUserId();
            PermissTreeByFunctionIdVO permissTreeByFunctionIdVO=  sysFunctionFacade.sysFunctionTsysFunctionScopeTreeForOrgseeForOrgs(userId,functionIds,belongRoleId);
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


    /**
     * 保存权限
     *
     */
    @ResponseBody
    @RequestMapping(value = "/saveSysFunctionScopeForOrgs",method = RequestMethod.POST)
    public ResultVo<PermissTreeByFunctionIdVO> sysFunctionTreeForOrgs(@RequestBody SaveSysFunctionScopeForOrgsSaveVO saveSysFunctionScopeForOrgsSaveVO) throws BizRuleException {
        ResultVo<PermissTreeByFunctionIdVO>  resultVo = new ResultVo();
        try {
            Integer flag=  sysFunctionFacade.sysFunctionTreeForOrgs(saveSysFunctionScopeForOrgsSaveVO);
            if (flag==1){
                resultVo.setSuccess(true);
                resultVo.setResultDes("保存权限成功");
            }else {
                resultVo.setResultDes("保存权限失败");
            }
        } catch (Exception e) {
            resultVo.setResultDes("保存权限异常");
            log.error("保存权限异常",e);
        }
        return resultVo;
    }

    /**
     * 保存权限
     *
     */
    @ResponseBody
    @ApiOperation(value = "权限批量设置")
    @RequestMapping(value = "/batchSaveSysFunctionScopeForOrgs",method = RequestMethod.POST)
    public ResultVo<PermissTreeByFunctionIdVO> batchSaveSysFunctionScopeForOrgs(@RequestBody BatchSaveSysFunctionScopeForOrgsSaveVO saveSysFunctionScopeForOrgsSaveVO) throws BizRuleException {
        ResultVo<PermissTreeByFunctionIdVO>  resultVo = new ResultVo();
        try {
            Integer flag=  sysFunctionFacade.batchSaveSysFunctionScopeForOrgs(saveSysFunctionScopeForOrgsSaveVO);
            if (flag==1){
                resultVo.setSuccess(true);
                resultVo.setResultDes("保存权限成功");
            }else {
                resultVo.setResultDes("保存权限失败");
            }
        } catch (Exception e) {
            resultVo.setResultDes("保存权限异常");
            log.error("保存权限异常",e);
        }
        return resultVo;
    }
}
