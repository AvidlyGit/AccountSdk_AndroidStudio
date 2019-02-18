package com.avildy.account.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.avidly.sdk.account.AvidlyAccountLoginCallback;
import com.avidly.sdk.account.AvidlyAccountSdk;
//import com.avidly.sdk.account.base.utils.LogUtils;
//import com.avidly.sdk.account.data.user.LoginUser;
//import com.avidly.sdk.account.data.user.LoginUserManager;
//import com.avidly.sdk.account.third.ThirdSdkFactory;

import account.avidly.com.accountsdk.BuildConfig;
import account.avidly.com.accountsdk.R;

public class MainActivity extends AppCompatActivity {
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

        startLogin();

        // >>>>>>> 以下是测试代码
//        LoginUserManager.freshUserCache(this);

//        LoginUserManager.onGuestLoginSuccess("guest-2222");
//        LoginUserManager.saveAccountUsers();

//        Log.i("xxxx", "current ggid:" + LoginUserManager.getCurrentGGID());
//        Log.i("xxxx", "guest:" + LoginUserManager.getGuestLoginUser());
//        if (LoginUserManager.getGuestLoginUser() != null) {
//            Log.i("xxxx", "guest gson:" + LoginUserManager.getGuestLoginUser().thisToString());
//        }
//        if (LoginUserManager.getCurrentActiveLoginUser() != null) {
//            Log.i("xxxx", "current actived user gson:" + LoginUserManager.getCurrentActiveLoginUser().thisToString());
//        }

//        LoginUser user = LoginUserManager.onAccountLoginSuccess(Account.ACCOUNT_MODE_AVIDLY, "guest-1111");
//        Account account = user.findAccountByMode(Account.ACCOUNT_MODE_AVIDLY);
//        account.accountName = "sam.liu@upltv.com";
//        account.nickname = "yjfnapsu_007";
//        account.accountPwd = "123456";
//        LoginUserManager.saveAccountUsers();
//        Log.i("xxxx", "guest:" + LoginUserManager.getGuestLoginUser());
        // <<<<<<<<<<


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLogin();
            }
        });

        mUserCenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AvidlyAccountSdk.showUserManagerUI(MainActivity.this);
            }
        });
    }

    private void startLogin() {
        AvidlyAccountSdk.accountLogin(MainActivity.this, BuildConfig.productId, new AvidlyAccountLoginCallback() {
            @Override
            public void onLoginSuccess(String ggid) {
                String messge = "MainActivity onLoginSuccess: " + ggid;
                Log.i("AccountLoginSdk", "onLoginSuccess: " + messge);

                mLoginButton.setVisibility(View.GONE);
                mUserCenterButton.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), messge, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoginFail(int code, String msg) {
                String messge = "MainActivity onLoginFail: " + msg;
                Log.i("AccountLoginSdk", "onLoginFail: " + messge);

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
}
