 
package com.wangxinenpu.springbootdemo.service.facade;

import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiSelfmachineAddress;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineAddress.OpenapiSelfmachineAddressDeleteVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineAddress.OpenapiSelfmachineAddressDetailVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineAddress.OpenapiSelfmachineAddressListVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineAddress.OpenapiSelfmachineAddressSaveVO;


public interface OpenapiSelfmachineAddressFacade{

	PageInfo<OpenapiSelfmachineAddress> getOpenapiSelfmachineAddressList(OpenapiSelfmachineAddressListVO listVO, Long userId);

    OpenapiSelfmachineAddress getOpenapiSelfmachineAddressDetail(OpenapiSelfmachineAddressDetailVO detailVO);

    OpenapiSelfmachineAddress saveOpenapiSelfmachineAddress(OpenapiSelfmachineAddressSaveVO saveVO, Long userId, String userName);

    Integer deleteOpenapiSelfmachineAddress(OpenapiSelfmachineAddressDeleteVO deleteVO, Long userId);

}

 
