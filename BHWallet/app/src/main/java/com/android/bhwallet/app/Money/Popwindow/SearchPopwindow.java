package com.android.bhwallet.app.Money.Popwindow;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.android.bhwallet.R;
import com.android.bhwallet.app.Money.Adapter.EditTagAdapter;
import com.android.bhwallet.app.Money.Interface.OnSearchClickListener;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/30
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class SearchPopwindow extends PopupWindow {
    private Context context;
    private OnSearchClickListener listener;

    private ArrayList list = new ArrayList<>(Arrays.asList("全部", "提现", "支出", "收入", "退款"));
    private EditTagAdapter adapter;

    public SearchPopwindow(Context context, OnSearchClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_search, null);
        setContentView(view);

        TagFlowLayout flowLayout = view.findViewById(R.id.flow_layout);
        adapter = new EditTagAdapter(context, list, new OnSearchClickListener() {
            @Override
            public void onSearch(String content) {
                listener.onSearch(content);
                dismiss();
            }
        });
        flowLayout.setAdapter(adapter);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);//设置outside可点击
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(0x44000000));//设置透明背景
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            setHeight(height);
        }
        super.showAsDropDown(anchor);
    }
}
