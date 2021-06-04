package com.wangxinenpu.springbootdemo.config;

import com.wangxinenpu.springbootdemo.dao.mapper.LinkTransferTaskErrorRecordMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTask;
import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTaskErrorRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class ExceptionWriteCompoent {

    private static final String errorPath="/opt/datacdc/errorrecord/exception.json";

    @Autowired
    LinkTransferTaskErrorRecordMapper linkTransferTaskErrorRecordMapper;

    public  void wirte(Throwable e)  {
        File file=new File(errorPath);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        PrintStream printStream= null;
        try {
            printStream = new PrintStream(new FileOutputStream(file,true));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        e.printStackTrace(printStream);
    }

    public  void wirte(String sql,Throwable e)  {
        File file=new File(errorPath);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        PrintStream printStream= null;
        try {
            printStream = new PrintStream(new FileOutputStream(file,true));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        e.printStackTrace(printStream);
    }

//    public static void main(String[] args) throws IOException {
//        wirte("sql",new Exception("dsfdsf"));
//    }
}
