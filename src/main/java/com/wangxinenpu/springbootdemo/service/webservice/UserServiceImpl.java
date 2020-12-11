package com.wangxinenpu.springbootdemo.service.webservice;




import com.wangxinenpu.springbootdemo.dataobject.dto.ParamDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SWPTPayVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SWPTRefundVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService(serviceName = "helloWebService", //web服务名称
        endpointInterface = "com.wangxinenpu.springbootdemo.service.webservice.UserService",//接口全包名
        targetNamespace = "http://webservices.insigma.com")

@Component
@Slf4j
public class UserServiceImpl implements UserService {
    @Override
    public String sayHi() {
        System.out.println("say Hi!");
        return "hello world,spring-boot-ws";
    }

    @Override
    public SWPTPayVO doPay(List<ParamDTO> paramDTOList) {
        log.info(paramDTOList+"");
        return null;
    }
}