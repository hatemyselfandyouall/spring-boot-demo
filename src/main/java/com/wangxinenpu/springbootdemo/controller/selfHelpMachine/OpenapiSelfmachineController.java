package com.wangxinenpu.springbootdemo.controller.selfHelpMachine;

import com.alibaba.fastjson.JSONObject;

import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.config.component.LoginComponent;
import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiOrg;
import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiSelfmachine;
import com.wangxinenpu.springbootdemo.dataobject.vo.DataListResultDto;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiOrg.OpenapiOrgDetailVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachine.OpenapiSelfmachineDeleteVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachine.OpenapiSelfmachineListVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachine.OpenapiSelfmachineShowVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestSaveVO;
import com.wangxinenpu.springbootdemo.service.facade.OpenapiOrgFacade;
import com.wangxinenpu.springbootdemo.service.facade.OpenapiSelfmachineFacade;
import com.wangxinenpu.springbootdemo.util.ExcelHelper;
import com.wangxinenpu.springbootdemo.util.ExcelReader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import star.fw.web.util.ServletAttributes;
import star.vo.result.ResultVo;

import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("openapiSelfmachine")
@Api(tags ="自助机管理")
@Slf4j
public class OpenapiSelfmachineController  {

    @Autowired
    OpenapiSelfmachineFacade openapiSelfmachineFacade;
    @Autowired
    LoginComponent loginComponent;
    @Autowired
    OpenapiOrgFacade openapiOrgFacade;

