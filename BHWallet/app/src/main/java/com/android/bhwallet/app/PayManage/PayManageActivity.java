package com.android.bhwallet.app.PayManage;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.view.annotation.ContentView;
import com.asuka.android.asukaandroid.view.annotation.Event;
import com.android.bhwallet.R;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/24
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class PayManageActivity extends AsukaActivity {
    private TextView reset_pay_password;

    private String merUserId;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_pay_password_manage);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            merUserId = bundle.getString("merUserId");
        }

        reset_pay_password = findViewById(R.id.reset_pay_password);
    }

    @Override
    protected void initEvent() {
        reset_pay_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId",merUserId);
                startActivity(SafeCheckActivity.class,"安全校验",bundle);
                finish();
            }
        });
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }
}
