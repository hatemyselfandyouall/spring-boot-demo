package com.wangxinenpu.springbootdemo.controller.root.seflMachineManager;

import com.github.pagehelper.PageInfo;

import com.wangxinenpu.springbootdemo.config.component.LoginComponent;
import com.wangxinenpu.springbootdemo.controller.root.BasicController;
import com.wangxinenpu.springbootdemo.dataobject.dto.DataListResultDto;
import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiOrgShortname;
import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiSelfmachineType;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineType.OpenapiSelfmachineTypeDeleteVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineType.OpenapiSelfmachineTypeDetailVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineType.OpenapiSelfmachineTypeListVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineType.OpenapiSelfmachineTypeSaveVO;
import com.wangxinenpu.springbootdemo.service.facade.OpenapiSelfmachineTypeFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import star.fw.web.util.ServletAttributes;
import star.vo.result.ResultVo;


@RestController
@RequestMapping("openapiSelfmachineType")
@Api(tags ="自助机型号管理-用户")
@Slf4j
public class OpenapiSelfmachineTypeController extends BasicController {

    @Autowired
    OpenapiSelfmachineTypeFacade openapiSelfmachineTypeFacade;

    @Autowired
    LoginComponent loginComponent;



    @ApiOperation(value = "列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiSelfmachineType> getOpenapiSelfmachineTypeList(@RequestBody OpenapiSelfmachineTypeListVO openapiSelfmachineTypeListVO){
        ResultVo resultVo=new ResultVo();
        try {
            Long userId=loginComponent.getLoginUserId();
            PageInfo<OpenapiSelfmachineType> openapiSelfmachineTypeList=openapiSelfmachineTypeFacade.getOpenapiSelfmachineTypeList(openapiSelfmachineTypeListVO,userId);
            if(openapiSelfmachineTypeList!=null){
                DataListResultDto<OpenapiSelfmachineType> dataListResultDto=new DataListResultDto<>(openapiSelfmachineTypeList.getList(),(int)openapiSelfmachineTypeList.getTotal());
                resultVo.setResult(dataListResultDto);
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
    public ResultVo<OpenapiSelfmachineType> getOpenapiSelfmachineTypeDetail(@RequestBody OpenapiSelfmachineTypeDetailVO openapiSelfmachineTypeDetailVO){
        ResultVo resultVo=new ResultVo();
        try {
        OpenapiSelfmachineType openapiSelfmachineType=openapiSelfmachineTypeFacade.getOpenapiSelfmachineTypeDetail(openapiSelfmachineTypeDetailVO);
        if(openapiSelfmachineType!=null){
            resultVo.setResult(openapiSelfmachineType);
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
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiSelfmachineType> saveOpenapiSelfmachineType(@RequestBody OpenapiSelfmachineTypeSaveVO openapiSelfmachineTypeSaveVO){
        ResultVo resultVo=new ResultVo();
        try {
            Long userId=loginComponent.getLoginUserId();
            String userName=loginComponent.getLoginUserName();
            Integer flag = openapiSelfmachineTypeFacade.saveOpenapiSelfmachineType(openapiSelfmachineTypeSaveVO,userId,userName);
            if (1 == flag) {
                resultVo.setResultDes("保存成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("保存失败");
            }

        }catch (Exception e){
                resultVo.setResultDes("接口保存异常");
                log.error("保存异常",e);
            }
        return resultVo;
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiSelfmachineType> deleteOpenapiSelfmachineType(@RequestBody OpenapiSelfmachineTypeDeleteVO openapiSelfmachineTypeDeleteVO){
        ResultVo resultVo=new ResultVo();
        try {
            Long userId=loginComponent.getLoginUserId();
            Integer flag = openapiSelfmachineTypeFacade.deleteOpenapiSelfmachineType(openapiSelfmachineTypeDeleteVO,userId);
            if (1 == flag) {
                resultVo.setResultDes("删除成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("删除失败");
            }

        }catch (Exception e){
            resultVo.setResultDes("删除异常");
            log.error("删除异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "确认删除是否可行")
    @RequestMapping(value = "/checkDelete",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrgShortname> checkDelete(@RequestBody OpenapiSelfmachineTypeDeleteVO openapiSelfmachineTypeDeleteVO){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = openapiSelfmachineTypeFacade.checkDelete(openapiSelfmachineTypeDeleteVO);
            if (1 == flag) {
                resultVo.setResult(flag);
                resultVo.setResultDes("可以删除");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResult(flag);
                resultVo.setResultDes("不可删除");
                resultVo.setSuccess(true);
            }

        }catch (Exception e){
            resultVo.setResultDes("确认删除是否可行异常");
            log.error("确认删除是否可行异常",e);
        }
        return resultVo;
    }

}
