package com.jdd.sample.studyapp.ui.customview;

import android.os.Bundle;
import android.widget.TextView;

import com.jdd.sample.studyapp.R;
import com.jdd.sample.studyapp.ui.BaseActivity;

/**
 * @author lc
 */
public class CustomViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        setSupportActionBar(findViewById(R.id.toolbar));
        setTitle("CustomViewActivity");

        showTagView();
    }

    private void showTagView() {
        TagLayout<String> tagLayout = findViewById(R.id.tag_layout);
        tagLayout.setTagActionCallBack((tag, view) -> {
            TextView tagTv = view.findViewById(R.id.tv_tag);
            tagTv.setText(tag);
        });
        tagLayout.setTags("百度", "阿里巴巴", "腾讯", "京东", "小米", "美团", "滴滴", "奖多多科技有限公司", "淘宝", "Google",
                "这是一个长度很长的 TAG，必需要换行才行，幺幺，切克闹，煎饼果子来一套");
    }

}
