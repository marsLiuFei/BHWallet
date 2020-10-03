package com.android.bhwallet.views.Dialogs;


import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

/**
 * =================================================================================================
 * 备注：***
 * 作者：赵锋
 * 时间：2017/4/25 0025
 * =================================================================================================
 */

public class CustomDialog extends AlertDialog {
    Context mContext;
    Builder builder;
    private View CustomView;
    public CustomDialog(Context context) {
        super(context);
        this.mContext=context;
        initDialog();
    }

    private void initDialog() {
        setCanceledOnTouchOutside(false);
         builder = new  Builder(mContext);
        builder.setCancelable(false);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
        this.mContext=context;
        initDialog();
    }

    public Builder setCancelButton(String s, OnClickListener listener){
        builder.setNegativeButton(s, listener);
        return builder;
    }
    public Builder setOkButton(String s, OnClickListener listener){
        builder.setPositiveButton(s, listener);
        return builder;
    }
    public Builder setTitle(String s){
        builder.setTitle(s);
        return builder;
    }
    public Builder setMessage(String s){
        builder.setMessage(s);
        return builder;
    }
    public void show(){
        if (CustomView==null){
            setTitle("提示");
        }
        builder.show();
    }
    public Builder setCustomLayout(int layout){
        LayoutInflater inflater = getLayoutInflater();
        CustomView=inflater.inflate(layout,null);
        builder.setView(CustomView);
        return builder;
    }
    public View getCustomView() {
        return CustomView;
    }

}
