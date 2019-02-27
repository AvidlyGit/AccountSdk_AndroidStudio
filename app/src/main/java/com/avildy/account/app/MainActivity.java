package com.avildy.account.app;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.avidly.sdk.account.AvidlyAccountCallback;
import com.avidly.sdk.account.AvidlyAccountSdk;

import account.avidly.com.accountsdk.BuildConfig;
import account.avidly.com.accountsdk.R;

//import com.avidly.sdk.account.base.utils.LogUtils;
//import com.avidly.sdk.account.data.user.LoginUser;
//import com.avidly.sdk.account.data.user.LoginUserManager;
//import com.avidly.sdk.account.third.ThirdSdkFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "AccountLoginSdk_";
    private View mLoginButton;
    private View mUserCenterButton;
    private TextView mGgidTextView;
    private TextView mModeTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoginButton = findViewById(R.id.login_id);
        mUserCenterButton = findViewById(R.id.usermanager_id);
        mGgidTextView = findViewById(R.id.tvGgid);
        mModeTextView = findViewById(R.id.tvMode);

        AvidlyAccountSdk.initSdk(BuildConfig.productId, new AvidlyAccountCallback() {
            @Override
            public void onGameGuestIdLoginSuccess(String ggid) {

                String messge = "MainActivity onLoginSuccess: " + ggid;
                Log.i(TAG, "onGameGuestIdLoginSuccess: "+messge);
                mLoginButton.setVisibility(View.GONE);
                mUserCenterButton.setVisibility(View.VISIBLE);
                mGgidTextView.setText("当前用户id是：" + (ggid == null ? "空" : ggid));
            }

            @Override
            public void onGameGuestIdLoginFailed(int code, String msg) {
                String messge = "MainActivity onLoginFail: " + msg;
                Log.i(TAG, "onGameGuestIdLoginFailed: "+messge);
                mLoginButton.setVisibility(View.VISIBLE);
                mUserCenterButton.setVisibility(View.GONE);
            }
        });

        AvidlyAccountSdk.accountLogin(MainActivity.this);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AvidlyAccountSdk.accountLogin(MainActivity.this);
            }
        });

        mUserCenterButton.setOnClickListener(new View.OnClickListener() {
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

//        String ggid = LoginUserManager.getCurrentGGID();
//        mGgidTextView.setText("当前用户id是：" + (ggid == null ? "空" : ggid));
//
//        if (ggid != null) {
//            LoginUser activedUser = LoginUserManager.getCurrentActiveLoginUser();
//            if (activedUser.isNowLogined) {
//                String mode = ThirdSdkFactory.nameOfAccountMode(activedUser.getLoginedMode());
//                mModeTextView.setText("当前账户类型是：" + mode);
//            }
//        }
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        Log.i("wan", "onConfigurationChanged: ........................");
//    }
}
