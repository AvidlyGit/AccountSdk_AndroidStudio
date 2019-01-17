package com.avidly.sdk.account.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avidly.sdk.account.callback.AccountHomeCallback;

import com.sdk.avidly.account.R;

/**
 * Created by t.wang on 2019/1/14.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public class AccountHomeFragment extends DialogFragment implements View.OnClickListener {
    private AccountHomeCallback mCallback;

    public void setCallback(AccountHomeCallback callback) {
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

        int id = view.getId();
        if (id == R.id.guest_login) {
            mCallback.onGuestLoginClicked();
        }
        if (id == R.id.avidly_login) {
            mCallback.onAvidlyLoginClicked();
        }
        if (id == R.id.facebook_login) {
            mCallback.onFacebookLoginClicked();
        }
        if (id == R.id.twitter_login) {
            mCallback.onTwitterLoginClicked();
        }
        if (id == R.id.google_login) {
            mCallback.onGoogleLoginClicked();
        }

    }
}
