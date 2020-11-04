package com.wangxinenpu.springbootdemo.config.aop;

import com.alibaba.fastjson.JSONObject;

import com.wangxinenpu.springbootdemo.config.component.LoginComponent;
import com.wangxinenpu.springbootdemo.dataobject.po.SysInnerOperateLog;
import com.wangxinenpu.springbootdemo.service.facade.SysInnerOperateLogFacade;
import com.wangxinenpu.springbootdemo.util.HttpUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import star.fw.web.util.ServletAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Slf4j
//@Component
public class OperatorLogger {

    @Autowired
    LoginComponent loginComponent;
    @Autowired
    SysInnerOperateLogFacade sysInnerOperateLogFacade;

    protected Logger logger = LoggerFactory.getLogger(getClass());
//    @Pointcut("execution(* com.insigma.web..*.*(..))")
//    public void pointCut() {s
//
//    }
//    @Around(value = "execution(* star.fw..*.*(..)),execution(* com.insigma.webtool..*.*(..)),execution(* com.insigma.web..*.*(..))")
    public void   doLog(JoinPoint joinPoint, Object returnObject){
//        ResultVo resultVo=new ResultVo();
        try {
            String ip="";
            Long userId=null;
            String userName=null;
            String engMethod=null;
            String chsMethod=null;
            String returnValue=null;
            String paramJSON=null;
            String url=null;
            String systemName = null;
            String method = null;
            HttpServletRequest httpServletRequest=null;
            try {
                httpServletRequest=ServletAttributes.getRequest();
            }catch (Throwable throwable) {
                //这里取不到完全无所谓，甚至不需要日志
            }
            if (httpServletRequest!=null){
                ip= HttpUtil.getIpFromRequest(httpServletRequest);
                url=httpServletRequest.getRequestURI();
                method = httpServletRequest.getMethod();
                userId=loginComponent.getLoginUserId();
                userName=loginComponent.getLoginUserName();
            }
            Class clazz = joinPoint.getTarget().getClass();
            try {
                if(StringUtils.isNotEmpty(method)) {
                    if ("GET".equals(method)) {
                        paramJSON = JSONObject.toJSONString(joinPoint.getArgs()[1]);
                    }
                    paramJSON = JSONObject.toJSONString(joinPoint.getArgs());
                }
            }catch (Exception e){
                //这里取不到完全无所谓，甚至不需要日志
            }
            String methodName=joinPoint.getSignature().getName();
            Class[] parameterTypes = ((MethodSignature)joinPoint.getSignature()).getMethod().getParameterTypes();
            Method methdo = clazz.getMethod(methodName,parameterTypes);
            engMethod=methodName;
            ApiOperation annotation=methdo.getAnnotation(ApiOperation.class);
            OperLog operLogAnnotation=methdo.getAnnotation(OperLog.class);
            if (annotation != null) {
                chsMethod=annotation.value();

            }
            returnValue= JSONObject.toJSONString(returnObject);
           // List<SysInnerOperateLogMenu> sysInnerOperateLogMenus=sysInnerOperateLogFacade.getSysInnerOperateLogMenus();
            //Set<String> needLogMenuSet=sysInnerOperateLogMenus.stream().map(SysInnerOperateLogMenu::getMethodName).collect(Collectors.toSet());
          //  if (needLogMenuSet.contains(methodName)) {
            if (operLogAnnotation != null) {
                systemName=operLogAnnotation.systemName();
                SysInnerOperateLog operateLog = new SysInnerOperateLog()
                        .setOperatorIp(ip)
                        .setOperatorId(userId)
                        .setOperateDetailJson(paramJSON)
                        .setMethodChName(chsMethod)
                        .setMethodName(methodName)
                        .setOperatorName(userName)
                        .setSystemName(systemName)
                        .setUrl(url)
                        .setReturnValue(returnValue);
                sysInnerOperateLogFacade.saveOpearte(operateLog);
            }
        } catch (Throwable throwable) {
            //☆反正都返回了，这里就算出问题，也不会怎么样的吧☆
            log.error("aop拦截方法异常",throwable);
        }
    }

}
