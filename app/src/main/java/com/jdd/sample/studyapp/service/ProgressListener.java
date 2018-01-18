package com.jdd.sample.studyapp.service;

/**
 * @author lc. 2018-01-17 23:56
 * @since 1.0.0
 */

public interface ProgressListener {

    /**
     * 当进度更新时回调
     *
     * @param progress 最新进度值
     */
    void onProgress(int progress);
}
