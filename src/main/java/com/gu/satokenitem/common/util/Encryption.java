package com.gu.satokenitem.common.util;
import org.apache.axis.encoding.Base64;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class Encryption {
    /*MD5加密*/
    public String waybillTraceMD5(String str) {
        String md5Str = null;
        String md5Str1 = null;
        if (str != null && str.length() != 0) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                try {
                    md.update(str.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                byte b[] = md.digest();

                StringBuffer buf = new StringBuffer("");
                for (int offset = 0; offset < b.length; offset++) {
                    int i = b[offset];
                    if (i < 0)
                        i += 256;
                    if (i < 16)
                        buf.append("0");
                    buf.append(Integer.toHexString(i));
                }
                //32位
                md5Str = buf.toString();
                System.out.println("md5---------" + md5Str);
                //转成base64位
                try {
                    md5Str1 = Base64.encode(md5Str.getBytes("UTF-8"));
                    System.out.println("Base64---------" + md5Str1);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return md5Str1;
    }
    /*encod加密*/
    public String waybillTraceEncod(String str){
        String encode = null;
        try {
            encode = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encode;
    }
    /*decode解密*/
    public String waybillTraceDecode(String str){
        String decode = null;
        try {
            decode = URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decode;
    }
}
