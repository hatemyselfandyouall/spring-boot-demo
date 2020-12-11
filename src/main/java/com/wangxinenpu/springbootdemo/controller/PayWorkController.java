package com.wangxinenpu.springbootdemo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.wangxinenpu.springbootdemo.dataobject.dto.ParamDTO;
import com.wangxinenpu.springbootdemo.dataobject.enums.PayStatusEnum;
import com.wangxinenpu.springbootdemo.dataobject.enums.PayWayStatusEnum;
import com.wangxinenpu.springbootdemo.dataobject.po.PayLog;
import com.wangxinenpu.springbootdemo.dataobject.vo.*;
import com.wangxinenpu.springbootdemo.service.PayWorkLogService;
import com.wangxinenpu.springbootdemo.util.JsonXmlUtils;
import com.wangxinenpu.springbootdemo.util.ReportCrawlerPorxy;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.ClientImpl;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.service.model.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import javax.xml.namespace.QName;
import java.io.StringReader;
import java.util.*;


@RestController
@RequestMapping(value = "pay")
@Slf4j
public class PayWorkController {

    @Autowired
    PayWorkLogService payWorkLogService;
    @Autowired
    ReportCrawlerPorxy reportCrawlerPorxy;

    @Value("${SWPT.WEBSERVICE_URL}")
    private String SWPT_WEBSERVICE_URL;
//    @GetMapping(value = "testLocalWebService")
//    public String testLocalWebService() throws Exception {
//        JaxWsDynamicClientFactory clientFactroy = JaxWsDynamicClientFactory.newInstance();
//        Client client = clientFactroy.createClient("http://127.0.0.1:8080/services/HelloService?wsdl");
//        Object[] invoke = client.invoke("sayHi");
//        System.out.println(invoke[0].toString());
//        return "testFinal";
//    }
//
//    @GetMapping(value = "getBasicInfo")
//    public String getBasicInfo() throws Exception {
//        JaxWsDynamicClientFactory clientFactroy = JaxWsDynamicClientFactory.newInstance();
//        Client client = clientFactroy.createClient("http://223.4.68.23:8080/nontax/services/remottingService?wsdl");
////        QName qName=new QName("http://223.4.68.23:8080/nontax/services/remottingService?wsdl","Get_Basic_Info");
//        Object[] invoke = client.invoke("Get_Basic_Info","123456","1234567");
//        System.out.println(invoke[0].toString());
//        return "testFinal";
//    }


