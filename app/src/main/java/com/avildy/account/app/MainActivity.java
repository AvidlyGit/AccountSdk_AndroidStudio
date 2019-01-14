package com.avildy.account.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.avidly.sdk.account.AvidlyAccountSdk;

import account.avidly.com.accountsdk.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_login);

        AvidlyAccountSdk.accountLogin(this);
    }
}
