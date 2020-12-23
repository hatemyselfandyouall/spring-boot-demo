package com.wangxinenpu.springbootdemo.controller.selfmachines;


import com.wangxinenpu.springbootdemo.dataobject.dto.sitematters.DataListResultDto;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteBusinessType;
import com.wangxinenpu.springbootdemo.service.facade.SiteBusinessTypeFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import star.vo.result.ResultVo;

import java.util.List;


@RestController
@RequestMapping("siteBusinessType")
@Api(tags ="业务类型管理")
@Slf4j
public class SiteBusinessTypeController {

    @Autowired
    private SiteBusinessTypeFacade siteBusinessTypeFacade;

    @ResponseBody
    @ApiOperation(value = "查询业务类型列表")
    @RequestMapping(value = "/getSiteBusinessTypeList",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public ResultVo getSiteBusinessTypeList(){
        ResultVo resultVo=new ResultVo();
        DataListResultDto<SiteBusinessType> dataListResultDto = new DataListResultDto<>();
        try {
            List<SiteBusinessType> siteBusinessTypes = siteBusinessTypeFacade.getSiteBusinessTypeList(null);
            if(siteBusinessTypes!=null){
                dataListResultDto.setTotalCount(siteBusinessTypes.size());
                dataListResultDto.setDataList(siteBusinessTypes);
            }
            resultVo.setCode("00");
            resultVo.setSuccess(true);
            resultVo.setResultDes("查询成功");
            resultVo.setResult(dataListResultDto);
        }catch (Exception e){
            resultVo.setCode("400");
            resultVo.setSuccess(false);
            resultVo.setResultDes("查询图标类型列表异常，原因为:"+e);
            log.error("查询图标类型列表异常",e);
        }
        return resultVo;
    }
}
