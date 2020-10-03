package com.android.bhwallet.app.Main.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.R;
import com.android.bhwallet.utils.HttpHelper;
import com.android.bhwallet.utils.UrlConfig;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.http.EGRequestParams;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/20
 * 邮箱：825814902@qq.com
 * 描述：
 */

public class EleOpenRulesActivity extends AsukaActivity {
    private Button open;
    private TextView content;
    private String merUserId;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_ele_open_rules);
        open = findViewById(R.id.open);
        content = findViewById(R.id.content);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            merUserId = bundle.getString("merUserId");
        }
        //getRules();
    }

    @Override
    protected void initEvent() {
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId", merUserId);
                startActivity(EleOpenActivity.class, "实名认证", bundle);
                finish();
            }
        });
    }

    private void getRules() {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("type", "1");
        HttpHelper.post(this, UrlConfig.PROTOCOL, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject data = JSON.parseObject(str);
                content.setText(data.getString("content"));
            }

            @Override
            public void complete(String str) {

            }
        });
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

}
