package com.wangxinenpu.springbootdemo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import star.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

public class IpConfigUtil {
	protected static Logger logger = LoggerFactory.getLogger(IpConfigUtil.class);

/**
	 * 获得客户端ip地址
	 * 
	 * @param request
	 * @return 获得客户端ip地址
	 */
	public static String getClientIp(HttpServletRequest request){
		String unknown = "unknown";
		String wrap = "\n\t\t";
		StringBuilder logBuilder = new StringBuilder();
		// 是否使用反向代理
		String ip = request.getHeader("X-Forwarded-For");
		logger.info("ip:{}",ip);
		ip = request.getHeader("Proxy-Client-IP");
		logger.info("ip:{}",ip);
		ip = request.getHeader("WL-Proxy-Client-IP");
		logger.info("ip:{}",ip);
		ip = request.getHeader("HTTP_CLIENT_IP");
		logger.info("ip:{}",ip);
		ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		logger.info("ip:{}",ip);
		ip = request.getRemoteAddr();
		
		String ipAddress = request.getHeader("X-Forwarded-For");
		logBuilder.append(wrap + "header_xForwardedFor:" + ipAddress);
		if (StringUtil.isEmpty(ipAddress) || unknown.equalsIgnoreCase(ipAddress)){
			ipAddress = request.getHeader("Proxy-Client-IP");
			logBuilder.append(wrap + "header_proxyClientIP:" + ipAddress);
		}
		if (StringUtil.isEmpty(ipAddress) || unknown.equalsIgnoreCase(ipAddress)){
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
			logBuilder.append(wrap + "header_wLProxyClientIP:" + ipAddress);
		}
		if (StringUtil.isEmpty(ipAddress) || unknown.equalsIgnoreCase(ipAddress)){
			ipAddress = request.getRemoteAddr();
			logBuilder.append(wrap + "request.getRemoteAddr():" + ipAddress);
		}
		if (StringUtil.isEmpty(ipAddress) || unknown.equalsIgnoreCase(ipAddress)){
			ipAddress =request.getHeader("HTTP_CLIENT_IP");
			logBuilder.append(wrap + "request.HTTP_CLIENT_IP:" + ipAddress);
		}
		if (StringUtil.isEmpty(ipAddress) || unknown.equalsIgnoreCase(ipAddress)){
			ipAddress =  request.getHeader("HTTP_X_FORWARDED_FOR");
			logBuilder.append(wrap + "HTTP_X_FORWARDED_FOR:" +ipAddress);
		}
		if (StringUtil.isEmpty(ipAddress) || unknown.equalsIgnoreCase(ipAddress)){
			ipAddress = request.getHeader("X-Real-IP");
			logBuilder.append(wrap + "X-Real-IP:" + ipAddress);
		}
		
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15){ // "***.***.***.***".length() = 15
			logBuilder.append(wrap + "all:" + ipAddress);
			if (ipAddress.indexOf(",") > 0){
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		logger.info("all ip:" + logBuilder.toString());
		return ipAddress;
	}
}
