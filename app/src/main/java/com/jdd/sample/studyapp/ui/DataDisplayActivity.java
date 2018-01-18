package com.jdd.sample.studyapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jdd.sample.studyapp.R;
import com.jdd.sample.studyapp.data.Person;

import java.util.Set;

public class DataDisplayActivity extends AppCompatActivity {

    private static final String EXTAR_PERSON = "extra_person";
    private static final String EXTRA_STR = "extra_str";
    private static final String EXTRA_BUNDLE = "extra_bundle";

    private TextView mDataTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_display);

        setSupportActionBar(findViewById(R.id.toolbar));
        setTitle("DataDisplayActivity");

        mDataTv = findViewById(R.id.tv_data_from_intent);

        initData();
    }

    public static void open(Context context, Person person, String str, Bundle bundle) {
        Intent intent = new Intent(context, DataDisplayActivity.class);
        intent.putExtra(EXTAR_PERSON, person);
        intent.putExtra(EXTRA_STR, str);
        intent.putExtra(EXTRA_BUNDLE, bundle);

        context.startActivity(intent);
    }

    private void initData() {
        Intent intent = getIntent();
        Person person = intent.getParcelableExtra(EXTAR_PERSON);
        String str = intent.getStringExtra(EXTRA_STR);
        Bundle bundle = intent.getBundleExtra(EXTRA_BUNDLE);

        mDataTv.append(String.valueOf(person) + "\n");
        mDataTv.append(str + "\n");
        mDataTv.append(bundle2str(bundle));
    }

    private String bundle2str(Bundle bundle) {
        if (bundle == null) {
            return "null";
        }

        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("[");

        Set<String> keys = bundle.keySet();
        for (String key : keys) {
            resultBuilder.append(key).append("=").append(String.valueOf(bundle.get(key))).append(" ");
        }

        if (resultBuilder.length() > 1) {
            resultBuilder.deleteCharAt(resultBuilder.length() - 1);
        }

        resultBuilder.append("]");
        return resultBuilder.toString();
    }

}
