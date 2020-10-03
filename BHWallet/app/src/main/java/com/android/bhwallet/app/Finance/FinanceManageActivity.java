package com.android.bhwallet.app.Finance;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONObject;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.view.annotation.ContentView;
import com.asuka.android.asukaandroid.view.annotation.Event;
import com.android.bhwallet.R;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/23
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class FinanceManageActivity extends AsukaActivity {
    private LinearLayout ele_account_manage;

    private String merUserId;
    private String EacAcctNo;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_finance_manage);
        ele_account_manage = findViewById(R.id.ele_account_manage);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            merUserId = bundle.getString("merUserId");
            EacAcctNo = bundle.getString("EacAcctNo");
        }
    }

    @Override
    protected void initEvent() {
        ele_account_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId",merUserId);
                bundle.putString("EacAcctNo",EacAcctNo);
                startActivity(FinanceAccountActivity.class,"电子账户",bundle);
            }
        });
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

}
