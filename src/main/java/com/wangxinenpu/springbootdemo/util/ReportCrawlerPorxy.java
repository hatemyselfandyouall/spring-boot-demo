package com.wangxinenpu.springbootdemo.util;



import com.alibaba.fastjson.JSONObject;
import com.wangxinenpu.springbootdemo.dataobject.vo.GetSWPTPayResultVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SWPTPayVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SWPTRefundVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lintc
 * @date 2016年8月17日
 */
@Slf4j
@Component
public  class ReportCrawlerPorxy  {

    @Value("${SWPT.AUTH_CODE}")
    private String AUTH_CODE;

    @Value("${SWPT.TRADECODE}")
    private String TRADECODE;

    @Value("${SWPT.ISBIZTRADE}")
    private String ISBIZTRADE;

    @Value("${SWPT.WAYCODE}")
    private String WAYCODE;

    private static String sentMSG="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://server.remotting.hundsun.com/\">\n" +
            "   <soapenv:Header/>\n" +
            "   <soapenv:Body>\n" +
            "      <ser:Get_Basic_Info>\n" +
            "         <!--Optional:-->\n" +
            "         <ser:RemottingService>\n" +
            "<ROOT>\n" +
            "\t<REQUEST>\n" +
            "<PARAM NAME=\"CHANNELNO\">#来源渠道编码</PARAM>\n" +
            "\t\t<PARAM NAME=\"CHECKCODE\">#校验码</PARAM>\n" +
            "     </REQUEST>\n" +
            "\t<RESPONSE/>\n" +
            "</ROOT>\n" +
            "</ser:RemottingService>\n" +
            "      </ser:Get_Basic_Info>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";

    private static String AGENCYINFO_XML="  <AGENCYINFO>\n" +
            "    <PARAM NAME=\"TRADECODE\">SWPT.TRADECODE</PARAM>\n" +
            "    <PARAM NAME=\"WAYCODE\">SWPT.WAYCODE</PARAM>\n" +
            "    <PARAM NAME=\"ISBIZTRADE\">SWPT.ISBIZTRADE</PARAM> \n" +
            "    <PARAM NAME=\"AUTH_CODE\">SWPT.AUTH_CODE</PARAM> \n" +
            "  </AGENCYINFO>";


    private static String START_PAY_XML="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://server.remotting.hundsun.com/\">\n" +
            "   <soapenv:Header/>\n" +
            "   <soapenv:Body>\n" +
            "      <ser:F2F_Pay>\n" +
            "         <!--Optional:-->\n" +
            "         <ser:RemottingService>\n" +
            "ROOT\n" +
            "</ser:RemottingService>\n" +
            "      </ser:F2F_Pay>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";

    private static String GET_PAY_RESULT_STRING="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://server.remotting.hundsun.com/\">\n" +
            "   <soapenv:Header/>\n" +
            "   <soapenv:Body>\n" +
            "      <ser:GetSearchPay_Info>\n" +
            "         <!--Optional:-->\n" +
            "         <ser:RemottingService>\n" +
            "         <![CDATA[<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<ROOT> \n" +
            "  <REQUEST> \n" +
            "    <PARAM NAME=\"NOTICENO\">#缴款单号</PARAM>  \n" +
            "    <PARAM NAME=\"CHANNELNO\">#缴款单来源渠道编号</PARAM>  \n" +
            "    <PARAM NAME=\"ORIGINALNOTICENO\">#业务单号</PARAM>  \n" +
            "    <PARAM NAME=\"YWCODE\">#业务码</PARAM> \n" +
            "  </REQUEST>  \n" +
            "  <RESPONSE> \n" +
            "</RESPONSE> \n" +
            "</ROOT>\n" +
            "]]>\n" +
            "         </ser:RemottingService>\n" +
            "      </ser:GetSearchPay_Info>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";

    private static String REFOUND_IN_SWPT_STRING="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://server.remotting.hundsun.com/\">\n" +
            "   <soapenv:Header/>\n" +
            "   <soapenv:Body>\n" +
            "      <ser:SetReFund_Info>\n" +
            "         <!--Optional:-->\n" +
            "         <ser:RemottingService><ROOT> \n" +
            "  <REQUEST> \n" +
            "    <PARAM NAME=\"NOTICENO\">(NOTICENO)</PARAM>  \n" +
            "    <PARAM NAME=\"CHANNELNO\">(CHANNELNO)</PARAM>  \n" +
            "    <PARAM NAME=\"ORIGINALNOTICENO\">(ORIGINALNOTICENO)</PARAM>  \n" +
            "    <PARAM NAME=\"YWCODE\">(YWCODE)</PARAM> \n" +
            "  </REQUEST>  \n" +
            "  <RESPONSE>\n" +
            "</RESPONSE> \n" +
            "</ROOT>\n" +
            "</ser:RemottingService>\n" +
            "      </ser:SetReFund_Info>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";

