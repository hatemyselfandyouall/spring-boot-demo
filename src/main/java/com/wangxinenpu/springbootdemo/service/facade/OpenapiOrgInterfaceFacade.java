 
package com.wangxinenpu.springbootdemo.service.facade;

import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiOrgInterface;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiOrgInterface.OpenapiOrgInterfaceDeleteVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiOrgInterface.OpenapiOrgInterfaceDetailVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiOrgInterface.OpenapiOrgInterfaceListVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiOrgInterface.OpenapiOrgInterfaceSaveVO;


public interface OpenapiOrgInterfaceFacade{

	PageInfo<OpenapiOrgInterface> getOpenapiOrgInterfaceList(OpenapiOrgInterfaceListVO listVO);

    OpenapiOrgInterface getOpenapiOrgInterfaceDetail(OpenapiOrgInterfaceDetailVO detailVO);

    Integer saveOpenapiOrgInterface(OpenapiOrgInterfaceSaveVO saveVO);

    Integer deleteOpenapiOrgInterface(OpenapiOrgInterfaceDeleteVO deleteVO);

	 

}

 
