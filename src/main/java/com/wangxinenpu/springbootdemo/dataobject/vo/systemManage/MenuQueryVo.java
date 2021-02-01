package com.wangxinenpu.springbootdemo.dataobject.vo.systemManage;

import lombok.Data;
import star.vo.BaseVo;


@Data
public class MenuQueryVo extends BaseVo {

    private String areaName;

    private String areaId;

    private String systemName;

    private String channelCode;

}
