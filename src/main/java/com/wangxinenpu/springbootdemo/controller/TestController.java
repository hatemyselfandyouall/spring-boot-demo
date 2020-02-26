//package com.wangxinenpu.springbootdemo.controller;
//
//import com.wangxinenpu.springbootdemo.dataobject.SysUser;
//import com.wangxinenpu.springbootdemo.service.SysUserService;
//import lombok.extern.slf4j.Slf4j;
//import org.fisco.bcos.channel.client.Service;
//import org.fisco.bcos.web3j.crypto.Credentials;
//import org.fisco.bcos.web3j.crypto.Keys;
//import org.fisco.bcos.web3j.protocol.Web3j;
//import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping(value = "test")
//@Slf4j
//public class TestController {
//
//    @Autowired
//    SysUserService sysUserService;
//
//    @Autowired
//    RedisTemplate redisTemplate;
//
//    @RequestMapping("hello")
//    public String hello(){
//        log.info("hello log");
//        return "hello Spring";
//    }
//
//    @RequestMapping(value = "mybtaisTest",method = RequestMethod.GET)
//    public SysUser mybtaisTest(@RequestParam(value = "id")Integer id){
//        SysUser sysUser=sysUserService.getById(id);
//        return sysUser;
//    }
//
//    @RequestMapping(value = "redisTest",method = RequestMethod.GET)
//    public SysUser redisTest(){
////        for (int i=0;i<10;i++) {
////            redisTemplate.opsForValue().set(i+"","缓存测试"+i);
////        }
//        for (int i=0;i<10;i++){
//            log.info(redisTemplate.opsForValue().get(i+"")+"");
//        }
//        return null;
//    }
//
//    @RequestMapping(value = "testBlockChain",method = RequestMethod.GET)
//    public String testBlockChain(){
////        for (int i=0;i<10;i++) {
////            redisTemplate.opsForValue().set(i+"","缓存测试"+i);
////        }
//        try {
//            ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//            Service service = context.getBean(Service.class);
//            service.run();
//
//            ChannelEthereumService channelEthereumService = new ChannelEthereumService();
//            channelEthereumService.setChannelService(service);
//// 初始化Web3j对象
//            Web3j web3j = Web3j.build(channelEthereumService, 1);
//// 初始化Credentials对象
//            Credentials credentials = Credentials.create(Keys.createEcKeyPair());
//        }catch (Exception e){
//            log.error("测试区块链异常",e);
//            return "测试区块链异常"+e;
//        }
//        return "测试区块链成功";
//    }
//
//    public static void main(String[] args) throws Exception{
//        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        Service service = context.getBean(Service.class);
//        service.run();
//
//        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
//        channelEthereumService.setChannelService(service);
//// 初始化Web3j对象
//        Web3j web3j = Web3j.build(channelEthereumService, 1);
//// 初始化Credentials对象
//        Credentials credentials = Credentials.create(Keys.createEcKeyPair());
//    }
//}
