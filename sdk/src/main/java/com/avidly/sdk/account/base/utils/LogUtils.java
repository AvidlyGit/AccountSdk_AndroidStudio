package com.avidly.sdk.account.base.utils;

import android.util.Log;

import com.sdk.avidly.account.BuildConfig;

/**
 * Created by t.wang on 2019/1/21.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public class LogUtils {
    private static final String TAG = "AccountLoginSdk_" + BuildConfig.VERSION_NAME;

    public static void i(String message) {
        Log.i(TAG, message);
    }

    public static void w(String message, Throwable throwable) {
        Log.w(TAG, message, throwable);
    }
}
