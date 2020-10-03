package com.android.bhwallet.app.Card;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.utils.HttpHelper;
import com.android.bhwallet.utils.UrlConfig;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.comm.utils.StringUtils;
import com.asuka.android.asukaandroid.http.EGRequestParams;
import com.asuka.android.asukaandroid.view.annotation.ContentView;
import com.asuka.android.asukaandroid.view.annotation.Event;
import com.android.bhwallet.R;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/23
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class CardManageActivity extends AsukaActivity {
    private TextView change_card;
    private TextView unbind_card;
    private TextView bank_name;
    private TextView bank_num;

    private String merUserId;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_card_manage);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            merUserId = bundle.getString("merUserId");
        }


        change_card = findViewById(R.id.change_card);
        unbind_card = findViewById(R.id.unbind_card);
        bank_name = findViewById(R.id.bank_name);
        bank_num = findViewById(R.id.bank_num);
        getCardMessage();
    }


    @Override
    protected void initEvent() {
        change_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("type",PayPasswordActivity.BIND_CARD);
                bundle.putString("merUserId",merUserId);
                bundle.putString("title","变更银行卡");
                startActivity(PayPasswordActivity.class,"验证密码",bundle);
            }
        });
        unbind_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("type",PayPasswordActivity.UNBIND_CARD);
                bundle.putString("merUserId",merUserId);
                bundle.putString("title","解绑银行卡");
                startActivity(PayPasswordActivity.class,"验证密码",bundle);
            }
        });
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {
        if ("card_update".equals(object.getString("type"))){
            getCardMessage();
        }
    }

    private void getCardMessage(){
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId",merUserId);
        HttpHelper.post(this, UrlConfig.WALLET_OPEN_DETAIL, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject data = JSON.parseObject(str);
                JSONObject user = data.getJSONObject("user");
                bank_name.setText(user.getString("acctBankName"));
                String acctNo = user.getString("acctNo");
                if (!StringUtils.isEmpty(acctNo)&&acctNo.length()>4) {
                    acctNo = "**** **** **** "+acctNo.substring(acctNo.length()-4);

                }
                bank_num.setText(acctNo);
            }

            @Override
            public void complete(String str) {

            }
        });
    }
}
