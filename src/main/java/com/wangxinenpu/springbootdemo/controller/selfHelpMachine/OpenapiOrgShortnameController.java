package com.wangxinenpu.springbootdemo.controller.selfHelpMachine;

import com.github.pagehelper.PageInfo;

import com.wangxinenpu.springbootdemo.config.component.LoginComponent;
import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiOrgShortname;
import com.wangxinenpu.springbootdemo.dataobject.vo.DataListResultDto;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiOrgShortname.OpenapiOrgShortnameDeleteVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiOrgShortname.OpenapiOrgShortnameDetailVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiOrgShortname.OpenapiOrgShortnameListVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiOrgShortname.OpenapiOrgShortnameSaveVO;
import com.wangxinenpu.springbootdemo.service.facade.*;
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
@RequestMapping("openapiOrgShortname")
@Api(tags ="开放平台机构简称管理")
@Slf4j
public class OpenapiOrgShortnameController  {

    @Autowired
    OpenapiOrgShortnameFacade openapiOrgShortnameFacade;
    @Autowired
    LoginComponent loginComponent;

    @ApiOperation(value = "机构简称列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrgShortname> getOpenapiOrgShortnameList(@RequestBody OpenapiOrgShortnameListVO openapiOrgShortnameListVO){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<OpenapiOrgShortname> openapiOrgShortnameList=openapiOrgShortnameFacade.getOpenapiOrgShortnameList(openapiOrgShortnameListVO);
            if(openapiOrgShortnameList!=null){
                DataListResultDto<OpenapiOrgShortname> dataListResultDto=new DataListResultDto<>(openapiOrgShortnameList.getList(),(int)openapiOrgShortnameList.getTotal());
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

    @ApiOperation(value = "机构简称详情")
    @RequestMapping(value = "/detail",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrgShortname> getOpenapiOrgShortnameDetail(@RequestBody OpenapiOrgShortnameDetailVO openapiOrgShortnameDetailVO){
        ResultVo resultVo=new ResultVo();
        try {
        OpenapiOrgShortname openapiOrgShortname=openapiOrgShortnameFacade.getOpenapiOrgShortnameDetail(openapiOrgShortnameDetailVO);
            resultVo.setResult(openapiOrgShortname);
            resultVo.setSuccess(true);

        } catch (Exception e){
        resultVo.setResultDes("获取详情异常");
        log.error("获取详情异常",e);
    }
        return resultVo;
    }


    @ApiOperation(value = "机构简称保存")
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrgShortname> saveOpenapiOrgShortname(@RequestBody OpenapiOrgShortnameSaveVO openapiOrgShortnameSaveVO){
        ResultVo resultVo=new ResultVo();
        try {
            String userName=loginComponent.getLoginUserName();
            openapiOrgShortnameSaveVO.setModifierName(userName);
            resultVo  = openapiOrgShortnameFacade.saveOpenapiOrgShortname(openapiOrgShortnameSaveVO);

        }catch (Exception e){
                resultVo.setResultDes("接口保存异常");
                log.error("保存异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "确认删除是否可行")
    @RequestMapping(value = "/checkDelete",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrgShortname> checkDelete(@RequestBody OpenapiOrgShortnameDeleteVO openapiOrgShortnameDeleteVO){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = openapiOrgShortnameFacade.checkDelete(openapiOrgShortnameDeleteVO);
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

    @ApiOperation(value = "机构简称删除")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrgShortname> deleteOpenapiOrgShortname(@RequestBody OpenapiOrgShortnameDeleteVO openapiOrgShortnameDeleteVO){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = openapiOrgShortnameFacade.deleteOpenapiOrgShortname(openapiOrgShortnameDeleteVO);
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


}
