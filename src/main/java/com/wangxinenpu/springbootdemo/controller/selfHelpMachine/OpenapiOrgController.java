package com.wangxinenpu.springbootdemo.controller.selfHelpMachine;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.config.component.LoginComponent;
import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiOrg;
import com.wangxinenpu.springbootdemo.dataobject.vo.DataListResultDto;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiApp.ResetAppSecretVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiOrg.*;
import com.wangxinenpu.springbootdemo.service.facade.OpenapiOrgFacade;
import com.wangxinenpu.springbootdemo.util.ExcelHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import star.fw.web.util.ServletAttributes;
import star.vo.result.ResultVo;

import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("openapiOrg")
@Api(tags ="开放平台-机构证书管理")
@Slf4j
public class OpenapiOrgController  {

    @Autowired
    OpenapiOrgFacade openapiOrgFacade;
    @Autowired
    LoginComponent loginComponent;


    @ApiOperation(value = "证书列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrgShowVO> getOpenapiOrgList(@RequestBody OpenapiOrgListVO openapiOrgListVO){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<OpenapiOrgShowVO> openapiOrgList=openapiOrgFacade.getOpenapiOrgList(openapiOrgListVO, null);
            if(openapiOrgList!=null){
                DataListResultDto<OpenapiOrgShowVO> dataListResultDto=new DataListResultDto<>(openapiOrgList.getList(),(int)openapiOrgList.getTotal());
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

    @ApiOperation(value = "机构证书列表导出")
    @RequestMapping(value = "/exportList",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public ResultVo exportList(@RequestParam("openapiOrgListVO") String openapiOrgListVO, HttpServletResponse response){
        ResultVo resultVo=new ResultVo();
        try {
            String tempString= new String(Base64.decodeBase64(openapiOrgListVO.getBytes()),"UTF-8");
            tempString= URLDecoder.decode(tempString);
            OpenapiOrgListVO orgListVO= JSONObject.parseObject(tempString,OpenapiOrgListVO.class);
            PageInfo<OpenapiOrgShowVO> openapiOrgList=openapiOrgFacade.getOpenapiOrgList(orgListVO, null);
            if(openapiOrgList!=null){
                List<List<String>> data=new ArrayList<>();
                data.add(Arrays.asList("证书编号","机构名称","所属区域","限制数量","自助机数量","有效日期","更新时间","更新用户"));
                if (!CollectionUtils.isEmpty(openapiOrgList.getList())) {
                    List<List<String>> dataDetails = openapiOrgList.getList().stream().map(i -> {
                        List<String> detail= Arrays.asList(i.getCertificateCode(),i.getOrgName(),i.getAreaName(),i.getLimitCount()+"",i.getMachineCount()+""
                                ,i.getAccessTimeStart()+"~"+i.getAccessTimeFinal(),i.getModifyTime()+"",i.getModifyName());
                        return detail;
                    }).collect(Collectors.toList());
                    data.addAll(dataDetails);
                }
                ExcelHelper.exportExcel(response,"orgList","导出数据",data);
            }else {

                resultVo.setResultDes("分页数据缺失");
            }
        }catch (Exception e){
            resultVo.setResultDes("获取自助机列表异常");
            log.error("获取自助机列表异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "机构证书详情")
    @RequestMapping(value = "/detail",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrg> getOpenapiOrgDetail(@RequestBody OpenapiOrgDetailVO openapiOrgDetailVO){
        ResultVo resultVo=new ResultVo();
        try {
        OpenapiOrg openapiOrg=openapiOrgFacade.getOpenapiOrgDetail(openapiOrgDetailVO);
        if(openapiOrg!=null){
            resultVo.setResult(openapiOrg);
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

    @ApiOperation(value = "机构证书保存")
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrg> saveOpenapiOrg(@RequestBody OpenapiOrgSaveVO openapiOrgSaveVO){
        ResultVo resultVo=new ResultVo();
        try {
            Long userId=loginComponent.getLoginUserId();
            String userName=loginComponent.getLoginUserName();
            Integer flag = openapiOrgFacade.saveOpenapiOrg(openapiOrgSaveVO,userId,userName);
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

    @ApiOperation(value = "机构证书删除")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrg> deleteOpenapiOrg(@RequestBody OpenapiOrgDeleteVO openapiOrgDeleteVO){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = openapiOrgFacade.deleteOpenapiOrg(openapiOrgDeleteVO);
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


    @ApiOperation(value = "机构证书审核")
    @RequestMapping(value = "/audit",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrg> auditOpenapiOrg(@RequestBody OpenapiOrgAuditVO openapiOrgAuditVO){
        ResultVo resultVo=new ResultVo();
        try {
            String auditName=loginComponent.getLoginUserName();
            Integer flag = openapiOrgFacade.auditOpenapiOrg(openapiOrgAuditVO,auditName);
            if (1 == flag) {
                resultVo.setResultDes("审核成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("审核失败");
            }

        }catch (Exception e){
            resultVo.setResultDes("审核异常");
            log.error("审核异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "机构证书状态回退")
    @RequestMapping(value = "/auditBack",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrg> auditBackOpenapiOrg(@RequestBody OpenapiOrgAuditVO openapiOrgAuditVO){
        ResultVo resultVo=new ResultVo();
        try {
            String auditName=loginComponent.getLoginUserName();
            Integer flag = openapiOrgFacade.auditBackOpenapiOrg(openapiOrgAuditVO,auditName);
            if (1 == flag) {
                resultVo.setResultDes("状态回退成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("状态回退失败");
            }
             
        }catch (Exception e){
            resultVo.setResultDes("状态回退异常");
            log.error("状态回退异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "开放平台机构重设Appsecret")
    @RequestMapping(value = "/resetAppSecret",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrg> resetAppSecret(@RequestBody ResetAppSecretVO resetAppSecretVO){
        ResultVo resultVo=new ResultVo();
        try {
            OpenapiOrg openapiApp = openapiOrgFacade.resetAppSecret(resetAppSecretVO);
            if (openapiApp != null) {
                resultVo.setResultDes("开放平台应用重设Appsecret成功");
                resultVo.setResult(openapiApp);
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("开放平台应用重设Appsecret失败");
            }
             
        }catch (Exception e){
            resultVo.setResultDes("开放平台应用重设Appsecret异常");
            log.error("开放平台应用重设Appsecret异常",e);
        }
        return resultVo;
    }


    @ApiOperation(value = "机构证书审核保存")
    @RequestMapping(value = "/saveCheckConfig",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiOrg> saveCheckConfig(@RequestBody OpenapiOrgSaveVO openapiOrgSaveVO){
        ResultVo resultVo=new ResultVo();
        try {
            Long userId=loginComponent.getLoginUserId();
            String userName=loginComponent.getLoginUserName();
            Integer flag = openapiOrgFacade.saveCheckConfigs(openapiOrgSaveVO,userId,userName);
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
}
