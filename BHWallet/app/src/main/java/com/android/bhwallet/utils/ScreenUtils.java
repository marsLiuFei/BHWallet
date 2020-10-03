package com.android.bhwallet.utils;

import android.content.Context;
import android.view.WindowManager;

public class ScreenUtils {

	@SuppressWarnings("deprecation")
	public static int[] getScreenDispaly(Context context){
		WindowManager wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		int width=wm.getDefaultDisplay().getWidth();//手机屏幕的宽度
		int height=wm.getDefaultDisplay().getHeight();//手机屏幕的高度
		int result[] = {width,height};
		return result;

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
	 * 获取屏幕宽度
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
