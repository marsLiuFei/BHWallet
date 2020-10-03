package com.android.bhwallet.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/8/5.
 */
public class PixelUtils {

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
    /**
     * 获取屏幕宽度
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Context context) {
        return ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                .getWidth();
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getScreenHeight(Context context) {
        return ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                .getHeight();
    }
}
