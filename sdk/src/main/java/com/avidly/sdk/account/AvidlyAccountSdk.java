package com.avidly.sdk.account;

import android.content.Context;
import android.content.Intent;

import com.avidly.sdk.account.activity.UserLookupPwdActivity;
import com.avidly.sdk.account.activity.UserManagerActivity;
import com.avidly.sdk.account.business.LoginCenter;

public class AvidlyAccountSdk {

    public static void accountLogin(Context context, String productid, AvidlyAccountLoginCallback callback) {
        LoginCenter.setProductId(productid);
        LoginCenter.setLoginCallback(callback);
        LoginCenter.loginNow(context);
    }

    public static void showUserManagerUI(Context context) {
        LoginCenter.checkScreenOrietation(context);
        Intent intent = new Intent(context, UserManagerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
