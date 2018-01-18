package com.jdd.sample.studyapp.ui;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.jdd.sample.studyapp.R;
import com.jdd.sample.studyapp.provider.PreferenceProvider;

public class ProviderActivity extends AppCompatActivity {

    private EditText mKeyEt;
    private EditText mValueEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        setSupportActionBar(findViewById(R.id.toolbar));
        setTitle("ProviderActivity");

        mKeyEt = findViewById(R.id.et_key);
        mValueEt = findViewById(R.id.et_value);

        findViewById(R.id.btn_insert).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_delete).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_update).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_query).setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = (v) -> {
        int id = v.getId();
        switch (id) {
            case R.id.btn_insert:
                ContentValues values = new ContentValues();
                values.put(mKeyEt.getText().toString(), mValueEt.getText().toString());
                getContentResolver().insert(PreferenceProvider.CONTENT_URI, values);
                break;

            case R.id.btn_delete:
                getContentResolver().delete(PreferenceProvider.CONTENT_URI, mKeyEt.getText().toString(), null);
                break;

            case R.id.btn_update:
                values = new ContentValues();
                values.put(mKeyEt.getText().toString(), mValueEt.getText().toString());
                getContentResolver().update(PreferenceProvider.CONTENT_URI, values, mKeyEt.getText().toString(), null);
                break;

            case R.id.btn_query:
                getContentResolver().query(PreferenceProvider.CONTENT_URI, null, mKeyEt.getText().toString(), null, null);
                break;

            default:
                break;
        }
    };
}
