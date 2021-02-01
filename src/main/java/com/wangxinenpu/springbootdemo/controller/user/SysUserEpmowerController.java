package com.wangxinenpu.springbootdemo.controller.user;


import com.wangxinenpu.springbootdemo.config.aop.OperLog;
import com.wangxinenpu.springbootdemo.dataobject.vo.*;
import com.wangxinenpu.springbootdemo.service.facade.SysUserEmpowerFacade;
import com.wangxinenpu.springbootdemo.service.facade.SysUserFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import star.bizbase.exception.BizRuleException;
import star.bizbase.vo.result.Results;
import star.vo.result.ResultVo;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("sys/userEpmower")
@Api(tags = "审核配置")
public class SysUserEpmowerController {
	
	@Autowired
	private SysUserEmpowerFacade sysUserEmpowerFacade;
	@Autowired
	private SysUserFacade sysUserFacade;
	
	/**
	 * 用户授权
	 * @param logonName
	 * @param passWord
	 * @param ythUserId
	 * @return
	 */
	@RequestMapping(value = { "/empower" })
	@ResponseBody
	@ApiOperation(value = "绑定一体化id")
	@OperLog(systemName="后管系统")
	public ResultVo<List<SysUserEmpowerDTO>> empower(String logonName, String passWord, String ythUserId) {
		ResultVo<List<SysUserEmpowerDTO>> result = Results.newResultVo();
		result.setSuccess(false);
//		HashMap<String, Object> searchMap = new HashMap<>();
//		searchMap.put("ythUserId", ythUserId);
//		List<SysUserEmpowerDTO> empList = sysUserEmpowerFacade.getListByWhere(searchMap);
//		if(empList.size()>0){
//			//已授权，跳转请求页面
//		}
		
		//绑定
		HashMap<String, Object> map = new HashMap<>();
		map.put("logonName", logonName);
		map.put("passwd", passWord);
		List<SysUserDTO> userList= sysUserFacade.getListByWhere(map);
		if(userList.size()>0){
			SysUserEmpowerDTO emp = new SysUserEmpowerDTO();
			emp.setuserId(userList.get(0).getId().toString());
			emp.setythUserId(ythUserId);
			try {
				sysUserEmpowerFacade.addSysUserEmpower(emp);
				result.setSuccess(true);
				result.setResultDes("用户授权成功，页面跳转");
			} catch (BizRuleException e) {
				result.setResultDes("用户授权失败"+e);
			}
		}else{
			result.setResultDes("用户名或密码错误");
		}
		
		result.setCode("0");
//		result.setResult(empList);
		return result;
	}

}
