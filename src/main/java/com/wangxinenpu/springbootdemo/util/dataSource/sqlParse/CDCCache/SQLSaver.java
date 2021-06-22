package com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.CDCCache;

import com.wangxinenpu.springbootdemo.dao.mapper.LinkTransferTaskErrorRecordMapper;
import com.wangxinenpu.springbootdemo.config.ExceptionWriteCompoent;
import com.wangxinenpu.springbootdemo.dao.mapper.LinkTransferTaskRecordMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTaskRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.util.StringUtil;

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

    @Autowired
    private LinkTransferTaskRecordMapper linkTransferTaskRecordMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private Connection connection;
    private Statement statement;

    public String recordSql="";
    public Long recordSCN=null;
    @Autowired
    ExceptionWriteCompoent exceptionWriteCompoent;
    public LinkedBlockingQueue<Runnable> workerQueues=new LinkedBlockingQueue<>();

    public static final Map<String,TreeMap<String,String>> tableCacheMap=new HashMap<>();
    public static final LinkedBlockingDeque<SaveTask> taskQueue=new LinkedBlockingDeque<>();

    public static Long totalInsertCount=0l;

    @PostConstruct
    private void init(){
        //todo initlist
         new ThreadPoolExecutor(
                4,9,10000l, TimeUnit.MILLISECONDS,
                 workerQueues,
                SoulThreadFactory.create("save-upstream-task", false))
                .execute(new Worker());
    }

    public  void save(String segOwner,String tableName, String sql, String tableStatus, String scn, String timeStamp){
        if (tableStatus.equals( MSGTYPECONSTANT.TABLE_STATUS_ISFULL_EXTRACT)){
            TreeMap<String,String> tableSQLMap=tableCacheMap.get(segOwner+"|"+tableName);
            if (CollectionUtils.isEmpty(tableSQLMap)){
                tableSQLMap=new TreeMap<>();
            }
            tableSQLMap.put(scn,sql);
            tableCacheMap.put(segOwner+"|"+tableName,tableSQLMap);
        }else {
//            taskQueue.add(new SaveTask(segOwner,tableName,scn,sql,timeStamp));
            redisTemplate.opsForList().leftPush("big:queue",sql);
        }
    }

    public  void  executeSQLs(String tableName) {
        log.info("开始清空表"+tableName+"的缓存");
        TreeMap<String, String> sqlMaps=tableCacheMap.get(tableName);
        if (CollectionUtils.isEmpty(sqlMaps)){
            return;
        }else {
            redisTemplate.opsForList().leftPushAll("big:queue:"+tableName,sqlMaps.values());
            log.info("清空完成,放入缓存"+"big:queue:"+tableName);
            sqlMaps.clear();
            tableCacheMap.remove(tableName);
        }
    }

    private void execute(SaveTask saveTask) {
//        String toUrl=toLinkUrl;
//        String toUserName=cdctousername;
//        String toPassWord=cdctopassword;
//        Properties props = new Properties() ;
//        props.put( "user" , toUserName) ;
//        props.put( "password" , toPassWord) ;
//        props.put( "oracle.net.CONNECT_TIMEOUT" , "10000000") ;
        try {
//        if (connection==null||connection.isClosed()){
//            connection= DriverManager.getConnection(toUrl, props);
//        }
//            if (statement==null||statement.isClosed()){
//                statement= connection.createStatement();
//            }
            redisTemplate.opsForList().leftPush("big:queue:"+saveTask.getSegOwner()+"|"+saveTask.getTableName(),saveTask.getSql());
//            log.info("监听到增量sql数据，进行同步"+"big:queue:"+saveTask.getSegOwner()+"|"+saveTask.getTableName());
//            totalInsertCount++;
////            statement.execute("alter session set nls_date_language='american' ");
//            statement. execute(saveTask.getSql());
//            log.info("增量sql数据同步成功，总体最终scn为"+saveTask.getScn()+"|"+saveTask.getTime());()
        } catch (Exception e) {
//            e.printStackTrace();;
            //todo 错误处理
            exceptionWriteCompoent.wirte(saveTask.getSql(),e,saveTask.getScn());
        }

    }

    private void updateSaveStatus(String Sql,Long scn,String segName,String tableName){
        try {
            linkTransferTaskRecordMapper.insert(new LinkTransferTaskRecord().setSaveSql(Sql).setScn(scn).setTableName(tableName).setSegOwner(segName));
        }catch (Exception e){
            // do no thing
        }
    }
    public class Worker implements Runnable {

        public Long totalCount=0l;

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
