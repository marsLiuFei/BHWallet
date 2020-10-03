package com.android.bhwallet.app.Fund.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.R;
import com.android.bhwallet.app.Card.PayPasswordActivity;
import com.asuka.android.asukaandroid.AsukaActivity;

public class FundOpenActivity extends AsukaActivity {
    private Button open;
    private String merUserId;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_fund_open);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            merUserId = bundle.getString("merUserId");
        }

        open = findViewById(R.id.open);
    }

    @Override
    protected void initEvent() {
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("type",PayPasswordActivity.OPEN_FUND);
                bundle.putString("merUserId",merUserId);
                startActivity(PayPasswordActivity.class,"验证密码",bundle);
                finish();
            }
        });
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }
}
