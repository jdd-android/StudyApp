package com.jdd.sample.studyapp.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.jdd.sample.studyapp.R;
import com.jdd.sample.studyapp.ui.fragment.NormalFragment;
import com.jdd.sample.studyapp.ui.fragment.RecyclerFragment;
import com.jdd.sample.studyapp.ui.fragment.ViewPagerFragment;

public class FragmentActivity extends AppCompatActivity {

    private static final String TAG = FragmentActivity.class.getSimpleName();

    private static final String FRG_TAG_NORMAL = "fragment_normal";
    private static final String FRG_TAG_VIEWPAGER = "fragment_viewpager";
    private static final String FRG_TAG_RECYCLER = "fragment_recycler";

    private FragmentManager mFragmentManager;

    private NormalFragment mNormalFragment;
    private ViewPagerFragment mViewPagerFragment;
    private RecyclerFragment mRecyclerViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        setSupportActionBar(findViewById(R.id.toolbar));
        setTitle("FragmentActivity");

        initView();
    }

    private void initView() {
        // 这个 FrameLayout 是 Fragment 的容器，Fragment 会被填充到这个 FrameLayout 中显示
        FrameLayout fragmentContainerLy = findViewById(R.id.fragment_container);

        findViewById(R.id.btn_normal).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_viewpager).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_recycler_view).setOnClickListener(mOnClickListener);

        // fragment 通过 FragmentManager 来管理，添加、删除、显示 fragment 等
        mFragmentManager = getSupportFragmentManager();
        mNormalFragment = NormalFragment.newInstance();

        // 对 fragment 的操作需要在一个 transaction 中!
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_container, mNormalFragment, FRG_TAG_NORMAL)
                .commit();
        setSelectBottomBtn(R.id.btn_normal);
    }

    private View.OnClickListener mOnClickListener = (v) -> {
        int id = v.getId();
        switch (id) {

            case R.id.btn_normal:
                if (mNormalFragment == null) {
                    // 如果 normal fragment 是空，创建新的 fragment add 到 fragmentManager
                    mNormalFragment = NormalFragment.newInstance();
                    mFragmentManager.beginTransaction()
                            .add(R.id.fragment_container, mNormalFragment, FRG_TAG_NORMAL)
                            .commit();
                } else {
                    // 如果当前显示的不是 normalFragment，就显示 normal fragment
                    mFragmentManager.beginTransaction().replace(R.id.fragment_container, mNormalFragment).commit();
                }
                // 底部导航文字显示为选中状态
                setSelectBottomBtn(R.id.btn_normal);
                break;

            case R.id.btn_viewpager:
                if (mViewPagerFragment == null) {
                    // 如果 viewpager fragment 是空，创建新的 fragment add 到 fragmentManager
                    mViewPagerFragment = ViewPagerFragment.newInstance();
                    mFragmentManager.beginTransaction()
                            .add(R.id.fragment_container, mViewPagerFragment, FRG_TAG_VIEWPAGER)
                            .commit();
                } else {
                    // 如果当前显示的不是 viewpagerFragment，就显示 viewpager fragment
                    mFragmentManager.beginTransaction().replace(R.id.fragment_container, mViewPagerFragment).commit();
                }
                // 底部导航文字显示为选中状态
                setSelectBottomBtn(R.id.btn_viewpager);
                break;

            case R.id.btn_recycler_view:
                if (mRecyclerViewFragment == null) {
                    // 如果 recycler fragment 是空，创建新的 fragment add 到 fragmentManager
                    mRecyclerViewFragment = RecyclerFragment.newInstance();
                    mFragmentManager.beginTransaction()
                            .add(R.id.fragment_container, mRecyclerViewFragment, FRG_TAG_RECYCLER)
                            .commit();
                } else {
                    // 如果当前显示的不是 recyclerFragment，就显示 recycler fragment
                    mFragmentManager.beginTransaction().replace(R.id.fragment_container, mRecyclerViewFragment).commit();
                }
                // 底部导航文字显示为选中状态
                setSelectBottomBtn(R.id.btn_recycler_view);
                break;

            default:
                break;
        }
    };

    /**
     * 设置底部文字的选中状态
     *
     * @param viewId 底部 TextView Id
     */
    private void setSelectBottomBtn(@IdRes int viewId) {
        findViewById(R.id.btn_normal).setSelected(false);
        findViewById(R.id.btn_viewpager).setSelected(false);
        findViewById(R.id.btn_recycler_view).setSelected(false);
        findViewById(viewId).setSelected(true);
    }
}
