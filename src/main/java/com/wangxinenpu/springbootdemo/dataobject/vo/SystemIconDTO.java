package com.wangxinenpu.springbootdemo.dataobject.vo;

import lombok.Data;
import star.vo.BaseVo;

@Data
public class SystemIconDTO extends BaseVo {

    private String id;

    private String iconName;

    private String createId;

    private Long functionId;
}
