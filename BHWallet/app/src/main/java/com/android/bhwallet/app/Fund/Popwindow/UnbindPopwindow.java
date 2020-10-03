package com.android.bhwallet.app.Fund.Popwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.bhwallet.R;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/27
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class UnbindPopwindow extends PopupWindow {
    private Context context;
    private TextView unbind;
    private SetOnClickListener listener;

    public UnbindPopwindow(Context context, SetOnClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_unbind, null);
        setContentView(view);
        unbind = view.findViewById(R.id.unbind);
        unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener.onClick();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);//设置outside可点击
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(0x44000000));//设置透明背景
        //添加pop窗口关闭事件
    }

    public void showAsDropDown(View anchor) {
        showAsDropDown(anchor, 0, 0);
    }

}
