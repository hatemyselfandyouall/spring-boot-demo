package com.wangxinenpu.springbootdemo.config.aop.interceptor;

import com.alibaba.fastjson.JSONObject;

import com.wangxinenpu.springbootdemo.config.component.LoginComponent;
import com.wangxinenpu.springbootdemo.service.facade.SysUserEmpowerFacade;
import com.wangxinenpu.springbootdemo.service.facade.SysUserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import star.bizbase.exception.BizRuleException;
import star.util.StringUtil;
import star.vo.result.ResultVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * 
 * Title: 登录拦截器
 * 
 * Description:
 * 
 * Copyright: (c) 2014
 * 
 * @author haoxz11
 * @created 上午9:02:07
 * @version $Id: LoginInterceptor.java 89113 2015-06-13 10:17:14Z zhjy $
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private LoginComponent loginComponent;
	@Autowired
	private SysUserEmpowerFacade sysUserEmpowerFacade;
	@Autowired
	private SysUserFacade sysUserFacade;

	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		// 上下文路径
//		if (loginComponent.isLogin(request, response)) {
			return super.preHandle(request, response, handler);
//		} else {
//			ResultVo result = new ResultVo();
//			result.setCode("6001");
//			result.setResultDes("login failed");
//			JSONObject.toJSONString(result);
//			response.setCharacterEncoding("UTF-8");
//			response.getWriter().write(JSONObject.toJSONString(result));
//		}
//		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		super.postHandle(request, response, handler, modelAndView);
	}
}
