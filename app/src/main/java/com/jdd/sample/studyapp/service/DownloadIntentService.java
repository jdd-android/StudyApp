package com.jdd.sample.studyapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jdd.sample.studyapp.utils.LogUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author lc. 2018-01-18 10:27
 * @since 1.0.0
 */

public class DownloadIntentService extends IntentService {

    private static final String TAG = DownloadIntentService.class.getSimpleName();

    private static final int MAX_PROGRESS = 100;
    private volatile int progress = 0;
    private ProgressListener mListener;

    public DownloadIntentService() {
        super("DownloadIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e(TAG, "Thread: " + Thread.currentThread().getName());
        starDownload((progress) -> {
            LogUtils.LOGI(TAG, "download progress.. " + progress + "%");
        });
    }

    public void starDownload(ProgressListener listener) {
        mListener = listener;
        Future<?> downloadTask = Executors.newSingleThreadExecutor().submit(() -> {
            while (progress < MAX_PROGRESS) {
                progress = progress + 5;
                if (mListener != null) {
                    mListener.onProgress(progress);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            downloadTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.LOGI(TAG, "=== onDestroy ===");
    }


}
