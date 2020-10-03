package com.android.bhwallet.app.Main.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.R;
import com.android.bhwallet.app.Card.CardManageActivity;
import com.android.bhwallet.app.ChangeTel.ChangePhoneActivity;
import com.android.bhwallet.app.ChangeTel.CheckPhoneActivity;
import com.android.bhwallet.app.Finance.FinanceManageActivity;
import com.android.bhwallet.app.Fund.UI.FundActivity;
import com.android.bhwallet.app.Fund.UI.FundOpenActivity;
import com.android.bhwallet.app.Money.UI.MoneyActivity;
import com.android.bhwallet.app.Money.UI.MoneyListActivity;
import com.android.bhwallet.app.Money.UI.PayInActivity;
import com.android.bhwallet.app.Money.UI.PayOutActivity;
import com.android.bhwallet.app.PayManage.PayManageActivity;
import com.android.bhwallet.app.Save.UI.SaveActivity;
import com.android.bhwallet.app.UserCenter.UI.QuestionActivity;
import com.android.bhwallet.app.WriteOff.WriteOffActivity;
import com.android.bhwallet.utils.HttpHelper;
import com.android.bhwallet.utils.PreferencesUtil;
import com.android.bhwallet.utils.UrlConfig;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.http.EGRequestParams;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/20
 * 邮箱：825814902@qq.com
 * 描述：
 */

public class WalletMainActivity extends AsukaActivity {
    private LinearLayout ll_open;
    private LinearLayout ll_close;
    private TextView open;
    private TextView finance_open;
    private TextView finance_close;
    private LinearLayout card_manage;
    private LinearLayout pay_manage;
    private LinearLayout change_tel;
    private LinearLayout write_off;
    private ImageView save1;
    private ImageView save2;
    private ImageView tjb1;
    private ImageView tjb2;
    private LinearLayout pay_in;
    private LinearLayout pay_out;
    private TextView money1;
    private TextView money2;
    private TextView share;
    private TextView eacAmt;
    private TextView freezeAmt;
    private TextView settAmt;
    private TextView wallet_num;
    private ImageView eye;

    private LinearLayout ll_question;

    private String merUserId = "";// 111
    private String myMerUserId;

    private Boolean eyeOpen;