    private static String GET_BASIC_INFO_STRING="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://server.remotting.hundsun.com/\">\n" +
            "   <soapenv:Header/>\n" +
            "   <soapenv:Body>\n" +
            "      <ser:Get_Basic_Info>\n" +
            "         <!--Optional:-->\n" +
            "         <ser:RemottingService><![CDATA[<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "\n" +
            "<ROOT> \n" +
            "  <REQUEST> \n" +
            "    <PARAM NAME=\"CHANNELNO\">channelno</PARAM>  \n" +
            "    <PARAM NAME=\"CHECKCODE\"/> \n" +
            "  </REQUEST>  \n" +
            "  <RESPONSE/> \n" +
            "</ROOT>\n" +
            "         \n" +
            "         \n" +
            "         ]]></ser:RemottingService>\n" +
            "      </ser:Get_Basic_Info>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";

    public static void main(String[] args) throws Exception {
//      String result= invoker(AGENCYINFO_XML,"http://223.4.68.23:8080/nontax/services/remottingService",null);
//      String xml = result.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
//        xml=XmlLoader.getMsgByXML(xml,"<return>","</return>");
//        JSONObject jsonObject = XmlLoader.xml2jsonObj(xml);
//        System.out.println(result);
        String test="<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "   <soap:Body>\n" +
                "      <ns1:F2F_PayResponse xmlns:ns1=\"http://server.remotting.hundsun.com/\">\n" +
                "         <return><![CDATA[<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"yes\"?><ROOT><IS_SUCCESS CODE=\"99\">其他错误</IS_SUCCESS><ERRMSG>公共支付平台：缴款单异常，请稍后重试或联系服务热线</ERRMSG></ROOT>]]></return>\n" +
                "      </ns1:F2F_PayResponse>\n" +
                "   </soap:Body>\n" +
                "</soap:Envelope>";
//        String test="1";
        System.out.println(getCodeFromResult(test));
    }

    public static String getCodeFromResult(String result) throws Exception{
        String xml = result.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
        xml="<ROOT>"+XmlLoader.getMsgByXML(xml,"<ROOT>","</ROOT>")+"</ROOT>";
        Document document= DocumentHelper.parseText(xml);
        Element rootElement=document.getRootElement();
        List<Element> elements=rootElement.elements();
        for (Element element:elements){
            if ("IS_SUCCESS".equals(element.getName())){
               String codeValue= element.attributeValue("CODE");
               return codeValue;
            }
        }
        return "-1";
    }

    public static String getErrorMsg(String result) throws DocumentException {
        String xml = result.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
        xml="<ROOT>"+XmlLoader.getMsgByXML(xml,"<ROOT>","</ROOT>")+"</ROOT>";
        Document document= DocumentHelper.parseText(xml);
        Element rootElement=document.getRootElement();
        List<Element> elements=rootElement.elements();
        for (Element element:elements){
            if ("ERRMSG".equals(element.getName())){
                String errmsg=element.getText();
                return errmsg;
            }
        }
        return "";
    }

    public static JSONObject returnSWPTPayVO(String result)  {
        String xml = result.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
        xml=XmlLoader.getMsgByXML(xml,"<RESULT>","</RESULT>");
        JSONObject jsonObject = XmlLoader.xml2jsonObj(xml);
        return jsonObject;
    }

    /**
     * "    <PARAM NAME=\"NOTICENO\">#缴款单号</PARAM>  \n" +
     *             "    <PARAM NAME=\"CHANNELNO\">#缴款单来源渠道编号</PARAM>  \n" +
     *             "    <PARAM NAME=\"ORIGINALNOTICENO\">#业务单号</PARAM>  \n" +
     *             "    <PARAM NAME=\"YWCODE\">#业务码</PARAM> \n" +
     * @param getSWPTPayResultVO
     * @return
     */
    public static String createGetPayResuleMsg(GetSWPTPayResultVO getSWPTPayResultVO) {
        return GET_PAY_RESULT_STRING.replaceFirst("#缴款单来源渠道编号",getSWPTPayResultVO.getCHANNELNO()!=null?getSWPTPayResultVO.getCHANNELNO():"")
                .replaceFirst("#缴款单号",getSWPTPayResultVO.getNOTICENO()!=null?getSWPTPayResultVO.getNOTICENO():"")
                .replaceFirst("#业务单号",getSWPTPayResultVO.getORIGINALNOTICENO()!=null?getSWPTPayResultVO.getORIGINALNOTICENO():"")
                .replaceFirst("#业务码",getSWPTPayResultVO.getYWCODE()!=null?getSWPTPayResultVO.getYWCODE():"");
    }

