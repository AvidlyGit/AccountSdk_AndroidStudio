package com.avidly.sdk.account;

import android.content.Context;
import android.content.Intent;

import com.avidly.sdk.account.activity.AccountLoginActivity;

public class AvidlyAccountSdk {

    public static void accountLogin(Context context) {
        context.startActivity(new Intent(context, AccountLoginActivity.class));
    }




}