    private float fAviAmt;
    private float fShare;
    private float fEacAmt;
    private float fFreezeAmt;
    private float fSettAmt;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_wallet_main);
        ll_open = findViewById(R.id.ll_open);
        ll_close = findViewById(R.id.ll_close);
        open = findViewById(R.id.open);
        finance_open = findViewById(R.id.finance_open);
        finance_close = findViewById(R.id.finance_close);
        card_manage = findViewById(R.id.card_manage);
        pay_manage = findViewById(R.id.pay_manage);
        change_tel = findViewById(R.id.change_tel);
        write_off = findViewById(R.id.write_off);
        save1 = findViewById(R.id.save1);
        save2 = findViewById(R.id.save2);
        tjb1 = findViewById(R.id.tjb1);
        tjb2 = findViewById(R.id.tjb2);
        pay_in = findViewById(R.id.pay_in);
        pay_out = findViewById(R.id.pay_out);
        money1 = findViewById(R.id.money1);
        money2 = findViewById(R.id.money2);
        ll_question = findViewById(R.id.ll_question);
        share = findViewById(R.id.share);
        eacAmt = findViewById(R.id.eacAmt);
        freezeAmt = findViewById(R.id.freezeAmt);
        settAmt = findViewById(R.id.settAmt);
        wallet_num = findViewById(R.id.wallet_num);
        eye = findViewById(R.id.eye);

        eyeOpen = true;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            merUserId = bundle.getString("merUserId");
        }
        checkUser();

    }


    private void checkUser() {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("mUserId", merUserId);
        HttpHelper.postSpecialNoProcess(this, UrlConfig.VIS_ACC_QRY, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);
                JSONObject data = object.getJSONObject("data");
                myMerUserId = data.getString("merUserId");
                //myMerUserId = "12003";
                if (object.getIntValue("code") == 1) {
                    //说明已开通钱包
                    ll_open.setVisibility(View.VISIBLE);
                    ll_close.setVisibility(View.GONE);
                    getWalletMessage();
                    getToken();
                }

            }

            @Override
            public void complete(String str) {

            }
        });
    }

    private void getToken() {
        EGRequestParams para = new EGRequestParams();
        HttpHelper.post(this, UrlConfig.GET_TOKRN, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);
                String token = object.getString("token");
                PreferencesUtil.getInstatnce(WalletMainActivity.this).save("token", token);
            }

            @Override
            public void complete(String str) {

            }
        });
    }


    private void getWalletMessage() {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", myMerUserId);
        HttpHelper.post(this, UrlConfig.WALLET_MESSAGE, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject data = JSON.parseObject(str);
                fAviAmt = data.getFloatValue("aviAmt");
                fShare = data.getFloatValue("share");
                fEacAmt = data.getFloatValue("eacAmt");
                fFreezeAmt = data.getFloatValue("freezeAmt");
                fSettAmt = data.getFloatValue("settAmt");

                showMoney();
            }

            @Override
            public void complete(String str) {

            }
        });
    }

    @Override
    protected void initEvent() {
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId", myMerUserId);
                bundle.putString("mUserId", merUserId);
                startActivity(WalletOpenRulesActivity.class, "账户开通协议", bundle);
            }
        });

        finance_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                financeManage();

            }
        });

        finance_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                financeManage();
            }
        });

        card_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId", myMerUserId);
                startActivity(CardManageActivity.class, "银行卡管理", bundle);
            }
        });

        pay_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId", myMerUserId);
                startActivity(PayManageActivity.class, "支付管理", bundle);
            }
        });
        change_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId", myMerUserId);
                startActivity(ChangePhoneActivity.class, "修改手机号", bundle);
            }
        });

        write_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId", myMerUserId);
                startActivity(WriteOffActivity.class, "账户注销", bundle);
            }
        });

        save1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId", myMerUserId);
                startActivity(WalletOpenRulesActivity.class, "账户开通协议", bundle);
            }
        });
        save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSave();
            }
        });

        tjb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId", myMerUserId);
                startActivity(WalletOpenRulesActivity.class, "账户开通协议", bundle);
            }
        });
        tjb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFund();

            }
        });

        pay_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId", myMerUserId);
                startActivity(PayInActivity.class, "余额充值", bundle);
            }
        });

        pay_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId", myMerUserId);
                startActivity(PayOutActivity.class, "余额提现", bundle);
            }
        });

        money1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId", myMerUserId);
                startActivity(WalletOpenRulesActivity.class, "账户开通协议", bundle);
            }
        });

        money2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId", myMerUserId);
                startActivity(MoneyActivity.class, "我的余额", bundle);
            }
        });

        ll_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(QuestionActivity.class, "钱包常见问题");
            }
        });

        wallet_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId", myMerUserId);
                startActivity(WalletNumActivity.class, "渤金钱包编号", bundle);
            }
        });

        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eyeOpen = !eyeOpen;
                eye.setImageResource(eyeOpen ? R.mipmap.eye_open_icon : R.mipmap.eye_close_icon);
                showMoney();

            }
        });
    }

    private void showMoney() {
        if (eyeOpen) {
            money2.setText(fAviAmt + "元");
            share.setText(fShare + "%");
            eacAmt.setText("￥" + fEacAmt);
            freezeAmt.setText("￥" + fFreezeAmt);
            settAmt.setText("￥" + fSettAmt);

        } else {
            money2.setText("**元");
            share.setText("**%");
            eacAmt.setText("￥**");
            freezeAmt.setText("￥**");
            settAmt.setText("￥**");
        }
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {
        if ("update_user".equals(object.getString("type"))) {
            checkUser();
        }
    }

    private void financeManage() {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", myMerUserId);
        HttpHelper.post(this, UrlConfig.WALLET_OPEN_DETAIL, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);

                Bundle bundle = new Bundle();
                bundle.putString("merUserId", myMerUserId);

                //判断是否开启电子账户
                JSONArray eleAccList = object.getJSONArray("eleAccList");
                if (eleAccList == null || eleAccList.size() == 0) {
                    //说明未开通电子账户
                    startActivity(EleOpenRulesActivity.class, "账户开通协议", bundle);
                } else {
                    JSONObject jsonObject = (JSONObject) eleAccList.get(0);
                    bundle.putString("EacAcctNo", jsonObject.getString("EacAcctNo"));
                    startActivity(FinanceManageActivity.class, "金融服务", bundle);
                }
            }

            @Override
            public void complete(String str) {

            }
        });
    }

    private void openFund() {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", myMerUserId);
        HttpHelper.post(this, UrlConfig.WALLET_OPEN_DETAIL, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);
                JSONArray acctList = object.getJSONArray("acctList");
                Bundle bundle = new Bundle();
                bundle.putString("merUserId", myMerUserId);
                if (acctList != null && acctList.size() > 0) {
                    JSONObject accObject = (JSONObject) acctList.get(0);
                    int fundFlag = accObject.getIntValue("FundFlag");
                    if (fundFlag == 1) {
                        bundle.putString("eleAcctNo", accObject.getString("VirlAcctNo"));
                        startActivity(FundActivity.class, "添金宝", bundle);
                    } else {
                        startActivity(FundOpenActivity.class, "添金宝", bundle);
                    }
                } else {
                    startActivity(FundOpenActivity.class, "添金宝", bundle);
                }
            }

            @Override
            public void complete(String str) {

            }
        });
    }

    private void openSave() {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", myMerUserId);
        HttpHelper.post(this, UrlConfig.WALLET_OPEN_DETAIL, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);

                Bundle bundle = new Bundle();
                bundle.putString("merUserId", myMerUserId);

                //判断是否开启电子账户
                JSONArray eleAccList = object.getJSONArray("eleAccList");
                if (eleAccList == null || eleAccList.size() == 0) {
                    //说明未开通电子账户
                    startActivity(EleOpenRulesActivity.class, "账户开通协议", bundle);
                } else {
                    startActivity(SaveActivity.class, "众智存", bundle);
                }
            }

            @Override
            public void complete(String str) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.tb_detail) {
            Bundle bundle = new Bundle();
            bundle.putString("merUserId", myMerUserId);
            startActivity(MoneyListActivity.class, "账单", bundle);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            getWalletMessage();
        }
    }
}