    @ApiOperation(value = "自助机列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiSelfmachineShowVO> getOpenapiSelfmachineList(@RequestBody OpenapiSelfmachineListVO openapiSelfmachineListVO){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<OpenapiSelfmachineShowVO> openapiSelfmachineList=openapiSelfmachineFacade.getOpenapiSelfmachineList(openapiSelfmachineListVO, null);
            if(openapiSelfmachineList!=null){
                DataListResultDto<OpenapiSelfmachineShowVO> dataListResultDto=new DataListResultDto<>(openapiSelfmachineList.getList(),(int)openapiSelfmachineList.getTotal());
                resultVo.setResult(dataListResultDto);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("分页数据缺失");
            }

        }catch (Exception e){
            resultVo.setResultDes("获取自助机列表异常");
            log.error("获取自助机列表异常",e);
        }
        return resultVo;
    }


    @ApiOperation(value = "自助机列表导出")
    @RequestMapping(value = "/exportList",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public void exportList(@RequestParam("openapiOrgListVO") String openapiOrgListVO, HttpServletResponse response){
        ResultVo resultVo=new ResultVo();
        try {
            String tempString= new String(Base64.decodeBase64(openapiOrgListVO.getBytes()),"UTF-8");
            tempString= URLDecoder.decode(tempString);
            OpenapiSelfmachineListVO openapiSelfmachineListVO= JSONObject.parseObject(tempString,OpenapiSelfmachineListVO.class);
            PageInfo<OpenapiSelfmachineShowVO> openapiSelfmachineList=openapiSelfmachineFacade.getOpenapiSelfmachineList(openapiSelfmachineListVO, null);
            if(openapiSelfmachineList!=null){
                DataListResultDto<OpenapiSelfmachineShowVO> dataListResultDto=new DataListResultDto<>(openapiSelfmachineList.getList(),(int)openapiSelfmachineList.getTotal());
                resultVo.setResult(dataListResultDto);
                resultVo.setSuccess(true);
                List<List<String>> data=new ArrayList<>();
                data.add(Arrays.asList("自助机编码","ip","MAC地址","操作系统","所属机构","自助机型号","客户端版本","qt版本","http版本","安全标识","创建时间"));
                if (!CollectionUtils.isEmpty(dataListResultDto.getDataList())) {
                    List<List<String>> dataDetails = dataListResultDto.getDataList().stream().map(i -> {
                        List<String> detail= Arrays.asList(i.getMachineCode(),i.getIp(),i.getMacAddress(),i.getSystemCode(),i.getOrgName()
                                ,i.getMachineType(),i.getClientVersion(),i.getQtVersion(),i.getHttpVersion(),i.getStatu().name(),i.getCreateTime().toString());
                        return detail;
                    }).collect(Collectors.toList());
                    data.addAll(dataDetails);
                }
                ExcelHelper.exportExcel(response,"selfMachine","导出数据",data);
            }else {
                resultVo.setResultDes("分页数据缺失");
            }
        }catch (Exception e){
            resultVo.setResultDes("获取自助机列表异常");
            log.error("获取自助机列表异常",e);
        }
    }

//    @ApiOperation(value = "自助机详情")
//    @RequestMapping(value = "/detail",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<OpenapiSelfmachine> getOpenapiSelfmachineDetail(@RequestBody OpenapiSelfmachineDetailVO openapiSelfmachineDetailVO){
//        ResultVo resultVo=new ResultVo();
//        try {
//        OpenapiSelfmachine openapiSelfmachine=openapiSelfmachineFacade.getOpenapiSelfmachineDetail(openapiSelfmachineDetailVO);
//        if(openapiSelfmachine!=null){
//            resultVo.setResult(openapiSelfmachine);
//            resultVo.setSuccess(true);
//        }else {
//            resultVo.setResultDes("获取详情失败");
//        }
//        } catch (Exception e){
//        resultVo.setResultDes("获取自助机详情异常");
//        log.error("获取自助机详情异常",e);
//    }
//        return resultVo;
//    }
//
//    @ApiOperation(value = "自助机保存")
//    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<OpenapiSelfmachine> saveOpenapiSelfmachine(@RequestBody OpenapiSelfmachineSaveVO openapiSelfmachineSaveVO){
//        ResultVo resultVo=new ResultVo();
//        try {
//            Integer flag = openapiSelfmachineFacade.saveOpenapiSelfmachine(openapiSelfmachineSaveVO);
//            if (1 == flag) {
//                resultVo.setResultDes("自助机保存成功");
//                resultVo.setSuccess(true);
//            } else {
//                resultVo.setResultDes("自助机保存失败");
//            }
//        }catch (Exception e){
//                resultVo.setResultDes("接口保存异常");
//                log.error("自助机保存异常",e);
//            }
//        return resultVo;
//    }

    @ApiOperation(value = "自助机设置状态")
    @RequestMapping(value = "/setStatu",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiSelfmachine> setStatu(@RequestBody OpenapiSelfmachineDeleteVO openapiSelfmachineDeleteVO){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = openapiSelfmachineFacade.setStatu(openapiSelfmachineDeleteVO);
            if (flag!=null&&flag>0) {
                resultVo.setResultDes("自助机设置状态成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("自助机设置状态失败");
            }

        }catch (Exception e){
            resultVo.setResultDes("自助机设置状态异常");
            log.error("自助机设置状态异常",e);
        }
        return resultVo;
    }


    @ApiOperation(value = "excel导入自助机")
    @RequestMapping(value = "/importExcel",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<OpenapiSelfmachineShowVO> getOpenapiSelfmachineList(@RequestPart MultipartFile file, @RequestParam("id") Long orgId) {
        ResultVo resultVo = new ResultVo();
        try{
            OpenapiOrg openapiOrg=openapiOrgFacade.getOpenapiOrgDetail(new OpenapiOrgDetailVO().setId(orgId));
            List<OpenapiSelfmachineRequestSaveVO> openapiSelfmachineRequestSaveVOS= ExcelReader.getSelfMachinesByExcel(file,openapiOrg);
            openapiOrgFacade.importExcels(openapiSelfmachineRequestSaveVOS,openapiOrg);
            resultVo.setSuccess(true);
            resultVo.setResultDes("成功");
        } catch (Exception e) {
            resultVo.setResultDes("excel导入自助机异常");
            log.error("excel导入自助机异常", e);
        }
        return resultVo;
    }

    @Scheduled(cron = "0 0 0 */1 * ?")
    public void clearOpenStatus(){
        try {
            openapiSelfmachineFacade.clearOpenStatu();
        }catch (Exception e){
            log.error("自助机状态清空定时器异常",e);
        }
    }
}
