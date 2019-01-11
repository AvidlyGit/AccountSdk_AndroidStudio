package com.avidly.sdk.account.base.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by sam on 2017/7/27.
 */

public class NetUtils {

    public static final int TYPE_NONE = -1;
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_WIFI = 1;
    public static final int TYPE_2G = 2;
    public static final int TYPE_3G = 3;
    public static final int TYPE_4G = 4;

    // 3G类型，http://zh.wikipedia.org/wiki/TD-SCDMA.
    // Android SDK 21里的TelephonyManager.NETWORK_TYPE_XXX只到16，在此处添加常量
    public static final int NETWORK_TD_SCDMA = 17;

    // 特殊机型特有的3G网络类型，例如华为荣耀。
    // Android SDK 21里的TelephonyManager.NETWORK_TYPE_XXX只到16，在此处添加常量
    public static final int NETWORK_HUAWEI_TDS_HSDPA = 18;


    /**
     * Check whether network is available.
     *
     * @param context Application context
     * @return <code>true</code> if network available, <code>false</code> otherwise
     */
    public static boolean isNetworkAvailable(Context context) {
        return isNetworkAvailable(getActiveNetworkInfo(context));
    }

    public static boolean isNetworkAvailable(NetworkInfo activeNetwork) {
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public static NetworkInfo getActiveNetworkInfo(Context context) {
        try {
            final ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            return cm.getActiveNetworkInfo();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isLessThan4g(Context context) {
        int mode = getNetworkSubType(context);
        return mode != TYPE_WIFI && mode < TYPE_4G;
    }

    public static int getNetworkSubType(Context context) {

        try {
            if (context == null) {
                return TYPE_UNKNOWN;
            }

            ConnectivityManager cm = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null && info.isConnectedOrConnecting()) {
                return TYPE_WIFI;
            } else {
                info = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (null != info && info.isAvailable() && info.isConnectedOrConnecting()) {
                    switch (info.getSubtype()) {
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            return TYPE_2G;
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                        case NETWORK_TD_SCDMA:
                        case NETWORK_HUAWEI_TDS_HSDPA:
                            return TYPE_3G;
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            return TYPE_4G;
                        default:
                            return TYPE_UNKNOWN;
                    }
                } else {
                    return TYPE_NONE;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return TYPE_UNKNOWN;
        }

    }
}
