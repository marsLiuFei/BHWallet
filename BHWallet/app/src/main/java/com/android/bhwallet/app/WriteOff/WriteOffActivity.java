package com.android.bhwallet.app.WriteOff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.view.annotation.ContentView;
import com.asuka.android.asukaandroid.view.annotation.Event;
import com.android.bhwallet.R;
import com.android.bhwallet.app.Card.PayPasswordActivity;

import static com.android.bhwallet.app.Card.PayPasswordActivity.ELE_WRITE_OFF;
import static com.android.bhwallet.app.Card.PayPasswordActivity.WRITE_OFF;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/26
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class WriteOffActivity extends AsukaActivity {

    private Button commit;

    private String merUserId;
    private Boolean is_ele;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_write_off);
        commit = findViewById(R.id.commit);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            merUserId = bundle.getString("merUserId");
            is_ele = bundle.getBoolean("is_ele", false);
        }
    }

    @Override
    protected void initEvent() {
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                if (is_ele) {
                    bundle.putInt("type", ELE_WRITE_OFF);
                } else {
                    bundle.putInt("type", WRITE_OFF);
                }
                bundle.putString("merUserId", merUserId);
                startActivityForResult(PayPasswordActivity.class, "注销账户", bundle);
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
            Bundle bundle = new Bundle();
            if (is_ele) {
                bundle.putString("type", "ele_write_off");
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
            } else{
                sendObseverMsg("update_user", new JSONObject());
            }

            finish();
        }
    }
}
