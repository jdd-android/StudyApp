package com.jdd.sample.studyapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jdd.sample.studyapp.utils.LogUtils;

/**
 * @author lc. 2018-01-17 23:50
 * @since 1.0.0
 */

public class DownloadService extends Service {

    private static final String TAG = DownloadService.class.getSimpleName();

    private static final int MAX_PROGRESS = 100;
    private volatile int progress = 0;
    private ProgressListener mListener;

    @Override
    public void onCreate() {
        LogUtils.LOGI(TAG, " === onCreate ===");
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.LOGI(TAG, " === onStartCommand ===");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.LOGI(TAG, "=== onBind ====");
        return new DownloadBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.LOGI(TAG, "=== onDestroy ====");
    }

    public void starDownload(ProgressListener listener) {
        mListener = listener;
        new Thread(() -> {
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
        }).start();
    }

    public class DownloadBinder extends Binder {
        public DownloadService getService() {
            return DownloadService.this;
        }
    }

}
