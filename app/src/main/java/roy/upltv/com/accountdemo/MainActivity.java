package roy.upltv.com.accountdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aas.sdk.account.AASGgidCallback;
import com.aas.sdk.account.AASTokenCallback;
import com.aas.sdk.account.AASdk;
import com.aly.sdk.ALYAnalysis;

import roy.upltv.com.demo.BuildConfig;
import roy.upltv.com.demo.R;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "AccountLoginSdk_";
    private View mLoginButton,mFbTokenBtn;
    private View mUserCenterButton;
    private TextView mGgidTextView;
    private TextView mModeTextView;
    private TextView mFbTokenTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mLoginButton = findViewById(R.id.login_id);
        mFbTokenBtn=findViewById(R.id.btn_fb_token);
        mUserCenterButton = findViewById(R.id.usermanager_id);
        mGgidTextView = findViewById(R.id.tvGgid);
        mModeTextView = findViewById(R.id.tvMode);
        mFbTokenTextView=findViewById(R.id.tv_fb_token);

        ALYAnalysis.init(this,"888888","32401");

        AASdk.initSdk(this, BuildConfig.productId);

        setAccountTokenCallback();

        AASdk.accountLogin(this);
        AASdk.getFacebookLoginedToken();
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AASdk.accountLogin(MainActivity.this);
            }
        });

        mUserCenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AASdk.showUserManagerUI(MainActivity.this);
            }
        });

        mFbTokenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fbToken=AASdk.getFacebookLoginedToken();
                if (fbToken!=null){
                    Log.i("WAN","fbtoken is "+fbToken);
                    mFbTokenTextView.setText(fbToken);
                }else{
                    mFbTokenTextView.setText("未获得授权");
                }

            }
        });
    }

    /**
     * 用于获得ggid的回调
     */
    public void setAccountGgidCallback() {
        AASdk.setAAUGgidCallback(new AASGgidCallback() {
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
    public void setAccountTokenCallback() {
        AASdk.setAAUTokenCallback(new AASTokenCallback() {
            @Override
            public void onUserTokenLoginSuccess(String token, int mode) {
                String messge = "MainActivity onLoginSuccess: " + token;
                Log.i(TAG, "onUserTokenLoginSuccess: " + messge);

                mUserCenterButton.setVisibility(View.VISIBLE);
                mGgidTextView.setText("当前用户token是：" + (token == null ? "空" : token));
                mModeTextView.setText("当前登录类型是：" + mode);

                //获得用户登陆后的GGID
                String ggid=AASdk.getLoginedGGid();
                Log.i("WAN","ggid is "+ggid);

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
    public void onBackPressed() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
