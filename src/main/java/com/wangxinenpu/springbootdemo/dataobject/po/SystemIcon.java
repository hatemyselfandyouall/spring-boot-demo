package com.wangxinenpu.springbootdemo.dataobject.po;

import lombok.Data;
import star.vo.BaseVo;

@Data
public class SystemIcon extends BaseVo {

    private String id;

    private String iconName;

    private String createId;

    private Long functionId;
}