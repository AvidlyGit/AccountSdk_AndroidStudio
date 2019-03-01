package com.avidly.sdk.account.base.monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.avidly.sdk.account.base.utils.NetUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sam on 2017/8/1.
 */

public class NetWorkMonitor {

    private static NetWorkMonitor sMonitor;

    private boolean mBinded;
    private BroadcastReceiver mReceiver;

    private List<NetWorkCallback> mListCallback;

    public static NetWorkMonitor getInstance() {
        if (sMonitor == null) {
            synchronized (NetWorkMonitor.class) {
                if (sMonitor == null) {
                    sMonitor = new NetWorkMonitor();
                }
            }
        }
        return sMonitor;
    }

    public boolean isBinded() {
        return mBinded;
    }

    public void bindIntoConnectivity(Context context) {
        if (mBinded) {
            return;
        }

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                 boolean isConnected = NetUtils.isNetworkAvailable(context);
                    boolean iswifi = NetUtils.getNetworkSubType(context) == 1;
                    if (mListCallback != null) {
                        ArrayList<NetWorkCallback> list = new ArrayList();
                        synchronized (mListCallback) {
                            list.addAll(mListCallback);
                        }
                        for (NetWorkCallback callback : list) {
                            try {
                                callback.onNetWorkChanged(isConnected, iswifi);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        final IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        try {
            context.registerReceiver(mReceiver, filter);
            mBinded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unbindConnectivity(Context context) {
        try {
            context.unregisterReceiver(mReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNetworkCallback(NetWorkCallback callback) {
        if (mListCallback == null) {
            mListCallback = new ArrayList<>();
        }

        synchronized (mListCallback) {
            if (!mListCallback.contains(callback)) {
                mListCallback.add(callback);
            }
        }
    }

    public void removeNetworkCallback(NetWorkCallback callback) {
        if (mListCallback == null) {
            return;
        }
        synchronized (mListCallback) {
            if (mListCallback.contains(callback)) {
                mListCallback.remove(callback);
            }
        }
    }
}
