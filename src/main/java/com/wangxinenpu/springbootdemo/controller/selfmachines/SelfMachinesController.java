package com.wangxinenpu.springbootdemo.controller.selfmachines;

import com.alibaba.fastjson.JSONArray;
import com.wangxinenpu.springbootdemo.config.component.LoginComponent;
import com.wangxinenpu.springbootdemo.constant.DataConstant;
import com.wangxinenpu.springbootdemo.dataobject.dto.sitematters.DataListResultDto;
import com.wangxinenpu.springbootdemo.dataobject.dto.sitematters.SiteIconCategoryDto;
import com.wangxinenpu.springbootdemo.dataobject.dto.sitematters.SiteMattersDto;
import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiSelfmachineType;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteIconCategory;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteMatters;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteMattersArea;
import com.wangxinenpu.springbootdemo.dataobject.vo.SiteMatters.SSMSiteMattersDetailInfoShowVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SiteMatters.SSMSiteMattersDetailShowVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SiteMatters.SSMSiteMattersSaveVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SiteMattersArea.SiteMattersAreaSaveVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysAreaDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysAreaNameDTO;
import com.wangxinenpu.springbootdemo.service.facade.*;
import com.wangxinenpu.springbootdemo.util.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import star.bizbase.vo.result.Results;
import star.util.StringUtil;
import star.vo.result.ResultVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 自助机菜单配置
 *
 * @author Administrator
 *
 */
@Controller
@RequestMapping("sm/selfmachines")
@Api(tags ="自助机菜单配置")
public class SelfMachinesController  {

	@Autowired
	private SysAreaFacade sysAreaFacade;

	@Autowired
	private SiteMattersFacade siteMattersFacade;

	@Autowired
	private LoginComponent loginComponent;


	@Autowired
	private SysUserFacade sysUserFacade;

	@Autowired
	private OpenapiSelfmachineTypeFacade openapiSelfmachineTypeFacade;

