package com.wangxinenpu.springbootdemo.controller.user;


import com.wangxinenpu.springbootdemo.config.aop.OperLog;
import com.wangxinenpu.springbootdemo.dataobject.po.SysOrgDepartment;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysOrgDepartment.*;
import com.wangxinenpu.springbootdemo.service.facade.SysOrgDepartmentFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import star.vo.result.ResultVo;

import java.util.List;


@RestController
@RequestMapping("sysOrgDepartment")
@Api(tags ="科室管理")
@Slf4j
public class SysOrgDepartmentController  {

    @Autowired
    SysOrgDepartmentFacade sysOrgDepartmentFacade;

    @ApiOperation(value = "列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<SysOrgDepartment> getSysOrgDepartmentList(@RequestBody SysOrgDepartmentListVO sysOrgDepartmentListVO){
        ResultVo resultVo=new ResultVo();
        try {
            List<SysOrgDepartment> sysOrgDepartmentList=sysOrgDepartmentFacade.getSysOrgDepartmentList(sysOrgDepartmentListVO);
            if(sysOrgDepartmentList!=null){
                // DataListResultDto<SysOrgDepartment> dataListResultDto=new DataListResultDto<>(sysOrgDepartmentList.getList(),(int)sysOrgDepartmentList.getTotal());
                resultVo.setResult(sysOrgDepartmentList);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("分页数据缺失");
            }
        }catch (Exception e){
            resultVo.setResultDes("获取列表异常");
            log.error("获取列表异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "/detail",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<SysOrgDepartment> getSysOrgDepartmentDetail(@RequestBody SysOrgDepartmentDetailVO sysOrgDepartmentDetailVO){
        ResultVo resultVo=new ResultVo();
        try {
            SysOrgDepartment sysOrgDepartment=sysOrgDepartmentFacade.getSysOrgDepartmentDetail(sysOrgDepartmentDetailVO);
            if(sysOrgDepartment!=null){
                resultVo.setResult(sysOrgDepartment);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("获取详情失败");
            }
        } catch (Exception e){
            resultVo.setResultDes("获取详情异常");
            log.error("获取详情异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "保存")
    @OperLog(systemName="后管系统")
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<SysOrgDepartment> saveSysOrgDepartment(@RequestBody SysOrgDepartmentSaveVO sysOrgDepartmentSaveVO){
        ResultVo resultVo=new ResultVo();
        try {
            resultVo = sysOrgDepartmentFacade.saveSysOrgDepartment(sysOrgDepartmentSaveVO);
        }catch (Exception e){
            resultVo.setResultDes("接口保存异常");
            log.error("保存异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "删除")
    @OperLog(systemName="后管系统")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public ResultVo<SysOrgDepartment> deleteSysOrgDepartment(@RequestBody SysOrgDepartmentDeleteVO sysOrgDepartmentDeleteVO){
        ResultVo resultVo=new ResultVo();
        try {
            resultVo = sysOrgDepartmentFacade.deleteSysOrgDepartment(sysOrgDepartmentDeleteVO);
        }catch (Exception e){
            resultVo.setResultDes("删除异常");
            log.error("删除异常",e);
        }
        return resultVo;
    }


}
