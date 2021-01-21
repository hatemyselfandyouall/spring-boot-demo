package com.wangxinenpu.springbootdemo.service.webservice;




import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.ctc.wstx.util.StringUtil;
import com.wangxinenpu.springbootdemo.dataobject.vo.getBatchPayInfoDTO.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.IsSuccessDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.RootDTO;
import com.wangxinenpu.springbootdemo.util.BIUtil;
import com.wangxinenpu.springbootdemo.util.XmlLoader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.interceptor.InInterceptors;
import org.apache.cxf.interceptor.OutInterceptors;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@WebService(serviceName = "helloWebService", //web服务名称
        targetNamespace = "http://server.webservice.datanew.com/")

@Component
@Slf4j
@InInterceptors(interceptors = { "com.wangxinenpu.springbootdemo.config.webservice.CDATAInInterceptor" })
@OutInterceptors(interceptors = { "com.wangxinenpu.springbootdemo.config.webservice.CDATAOutInterceptor" })
public class UserServiceImpl implements UserService {

    @Value("${SJZPT.INTERFACE_URL}")
    private String SJZPTURL;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public String sayHi() {
        System.out.println("say Hi!");
        return "hello world,spring-boot-ws";
    }

    @Override
    public String Get_AppPay_Infos(String xml) {
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
            for (Iterator<Element> params = REQUEST.elementIterator(); params.hasNext(); ) {
                Element param = params.next();
                if (param.attributeValue("NAME").equals("PAYERSFZ")) {
                    PAYERSFZ = param.getText();
                } else if (param.attributeValue("NAME").equals("PAYERMOBILE")) {
                    PAYERMOBILE = param.getText();
                } else if (param.attributeValue("NAME").equals("PAYEREMAIL")) {
                    PAYEREMAIL = param.getText();
                } else if (param.attributeValue("NAME").equals("PAYERDEFINE1")) {
                    PAYERDEFINE1 = param.getText();
                } else if (param.attributeValue("NAME").equals("PAYERDEFINE2")) {
                    PAYERDEFINE2 = param.getText();
                } else if (param.attributeValue("NAME").equals("ENTERCODE")) {
                    ENTERCODE = param.getText();
                } else if (param.attributeValue("NAME").equals("REGICODE")) {
                    REGICODE = param.getText();
                }
            }
            RootDTO rootDTO = new RootDTO();
            rootDTO.setREQUEST(new RequestDTO().setPARAM(Arrays.asList(new ParamDTO().setNAMES("PAYERSFZ").setVALUE(PAYERSFZ),
                    new ParamDTO().setNAMES("PAYERMOBILE").setVALUE(PAYERMOBILE),
                    new ParamDTO().setNAMES("PAYEREMAIL").setVALUE(PAYEREMAIL),
                    new ParamDTO().setNAMES("PAYERDEFINE1").setVALUE(PAYERDEFINE1),
                    new ParamDTO().setNAMES("PAYERDEFINE2").setVALUE(PAYERDEFINE2),
                    new ParamDTO().setNAMES("ENTERCODE").setVALUE(ENTERCODE),
                    new ParamDTO().setNAMES("REGICODE").setVALUE(REGICODE)
            )));
            TypeUtils.compatibleWithJavaBean = true;
            JSONObject jsonObject = new JSONObject();
            if (rootDTO.getREQUEST() != null && !CollectionUtils.isEmpty(rootDTO.getREQUEST().getPARAM())) {
                for (ParamDTO paramDTO : rootDTO.getREQUEST().getPARAM()) {
                    jsonObject.put(paramDTO.getNAMES(), paramDTO.getVALUE());
                }
            }
            log.info(jsonObject.toJSONString());
            //todo 进行http调用业务接口
            Map<String,Object> paramMap =new HashMap<>();
            paramMap.put("json", jsonObject.toJSONString());
            paramMap.put("facade","14101");
            ResponseEntity<String> responseEntity = BIUtil.postWithParamterMap(SJZPTURL,paramMap,null,restTemplate);
            String  returnJSON=responseEntity.getBody();
            BatchResponseRootDTO batchResponseRootDTO = JSONObject.parseObject(returnJSON, BatchResponseRootDTO.class);
            if (!"00".equals(batchResponseRootDTO.getIS_SUCCESS().getCODE())){
                Document dom = DocumentHelper.createDocument();// ����xml�ļ�
                Element root = dom.addElement("ROOT");// ��Ӹ�Ԫ��
                root.addElement("IS_SUCCESS").addAttribute("CODE", batchResponseRootDTO.getIS_SUCCESS().getCODE()).addText(batchResponseRootDTO.getERRMSG());
                log.error("批量获取缴款单失败，返回为"+returnJSON);
                return dom.asXML();
            }
            batchResponseRootDTO.setREQUEST(rootDTO.getREQUEST());
            Document dom = DocumentHelper.createDocument();// ����xml�ļ�
            Element root = dom.addElement("ROOT");// ��Ӹ�Ԫ��
            root.addElement("IS_SUCCESS").addAttribute("CODE", "00").addText("成功");
            root.addElement("ERRMSG").addText("");
            //����REQUEST
            REQUEST = root.addElement("REQUEST");
            REQUEST.addElement("PARAM").addAttribute("NAME", "PAYERSFZ").addText(PAYERSFZ);
            REQUEST.addElement("PARAM").addAttribute("NAME", "PAYERMOBILE").addText(PAYERMOBILE);
            REQUEST.addElement("PARAM").addAttribute("NAME", "PAYEREMAIL").addText(PAYEREMAIL);
            REQUEST.addElement("PARAM").addAttribute("NAME", "PAYERDEFINE1").addText(PAYERDEFINE1);
            REQUEST.addElement("PARAM").addAttribute("NAME", "PAYERDEFINE2").addText(PAYERDEFINE2);
            REQUEST.addElement("PARAM").addAttribute("NAME", "ENTERCODE").addText(ENTERCODE);
            REQUEST.addElement("PARAM").addAttribute("NAME", "REGICODE").addText(REGICODE);
            Element RESULT = root.addElement("RESPONSE").addElement("RESULT");
            //����DETAIL��Ϣ
            Element DETAIL = RESULT.addElement("DETAIL");
            for (ResponseDetailDTO detail : batchResponseRootDTO.getRESPONSE().getRESULT().getDETAIL()) {
                DETAIL.addElement("REGICODE").addText(detail.getREGICODE() != null ? detail.getREGICODE() : "");
                DETAIL.addElement("NOTICENO").addText(detail.getNOTICENO() != null ? detail.getNOTICENO() : "");
                DETAIL.addElement("CHANNELNO").addText(detail.getCHANNELNO() != null ? detail.getCHANNELNO() : "");
                DETAIL.addElement("ORIGINALNOTICENO").addText(detail.getORIGINALNOTICENO() != null ? detail.getORIGINALNOTICENO() : "");
                DETAIL.addElement("PAYER").addText(detail.getPAYER() != null ? detail.getPAYER() : "");
                DETAIL.addElement("PAYERSFZ").addText(detail.getPAYERSFZ() != null ? detail.getPAYERSFZ() : "");
                DETAIL.addElement("MAKEDATE").addText(detail.getMAKEDATE() != null ? detail.getMAKEDATE() : "");
                DETAIL.addElement("MAKETIME").addText(detail.getMAKETIME() != null ? detail.getMAKETIME() : "");
                DETAIL.addElement("TOTALMONEY").addText(detail.getTOTALMONEY() != null ? detail.getTOTALMONEY() : "");
                DETAIL.addElement("YWCODE").addText(detail.getYWCODE() != null ? detail.getYWCODE() : "");
                DETAIL.addElement("ENTERCODE").addText(detail.getENTERCODE() != null ? detail.getENTERCODE() : "");
                DETAIL.addElement("ENTERNAME").addText(detail.getENTERNAME() != null ? detail.getENTERNAME() : "");
                DETAIL.addElement("NOTICEDIS").addText(detail.getNOTICEDIS() != null ? detail.getNOTICEDIS() : "");
                DETAIL.addElement("ISTRUENAME").addText(detail.getISTRUENAME() != null ? detail.getISTRUENAME() : "");
                DETAIL.addElement("EXPIREDPAYDATE").addText(detail.getEXPIREDPAYDATE() != null ? detail.getEXPIREDPAYDATE() : "");
                DETAIL.addElement("EXPIREDPAYTIME").addText(detail.getEXPIREDPAYTIME() != null ? detail.getEXPIREDPAYTIME() : "");
                DETAIL.addElement("ISCOUNTOVERDUE").addText(detail.getISCOUNTOVERDUE() != null ? detail.getISCOUNTOVERDUE() : "");
                DETAIL.addElement("PRINTINFO").addText(detail.getPRINTINFO() != null ? detail.getPRINTINFO() : "");
                DETAIL.addElement("FJ1").addText(detail.getFJ1() != null ? detail.getFJ1() : "");
                DETAIL.addElement("FJCONTENT1").addText(detail.getFJCONTENT1() != null ? detail.getFJCONTENT1() : "");
                DETAIL.addElement("FJ2").addText(detail.getFJ2() != null ? detail.getFJ2() : "");
                DETAIL.addElement("FJCONTENT2").addText(detail.getFJCONTENT2() != null ? detail.getFJCONTENT2() : "");
                DETAIL.addElement("FJ3").addText(detail.getFJ3() != null ? detail.getFJ3() : "");
                DETAIL.addElement("FJCONTENT3").addText(detail.getFJCONTENT3() != null ? detail.getFJCONTENT3() : "");
                DETAIL.addElement("FJ4").addText(detail.getFJ4() != null ? detail.getFJ4() : "");
                DETAIL.addElement("FJCONTENT4").addText(detail.getFJCONTENT4() != null ? detail.getFJCONTENT4() : "");
                DETAIL.addElement("FJ5").addText(detail.getFJ5() != null ? detail.getFJ5() : "");
                DETAIL.addElement("FJCONTENT5").addText(detail.getFJCONTENT5() != null ? detail.getFJCONTENT5() : "");
                //����PROJECT��Ϣ
                for (ResponsePayProjectDTO responsePayProjectDTO : detail.getPROJECT()) {
                    Element PROJECT = DETAIL.addElement("PROJECT");
                    PROJECT.addElement("CHITCODE").addText(responsePayProjectDTO.getCHITCODE() != null ? responsePayProjectDTO.getCHITCODE() : "");
                    PROJECT.addElement("CHITNAME").addText(responsePayProjectDTO.getCHITNAME() != null ? responsePayProjectDTO.getCHITNAME() : "");
                    PROJECT.addElement("METRICUNIT").addText(responsePayProjectDTO.getMETRICUNIT() != null ? responsePayProjectDTO.getMETRICUNIT() : "");
                    PROJECT.addElement("CHCOUNT").addText(responsePayProjectDTO.getCHCOUNT() != null ? responsePayProjectDTO.getCHCOUNT() : "");
                    PROJECT.addElement("CHARGESTANDARD").addText(responsePayProjectDTO.getCHARGESTANDARD() != null ? responsePayProjectDTO.getCHARGESTANDARD() : "");
                    PROJECT.addElement("CHMONEY").addText(responsePayProjectDTO.getCHCOUNT() != null ? responsePayProjectDTO.getCHMONEY() : "");
                    PROJECT.addElement("ISEXISTSOVERDUE").addText(responsePayProjectDTO.getISEXISTSOVERDUE() != null ? responsePayProjectDTO.getISEXISTSOVERDUE() : "");
                    PROJECT.addElement("MAXPAYMENT").addText(responsePayProjectDTO.getMAXPAYMENT() != null ? responsePayProjectDTO.getMAXPAYMENT() : "");
                    PROJECT.addElement("PAYMENTRADIO").addText(responsePayProjectDTO.getPAYMENTRADIO() != null ? responsePayProjectDTO.getPAYMENTRADIO() : "");
                    PROJECT.addElement("STARTCALCDATE").addText(responsePayProjectDTO.getSTARTCALCDATE() != null ? responsePayProjectDTO.getSTARTCALCDATE() : "");
                    PROJECT.addElement("BELONGCHITCODE").addText(responsePayProjectDTO.getBELONGCHITCODE() != null ? responsePayProjectDTO.getBELONGCHITCODE() : "");
                    PROJECT.addElement("BELONGCHITNAME").addText(responsePayProjectDTO.getBELONGCHITNAME() != null ? responsePayProjectDTO.getBELONGCHITNAME() : "");
                }
            }
            return dom.asXML();
        } catch (Exception e) {
            Document dom = DocumentHelper.createDocument();// ����xml�ļ�
            Element root = dom.addElement("ROOT");// ��Ӹ�Ԫ��
            root.addElement("IS_SUCCESS").addAttribute("CODE", "99").addText(e.getMessage());
            log.error("批量获取缴款单异常",e);
            return dom.asXML();
        }
//        return new BatchResponseRootDTO().setERRMSG("").setIS_SUCCESS(new IsSuccessDTO().setCODE("00").setVALUE("成功")).setREQUEST(rootDTO.getREQUEST())
//                .setRESPONSE(new BatchResponseDTO().setRESULT(new BatchResponseResultDTO().setDETAIL(Arrays.asList(new ResponseDetailDTO().setCHANNELNO("1").setNOTICENO("1").setPROJECT(Arrays.asList(new ResponsePayProjectDTO().setBELONGCHITCODE("1").setBELONGCHITNAME("1")))))));
    }

    @Override
    public String Get_AppPay_Info(String xml) {
        String NOTICENO = "";
        String CHANNELNO = "";
        String ORIGINALNOTICENO = "";
        String YWCODE = "";
        xml="<ROOT>"+XmlLoader.getMsgByXML(xml,"<ROOT>","</ROOT>")+"</ROOT>";
        try {
            Document document = DocumentHelper.parseText(xml);
            Element rootr = document.getRootElement();// �õ����ڵ�
            Element REQUEST = rootr.element("REQUEST");
            for (Iterator<Element> params = REQUEST.elementIterator(); params.hasNext();) {
                Element param = params.next();
                if(param.attributeValue("NAME").equals("NOTICENO")) {
                    NOTICENO = param.getText();
                }else if(param.attributeValue("NAME").equals("CHANNELNO")) {
                    CHANNELNO = param.getText();
                }else if(param.attributeValue("NAME").equals("ORIGINALNOTICENO")) {
                    ORIGINALNOTICENO = param.getText();
                }else if(param.attributeValue("NAME").equals("YWCODE")) {
                    YWCODE = param.getText();
                }
            }

        RootDTO rootDTO=new RootDTO();
        rootDTO.setREQUEST(new RequestDTO().setPARAM(Arrays.asList(
                new ParamDTO().setNAMES("NOTICENO").setVALUE(NOTICENO),
                new ParamDTO().setNAMES("CHANNELNO").setVALUE(CHANNELNO),
                new ParamDTO().setNAMES("ORIGINALNOTICENO").setVALUE(ORIGINALNOTICENO),
                new ParamDTO().setNAMES("YWCODE").setVALUE(YWCODE)
        )));
        TypeUtils.compatibleWithJavaBean = true;
        JSONObject jsonObject=new JSONObject();
        if (rootDTO.getREQUEST()!=null&&!CollectionUtils.isEmpty(rootDTO.getREQUEST().getPARAM())){
            for (ParamDTO paramDTO:rootDTO.getREQUEST().getPARAM()){
                jsonObject.put(paramDTO.getNAMES(),paramDTO.getVALUE());
            }
        }
        log.info(jsonObject.toJSONString());
        Map<String,Object> paramMap =new HashMap<>();
        paramMap.put("json", jsonObject.toJSONString());
        paramMap.put("facade","14102");
        ResponseEntity<String> responseEntity = BIUtil.postWithParamterMap(SJZPTURL,paramMap,null,restTemplate);
        String  returnJSON=responseEntity.getBody();
        OneResponseRootDTO oneResponseRootDTO=JSONObject.parseObject(returnJSON,OneResponseRootDTO.class);
        oneResponseRootDTO.setREQUEST(rootDTO.getREQUEST());
        if (!"00".equals(oneResponseRootDTO.getIS_SUCCESS().getCODE())){
            Document dom = DocumentHelper.createDocument();// ����xml�ļ�
            Element root = dom.addElement("ROOT");// ��Ӹ�Ԫ��
            root.addElement("IS_SUCCESS").addAttribute("CODE", oneResponseRootDTO.getIS_SUCCESS().getCODE()).addText(oneResponseRootDTO.getERRMSG());
            log.error("批量获取缴款单失败，返回为"+returnJSON);
            return dom.asXML();
        }
        Document dom = DocumentHelper.createDocument();// ����xml�ļ�
        Element root = dom.addElement("ROOT");// ��Ӹ�Ԫ��
        root.addElement("IS_SUCCESS").addAttribute("CODE", "00").addText("成功");
        root.addElement("ERRMSG").addText("");
        //����REQUES
            REQUEST = root.addElement("REQUEST");
        REQUEST.addElement("PARAM").addAttribute("NAME", "NOTICENO").addText(NOTICENO);
        REQUEST.addElement("PARAM").addAttribute("NAME", "CHANNELNO").addText(CHANNELNO);
        REQUEST.addElement("PARAM").addAttribute("NAME", "ORIGINALNOTICENO").addText(ORIGINALNOTICENO);
        REQUEST.addElement("PARAM").addAttribute("NAME", "YWCODE").addText(YWCODE);
        Element RESULT = root.addElement("RESPONSE").addElement("RESULT");
        //����DETAIL��Ϣ
        Element DETAIL = RESULT.addElement("DETAIL");
        DETAIL.addElement("REGICODE").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getREGICODE()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getREGICODE():"");
        DETAIL.addElement("NOTICENO").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getNOTICENO()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getNOTICENO():"");
        DETAIL.addElement("CHANNELNO").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getCHANNELNO()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getCHANNELNO():"");
        DETAIL.addElement("ORIGINALNOTICENO").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getORIGINALNOTICENO()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getORIGINALNOTICENO():"");
        DETAIL.addElement("PAYER").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getPAYER()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getPAYER():"");
        DETAIL.addElement("PAYERSFZ").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getPAYERSFZ()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getPAYERSFZ():"");
        DETAIL.addElement("MAKEDATE").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getMAKEDATE()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getMAKEDATE():"");
        DETAIL.addElement("MAKETIME").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getMAKETIME()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getMAKETIME():"");
        DETAIL.addElement("TOTALMONEY").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getTOTALMONEY()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getTOTALMONEY():"");
        DETAIL.addElement("YWCODE").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getYWCODE()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getYWCODE():"");
        DETAIL.addElement("ENTERCODE").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getENTERCODE()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getENTERCODE():"");
        DETAIL.addElement("ENTERNAME").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getENTERNAME()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getENTERNAME():"");
        DETAIL.addElement("NOTICEDIS").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getNOTICEDIS()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getNOTICEDIS():"");
        DETAIL.addElement("ISTRUENAME").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getISTRUENAME()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getISTRUENAME():"");
        DETAIL.addElement("EXPIREDPAYDATE").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getEXPIREDPAYDATE()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getEXPIREDPAYDATE():"");
        DETAIL.addElement("EXPIREDPAYTIME").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getEXPIREDPAYTIME()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getEXPIREDPAYTIME():"");
        DETAIL.addElement("ISCOUNTOVERDUE").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getISCOUNTOVERDUE()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getISCOUNTOVERDUE():"");
        DETAIL.addElement("PRINTINFO").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getPRINTINFO()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getPRINTINFO():"");
        DETAIL.addElement("FJ1").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getFJ1()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getFJ1():"");
        DETAIL.addElement("FJCONTENT1").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getFJCONTENT1()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getFJCONTENT1():"");
        DETAIL.addElement("FJ2").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getFJ2()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getFJ2():"");
        DETAIL.addElement("FJCONTENT2").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getFJCONTENT2()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getFJCONTENT2():"");
        DETAIL.addElement("FJ3").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getFJ3()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getFJ3():"");
        DETAIL.addElement("FJCONTENT3").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getFJCONTENT3()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getFJCONTENT3():"");
        DETAIL.addElement("FJ4").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getFJ4()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getFJ4():"");
        DETAIL.addElement("FJCONTENT4").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getFJCONTENT4()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getFJCONTENT4():"");
        DETAIL.addElement("FJ5").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getFJ5()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getFJ5():"");
        DETAIL.addElement("FJCONTENT5").addText(oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getFJCONTENT5()!=null?oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getFJCONTENT5():"");
        //����PROJECT��Ϣ
        for (ResponsePayProjectDTO responsePayProjectDTO:oneResponseRootDTO.getRESPONSE().getRESULT().getDETAIL().getPROJECT()) {
            Element PROJECT = DETAIL.addElement("PROJECT");
            PROJECT.addElement("CHITCODE").addText(responsePayProjectDTO.getCHITCODE()!=null?responsePayProjectDTO.getCHITCODE():"");
            PROJECT.addElement("CHITNAME").addText(responsePayProjectDTO.getCHITNAME()!=null?responsePayProjectDTO.getCHITNAME():"");
            PROJECT.addElement("METRICUNIT").addText(responsePayProjectDTO.getMETRICUNIT()!=null?responsePayProjectDTO.getMETRICUNIT():"");
            PROJECT.addElement("CHCOUNT").addText(responsePayProjectDTO.getCHCOUNT()!=null?responsePayProjectDTO.getCHCOUNT():"");
            PROJECT.addElement("CHARGESTANDARD").addText(responsePayProjectDTO.getCHARGESTANDARD()!=null?responsePayProjectDTO.getCHARGESTANDARD():"");
            PROJECT.addElement("CHMONEY").addText(responsePayProjectDTO.getCHCOUNT()!=null?responsePayProjectDTO.getCHMONEY():"");
            PROJECT.addElement("ISEXISTSOVERDUE").addText(responsePayProjectDTO.getISEXISTSOVERDUE()!=null?responsePayProjectDTO.getISEXISTSOVERDUE():"");
            PROJECT.addElement("MAXPAYMENT").addText(responsePayProjectDTO.getMAXPAYMENT()!=null?responsePayProjectDTO.getMAXPAYMENT():"");
            PROJECT.addElement("PAYMENTRADIO").addText(responsePayProjectDTO.getPAYMENTRADIO()!=null?responsePayProjectDTO.getPAYMENTRADIO():"");
            PROJECT.addElement("STARTCALCDATE").addText(responsePayProjectDTO.getSTARTCALCDATE()!=null?responsePayProjectDTO.getSTARTCALCDATE():"");
            PROJECT.addElement("BELONGCHITCODE").addText(responsePayProjectDTO.getBELONGCHITCODE()!=null?responsePayProjectDTO.getBELONGCHITCODE():"");
            PROJECT.addElement("BELONGCHITNAME").addText(responsePayProjectDTO.getBELONGCHITNAME()!=null?responsePayProjectDTO.getBELONGCHITNAME():"");
        }
            return dom.asXML();
        } catch (Exception e) {
            Document dom = DocumentHelper.createDocument();// ����xml�ļ�
            Element root = dom.addElement("ROOT");// ��Ӹ�Ԫ��
            root.addElement("IS_SUCCESS").addAttribute("CODE", "99").addText(e.getMessage());
            log.error("单体获取缴款单异常",e);
            return dom.asXML();
        }
    }

    @Override
    public String GetPay_Info(String xml) {
        String NOTICENO = "";
        String CHANNELNO = "";
        String ORIGINALNOTICENO = "";
        String PAYLISTNO = "";
        String PAYNO = "";
        String CERTIFICATENO = "";
        String YWCODE = "";
        String TRADECODE = "";
        String TRADENAME = "";
        String WAYCODE = "";
        String WAYNAME = "";
        String TRADENO = "";
        String TRADEDATE = "";
        String CHECKCODE = "";
        String PAYMONEY = "";
        String PAYDATE = "";
        String PAYTIME = "";
        try {
            Document document = DocumentHelper.parseText(xml);
            Element rootr = document.getRootElement();// �õ����ڵ�
            Element REQUEST = rootr.element("REQUEST");
            for (Iterator<Element> params = REQUEST.elementIterator(); params.hasNext();) {
                Element param = params.next();
                if(param.attributeValue("NAME").equals("NOTICENO")) {
                    NOTICENO = param.getText();
                }else if(param.attributeValue("NAME").equals("CHANNELNO")) {
                    CHANNELNO = param.getText();
                }else if(param.attributeValue("NAME").equals("ORIGINALNOTICENO")) {
                    ORIGINALNOTICENO = param.getText();
                }else if(param.attributeValue("NAME").equals("PAYLISTNO")) {
                    PAYLISTNO = param.getText();
                }else if(param.attributeValue("NAME").equals("PAYNO")) {
                    PAYNO = param.getText();
                }else if(param.attributeValue("NAME").equals("CERTIFICATENO")) {
                    CERTIFICATENO = param.getText();
                }else if(param.attributeValue("NAME").equals("YWCODE")) {
                    YWCODE = param.getText();
                }else if(param.attributeValue("NAME").equals("TRADECODE")) {
                    TRADECODE = param.getText();
                }else if(param.attributeValue("NAME").equals("TRADENAME")) {
                    TRADENAME = param.getText();
                }else if(param.attributeValue("NAME").equals("WAYCODE")) {
                    WAYCODE = param.getText();
                }else if(param.attributeValue("NAME").equals("WAYNAME")) {
                    WAYNAME = param.getText();
                }else if(param.attributeValue("NAME").equals("TRADENO")) {
                    TRADENO = param.getText();
                }else if(param.attributeValue("NAME").equals("TRADEDATE")) {
                    TRADEDATE = param.getText();
                }else if(param.attributeValue("NAME").equals("CHECKCODE")) {
                    CHECKCODE = param.getText();
                }if(param.attributeValue("NAME").equals("PAYMONEY")) {
                    PAYMONEY = param.getText();
                }if(param.attributeValue("NAME").equals("PAYDATE")) {
                    PAYDATE = param.getText();
                }if(param.attributeValue("NAME").equals("PAYTIME")) {
                    PAYTIME = param.getText();
                }
            }

        RootDTO rootDTO=new RootDTO();
        rootDTO.setREQUEST(new RequestDTO().setPARAM(Arrays.asList(new ParamDTO().setNAMES("NOTICENO").setVALUE(NOTICENO),
                new ParamDTO().setNAMES("CHANNELNO").setVALUE(CHANNELNO),
                new ParamDTO().setNAMES("ORIGINALNOTICENO").setVALUE(ORIGINALNOTICENO),
                new ParamDTO().setNAMES("PAYLISTNO").setVALUE(PAYLISTNO),
                new ParamDTO().setNAMES("PAYNO").setVALUE(PAYNO),
                new ParamDTO().setNAMES("CERTIFICATENO").setVALUE(CERTIFICATENO),
                new ParamDTO().setNAMES("YWCODE").setVALUE(YWCODE),
                new ParamDTO().setNAMES("TRADECODE").setVALUE(TRADECODE),
                new ParamDTO().setNAMES("TRADENAME").setVALUE(TRADENAME),
                new ParamDTO().setNAMES("WAYCODE").setVALUE(WAYCODE),
                new ParamDTO().setNAMES("WAYNAME").setVALUE(WAYNAME),
                new ParamDTO().setNAMES("TRADENO").setVALUE(TRADENO),
                new ParamDTO().setNAMES("TRADEDATE").setVALUE(TRADEDATE),
                new ParamDTO().setNAMES("CHECKCODE").setVALUE(CHECKCODE),
                new ParamDTO().setNAMES("PAYMONEY").setVALUE(PAYMONEY),
                new ParamDTO().setNAMES("PAYDATE").setVALUE(PAYDATE),
                new ParamDTO().setNAMES("PAYTIME").setVALUE(PAYTIME)
                )));
        JSONObject jsonObject=new JSONObject();
        if (rootDTO.getREQUEST()!=null&&!CollectionUtils.isEmpty(rootDTO.getREQUEST().getPARAM())){
            for (ParamDTO paramDTO:rootDTO.getREQUEST().getPARAM()){
                jsonObject.put(paramDTO.getNAMES(),paramDTO.getVALUE());
            }
        }
        log.info("支付回调接口接收参数"+jsonObject.toJSONString());
        Map<String,Object> paramMap =new HashMap<>();
        paramMap.put("json", jsonObject.toJSONString());
        paramMap.put("facade","14201");
        log.info("进行业务回调参数未"+JSONObject.toJSONString(paramMap));
        ResponseEntity<String> responseEntity = BIUtil.postWithParamterMap(SJZPTURL,paramMap,null,restTemplate);
        String  returnJSON=responseEntity.getBody();
        log.info("调用业务接口通知回调，获取业务返回为"+returnJSON);
        Document dom = DocumentHelper.createDocument();// ����xml�ļ�
        Element root = dom.addElement("ROOT");// ��Ӹ�Ԫ��
        root.addElement("IS_SUCCESS").addAttribute("CODE", "00").addText("成功");
        root.addElement("ERRMSG").addText("");
        //����REQUEST
             REQUEST = root.addElement("REQUEST");
        REQUEST.addElement("PARAM").addAttribute("NAME", "NOTICENO").addText(NOTICENO);
        REQUEST.addElement("PARAM").addAttribute("NAME", "CHANNELNO").addText(CHANNELNO);
        REQUEST.addElement("PARAM").addAttribute("NAME", "ORIGINALNOTICENO").addText(ORIGINALNOTICENO);
        REQUEST.addElement("PARAM").addAttribute("NAME", "YWCODE").addText(YWCODE);
        REQUEST.addElement("PARAM").addAttribute("NAME", "PAYLISTNO").addText(PAYLISTNO);
        REQUEST.addElement("PARAM").addAttribute("NAME", "PAYNO").addText(PAYNO);
        REQUEST.addElement("PARAM").addAttribute("NAME", "CERTIFICATENO").addText(CERTIFICATENO);
        REQUEST.addElement("PARAM").addAttribute("NAME", "TRADECODE").addText(TRADECODE);
        REQUEST.addElement("PARAM").addAttribute("NAME", "TRADENAME").addText(TRADENAME);
        REQUEST.addElement("PARAM").addAttribute("NAME", "WAYCODE").addText(WAYCODE);
        REQUEST.addElement("PARAM").addAttribute("NAME", "WAYNAME").addText(WAYNAME);
        REQUEST.addElement("PARAM").addAttribute("NAME", "TRADENO").addText(TRADENO);
        REQUEST.addElement("PARAM").addAttribute("NAME", "TRADEDATE").addText(TRADEDATE);
        REQUEST.addElement("PARAM").addAttribute("NAME", "CHECKCODE").addText(CHECKCODE);
        REQUEST.addElement("PARAM").addAttribute("NAME", "PAYMONEY").addText(PAYMONEY);
        REQUEST.addElement("PARAM").addAttribute("NAME", "PAYDATE").addText(PAYDATE);
        REQUEST.addElement("PARAM").addAttribute("NAME", "PAYTIME").addText(PAYTIME);
        return dom.asXML();
        } catch (Exception e) {
            Document dom = DocumentHelper.createDocument();// ����xml�ļ�
            Element root = dom.addElement("ROOT");// ��Ӹ�Ԫ��
            root.addElement("IS_SUCCESS").addAttribute("CODE", "99").addText(e.getMessage());
            log.error("批量获取缴款单异常",e);
            return dom.asXML();
        }
    }


    @Override
    public String testString(RootDTO test) {
        System.out.println(JSONObject.toJSONString(test));
        return null;
    }

    public String Get_AppPay_Infos_WithSrring(String requestXml) {
        //����xml����
        String PAYERSFZ = "";
        String PAYERMOBILE = "";
        String PAYEREMAIL = "";
        String PAYERDEFINE1 = "";
        String PAYERDEFINE2 = "";
        String ENTERCODE = "";
        String REGICODE = "";
        try {
            Document document = DocumentHelper.parseText(requestXml);
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
        /////////////����ҵ��/////////



        ////////////����xml������//////
        Document dom = DocumentHelper.createDocument();// ����xml�ļ�
        Element root = dom.addElement("ROOT");// ��Ӹ�Ԫ��
        root.addElement("IS_SUCCESS").addAttribute("CODE", "").addText("");
        root.addElement("ERRMSG").addText("");
        //����REQUEST
        Element request = root.addElement("REQUEST");
        request.addElement("PARAM").addAttribute("NAME", "PAYERSFZ").addText(PAYERSFZ);
        request.addElement("PARAM").addAttribute("NAME", "PAYERMOBILE").addText(PAYERMOBILE);
        request.addElement("PARAM").addAttribute("NAME", "PAYEREMAIL").addText(PAYEREMAIL);
        request.addElement("PARAM").addAttribute("NAME", "PAYERDEFINE1").addText(PAYERDEFINE1);
        request.addElement("PARAM").addAttribute("NAME", "PAYERDEFINE2").addText(PAYERDEFINE2);
        request.addElement("PARAM").addAttribute("NAME", "ENTERCODE").addText(ENTERCODE);
        request.addElement("PARAM").addAttribute("NAME", "REGICODE").addText(REGICODE);
        Element RESULT = root.addElement("RESPONSE").addElement("RESULT");
        //����DETAIL��Ϣ
        Element DETAIL = RESULT.addElement("DETAIL");
        DETAIL.addElement("REGICODE").addText(""!=null?"":"");
        DETAIL.addElement("NOTICENO").addText(""!=null?"":"");
        DETAIL.addElement("CHANNELNO").addText(""!=null?"":"");
        DETAIL.addElement("ORIGINALNOTICENO").addText(""!=null?"":"");
        DETAIL.addElement("PAYER").addText(""!=null?"":"");
        DETAIL.addElement("PAYERSFZ").addText(""!=null?"":"");
        DETAIL.addElement("MAKEDATE").addText(""!=null?"":"");
        DETAIL.addElement("MAKETIME").addText(""!=null?"":"");
        DETAIL.addElement("TOTALMONEY").addText(""!=null?"":"");
        DETAIL.addElement("YWCODE").addText(""!=null?"":"");
        DETAIL.addElement("ENTERCODE").addText(""!=null?"":"");
        DETAIL.addElement("ENTERNAME").addText(""!=null?"":"");
        DETAIL.addElement("NOTICEDIS").addText(""!=null?"":"");
        DETAIL.addElement("ISTRUENAME").addText(""!=null?"":"");
        DETAIL.addElement("EXPIREDPAYDATE").addText(""!=null?"":"");
        DETAIL.addElement("EXPIREDPAYTIME").addText(""!=null?"":"");
        DETAIL.addElement("ISCOUNTOVERDUE").addText(""!=null?"":"");
        DETAIL.addElement("PRINTINFO").addText(""!=null?"":"");
        DETAIL.addElement("FJ1").addText(""!=null?"":"");
        DETAIL.addElement("FJCONTENT1").addText(""!=null?"":"");
        DETAIL.addElement("FJ2").addText(""!=null?"":"");
        DETAIL.addElement("FJCONTENT2").addText(""!=null?"":"");
        DETAIL.addElement("FJ3").addText(""!=null?"":"");
        DETAIL.addElement("FJCONTENT3").addText(""!=null?"":"");
        DETAIL.addElement("FJ4").addText(""!=null?"":"");
        DETAIL.addElement("FJCONTENT4").addText(""!=null?"":"");
        DETAIL.addElement("FJ5").addText(""!=null?"":"");
        DETAIL.addElement("FJCONTENT5").addText(""!=null?"":"");
        //����PROJECT��Ϣ
        Element PROJECT = DETAIL.addElement("PROJECT");
        PROJECT.addElement("CHITCODE").addText(""!=null?"":"");
        PROJECT.addElement("CHITNAME").addText(""!=null?"":"");
        PROJECT.addElement("METRICUNIT").addText(""!=null?"":"");
        PROJECT.addElement("CHCOUNT").addText(""!=null?"":"");
        PROJECT.addElement("CHARGESTANDARD").addText(""!=null?"":"");
        PROJECT.addElement("CHMONEY").addText(""!=null?"":"");
        PROJECT.addElement("ISEXISTSOVERDUE").addText(""!=null?"":"");
        PROJECT.addElement("MAXPAYMENT").addText(""!=null?"":"");
        PROJECT.addElement("PAYMENTRADIO").addText(""!=null?"":"");
        PROJECT.addElement("STARTCALCDATE").addText(""!=null?"":"");
        PROJECT.addElement("BELONGCHITCODE").addText(""!=null?"":"");
        PROJECT.addElement("BELONGCHITNAME").addText(""!=null?"":"");
        return dom.asXML();
    }

    public static void main(String[] args) {
        String test="{\n" +
                "\t\"RESPONSE\": {\n" +
                "\t\t\"RESULT\": {\n" +
                "\t\t\t\"DETAIL\": {\n" +
                "\t\t\t\t\"PROJECT\": [{\n" +
                "\t\t\t\t\t\"METRICUNIT\": \"元\",\n" +
                "\t\t\t\t\t\"CHARGESTANDARD\": \"2\",\n" +
                "\t\t\t\t\t\"CHCOUNT\": \"1\",\n" +
                "\t\t\t\t\t\"CHMONEY\": \"0.01\",\n" +
                "\t\t\t\t\t\"CHITCODE\": \"00034002\",\n" +
                "\t\t\t\t\t\"CHITNAME\": \"森林植被恢复费\"\n" +
                "\t\t\t\t}],\n" +
                "\t\t\t\t\"MAKEDATE\": \"20201208\",\n" +
                "\t\t\t\t\"NOTICEDIS\": \"6\",\n" +
                "\t\t\t\t\"ISCOUNTOVERDUE\": \"0\",\n" +
                "\t\t\t\t\"PAYER\": \"陶爱香\",\n" +
                "\t\t\t\t\"PAYERSFZ\": \"330623197006188223\",\n" +
                "\t\t\t\t\"REGICODE\": \"330602\",\n" +
                "\t\t\t\t\"NOTICENO\": \"330001296FS0001934251\",\n" +
                "\t\t\t\t\"ORIGINALNOTICENO\": \"FS0001934251\",\n" +
                "\t\t\t\t\"CHANNELNO\": \"330001296\",\n" +
                "\t\t\t\t\"ENTERNAME\": \"省本级\",\n" +
                "\t\t\t\t\"ISTRUENAME\": \"0\",\n" +
                "\t\t\t\t\"TOTALMONEY\": \"0.01\",\n" +
                "\t\t\t\t\"ENTERCODE\": \"000368\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t\"IS_SUCCESS\": {\n" +
                "\t\t\"CODE\": \"00\",\n" +
                "\t\t\"VALUE\": \"成功\"\n" +
                "\t}\n" +
                "}";
        OneResponseRootDTO oneResponseRootDTO=JSONObject.parseObject(test,OneResponseRootDTO.class);
        System.out.println(oneResponseRootDTO);
    }
}