    public static String createReFoundinSWPTMsg(SWPTRefundVO swptRefundVO) {
        return REFOUND_IN_SWPT_STRING.replaceFirst("(CHANNELNO)",swptRefundVO.getCHANNELNO())
                .replaceFirst("(NOTICENO)",swptRefundVO.getNOTICENO())
                .replaceFirst("(ORIGINALNOTICENO)",swptRefundVO.getORIGINALNOTICENO())
                .replaceFirst("(YWCODE)",swptRefundVO.getYWCODE());
    }

    public static SWPTRefundVO setSwptRefundVO(String result) {
        String xml = result.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
        xml=XmlLoader.getMsgByXML(xml,"<NOTICEINFO>","</NOTICEINFO>");
        JSONObject jsonObject = XmlLoader.xml2jsonObj(xml);
        SWPTRefundVO swptPayVO=JSONObject.parseObject(jsonObject.toJSONString(),SWPTRefundVO.class);
        return swptPayVO;
    }

    public static String createGetBasicInfoMsg(String channelNO) {
        return GET_BASIC_INFO_STRING.replaceFirst("channelno",channelNO);
    }

    public static JSONObject getBasicInfoDetail(String result) {
        String xml = result.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
        xml="<ROOT>"+XmlLoader.getMsgByXML(xml,"<ROOT>","</ROOT>")+"</ROOT>";
        JSONObject jsonObject = XmlLoader.xml2jsonObj(xml);
        return jsonObject;
    }

    public  String createAGENCYINFOXML(){
        return AGENCYINFO_XML.replaceFirst("SWPT.TRADECODE",TRADECODE).replaceFirst("SWPT.WAYCODE",WAYCODE).replaceFirst("SWPT.ISBIZTRADE",ISBIZTRADE).replaceFirst("SWPT.AUTH_CODE",AUTH_CODE);
    }

    public static String getStartPayXml(String rootString){
        rootString=rootString.replaceFirst("<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>","");
        return START_PAY_XML.replaceFirst("ROOT",rootString);
    }

            /**
             * HTTP请
             *
             * @param msg
             * @param wsdlUrl
             * @param action
             * @return
             */
            public static String invoker(String msg, String wsdlUrl, String action) {
                String xmls = null;
                HttpPost post = new HttpPost(wsdlUrl);

                try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                    HttpEntity entity = new StringEntity(msg, "UTF-8");
                    post.setHeader("Content-Type", "text/xml; charset=utf-8");
                    if (!StringUtils.isEmpty(action)) {
                        post.setHeader("SOAPAction", action);
                    }
                    post.setEntity(entity);
                    HttpResponse response = httpclient.execute(post);
                    xmls = EntityUtils.toString(response.getEntity()).toString();
                } catch (UnsupportedEncodingException e) {
                    log.debug("",e);
                } catch (IOException e) {
                    log.debug("",e);
        }
        return xmls;
    }

    /**
     * wcf的we
     *
     * @return
     */
    public String wcfinvoker(String wsdlUrl, String tempUrl, String action, Map<String, String> param) {

        Service service = new Service();
        Object retVal2 = null;
        try {
            Call call2 = (Call) service.createCall();
            call2.setTargetEndpointAddress(wsdlUrl);
            call2.setUseSOAPAction(true);
//			             call2.setOperationName(new QName("http://tempuri.org/", "GetResultReport"));//设置函数名
            call2.setOperationName(new QName("http://tempuri.org/", action));//设置函数名
//			             call2.setSOAPActionURI("http://tempuri.org/IWReportResult/GetResultReport");//设置URI
            call2.setSOAPActionURI(tempUrl);//设置URI
            List<String> values = new ArrayList<>();
            for (Map.Entry<String, String> entry : param.entrySet()) {
                values.add(entry.getValue());
                call2.addParameter(new QName("http://tempuri.org/", entry.getKey()), XMLType.XSD_STRING, ParameterMode.IN);  // 这里设置对应参数名称
            }
            call2.setReturnClass(String.class);
            retVal2 = call2.invoke(values.toArray());  //调用并带上参数数据
        } catch (Exception e) {
            log.debug("",e);

        }
        if (retVal2 == null) {
            return "";
        }
        return retVal2.toString();
    }
}
