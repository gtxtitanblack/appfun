package com.example.appfun.util;

import android.support.design.BuildConfig;
import android.util.Log;

/**
 * Created by zx on 2016/8/3.
 */

public class LogUtil {
    private static final int JSON_INDENT = 4;

    public static void d(String tag, String data) {
        if (!BuildConfig.DEBUG) {
            return;
        }
        Log.d(tag, data);
    }
}
