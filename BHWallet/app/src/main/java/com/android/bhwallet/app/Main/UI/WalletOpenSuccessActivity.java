package com.android.bhwallet.app.Main.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.view.annotation.ContentView;
import com.asuka.android.asukaandroid.view.annotation.Event;
import com.android.bhwallet.R;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/20
 * 邮箱：825814902@qq.com
 * 描述：
 */

public class WalletOpenSuccessActivity extends AsukaActivity {

    private Button success;
    private Boolean userEa;
    private String merUserId;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_wallet_open_success);
        success = findViewById(R.id.success);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userEa = bundle.getBoolean("UserEa");
            merUserId = bundle.getString("merUserId");

        }
    }

    @Override
    protected void initEvent() {
        success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userEa) {
                    Bundle bundle = new Bundle();
                    bundle.putString("merUserId", merUserId);
                    startActivity(EleOpenRulesActivity.class, "账户开通协议",bundle);
                }
                finish();


            }
        });
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }
}