    @ApiOperation(value = "税务平台发起支付接口")
    @RequestMapping(value = {"/payInSWPT"}, method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public SWPTPayBackVO getPayInformation(@RequestBody SWPTPayVO swptPayVO){
        try {
            //创建验证信息
            String agencyInfoXML= reportCrawlerPorxy.createAGENCYINFOXML();
            Document headXML = DocumentHelper.parseText(agencyInfoXML);
            //创建内部详细参数
            JSONObject jsonObject=new JSONObject();
            TypeUtils.compatibleWithJavaBean = true;
            jsonObject.put("NOTICEINFO",JSONObject.parseObject(JSONObject.toJSONString(swptPayVO)));
            JSONObject requestJSON=new JSONObject();
            requestJSON.put("REQUEST",jsonObject);
            Document bodyXML= JsonXmlUtils.jsonToDocument(requestJSON);
            Document requestXML = DocumentHelper.createDocument();
            Element rootElement= requestXML.addElement("ROOT");
            rootElement.add(bodyXML.getRootElement());
            rootElement.add(headXML.getRootElement());
            log.info(requestXML.asXML());
            String msg=ReportCrawlerPorxy.getStartPayXml(requestXML.asXML());
            log.info(msg);
            //发起请求
            String result=ReportCrawlerPorxy.invoker(msg,SWPT_WEBSERVICE_URL,null);
            //结果解析
            log.info(result);
            String code=ReportCrawlerPorxy.getCodeFromResult(result);
            Boolean isSuccess=false;
            String errorMsg=ReportCrawlerPorxy.getErrorMsg(result);
            SWPTPayVO returnSWPTPayVO=null;
            if ("00".equals(code)){
                isSuccess=true;
                returnSWPTPayVO=ReportCrawlerPorxy.returnSWPTPayVO(result);
            }
            try {
                PayLog payLog=new PayLog();
                payLog.setAmountOfPayment(Double.valueOf(swptPayVO.getTOTALMONEY())).setReturMsg(result).setSendMsg(swptPayVO+"").setCreateTime(new Date()).setPayWay(PayWayStatusEnum.SWPT);
                if (isSuccess){
                    payLog.setStatus(PayStatusEnum.PAYING);
                }else {
                    payLog.setStatus(PayStatusEnum.FAIL);
                }
                payWorkLogService.insertLog(payLog);
            }catch (Exception e){
                log.error("记录支付日志异常",e);
                //此处记录日志到表，出任何问题都不停止
            }
            return new SWPTPayBackVO().setCode(code).setSuccess(isSuccess).setERRMSG(errorMsg).setSwptPayVO(returnSWPTPayVO);
        }catch (Exception e){
            log.error("调用支付接口异常",e);
            return new SWPTPayBackVO().setSuccess(false).setERRMSG(e.getMessage());
        }

    }


    @ApiOperation(value = "税务平台支付结果主动查询接口")
    @RequestMapping(value = {"/getSWPTPayResult"}, method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public SWPTPayBackVO getPayResult(@RequestBody GetSWPTPayResultVO getSWPTPayResultVO){
        try {
            //创建请求参数
            String msg=ReportCrawlerPorxy.createGetPayResuleMsg(getSWPTPayResultVO);
            //发起请求
            String result=ReportCrawlerPorxy.invoker(msg,SWPT_WEBSERVICE_URL,null);
            //结果解析
            log.info(result);
            String code=ReportCrawlerPorxy.getCodeFromResult(result);
            Boolean isSuccess=false;
            String errorMsg=ReportCrawlerPorxy.getErrorMsg(result);
            SWPTPayVO returnSWPTPayVO=null;
            if ("00".equals(code)){
                isSuccess=true;
                returnSWPTPayVO=ReportCrawlerPorxy.returnSWPTPayVO(result);
            }
            return new SWPTPayBackVO().setCode(code).setSuccess(isSuccess).setERRMSG(errorMsg).setSwptPayVO(returnSWPTPayVO);
        }catch (Exception e){
            log.error("税务平台支付结果主动查询接口异常",e);
            return new SWPTPayBackVO().setSuccess(false).setERRMSG(e.getMessage());
        }
    }


    @ApiOperation(value = "税务平台退款接口")
    @RequestMapping(value = {"/reFoundinSWPT"}, method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public SWPTRefundReturnVO reFoundinSWPT(@RequestBody SWPTRefundVO swptRefundVO){
        try {
            //创建请求参数
            String msg=ReportCrawlerPorxy.createReFoundinSWPTMsg(swptRefundVO);
            //发起请求
            String result=ReportCrawlerPorxy.invoker(msg,SWPT_WEBSERVICE_URL,null);
            //结果解析
            log.info(result);
            String code=ReportCrawlerPorxy.getCodeFromResult(result);
            Boolean isSuccess=false;
            String errorMsg=ReportCrawlerPorxy.getErrorMsg(result);
            SWPTRefundVO returnSWPTPayVO=null;
            if ("00".equals(code)){
                isSuccess=true;
                returnSWPTPayVO=ReportCrawlerPorxy.setSwptRefundVO(result);
            }
            return new SWPTRefundReturnVO().setCode(code).setSuccess(isSuccess).setERRMSG(errorMsg).setSwptRefundVO(returnSWPTPayVO);
        }catch (Exception e){
            log.error("税务平台退款接口异常",e);
            return new SWPTRefundReturnVO().setSuccess(false).setERRMSG(e.getMessage());
        }
    }

    @ApiOperation(value = "税务平台支付结果回调推送接口")
    @RequestMapping(value = {"/callBackSWPTPayResult"}, method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public void callBackSWPTPayResult(@RequestBody SWPTPayBackVO swptPayVO){
        //doto 代码逻辑及返回值处理
        return ;
    }


    @ApiOperation(value = "税务平台退款结果回调推送接口")
    @RequestMapping(value = {"/callBackReFoundinSWPT"}, method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public void reFoundinSWPT(@RequestBody SWPTPayBackVO swptPayVO){
        //doto 代码逻辑及返回值处理
        return ;
    }

}
