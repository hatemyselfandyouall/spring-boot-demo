package com.wangxinenpu.springbootdemo.util;

import java.io.*;

public class ExceptionWriteUtil {

    private static final String errorPath="/opt/datacdc/errorrecord/exception.json";

    public static void wirte(Throwable e)  {

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

    public static void main(String[] args) throws IOException {
//        wirte(new Exception("dsfdsf"),"F:\\\\数据回流项目部署准备\\\\datax\\\\failRecord\\\\VILQUERY_MV_BC02.json");
    }
}
