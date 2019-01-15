package com.avidly.sdk.account.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.avidly.sdk.account.callback.AccountLoginCallback;
import com.avidly.sdk.account.fragment.AccountHomeFragment;
import com.avidly.sdk.account.fragment.AccountLoginFragment;

import com.sdk.avidly.account.R;


public class AccountLoginActivity extends Activity implements AccountLoginCallback {
    private static final String TAG = "AccountLoginSdk";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avidly_activity_login);

        AccountHomeFragment homeFragment = (AccountHomeFragment) getFragmentManager().findFragmentById(R.id.avidly_fragment_login);
        homeFragment.setCallback(this);
    }

    @Override
    public void onGuestLoginClicked() {
        Log.i(TAG, "onGuestLoginClicked: ");
    }

    @Override
    public void onAvidlyLoginClicked() {
        Log.i(TAG, "onAvidlyLoginClicked: ");

        AccountLoginFragment loginFragment = new AccountLoginFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.avidly_fragment_login, loginFragment);
        transaction.commit();
    }

    @Override
    public void onFacebookeLoginClicked() {
        Log.i(TAG, "onFacebookeLoginClicked: ");
    }

    @Override
    public void onTwitterLoginClicked() {
        Log.i(TAG, "onTwitterLoginClicked: ");
    }

    @Override
    public void onGoogleLoginClicked() {
        Log.i(TAG, "onGoogleLoginClicked: ");
    }
}
