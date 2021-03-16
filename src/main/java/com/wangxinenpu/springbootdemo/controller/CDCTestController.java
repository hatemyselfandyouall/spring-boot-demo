package com.wangxinenpu.springbootdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.wangxinenpu.springbootdemo.dataobject.vo.TablePrestatementVO;
import com.wangxinenpu.springbootdemo.util.CDCUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.*;

@RestController
@RequestMapping(value = "cdcTest")
@Slf4j
public class CDCTestController {

    @Value("${cdc.url}")
    private String url;

    @Value("${cdc.userName}")
    private String userName;

    @Value("${cdc.passWord}")
    private String passWord;

//    @Value("${cdc.tables}")
//    private String tables;

    @Value("${cdc.schema}")
    private String schema;

    @Value("${cdc.driver}")
    private String driver;

//    @Value("${cdc.startTime}")
//    private String startTime;

    @Value("${cdc.operations}")
    private String operations;

    @Value("${cdc.target.url}")
    private String target_url;

    @Value("${cdc.target.userName}")
    private String targetUserName;

    @Value("${cdc.target.passWord}")
    private String targetPassword;

    private Connection connection;
//    private Logger logger = Logger.getLogger(CDC.class);
    private Statement statement;

    private Connection targetConnection;



    @RequestMapping(value = "testConnect",method = RequestMethod.POST)
        public String testConnect(@RequestParam(value = "tables",required = false) String tables,
                                  @RequestParam(value = "startTime",required = false)String startTime,
                                  @RequestParam(value = "endTime",required = false)String endTime
        ){
        try {
            log.info("开始测试");
            Class.forName(driver);
            connection = DriverManager.getConnection(url,userName,passWord);
            targetConnection=DriverManager.getConnection(target_url,targetUserName,targetPassword);
            CDCUtils.prepareNLS(connection);
            List<String> archivedFiles=CDCUtils.getArchivedFiles(connection,startTime,endTime);
            if (CollectionUtils.isEmpty(archivedFiles)){
                throw new Exception("未取得日志文件");
            }
            CDCUtils.startLogMnrWithArchivedFiles(connection, archivedFiles);

            statement = connection.createStatement();
            statement.setFetchSize(1000);
            statement.setQueryTimeout(0);

            String queryString=String.format(
                    "SELECT scn,timestamp,operation,seg_owner,table_name,row_id,sql_redo FROM v$logmnr_contents WHERE table_name in (%s) AND seg_owner = %s AND operation IN (%s)",
                    tables,
                    schema,
                    operations
            );
            log.info("调用String为"+queryString);
            ResultSet resultSet = statement
                    .executeQuery(queryString
                    );
            log.info("进入循环");
            int rowCount=0;
//            List<String> test=new ArrayList<>();
            PreparedStatement tempStatement=null;
//            log.info("数据长度为"+resultSet.getFetchSize());
            List<String> redoSQls=new ArrayList<>();
            Map<String, TablePrestatementVO> tablePrestatementVOMap;

            while (resultSet.next()) {
//                    log.info("循环执行");
//
//                    log.info("1");

                targetConnection.prepareStatement("insert into \"SYNC\".\"QMCB_AC03_3308\"(\"AAC001\",\"AAB301\",\"AAC002\",\"AAC003\",\"AAC012\",\"AAB001\",\"BAZ023\",\"AAE140\",\"AAC008\",\"AAC031\",\"BAZ159\",\"AAZ157\",\"AAC049\",\"AAE030\",\"AAE031\") values ('3080000196844570','330881','330823196012010022','杨水香','90','3080000189848455',' ','110','1','3',' ','3080000445861736','198901','19920301','20110101')");
                    String redoSQL=resultSet.getString("sql_redo");
                    if (redoSQL.lastIndexOf(";")==redoSQL.length()-1){
                        redoSQL=redoSQL.split(";")[0];
                    }
//                log.info(redoSQL+"");
                redoSQls.add(redoSQL);
//                tempStatement.addBatch();
//                rowCount++;
//                preparedStatement.executeUpdate();
//                preparedStatement.clearBatch();
//                if (rowCount%1000==0){
//                    tempStatement.executeBatch();
//                    tempStatement.close();
//                    tempStatement=targetConnection.prepareStatement("insert into \"SYNC\".\"QMCB_AC03_3308\"(\"AAC001\",\"AAB301\",\"AAC002\",\"AAC003\",\"AAC012\",\"AAB001\",\"BAZ023\",\"AAE140\",\"AAC008\",\"AAC031\",\"BAZ159\",\"AAZ157\",\"AAC049\",\"AAE030\",\"AAE031\") values ('3080000196844570','330881','330823196012010022','杨水香','90','3080000189848455',' ','110','1','3',' ','3080000445861736','198901','19920301','20110101')");
//                    rowCount=0;
//                    targetConnection.commit();
//                }
            }
//            tempStatement.executeBatch();
//            tempStatement.close();
           log.info("查询完成时间");
        }catch (Exception e){
            log.error("测试cdc异常",e);
        }
        return "hello CDC";
    }
}

