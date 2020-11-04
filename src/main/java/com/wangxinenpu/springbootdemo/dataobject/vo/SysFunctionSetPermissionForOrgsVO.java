package com.wangxinenpu.springbootdemo.dataobject.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class SysFunctionSetPermissionForOrgsVO implements Serializable {

    private Long functionId;

    private List<Long> orgIds;


}
