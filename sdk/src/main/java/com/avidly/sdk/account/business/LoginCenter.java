package com.avidly.sdk.account.business;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.avidly.sdk.account.activity.AccountLoginActivity;
import com.avidly.sdk.account.data.user.LoginUser;
import com.avidly.sdk.account.data.user.LoginUserManager;

public class LoginCenter {

    private static int gameOrietation = 99999;

    public static void checkScreenOrietation(Context context) {
        LoginCenter.gameOrietation = context.getResources().getConfiguration().orientation;
        Log.i("xxxxx", "orientation: " + LoginCenter.gameOrietation);
        if (!LoginCenter.isScreenLandscape() && !LoginCenter.isScreenPortrait()) {
            Log.i("xxxxx", "orientation is nukown ");
            if (context instanceof Activity) {
                int screenWidth, screenHeight;
                WindowManager windowManager = ((Activity) context).getWindowManager();
                Display display = windowManager.getDefaultDisplay();
                screenWidth = display.getWidth();
                screenHeight = display.getHeight();
                if (screenWidth > screenHeight) {
                    LoginCenter.gameOrietation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                } else {
                    LoginCenter.gameOrietation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                }
                Log.i("xxxxx", "check orientation: " + LoginCenter.gameOrietation);
            }
        }
    }

    public static boolean isScreenLandscape() {
        return gameOrietation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                || gameOrietation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                || gameOrietation == ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
                || gameOrietation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
    }

    public static boolean isScreenPortrait() {
        return gameOrietation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                || gameOrietation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                || gameOrietation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
                || gameOrietation == ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT;
    }

    public static void loginNow(Context context) {
        // 从缓存中读取上次登陆的帐号数据
        LoginUserManager.freshUserCache(context);

        LoginCenter.checkScreenOrietation(context);

        LoginUser accountUser = LoginUserManager.getAccountLoginUser();
        if (accountUser != null) {
            if (accountUser.isActived) {
                // 上次登陆是帐号登陆（即非游客登陆）
                if (accountUser.isNowLogined) {
                    // 已经登陆，不需要重复登陆
                    Log.i("xxxx", "已经帐号登陆，不需要重复登陆");
                    return;
                }
                continueLogin(context, accountUser);
                return;
            }
        }

        LoginUser guestUser = LoginUserManager.getGuestLoginUser();
        if (guestUser != null && guestUser.isActived) {
            if (guestUser.isNowLogined) {
                Log.i("xxxx", "已经游客登陆，不需要重复登陆");
                return;
            } else {
                // 重新登录
                continueLogin(context, guestUser);
                return;
            }
        }

        newLogin(context);
    }

    private static void newLogin(Context context) {
        context.startActivity(new Intent(context, AccountLoginActivity.class));
    }

    private static void continueLogin(Context context, LoginUser user) {
        context.startActivity(new Intent(context, AccountLoginActivity.class));
    }

}
