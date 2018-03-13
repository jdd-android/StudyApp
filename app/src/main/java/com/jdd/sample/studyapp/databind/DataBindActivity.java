package com.jdd.sample.studyapp.databind;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.jdd.sample.studyapp.R;
import com.jdd.sample.studyapp.databinding.ActivityDataBindBinding;
import com.jdd.sample.studyapp.ui.BaseActivity;

/**
 * @author lc
 */
public class DataBindActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDataBindBinding activityDataBindBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_bind);
        activityDataBindBinding.setUser(new User("呵呵哒"));

        setToolbarAsBack(v -> {
            finish();
        });
        setTitle("DataBindActivity");

    }

}
