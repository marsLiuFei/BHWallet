package com.android.bhwallet.dialog;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.bhwallet.R;


/**
 * 自定义对话框
 * <p/>
 * roy
 */

public class CustomAlertDialog extends BaseDialog implements OnClickListener {

    private TextView mTitle;
    private LinearLayout mLlContent;
    private Button mLeftBtn, mRightBtn;
    private OnNewClickListener listener;

    public CustomAlertDialog(Context context) {
        super(context);
        init();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.custom_alert_dialog;
    }

    @Override
    protected void initView() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        mainView = findViewById(R.id.main);
        mTitle = findViewById(R.id.custom_alert_dialog_title);
        mLlContent = findViewById(R.id.custom_alert_dialog_content);
        mLeftBtn = findViewById(R.id.custom_alert_dialog_btn_left);
        mRightBtn = findViewById(R.id.custom_alert_dialog_btn_right);
    }

    public void setTitle(CharSequence message) {
        mTitle.setText(message);
    }

    public void setTitle(int messageId) {
        mTitle.setText(messageId);
    }

    public void addContentView(View view) {
        mLlContent.addView(view);
    }

    public void setLeftButton(CharSequence text) {
        mLeftBtn.setText(text);
    }

    public void setLeftButton(int textId) {
        mLeftBtn.setText(textId);
    }

    public void setRightButton(CharSequence text) {
        mRightBtn.setText(text);
    }

    public void setRightButton(int textId) {
        mRightBtn.setText(textId);
    }

    public void setOnNewClickListener(OnNewClickListener l) {
        this.listener = l;
    }

    @Override
    protected void setListener() {
        super.setListener();
        mLeftBtn.setOnClickListener(this);
        mRightBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.custom_alert_dialog_btn_left) {
            if (listener != null)
                listener.onLeftClick();
        } else if (id == R.id.custom_alert_dialog_btn_right) {
            if (listener != null)
                listener.onRightClick();
        }
        dismiss();
    }

    @Override
    protected EffectsType initEffectsType() {
        return null;
    }

    public void setRightBtnInvisible(){
        mRightBtn.setVisibility(View.GONE);
    }

}
