package com.jdd.sample.studyapp.room;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jdd.sample.studyapp.R;
import com.jdd.sample.studyapp.room.entity.User;
import com.jdd.sample.studyapp.ui.BaseActivity;

import java.util.List;

public class RoomActivity extends BaseActivity {

    private EditText mNameEt;
    private EditText mAgeEt;
    private EditText mUserIdEt;

    private TextView mQueryTv;

    private AppDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        setToolbarAsBack((v) -> finish());
        setTitle("RoomActivity");

        mUserIdEt = findViewById(R.id.et_uid);
        mNameEt = findViewById(R.id.et_name);
        mAgeEt = findViewById(R.id.et_age);
        mQueryTv = findViewById(R.id.tv_query_result);

        findViewById(R.id.btn_insert).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_delete).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_update).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_query).setOnClickListener(mOnClickListener);

        mDatabase = AppDatabase.getInstance(getApplicationContext());
    }


    private View.OnClickListener mOnClickListener = (v) -> {
        int userId = str2int(mUserIdEt.getText().toString());
        int age = str2int(mAgeEt.getText().toString());
        String name = mNameEt.getText().toString();

        switch (v.getId()) {

            case R.id.btn_insert:
                User user2Insert = new User();
                user2Insert.setAge(age);
                user2Insert.setName(name);
                mDatabase.userDao().insertAll(user2Insert);
                break;

            case R.id.btn_delete:
                User userWillDelete = new User();
                userWillDelete.setUid(userId);
                mDatabase.userDao().delete(userWillDelete);
                break;

            case R.id.btn_update:
                User userWillUpdate = new User();
                userWillUpdate.setUid(userId);
                userWillUpdate.setAge(age);
                userWillUpdate.setName(name);
                mDatabase.userDao().update(userWillUpdate);
                break;

            case R.id.btn_query:
                List<User> userList = mDatabase.userDao().getAll();
                StringBuilder logBuilder = new StringBuilder();
                for (User user : userList) {
                    logBuilder.append(user.toString()).append("\n");
                }
                mQueryTv.setText(logBuilder.toString());
                break;

            default:
                break;
        }
    };


    private int str2int(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
