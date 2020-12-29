 
package com.wangxinenpu.springbootdemo.service.facade;

import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.dataobject.dto.sitematters.SelfMachineOrgDTO;
import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiOrg;
import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiSelfmachineRequest;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineRequest.*;


public interface OpenapiSelfmachineRequestFacade{

	PageInfo<OpenapiSelfmachineRequest> getOpenapiSelfmachineRequestList(OpenapiSelfmachineRequestListVO listVO);

    OpenapiSelfmachineRequest getOpenapiSelfmachineRequestDetail(OpenapiSelfmachineRequestDetailVO detailVO);

    Integer saveOpenapiSelfmachineRequest(OpenapiSelfmachineRequestSaveVO saveVO);

    Integer deleteOpenapiSelfmachineRequest(OpenapiSelfmachineRequestDeleteVO deleteVO);


    OpenapiSelfmachineRequest createToken(OpenapiSelfmachineRequest uniqueCode, OpenapiOrg openapiOrg);

    Boolean checkTokenExit(String token);


    SelfMachineOrgDTO getOrgByToken(String token);

    OpenapiSelfmachineDetailShowVO getDetailByToken(String token);

    String getInitMachineCode(OpenapiSelfmachineRequest openapiSelfmachineRequest, OpenapiOrg openapiOrg);

}

 
