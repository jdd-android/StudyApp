package com.jdd.sample.studyapp.provider;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author lc. 2018-01-18 13:32
 * @since 1.0.0
 */

public class PreferenceUtils {

    /** 文件名称 */
    private static final String FILE_NAME = "provider_preference";

    static void putString(Context context, String key, String value) {
        getPreference(context).edit().putString(key, value).apply();
    }

    static void remove(Context context, String key) {
        getPreference(context).edit().remove(key).apply();
    }

    static String getString(Context context, String key) {
        return getPreference(context).getString(key, "");
    }

    private static SharedPreferences getPreference(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }
}
