package com.jdd.sample.studyapp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.jdd.sample.studyapp.utils.LogUtils;

/**
 * @author lc. 2018-01-18 10:47
 * @since 1.0.0
 */

public class StaticReceiver extends BroadcastReceiver {

    private static final String TAG = LogUtils.makeLogTag(StaticReceiver.class);

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.LOGI(TAG, "== onReceiver action: " + intent.getAction());
        Toast.makeText(context, TAG + " " + intent.getAction(), Toast.LENGTH_SHORT).show();
    }
}
