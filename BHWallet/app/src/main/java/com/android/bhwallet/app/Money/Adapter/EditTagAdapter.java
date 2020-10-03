package com.android.bhwallet.app.Money.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.bhwallet.R;
import com.android.bhwallet.app.Money.Interface.OnSearchClickListener;
import com.android.bhwallet.utils.Utils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by ZhouXin on 2018/9/14.
 */

public class EditTagAdapter extends TagAdapter<String> {
    private List<String> mEditDatas;
    private Context mContext;
    private LayoutInflater mInflater;
    private OnSearchClickListener listener;
    public EditTagAdapter(Context mCotext, List<String> datas, OnSearchClickListener listener) {
        super(datas);
        this.mEditDatas = datas;
        this.mContext = mCotext;
        this.listener = listener;
        mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public View getView(FlowLayout parent, int position, String str) {
        TextView tv = (TextView) mInflater.inflate(R.layout.flowlaout_item_textview_card,
                parent, false);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int px = Utils.dip2px(mContext, 5);
        lp.setMargins(px, px, px, px);
        tv.setLayoutParams(lp);
        tv.setText(str);
        return tv;
    }


    @Override
    public void onSelected(int position, View view) {
        super.onSelected(position, view);
        listener.onSearch(mEditDatas.get(position));

    }

    @Override
    public void unSelected(int position, View view) {
        super.unSelected(position, view);

    }
}
