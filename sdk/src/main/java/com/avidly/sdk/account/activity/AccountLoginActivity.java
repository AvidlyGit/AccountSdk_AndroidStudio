package com.avidly.sdk.account.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.avidly.sdk.account.base.Constants;
import com.avidly.sdk.account.business.LoginPresenter;
import com.avidly.sdk.account.business.LoginPresenterImpl;
import com.avidly.sdk.account.fragment.AccountHomeFragment;
import com.avidly.sdk.account.fragment.AccountLoadingFragment;
import com.avidly.sdk.account.fragment.AccountLoginFragment;
import com.avidly.sdk.account.listener.AccountHomeListener;
import com.avidly.sdk.account.listener.AccountLoadingListener;
import com.avidly.sdk.account.listener.AccountLoginListener;
import com.sdk.avidly.account.R;

public class AccountLoginActivity extends AppCompatActivity implements AccountLoginView,
        AccountHomeListener, AccountLoadingListener, AccountLoginListener {
    private static final String TAG = "AccountLoginSdk";

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

        if (false) {
            AccountLoadingFragment loadingFragment = (AccountLoadingFragment) mFragmentManager.findFragmentById(R.id.avidly_fragment_login);
            loadingFragment.setCallback(this);

            mPresenter.accountLogin();
        } else {
            showAccountHomeFragment();
        }
    }

    // from view interface
    @Override
    public void onUserLoginSuccessed() {
        Log.i(TAG, "onUserLoginSuccessed: ");

        finish();
    }

    @Override
    public void onUserLoginFailed(String message) {
        Log.i(TAG, "onUserLoginFailed: ");

        showErrorMessage(message);
//        showAccountHomeFragment();
    }

    // from loading fragment
    @Override
    public void onSwitchAccountClicked() {
        Log.i(TAG, "onSwitchAccountClicked: ");

        showAccountHomeFragment();
    }

    // from home fragment
    @Override
    public void onGuestLoginClicked() {
        Log.i(TAG, "onGuestLoginClicked: ");

        AccountLoginFragment loginFragment = AccountLoginFragment.newInstance(Constants.LOGIN_TYPE_BIND);
        loginFragment.setCallback(this);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.avidly_fragment_login, loginFragment);
        transaction.addToBackStack(null);
        transaction.commit();

//        mPresenter.accountLogin();
    }

    @Override
    public void onAvidlyLoginClicked() {
        Log.i(TAG, "onAvidlyLoginClicked: ");

        AccountLoginFragment loginFragment = AccountLoginFragment.newInstance(Constants.LOGIN_TYPE_LOGIN);
        loginFragment.setCallback(this);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.avidly_fragment_login, loginFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFacebookLoginClicked() {
        Log.i(TAG, "onFacebookLoginClicked: ");


    }

    @Override
    public void onTwitterLoginClicked() {
        Log.i(TAG, "onTwitterLoginClicked: ");

    }

    @Override
    public void onGoogleLoginClicked() {
        Log.i(TAG, "onGoogleLoginClicked: ");

    }

    // from login fragment
    @Override
    public void onBackToHomePressed() {
        Log.i(TAG, "onBackToHomePressed: ");
        super.onBackPressed();
    }

    @Override
    public void onAccountLogin() {
        Log.i(TAG, "onAccountLogin: ");

    }

    @Override
    public void onAccountRegist() {
        Log.i(TAG, "onAccountRegist: ");

    }

    @Override
    public void onForgotPasswordClicked() {
        Log.i(TAG, "onForgotPasswordClicked: ");

    }

    @Override
    public void onReadProtocolClicked() {
        Log.i(TAG, "onReadProtocolClicked: ");

    }

    private void showAccountHomeFragment() {
        Log.i(TAG, "showAccountHomeFragment: ");

        AccountHomeFragment homeFragment = new AccountHomeFragment();
        homeFragment.setCallback(this);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.avidly_fragment_login, homeFragment);
        transaction.commit();
    }

    private void hideErrorMessage() {
        Log.i(TAG, "hideErrorMessage: ");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mErrorLayout.setVisibility(View.GONE);
            }
        });
    }

    private void showErrorMessage(final String message) {
        Log.i(TAG, "showErrorMessage: ");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMessgeText.setText(message);
                mErrorLayout.setVisibility(View.VISIBLE);
            }
        });
    }
}
