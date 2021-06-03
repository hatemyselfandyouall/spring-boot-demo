package com.wangxinenpu.springbootdemo.util.statuCheck;

import com.alibaba.fastjson.JSONObject;
import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.table.TableInfo;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.ResultVo;
import org.apache.poi.ss.usermodel.Workbook;


import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class FullExtractStatusConfirmer {
    public static void main(String[] args) throws Exception {
        String toUrl="jdbc:oracle:thin:@172.16.98.100:1521:ORCL";
        String toUserName="SYNC";
        String toPassWord="SYNC";
        String excelPath="F:\\数据回流项目部署准备\\富阳数据回流清单反馈.xlsx";
        ResultVo<List<TableInfo>> resultVo= ExcelUtil.testConnect(excelPath);
        TableInfo tableInfo=resultVo.getResult().get(0);
        Properties props = new Properties() ;
        props.put( "oracle.net.CONNECT_TIMEOUT" , "10000000") ;
        props.put( "user" , toUserName) ;
        props.put( "password" , toPassWord) ;
        Connection toConnection=DriverManager.getConnection(toUrl, props);
        Integer totalNeedHave=0;
        Integer totalRealHave=0;
        List<String> titles= Arrays.asList("用户名","表英文名","表中文名","应有条数","实有条数","备注");
        String SheetName="test";
        List<List<String>> columns=new ArrayList<>();
        for (JSONObject jsonObject:tableInfo.getTableDatas()) {
            String tableName=jsonObject.getString("表英文名");
            String userName=jsonObject.getString("用户名");
            String chineseName=jsonObject.getString("表中文名");
            if (chineseName.contains("不存在")){
                columns.add(Arrays.asList(userName,tableName,jsonObject.getString("表中文名"),0+"",0+"","不存在的表"));
                continue;
            }
            String fullTableName=userName+"."+tableName;
           ResultSet resultSet=toConnection.createStatement().executeQuery("select count(*) from "+fullTableName);
            if (resultSet.next()) {
               Integer realHaveCount = resultSet.getInt("count(*)");
               if (realHaveCount>1000000){
                   System.out.println("drop table "+fullTableName+" purge;");
               }else {
                   if (realHaveCount>0){
                       System.out.println("delete from "+fullTableName+";");
                   }
               }

            }
        }
//        doCompare();
//        compareOneTable();
    }

    public static void compareOneTable() throws Exception{
        Long startTime=System.currentTimeMillis();
        System.out.println(startTime);
        Integer lastCount=0;
        while (true) {
            String fromUrl = "jdbc:oracle:thin:@//172.16.81.11:1521/hzrsrac";
            String fromUserName = "sjhl_fy";
            String fromPassWord = "sjhl_pwdfy21";
            String toUrl = "jdbc:oracle:thin:@172.16.98.101:1521:ORCL";
            String toUserName = "SYNC";
            String toPassWord = "SYNC";
            Properties props = new Properties();
            props.put("user", fromUserName);
            props.put("password", fromPassWord);
            props.put("oracle.net.CONNECT_TIMEOUT", "10000000");
            Connection fromConnection = DriverManager.getConnection(fromUrl, props);
            props.put("user", toUserName);
            props.put("password", toPassWord);
            Connection toConnection = DriverManager.getConnection(toUrl, props);
            Integer needHaveCount = 0;
            Integer realHaveCount = 0;
            String tableName = "AC05";
            String userName = "EMPQUERY";
            String fullTableName = userName + "." + tableName;
            String FileName = userName + "_" + tableName;
            String querySQL = "select count(*) from " + fullTableName + " where aab301='330183'";
            ResultSet resultSet = fromConnection.createStatement().executeQuery(querySQL);
            if (resultSet.next()) {
                needHaveCount = resultSet.getInt("count(*)");
            }
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }
            resultSet = toConnection.createStatement().executeQuery("select count(*) from " + fullTableName);
            if (resultSet.next()) {
                realHaveCount = resultSet.getInt("count(*)");
            }
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }
//                if ((needHaveCount-realHaveCount)>2000&&realHaveCount!=0) {
            if (System.currentTimeMillis() - startTime > 60000) {
                System.out.println("表" + fullTableName + "应有数据" + needHaveCount + "，实有数据" + realHaveCount + "差值为" + (needHaveCount - realHaveCount));
                System.out.println("增加条数" + ((needHaveCount - realHaveCount)-lastCount));
                lastCount=(needHaveCount - realHaveCount);
            }
        }
    }
    public static void  doCompare()throws Exception{
        String fromUrl="jdbc:oracle:thin:@//172.16.81.11:1521/hzrsrac";
        String fromUserName="sjhl_fy";
        String fromPassWord="sjhl_pwdfy21";
        String toUrl="jdbc:oracle:thin:@172.16.98.101:1521:ORCL";
        String toUserName="SYNC";
        String toPassWord="SYNC";
        String excelPath="F:\\数据回流项目部署准备\\富阳数据回流清单反馈.xlsx";
        ResultVo<List<TableInfo>> resultVo= ExcelUtil.testConnect(excelPath);
        TableInfo tableInfo=resultVo.getResult().get(0);
        Properties props = new Properties() ;
        props.put( "user" , fromUserName) ;
        props.put( "password" , fromPassWord) ;
        props.put( "oracle.net.CONNECT_TIMEOUT" , "10000000") ;
        Connection fromConnection= DriverManager.getConnection(fromUrl, props);
        props.put( "user" , toUserName) ;
        props.put( "password" , toPassWord) ;
        Connection toConnection=DriverManager.getConnection(toUrl, props);
        Integer totalNeedHave=0;
        Integer totalRealHave=0;
        List<String> titles= Arrays.asList("用户名","表英文名","表中文名","应有条数","实有条数","备注");
        String SheetName="test";
        List<List<String>> columns=new ArrayList<>();
        for (JSONObject jsonObject:tableInfo.getTableDatas()){
            try {
                Integer needHaveCount=0;
                Integer realHaveCount=0;
                String tableName=jsonObject.getString("表英文名");
                String userName=jsonObject.getString("用户名");
                String chineseName=jsonObject.getString("表中文名");
                if (chineseName.contains("不存在")){
                    columns.add(Arrays.asList(userName,tableName,jsonObject.getString("表中文名"),0+"",0+"","不存在的表"));
                    continue;
                }
                String fullTableName=userName+"."+tableName;
                String FileName=userName+"_"+tableName;
                String querySQL=jsonObject.getString("富阳过滤sql");

                columns.add(titles);
                if ("aab301='330183'".equals(querySQL)){
                    querySQL="select * from "+fullTableName+" where aab301='330183'";
                }else {
                    querySQL=querySQL.replace("*","* ").replaceAll("'","'").replace("where"," where ");
                }
                if (querySQL.contains("全部")){
                    querySQL="select * from "+fullTableName;
                }
                querySQL=querySQL.replace("*","count(*)");
                ResultSet resultSet=fromConnection.createStatement().executeQuery(querySQL);
                if (resultSet.next()) {
                    needHaveCount = resultSet.getInt("count(*)");
                }
                if (resultSet!=null&&!resultSet.isClosed()){
                    resultSet.close();
                }
                resultSet=toConnection.createStatement().executeQuery("select count(*) from "+fullTableName);
                if (resultSet.next()) {
                    realHaveCount = resultSet.getInt("count(*)");
                }
                if (resultSet!=null&&!resultSet.isClosed()){
                    resultSet.close();
                }
                totalNeedHave+=needHaveCount;
                totalRealHave+=realHaveCount;
//                if ((needHaveCount-realHaveCount)>2000&&realHaveCount!=0) {
                System.out.println("表" + fullTableName + "应有数据" + needHaveCount + "，实有数据" + realHaveCount + "差值为" + (needHaveCount - realHaveCount));
//                }
                columns.add(Arrays.asList(userName,tableName,jsonObject.getString("表中文名"),needHaveCount+"",realHaveCount+"","全量完成之后的用户操作，会导致数据量产生偏差，增量上线后会解决"));
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }

        }
//        Workbook workbook=ExcelHelper.createExcleFile(SheetName,columns);
//        workbook.write(new FileOutputStream(new File("F:\\数据回流项目部署准备\\全量抽取结果.xls")));
        System.out.println("总计应有数据"+totalNeedHave+"总计实有数据"+totalRealHave);

    }
}
