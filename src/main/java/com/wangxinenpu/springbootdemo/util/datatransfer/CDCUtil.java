package com.wangxinenpu.springbootdemo.util.datatransfer;

import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.Link.LinkShowVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public class CDCUtil {

    public static ResultVo<DataTransResultVO> doTrans(
            String url, Integer port, String oracleSid, String oracleServerName,
            String userName,
            String passWord,
            String target_url,
            String targetUserName,
            String targetPassword,
            Integer targetPort,
            String targetServerName,
            String targetSid,
            String schema,
            String operations,
            String tables,
            String startTime,
            String endTime,
            String startSCN,
            String typeCode,
            LinkShowVO fromLink, LinkShowVO toLink, Integer isOfflineMode) throws Exception{
//        if ("ORACLE".equals(typeCode.toUpperCase())){
//            return OracleCDCUtils.doTrans(getTargetUrl(url,port,oracleServerName,oracleSid,typeCode),userName,passWord,getTargetUrl(target_url,targetPort,targetServerName,targetSid,typeCode),targetUserName,targetPassword,schema,operations,tables,startTime,endTime,startSCN,fromLink,toLink,isOfflineMode);
//        }else {
            ResultVo resultVo=new ResultVo();
            resultVo.setSuccess(true);
            resultVo.setResult("抽取成功");
            return resultVo;
//        }
    }

    private static  String getTargetUrl(String url,Integer port,String oracleServerName,String oracleSid,String typeCode){
        String connectUrl="";
        String serverName="";
        if (!StringUtils.isEmpty(oracleServerName)){
            serverName=oracleServerName;
            connectUrl = "jdbc:oracle:thin:"   + "@//" + url + ":" +port + "/" + serverName;
        }else {
            serverName=oracleSid;
            connectUrl = "jdbc:oracle:thin:"  +  "@" + url + ":" + port + ":" + serverName;
        }
        return connectUrl;
    }
}
