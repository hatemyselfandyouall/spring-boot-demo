package com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.CDCCache;

import com.wangxinenpu.springbootdemo.dao.mapper.LinkTransferTaskErrorRecordMapper;
import com.wangxinenpu.springbootdemo.config.ExceptionWriteCompoent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class SQLSaver {
//
    @Value("${cdc.to.linkurl}")
    private String toLinkUrl;
    @Value("${cdc.to.username}")
    private String cdctousername;
    @Value("${cdc.to.password}")
    private String cdctopassword;

    private Connection connection;
    private Statement statement;

    public String recordSql="";
    public Long recordSCN=null;
    @Autowired
    ExceptionWriteCompoent exceptionWriteCompoent;

    public static final Map<String, TreeMap<Long, String>> tableCacheMap=new HashMap<>();
    public static final LinkedBlockingDeque<SaveTask> taskQueue=new LinkedBlockingDeque<>();

    @PostConstruct
    private void init(){
        //todo initlist
         new ThreadPoolExecutor(
                4,9,10000l, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                SoulThreadFactory.create("save-upstream-task", false))
                .execute(new Worker());
    }

    public static void save(String tableName, String sql, String tableStatus, Long scn, String timeStamp){
        if (tableStatus.equals( MSGTYPECONSTANT.TABLE_STATUS_ISFULL_EXTRACT)){
            TreeMap<Long, String> tableSQLMap=tableCacheMap.get(tableName);
            if (CollectionUtils.isEmpty(tableSQLMap)){
                tableSQLMap=new TreeMap<>();
            }
            tableSQLMap.put(scn,sql);
            tableCacheMap.put(tableName,tableSQLMap);
        }else {
            SaveTask saveTask=new SaveTask(scn,sql,timeStamp);
            taskQueue.add(saveTask);
        }
    }

    public  void  executeSQLs(String tableName, Connection connection) {
        log.info("开始清空表"+tableName+"的缓存");
        TreeMap<Long, String> sqlMaps=tableCacheMap.get(tableName);
        if (CollectionUtils.isEmpty(sqlMaps)){
            return;
        }else {
            Iterator<Map.Entry<Long, String>> it=sqlMaps.entrySet().iterator();//新建一个迭代器，准备遍历整个Set<Map.EntrySet<String,String>>集合；

            try (Statement statement=connection.createStatement()){
                while(it.hasNext()){
                    Map.Entry<Long, String> en=it.next();//
                    recordSql=en.getValue();
                    recordSCN=en.getKey();
                    log.info("开始清空表"+tableName+"的缓存");
                    statement.execute(recordSql);
                    log.info("清空一条");
                    //todo 数据入库
            }
            } catch (SQLException e) {
                e.printStackTrace();
                //todo 错误处理
                exceptionWriteCompoent.wirte(recordSql,e,recordSCN);
            }
        }
    }

    private void execute(SaveTask saveTask) {
        String toUrl=toLinkUrl;
        String toUserName=cdctousername;
        String toPassWord=cdctopassword;
        Properties props = new Properties() ;
        props.put( "user" , toUserName) ;
        props.put( "password" , toPassWord) ;
        props.put( "oracle.net.CONNECT_TIMEOUT" , "10000000") ;
        try {
        if (connection==null||connection.isClosed()){
            connection= DriverManager.getConnection(toUrl, props);
        }
            if (statement==null||statement.isClosed()){
                statement= connection.createStatement();
            }
            log.info("监听到增量sql数据，进行同步");
            statement. execute(saveTask.getSql());
            log.info("增量sql数据同步成功，总体最终scn为"+saveTask.getScn()+"|"+saveTask);
        } catch (SQLException e) {
            e.printStackTrace();;
            //todo 错误处理
            exceptionWriteCompoent.wirte(saveTask.getSql(),e,saveTask.getScn());
        }

    }

    class Worker implements Runnable {

        @Override
        public void run() {
            runTask();
        }

        private void runTask() {
            for (;;) {
                try {
                    final SaveTask saveTask = taskQueue.take();
                    Optional.of(saveTask).ifPresent(SQLSaver.this::execute);
                } catch (InterruptedException e) {
                    log.warn("BLOCKING_QUEUE take operation was interrupted.", e);
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

//    public static void main(String[] args) {
//        Roc
//    }

}
