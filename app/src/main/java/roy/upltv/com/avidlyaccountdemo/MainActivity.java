package roy.upltv.com.avidlyaccountdemo;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.avidly.sdk.account.AvidlyAccountGgidCallback;
import com.avidly.sdk.account.AvidlyAccountSdk;
import com.avidly.sdk.account.AvidlyAccountTokenCallback;


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

        AvidlyAccountSdk.initSdk(this, BuildConfig.productId);


//      setAvidlyAccountGgidCallback();

        setAvidlyAccountTokenCallback();

        AvidlyAccountSdk.accountLogin(this);
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

    /**
     * 用于获得ggid的回调
     */
    public void setAvidlyAccountGgidCallback() {
        AvidlyAccountSdk.setAvidlyAccountGgidCallback(new AvidlyAccountGgidCallback() {
            @Override
            public void onGameGuestIdLoginSuccess(String ggid, int mode) {
                String messge = "MainActivity onLoginSuccess: " + ggid;
                Log.i(TAG, "onGameGuestIdLoginSuccess: " + messge);
                mLoginButton.setVisibility(View.GONE);
                mUserCenterButton.setVisibility(View.VISIBLE);
                mGgidTextView.setText("当前用户id是：" + (ggid == null ? "空" : ggid));
                mModeTextView.setText("当前登录类型是：" + mode);
            }

            @Override
            public void onGameGuestIdLoginFailed(int code, String msg) {
                String messge = "MainActivity onLoginFail: " + msg;
                Log.i(TAG, "onGameGuestIdLoginFailed: " + messge);
                mLoginButton.setVisibility(View.VISIBLE);
                mUserCenterButton.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 用于获得登录token的回调
     */
    public void setAvidlyAccountTokenCallback() {
        AvidlyAccountSdk.setAvidlyAccountTokenCallback(new AvidlyAccountTokenCallback() {
            @Override
            public void onUserTokenLoginSuccess(String token, int mode) {
                String messge = "MainActivity onLoginSuccess: " + token;
                Log.i(TAG, "onUserTokenLoginSuccess: " + messge);
                mLoginButton.setVisibility(View.GONE);
                mUserCenterButton.setVisibility(View.VISIBLE);
                mGgidTextView.setText("当前用户token是：" + (token == null ? "空" : token));
                mModeTextView.setText("当前登录类型是：" + mode);
            }

            @Override
            public void onUserTokenLoginFailed(int code, String msg) {
                String messge = "MainActivity onLoginFail: " + msg;
                Log.i(TAG, "onUserTokenLoginFailed: " + messge);
                mLoginButton.setVisibility(View.VISIBLE);
                mUserCenterButton.setVisibility(View.GONE);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i("xxxxx", "onResume orientation: " + getResources().getConfiguration().orientation);
    }
}