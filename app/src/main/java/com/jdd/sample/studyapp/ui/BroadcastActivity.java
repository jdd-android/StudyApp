package com.jdd.sample.studyapp.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jdd.sample.studyapp.R;
import com.jdd.sample.studyapp.broadcast.Const;

public class BroadcastActivity extends AppCompatActivity {

    private TextView mReceiverActionTv;

    private MyBroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        setSupportActionBar(findViewById(R.id.toolbar));
        setTitle("BroadcastActivity");

        mReceiverActionTv = findViewById(R.id.tv_receiver_action);

        // 注册广播
        mReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Const.ACTION_TEST);
        filter.setPriority(100);
        registerReceiver(mReceiver, filter);
    }

    public void sendBroadcast(View view) {
        Intent intent = new Intent(Const.ACTION_TEST);
        // 发送广播
//        sendBroadcast(intent);
        // 发送有序广播
        sendOrderedBroadcast(intent, null);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                mReceiverActionTv.append("网络连接发生变化\n");
            }
            if (Const.ACTION_TEST.equals(intent.getAction())) {
                mReceiverActionTv.append("收到一条广播\n");
                // 中断广播
                  abortBroadcast();
            }
        }
    }
}
