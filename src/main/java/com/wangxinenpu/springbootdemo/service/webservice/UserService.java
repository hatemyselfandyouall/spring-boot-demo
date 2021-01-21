package com.wangxinenpu.springbootdemo.service.webservice;

import com.wangxinenpu.springbootdemo.dataobject.vo.getBatchPayInfoDTO.BatchResponseRootDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.getBatchPayInfoDTO.OneResponseRootDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.RootDTO;
import org.apache.cxf.interceptor.InInterceptors;
import org.apache.cxf.interceptor.OutInterceptors;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace = "http://server.webservice.datanew.com/")
public interface UserService {

    //暴露在webservice接口服务上的方法
    @WebMethod
    public String sayHi();

    /**
     * 批量获取缴款单
     * @param xml
     * @return
     */
    @WebMethod(operationName = "Get_AppPay_Infos")
    public String Get_AppPay_Infos(@WebParam(name = "xml", targetNamespace = "http://server.webservice.datanew.com/") String xml);

    /**
     * 单体获取缴款单
     * @param xml
     * @return
     */
    @WebMethod(operationName = "Get_AppPay_Info")
    public String Get_AppPay_Info(@WebParam(name = "xml", targetNamespace = "http://server.webservice.datanew.com/") String xml);

    /**
     * 单体获取缴款单
     * @param xml
     * @return
     */
    @WebMethod(operationName = "GetPay_Info")
    public String GetPay_Info(@WebParam(name = "xml", targetNamespace = "http://server.webservice.datanew.com/") String xml);

    @WebMethod(operationName = "testString")
    public String testString(@WebParam(name = "ROOT", targetNamespace = "http://server.webservice.datanew.com/") RootDTO test);

    @WebMethod(operationName = "Get_AppPay_Infos_WithSrring")
    String Get_AppPay_Infos_WithSrring(String requestXml);
}

