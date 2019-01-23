package com.avidly.sdk.account.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avidly.sdk.account.base.Constants;
import com.avidly.sdk.account.base.utils.Md5Utils;
import com.sdk.avidly.account.R;

/**
 * Created by t.wang on 2019/1/18.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public class AccountRegistSubFragment extends AccountBaseSubFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSubFragmentType = Constants.SUB_FRAGMENT_TYPE_REGIST;

        View view = inflater.inflate(R.layout.avidly_fragment_login_sub, container, false);
        super.initView(view);

        mActionButton.setText(getString(R.string.avidly_string_action_regist));
        mForgotPassword.setVisibility(View.GONE);
        mProtocolLayout.setVisibility(View.VISIBLE);

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
                mLoginListener.onAccountRegistClicked(mInputEmail.getText().toString(), mPassword);
            }
        }
    }

}
