//package com.wangxinenpu.springbootdemo.controller.user;
//
//
//
//import com.wangxinenpu.springbootdemo.config.aop.OperLog;
//import com.wangxinenpu.springbootdemo.dataobject.po.AuditConfigRule;
//import com.wangxinenpu.springbootdemo.dataobject.vo.*;
//import com.wangxinenpu.springbootdemo.service.facade.AuditConfigRuleFacade;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import star.bizbase.exception.BizRuleException;
//import star.vo.result.ResultVo;
//
///**
// * 审核配置规则
// *
// * @author Administrator
// *
// */
//@Controller
//@RequestMapping("sys/auditconfigrule")
//@Api(tags = "审核配置规则")
//public class AuditConfigRuleController  {
//
//	@Autowired
//	private AuditConfigRuleFacade auditConfigRuleFacade;
//
//
//	protected Logger logger = LoggerFactory.getLogger(getClass());
//
//
//	@RequestMapping(value = "/getAuditConfigRuleDTOListPage", method = RequestMethod.GET)
//	@ResponseBody
//	@ApiOperation(value = "审核配置规则列表")
//	@OperLog(systemName="后管系统")
//	public ResultVo<DataListResultDto<AuditConfigRuleDTO>> getAuditConfigRuleDTOListPage(@RequestParam(value = "funName", required = false) String funName,
//																						 @RequestParam(value = "funType", required = false) String funType,
//																						 @RequestParam(value = "ruleStruts", required = false) String ruleStruts,
//																						 @RequestParam(value = "isBus", required = false) String isBus,
//																						 @RequestParam(value = "funLevel", required = false) String funLevel,
//																						 @RequestParam(value = "auFlag", required = false) String auFlag,
//																						 @RequestParam(value = "page") Integer page,
//																						 @RequestParam(value = "pageSize") Integer pageSize) throws BizRuleException {
//
//		ResultVo<DataListResultDto<AuditConfigRuleDTO>> grid = new ResultVo();
//		DataListResultDto<AuditConfigRuleDTO> auditConfigRuleDTODataListResultDto = new DataListResultDto();
//		try{
//			auditConfigRuleDTODataListResultDto.setTotalCount(auditConfigRuleFacade.getAuditConfigRuleDTOListCount(funName,funType,ruleStruts,isBus,funLevel,auFlag));
//			auditConfigRuleDTODataListResultDto.setDataList(auditConfigRuleFacade.getAuditConfigRuleDTOListPage(funName,funType,ruleStruts,isBus,funLevel,auFlag,page,pageSize));
//			grid.setCode("0000");
//			grid.setResultDes("success");
//			grid.setSuccess(true);
//			grid.setResult(auditConfigRuleDTODataListResultDto);
//		} catch (Exception e) {
//			grid.setCode("4000");
//			grid.setResult(null);
//			grid.setSuccess(false);
//			e.printStackTrace();
//			grid.setResultDes("获取审核配置规则列表失败");
//		}
//		return grid;
//	}
//
//	@RequestMapping(value = "/getAuditConfigRuleInfo", method = RequestMethod.GET)
//	@ResponseBody
//	@ApiOperation(value = "审核配置规则详情")
//	public ResultVo<AuditConfigRuleDTO> getAuditConfigRuleInfo(@RequestParam(value = "id", required = false) Long id) throws BizRuleException {
//		ResultVo<AuditConfigRuleDTO> grid = new ResultVo();
//		try{
//			grid.setCode("0000");
//			grid.setResultDes("success");
//			grid.setSuccess(true);
//			grid.setResult(auditConfigRuleFacade.getAuditConfigRuleInfo(id));
//		} catch (Exception e) {
//			grid.setCode("4000");
//			grid.setResult(null);
//			grid.setSuccess(false);
//			e.printStackTrace();
//			grid.setResultDes("审核配置规则详情失败");
//		}
//		return grid;
//	}
//
//	@RequestMapping(value = "/saveAuditConfigRuleInfo", method = RequestMethod.POST)
//	@ResponseBody
//	@ApiOperation(value = "保存审核配置规则")
//	@OperLog(systemName="后管系统")
//	public ResultVo<Integer> saveAuditConfigRuleInfo(@RequestBody AuditConfigRule auditConfigRule) throws BizRuleException {
//		ResultVo<Integer> grid = new ResultVo();
//		try{
//			grid.setCode("0000");
//			grid.setResultDes("success");
//			grid.setSuccess(true);
//			grid.setResult(auditConfigRuleFacade.saveAuditConfigRuleInfo(auditConfigRule));
//		} catch (Exception e) {
//			grid.setCode("4000");
//			grid.setResult(null);
//			grid.setSuccess(false);
//			e.printStackTrace();
//			grid.setResultDes("保存审核配置规则失败");
//		}
//		return grid;
//	}
//
//	@RequestMapping(value = "/updAuditConfigRuleInfo", method = RequestMethod.POST)
//	@ResponseBody
//	@ApiOperation(value = "修改审核配置规则")
//	@OperLog(systemName="后管系统")
//		public ResultVo<Integer> updAuditConfigRuleInfo(@RequestBody AuditConfigRule auditConfigRule) throws BizRuleException {
//		ResultVo<Integer> grid = new ResultVo();
//		try{
//			grid.setCode("0000");
//			grid.setResultDes("success");
//			grid.setSuccess(true);
//			grid.setResult(auditConfigRuleFacade.updAuditConfigRuleInfo(auditConfigRule));
//		} catch (Exception e) {
//			grid.setCode("4000");
//			grid.setResult(null);
//			grid.setSuccess(false);
//			e.printStackTrace();
//			grid.setResultDes("修改审核配置规则失败");
//		}
//		return grid;
//	}
//}
