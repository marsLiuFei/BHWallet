package com.android.bhwallet.app.Main.UI;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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

public class WalletNumActivity extends AsukaActivity {

    private TextView name;
    private TextView card_num;
    private TextView copy;
    private TextView content;
    private Button commit;

    private String merUserId;

    private String copyContent;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_wallet_num);
        name = findViewById(R.id.name);
        card_num = findViewById(R.id.card_num);
        copy = findViewById(R.id.copy);
        content = findViewById(R.id.content);
        commit = findViewById(R.id.commit);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            merUserId = bundle.getString("merUserId");
        }

        initData();
    }

    @Override
    protected void initEvent() {
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取剪贴板管理器
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", copyContent);
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);

                showSuccess("已复制");
            }
        });
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

    private void initData() {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", merUserId);
        HttpHelper.post(this, UrlConfig.WALLET_OPEN_DETAIL, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);
                JSONObject user = object.getJSONObject("user");
                name.setText("户名：" + user.getString("customerName"));
                card_num.setText(user.getString("acctNo"));
                copyContent = user.getString("acctNo");
                content.setText("您可以通过绑定银行卡官方客户端(网银/手机银行)向您的渤金钱 包转账完成充值(户名：" + user.getString("customerName") + "，收款账号：" + user.getString("acctNo") + "), 入账时间在30分钟以内并有短信提醒，入账后即可立即购买产品。渤金钱包仅支持绑定本人借记卡。");
            }

            @Override
            public void complete(String str) {

            }
        });
    }
}
