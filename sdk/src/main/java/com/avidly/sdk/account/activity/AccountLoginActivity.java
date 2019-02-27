package com.avidly.sdk.account.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.avidly.sdk.account.base.AvidlyAccountSdkErrors;
import com.avidly.sdk.account.base.Constants;
import com.avidly.sdk.account.base.utils.LogUtils;
import com.avidly.sdk.account.base.utils.ThreadHelper;
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
import com.avidly.sdk.account.third.ThirdLoginSdkDelegate;
import com.avidly.sdk.account.third.ThirdSdkFactory;
import com.avidly.sdk.account.third.ThirdSdkLoginCallback;
import com.sdk.avidly.account.R;

public class AccountLoginActivity extends AppCompatActivity implements AccountLoginInterface,
        AccountHomeListener, AccountLoadingListener, AccountLoginListener {
    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private LoginPresenter mPresenter;
    private boolean mLoading;

    private View mErrorLayout;
    private TextView mMessgeText;
    private View mMessgeClose;
    private ThirdLoginSdkDelegate thirdLoginSdkDelegate;
    private AccountHomeFragment homeFragment;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avidly_activity_login);
        Log.i("wan", "onCreate has been called: ");
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

//        if(savedInstanceState == null)
//        {
//            parseIntent();
//        }
        parseIntent();

    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.i("wan", "onSaveInstanceState");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("wan", "onRestoreInstanceState");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (thirdLoginSdkDelegate != null) {
            thirdLoginSdkDelegate.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
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
                        LoginCenter.setIsAutoLogin(true);
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
        hideLoadingUI();
        LoginCenter.setIsAutoLogin(false);
        finish();

        if (!LoginUserManager.isLoginedNow()) {
            showAccountHomeFragment();
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoginCenter.getLoginCallback().onGameGuestIdLoginSuccess(loginUser.ggid);
            }
        });
    }

    @Override
    public void onUserLoginFailed(final int errorCode) {
        hideLoadingUI();
        final String message = getResources().getString(AvidlyAccountSdkErrors.getLoginErrorMessge(errorCode));
        showErrorMessage(message);

        if (LoginCenter.isIsAutoLogin()) {
            showAccountHomeFragment();
            LoginCenter.setIsAutoLogin(false);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoginCenter.getLoginCallback().onGameGuestIdLoginFailed(errorCode, message);
            }
        });
    }

    // from loading fragment
    @Override
    public void onSwitchAccountClicked() {
        showAccountHomeFragment();
    }

    @Override
    public void onAutoLoginWaitingTimeOut() {
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
                doThirdSdkLogin(Account.ACCOUNT_MODE_FACEBOOK);
        }
    }

    // from home fragment
    @Override
    public void onGuestLoginClicked() {
        showLoadingUI();
        LoginUser guestLoginUser = LoginUserManager.getGuestLoginUser();
        if (guestLoginUser != null) {
            mPresenter.guestLogin(guestLoginUser);
        } else {
            mPresenter.guestLogin(null);
        }
    }

    @Override
    public void onAvidlyLoginClicked() {
        showAccountLoginFragment(Constants.SUB_FRAGMENT_TYPE_LOGIN);
    }

    @Override
    public void onFacebookLoginClicked() {
        LogUtils.i("onFacebookLoginClicked: ");
        showLoadingUI();
        doThirdSdkLogin(Account.ACCOUNT_MODE_FACEBOOK);
    }

    @Override
    public void onTwitterLoginClicked() {
        LogUtils.i("onTwitterLoginClicked: ");
        showLoadingUI();
        doThirdSdkLogin(Account.ACCOUNT_MODE_TWITTER);
    }

    @Override
    public void onGoogleLoginClicked() {
        LogUtils.i("onGoogleLoginClicked: ");
        showLoadingUI();
        doThirdSdkLogin(Account.ACCOUNT_MODE_GOOGLEPLAY);
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
        showLoadingUI();
        mPresenter.accountLogin(email, password);
    }

    @Override
    public void onAccountRegistClicked(String email, String password) {
        showLoadingUI();
        mPresenter.accountRegistOrBind(null, email, password);
    }

    @Override
    public void onAccountBindClicked(String email, String password) {
        showLoadingUI();
        String ggid = LoginUserManager.getCurrentGGID();
        mPresenter.accountRegistOrBind(ggid, email, password);
    }

    @Override
    public void onForgotPasswordClicked() {
        showUserLookupPasswordrUI(getApplicationContext());
    }

    @Override
    public void onReadProtocolClicked() {
        Intent intent = new Intent(this, AvidlyProtocolActivity.class);
        startActivity(intent);
    }

    private static void showUserLookupPasswordrUI(Context context) {
        LoginCenter.checkScreenOrietation(context);
        Intent intent = new Intent(context, UserLookupPwdActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void doThirdSdkLogin(int mode) {
        LogUtils.i("doThirdSdkLogin, mode: " + mode);
        if (!ThirdSdkFactory.isExistSdkLib(mode)) {
            thirdLoginSdkDelegate = null;
            LogUtils.i("doThirdSdkLogin, the third login sdk is not exist. ");
            onUserLoginFailed(AvidlyAccountSdkErrors.AVIDLY_LOGIN_ERROR_THIRD_SDK_EXCEPTION);
            return;
        }

        if (thirdLoginSdkDelegate != null && !thirdLoginSdkDelegate.isThis(mode)) {
            thirdLoginSdkDelegate.exit();
            thirdLoginSdkDelegate = null;
        }

        if (thirdLoginSdkDelegate == null) {
            thirdLoginSdkDelegate = ThirdSdkFactory.newThirdSdkLoginDeleage(mode);
            if (thirdLoginSdkDelegate == null) {
                LogUtils.i("doThirdSdkLogin, fail to create third sdk delegate object.");
                onUserLoginFailed(AvidlyAccountSdkErrors.AVIDLY_LOGIN_ERROR_THIRD_SDK_EXCEPTION);
                return;
            }

            thirdLoginSdkDelegate.login(this, new ThirdSdkLoginCallback() {

                @Override
                public void onLoginStart() {

                }

                @Override
                public void onLoginSuccess(LoginUser loginUser) {
                    thirdLoginSdkDelegate = null;
                    onUserLoginSuccessed(loginUser);
                }

                @Override
                public void onLoginFailed(int code) {
                    thirdLoginSdkDelegate = null;

                    onUserLoginFailed(code);
                }
            });
        }
    }

    private void showAccountHomeFragment() {
        homeFragment = new AccountHomeFragment();
        homeFragment.setHomeListener(this);
        transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.avidly_fragment_login, homeFragment);
        transaction.commitAllowingStateLoss();
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

    private Runnable mHideErrorMessageRunnable = new Runnable() {
        @Override
        public void run() {
            hideErrorMessage();
        }
    };

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

        ThreadHelper.removeOnWorkThread(mHideErrorMessageRunnable);
        ThreadHelper.runOnWorkThread(mHideErrorMessageRunnable, Constants.AUTO_CLOSE_ERROR_LAYOUT_MILLS);
    }

    private void showLoadingUI() {
        mLoading = true;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.avidly_loading_layout).setVisibility(View.VISIBLE);
            }
        });
    }

    private void hideLoadingUI() {
        mLoading = false;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.avidly_loading_layout).setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mLoading && keyCode == KeyEvent.KEYCODE_BACK) {
            return true;//不执行父类点击事件
        }
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i("wan", "onConfigurationChanged is " + newConfig.toString());
         transaction.remove(homeFragment);
         showAccountHomeFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("wan", "onDestroy");
    }
}
