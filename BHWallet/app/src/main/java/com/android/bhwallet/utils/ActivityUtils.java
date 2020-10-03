package com.android.bhwallet.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;


import com.asuka.android.asukaandroid.comm.utils.LogUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/7/4.
 */
public class ActivityUtils {
    /**
     * 判断某个界面是否在前台
     *
     * @param context
     * @param className
     *            某个界面名称
     */
    public static boolean isForeground(Context context, String className) {
        LogUtil.e("[消息]----isForeground-------->");
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            String clsName=cpn.getShortClassName();
            if (className.equals(clsName)) {
                return true;
            }
        }

        return false;
    }
}
