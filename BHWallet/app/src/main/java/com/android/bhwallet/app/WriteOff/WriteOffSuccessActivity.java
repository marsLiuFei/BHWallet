package com.android.bhwallet.app.WriteOff;

import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.view.annotation.ContentView;
import com.asuka.android.asukaandroid.view.annotation.Event;
import com.android.bhwallet.R;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/26
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class WriteOffSuccessActivity extends AsukaActivity {

    private Button login_out;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_write_off_success);
        login_out = findViewById(R.id.login_out);
    }

    @Override
    protected void initEvent() {
        login_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }
}
