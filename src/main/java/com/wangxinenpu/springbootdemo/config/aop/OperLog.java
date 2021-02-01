package com.wangxinenpu.springbootdemo.config.aop;

import java.lang.annotation.*;


/**
 * 自定义操作日志注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface OperLog {

	/**
	 * 系统名称
	 * @return
	 */
	String systemName();
	
	/**
	 * 功能描述
	 * @return
	 */
	String description() default "";
}
