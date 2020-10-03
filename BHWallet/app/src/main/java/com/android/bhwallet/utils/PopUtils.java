package com.android.bhwallet.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;


/**
 * Created by Administrator on 2016/7/15.
 */
public class PopUtils {
    onGets onGets;
    onSetShowLoacation onSetShowLoacation;
    private PopupWindow popwd;

    public interface onGets{
        public void onGetPopView(PopupWindow popupWindow);
        public void onGetPopLayoutView(View view);
    }
    public interface onSetShowLoacation{
        public void onSet(PopupWindow popupWindow, View parent);

    }
    public PopUtils() {

    }
    public PopUtils(PopUtils.onSetShowLoacation onSetShowLoacation) {
        this.onSetShowLoacation = onSetShowLoacation;
    }

    public void showCustomPop(Context mContext,View view,int PopLayout,onGets onGets){
        this.onGets=onGets;

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(mContext).inflate(
                PopLayout, null);
        onGets.onGetPopLayoutView(contentView);
//        PopupWindow popupWindow=new PopupWindow(contentView);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                RelativeLayout.LayoutParams.WRAP_CONTENT,   RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        onGets.onGetPopView(popupWindow);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        int a=view.getHeight();
        int b=view.getWidth();
        // 设置好参数之后再show
        if (onSetShowLoacation==null){
            popupWindow.showAsDropDown(view, (b - 120) / 2, -a - 150, Gravity.CENTER_HORIZONTAL);
        }else {
            onSetShowLoacation.onSet(popupWindow,view);
        }

    }

    /**
     *
     * @param mContext
     * @param view
     * @param PopLayout
     * @param id
     */

    public  void  showTehangCmsPop(final Context mContext, final View view, int PopLayout, final String id){

//        // 一个自定义的布局，作为显示的内容
//        View contentView = LayoutInflater.from(mContext).inflate(
//                PopLayout, null);
//        WebView webViewShow= (WebView) contentView.findViewById(R.id.webViewShow);
//        TextView tv_close= (TextView) contentView.findViewById(R.id.tv_close);
//
//        webViewShow.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url + HttpUtil.getWebViewPath(mContext));
//                return true;
//            }
//        });
//
//        String    url1= UrlConfig.ZIXUN_DETAIL+ HttpUtil.getWebViewPath(mContext)+"&id="+id;
//        LogUtil.e(url1);
//        webViewShow.loadUrl(url1);
////        PopupWindow popupWindow=new PopupWindow(contentView);
//        int width=ScreenUtils.getScreenWidth(mContext);
//        int length=ScreenUtils.getScreenHeight(mContext);
//        final PopupWindow popupWindow = new PopupWindow(contentView,
//                width-200 ,   length-300, true);
//        popupWindow.setTouchable(true);
//        popupWindow.setOutsideTouchable(false);
//        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
////                 int  mode=  event.getAction();
////                if (mode==MotionEvent.ACTION_OUTSIDE){
////                    return true;
////                }
//                return false;
//                // 这里如果返回true的话，touch事件将被拦截
//                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
//            }
//        });
//        tv_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (popupWindow!=null){
//                    popupWindow.dismiss();
//                }
//            }
//        });
//        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
//        // 我觉得这里是API的一个bug
////        popupWindow.setBackgroundDrawable(new ColorDrawable());
//        int a=view.getHeight();
//        int b=view.getWidth();
//        // 设置好参数之后再show
//        popupWindow.showAtLocation(view, Gravity.CENTER,0, 0);

    }
}
