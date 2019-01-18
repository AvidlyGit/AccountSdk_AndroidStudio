package com.avidly.sdk.account.base.utils;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * Created by Holaverse on 2017/4/19.
 */

public class ThreadHelper {
    private static Handler mMainHandler; // UI线程
    private static Handler mWorkHandler; // 轻量级任务、线程调度
//    private static ExecutorService mSingleThreadPool = Executors.newSingleThreadExecutor(); // 主线程加载的广告在这里做散列化处理再转进主线程
//    private static ExecutorService mLoadThreadPool = Executors.newFixedThreadPool(4); // 广告加载
//    private static ExecutorService mHttpThreadPool = Executors.newFixedThreadPool(2); // 网络请求
//    private static ExecutorService mWorkThreadPool = Executors.newFixedThreadPool(2); // 其他业务

    static {
        initMainHandler();
        initWorkHandler();
    }

    private static void initWorkHandler() {
        if (mWorkHandler == null) {
            final HandlerThread thread = new HandlerThread("ad_sdk");
            thread.start();
            mWorkHandler = new Handler(thread.getLooper());
        }
    }

    private static void initMainHandler() {
        if (mMainHandler == null) {
            mMainHandler = new Handler(Looper.getMainLooper());
        }
    }

    public static void runOnMainThread(Runnable r) {
        initMainHandler();

        mMainHandler.post(r);
    }

    public static void runOnMainThread(Runnable r, long delay) {
        initMainHandler();

        mMainHandler.postDelayed(r, delay);
    }

    public static void removeOnMainThread(Runnable r) {
        if (mMainHandler != null && r != null) {
            mMainHandler.removeCallbacks(r);
        }
    }

    public static void runOnWorkThread(Runnable r) {
        initWorkHandler();

        mWorkHandler.post(r);
    }

    public static void runOnWorkThread(Runnable r, long delay) {
        initWorkHandler();

        mWorkHandler.postDelayed(r, delay);
    }

    public static void removeOnWorkThread(Runnable r) {
        if (mWorkHandler != null && r != null) {
            mWorkHandler.removeCallbacks(r);
        }
    }

//    public static void runInHttpThreadPool(Runnable r) {
//        mHttpThreadPool.execute(r);
//    }
//
//    public static void runInLoadThreadPool(Runnable r) {
//        mLoadThreadPool.execute(r);
//    }
//
//    public static void runInWorkThreadPool(Runnable r) {
//        mWorkThreadPool.execute(r);
//    }

    // 将必须在主线程调用的广告加载任务顺序放入主线程，错开100ms时间执行
//    public static void addToSingleThread(final Runnable r) {
//        mSingleThreadPool.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    runOnMainThread(r);
//                    Thread.sleep(200);
//                } catch (Throwable e) {
//
//                }
//            }
//        });
//    }

    public static String getAppKey(Context context) {
        int resId = getResId(context, "string", "ad_app_key");
        return context.getResources().getString(resId);
    }

    public static boolean isUIThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    public static int getResId(Context context, String type, String name) {
        int id = context.getResources().getIdentifier(name, type, context.getPackageName());
        return id;
    }

    public static boolean hasClass(String name) {
        try {
            Class.forName(name);
        } catch (Throwable throwable) {
            return false;
        }
        return true;
    }
}
