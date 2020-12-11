package com.wangxinenpu.springbootdemo.service.webservice;

import com.wangxinenpu.springbootdemo.dataobject.dto.ParamDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SWPTPayVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SWPTRefundVO;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService(name = "helloWebService",targetNamespace = "http://webservices.insigma.com")
public interface UserService {

    //暴露在webservice接口服务上的方法
    @WebMethod
    public String sayHi();

    @WebMethod
    public SWPTPayVO doPay(@WebParam(name = "ROOT") List<ParamDTO> paramDTOS);
}

