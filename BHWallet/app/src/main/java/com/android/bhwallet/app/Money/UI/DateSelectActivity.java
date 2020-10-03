package com.android.bhwallet.app.Money.UI;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.alibaba.fastjson.JSONObject;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.view.annotation.ContentView;
import com.asuka.android.asukaandroid.view.annotation.ViewInject;
import com.flyco.tablayout.SlidingTabLayout;
import com.android.bhwallet.R;
import com.android.bhwallet.app.Money.Fragment.DayScreenFragment;
import com.android.bhwallet.app.Money.Fragment.MonthScreenFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/30
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class DateSelectActivity extends AsukaActivity {
    private SlidingTabLayout tabLayout;
    private ViewPager pager;

    private final String[] mTitles = {"按月选择", "按日选择"};
    private List<Fragment> mFragments = new ArrayList<>();
    @Override
    protected void initView() {
        setContentView(R.layout.activity_date_select);
        tabLayout = findViewById(R.id.tabLayout);
        pager = findViewById(R.id.pager);

        mFragments.add(MonthScreenFragment.getInstance());
        mFragments.add(DayScreenFragment.getInstance());
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
            return mFragments.get(position);
        }
    }
}
