package com.android.bhwallet.app.Save.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.R;
import com.android.bhwallet.utils.HttpHelper;
import com.android.bhwallet.utils.UrlConfig;
import com.android.bhwallet.views.WiperSwitch;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.comm.utils.StringUtils;
import com.asuka.android.asukaandroid.http.EGRequestParams;

/**
 * @author 蒋冬冬
 * 创建日期：2020/9/20
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class SellActivity extends AsukaActivity {
    private TextView money;
    private TextView time;
    private TextView rate;
    private WiperSwitch wiper_switch;
    private LinearLayout ll_money;
    private EditText sell_money;
    private EditText password;
    private Button commit;

    private String merUserId;
    private JSONObject object;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_sell);
        money = findViewById(R.id.money);
        time = findViewById(R.id.time);
        rate = findViewById(R.id.rate);
        wiper_switch = findViewById(R.id.wiper_switch);
        ll_money = findViewById(R.id.ll_money);
        sell_money = findViewById(R.id.sell_money);
        password = findViewById(R.id.password);
        commit = findViewById(R.id.commit);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String data = bundle.getString("data");
            object = JSON.parseObject(data);
            merUserId = bundle.getString("merUserId");
        }
        time.setText(object.getString("SignDate")+"~"+object.getString("IntBeginDate"));
        rate.setText(object.getFloatValue("Rate")+"%");
        money.setText(object.getString("AcctBalance")+"元");
    }

    @Override
    protected void initEvent() {
        wiper_switch.setOnChangedListener(new WiperSwitch.OnChangedListener() {
            @Override
            public void OnChanged(WiperSwitch wiperSwitch, boolean checkState) {
                if (checkState){
                    ll_money.setVisibility(View.GONE);
                }else {
                    ll_money.setVisibility(View.VISIBLE);
                }
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitData();
            }
        });
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

    private void commitData(){
        String strMoney = sell_money.getText().toString().trim();
        if (!wiper_switch.getNowStatus()){
            if (StringUtils.isEmpty(strMoney)){
                showWarning("请输入提取金额");
                return;
            }else {
                int iMoney = Integer.parseInt(strMoney);
                if (iMoney<=0){
                    showWarning("请输入大于0的整数");
                    return;
                }
            }
        }

        String strPassword = password.getText().toString().trim();
        if (StringUtils.isEmpty(strPassword) || strPassword.length()!=6){
            showWarning("请输入六位支付密码");
            return;
        }

        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("linkAcctNo",object.getString("AcctNo"));
        para.addBodyParameter("subAcct",object.getString("SubAcct"));
        para.addBodyParameter("merUserId",merUserId);
        para.addBodyParameter("productCode",object.getString("ProductCode"));
        para.addBodyParameter("password",strPassword);
        if (!wiper_switch.getNowStatus()){
            para.addBodyParameter("transAmount",strMoney);
        }

        HttpHelper.post(this, wiper_switch.getNowStatus() ? UrlConfig.SELL_ALL : UrlConfig.SELL, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                showSuccess("提取成功");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void complete(String str) {

            }
        });
    }
}