	@Autowired
	private SiteIconCategoryFacade siteIconCategoryFacade;
	/**
	 * 获取区域列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAreaTree",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	@ApiOperation(value = "行政区域树")
	public ResultVo<JSONArray> getAreaTreeNodes() {
		ResultVo<JSONArray> result = Results.newResultVo();
		//从缓存获取用户信息
		List<SysAreaDTO> sysAreaDTOS= sysAreaFacade.findAllByParentIdNull();
		JSONArray orgNodes = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(sysAreaDTOS)), "id", "parentId", "children");
		result.setCode("0");
		result.setSuccess(true);
		result.setResult(orgNodes);
		return result;
	}

	@ResponseBody
	@ApiOperation(value = "查询自助机事项管理列表")
	@RequestMapping(value = "/getSSMSiteMattersList",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	public ResultVo getSSMSiteMattersList(@RequestParam(value = "areaId",required = false) String areaId){
		ResultVo resultVo=new ResultVo();
		Long areaIdL = null;
		List<Object> siteMattersList = new ArrayList<>();
		if(StringUtils.isNotEmpty(areaId)) {
			areaIdL = Long.parseLong(areaId);
		}
		try {
			//查询事项分类
			List<SiteIconCategoryDto> siteIconCategoryDtos = siteIconCategoryFacade.getSSMSiteIconCategoryList(2,null);
			//查询事项菜单
			List<SiteMatters> mattersList = siteMattersFacade.getSSMSiteMattersList(areaIdL);
			siteMattersList.addAll(mattersList);
			siteMattersList.addAll(siteIconCategoryDtos);
			JSONArray jsonArray = null;
			if (siteMattersList.size() > 0) {
				jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(siteMattersList)), "id", "iconCategoryId",
						"children");
			}

			resultVo.setCode("00");
			resultVo.setSuccess(true);
			resultVo.setResultDes("查询自助机事项管理列表成功");
			resultVo.setResult(jsonArray);
		}catch (Exception e){
			resultVo.setCode("400");
			resultVo.setSuccess(false);
			resultVo.setResultDes("查询自助机事项管理列表异常，原因为:"+e);
		}
		return resultVo;
	}

	@ResponseBody
	@ApiOperation(value = "查询区域（市）")
	@RequestMapping(value = "/getAreaCityList",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	public ResultVo getAreaCityList(){
		ResultVo resultVo=new ResultVo();
		try {
			List<SysAreaDTO> sysAreaDTOS = sysAreaFacade.getAreaCityList();
			resultVo.setCode("00");
			resultVo.setSuccess(true);
			resultVo.setResultDes("查询区域（市）成功");
			resultVo.setResult(sysAreaDTOS);
		}catch (Exception e){
			resultVo.setCode("400");
			resultVo.setSuccess(false);
			resultVo.setResultDes("查询区域（市）异常，原因为:"+e);
		}
		return resultVo;
	}

	@ResponseBody
	@ApiOperation(value = "查询区域（区）")
	@RequestMapping(value = "/getAreaRegionList",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	public ResultVo getAreaRegionList(@RequestParam(value = "parentId") String parentId){
		ResultVo resultVo=new ResultVo();
		try {
			List<SysAreaDTO> sysAreaDTOS = sysAreaFacade.getAreaRegionList(parentId);
			resultVo.setCode("00");
			resultVo.setSuccess(true);
			resultVo.setResultDes("查询区域（区）成功");
			resultVo.setResult(sysAreaDTOS);
		}catch (Exception e){
			resultVo.setCode("400");
			resultVo.setSuccess(false);
			resultVo.setResultDes("查询区域（区）异常，原因为:"+e);
		}
		return resultVo;
	}


	@ResponseBody
	@ApiOperation(value = "查询自助机事项管理详情")
	@RequestMapping(value = "/siteSSMMattersDetail",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	public ResultVo siteSSMMattersDetail(@RequestParam(value = "id") Long id){
		ResultVo resultVo=new ResultVo();
		try {
			SSMSiteMattersDetailShowVO ssmSiteMattersDetailShowVO = siteMattersFacade.siteSSMMattersDetail(id);
			SSMSiteMattersDetailInfoShowVO ssmSiteMattersDetailInfoShowVO = new SSMSiteMattersDetailInfoShowVO();
			SiteMattersDto siteMattersDto = ssmSiteMattersDetailShowVO.getSiteMattersDto();
			siteMattersDto.setCreatorName(sysUserFacade.findUserNameById(siteMattersDto.getOperatorId()));
			siteMattersDto.setModifierName(sysUserFacade.findUserNameById(siteMattersDto.getModifierId()));
			ssmSiteMattersDetailInfoShowVO.setSiteMattersDto(siteMattersDto);
			List<SiteMattersArea> siteMattersAreas = ssmSiteMattersDetailShowVO.getSiteMattersAreas();
			List<SiteMattersAreaSaveVO> siteMattersAreaSaveVOS = new ArrayList<>();
			for(SiteMattersArea siteMattersArea:siteMattersAreas){
				SiteMattersAreaSaveVO siteMattersAreaSaveVO = new SiteMattersAreaSaveVO();
				Long areaId = siteMattersArea.getAreaId();
				SysAreaNameDTO sysAreaNameDTO = sysAreaFacade.getAreaInfo(areaId);
				if (sysAreaNameDTO!=null){
					siteMattersAreaSaveVO.setCity(sysAreaNameDTO.getCity());
					siteMattersAreaSaveVO.setRegion(sysAreaNameDTO.getRegion());
				}
				siteMattersAreaSaveVO.setAreaId(areaId);
				siteMattersAreaSaveVO.setMattersId(siteMattersArea.getMattersId());
				siteMattersAreaSaveVO.setMachineNumber(siteMattersArea.getMachineNumber());
				siteMattersAreaSaveVO.setAccountTypeId(siteMattersArea.getAccountTypeId());
				siteMattersAreaSaveVOS.add(siteMattersAreaSaveVO);
			}
			ssmSiteMattersDetailInfoShowVO.setSiteMattersAreaSaveVOS(siteMattersAreaSaveVOS);
			ssmSiteMattersDetailInfoShowVO.setSiteBlock(ssmSiteMattersDetailShowVO.getSiteBlock());
			resultVo.setCode("00");
			resultVo.setSuccess(true);
			resultVo.setResultDes("查询事项管理成功");
			resultVo.setResult(ssmSiteMattersDetailInfoShowVO);
		}catch (Exception e){
			resultVo.setCode("400");
			resultVo.setSuccess(false);
			resultVo.setResultDes("查询事项管理详情异常，原因为:"+e);
		}
		return resultVo;
	}

	@ResponseBody
	@ApiOperation(value = "保存或修改自助机事项管理")
	@RequestMapping(value = "/saveSSMSiteMatters",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	public ResultVo<SiteMatters> saveSSMSiteMatters(@RequestBody SSMSiteMattersSaveVO sSMSiteMattersSaveVO){
		ResultVo resultVo=new ResultVo();
		try {
			if (sSMSiteMattersSaveVO!=null&&sSMSiteMattersSaveVO.getId()==null&&!StringUtil.isEmpty(sSMSiteMattersSaveVO.getName())){
				Integer flag=siteMattersFacade.checkSSMSiteMatters(sSMSiteMattersSaveVO.getName());
				if (DataConstant.TYPE_MATTER_EXIT.equals(flag)){
					resultVo.setSuccess(false);
					resultVo.setResultDes("保存或修改事项管理失败，事项名不能重复");
					return resultVo;
				}
			}
			Long userId=loginComponent.getLoginUserId();
			sSMSiteMattersSaveVO.setModifierId(userId.toString());
			Integer siteMattersRes = siteMattersFacade.saveSSMSiteMatters(sSMSiteMattersSaveVO);
			resultVo.setCode("00");
			resultVo.setSuccess(true);
			resultVo.setResultDes("保存或修改事项管理成功");
			resultVo.setResult(siteMattersRes);
		}catch (Exception e){
			resultVo.setCode("400");
			resultVo.setSuccess(false);
			resultVo.setResultDes("保存或修改事项管理异常，原因为:"+e);
		}
		return resultVo;
	}

	@ResponseBody
	@ApiOperation(value = "获取机器码列表")
	@RequestMapping(value = "/getSelfmachine",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	public ResultVo getSelfmachine(){
		ResultVo resultVo=new ResultVo();
		try {
			List<OpenapiSelfmachineType> openapiSelfmachineTypeList = openapiSelfmachineTypeFacade.getAllTypes();
			resultVo.setCode("00");
			resultVo.setSuccess(true);
			resultVo.setResultDes("获取机器码列表成功");
			resultVo.setResult(openapiSelfmachineTypeList);
		}catch (Exception e){
			resultVo.setCode("400");
			resultVo.setSuccess(false);
			resultVo.setResultDes("获取机器码列表异常，原因为:"+e);
		}
		return resultVo;
	}


	@ResponseBody
	@ApiOperation(value = "根据事项类型id查询自助机事项列表")
	@RequestMapping(value = "/getSSMSiteMattersByCategoryId",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	public ResultVo<SiteMatters> getSSMSiteMattersByCategoryId(@RequestParam(value = "iconCategoryId") Long iconCategoryId, @RequestParam(value = "bussType",required = false) Integer bussType){
		ResultVo resultVo=new ResultVo();
		try {
			List<SiteMatters> siteMatters = siteMattersFacade.getSSMSiteMattersByCategoryId(iconCategoryId,bussType);
			resultVo.setCode("00");
			resultVo.setSuccess(true);
			resultVo.setResultDes("根据事项类型id查询自助机事项列表");
			resultVo.setResult(siteMatters);
		}catch (Exception e){
			resultVo.setCode("400");
			resultVo.setSuccess(false);
			resultVo.setResultDes("根据事项类型id查询自助机事项列表异常，原因为:"+e);
		}
		return resultVo;
	}


	@ResponseBody
	@ApiOperation(value = "查询没有分类的自助机事项")
	@RequestMapping(value = "/getSSMSiteMattersByCategoryIdNull",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	public ResultVo<SiteMatters> getSSMSiteMattersByCategoryIdNull(@RequestParam(value = "bussType",required = false) Integer bussType){
		ResultVo resultVo=new ResultVo();
		try {
			List<SiteMatters> siteMattersList = siteMattersFacade.getSSMSiteMattersByCategoryIdNull(bussType);
			resultVo.setCode("00");
			resultVo.setSuccess(true);
			resultVo.setResultDes("查询没有分类的自助机事项成功");
			resultVo.setResult(siteMattersList);
		}catch (Exception e){
			resultVo.setCode("400");
			resultVo.setSuccess(false);
			resultVo.setResultDes("查询没有分类的自助机事项异常，原因为:"+e);
		}
		return resultVo;
	}

	@ResponseBody
	@ApiOperation(value = "查询自助机事项分类列表")
	@RequestMapping(value = "/getSSMSiteIconCategoryList",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	public ResultVo getSSMSiteIconCategoryList(@RequestParam(value = "bussType") Integer bussType, @RequestParam(value = "name",required = false) String name,
                                               @RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size){
		ResultVo resultVo=new ResultVo();
		DataListResultDto<SiteIconCategoryDto> dataListResultDto = new DataListResultDto<>();
		try {
			//计算当前页
//			page = (page -1)*size;
			List<SiteIconCategoryDto> siteIconCategoryDtos = siteIconCategoryFacade.getSSMSiteIconCategoryPageList(bussType, name, page, size);
			if(siteIconCategoryDtos!=null){
				List<SiteIconCategoryDto> scList = siteIconCategoryFacade.getSSMSiteIconCategoryList(bussType, name);
				dataListResultDto.setTotalCount(scList.size());

				JSONArray jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(siteIconCategoryDtos)), "id", "parentId",
						"children");
				dataListResultDto.setDataJson(jsonArray);
			}
			resultVo.setCode("00");
			resultVo.setSuccess(true);
			resultVo.setResultDes("查询成功");
			resultVo.setResult(dataListResultDto);
		}catch (Exception e){
			resultVo.setCode("400");
			resultVo.setSuccess(false);
			resultVo.setResultDes("查询事项分类列表异常，原因为:"+e);
		}
		return resultVo;
	}


	@ResponseBody
	@ApiOperation(value = "查询事项分类树")
	@RequestMapping(value = "/getSSMSiteIconCategoryTree",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	public ResultVo getSSMSiteIconCategoryTree(){
		ResultVo resultVo=new ResultVo();
		try {
			JSONArray jsonArray = null;
			List<SiteIconCategory> scList = siteIconCategoryFacade.getListAll();
			if(scList!=null){
				jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(scList)), "id", "parentId",
						"children");
			}
			resultVo.setCode("00");
			resultVo.setSuccess(true);
			resultVo.setResultDes("查询成功");
			resultVo.setResult(jsonArray);
		}catch (Exception e){
			resultVo.setCode("400");
			resultVo.setSuccess(false);
			resultVo.setResultDes("查询事项分类树异常，原因为:"+e);
		}
		return resultVo;
	}
}
