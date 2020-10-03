package com.android.bhwallet.base;

import android.os.Environment;

/**
 * =================================================================================================
 * 备注：***
 * 作者：高露
 * 时间：2017/4/21
 * =================================================================================================
 */
public class Constant {
    public final static String SharedPreferences = "user";

    /**
     * openfire地址
     */
    public static String netErrResultMsg="当前网络不给力，请稍后重试";
    public static String save_img_url = Environment.getExternalStorageDirectory()+"/demo_img/";
    public static String save_audio_url = Environment.getExternalStorageDirectory()+"/demo_audio/";
}
