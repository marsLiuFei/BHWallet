package com.android.bhwallet.app.PayManage;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.view.annotation.ContentView;
import com.asuka.android.asukaandroid.view.annotation.Event;
import com.asuka.android.asukaandroid.view.annotation.ViewInject;
import com.android.bhwallet.R;
import com.android.bhwallet.app.Card.PayPasswordActivity;

import static com.android.bhwallet.app.Card.PayPasswordActivity.RESET_PASSWORD;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/24
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class SafeCheckActivity extends AsukaActivity {
    private TextView forget_password;
    private TextView check_pay_password;

    private String merUserId;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_safe_check);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            merUserId = bundle.getString("merUserId");
        }
        forget_password = findViewById(R.id.forget_password);
        check_pay_password = findViewById(R.id.check_pay_password);

        forget_password.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        forget_password.getPaint().setAntiAlias(true);//抗锯齿
    }

    @Override
    protected void initEvent() {
        check_pay_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("type",RESET_PASSWORD);
                bundle.putString("title","验证密码");
                bundle.putString("merUserId",merUserId);
                startActivity(PayPasswordActivity.class,"",bundle);
            }
        });

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId",merUserId);
                startActivity(CheckIdcardActivity.class,"验证身份",bundle);
            }
        });
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

}
