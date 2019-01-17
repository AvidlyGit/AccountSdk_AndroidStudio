package com.avidly.sdk.account.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.avidly.sdk.account.base.Constants;
import com.avidly.sdk.account.business.LoginPresenter;
import com.avidly.sdk.account.business.LoginPresenterImpl;
import com.avidly.sdk.account.callback.AccountHomeCallback;
import com.avidly.sdk.account.callback.AccountLoadingCallback;
import com.avidly.sdk.account.callback.AccountLoginCallback;
import com.avidly.sdk.account.fragment.AccountErrorFragment;
import com.avidly.sdk.account.fragment.AccountHomeFragment;
import com.avidly.sdk.account.fragment.AccountLoadingFragment;
import com.avidly.sdk.account.fragment.AccountLoginFragment;

import com.sdk.avidly.account.R;


public class AccountLoginActivity extends FragmentActivity implements AccountLoginView,
        AccountHomeCallback, AccountLoadingCallback, AccountLoginCallback {
    private static final String TAG = "AccountLoginSdk";

    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avidly_activity_login);

        mPresenter = new LoginPresenterImpl(this);
        hideErrorFragment();

        if (false) {
            AccountLoadingFragment loadingFragment = (AccountLoadingFragment) mFragmentManager.findFragmentById(R.id.avidly_fragment_login);
            loadingFragment.setCallback(this);
        } else {
            showAccountHomeFragment();
        }
    }

    @Override
    public void onSwitchAccountClicked() {
        Log.i(TAG, "onSwitchAccountClicked: ");
        showAccountHomeFragment();
    }

    @Override
    public void onUserLoginSuccessed() {
        Log.i(TAG, "onUserLoginSuccessed: ");

    }

    @Override
    public void onUserLoginFailed() {
        Log.i(TAG, "onUserLoginFailed: ");

    }

    public void showAccountHomeFragment() {
        AccountHomeFragment homeFragment = new AccountHomeFragment();
        homeFragment.setCallback(this);
        android.support.v4.app.FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.avidly_fragment_login, homeFragment);
        transaction.commit();
    }

    @Override
    public void onBackToHomePressed() {
        Log.i(TAG, "onGuestLoginClicked: ");
        showAccountHomeFragment();
    }

    @Override
    public void onGuestLoginClicked() {
        Log.i(TAG, "onGuestLoginClicked: ");
        mPresenter.guestLogin();
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

    @Override
    public void hideErrorFragment() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        AccountErrorFragment errorFragment = (AccountErrorFragment) mFragmentManager.findFragmentById(R.id.avidly_fragment_error);
        transaction.hide(errorFragment);
        transaction.commit();
    }

    @Override
    public void showErrorFragment() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        AccountErrorFragment errorFragment = (AccountErrorFragment) mFragmentManager.findFragmentById(R.id.avidly_fragment_error);
        transaction.show(errorFragment);
        transaction.commit();
    }

}
