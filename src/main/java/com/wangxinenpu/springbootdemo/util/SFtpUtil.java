package com.wangxinenpu.springbootdemo.util;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import ch.qos.logback.core.joran.spi.XMLUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


@Slf4j
public class SFtpUtil {
    private static final Logger loggerMonitor = LoggerFactory.getLogger("monitor");

    /**
     * FTPClient对象
     **/
    private static ChannelSftp ftpClient = null;
    /**
     *
     */
    private static Session sshSession = null;

    /**
     * 连接服务器
     * @param host
     * @param port
     * @param userName
     * @param password
     * @return
     * @throws Exception
     */
    public static ChannelSftp getConnect(String host, String port, String userName, String password)
            throws Exception {
        try {
            JSch jsch = new JSch();
            // 获取sshSession
            sshSession = jsch.getSession(userName, host, Integer.parseInt(port));
            // 添加s密码
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            // 开启sshSession链接
            sshSession.connect();
            // 获取sftp通道
            ftpClient = (ChannelSftp) sshSession.openChannel("sftp");
            // 开启
            ftpClient.connect();
            loggerMonitor.debug("success ..........");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("连接sftp服务器异常。。。。。。。。");
        }
        return ftpClient;
    }

    /**
     * @param save_path	下载保存路径
     * 下载文件
     * @param ftp_path	服务器文件路径
     * @param oldFileName	服务器上文件名
     * @param newFileName	保存后新文件名
     * @throws Exception
     */
    public static void download(String ftp_path, String save_path, String oldFileName, String newFileName)
            throws Exception {
        FileOutputStream fos = null;
        try {
            ftpClient.cd(ftp_path);
            File file = new File(save_path);
            if (!file.exists()) {
                file.mkdirs();
            }
            String saveFile = save_path + newFileName;
            File file1 = new File(saveFile);
            fos = new FileOutputStream(file1);
            ftpClient.get(oldFileName, fos);
        } catch (Exception e) {
            loggerMonitor.error("下载文件异常............", e.getMessage());
            throw new Exception("download file error............");
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("close stream error..........");
                }
            }
        }
    }

    /**
     * 上传
     * @param upload_path 上传文件路径
     * @param ftp_path	服务器保存路径
     * @param newFileName	新文件名
     * @throws Exception
     */
    public static void uploadFile(String upload_path, String ftp_path, String newFileName) throws Exception {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(upload_path));
            ftpClient.cd(ftp_path);
            ftpClient.put(fis, newFileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Upload file error.");
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new Exception("close stream error.");
                }
            }
        }
    }

    /**
     * 关闭
     *
     * @throws Exception
     */
    public static void close() throws Exception {
        loggerMonitor.debug("close............");
        try {
            ftpClient.disconnect();
            sshSession.disconnect();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new Exception("close stream error.");
        }
    }

    public static void main(String[] args) {
        try {
//            String downLoadPath="G:\\ftpPath\\download\\";
//            getConnect("10.85.159.202", "22222", "rstxt", "rstxt@123");
//            Vector<ChannelSftp.LsEntry> vector=ftpClient.ls("~");
////            vector.forEach(i-> System.out.println(i.getFilename()+"|"+i.getLongname()+"|"+i.getAttrs()));
//            //木得办法，如果是ftp，我就能用更方便的多的apache ftpserver了，但是sftp只能用这个库类，功能相比之下太弱了
//            List<String> dicNames=vector.stream().map(i->i.getFilename()).collect(Collectors.toList());
//            for (String dicName:dicNames){
//                dicName="~/"+dicName;
//                Vector<ChannelSftp.LsEntry> fileS=ftpClient.ls(dicName);
//                for (ChannelSftp.LsEntry lsEntry:fileS){
//                    download(dicName,downLoadPath,lsEntry.getFilename(),lsEntry.getFilename());
//                }
//            }
//           close();
            System.out.println("DW330001296330000202012300101.xml".substring(17,25));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void updateDZFiles(String swpt_dzwj_sftp_url, String swpt_dzwj_sftp_port, String swpt_dzwj_sftp_login_name, String swpt_dzwj_sftp_pass_word, String SWPT_DZWJ_DOWN_PATH) throws Exception {
        getConnect(swpt_dzwj_sftp_url, swpt_dzwj_sftp_port, swpt_dzwj_sftp_login_name, swpt_dzwj_sftp_pass_word);
        Vector<ChannelSftp.LsEntry> vector=ftpClient.ls("~");
//            vector.forEach(i-> System.out.println(i.getFilename()+"|"+i.getLongname()+"|"+i.getAttrs()));
        //木得办法，如果是ftp，我就能用更方便的多的apache ftpserver了，但是sftp只能用这个库类，功能相比之下太弱了
        List<String> dicNames=vector.stream().map(i->i.getFilename()).collect(Collectors.toList());
        for (String dicName:dicNames){
            dicName="~/"+dicName;
            Vector<ChannelSftp.LsEntry> fileS=ftpClient.ls(dicName);
            for (ChannelSftp.LsEntry lsEntry:fileS){
                if(fileHasNotDownYet(lsEntry.getFilename(),SWPT_DZWJ_DOWN_PATH)) {
                    download(dicName, SWPT_DZWJ_DOWN_PATH, lsEntry.getFilename(), lsEntry.getFilename());
                }
            }
        }
        close();
    }

    private static boolean fileHasNotDownYet(String filename,String SWPT_DZWJ_DOWN_PATH) {
        File dic=new File(SWPT_DZWJ_DOWN_PATH);
        if (!dic.exists()){
            dic.mkdirs();
        }
        if (!dic.isDirectory()){
            return false;
        }
        File targetFile=new File(SWPT_DZWJ_DOWN_PATH+filename);
        if (targetFile.exists()){
            return false;
        }
        return true;
    }

    public static List<File> getDZFilesByDate(String dataString, String SWPT_DZWJ_DOWN_PATH)throws Exception {
        File dic=new File(SWPT_DZWJ_DOWN_PATH);
        if (!dic.exists()){
            throw new Exception("查询目录不存在"+SWPT_DZWJ_DOWN_PATH);
        }
        File[] files=dic.listFiles();
        List<File> fileList=new ArrayList<>();
        for (File file:files){
            String fileDate=file.getName().substring(17,25);
            if (dataString.equals(fileDate)){
                fileList.add(file);
            }
        }
        return fileList;
    }

    public static JSONArray  parseFiles(List<File> files, String swpt_private_rsa_key) throws Exception {
        JSONArray jsonArray=new JSONArray();
        for (File file:files){
            String fileContent=readFileContent(file);
            String AESKEYString=XmlLoader.getMsgByXML(fileContent,"<AESKEY>","</AESKEY>");
            String aesKey=RSAEncrypt.decrypt(AESKEYString,swpt_private_rsa_key);
            log.info("解析key为"+aesKey);
            String contentString=XmlLoader.getMsgByXML(fileContent,"<BILL_CONTENT>","</BILL_CONTENT>");
            //2. AES密钥解密BILL_CONTENT获取签名XML文档
            log.info("解析content为"+contentString);
            byte[] decryptResult = AESTool.decrypt(Base64.decodeBase64(contentString.getBytes()),aesKey);
            String content = new String(decryptResult);
            JSONObject test= JsonXmlUtils.xmlToJson(content);
            jsonArray.add(test.getJSONObject("ROOT").getJSONObject("DETAIL"));
//            System.out.println("签名XML：" + content);
        }
        return jsonArray;
    }

    public static String readFileContent(File file) throws IOException {
        StringBuffer sbf = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        }
    }
}
