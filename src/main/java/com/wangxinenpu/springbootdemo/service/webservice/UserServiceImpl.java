package com.wangxinenpu.springbootdemo.service.webservice;




import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.wangxinenpu.springbootdemo.dataobject.vo.getBatchPayInfoDTO.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.IsSuccessDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.RootDTO;
import com.wangxinenpu.springbootdemo.util.XmlLoader;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.interceptor.InInterceptors;
import org.apache.cxf.interceptor.OutInterceptors;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.jws.WebService;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Iterator;

@WebService(serviceName = "helloWebService", //web服务名称
        endpointInterface = "com.wangxinenpu.springbootdemo.service.webservice.UserService",//接口全包名
        targetNamespace = "http://webservices.insigma.com")

@Component
@Slf4j
@InInterceptors(interceptors = { "com.wangxinenpu.springbootdemo.config.webservice.CDATAInInterceptor" })
@OutInterceptors(interceptors = { "com.wangxinenpu.springbootdemo.config.webservice.CDATAOutInterceptor" })
public class UserServiceImpl implements UserService {
    @Override
    public String sayHi() {
        System.out.println("say Hi!");
        return "hello world,spring-boot-ws";
    }

    @Override
    public BatchResponseRootDTO Get_AppPay_Infos(String xml) {
        String PAYERSFZ = "";
        String PAYERMOBILE = "";
        String PAYEREMAIL = "";
        String PAYERDEFINE1 = "";
        String PAYERDEFINE2 = "";
        String ENTERCODE = "";
        String REGICODE = "";
        try {
            Document document = DocumentHelper.parseText(xml);
            Element rootr = document.getRootElement();// �õ����ڵ�
            Element REQUEST = rootr.element("REQUEST");
            for (Iterator<Element> params = REQUEST.elementIterator(); params.hasNext();) {
                Element param = params.next();
                if(param.attributeValue("NAME").equals("PAYERSFZ")) {
                    PAYERSFZ = param.getText();
                }else if(param.attributeValue("NAME").equals("PAYERMOBILE")) {
                    PAYERMOBILE = param.getText();
                }else if(param.attributeValue("NAME").equals("PAYEREMAIL")) {
                    PAYEREMAIL = param.getText();
                }else if(param.attributeValue("NAME").equals("PAYERDEFINE1")) {
                    PAYERDEFINE1 = param.getText();
                }else if(param.attributeValue("NAME").equals("PAYERDEFINE2")) {
                    PAYERDEFINE2 = param.getText();
                }else if(param.attributeValue("NAME").equals("ENTERCODE")) {
                    ENTERCODE = param.getText();
                }else if(param.attributeValue("NAME").equals("REGICODE")) {
                    REGICODE = param.getText();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        RootDTO rootDTO=new RootDTO();
        rootDTO.setREQUEST(new RequestDTO().setPARAM(Arrays.asList(new ParamDTO().setNAMES("PAYERSFZ").setVALUE(PAYERSFZ),
                new ParamDTO().setNAMES("PAYERMOBILE").setVALUE(PAYERMOBILE),
                new ParamDTO().setNAMES("PAYEREMAIL").setVALUE(PAYEREMAIL),
                new ParamDTO().setNAMES("PAYERDEFINE1").setVALUE(PAYERDEFINE1),
                new ParamDTO().setNAMES("PAYERDEFINE2").setVALUE(PAYERDEFINE2),
                new ParamDTO().setNAMES("ENTERCODE").setVALUE(ENTERCODE),
                new ParamDTO().setNAMES("REGICODE").setVALUE(REGICODE)
                )));
        TypeUtils.compatibleWithJavaBean = true;
        JSONObject jsonObject=new JSONObject();
        if (rootDTO.getREQUEST()!=null&&!CollectionUtils.isEmpty(rootDTO.getREQUEST().getPARAM())){
            for (ParamDTO paramDTO:rootDTO.getREQUEST().getPARAM()){
                jsonObject.put(paramDTO.getNAMES(),paramDTO.getVALUE());
            }
        }
        log.info(jsonObject.toJSONString());
        JSONObject temp=new JSONObject();
        JSONObject detail=new JSONObject();
        detail.put("REGICODE","#行政区划编");
        temp.put("Detail",detail.toJSONString());
        return new BatchResponseRootDTO().setERRMSG("").setIS_SUCCESS(new IsSuccessDTO().setCODE("00").setVALUE("成功")).setREQUEST(rootDTO.getREQUEST())
                .setRESPONSE(new BatchResponseDTO().setDETAIL(new BatchResponseResultDTO().setDETAIL(Arrays.asList(new ResponseDetailDTO().setCHANNELNO("1").setNOTICENO("1").setPROJECT(Arrays.asList(new ResponsePayProjectDTO().setBELONGCHITCODE("1").setBELONGCHITNAME("1")))))));
    }

    @Override
    public OneResponseRootDTO Get_AppPay_Info(String xml) {
        String PAYERSFZ = "";
        String PAYERMOBILE = "";
        String PAYEREMAIL = "";
        String PAYERDEFINE1 = "";
        String PAYERDEFINE2 = "";
        String ENTERCODE = "";
        String REGICODE = "";
        try {
            Document document = DocumentHelper.parseText(xml);
            Element rootr = document.getRootElement();// �õ����ڵ�
            Element REQUEST = rootr.element("REQUEST");
            for (Iterator<Element> params = REQUEST.elementIterator(); params.hasNext();) {
                Element param = params.next();
                if(param.attributeValue("NAME").equals("PAYERSFZ")) {
                    PAYERSFZ = param.getText();
                }else if(param.attributeValue("NAME").equals("PAYERMOBILE")) {
                    PAYERMOBILE = param.getText();
                }else if(param.attributeValue("NAME").equals("PAYEREMAIL")) {
                    PAYEREMAIL = param.getText();
                }else if(param.attributeValue("NAME").equals("PAYERDEFINE1")) {
                    PAYERDEFINE1 = param.getText();
                }else if(param.attributeValue("NAME").equals("PAYERDEFINE2")) {
                    PAYERDEFINE2 = param.getText();
                }else if(param.attributeValue("NAME").equals("ENTERCODE")) {
                    ENTERCODE = param.getText();
                }else if(param.attributeValue("NAME").equals("REGICODE")) {
                    REGICODE = param.getText();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        RootDTO rootDTO=new RootDTO();
        rootDTO.setREQUEST(new RequestDTO().setPARAM(Arrays.asList(new ParamDTO().setNAMES("PAYERSFZ").setVALUE(PAYERSFZ),
                new ParamDTO().setNAMES("PAYERMOBILE").setVALUE(PAYERMOBILE),
                new ParamDTO().setNAMES("PAYEREMAIL").setVALUE(PAYEREMAIL),
                new ParamDTO().setNAMES("PAYERDEFINE1").setVALUE(PAYERDEFINE1),
                new ParamDTO().setNAMES("PAYERDEFINE2").setVALUE(PAYERDEFINE2),
                new ParamDTO().setNAMES("ENTERCODE").setVALUE(ENTERCODE),
                new ParamDTO().setNAMES("REGICODE").setVALUE(REGICODE)
        )));
        TypeUtils.compatibleWithJavaBean = true;
        JSONObject jsonObject=new JSONObject();
        if (rootDTO.getREQUEST()!=null&&!CollectionUtils.isEmpty(rootDTO.getREQUEST().getPARAM())){
            for (ParamDTO paramDTO:rootDTO.getREQUEST().getPARAM()){
                jsonObject.put(paramDTO.getNAMES(),paramDTO.getVALUE());
            }
        }
        log.info(jsonObject.toJSONString());
        JSONObject temp=new JSONObject();
        JSONObject detail=new JSONObject();
        detail.put("REGICODE","#行政区划编");
        temp.put("Detail",detail.toJSONString());
        return new OneResponseRootDTO().setERRMSG("123").setIS_SUCCESS(new IsSuccessDTO().setCODE("1").setVALUE("123")).setREQUEST(rootDTO.getREQUEST())
                .setRESPONSE(new OneResponseDTO().setDETAIL(new ResponseDetailDTO().setCHANNELNO("1").setNOTICENO("1").setPROJECT(Arrays.asList(new ResponsePayProjectDTO().setBELONGCHITCODE("1").setBELONGCHITNAME("1")))));

    }

    @Override
    public String testString(RootDTO test) {
        System.out.println(JSONObject.toJSONString(test));
        return null;
    }
}