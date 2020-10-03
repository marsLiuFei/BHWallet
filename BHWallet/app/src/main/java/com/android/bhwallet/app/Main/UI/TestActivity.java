package com.android.bhwallet.app.Main.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.R;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.comm.utils.StringUtils;

public class TestActivity extends AsukaActivity {
    EditText user;
    Button go;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_test);
        go = findViewById(R.id.go);
        user = findViewById(R.id.user);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String merUserId = user.getText().toString().trim();
                if (StringUtils.isEmpty(merUserId)) {
                    Toast.makeText(TestActivity.this, "请输入用户id", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(TestActivity.this, WalletMainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("merUserId", merUserId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }
}
