package com.wangxinenpu.springbootdemo.config;

import com.wangxinenpu.springbootdemo.dao.mapper.LinkTransferTaskErrorRecordMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTask;
import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTaskErrorRecord;
import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Date;

@Component
public class ExceptionWriteCompoent {

    private static final String errorPath="/opt/datacdc/errorrecord/exception.json";

    @Autowired
    LinkTransferTaskErrorRecordMapper linkTransferTaskErrorRecordMapper;

//    public  void wirte(Throwable e)  {
//        File file=new File(errorPath);
//        if (!file.exists()){
//            try {
//                file.createNewFile();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//        PrintStream printStream= null;
//        try {
//            printStream = new PrintStream(new FileOutputStream(file,true));
//        } catch (FileNotFoundException ex) {
//            ex.printStackTrace();
//        }
//        e.printStackTrace(printStream);
//    }

    public  void wirte(String sql,Throwable e,Long scn)  {
        try {
            LinkTransferTaskErrorRecord linkTransferTaskErrorRecord=new LinkTransferTaskErrorRecord();
            sql=sql.replace("'", "\\'");
            sql=sql.replace("\"", "\\\"");
//            sql= Base64.encodeToString(sql.getBytes());
            linkTransferTaskErrorRecord.setSaveSql(sql);
            linkTransferTaskErrorRecord.setCreateTime(new Date());
            linkTransferTaskErrorRecord.setExceptionTrace(e.toString());
            linkTransferTaskErrorRecord.setExceptionMsg(e.getMessage());
            linkTransferTaskErrorRecordMapper.insert(linkTransferTaskErrorRecord);
        }catch (Exception ex){
            ex.printStackTrace();
        }

//        File file=new File(errorPath);
//        if (!file.exists()){
//            try {
//                file.createNewFile();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//        PrintStream printStream= null;
//        try {
//            printStream = new PrintStream(new FileOutputStream(file,true));
//        } catch (FileNotFoundException ex) {
//            ex.printStackTrace();
//        }
//        e.printStackTrace(printStream);
    }

    public static void main(String[] args) throws IOException {
        String temp=Base64.encodeToString("insert into ||EMPQUERY||.||AC51||(||AAZ117||,||AAZ116||,||AAA027||,||AAC001||,||AAA119||,||AAE001||,||AAE253||,||AAE254||,||AIC042||,||BIC400||,||AAE259||,||AAE260||,||AAE262||,||AAE264||,||AAE263||,||AAE265||,||AIC079||,||AIC080||,||AIC043||,||AIC044||,||AIC074||,||AIC075||,||AIC045||,||BIC401||,||AAA031||,||BAE043||,||AIC081||,||BAE044||,||AAE013||,||AAE011||,||PRSENO||,||CREATE_TIME||,||MODIFY_TIME||,||AAE255||,||AAE261||,||AAE266||,||AAE267||) values (|4000000057979078|,|3010000091432978|,|330100|,|3010001031156342|,|11|,|2005|,|4271.27|,|5421.42|,|108|,|87464.04|,|149.49|,|189.75|,|338.4|,|902.4|,|6.42|,|17.11|,|12|,|11280|,|0|,|0|,|0|,|0|,|0|,|0|,|.035|,|200512|,TO_DATE(|04-6月 -21|, |DD-MON-RR|),|0|,NULL,|51880|,|163999900414590009|,TO_DATE(|04-6月 -21|, |DD-MON-RR|),TO_DATE(|04-6月 -21|, |DD-MON-RR|)".getBytes());
        System.out.println(Base64.decodeToString(temp));
    }
}
