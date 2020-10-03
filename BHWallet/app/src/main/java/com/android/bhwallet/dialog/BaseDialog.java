package com.android.bhwallet.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.view.View;
import android.view.Window;

import com.android.bhwallet.R;


public abstract class BaseDialog extends Dialog implements OnShowListener {

    protected Context mContext;
    protected View mainView;

    public BaseDialog(Context context) {
        super(context, R.style.DialogTheme);
        this.mContext = context;
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutResID());
        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    protected abstract int getLayoutResID();

    protected void init() {
        initView();
        setListener();
    }

    protected abstract void initView();

    protected void setListener() {
        setOnShowListener(this);
    }

    @Override
    public void onShow(DialogInterface arg0) {
        if (initEffectsType() != null) {
            BaseEffects animator = initEffectsType().getAnimator();
            animator.start(mainView);
        }
    }

    protected EffectsType initEffectsType() {
        return EffectsType.SlideBottom;// default SlideBottom
    }

}
