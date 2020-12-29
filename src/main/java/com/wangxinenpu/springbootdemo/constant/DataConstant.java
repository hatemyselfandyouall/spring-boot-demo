package com.wangxinenpu.springbootdemo.constant;

public class DataConstant {
    public static Integer NO_DELETE=0;
    public static Integer IS_DELETE=1;
    public static Integer NO_USE=0;
    public static Integer IS_USE=1;
    public static Integer TYPE_MEND_PUBPIC=0;
    public static Integer TYPE_MEND_PRIVATE=1;
    //0非公共消息1公共消息
    public static Integer TPYE_NOTIFICATION_PUBLIC=1;
    public static Integer TPYE_NOTIFICATION_NO_PUBLIC=0;
    //0未发布1已发布
    public static Integer TPYE_NOTIFICATION_PUBLISHED=1;
    public static Integer TPYE_NOTIFICATION_NO_PUBLISHED=0;
    //1单位2个人
    public static Long TPYE_ACCOUNT_TPYE_ORG=1L;
    public static Long TPYE_ACCOUNT_TPYE_PERSON=2L;
    //1事项存在0不存在
    public static Integer TYPE_MATTER_EXIT=1;
    public static Integer TYPE_MATTER_NO_EXIT=0;
    //1事项未生效2已生效
    public static Integer TYPE_MATTER_NO_USE=1;
    public static Integer TYPE_MATTER_IS_USE=2;

    public static final Integer IS_LAST_USED = 1;
    public static final Integer NOT_LAST_USED = 0;

    //自助机软件类型（0.MONITOR_SERVER，1.HTTP，2.MONITOR_CLIENT)
    public static String SOFTTYPE_MONITOR_SERVER = "0";
    public static String SOFTTYPE_HTTP = "1";
    public static String SOFTTYPE_MONITOR_CLIENT = "2";


    public static Integer NO = 0;
    public static Integer YES = 1;
}
