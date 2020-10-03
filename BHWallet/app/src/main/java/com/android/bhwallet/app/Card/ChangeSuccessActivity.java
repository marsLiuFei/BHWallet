package com.android.bhwallet.app.Card;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.view.annotation.ContentView;
import com.asuka.android.asukaandroid.view.annotation.Event;
import com.asuka.android.asukaandroid.view.annotation.ViewInject;
import com.android.bhwallet.R;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/24
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class ChangeSuccessActivity extends AsukaActivity {
    private TextView content;
    private Button back;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_change_success);
        content = findViewById(R.id.content);
        back = findViewById(R.id.back);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            content.setText(bundle.getString("title"));
        }

    }

    @Override
    protected void initEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

}
