package com.wangxinenpu.springbootdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.wangxinenpu.springbootdemo.dataobject.vo.TablePrestatementVO;
import com.wangxinenpu.springbootdemo.util.CDCUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static final Pattern insertPattern=Pattern.compile("(?<=\\(')(.*?)(?=')|(?<=',')(.*?)(?=')|NULL|(?<=NULL,')(.*?)(?=')");

    private static final List<String> OPERATIONS=new ArrayList<>();
    static {
        OPERATIONS.add("INSERT");
        OPERATIONS.add("UPDATE");
        OPERATIONS.add("DELETE");
    }

    @RequestMapping(value = "testConnect",method = RequestMethod.POST)
        public String testConnect(@RequestParam(value = "tables",required = false) String tables,
                                  @RequestParam(value = "startTime",required = false)String startTime,
                                  @RequestParam(value = "endTime",required = false)String endTime,
                                  @RequestParam(value="startSCN",required = false) String startSCN
        ){
        try {
            log.info("开始测试");
            Class.forName(driver);
            connection = DriverManager.getConnection(url,userName,passWord);
            targetConnection=DriverManager.getConnection(target_url,targetUserName,targetPassword);
            CDCUtils.prepareNLS(connection);
            List<String> currentFiles=CDCUtils.getCurrentFiles(connection);
            List<String> archivedFiles=CDCUtils.getArchivedFiles(connection,startTime,endTime);
            if (CollectionUtils.isEmpty(archivedFiles)){
                throw new Exception("未取得日志文件");
            }
            archivedFiles.addAll(currentFiles);
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
            if (!StringUtils.isEmpty(startSCN)){
                queryString=queryString+"and scn >"+startSCN;
            }
            if (!StringUtils.isEmpty(startTime)){
                queryString+="and timestamp > to_date('"+ startTime+"', 'yyyy-mm-dd hh24:mi:ss')";
            }
            if (!StringUtils.isEmpty(endTime) ){
                queryString+="and timestamp < to_date('"+ endTime+"', 'yyyy-mm-dd hh24:mi:ss')";
            }
            log.info("调用String为"+queryString);
            ResultSet resultSet = statement
                    .executeQuery(queryString
                    );
            log.info("进入循环");
            int rowCount=0;
            PreparedStatement tempStatement=null;
//            List<String> redoSQls=new ArrayList<>();
            Map<String,Map<String,List<String>>> insertMap=new HashMap<>();
            Map<String, TablePrestatementVO> tablePrestatementVOMap=new HashMap<>();
            /**
             * 此处涉及到一个数据插入的问题，单纯以数据取出为标准，5万条的查询时间为8秒，但是，保存到新的库，5w条的用时就很长了，这里的做法:
             * step1.将数据以5万条为单位放入内存
             * step2.将数据根据所属表分组
             * step3.将表中数据根据根据表-操作类型进行批量操作--这里处于效率考虑，必须使用preStatement，试试这样优化之后的效果吧
                        */
            String lastSCN="";
                while (resultSet.next()) {
                    rowCount++;
                    String redoSQL=resultSet.getString("sql_redo");
                    if (redoSQL.lastIndexOf(";")==redoSQL.length()-1){
                        redoSQL=redoSQL.split(";")[0];
                    }
                    String tableName=resultSet.getString("table_name");
                    String opeartion=resultSet.getString("operation");
                if (insertMap.get(tableName)==null){
                    Map<String,List<String>> operationMap=new HashMap<>();
                    List<String> tempList=new ArrayList<>();
                    tempList.add(redoSQL);
                    operationMap.put(opeartion,tempList);
                    insertMap.put(tableName,operationMap);
                }else {
                    Map<String,List<String>> operationMap=insertMap.get(tableName);
                    if (operationMap.get(opeartion)==null){
                        List<String> tempList=new ArrayList<>();
                        tempList.add(redoSQL);
                        operationMap.put(opeartion,tempList);
                    }else {
                        List<String> redoSqls=operationMap.get(opeartion);
                        redoSqls.add(redoSQL);
                    }
                }
                if (rowCount>=1000){
                    doBatchSave(targetConnection,insertMap,tablePrestatementVOMap);
                    insertMap.clear();
                    rowCount=0;
                }
                    lastSCN=resultSet.getString("scn");
            }
            doBatchSave(targetConnection,insertMap,tablePrestatementVOMap);
           log.info("查询时的最终scn为"+lastSCN);
        }catch (Exception e){
            log.error("测试cdc异常",e);
        }
        return "hello CDC";
    }

    private void doBatchSave(Connection targetConnection, Map<String, Map<String, List<String>>> insertMap, Map<String, TablePrestatementVO> tablePrestatementVOMap) throws SQLException {
        for (String table:insertMap.keySet()){
            for (String opeartion:OPERATIONS){
                List<String> redoSqls=insertMap.get(table).get(opeartion);
                if (CollectionUtils.isEmpty(redoSqls)){
                    continue;
                }
                TablePrestatementVO prestatementVO;
                if (tablePrestatementVOMap.get(table)==null){
                    prestatementVO=new TablePrestatementVO();
                    tablePrestatementVOMap.put(table,prestatementVO);
                }else {
                    prestatementVO=tablePrestatementVOMap.get(table);
                }
                PreparedStatement preparedStatement=null;
                switch (opeartion){
                    case "INSERT":
                        try {
                            if (prestatementVO.getInsertSQL() == null) {
                                createInsertPreSQL(redoSqls.get(0), prestatementVO);
                                System.out.println("test");
                            }
                            preparedStatement = targetConnection.prepareStatement(prestatementVO.getInsertSQL());
                            for (String redoSql : redoSqls) {
                                doSqlSet(redoSql, preparedStatement);
                                preparedStatement.addBatch();
                            }
                            preparedStatement.executeBatch();
                        } catch (BatchUpdateException e){
                            /**
                             * todo statement的速度可比prestatement慢太多了，得想个办法
                             */
                            log.error("批量插入异常,不得不转为使用Statement进行插入，异常为",e);
                            log.error("第"+e.getUpdateCounts().length+"条sql插入异常，插入异常的sql为"+redoSqls.get(+e.getUpdateCounts().length));
                            //todo 这里的策略值得考虑,出错的这条是必须要使用statement保存的，剩余的条数，如果不到10条，就state到结束，反之就使用prestatement好了，治标不如治本，这里没必要进一步优化，优化halder那一部分好了
//                            for (int i=0;i<e.getUpdateCounts().length;i++){
//                                log.error(e.getUpdateCounts()[i]+"");
//                            }
                            for (int i=e.getUpdateCounts().length;i<redoSqls.size();i++){
                                statement=targetConnection.createStatement();
                                statement.executeUpdate(redoSqls.get(i));
                                statement.close();
                            }

                        }
                        if (preparedStatement!=null) {
                            preparedStatement.close();
                        }
                        break;
                    case "UPDATE":
                        /**
                         * to 好像没有好用的修改预编译写法，看看情况吧，应当是能够抽象出来的，但是现在没空
                         */
//                        if (prestatementVO.getUpdateSQL()==null){
//                            createUpadtePreSQL(redoSqls.get(0),prestatementVO);
//                            System.out.println("test");
//                        }
                        for (String redoSql:redoSqls){
                            Statement statement=targetConnection.createStatement();
                            statement.executeUpdate(redoSql);
                            statement.close();
                        }
//                        preparedStatement.executeBatch();
//                        preparedStatement.close();
                        break;
                    case "DELETE":
//                        if (prestatementVO.getDeleteSQL()==null){
//                            createDeletePreSQL(redoSqls.get(0),prestatementVO);
//                            System.out.println("test");
//                        }
//                        preparedStatement=targetConnection.prepareStatement(prestatementVO.getDeleteSQL());
//                        for (String redoSql:redoSqls){
//                            doSqlSet(redoSql,preparedStatement);
//                            preparedStatement.addBatch();
//                        }
//                        preparedStatement.executeBatch();
//                        preparedStatement.close();
                        /**
                         * to 同上，出现效率问题再优化吧
                         */
                        for (String redoSql:redoSqls){
//                            log.info("删除语句"+redoSql);
                            statement=targetConnection.createStatement();
                            statement.executeUpdate(redoSql);
                            statement.close();
                        }
                        break;
                }
            }
        }
    }

    private void createDeletePreSQL(String s, TablePrestatementVO prestatementVO) {
        String  temp=s.replaceAll("(?<=\\(')(.*?)(?=')|(?<=',')(.*?)(?=')|NULL|(?<=NULL,')(.*?)(?=')","?").replaceAll("'","");
        prestatementVO.setDeleteSQL(temp);
    }

    private void createUpadtePreSQL(String s, TablePrestatementVO prestatementVO) {
        String  temp=s.replaceAll("(?<=\\(')(.*?)(?=')|(?<=',')(.*?)(?=')|NULL|(?<=NULL,')(.*?)(?=')","?").replaceAll("'","");
        prestatementVO.setUpdateSQL(temp);
    }

    private void doSqlSet(String redoSql, PreparedStatement preparedStatement) throws SQLException {
        Matcher matcher = insertPattern.matcher(redoSql);
         Integer count=1;
          while (matcher.find()) {
              if ("NULL".equals(matcher.group())){
                  preparedStatement.setNull(count, JDBCType.VARCHAR.getVendorTypeNumber());
              }
           preparedStatement.setString(count,matcher.group());
           count++;
        }
    }

    private void createInsertPreSQL(String s, TablePrestatementVO prestatementVO) {
        String  temp=s.replaceAll("(?<=\\(')(.*?)(?=')|(?<=',')(.*?)(?=')|NULL|(?<=NULL,')(.*?)(?=')","?").replaceAll("'","");
        prestatementVO.setInsertSQL(temp);
    }

    public static void main(String[] args) {
        String test="insert into \"SYNC\".\"QMCB_AC03_3308\"(\"AAC001\",\"AAB301\",\"AAC002\",\"AAC003\",\"AAC012\",\"AAB001\",\"BAZ023\",\"AAE140\",\"AAC008\",\"AAC031\",\"BAZ159\",\"AAZ157\",\"AAC049\",\"AAE030\",\"AAE031\") values ('3333','3333',NULL,'test',NULL,NULL,NULL,'333','1','3',NULL,'333','13','13',NULL)";
//        String teet=""
        Pattern pattern=Pattern.compile("(?<=\\(')(.*?)(?=')|(?<=',')(.*?)(?=')|NULL|(?<=NULL,')(.*?)(?=')");
        System.out.println(test.replaceAll(pattern.pattern(),"?"));
        Matcher matcher=pattern.matcher(test);
        Integer count=1;
        while (matcher.find()){
            System.out.println(matcher.group());
//           for (int i=1;i<=matcher.groupCount();i++){
//               System.out.println(matcher.start(i));
//           }
        }
    }
}

