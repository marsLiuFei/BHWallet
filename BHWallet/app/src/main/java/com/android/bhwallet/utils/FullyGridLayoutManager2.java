package com.android.bhwallet.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

public class FullyGridLayoutManager2 extends GridLayoutManager {
    private int mwidth = 0;
    private int mheight = 0;
    private int[] mMeasuredDimension = new int[2];
    int col=0,spanCount;
    OnGetViewWidth ongetViewWidth;
    public FullyGridLayoutManager2(Context context, int spanCount, OnGetViewWidth onget) {
        super(context, spanCount);
        this.spanCount=spanCount;
        this.ongetViewWidth=onget;
    }

    public FullyGridLayoutManager2(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    public int getMwidth() {
        return this.mwidth;
    }

    public void setMwidth(int mwidth) {
        this.mwidth = mwidth;
    }

    public int getMheight() {
        return this.mheight;
    }

    public void setMheight(int mheight) {
        this.mheight = mheight;
    }

    public void onMeasure(Recycler recycler, State state, int widthSpec, int heightSpec) {
        int widthMode = MeasureSpec.getMode(widthSpec);
        int heightMode = MeasureSpec.getMode(heightSpec);
        int widthSize = MeasureSpec.getSize(widthSpec);
        int heightSize = MeasureSpec.getSize(heightSpec);
        int width = 0;
        int height = 0;
        int count = this.getItemCount();
        int span = this.getSpanCount();
        col= (int) Math.ceil(count / span);
        ongetViewWidth.onGet(widthSize);
        for(int i = 0; i < count; ++i) {
            this.measureScrapChild(recycler, i, MeasureSpec.makeMeasureSpec(i, 0), MeasureSpec.makeMeasureSpec(i, 0), this.mMeasuredDimension);
            if(this.getOrientation() == 0) {
                if(i % span == 0) {
                    width += this.mMeasuredDimension[0];
                }

                if(i == 0) {
                    height = this.mMeasuredDimension[1];
                }
            } else {
                if(i % span == 0) {
                    height += this.mMeasuredDimension[1];
                }

                if(i == 0) {
                    width = this.mMeasuredDimension[0];
                }
            }
        }

        switch(widthMode) {
            case 1073741824:
                width = widthSize;
                height=widthSize*(col-1)/spanCount+widthSize/(spanCount*2) +col+5;
            case -2147483648:
            case 0:
            default:
                switch(heightMode) {
                    case 1073741824:
                        height = height;
                    case -2147483648:
                    case 0:
                    default:
                        this.setMheight(height);
                        this.setMwidth(width);
                        this.setMeasuredDimension(width, height);
                }
        }
    }

    private void measureScrapChild(Recycler recycler, int position, int widthSpec, int heightSpec, int[] measuredDimension) {
        if(position < this.getItemCount()) {
            try {
                View e = recycler.getViewForPosition(0);
                if(e != null) {
                    LayoutParams p = (LayoutParams)e.getLayoutParams();
                    int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, this.getPaddingLeft() + this.getPaddingRight(), p.width);
                    int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, this.getPaddingTop() + this.getPaddingBottom(), p.height);
                    e.measure(childWidthSpec, childHeightSpec);
                    measuredDimension[0] = e.getMeasuredWidth() + p.leftMargin + p.rightMargin;
                    measuredDimension[1] = e.getMeasuredHeight() + p.bottomMargin + p.topMargin;
                    recycler.recycleView(e);
                }
            } catch (Exception var10) {
                var10.printStackTrace();
            }
        }

    }
   public interface OnGetViewWidth{
       public void onGet(int a);
    }
}
