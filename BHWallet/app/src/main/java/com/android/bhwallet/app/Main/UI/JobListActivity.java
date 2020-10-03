package com.android.bhwallet.app.Main.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.R;
import com.android.bhwallet.utils.DividerItemDecoration;
import com.android.bhwallet.utils.FullyGridLayoutManager;
import com.android.bhwallet.utils.HttpHelper;
import com.android.bhwallet.utils.UrlConfig;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.http.EGRequestParams;
import com.asuka.android.asukaandroid.widget.recycleView.BaseViewHolder;
import com.asuka.android.asukaandroid.widget.recycleView.RecyclerView;
import com.asuka.android.asukaandroid.widget.recycleView.UITableViewDelegate;

/**
 * @author 蒋冬冬
 * 创建日期：2020/8/24
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class JobListActivity extends AsukaActivity implements UITableViewDelegate {
    private RecyclerView recyclerView;

    JSONArray array;
    @Override
    protected void initView() {
        setContentView(R.layout.comm_recycleview);
        recyclerView = findViewById(R.id.recyclerView);

        array = new JSONArray();
        recyclerView.getRecyclerView().setLayoutManager(new FullyGridLayoutManager(this,1));
        recyclerView.getRecyclerView().addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST,true));
        recyclerView.setDelegate(this);

        getData();
    }

    private void getData(){
        EGRequestParams object = new EGRequestParams();
        HttpHelper.post(this, UrlConfig.GET_JOB_LIST, object, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject data = JSON.parseObject(str);
                JSONArray jsonArray = data.getJSONArray("list");
                array.addAll(jsonArray);
                recyclerView.setDataSource(array);
            }

            @Override
            public void complete(String str) {

            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

    @Override
    public android.support.v7.widget.RecyclerView.ViewHolder getItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_test,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindData(BaseViewHolder hoderView, int position) {
        ViewHolder holder = (ViewHolder) hoderView;
        final JSONObject object = (JSONObject) array.get(position);
        holder.title.setText(object.getString("workType"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("name",object.getString("workType"));
                bundle.putString("id",object.getString("workNum"));
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

    private class ViewHolder extends BaseViewHolder{
        private TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }

}
