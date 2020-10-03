package com.android.bhwallet.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * 备注：
 * 作者：王莹
 * 时间：2017/5/4.
 * ~_~想睡觉了!!
 * (～o～)~zZ我想睡啦～
 * π_π?打瞌睡
 */

public class FontsTextView extends android.support.v7.widget.AppCompatTextView {
    Context context;

    public FontsTextView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public FontsTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public FontsTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), "wangangziti.ttf"));
    }

}
