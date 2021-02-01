package com.wangxinenpu.springbootdemo.util;


import com.alibaba.fastjson.JSONObject;
import com.taobao.diamond.extend.DynamicProperties;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

public final class HttpUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);


    public static String upload(String localFile) throws Exception {
        File file = new File(localFile);
        PostMethod filePost = new PostMethod(DynamicProperties.staticProperties.getProperty("oss.upload.http.url"));
        HttpClient client = new HttpClient();

        try {
            // 通过以下方法可以模拟页面参数提交
//            filePost.setParameter("isPublic", "1");

            Part[] parts = { new FilePart("file", file),new StringPart("isPublic", "0") };
            filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
            client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);

            int status = client.executeMethod(filePost);
            if (status == HttpStatus.SC_OK) {
                String response = new String(filePost.getResponseBodyAsString().getBytes("utf-8"));;
                logger.info("response={}",response);
                JSONObject responJSON= JSONObject.parseObject(response);
                logger.info("上传成功"+response);
                return responJSON.getJSONObject("result").getString("key");
            } else {
            	logger.info("上传失败");
            }
        } catch (Exception ex) {
        	logger.error(ex.getMessage());
            ex.printStackTrace();
            throw ex;
        } finally {
            filePost.releaseConnection();
        }
        return "";
    }



    public static void main(String[] args) throws Exception {
        System.out.println(upload("E:\\sign\\PersonCertification.cpt.pdf"));
    }

    public static String upload(MultipartFile file) {
        PostMethod filePost = new PostMethod(DynamicProperties.staticProperties.getProperty("oss.upload.http.url"));
        HttpClient client = new HttpClient();

        try {
            // 通过以下方法可以模拟页面参数提交
//            filePost.setParameter("isPublic", "1");

            Part[] parts = { new FilePart("file", new ByteArrayPartSource(file.getOriginalFilename(),file.getBytes())),new StringPart("isPublic", "0") };
            filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
            client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);

            int status = client.executeMethod(filePost);
            if (status == HttpStatus.SC_OK) {
                String response = new String(filePost.getResponseBodyAsString().getBytes("utf-8"));;
                logger.info("response={}",response);
                JSONObject responJSON= JSONObject.parseObject(response);
                logger.info("上传成功"+response);
                return responJSON.getJSONObject("result").getString("key");
            } else {
                logger.info("上传失败");
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        } finally {
            filePost.releaseConnection();
        }
        return "";
    }

    public static String getIpFromRequest(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
