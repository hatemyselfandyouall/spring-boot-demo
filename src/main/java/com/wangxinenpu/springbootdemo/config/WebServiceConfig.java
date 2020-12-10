//package com.wangxinenpu.springbootdemo.config;
//
//import com.wangxinenpu.springbootdemo.service.UserService;
//import org.apache.cxf.Bus;
//import org.apache.cxf.jaxws.EndpointImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.xml.ws.Endpoint;
//
//
//@Configuration
//public class WebServiceConfig {
//    @Autowired
//    private Bus bus;
//
//    @Autowired
//    private UserService userService;
//
//    @Bean
//    public Endpoint endpoint(){
//        EndpointImpl endpointImpl = new EndpointImpl(bus, userService);
//        endpointImpl.publish("/HelloService");
//        return endpointImpl;
//    }
//}
