package com.wangxinenpu.springbootdemo.controller.selfmachines;

import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteArea;
import com.wangxinenpu.springbootdemo.dataobject.vo.DataListResultDto;
import com.wangxinenpu.springbootdemo.dataobject.vo.SiteArea.SiteAreaDeleteVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SiteArea.SiteAreaDetailVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SiteArea.SiteAreaListVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SiteArea.SiteAreaSaveVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.TreeVO;
import com.wangxinenpu.springbootdemo.service.facade.SiteAreaFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import star.vo.result.ResultVo;


@RestController
@RequestMapping("siteArea")
@Api(tags ="管理")
@Slf4j
public class SiteAreaController{

    @Autowired
    SiteAreaFacade siteAreaFacade;
    @ResponseBody
    @ApiOperation(value = "列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<SiteArea> getSiteAreaList(@RequestBody SiteAreaListVO SiteAreaListVO){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<SiteArea> SiteAreaList=siteAreaFacade.getSiteAreaList(SiteAreaListVO);
            if(SiteAreaList!=null){
                DataListResultDto<SiteArea> dataListResultDto=new DataListResultDto<>(SiteAreaList.getList(),(int)SiteAreaList.getTotal());
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

    @ResponseBody
    @ApiOperation(value = "树列表")
    @RequestMapping(value = "/tree",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<TreeVO> getSiteAreaTree(@RequestBody SiteAreaListVO SiteAreaListVO){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<TreeVO> SiteAreaList=siteAreaFacade.getSiteAreaTree(SiteAreaListVO);
            if(SiteAreaList!=null){
                DataListResultDto<TreeVO> dataListResultDto=new DataListResultDto<>(SiteAreaList.getList(),(int)SiteAreaList.getTotal());
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

//    @ResponseBody
//    @ApiOperation(value = "权限配置")
//    @RequestMapping(value = "/permissionConfiguration",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<TreeVO> permissionConfiguration(@RequestBody SiteAreaListVO SiteAreaListVO){
//        ResultVo resultVo=new ResultVo();
//        try {
//            PageInfo<TreeVO> SiteAreaList=siteAreaFacade.getSiteAreaTree(SiteAreaListVO);
//            if(SiteAreaList!=null){
//                DataListResultDto<TreeVO> dataListResultDto=new DataListResultDto<>(SiteAreaList.getList(),(int)SiteAreaList.getTotal());
//                resultVo.setResult(dataListResultDto);
//                resultVo.setSuccess(true);
//            }else {
//                resultVo.setResultDes("分页数据缺失");
//            }
//        }catch (Exception e){
//            resultVo.setResultDes("获取列表异常");
//            log.error("获取列表异常",e);
//        }
//        return resultVo;
//    }


    @ResponseBody
    @ApiOperation(value = "详情")
    @RequestMapping(value = "/detail",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<SiteArea> getSiteAreaDetail(@RequestBody SiteAreaDetailVO SiteAreaDetailVO){
        ResultVo resultVo=new ResultVo();
        try {
        SiteArea SiteArea=siteAreaFacade.getSiteAreaDetail(SiteAreaDetailVO);
        if(SiteArea!=null){
            resultVo.setResult(SiteArea);
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

    @ResponseBody
    @ApiOperation(value = "网报区域保存")
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<SiteArea> saveSiteArea(@RequestBody SiteAreaSaveVO SiteAreaSaveVO){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = siteAreaFacade.saveSiteArea(SiteAreaSaveVO);
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

    @ResponseBody
    @ApiOperation(value = "网报区域删除")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public ResultVo<SiteArea> deleteSiteArea(@RequestBody SiteAreaDeleteVO SiteAreaDeleteVO){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = siteAreaFacade.deleteSiteArea(SiteAreaDeleteVO);
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
