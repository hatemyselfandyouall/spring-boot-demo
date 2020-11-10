package com.wangxinenpu.springbootdemo.config.aop.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.wangxinenpu.springbootdemo.config.component.LoginComponent;
import com.wangxinenpu.springbootdemo.service.facade.SysUserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import star.bizbase.exception.BizRuleException;
import star.vo.result.ResultVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private LoginComponent loginComonent;
	
	@Autowired
	private SysUserFacade sysUserFacade;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		if (uri.equals("/") || uri.equals("")) {
			request.getRequestDispatcher("/index.html").forward(request, response);
			return false;
		}

		Long userId = loginComonent.getLoginUserId();
		if (userId==null) {
			ResultVo result = new ResultVo();
			result.setCode("6002");
			result.setResultDes("PermissionInterceptor get user fail");
			JSONObject.toJSONString(result);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(JSONObject.toJSONString(result));
			return false;
		}

		if (!sysUserFacade.getAuthByUrlAndUserId(uri, userId)) {
			ResultVo result = new ResultVo();
			result.setCode("6003");
			result.setResultDes("no permission");
			JSONObject.toJSONString(result);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(JSONObject.toJSONString(result));
			return false;
		}

		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		super.postHandle(request, response, handler, modelAndView);
	}

}
