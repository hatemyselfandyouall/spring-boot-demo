package com.wangxinenpu.springbootdemo.service.webservice;

import com.wangxinenpu.springbootdemo.dataobject.vo.getBatchPayInfoDTO.BatchResponseRootDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.getBatchPayInfoDTO.OneResponseRootDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.RootDTO;
import org.apache.cxf.interceptor.InInterceptors;
import org.apache.cxf.interceptor.OutInterceptors;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "helloWebService",targetNamespace = "http://server.webservice.datanew.com/")
public interface UserService {

    //暴露在webservice接口服务上的方法
    @WebMethod
    public String sayHi();

    @WebMethod
    public BatchResponseRootDTO Get_AppPay_Infos(@WebParam(name = "xml") String xml);

    @WebMethod
    public OneResponseRootDTO Get_AppPay_Info(@WebParam(name = "xml") String xml);

    @WebMethod
    public String testString(@WebParam(name = "ROOT") RootDTO test);
}

