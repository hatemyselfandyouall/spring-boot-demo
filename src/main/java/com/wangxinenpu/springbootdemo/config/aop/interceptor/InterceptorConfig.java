package com.wangxinenpu.springbootdemo.config.aop.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import com.wangxinenpu.springbootdemo.config.aop.interceptor.ParameterInterceptor;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    LoginInterceptor loginInterceptor;

    @Autowired
    ParameterInterceptor parameterInterceptor;

    @Autowired
    PermissionInterceptor permissionInterceptor;



    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //这里可以用registry.addInterceptor添加多个拦截器实例，后面加上匹配模式
        registry.addInterceptor(parameterInterceptor)
                //添加需要验证登录用户操作权限的请求
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/images/**")
                .excludePathPatterns("/index.html")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/error*");
        //这里add为“/**”,下面的exclude才起作用，且不管controller层是否有匹配客户端请求，拦截器都起作用拦截
//                .addPathPatterns("/hello")
        //如果add为具体的匹配如“/hello”，下面的exclude不起作用,且controller层不匹配客户端请求时拦截器不起作用

        //排除不需要验证登录用户操作权限的请求
        super.addInterceptors(registry);//最后将register往这里塞进去就可以了
        registry.addInterceptor(loginInterceptor)
                //添加需要验证登录用户操作权限的请求
                .addPathPatterns("/**")
                //这里add为“/**”,下面的exclude才起作用，且不管controller层是否有匹配客户端请求，拦截器都起作用拦截
//                .addPathPatterns("/hello")
                //如果add为具体的匹配如“/hello”，下面的exclude不起作用,且controller层不匹配客户端请求时拦截器不起作用

                //排除不需要验证登录用户操作权限的请求
                .excludePathPatterns("/login*")
                .excludePathPatterns("/doLogin*")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/images/**")
                .excludePathPatterns("/index.html")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/swagger**")
                .excludePathPatterns("**css")
                .excludePathPatterns("**js")
                .excludePathPatterns("**png")
                .excludePathPatterns("/error*");
        super.addInterceptors(registry);//最后将register往这里塞进去就可以了
        registry.addInterceptor(permissionInterceptor)
                //添加需要验证登录用户操作权限的请求
                .addPathPatterns("/**")
                //这里add为“/**”,下面的exclude才起作用，且不管controller层是否有匹配客户端请求，拦截器都起作用拦截
//                .addPathPatterns("/hello")
                //如果add为具体的匹配如“/hello”，下面的exclude不起作用,且controller层不匹配客户端请求时拦截器不起作用

                //排除不需要验证登录用户操作权限的请求
                .excludePathPatterns("/login*")
                .excludePathPatterns("/doLogin*")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/images/**")
                .excludePathPatterns("/index.html")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/swagger**")
                .excludePathPatterns("**css")
                .excludePathPatterns("**js")
                .excludePathPatterns("**png")
                .excludePathPatterns("/error*");
        super.addInterceptors(registry);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/static/");
    }
}
