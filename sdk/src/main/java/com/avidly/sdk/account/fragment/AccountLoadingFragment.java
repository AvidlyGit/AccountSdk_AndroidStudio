package com.avidly.sdk.account.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avidly.sdk.account.listener.AccountLoadingListener;
import com.sdk.avidly.account.R;

/**
 * Created by t.wang on 2019/1/16.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public class AccountLoadingFragment extends DialogFragment implements View.OnClickListener {
    private AccountLoadingListener mCallback;

    public void setCallback(AccountLoadingListener callback) {
        mCallback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.avidly_fragment_loading, container, false);
        TextView userNameText = view.findViewById(R.id.avidly_user_name);
        userNameText.setText("Tao.Wang");
        view.findViewById(R.id.avidly_switch_user).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (mCallback == null) {

            return;
        }

        if (id == R.id.avidly_switch_user) {
            mCallback.onSwitchAccountClicked();
        }
    }
}
