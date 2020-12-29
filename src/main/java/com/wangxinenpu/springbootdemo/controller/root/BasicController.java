package com.wangxinenpu.springbootdemo.controller.root;


import com.wangxinenpu.springbootdemo.dataobject.vo.ManagerVo;
import com.wangxinenpu.springbootdemo.dataobject.vo.PageListVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import star.bizbase.vo.result.Results;
import star.fw.web.support.IntegerPropertiesSupport;
import star.fw.web.support.LongPropertiesSupport;
import star.fw.web.support.StringPropertiesSupport;
import star.fw.web.util.RequestUtil;
import star.fw.web.util.ServletAttributes;
import star.util.StringUtil;
import star.vo.result.ResultVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * 
 * Title: 基础Controller
 * 
 * Description:
 * 
 * Copyright: (c) 2014
 * 
 * @author haoxz11
 * @created 上午10:19:10
 * @version $Id: BasicController.java 123184 2015-12-17 07:25:17Z xuxh $
 */
public abstract class BasicController {

//	public static final Gson gson = new Gson();
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 获取登陆用户的信息
	 * @return
	 */
	protected ManagerVo getSysUser(){
		return (ManagerVo) ServletAttributes.getRequest().getAttribute("sysUser");
	}

	/**
	 * 获取当前登陆人信息
	 * @return
	 */
	protected  ManagerVo getManageVo(HttpServletRequest request){
		return (ManagerVo) request.getAttribute("sysUser");
	}
	
	/**
	 * 文本直接输出
	 * 
	 * @param s
	 */
	protected void out(String s) {
		try {
			PrintWriter out = ServletAttributes.getResponse().getWriter();
			out.print(s);
			out.close();
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}

	/**
	 * 判断请求是否为ajax
	 * 
	 * @return
	 */
	protected boolean isAjax() {
		return RequestUtil.isAjax(ServletAttributes.getRequest());
	}

	/**
	 * 判断是否为爬虫
	 * 
	 * @return
	 */
	public boolean isRobot() {
		return RequestUtil.isRobot(ServletAttributes.getRequest());
	}

	/**
	 * 判断是否为手机访问
	 * 
	 * @return
	 */
	protected boolean isMobile() {
		return RequestUtil.isMobile(ServletAttributes.getRequest());
	}

	/**
	 * 判断是否为ipad访问
	 * 

	 * @return
	 */
	protected boolean isIpad() {
		return RequestUtil.isIpad(ServletAttributes.getRequest());
	}

	/**
	 * 是否为UC浏览器
	 * 
	 * @return
	 */
	public boolean checkUCJisu() {
		return RequestUtil.checkUCJisu(ServletAttributes.getRequest());
	}

	/**
	 * 302跳转
	 * 
	 * @param response
	 * @param redirectUrl
	 */
	protected void send302(HttpServletResponse response, String redirectUrl) {
		RequestUtil.send302(response, redirectUrl);
	}

	/**
	 * 301跳转
	 * 
	 * @param response
	 * @param redirectUrl
	 */
	protected void send301(HttpServletResponse response, String redirectUrl) {
		RequestUtil.send301(response, redirectUrl);
	}

	/**
	 * 修改response的状态码
	 * 
	 * @param response
	 * @param code
	 */
	protected void sendError(HttpServletResponse response, int code) {
		try {
			response.sendError(code);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到来源
	 * 
	 * @return
	 */
	protected String getReferer() {
		return ServletAttributes.getRequest().getHeader("Referer");
	}

	/**
	 * 得到来源
	 * 
	 * @return
	 */
	protected String getIp() {
		return RequestUtil.getRealIp(ServletAttributes.getRequest());
	}

	@InitBinder
	// 必须有一个参数WebDataBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		binder.registerCustomEditor(int.class, new IntegerPropertiesSupport());
		binder.registerCustomEditor(Integer.class, new IntegerPropertiesSupport());
		binder.registerCustomEditor(Long.class, new LongPropertiesSupport());
		binder.registerCustomEditor(String.class, new StringPropertiesSupport());
	}
	
	/**
	 * 将获取的时间字符串转化成date对象
	 * @param dateString
	 * @return Date
	 */
	public Date parseDate(String dateString){
	    try {
	        if(StringUtil.isEmpty(dateString)){
	            return null;
	        }
           return simpleDateFormat.parse(dateString); 
        } catch (Exception e) {
            throw new RuntimeException("时间转换异常");
        }
	}

	public ResultVo<PageListVo> setPageResultError(String code, String message) {
		ResultVo<PageListVo> result = Results.newResultVo();
		result.setSuccess(false);
		result.setCode(code);
		result.setResultDes(message);
		return result;
	}

	public ResultVo<List<?>> setListResultError(String code, String message) {
		ResultVo<List<?>> result = Results.newResultVo();
		result.setSuccess(false);
		result.setCode(code);
		result.setResultDes(message);
		return result;
	}

	public ResultVo<Integer> setIntResultError(String code, String message) {
		ResultVo<Integer> result = Results.newResultVo();
		result.setSuccess(false);
		result.setCode(code);
		result.setResultDes(message);
		return result;
	}

	public ResultVo<Integer> setIntResultSuccessed(int n) {
		ResultVo<Integer> result = Results.newResultVo();
		result.setCode("0");
		result.setSuccess(true);
		result.setResultDes("Success");
		result.setResult(n);
		return result;
	}

	public ResultVo<Double> setDoubleResultError(String code, String message) {
		ResultVo<Double> result = Results.newResultVo();
		result.setSuccess(false);
		result.setCode(code);
		result.setResultDes(message);
		return result;
	}
	public ResultVo<Double> setDoubleResultSuccessed(Double n) {
		ResultVo<Double> result = Results.newResultVo();
		result.setCode("0");
		result.setSuccess(true);
		result.setResultDes("Success");
		result.setResult(n);
		return result;
	}

	public ResultVo<List<?>> setListResultSuccessed(List<?> list) {
		ResultVo<List<?>> result = Results.newResultVo();
		result.setCode("0");
		result.setSuccess(true);
		result.setResultDes("Success");
		result.setResult(list);
		return result;
	}

	public ResultVo<PageListVo> setPageResultSuccessed(List<?> list, int count) {
		ResultVo<PageListVo> result = Results.newResultVo();
		PageListVo pageListVo = new PageListVo();
		pageListVo.setPageTotal(count);
		pageListVo.setList(list);

		result.setResult(pageListVo);
		result.setSuccess(true);
		result.setResultDes("success");
		result.setCode("0");
		return result;
	}

	public ResultVo<PageListVo> setPageResultSuccessed(PageListVo pageListVo) {
		ResultVo<PageListVo> result = Results.newResultVo();

		result.setResult(pageListVo);
		result.setSuccess(true);
		result.setResultDes("success");
		result.setCode("0");
		return result;
	}

}
