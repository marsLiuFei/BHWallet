package com.asuka.android.asukaandroid.widget.recycleView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.asuka.android.asukaandroid.R;
import com.asuka.android.asukaandroid.comm.utils.LogUtil;
import com.asuka.android.asukaandroid.view.EmptyView;

import java.util.List;

/**
 * Created by lugao on 16/5/19.
 */
public class RecyclerView extends FrameLayout {

    private android.support.v7.widget.RecyclerView recyclerView;
    private UITableViewDelegate delegate;
    private BaseListAdapter adapter;
    private List<?> dataSource;
    private static String TAG = "RecyclerView";

    public RecyclerView(Context context) {
        this(context, null);

    }

    public android.support.v7.widget.RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public RecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }



    private void init(Context context) {

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.view_recycler, this, true);
        recyclerView = findViewById(R.id.eg_recyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new BaseListAdapter(getContext(), this.dataSource);
        recyclerView.setAdapter(adapter);
    }

    public void setDelegate(UITableViewDelegate delegate) {
        this.delegate = delegate;
        this.setDataSource(this.dataSource);
    }


    /**
     * 设置数据源
     * @param dataSource
     */
    public void setDataSource(List<?> dataSource) {
        this.dataSource = dataSource;
        this.adapter.updateView(this.dataSource);
        if (this.dataSource != null && this.dataSource.size() > 0) {
            recyclerView.setVisibility(VISIBLE);

        } else {
            recyclerView.setVisibility(GONE);

        }

    }
    /**
     * 带动画的刷新
     */
    public void reMoveItem(int index) {
        this.adapter.notifyItemRemoved(index);

    }

    //=======================适配器

    /**
     * Auther:egojit
     * Time:2016-1-28
     * QQ:408365330
     * Mark:基础列表适配器
     */
    private class BaseListAdapter<T extends BaseViewHolder> extends android.support.v7.widget.RecyclerView.Adapter {

        private List<?> mList;
        private Context mContext;

        public BaseListAdapter(Context context, List<?> list) {
            this.mList = list;
            this.mContext = context;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        public void updateView(List<?> list) {
            this.mList = list;
            this.notifyDataSetChanged();
        }

        @Override
        public android.support.v7.widget.RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if (RecyclerView.this.delegate != null)
                return RecyclerView.this.delegate.getItemViewHolder(parent, viewType);
            else {
                LogUtil.e("请给RecyclerView设置delegate");
                return null;
            }
        }

        @Override
        public void onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder viewHolder, int position) {
            //LogUtil.d("onBindViewHolder, i: " + position + ", viewHolder: " + viewHolder);
            T holder = (T) viewHolder;
            holder.position = position;
            if (RecyclerView.this.delegate != null)
                RecyclerView.this.delegate.onBindData(holder, position);
            else {
                LogUtil.e("请给RecyclerView设置delegate");

            }

        }

        @Override
        public int getItemCount() {
            if (mList != null)
                return mList.size();
            return 0;
        }
    }

}
