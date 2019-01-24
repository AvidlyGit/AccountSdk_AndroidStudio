package com.avidly.sdk.account.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.avidly.sdk.account.AvidlyAccountSdk;
import com.avidly.sdk.account.base.Constants;
import com.avidly.sdk.account.base.utils.LogUtils;
import com.avidly.sdk.account.business.LoginCenter;
import com.avidly.sdk.account.business.LoginPresenter;
import com.avidly.sdk.account.business.LoginPresenterImpl;
import com.avidly.sdk.account.data.user.Account;
import com.avidly.sdk.account.data.user.LoginUser;
import com.avidly.sdk.account.data.user.LoginUserManager;
import com.avidly.sdk.account.fragment.AccountHomeFragment;
import com.avidly.sdk.account.fragment.AccountLoadingFragment;
import com.avidly.sdk.account.fragment.AccountLoginFragment;
import com.avidly.sdk.account.listener.AccountHomeListener;
import com.avidly.sdk.account.listener.AccountLoadingListener;
import com.avidly.sdk.account.listener.AccountLoginListener;
import com.sdk.avidly.account.R;

public class AccountLoginActivity extends AppCompatActivity implements AccountLoginInterface,
        AccountHomeListener, AccountLoadingListener, AccountLoginListener {
    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private LoginPresenter mPresenter;

    private View mErrorLayout;
    private TextView mMessgeText;
    private View mMessgeClose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avidly_activity_login);

        mPresenter = new LoginPresenterImpl(this);
        mErrorLayout = findViewById(R.id.avidly_error_layout);
        mMessgeText = findViewById(R.id.avidly_error_message);
        mMessgeClose = findViewById(R.id.avidly_error_close);
        mMessgeClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideErrorMessage();
            }
        });

        parseIntent();
    }

    private void parseIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            switch (intent.getAction()) {
                case Constants.INTENT_KEY_ACTION_LOGIN:
                    LoginUser activedUser = LoginUserManager.getCurrentActiveLoginUser();
                    if (activedUser == null) {
                        // 没有现存账号，展示home_fragment
                        showAccountHomeFragment();
                    } else {
                        // 有现存账号，展示loading_fragment
                        AccountLoadingFragment loadingFragment = (AccountLoadingFragment) mFragmentManager.findFragmentById(R.id.avidly_fragment_login);
                        loadingFragment.setLoadingListener(this);

                        switch (activedUser.getLoginedMode()) {
                            case Account.ACCOUNT_MODE_GUEST:
                                // 现存账号是guest
                                mPresenter.guestLogin(activedUser);
                                break;
                            case Account.ACCOUNT_MODE_AVIDLY:
                                // 现存账号是avidly
                                Account avidlyAccount = activedUser.findAccountByMode(Account.ACCOUNT_MODE_AVIDLY);
                                // TODO: 2019/1/23  这里accountName， accountPwd取不到
                                mPresenter.accountLogin(avidlyAccount.accountName, avidlyAccount.accountPwd);
//                                mPresenter.accountLogin("Tao.Wang", "password");
                                break;
                            case Account.ACCOUNT_MODE_FACEBOOK:
                                //todo 现存账号是facebook
                                Account facebookAccount = activedUser.findAccountByMode(Account.ACCOUNT_MODE_FACEBOOK);
                                mPresenter.facebookLogin(activedUser);
                        }
                    }
                    break;
                case Constants.INTENT_KEY_ACTION_BIND:
                    // 用户绑定账号，展示bind_fragment
                    showAccountLoginFragment(Constants.SUB_FRAGMENT_TYPE_BIND);
                    break;
                case Constants.INTENT_KEY_ACTION_SWITCH:
                    // 用户切换账号，展示home_fragment
                    showAccountHomeFragment();
                    break;
            }
        }
    }

    // from view interface
    @Override
    public void onUserLoginSuccessed(final LoginUser loginUser) {
        LogUtils.i("onUserLoginSuccessed: ");
        // TODO: 2019/1/23 需要通知用户中心更新用户数据

        finish();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoginCenter.getLoginCallback().onLoginSuccess(loginUser.ggid);
            }
        });
    }

    @Override
    public void onUserLoginFailed(final int errorCode) {
        LogUtils.i("onUserLoginFailed: ");

        showErrorMessage(errorCode + "");
        showAccountHomeFragment();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoginCenter.getLoginCallback().onLoginFail(errorCode, "");
            }
        });
    }

    // from loading fragment
    @Override
    public void onSwitchAccountClicked() {
        LogUtils.i("onSwitchAccountClicked: ");

        // TODO: 2019/1/21 因为点击了切换，需要忽略此次登录请求的回调
        showAccountHomeFragment();
    }

    // from home fragment
    @Override
    public void onGuestLoginClicked() {
        LogUtils.i("onGuestLoginClicked: ");

        mPresenter.guestLogin(null);
    }

    @Override
    public void onAvidlyLoginClicked() {
        LogUtils.i("onAvidlyLoginClicked: ");

        showAccountLoginFragment(Constants.SUB_FRAGMENT_TYPE_LOGIN);
    }

    @Override
    public void onFacebookLoginClicked() {
        LogUtils.i("onFacebookLoginClicked: ");

    }

    @Override
    public void onTwitterLoginClicked() {
        LogUtils.i("onTwitterLoginClicked: ");

    }

    @Override
    public void onGoogleLoginClicked() {
        LogUtils.i("onGoogleLoginClicked: ");

    }

    // from login fragment
    @Override
    public void onBackToHomePressed() {
        LogUtils.i("onBackToHomePressed: ");
        super.onBackPressed();
    }

    @Override
    public void onLoginErrorOccured(String message) {
        showErrorMessage(message);
    }

    @Override
    public void onAccountLoginClicked(String email, String password) {
        LogUtils.i("onAccountLoginClicked: ");

        mPresenter.accountLogin(email, password);
    }

    @Override
    public void onAccountRegistClicked(String email, String password) {
        LogUtils.i("onAccountRegistClicked: ");

        mPresenter.accountRegistOrBind(null, email, password);
    }

    @Override
    public void onAccountBindClicked(String email, String password) {
        LogUtils.i("onAccountBindClicked: ");
        String ggid = LoginUserManager.getCurrentGGID();
        mPresenter.accountRegistOrBind(ggid, email, password);
    }

    @Override
    public void onForgotPasswordClicked() {
        LogUtils.i("onForgotPasswordClicked: ");

        AvidlyAccountSdk.showUserLookupPasswordrUI(getApplicationContext());
    }

    @Override
    public void onReadProtocolClicked() {
        LogUtils.i("onReadProtocolClicked: ");

        Intent intent = new Intent(this, AvidlyProtocolActivity.class);
        startActivity(intent);
    }

    private void showAccountHomeFragment() {
        LogUtils.i("showAccountHomeFragment: ");

        AccountHomeFragment homeFragment = new AccountHomeFragment();
        homeFragment.setHomeListener(this);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.avidly_fragment_login, homeFragment);
        transaction.commit();
    }

    private void showAccountLoginFragment(int loginType) {
        LogUtils.i("showAccountLoginFragment: ");

        AccountLoginFragment loginFragment = AccountLoginFragment.newInstance(loginType);
        loginFragment.setLoginListener(this);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.avidly_fragment_login, loginFragment);
        if (loginType == Constants.SUB_FRAGMENT_TYPE_LOGIN) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    private void hideErrorMessage() {
        LogUtils.i("hideErrorMessage: ");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mErrorLayout.setVisibility(View.GONE);
            }
        });
    }

    private void showErrorMessage(final String message) {
        LogUtils.i("showErrorMessage: ");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMessgeText.setText(message);
                mErrorLayout.setVisibility(View.VISIBLE);
            }
        });
    }
}
