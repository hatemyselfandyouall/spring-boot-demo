package com.wangxinenpu.springbootdemo.controller.user;

import com.alibaba.fastjson.JSONArray;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysCodeDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysOrginstypeDTO;
import com.wangxinenpu.springbootdemo.service.facade.SysCodeFacade;
import com.wangxinenpu.springbootdemo.service.facade.SysOrginstypeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import star.bizbase.exception.BizRuleException;
import star.bizbase.vo.result.Results;
import star.vo.result.ResultVo;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码维护
 * @author Administrator
 *
 */
@Controller
@RequestMapping("sys/code")
public class SysCodeController {
	
	@Autowired
	private SysCodeFacade sysCodeFacade;
	@Autowired
	private SysOrginstypeFacade sysOrginstypeFacade;
	
	/**
	 * 列表查询
	 * @param request
	 * @param model
	 * @param codeType
	 * @param codeName
	 * @param page
	 * @param size
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/queryTable" })
	public Map<String, Object> queryTable(HttpServletRequest request, Model model,
                                          @RequestParam(name = "codeType") String codeType,
                                          @RequestParam(name = "codeName") String codeName,
                                          @RequestParam(name = "parameterSort") String parameterSort,
                                          @RequestParam(name = "effectiveSign") String effectiveSign,
                                          @RequestParam(name = "maintainSign") String maintainSign,
                                          @RequestParam(name = "page") Integer page,
                                          @RequestParam(name = "size") Integer size) {
		ResultVo<List<SysCodeDTO>> result = Results.newResultVo();
		Map<String, Object> resultMap=new HashMap<>();
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("codeType", codeType);
		searchMap.put("codeName", codeName);
		searchMap.put("parameterSort", parameterSort);
		searchMap.put("effectiveSign", effectiveSign);
		searchMap.put("maintainSign", maintainSign);
		//计算当前页
		page = (page -1)*size;
		List<SysCodeDTO> list = sysCodeFacade.getListByWhere(searchMap, page, size);
		int count = sysCodeFacade.getCountByWhere(searchMap);
		result.setSuccess(true);
		result.setResultDes("success");
		result.setCode("0");
		result.setResult(list);
		resultMap.put("count", count);
		resultMap.put("pageList", result);
		return resultMap;
	}
	
	/**
	 * 保存
	 * @param sysCodeDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/saveCode" },method = RequestMethod.POST)
	public ResultVo<JSONArray> saveCode(@RequestBody SysCodeDTO sysCodeDTO) {
		ResultVo<JSONArray> result = Results.newResultVo();
		result.setSuccess(false);
		HashMap<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("codeType", sysCodeDTO.getCodeType());
		searchMap.put("codeValue", sysCodeDTO.getCodeValue());
		searchMap.put("id", sysCodeDTO.getId());
		List<SysCodeDTO> codelist = sysCodeFacade.findByCodeTypeAndCodeValue(searchMap);
		if(codelist.size()>0){
			result.setResultDes("代码值重复");
			return result;
		}
		try {
			if(null ==sysCodeDTO.getId() || "".equals(sysCodeDTO.getId())){
				sysCodeFacade.addSysCode(sysCodeDTO);
			}else{
				if("0".equals(sysCodeDTO.getMaintainSign())){
					result.setResultDes("当前数据不可修改");
					return result;
				}
				sysCodeFacade.updatepo(sysCodeDTO);
			}
			result.setCode("0");
			result.setSuccess(true);
			result.setResultDes("保存成功");
		} catch (Exception e) {
			result.setResultDes("代码维护保存失败，失败原因："+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 查询详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/detail" })
	public ResultVo<SysCodeDTO> detail(String id) {
		ResultVo<SysCodeDTO> result = Results.newResultVo();
		result.setSuccess(false);
		if(null !=id && !"".equals(id)){
			SysCodeDTO sysCode = sysCodeFacade.getByPrimaryKey(Long.parseLong(id));
			result.setResult(sysCode);
			result.setCode("0");
			result.setSuccess(true);
		}
		return result;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/deleteCode" })
	public ResultVo<SysCodeDTO> deleteCode(String id) {
		ResultVo<SysCodeDTO> result = Results.newResultVo();
		result.setSuccess(false);
		if(null !=id && !"".equals(id)){
			HashMap<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("insId", id);
			List<SysOrginstypeDTO> insList= sysOrginstypeFacade.getListByWhere(searchMap);
			if(insList.size()>0){
				result.setResultDes("有机构关联不能删除");
				return result;
			}
			else{
				SysCodeDTO sysCodeDTO = sysCodeFacade.getByPrimaryKey(Long.parseLong(id));
				if(null != sysCodeDTO){
					if("0".equals(sysCodeDTO.getMaintainSign())){
						result.setResultDes("当前数据不可删除");
						return result;
					}
				}
				try {
					sysCodeFacade.deleteByPrimaryKey(Long.parseLong(id));
					result.setResultDes("删除成功");
					result.setCode("0");
					result.setSuccess(true);
				} catch (BizRuleException e) {
					result.setResultDes("删除失败，失败原因："+e.getMessage());
				}
			}
		}
		return result;
	}

}
