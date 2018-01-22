package com.jdd.sample.studyapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jdd.sample.studyapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lc. 2018-01-22 10:03
 * @since 0.5.1
 */

public class ViewPagerFragment extends Fragment {

    private View mRoot;
    private TabLayout mTabLy;
    private ViewPager mViewpager;

    private MyViewPagerAdapter mAdapter;

    private TabLayout.OnTabSelectedListener mTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mViewpager.setCurrentItem(tab.getPosition(), true);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    public static ViewPagerFragment newInstance() {
        return new ViewPagerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_viewpager, container, false);

        mTabLy = mRoot.findViewById(R.id.tab_layout);
        mViewpager = mRoot.findViewById(R.id.viewpager);

        // 将 TabLayout 绑定到 ViewPager，TabLayout 会读取对应 viewpager title 显示
        mTabLy.setupWithViewPager(mViewpager);
        // 监听 tab 点击，切换到对应的 pager
        mTabLy.addOnTabSelectedListener(mTabSelectedListener);
        // 设置 Viewpager 的适配器
        mAdapter = new MyViewPagerAdapter();
        mViewpager.setAdapter(mAdapter);

        // 初始化 5 个 pager 显示到 viewpager
        List<Pager> pagerList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            pagerList.add(new Pager("pager-" + i));
        }
        mAdapter.addPager(pagerList);

        return mRoot;
    }

    private class Pager {
        private String mTitle;
        private View mView;

        private Pager(String title) {
            mTitle = title;
        }

        private String getTitle() {
            return mTitle;
        }

        private View getView(LayoutInflater inflater, ViewGroup parent) {
            if (mView != null) {
                return mView;
            }
            mView = inflater.inflate(R.layout.layout_viewpager, parent, false);
            ((TextView) mView.findViewById(R.id.tv_text)).setText(getTitle());
            return mView;
        }
    }

    private class MyViewPagerAdapter extends PagerAdapter {

        private List<Pager> mPagerList = new ArrayList<>();

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Pager pager = mPagerList.get(position);
            View pagerView = pager.getView(getLayoutInflater(), container);
            container.addView(pagerView);
            return pagerView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mPagerList.get(position).getView(getLayoutInflater(), container));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mPagerList.get(position).getTitle();
        }

        private void addPager(List<Pager> list) {
            mPagerList.addAll(list);
            notifyDataSetChanged();
        }
    }
}
