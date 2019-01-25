package com.avidly.sdk.account.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avidly.sdk.account.base.Constants;
import com.avidly.sdk.account.base.utils.Md5Utils;
import com.avidly.sdk.account.data.user.Account;
import com.avidly.sdk.account.data.user.LoginUser;
import com.avidly.sdk.account.data.user.LoginUserManager;
import com.sdk.avidly.account.R;

/**
 * Created by t.wang on 2019/1/18.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public class AccountLoginSubFragment extends AccountBaseSubFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSubFragmentType = Constants.SUB_FRAGMENT_TYPE_LOGIN;

        View view = inflater.inflate(R.layout.avidly_fragment_login_sub, container, false);
        super.initView(view);

        LoginUser activedUser = LoginUserManager.getCurrentActiveLoginUser();
        if (activedUser != null) {
            Account account = activedUser.findActivedAccount();
            if (account != null) {
                mInputEmail.setText(account.accountName);
            }
        }

        mActionButton.setText(getString(R.string.avidly_string_action_login));
        mForgotPassword.setVisibility(View.VISIBLE);
        mProtocolLayout.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        int id = view.getId();
        if (mLoginListener == null) {

            return;
        }

        if (id == R.id.avidly_account_action) {
            if (checkInputValid()) {
                mLoginListener.onAccountLoginClicked(mInputEmail.getText().toString(), mPassword);
            }
        }
    }

}
