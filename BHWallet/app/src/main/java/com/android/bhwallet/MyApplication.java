package com.android.bhwallet;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.asuka.android.asukaandroid.AsukaAndroid;
import com.asuka.android.asukaandroid.AsukaApplication;
import com.asuka.android.asukaandroid.comm.utils.LogUtil;
import com.asuka.android.asukaandroid.orm.AsukaOrmAndroid;
import com.asuka.android.asukaandroid.utils.StorageUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * =================================================================================================
 * 备注：***
 * 作者：蒋冬冬
 * 时间：2017/4/21
 * =================================================================================================
 */
public class MyApplication extends AsukaApplication {
    private static MyApplication instanse;
    public MyApplication(Context context) {
        super(context);
    }

    public MyApplication(){
        super();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instanse = this;
//        AsukaAndroid.Ext.init(this);
//        AsukaAndroid.Ext.setDebug(true);

        StorageUtil.init(this, null);//初始化监测sdcard
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

    }
    public static MyApplication getInstanse() {
        return instanse;
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        AsukaOrmAndroid.dispose();
    }

    @Override
    public void OnUncaughtException(Throwable ex) {
        super.OnUncaughtException(ex);


        StringBuffer sb = new StringBuffer();
        //递归获取全部的exception信息
        try {
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.close();
            String result = writer.toString();
            sb.append(result);
            LogUtil.e(sb.toString());

        }catch (Exception e){

        }


    }

}
