package com.jdd.sample.studyapp.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.jdd.sample.studyapp.utils.LogUtils;

import java.util.Arrays;
import java.util.Set;

/**
 * @author lc. 2018-01-18 13:31
 * @since 1.0.0
 */

public class PreferenceProvider extends ContentProvider {

    private static final String TAG = LogUtils.makeLogTag(PreferenceProvider.class);

    private static final String CONTENT_AUTHORITY = "com.jdd.sample.test.provider.preference";
    public static final Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        LogUtils.LOGI(TAG, "insert(uri=" + uri + ", values=" + String.valueOf(values) + ")");
        if (values == null) {
            return uri;
        }
        Set<String> keys = values.keySet();
        for (String key : keys) {
            PreferenceUtils.putString(getContext(), key, String.valueOf(values.get(key)));
        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        LogUtils.LOGI(TAG, "delete(uri=" + uri + ")");
        if (TextUtils.isEmpty(selection)) {
            return 0;
        }
        PreferenceUtils.remove(getContext(), selection);
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        LogUtils.LOGI(TAG, "update(uri=" + uri + ", values=" + String.valueOf(selection) + ")");
        if (values == null) {
            return 0;
        }
        Set<String> keys = values.keySet();
        for (String key : keys) {
            PreferenceUtils.putString(getContext(), key, String.valueOf(values.get(key)));
        }
        return 0;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        LogUtils.LOGI(TAG, "uri=" + uri + " code=" + " proj=" +
                Arrays.toString(projection) + " selection=" + selection + " args="
                + Arrays.toString(selectionArgs) + ")");
        LogUtils.LOGE(TAG, "query result: " + selection + " = " + PreferenceUtils.getString(getContext(), selection));
        return null;
    }


}
