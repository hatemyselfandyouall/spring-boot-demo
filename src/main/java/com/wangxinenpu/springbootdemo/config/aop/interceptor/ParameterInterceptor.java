package com.wangxinenpu.springbootdemo.config.aop.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import star.fw.web.util.ServletAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ParameterInterceptor extends HandlerInterceptorAdapter {

    public ParameterInterceptor() {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ServletAttributes.setRequest(request);
        ServletAttributes.setResponse(response);
        return super.preHandle(request, response, handler);
    }
}
