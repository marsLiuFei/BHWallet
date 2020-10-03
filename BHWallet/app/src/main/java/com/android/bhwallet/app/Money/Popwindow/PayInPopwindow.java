package com.android.bhwallet.app.Money.Popwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.bhwallet.R;
import com.android.bhwallet.app.Money.Interface.PayOnClickListener;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/27
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class PayInPopwindow extends PopupWindow {
    private Context context;
    private PayOnClickListener listener;

    public PayInPopwindow(Context context, String money, PayOnClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        init(money);
    }

    private void init(String money) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_pay_in, null);
        setContentView(view);

        TextView tvMoney = view.findViewById(R.id.money);
        tvMoney.setText("￥" + money);
        Button pay = view.findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener.onClick();
            }
        });

        ImageView close = view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener.onCancel();
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
        setAnimationStyle(R.style.AnimBottom);
    }
}
