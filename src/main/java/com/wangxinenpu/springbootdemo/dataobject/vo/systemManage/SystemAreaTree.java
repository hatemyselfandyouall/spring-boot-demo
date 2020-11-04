package com.wangxinenpu.springbootdemo.dataobject.vo.systemManage;

import lombok.Data;
import star.vo.BaseVo;

import java.util.List;

@Data
public class SystemAreaTree extends BaseVo {

    private Long areaId;

    private Long parentId;

    private String areaName;

    private List<SystemTreeVo> systemList;

    private List<SystemAreaTree> children;
}
