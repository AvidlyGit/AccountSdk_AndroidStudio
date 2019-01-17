package com.avidly.sdk.account.business;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

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

}
