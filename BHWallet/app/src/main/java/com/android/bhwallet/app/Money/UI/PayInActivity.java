package com.android.bhwallet.app.Money.UI;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.R;
import com.android.bhwallet.app.Card.PayPasswordActivity;
import com.android.bhwallet.app.Money.Interface.PayOnClickListener;
import com.android.bhwallet.app.Money.Popwindow.PayInPopwindow;
import com.android.bhwallet.utils.HttpHelper;
import com.android.bhwallet.utils.UrlConfig;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.comm.utils.StringUtils;
import com.asuka.android.asukaandroid.http.EGRequestParams;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/27
 * 邮箱：825814902@qq.com
 * 描述：充值
 */
public class PayInActivity extends AsukaActivity {

    private EditText et_money;
    private Button commit;
    private TextView bank;
    PayInPopwindow popwindow;

    private String merUserId;
    private Boolean is_ele;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_pay_in);
        et_money = findViewById(R.id.et_money);
        commit = findViewById(R.id.commit);
        bank = findViewById(R.id.bank);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            merUserId = bundle.getString("merUserId");
            is_ele = bundle.getBoolean("is_ele", false);
        }
        getData();
    }

    private void getData() {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", merUserId);
        HttpHelper.post(this, UrlConfig.WALLET_OPEN_DETAIL, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);
                if (is_ele) {
                    JSONArray eleAccList = object.getJSONArray("eleAccList");
                    if (eleAccList != null && eleAccList.size() > 0) {
                        JSONObject jsonObject = (JSONObject) eleAccList.get(0);
                        String acctNo = jsonObject.getString("EacCardNo");
                        bank.setText("尾号" + acctNo.substring(acctNo.length() - 4));
                    } else {
                        showWarning("请先绑定银行卡");
                        finish();
                    }
                } else {
                    JSONObject user = object.getJSONObject("user");
                    if (StringUtils.isEmpty(user.getString("acctNo"))) {
                        showWarning("请先绑定银行卡");
                        finish();
                    } else {
                        String acctNo = user.getString("acctNo");
                        bank.setText(user.getString("acctBankName") + "\n(" + acctNo.substring(acctNo.length() - 4) + ")");
                    }
                }


            }

            @Override
            public void complete(String str) {

            }
        });
    }

    @Override
    protected void initEvent() {
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String strMoney = et_money.getText().toString();
                if (StringUtils.isEmpty(strMoney)) {
                    showWarning("请输入充值金额");
                    return;
                }
                if (popwindow == null) {
                    popwindow = new PayInPopwindow(PayInActivity.this, strMoney, new PayOnClickListener() {
                        @Override
                        public void onClick() {
                            onPay(strMoney);
                        }

                        @Override
                        public void onCancel() {
                            showCancel();
                        }
                    });
                }
                if (popwindow.isShowing()) {
                    popwindow.dismiss();
                } else {
                    hideInputKeyboard(et_money);
                    popwindow.showAsDropDown(findViewById(R.id.linMain), 0, 0);
                }
            }
        });
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

    private void onPay(String money) {
        Bundle bundle = new Bundle();
        bundle.putString("merUserId", merUserId);
        if (is_ele){
            bundle.putInt("type", PayPasswordActivity.ELE_PAY_IN);
        }else {
            bundle.putInt("type", PayPasswordActivity.WALLET_PAY_IN);
        }
        bundle.putString("money", money);
        startActivityForResult(PayPasswordActivity.class, "验证密码", bundle);
    }

    private void showCancel() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_cancel_pay, null);
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(null)
                .setView(view)
                .show();

        TextView cancel = view.findViewById(R.id.cancel);
        TextView cont = view.findViewById(R.id.cont);
        ImageView close = view.findViewById(R.id.close);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                popwindow.showAsDropDown(findViewById(R.id.linMain), 0, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            setResult(RESULT_OK);
            finish();
        }
    }
}
