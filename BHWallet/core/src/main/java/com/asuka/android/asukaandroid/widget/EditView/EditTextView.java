package com.asuka.android.asukaandroid.widget.EditView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import com.asuka.android.asukaandroid.R;

/**
 * 备注：
 * 作者：王莹
 * 时间：2017/4/25.
 */

public class EditTextView extends android.support.v7.widget.AppCompatEditText {
    private Drawable mClearDrawable;
    private String edit_type;
    private boolean isSee = false;//密码是否可见
    private static final int PASSWORD_MINGWEN = 0x90;
    private static final int PASSWORD_MIWEN = 0x81;
    private int mDrawablePadding = 16;
    String myNamespace = "http://schemas.android.com/apk/res-auto";

    public EditTextView(Context context) {
        this(context, null);
    }

    public EditTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public EditTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        edit_type = attrs.getAttributeValue(myNamespace,
                "edit_type");
        init();
    }

    private void init() {
        //获取EditText的DrawableRight,getCompoundDrawables()获取Drawable的四个位置的数组
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            if (edit_type.equals("name")) {
//          默认显示的是删除按钮
                mClearDrawable = getResources().getDrawable(R.drawable.delete);
            } else if (edit_type.equals("password")) {
                // 默认显示的是明文密文按钮
                mClearDrawable = getResources().getDrawable(R.drawable.icon_eye);
            }
        }
        //设置图标的位置以及大小,getIntrinsicWidth()获取显示出来的大小而不是原图片的大小
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth() + 10, mClearDrawable.getIntrinsicHeight() + 10);
        //默认设置隐藏图标
        setClearIconVisible(false);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setClearIconVisible(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    /**
     * 设置晃动动画
     */
    public void setShakeAnimation() {
        this.startAnimation(shakeAnimation(3));
    }

    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                //getTotalPaddingRight()图标左边缘至控件右边缘的距离
                //getWidth() - getTotalPaddingRight()表示从最左边到图标左边缘的位置
                //getWidth() - getPaddingRight()表示最左边到图标右边缘的位置
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    if (edit_type != null) {
                        if (edit_type.equals("name")) {
                            this.setText("");
                        } else if (edit_type.equals("password")) {
                            if (isSee) {
                                //设置不可见
                                this.setInputType(PASSWORD_MIWEN);//密文
                                this.setSelection(this.length());//设置光标显示
                                mClearDrawable = getResources().getDrawable(R.drawable.icon_eye);
                                setIcon(mClearDrawable);
                            } else {
                                //设置可见
                                this.setInputType(PASSWORD_MINGWEN);//明文
                                this.setSelection(this.length());//设置光标显示
                                mClearDrawable = getResources().getDrawable(R.drawable.icon_openeye);
                                setIcon(mClearDrawable);
                            }
                            isSee = !isSee;
                        }
                    } else {
                        //默认为进行删除操作
                        this.setText("");
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void setIcon(Drawable mDeleIcon) {
        //设置图标的位置以及大小,getIntrinsicWidth()获取显示出来的大小而不是原图片的大小
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth() + 10, mClearDrawable.getIntrinsicHeight() + 10);
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[0], mDeleIcon, getCompoundDrawables()[0]);
    }
}
