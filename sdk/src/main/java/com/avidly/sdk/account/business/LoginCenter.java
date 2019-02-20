package com.avidly.sdk.account.business;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Display;
import android.view.WindowManager;

import com.avidly.sdk.account.AvidlyAccountCallback;
import com.avidly.sdk.account.activity.AccountLoginActivity;
import com.avidly.sdk.account.base.Constants;
import com.avidly.sdk.account.base.utils.LogUtils;
import com.avidly.sdk.account.data.user.LoginUser;
import com.avidly.sdk.account.data.user.LoginUserManager;

import java.lang.ref.WeakReference;

public class LoginCenter {
    private static String productId;

    private static boolean freshUserManagerUI;

    private static AvidlyAccountCallback loginCallback;

    private static int gameOrietation = 99999;

    private static boolean isAutoLogin = true;

    private static WeakReference<Context> sContext;

    public static Context getContext() {
        return sContext == null ? null : sContext.get();
    }

    public static String getProductId() {
        return productId;
    }

    public static void setProductId(String productId) {
        LoginCenter.productId = productId;
    }

    public static AvidlyAccountCallback getLoginCallback() {
        return loginCallback;
    }

    public static void setLoginCallback(AvidlyAccountCallback loginCallback) {
        LoginCenter.loginCallback = loginCallback;
    }

    public static void checkScreenOrietation(Context context) {
        LoginCenter.gameOrietation = context.getResources().getConfiguration().orientation;
        LogUtils.i("orientation: " + LoginCenter.gameOrietation);
        if (!LoginCenter.isScreenLandscape() && !LoginCenter.isScreenPortrait()) {
            LogUtils.i("orientation is nukown ");
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
                LogUtils.i("check orientation: " + LoginCenter.gameOrietation);
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

    public static boolean isFreshUserManagerUI() {
        return freshUserManagerUI;
    }

    public static void setFreshUserManagerUI(boolean freshUserManagerUI) {
        LoginCenter.freshUserManagerUI = freshUserManagerUI;
    }

    public static boolean isIsAutoLogin() {
        return isAutoLogin;
    }

    public static void setIsAutoLogin(boolean isAutoLogin) {
        LoginCenter.isAutoLogin = isAutoLogin;
    }

    public static void loginNow(Context context) {
        if (context == null) {
            throw new RuntimeException("context is null, fail to login.");
        }

        if (sContext == null || sContext.get() == null) {
            if (sContext != null) {
                sContext.clear();
            }
            sContext = new WeakReference<>(context.getApplicationContext());
        }

        // 从缓存中读取上次登陆的帐号数据
        LoginUserManager.freshUserCache(context);

        LoginCenter.checkScreenOrietation(context);

//        LoginUser accountUser = LoginUserManager.getAccountLoginUser();
//        if (accountUser != null && accountUser.isActived) {
//            // 上次登陆是帐号登陆（即非游客登陆）
//            if (accountUser.isNowLogined) {
//                LogUtils.w("已经帐号登陆，不需要重复登陆");
//                if (loginCallback != null) {
//                    loginCallback.onGameGuestIdLoginSuccess(accountUser.ggid);
//                }
//                return;
//            }
//
//            continueLogin(context, accountUser);
//            return;
//        }
//
//        LoginUser guestUser = LoginUserManager.getGuestLoginUser();
//        if (guestUser != null && guestUser.isActived) {
//            if (guestUser.isNowLogined) {
//                LogUtils.w("已经游客登陆，不需要重复登陆");
//                if (loginCallback != null) {
//                    loginCallback.onGameGuestIdLoginSuccess(accountUser.ggid);
//                }
//                return;
//            }
//
//            continueLogin(context, guestUser);
//            return;
//        }

        LoginUser activedUser = LoginUserManager.getCurrentActiveLoginUser();
        if (activedUser != null) {
            if (activedUser.isNowLogined) {
                LogUtils.w("已经登陆，不需要重复登陆, account mode:" + activedUser.getLoginedMode() + ", ggid:" + activedUser.ggid, null);
                if (loginCallback != null) {
                    loginCallback.onGameGuestIdLoginSuccess(activedUser.ggid);
                }
            } else {
                continueLogin(context);
            }
        } else {
            newLogin(context);
        }
    }

    private static void newLogin(Context context) {
        Intent intent = new Intent(context, AccountLoginActivity.class);
        intent.setAction(Constants.INTENT_KEY_ACTION_LOGIN);
        context.startActivity(intent);
    }

    private static void continueLogin(Context context) {
        Intent intent = new Intent(context, AccountLoginActivity.class);
        intent.setAction(Constants.INTENT_KEY_ACTION_LOGIN);
        context.startActivity(intent);
    }

}
