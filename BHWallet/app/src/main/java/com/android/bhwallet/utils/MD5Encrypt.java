/**
 * MD5.java
 * Created at 2015年4月20日
 * Created by Jackie Liu
 * Copyright (C) 2015 TONGHE, All rights reserved.
 */
package com.android.bhwallet.utils;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>ClassName: MD5</p>
 * <p>Description: MD5加密</p>
 * <p>Author: Jackie Liu</p>
 * <p>Date: 2015年4月20日</p>
 */
public class MD5Encrypt {
	// 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public MD5Encrypt() {}

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }



    /**
     * <p>Description: 获取密文</p>
     * @param strObj 需要加密的字符串
     * @return
     */
    public static String getCode(String strObj) {


        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    /**
     * <p>Description: 获取密文</p>
     * @param
     * @return
     */
    public static String[] getCode(Context context) {
        //     其中userId为当前用户的id，timestamp为用户终端时间戳，Token为MD5(userId+timestamp+key)。Key暂时定为固定值"com.huabo.spsp.appkey"

        String[] resultString2 = new String[2];
        String resultString = null;
        Long time=0l;
        try {
            JSONObject user = PreferencesUtil.getInstatnce(context).getUser();
            if (user==null){
                resultString2[1]="";
                resultString2[0]="";
                return resultString2;
            }
            String id=user.getJSONObject("frontUser").getString("id");
             time=System.currentTimeMillis();
            String key="com.huabo.spsp.appkey";
            String  strObj=id+time+key;
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (Exception e){
            e.printStackTrace();
            resultString2[1]="";
            resultString2[0]="";
        }
        resultString2[1]=resultString.toUpperCase();
        resultString2[0]=time+"";
        return resultString2;
    }
}
