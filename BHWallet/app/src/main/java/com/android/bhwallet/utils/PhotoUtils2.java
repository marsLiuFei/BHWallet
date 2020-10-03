package com.android.bhwallet.utils;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * 备注:照片出来帮助
 * 作者:朱宗庆
 * 日期:2017/5/5
 */
public class PhotoUtils2 {
    /**
     * BitMap转Byte数组
     * @param bmp
     * @return
     */
    public  static byte[]  BitmapToByte(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return  byteArray;
    }
}
