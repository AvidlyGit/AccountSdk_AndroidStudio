package com.avildy.account.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.avidly.sdk.account.AvidlyAccountSdk;

import account.avidly.com.accountsdk.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_login);

        //AvidlyAccountSdk.accountLogin(this);

//        (new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                AvidlyAccountSdk.accountLogin(MainActivity.this);
//            }
//        }, 500);

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

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("xxxxx", "onResume orientation: " + getResources().getConfiguration().orientation);
    }
}
