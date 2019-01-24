package com.avidly.sdk.account.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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

        finish();
        if (!LoginUserManager.isLoginedNow()) {
            showAccountHomeFragment();
        }

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
        showAccountHomeFragment();
    }

    @Override
    public void onWaitingTimeOut() {
        LoginUser activedUser = LoginUserManager.getCurrentActiveLoginUser();
        switch (activedUser.getLoginedMode()) {
            case Account.ACCOUNT_MODE_GUEST:
                // 现存账号是guest
                mPresenter.guestLogin(activedUser);
                break;
            case Account.ACCOUNT_MODE_AVIDLY:
                // 现存账号是avidly
                Account avidlyAccount = activedUser.findAccountByMode(Account.ACCOUNT_MODE_AVIDLY);
                mPresenter.accountLogin(avidlyAccount.accountName, avidlyAccount.accountPwd);
                break;
            case Account.ACCOUNT_MODE_FACEBOOK:
                //todo 现存账号是facebook
                Account facebookAccount = activedUser.findAccountByMode(Account.ACCOUNT_MODE_FACEBOOK);
                mPresenter.facebookLogin(activedUser);
        }
    }

    // from home fragment
    @Override
    public void onGuestLoginClicked() {
        mPresenter.guestLogin(null);
    }

    @Override
    public void onAvidlyLoginClicked() {
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
        super.onBackPressed();
    }

    @Override
    public void onLoginErrorOccured(String message) {
        showErrorMessage(message);
    }

    @Override
    public void onAccountLoginClicked(String email, String password) {
        mPresenter.accountLogin(email, password);
    }

    @Override
    public void onAccountRegistClicked(String email, String password) {
        mPresenter.accountRegistOrBind(LoginUserManager.getCurrentGGID(), email, password);
    }

    @Override
    public void onAccountBindClicked(String email, String password) {
        String ggid = LoginUserManager.getCurrentGGID();
        mPresenter.accountRegistOrBind(ggid, email, password);
    }

    @Override
    public void onForgotPasswordClicked() {
        AvidlyAccountSdk.showUserLookupPasswordrUI(getApplicationContext());
    }

    @Override
    public void onReadProtocolClicked() {
        Intent intent = new Intent(this, AvidlyProtocolActivity.class);
        startActivity(intent);
    }

    private void showAccountHomeFragment() {
        AccountHomeFragment homeFragment = new AccountHomeFragment();
        homeFragment.setHomeListener(this);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.avidly_fragment_login, homeFragment);
        transaction.commit();
    }

    private void showAccountLoginFragment(int loginType) {
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mErrorLayout.setVisibility(View.GONE);
            }
        });
    }

    private void showErrorMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMessgeText.setText(message);
                mErrorLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    v.clearFocus();
                    onWindowFocusChanged(true);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 点击输入框外 隐藏软键盘 * @param v * @param event * @return
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

}
