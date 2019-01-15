package com.avidly.sdk.account.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avidly.sdk.account.callback.AccountLoginCallback;

import com.sdk.avidly.account.R;

/**
 * Created by t.wang on 2019/1/14.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public class AccountHomeFragment extends DialogFragment implements View.OnClickListener {
    AccountLoginCallback mCallback;

    public void setCallback(AccountLoginCallback callback) {
        mCallback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.avidly_fragment_home, container, false);
        view.findViewById(R.id.guest_login).setOnClickListener(this);
        view.findViewById(R.id.avidly_login).setOnClickListener(this);
        view.findViewById(R.id.facebook_login).setOnClickListener(this);
        view.findViewById(R.id.twitter_login).setOnClickListener(this);
        view.findViewById(R.id.google_login).setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        if (mCallback == null) {

            return;
        }

        int i = view.getId();
        if (i == R.id.guest_login) {
            mCallback.onGuestLoginClicked();
        }
        if (i == R.id.avidly_login) {
            mCallback.onAvidlyLoginClicked();
        }
        if (i == R.id.facebook_login) {
            mCallback.onFacebookeLoginClicked();
        }
        if (i == R.id.twitter_login) {
            mCallback.onTwitterLoginClicked();
        }
        if (i == R.id.google_login) {
            mCallback.onGoogleLoginClicked();
        }

    }
}
