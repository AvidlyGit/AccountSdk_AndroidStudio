package com.avidly.sdk.account.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avidly.sdk.account.base.Constants;
import com.avidly.sdk.account.callback.AccountLoginCallback;
import com.sdk.avidly.account.R;

/**
 * Created by t.wang on 2019/1/15.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public class AccountLoginFragment extends DialogFragment implements View.OnClickListener {
    private AccountLoginCallback mCallback;
    private int mOperationType = Constants.LOGIN_TYPE_LOGIN;
    private static String OPERATION_KEY = "operation";
    private boolean isOpenEye = false;
    private boolean isAgreeProtocol = true;

    private TextView mUserBindTitle;
    private TextView mUserLoginTitle;
    private TextView mUserRegistTitle;
    private TextView mLine1;
    private TextView mLine2;

    private EditText mInputEmail;
    private EditText mInputPassword;
    private ImageView mIconClear;
    private ImageView mIconEye;
    private TextView mActionButton;

    private View mForgotPassword;
    private View mAgreeProtocol;
    private View mAgreeBox;

    public static AccountLoginFragment newInstance(int operationType) {
        AccountLoginFragment loginFragment = new AccountLoginFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(OPERATION_KEY, operationType);
        loginFragment.setArguments(bundle);
        return loginFragment;
    }

    public void setCallback(AccountLoginCallback callback) {
        mCallback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.avidly_fragment_login, container, false);
        View iconBack = view.findViewById(R.id.avidly_login_back);
        iconBack.setOnClickListener(this);

        mUserBindTitle = view.findViewById(R.id.avidly_user_bind_title);
        mUserLoginTitle = view.findViewById(R.id.avidly_user_login_title);
        mUserLoginTitle.setOnClickListener(this);
        mUserRegistTitle = view.findViewById(R.id.avidly_user_register_title);
        mUserRegistTitle.setOnClickListener(this);

        mLine1 = view.findViewById(R.id.avidly_login_line_1);
        mLine2 = view.findViewById(R.id.avidly_login_line_2);

        mInputEmail = view.findViewById(R.id.avidly_input_email);
        mInputPassword = view.findViewById(R.id.avidly_input_password);
        mIconClear = view.findViewById(R.id.avidly_icon_clear);
        mIconClear.setOnClickListener(this);
        mIconEye = view.findViewById(R.id.avidly_icon_eye);
        mIconEye.setOnClickListener(this);
        mActionButton = view.findViewById(R.id.avidly_account_action);

        mForgotPassword = view.findViewById(R.id.avidly_forgot_password);
        mForgotPassword.setOnClickListener(this);
        mAgreeProtocol = view.findViewById(R.id.avidly_agree_protocol);
        mAgreeProtocol.setOnClickListener(this);
        mAgreeBox = view.findViewById(R.id.avidly_agree_box);
        mAgreeBox.setSelected(isAgreeProtocol);

        mOperationType = getArguments().getInt(OPERATION_KEY);
        switchUiFromType();
        return view;
    }

    private void switchUiFromType() {
        if (mOperationType == Constants.LOGIN_TYPE_BIND) {
            mUserBindTitle.setTextColor(getResources().getColor(R.color.avildy_color_text_4));
            mUserBindTitle.setVisibility(View.VISIBLE);

            mUserLoginTitle.setVisibility(View.GONE);
            mUserRegistTitle.setVisibility(View.GONE);

            mIconClear.setVisibility(View.GONE);
            mIconEye.setVisibility(View.GONE);
            mLine1.setVisibility(View.VISIBLE);
            mLine2.setVisibility(View.VISIBLE);

            mActionButton.setText(R.string.avidly_string_action_bind);
            mForgotPassword.setVisibility(View.GONE);
            mAgreeProtocol.setVisibility(View.VISIBLE);
        }

        if (mOperationType == Constants.LOGIN_TYPE_LOGIN) {
            mUserLoginTitle.setTextColor(getResources().getColor(R.color.avildy_color_text_4));
            mUserLoginTitle.setVisibility(View.VISIBLE);

            mUserRegistTitle.setTextColor(getResources().getColor(R.color.avildy_color_text_2));
            mUserRegistTitle.setVisibility(View.VISIBLE);

            mUserBindTitle.setVisibility(View.GONE);

            mIconClear.setVisibility(View.VISIBLE);
            mIconEye.setVisibility(View.VISIBLE);
            mLine1.setVisibility(View.VISIBLE);
            mLine2.setVisibility(View.INVISIBLE);

            mActionButton.setText(R.string.avidly_string_action_login);
            mForgotPassword.setVisibility(View.VISIBLE);
            mAgreeProtocol.setVisibility(View.GONE);
        }

        if (mOperationType == Constants.LOGIN_TYPE_REGIST) {
            mUserLoginTitle.setTextColor(getResources().getColor(R.color.avildy_color_text_2));
            mUserLoginTitle.setVisibility(View.VISIBLE);

            mUserRegistTitle.setTextColor(getResources().getColor(R.color.avildy_color_text_4));
            mUserRegistTitle.setVisibility(View.VISIBLE);

            mUserBindTitle.setVisibility(View.GONE);

            mIconClear.setVisibility(View.GONE);
            mIconEye.setVisibility(View.GONE);
            mLine1.setVisibility(View.INVISIBLE);
            mLine2.setVisibility(View.VISIBLE);

            mActionButton.setText(R.string.avidly_string_action_regist);
            mForgotPassword.setVisibility(View.GONE);
            mAgreeProtocol.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if (mCallback == null) {

            return;
        }

        int id = view.getId();
        if (id == R.id.avidly_login_back) {
            mCallback.onBackToHomePressed();
        }

        if (id == R.id.avidly_user_login_title) {
            mOperationType = Constants.LOGIN_TYPE_LOGIN;
            switchUiFromType();
        }
        if (id == R.id.avidly_user_register_title) {
            mOperationType = Constants.LOGIN_TYPE_REGIST;
            switchUiFromType();
        }
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
        if (id == R.id.avidly_forgot_password) {

        }
        if (id == R.id.avidly_agree_protocol) {
            if (isAgreeProtocol) {
                isAgreeProtocol = false;
                mAgreeBox.setSelected(false);
            } else {
                isAgreeProtocol = true;
                mAgreeBox.setSelected(true);
            }
        }

    }
}
