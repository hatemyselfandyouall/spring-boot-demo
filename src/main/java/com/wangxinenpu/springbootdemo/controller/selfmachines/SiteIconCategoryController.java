package com.wangxinenpu.springbootdemo.controller.selfmachines;

import com.wangxinenpu.springbootdemo.dataobject.dto.DataListResultDto;
import com.wangxinenpu.springbootdemo.dataobject.dto.sitematters.SiteIconCategoryDto;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteIconCategory;
import com.wangxinenpu.springbootdemo.dataobject.vo.SiteBlock.SiteBlockDetailVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SiteMatters.SSMSiteIconCategorySaveVO;
import com.wangxinenpu.springbootdemo.service.facade.SiteIconCategoryFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import star.vo.result.ResultVo;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("siteIconCategory")
@Api(tags ="事项分类")
@Slf4j
public class SiteIconCategoryController{

    @Autowired
    private SiteIconCategoryFacade siteIconCategoryFacade;


    @ResponseBody
    @ApiOperation(value = "查询事项分类列表")
    @RequestMapping(value = "/getSiteIconCategoryList",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public ResultVo getSiteIconCategoryList(@RequestParam(value = "bussType") Integer bussType){
        ResultVo resultVo=new ResultVo();
        DataListResultDto<SiteIconCategoryDto> dataListResultDto = new DataListResultDto<>();
        try {
            List<SiteIconCategoryDto> siteIconCategoryDtos = siteIconCategoryFacade.getSiteIconCategoryList(bussType);
            if(siteIconCategoryDtos!=null){
                dataListResultDto.setTotalCount(siteIconCategoryDtos.size());
                dataListResultDto.setDataList(siteIconCategoryDtos);
            }
            resultVo.setCode("00");
            resultVo.setSuccess(true);
            resultVo.setResultDes("查询成功");
            resultVo.setResult(dataListResultDto);
        }catch (Exception e){
            resultVo.setCode("400");
            resultVo.setSuccess(false);
            resultVo.setResultDes("查询事项分类列表异常，原因为:"+e);
            log.error("查询事项分类列表异常",e);
        }
        return resultVo;
    }

    @ResponseBody
    @ApiOperation(value = "保存或修改事项分类")
    @RequestMapping(value = "/saveSiteIconCategory",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<SiteIconCategory> saveSiteIconCategory(@RequestBody SiteIconCategory siteIconCategory){
        ResultVo resultVo=new ResultVo();
        try {
            SiteIconCategory siteIconCategoryRes = siteIconCategoryFacade.saveSiteIconCategory(siteIconCategory);
            resultVo.setCode("00");
            resultVo.setSuccess(true);
            resultVo.setResultDes("保存或修改事项分类成功");
            resultVo.setResult(siteIconCategoryRes);
        }catch (Exception e){
            resultVo.setCode("400");
            resultVo.setSuccess(false);
            String message = e.getMessage();
            if(message.indexOf("MySQLIntegrityConstraintViolationException")>-1) {
                resultVo.setResultDes("事项分类名称重复");
            }else{
                resultVo.setResultDes("保存或修改事项分类异常，原因为:" + e.getMessage());
            }
            log.error("保存或修改事项分类异常",e);
        }
        return resultVo;
    }

    @ResponseBody
    @ApiOperation(value = "删除事项分类")
    @RequestMapping(value = "/deleteSiteIconCategory",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public ResultVo<SiteBlockDetailVO> deleteSiteIconCategory(@RequestParam(value = "id") Integer id){
        ResultVo resultVo=new ResultVo();
        try {
            int res = siteIconCategoryFacade.deleteSiteIconCategory(id);
            if(res== -1){
            	resultVo.setCode("-1");
    			resultVo.setSuccess(false);
    			resultVo.setResultDes("该分类下存在事项菜单，不能删除");
            }
            else if(res== -2){
            	resultVo.setCode("-1");
    			resultVo.setSuccess(false);
    			resultVo.setResultDes("该分类下存在子类，不能删除");
            }
            else{
            	resultVo.setCode("00");
    			resultVo.setSuccess(true);
    			resultVo.setResultDes("删除成功");
            }
        }catch (Exception e){
            resultVo.setCode("400");
            resultVo.setSuccess(false);
            resultVo.setResultDes("删除事项分类异常，原因为:"+e);
            log.error("删除事项分类异常",e);
        }
        return resultVo;
    }
    
    @ApiOperation(value = "批量保存事项分类")
    @RequestMapping(value = "/batchSaveSiteIconCategory",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<SiteIconCategory> batchSaveSiteIconCategory(@RequestBody SSMSiteIconCategorySaveVO sSMSiteIconCategorySaveVO){
        ResultVo resultVo=new ResultVo();
        try {
        	List<Integer> categoryIds = sSMSiteIconCategorySaveVO.getCategoryIds();
        	for(int id : categoryIds){
        		int res = siteIconCategoryFacade.deleteSiteIconCategory(id);
                if(res== -1){
                	resultVo.setCode("-1");
        			resultVo.setSuccess(false);
        			resultVo.setResultDes("移除的子类中有正在使用的，不能删除");
        			return resultVo;
                }
        	}
        	
        	SSMSiteIconCategorySaveVO scVo = new SSMSiteIconCategorySaveVO();
        	List<SiteIconCategory> scList = new ArrayList<SiteIconCategory>();
        	SiteIconCategory siteIconCategory= sSMSiteIconCategorySaveVO.getSiteIconCategory();
        	List<SiteIconCategory> siteIconCategoryList= sSMSiteIconCategorySaveVO.getSiteIconCategoryList();
            SiteIconCategory siteIconCategoryRes = siteIconCategoryFacade.saveSiteIconCategory(siteIconCategory);
            //先删除父类下所有子类
//            siteIconCategoryFacade.deleteByParentId(siteIconCategoryRes.getId().intValue());
            for(SiteIconCategory ca :siteIconCategoryList){
            	ca.setParentId(siteIconCategoryRes.getId());
            	 SiteIconCategory sc = siteIconCategoryFacade.saveSiteIconCategory(ca);
            	 scList.add(sc);
            }
            scVo.setSiteIconCategory(siteIconCategoryRes);
            scVo.setSiteIconCategoryList(scList);
            resultVo.setCode("00");
            resultVo.setSuccess(true);
            resultVo.setResultDes("批量保存事项分类成功");
            resultVo.setResult(scVo);
        }catch (Exception e){
            resultVo.setCode("400");
            resultVo.setSuccess(false);
            String message = e.getMessage();
            if(message.indexOf("MySQLIntegrityConstraintViolationException")>-1) {
                resultVo.setResultDes("事项分类名称重复");
            }else{
                resultVo.setResultDes("批量保存事项分类异常，原因为:" + e.getMessage());
            }
            log.error("批量保存事项分类异常",e);
        }
        return resultVo;
    }
    
    
    @ResponseBody
    @ApiOperation(value = "查询事项分类详情")
    @RequestMapping(value = "/getSiteIconCategoryDetail",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public ResultVo getSiteIconCategoryDetail(@RequestParam(value = "id") Integer id){
        ResultVo resultVo=new ResultVo();
        SSMSiteIconCategorySaveVO scVo = new SSMSiteIconCategorySaveVO();
    	List<SiteIconCategory> scList = new ArrayList<SiteIconCategory>();
        try {
        	SiteIconCategory sc = siteIconCategoryFacade.getByPrimaryKey(id);
            if(null != sc){
            	List<SiteIconCategory> s = siteIconCategoryFacade.getByParentId(sc.getId().intValue());
            	scVo.setSiteIconCategoryList(s);
            }
            scVo.setSiteIconCategory(sc);
            resultVo.setCode("00");
            resultVo.setSuccess(true);
            resultVo.setResultDes("查询成功");
            resultVo.setResult(scVo);
        }catch (Exception e){
            resultVo.setCode("400");
            resultVo.setSuccess(false);
            resultVo.setResultDes("查询事项分类详情异常，原因为:"+e);
            log.error("查询事项分类详情",e);
        }
        return resultVo;
    }
}
