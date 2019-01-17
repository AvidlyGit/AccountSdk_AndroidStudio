package com.avidly.sdk.account;

import android.content.Context;
import android.content.Intent;

import com.avidly.sdk.account.activity.UserLookupPwdActivity;
import com.avidly.sdk.account.activity.UserManagerActivity;
import com.avidly.sdk.account.business.LoginCenter;

public class AvidlyAccountSdk {

    public static void accountLogin(Context context) {
        LoginCenter.loginNow(context);
    }

    public static void showUserManagerUI(Context context) {
        LoginCenter.checkScreenOrietation(context);
        context.startActivity(new Intent(context, UserManagerActivity.class));
    }

    public static void showUserLookupPasswordrUI(Context context) {
        LoginCenter.checkScreenOrietation(context);
        context.startActivity(new Intent(context, UserLookupPwdActivity.class));
    }


}
