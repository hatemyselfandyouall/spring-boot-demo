//package com.wangxinenpu.springbootdemo.util;
//
//import org.bouncycastle.util.encoders.Base64;
//import tk.mybatis.mapper.util.StringUtil;
//
//import javax.crypto.Cipher;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//
//public class AESUtil {
//
//    public static final String CIPHER_MODE = "AES/CBC/PKCS5Padding";
//    public static final String IVPARAMETER = "4d@L!L1SaFe4D4f1";
//
//    public AESUtil() {
//    }
//
//    public static String encrypt(String sSrc, String sKey, String mode, String ivParameter) {
//        try {
//            if (sKey == null) {
//                System.out.print("Key为空null");
//                return null;
//            } else if (sKey.length() != 16) {
//                System.out.print("Key长度不是16位");
//                return null;
//            } else {
//                byte[] raw = sKey.getBytes();
//                SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
//                Cipher cipher = Cipher.getInstance(mode);
//                if (!StringUtil.isEmpty(ivParameter)) {
//                    IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
//                    cipher.init(1, skeySpec, iv);
//                } else {
//                    cipher.init(1, skeySpec);
//                }
//
//                byte[] encrypted = cipher.doFinal(sSrc.getBytes("UTF-8"));
//                return new String(Base64.encode(encrypted));
//            }
//        } catch (Throwable var8) {
//            System.out.println(var8.toString());
//            return null;
//        }
//    }
//
//    public static String encrypt(String sSrc, String sKey) {
//        return encrypt(sSrc, sKey, "AES/CBC/PKCS5Padding", "4d@L!L1SaFe4D4f1");
//    }
//
//    public static String decrypt(String sSrc, String sKey, String mode, String ivParameter) {
//        try {
//            if (sKey == null) {
//                System.out.print("Key为空null");
//                return null;
//            } else if (sKey.length() != 16) {
//                System.out.print("Key长度不是16位");
//                return null;
//            } else {
//                byte[] raw = sKey.getBytes("UTF-8");
//                SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
//                Cipher cipher = Cipher.getInstance(mode);
//                if (!StringUtil.isEmpty(ivParameter)) {
//                    IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
//                    cipher.init(2, skeySpec, iv);
//                } else {
//                    cipher.init(2, skeySpec);
//                }
//
//                byte[] encrypted1 = Base64.decode(sSrc.getBytes("UTF-8"));
//                byte[] original = cipher.doFinal(encrypted1);
//                String originalString = new String(original);
//                return originalString;
//            }
//        } catch (Throwable var10) {
//            System.out.println(var10.toString());
//            return null;
//        }
//    }
//
//    public static String decrypt(String sSrc, String sKey) {
//        return decrypt(sSrc, sKey, "AES/CBC/PKCS5Padding", "4d@L!L1SaFe4D4f1");
//    }
//}
