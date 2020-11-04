package com.wangxinenpu.springbootdemo.dataobject.vo;

import com.wangxinenpu.springbootdemo.dataobject.vo.systemManage.SystemManageListVo;
import lombok.Data;

import java.util.List;

@Data
public class SysOrgDTOwithSystemTreeVo extends SysOrgDTO {

    private List<SystemManageListVo> systemList;

    private List<SysOrgDTOwithSystemTreeVo> children;

}
