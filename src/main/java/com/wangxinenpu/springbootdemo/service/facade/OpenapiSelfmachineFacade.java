 
package com.wangxinenpu.springbootdemo.service.facade;

import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiOrg;
import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiSelfmachine;
import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiSelfmachineRequest;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachine.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestSaveVO;


public interface OpenapiSelfmachineFacade{

	PageInfo<OpenapiSelfmachineShowVO> getOpenapiSelfmachineList(OpenapiSelfmachineListVO listVO, Long userId);

    OpenapiSelfmachine getOpenapiSelfmachineDetail(OpenapiSelfmachineDetailVO detailVO);

    OpenapiSelfmachine saveOpenapiSelfmachine(OpenapiSelfmachineSaveVO saveVO);

    Integer deleteOpenapiSelfmachine(OpenapiSelfmachineDeleteVO deleteVO);


    String saveSelfMachine(OpenapiSelfmachineRequestSaveVO openapiSelfmachineRequestSaveVO, OpenapiOrg id);

    String saveSelfMachineWithPermission(OpenapiSelfmachineRequestSaveVO openapiSelfmachineRequestSaveVO, OpenapiOrg openapiOrg);

    Integer setStatu(OpenapiSelfmachineDeleteVO openapiSelfmachineDeleteVO);

    Integer setActiveStatus(OpenapiSelfmachineSetActiveStatusVO openapiSelfmachineSetActiveStatusVO);

    Integer setOrg(OpenapiSelfmachineSetOrgVO openapiSelfmachineSetOrgVO);

    void updateSelfMachine(OpenapiSelfmachineRequestSaveVO openapiSelfmachineRequestSaveVO, OpenapiSelfmachineRequest openapiSelfmachine);

    String reActivSelfMachine(OpenapiSelfmachine openapiSelfmachine, OpenapiSelfmachineRequest openapiSelfmachineRequest, OpenapiOrg openapiOrg);

    void todayOpen(String uniqueCode);

    void clearOpenStatu();
}

 
