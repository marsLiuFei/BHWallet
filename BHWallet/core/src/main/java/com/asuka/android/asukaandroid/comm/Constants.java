package com.asuka.android.asukaandroid.comm;

import android.os.Environment;

/**
 * Created by Administrator on 2017/4/17 0017.
 */

public class Constants {
    public static final String APP_DIR                    = Environment.getExternalStorageDirectory() + "/egojit";
    public static final String APP_TEMP                   = APP_DIR + "/temp";
    public static final String APP_IMAGE                  = APP_DIR + "/image";
    public static final int REQUEST_CROP = 6709;
    public static final int REQUEST_PICK = 9162;
    public static final int RESULT_ERROR = 404;


    /**
     * 裁剪默认宽度
     */
    public final static int outputX=480;
    /**
     * 裁剪默认高度
     */
    public final static int outputY=480;
    /**
     * request Code 从相册选择照片并裁切
     **/
    public final static int PIC_SELECT_CROP = 123;
    /**
     * request Code 从相册选择照片不裁切
     **/
    public final static int PIC_SELECT_ORIGINAL = 126;
    /**
     * request Code 拍取照片并裁切
     **/
    public final static int PIC_TAKE_CROP = 124;
    /**
     * request Code 拍取照片不裁切
     **/
    public final static int PIC_TAKE_ORIGINAL = 127;
    /**
     * request Code 裁切照片
     **/
    public final static int PIC_CROP = 125;
}
