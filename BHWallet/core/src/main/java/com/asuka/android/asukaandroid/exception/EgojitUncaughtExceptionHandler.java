package com.asuka.android.asukaandroid.exception;

import android.content.Context;
import android.os.Looper;

import com.asuka.android.asukaandroid.AsukaAndroid;
import com.asuka.android.asukaandroid.AsukaApplication;
import com.asuka.android.asukaandroid.comm.utils.LogUtil;
import com.asuka.android.asukaandroid.comm.utils.ToastUtil;

/**
 * Created by egojit on 2017/4/7.
 */

public class EgojitUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static Object lock = new Object();



    private static EgojitUncaughtExceptionHandler mCrashHandler;
    private Context mContext;

    private Thread.UncaughtExceptionHandler mDefaultHandler;

    public  static EgojitUncaughtExceptionHandler getInstance(){
        synchronized (lock) {
            if (mCrashHandler == null) {
                synchronized (lock) {
                    if (mCrashHandler == null) {
                        mCrashHandler = new EgojitUncaughtExceptionHandler();
                    }
                }
            }

            return mCrashHandler;
        }
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }





    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, new Throwable("未知的异常！"));
        } else {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
               LogUtil.e(ex.toString());
            }
            // 退出程序
            AsukaAndroid.app().getAppManager().finishAllActivity();
            //退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }


        Context c=  AsukaAndroid.app().getApplicationContext();
        AsukaApplication app=(AsukaApplication)c;
        app.OnUncaughtException(ex);
         // 使用Toast来显示异常信息
         new Thread() {
         @Override
         public void run() {
         Looper.prepare();
            ToastUtil.showToast(mContext,"出现异常,即将退出重启！我们尽快修复，请谅解！");
         Looper.loop();

         }
         }.start();

        return true;
    }


}
