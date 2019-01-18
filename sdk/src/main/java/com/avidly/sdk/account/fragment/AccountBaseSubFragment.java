package com.avidly.sdk.account.fragment;

import android.support.v4.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdk.avidly.account.R;

/**
 * Created by t.wang on 2019/1/18.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public class AccountBaseSubFragment extends Fragment implements View.OnClickListener {
    protected EditText mInputEmail;
    protected EditText mInputPassword;
    protected ImageView mIconClear;
    protected ImageView mIconEye;
    protected TextView mActionButton;

    protected View mForgotPassword;
    protected View mAgreeProtocol;
    protected View mReadProtocol;
    protected View mProtocolLayout;

    protected boolean isOpenEye = false;
    protected boolean isAgreeProtocol = true;

    protected void initView(View view) {
        mInputEmail = view.findViewById(R.id.avidly_input_email);
        mInputPassword = view.findViewById(R.id.avidly_input_password);
        mIconClear = view.findViewById(R.id.avidly_icon_clear);
        mIconClear.setOnClickListener(this);
        mIconEye = view.findViewById(R.id.avidly_icon_eye);
        mIconEye.setOnClickListener(this);
        mActionButton = view.findViewById(R.id.avidly_account_action);
        mActionButton.setOnClickListener(this);

        mForgotPassword = view.findViewById(R.id.avidly_forgot_password);
        mForgotPassword.setOnClickListener(this);
        mAgreeProtocol = view.findViewById(R.id.avidly_agree_protocol);
        mAgreeProtocol.setOnClickListener(this);
        mAgreeProtocol.setSelected(isAgreeProtocol);
        mReadProtocol = view.findViewById(R.id.avidly_read_protocol);
        mReadProtocol.setOnClickListener(this);
        mProtocolLayout = view.findViewById(R.id.avidly_protocol_layout);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.avidly_icon_clear) {
            mInputEmail.setText("");
        }
        if (id == R.id.avidly_icon_eye) {
            if (!isOpenEye) {
                mIconEye.setSelected(true);
                isOpenEye = true;
                mInputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                mIconEye.setSelected(false);
                isOpenEye = false;
                mInputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            mInputPassword.setSelection(mInputPassword.getText().length());
        }

        if (id == R.id.avidly_agree_protocol) {
            if (isAgreeProtocol) {
                isAgreeProtocol = false;
                mAgreeProtocol.setSelected(false);
            } else {
                isAgreeProtocol = true;
                mAgreeProtocol.setSelected(true);
            }
        }
    }
}
