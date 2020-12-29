package com.wangxinenpu.springbootdemo.dataobject.dto;

import com.wangxinenpu.springbootdemo.dataobject.vo.root.PageVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class GetSelfMachineDTO extends PageVO implements Serializable {

    private static final long serialVersionUID = -1L;

    private String name;
}
