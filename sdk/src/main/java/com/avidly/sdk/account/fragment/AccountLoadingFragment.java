package com.avidly.sdk.account.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avidly.sdk.account.base.Constants;
import com.avidly.sdk.account.data.user.Account;
import com.avidly.sdk.account.data.user.LoginUser;
import com.avidly.sdk.account.data.user.LoginUserManager;
import com.avidly.sdk.account.listener.AccountLoadingListener;
import com.sdk.avidly.account.R;

/**
 * Created by t.wang on 2019/1/16.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public class AccountLoadingFragment extends DialogFragment implements View.OnClickListener {
    private AccountLoadingListener mLoadingListener;
    private View mLoginLayout;
    private View mSwitchUser;

    public void setLoadingListener(AccountLoadingListener listener) {
        mLoadingListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.avidly_fragment_loading, container, false);
        mLoginLayout = view.findViewById(R.id.avidly_logining_layout);
        mSwitchUser = view.findViewById(R.id.avidly_switch_user);
        mSwitchUser.setOnClickListener(this);

        mLoginLayout.setVisibility(View.GONE);
        mSwitchUser.setVisibility(View.VISIBLE);

        TextView userNameText = view.findViewById(R.id.avidly_user_name);
        LoginUser activedUser = LoginUserManager.getCurrentActiveLoginUser();
        if (activedUser != null) {
            boolean isGuest = activedUser.getLoginedMode() == Account.ACCOUNT_MODE_GUEST;
            if (isGuest) {
                userNameText.setText(getString(R.string.avidly_string_usermanger_guest));
            } else {
                userNameText.setText(activedUser.findActivedAccount().nickname);
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startAutoLogin();
            }
        }, Constants.AUTO_LOGIN_TIME_OUT_MILLS);

        return view;
    }

    private void startAutoLogin() {
        mLoginLayout.setVisibility(View.VISIBLE);
        mSwitchUser.setVisibility(View.GONE);

        if (mLoadingListener != null) {
            mLoadingListener.onWaitingTimeOut();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (mLoadingListener == null) {

            return;
        }

        if (id == R.id.avidly_switch_user) {
            mLoadingListener.onSwitchAccountClicked();
        }
    }
}
