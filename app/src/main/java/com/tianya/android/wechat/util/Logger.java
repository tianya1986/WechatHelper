package com.tianya.android.wechat.util;

import android.util.Log;

public final class Logger {

    private static final String TAG = "WechatHelper";

    public static void d(String tag, String message) {
        Log.d(TAG + "-" + tag, message);
    }

    public static void i(String tag, String message) {
        Log.i(TAG + "-" + tag, message);
    }

    public static void e(String tag, String message) {
        Log.e(TAG + "-" + tag, message);
    }

    public static void v(String tag, String message) {
        Log.v(TAG + "-" + tag, message);
    }
}
