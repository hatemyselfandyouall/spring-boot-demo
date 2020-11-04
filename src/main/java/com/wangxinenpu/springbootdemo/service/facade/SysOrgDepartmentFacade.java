 
package com.wangxinenpu.springbootdemo.service.facade;


import com.wangxinenpu.springbootdemo.dataobject.po.SysOrgDepartment;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysOrgDepartment.*;
import star.vo.result.ResultVo;

import java.util.List;


public interface SysOrgDepartmentFacade{

	List<SysOrgDepartment> getSysOrgDepartmentList(SysOrgDepartmentListVO listVO);

    SysOrgDepartment getSysOrgDepartmentDetail(SysOrgDepartmentDetailVO detailVO);

    ResultVo saveSysOrgDepartment(SysOrgDepartmentSaveVO saveVO);

    ResultVo deleteSysOrgDepartment(SysOrgDepartmentDeleteVO deleteVO);

	 

}

 
