package com.asuka.android.asukaandroid.widget.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.asuka.android.asukaandroid.R;


/**
 * 根据宽高比例自动计算高度ImageView
 */
public class RatioImageView extends android.support.v7.widget.AppCompatImageView {

    //宽高比例
    private float mRatio = 0f;
    private Context mContext;



    public RatioImageView(Context context) {
        super(context);
        this.mContext = context;
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
        mRatio = typedArray.getFloat(R.styleable.RatioImageView_ratio, 0f);
        typedArray.recycle();
    }


    /**
     * 设置ImageView的宽高比
     *
     * @param ratio
     */
    public void setRatio(float ratio) {
        this.mRatio = ratio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if (mRatio != 0) {
            float height = width / mRatio;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 处理图片点击变色
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Drawable drawable = getDrawable();
                if (null != drawable) {
                    int color = ContextCompat.getColor(mContext, R.color.color_diveder);
                    drawable.mutate().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Drawable drawableUp = getDrawable();
                if (null != drawableUp) {
                    drawableUp.mutate().clearColorFilter();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

}
