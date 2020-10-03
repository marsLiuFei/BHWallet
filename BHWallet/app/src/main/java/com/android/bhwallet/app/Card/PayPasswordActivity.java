package com.android.bhwallet.app.Card;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.R;
import com.android.bhwallet.app.Fund.UI.FundActivity;
import com.android.bhwallet.app.PayManage.ResetPasswordActivity;
import com.android.bhwallet.app.PayManage.SetNewPasswordActivity;
import com.android.bhwallet.app.WriteOff.WriteOffSuccessActivity;
import com.android.bhwallet.dialog.CustomAlertDialog;
import com.android.bhwallet.utils.CodeView.VerificationCodeView;
import com.android.bhwallet.utils.HttpHelper;
import com.android.bhwallet.utils.UrlConfig;
import com.android.bhwallet.views.Dialogs.CustomDialog;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.comm.utils.StringUtils;
import com.asuka.android.asukaandroid.http.EGRequestParams;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/24
 * 邮箱：825814902@qq.com
 * 描述：输入支付密码
 */

public class PayPasswordActivity extends AsukaActivity implements VerificationCodeView.OnCodeFinishListener {
    private TextView title;
    private VerificationCodeView password;

    public static int BIND_CARD = 1;
    public static int UNBIND_CARD = 2;
    public static int RESET_PASSWORD = 3;
    public static int WRITE_OFF = 4;
    public static int WALLET_PAY_IN = 5;
    public static int WALLET_PAY_OUT = 6;
    public static int OPEN_FUND = 7;
    public static int CLOSE_FUND = 8;
    public static int ELE_PAY_IN = 9;
    public static int ELE_PAY_OUT = 10;
    public static int ELE_WRITE_OFF = 11;
    private int type = 0;

    private String merUserId;
    private String money;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_pay_password);
        title = findViewById(R.id.title);
        password = findViewById(R.id.password);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getInt("type");
            title.setText(bundle.getString("title"));
            merUserId = bundle.getString("merUserId");
            money = bundle.getString("money");
        }
    }

    @Override
    protected void initEvent() {
        password.setOnCodeFinishListener(this);
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

    @Override
    public void onTextChange(View view, String content) {

    }

    @Override
    public void onComplete(View view, String content) {
        if (type == WRITE_OFF) {
            writeOffAccount(content);
        } else if (type == OPEN_FUND) {
            openFund(content);
        } else if (type == CLOSE_FUND) {
            closeFund(content);
        } else if (type == WALLET_PAY_OUT) {
            walletPayOut(content);

        } else if (type == ELE_PAY_OUT) {
            elePayOut(content);
        } else if (type == ELE_WRITE_OFF) {
            eleWriteOff(content);
        } else {
            checkPassword(content);
        }


    }

    private void checkPassword(final String password) {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", merUserId);
        para.addBodyParameter("password", password);
        HttpHelper.post(this, UrlConfig.PASSWORD_CHECK, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId", merUserId);
                if (type == BIND_CARD) {
                    bundle.putString("password", password);
                    startActivity(NewCardActivity.class, "变更绑定卡", bundle);
                    finish();
                } else if (type == UNBIND_CARD) {
                    unBindCard(password);

                } else if (type == RESET_PASSWORD) {
                    bundle.putString("oldPassword", password);
                    startActivity(SetNewPasswordActivity.class, "支付密码重置", bundle);
                    finish();
                } else if (type == WRITE_OFF) {

                } else if (type == WALLET_PAY_IN) {
                    walletPayIn(password);
                } else if (type == WALLET_PAY_OUT) {

                } else if (type == ELE_PAY_IN) {
                    elePayIn(password);
                }
            }

            @Override
            public void complete(String str) {
                if (!StringUtils.isEmpty(str)){
                    CustomDialog dialog = new CustomDialog(PayPasswordActivity.this);
                    dialog.setMessage("密码错误，请重试");
                    dialog.setOkButton("重试", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.setCancelButton("忘记密码", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Bundle bundle = new Bundle();
                            bundle.putString("merUserId",merUserId);
                            startActivity(ResetPasswordActivity.class,"找回密码",bundle);
                        }
                    });
                    dialog.show();
                }
            }
        });

    }

    private void writeOffAccount(String password) {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", merUserId);
        para.addBodyParameter("Password", password);
        HttpHelper.post(this, UrlConfig.WRITE_OFF_ACC, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                startActivity(WriteOffSuccessActivity.class, "注销账户");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void complete(String str) {

            }
        });


    }

    private void unBindCard(String password) {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", merUserId);
        para.addBodyParameter("password", password);
        HttpHelper.post(this, UrlConfig.UNBIND_CARD, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "解除绑定成功！");
                startActivity(ChangeSuccessActivity.class, "解绑银行卡", bundle);
                sendObseverMsg("card_update", new JSONObject());
                finish();
            }

            @Override
            public void complete(String str) {

            }
        });

    }

    private void openFund(String password) {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", merUserId);
        para.addBodyParameter("password", password);
        HttpHelper.post(this, UrlConfig.OPEN_FUND, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                startActivity(FundActivity.class, "添金宝");
                finish();
            }

            @Override
            public void complete(String str) {

            }
        });
    }

    private void closeFund(String password) {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", merUserId);
        para.addBodyParameter("password", password);
        HttpHelper.post(this, UrlConfig.CLOSE_FUND, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                showSuccess("已解约添金宝");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void complete(String str) {

            }
        });
    }

    private void walletPayIn(String password) {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", merUserId);
        para.addBodyParameter("password", password);
        para.addBodyParameter("transAmount", money);
        HttpHelper.post(this, UrlConfig.WALLET_PAY_IN, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                showSuccess("充值成功");
                Bundle bundle = new Bundle();
                bundle.putString("title", "充值成功！");
                startActivity(ChangeSuccessActivity.class, "充值成功！", bundle);
                finish();
            }

            @Override
            public void complete(String str) {

            }
        });
    }

    private void elePayIn(String password) {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", merUserId);
        para.addBodyParameter("password", password);
        para.addBodyParameter("transAmount", money);
        HttpHelper.post(this, UrlConfig.ELE_PAY_IN, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                showSuccess("充值成功");
                Bundle bundle = new Bundle();
                bundle.putString("title", "充值成功！");
                startActivity(ChangeSuccessActivity.class, "充值成功！", bundle);
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void complete(String str) {

            }
        });
    }

    private void walletPayOut(String password) {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", merUserId);
        para.addBodyParameter("password", password);
        para.addBodyParameter("transAmount", money);
        HttpHelper.post(this, UrlConfig.WALLET_PAY_OUT, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "提现成功！");
                startActivity(ChangeSuccessActivity.class, "提现成功！", bundle);
                finish();
            }

            @Override
            public void complete(String str) {

            }
        });
    }

    private void elePayOut(String password) {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", merUserId);
        para.addBodyParameter("password", password);
        para.addBodyParameter("transAmount", money);
        HttpHelper.post(this, UrlConfig.ELE_PAY_OUT, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "提现成功！");
                startActivity(ChangeSuccessActivity.class, "提现成功！", bundle);
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void complete(String str) {

            }
        });
    }

    private void eleWriteOff(String password) {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", merUserId);
        para.addBodyParameter("password", password);
        HttpHelper.post(this, UrlConfig.ELE_WRITE_OFF, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {

                startActivity(WriteOffSuccessActivity.class, "注销账户");

                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void complete(String str) {

            }
        });


    }
}
