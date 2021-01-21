package com.wangxinenpu.springbootdemo.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class BIUtil {



    public static ResponseEntity<String> getWithParamterMap(String url, Map<String,String> paramMap, RestTemplate restTemplate){
        Set<String> keyset= paramMap.keySet();
        if (!keyset.isEmpty()){
            url=url+"?";
            for (String key:keyset){
                url=url+key+"="+paramMap.get(key)+"&";
            }
            url=url.substring(0,url.length()-1);
        }
        return restTemplate.getForEntity(url,String.class);
    }


    public static ResponseEntity<String> postWithParamterMap(String url, Map<String, Object> paramMap, HttpHeaders headers, RestTemplate restTemplate){
        HttpEntity httpEntity=new HttpEntity(paramMap,headers);
        return restTemplate.postForEntity(url,httpEntity,String.class);
    }



    public static ResponseEntity<String> postWithUrlParam(String url, JSONObject paramMap, HttpHeaders headers, RestTemplate restTemplate) throws UnsupportedEncodingException {
        if (paramMap!=null&&!paramMap.isEmpty()){
            url+="?";
            Set<String> keySet=paramMap.keySet();
            for (String o:keySet){
//                String param=URLEncoder.encode(paramMap.get(o)+"","UTF-8");
                String param=paramMap.get(o)+"";
                url=url+o+"="+param+"&";
            }
            url=url.substring(0,url.length()-1);
        }
        log.info("调用url"+url);
        HttpEntity httpEntity=new HttpEntity(headers);
        return restTemplate.postForEntity(url,httpEntity,String.class);
    }


    public static void main(String[] args) throws IOException {
        RestTemplate restTemplate=new RestTemplate();
        String url="http://10.85.94.238:10540/openapiApp/download?key=bizamt/rdm/15887491648093NX.html";
        ResponseEntity<Resource> entity = restTemplate.getForEntity(url, Resource.class);
        InputStream in = entity.getBody().getInputStream();
        System.out.println(in.available());
    }

}
