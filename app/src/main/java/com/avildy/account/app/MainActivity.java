package com.avildy.account.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.avidly.sdk.account.AvidlyAccountLoginCallback;
import com.avidly.sdk.account.AvidlyAccountSdk;
import com.avidly.sdk.account.base.utils.LogUtils;

import account.avidly.com.accountsdk.R;

public class MainActivity extends AppCompatActivity {
    private View mLoginButton;
    private View mUserCenterButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoginButton = findViewById(R.id.login_id);
        startLogin();
//        mLoginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startLogin();
//            }
//        });

        mUserCenterButton = findViewById(R.id.usermanager_id);
        mUserCenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AvidlyAccountSdk.showUserManagerUI(MainActivity.this);
            }
        });

    }

    private void startLogin() {
        AvidlyAccountSdk.accountLogin(MainActivity.this, "610322", new AvidlyAccountLoginCallback() {
            @Override
            public void onLoginSuccess(String ggid) {
                String messge = "MainActivity onLoginSuccess: " + ggid;
                LogUtils.i(messge);

                mLoginButton.setVisibility(View.GONE);
                mUserCenterButton.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), messge, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoginFail(int code, String msg) {
                String messge = "MainActivity onLoginFail: " + code;
                LogUtils.i(messge);

                mLoginButton.setVisibility(View.VISIBLE);
                mUserCenterButton.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), messge, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("xxxxx", "onResume orientation: " + getResources().getConfiguration().orientation);
    }
}
