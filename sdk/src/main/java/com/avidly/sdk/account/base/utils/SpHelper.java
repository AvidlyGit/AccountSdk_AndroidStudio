package com.avidly.sdk.account.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.avidly.sdk.account.base.Constants;

/**
 * Created by sam on 2018/11/5.
 */

public class SpHelper {
    private SharedPreferences mSp = null;

    public SpHelper(Context context, String name) {
        mSp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public void setOnSettingChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        synchronized (this) {
            if (null != listener) {
                mSp.registerOnSharedPreferenceChangeListener(listener);
            }
        }
    }

    public boolean isCreated() {
        return mSp == null;
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = mSp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return mSp.getString(key, null);
    }

    public static String getString(Context context, String key) {
        if(context == null) {
            return "";
        } else {
            SharedPreferences var2 = context.getSharedPreferences(Constants.SP_NAME, 0);
            return var2.getString(key, (String)null);
        }
    }

    public boolean contains(String key) {
        return mSp.contains(key);
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = mSp.edit();
        editor.remove(key);
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mSp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public SharedPreferences.Editor getEditor() {
        return mSp.edit();
    }

    public boolean getBoolean(String key) {
        return getBooleanWithDefault(key, false);
    }

    public boolean getBooleanWithDefault(String key, boolean value) {
        return mSp.getBoolean(key, value);
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = mSp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key) {
        return getIntWithDefault(key, 0);
    }

    public int getIntWithDefault(String key, int value) {
        return mSp.getInt(key, value);
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = mSp.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public long getLong(String key) {
        return getLongWithDefault(key, 0);
    }

    public long getLongWithDefault(String key, long value) {
        return mSp.getLong(key, value);
    }

    public void putFloat(String key, float value) {
        SharedPreferences.Editor editor = mSp.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public float getFloat(String key) {
        return getFloatDefault(key, 0);
    }

    public float getFloatDefault(String key, long value) {
        return mSp.getFloat(key, value);
    }

}
