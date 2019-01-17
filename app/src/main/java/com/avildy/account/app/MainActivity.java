package com.avildy.account.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import android.util.Log;
import android.view.View;

import com.avidly.sdk.account.AvidlyAccountSdk;

import account.avidly.com.accountsdk.R;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.login_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AvidlyAccountSdk.accountLogin(MainActivity.this);
            }
        });

        findViewById(R.id.usermanager_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AvidlyAccountSdk.showUserManagerUI(MainActivity.this);
            }
        });

        findViewById(R.id.user_lookuppwd_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AvidlyAccountSdk.showUserLookupPasswordrUI(MainActivity.this);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("xxxxx", "onResume orientation: " + getResources().getConfiguration().orientation);
    }
}
