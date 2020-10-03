package com.android.bhwallet.app.Save.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.alibaba.fastjson.JSONObject;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.view.annotation.ContentView;
import com.asuka.android.asukaandroid.view.annotation.ViewInject;
import com.flyco.tablayout.SlidingTabLayout;
import com.android.bhwallet.R;
import com.android.bhwallet.app.Save.Fragment.MyProductFragment;
import com.android.bhwallet.app.Save.Fragment.SaveProductFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/26
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class SaveActivity extends AsukaActivity {
    private SlidingTabLayout tabLayout;
    private ViewPager pager;

    private final String[] mTitles = {"存款产品", "我的存款"};
    private List<Fragment> mFragments = new ArrayList<>();

    private String merUserId;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_save);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            merUserId = bundle.getString("merUserId");
        }

        tabLayout = findViewById(R.id.tabLayout);
        pager = findViewById(R.id.pager);
        mFragments.add(SaveProductFragment.getInstance());
        mFragments.add(MyProductFragment.getInstance());
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.setOffscreenPageLimit(mFragments.size());
        tabLayout.setViewPager(pager);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putString("merUserId",merUserId);
            Fragment fragment = mFragments.get(position);
            fragment.setArguments(bundle);
            return fragment;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_describe,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.tb_describe){
            startActivity(ProduceDetailActivity.class,"产品详情");
        }
        return super.onOptionsItemSelected(item);
    }
}
