package com.wangxinenpu.springbootdemo.constant;

/**
 * 基础数据配置类, <br>
 * 与 t_base_data 表中 相对应 <br>
 * 使用:
 * 
 * <pre>
 * SessionAttributeSign.CURRENT_ACCOUNT_INFO
 * </pre>
 * 
 **/


public class SessionAttributeSign {
	
	

    /**
     * 当前用户
     */
    public static final String CURRENT_ACCOUNT_INFO = "currUser";
	
    
    /**
     * 当前用户  所属地区
     */
    public static final String CURRENT_ACCOUNT_AREA = "currArea";
    
    /**
     * 登录人头像
     */
    public static final String PORTRAIT_URL = "portraitUrl";
    
    
    /**
     * 当前用户  所属角色
     */
    public static final String CURRENT_ACCOUNT_ROLE = "currRole";


	public static final String CURRENT_ACCOUNT_MAIN_URL = "currMainUrl";

}
