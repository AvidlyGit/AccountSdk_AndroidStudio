package com.avidly.sdk.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.avidly.sdk.account.activity.AccountLoginActivity;
import com.avidly.sdk.account.activity.UserManagerActivity;
import com.avidly.sdk.account.business.LoginCenter;

public class AvidlyAccountSdk {

    public static void accountLogin(Activity context) {
        LoginCenter.checkScreenOrietation(context);
        context.startActivity(new Intent(context, AccountLoginActivity.class));
    }


    public static void showUserManagerUI(Context context) {
        LoginCenter.checkScreenOrietation(context);
        context.startActivity(new Intent(context, UserManagerActivity.class));
    }


}
