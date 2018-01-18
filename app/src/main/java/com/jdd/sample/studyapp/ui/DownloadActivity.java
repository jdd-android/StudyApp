package com.jdd.sample.studyapp.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jdd.sample.studyapp.R;
import com.jdd.sample.studyapp.service.DownloadIntentService;
import com.jdd.sample.studyapp.service.DownloadService;
import com.jdd.sample.studyapp.service.ProgressListener;
import com.jdd.sample.studyapp.utils.LogUtils;

import java.util.Locale;

public class DownloadActivity extends AppCompatActivity {
    private static final String TAG = LogUtils.makeLogTag(DownloadActivity.class);

    private TextView mProgressTv;

    private boolean mIsDownloading;
    private boolean mIsServiceBindOk;

    private DownloadServiceConnection mServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        initToolbar();
        initView();
    }

    private void initToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        setTitle("Download");
    }

    private void initView() {
        mProgressTv = findViewById(R.id.tvProgress);

        findViewById(R.id.btnDownloadService).setOnClickListener(mOnClickListener);
        findViewById(R.id.btnDownloadIntentService).setOnClickListener(mOnClickListener);
    }

    /**
     * 使用 service 下载
     */
    private void downloadWithService() {
        if (mIsDownloading) {
            Toast.makeText(this, "正在下载...", Toast.LENGTH_SHORT).show();
            return;
        }

        // 创建 service 连接监听器，实现 service 与 activity 之间的通信
        mServiceConnection = new DownloadServiceConnection();
        Intent intent = new Intent(this, DownloadService.class);
        mIsServiceBindOk = bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        if (mIsServiceBindOk) {
            mIsDownloading = true;
        }
    }

    /**
     * 使用 intent service 下载
     */
    private void downloadWithIntentService() {
        Intent intent = new Intent(this, DownloadIntentService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        // activity destroy 时，需要将 service 解绑
        if (mServiceConnection != null && mIsServiceBindOk) {
            unbindService(mServiceConnection);
        }
        super.onDestroy();
    }

    private View.OnClickListener mOnClickListener = (v) -> {
        int id = v.getId();
        switch (id) {
            case R.id.btnDownloadService:
                downloadWithService();
                break;
            case R.id.btnDownloadIntentService:
                downloadWithIntentService();
                break;
            default:
                break;
        }
    };

    private class DownloadServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 从 Binder 对象中获取到 service 对象
            DownloadService downloadService = ((DownloadService.DownloadBinder) service).getService();
            // 调用 service download 方法开始下载，并监督下载进度
            downloadService.starDownload(mProgressListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsDownloading = false;
            mIsServiceBindOk = false;
        }

        private ProgressListener mProgressListener = new ProgressListener() {
            @Override
            public void onProgress(int progress) {
                LogUtils.LOGI(TAG, "" + progress);
                // 下载进度更新，显示下载进度，注意更新 UI 需要在主线程中操作
                mProgressTv.post(() ->
                        mProgressTv.setText(String.format(Locale.getDefault(), "%d%%", progress)));
            }
        };
    }


}
