package com.avidly.sdk.account.fragment;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avidly.sdk.account.base.Constants;
import com.avidly.sdk.account.base.utils.Md5Utils;
import com.avidly.sdk.account.base.utils.Utils;
import com.avidly.sdk.account.listener.AccountLoginListener;
import com.sdk.avidly.account.R;

public class AccountBaseSubFragment extends Fragment implements View.OnClickListener {
    protected AccountLoginListener mLoginListener;
    protected int mSubFragmentType;

    public void setLoginListener(AccountLoginListener listener) {
        mLoginListener = listener;
    }

    protected EditText mInputEmail;
    protected EditText mInputPassword;
    protected ImageView mIconClear;
    protected ImageView mIconEye;
    protected TextView mActionButton;

    protected View mForgotPassword;
    protected View mAgreeProtocol;
    protected View mReadProtocol;
    protected View mProtocolLayout;

    protected String mPassword;
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

    protected boolean checkInputValid() {
        String email = mInputEmail.getText().toString();
        String password = mInputPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mLoginListener.onLoginErrorOccured(getString(R.string.avidly_string_email_account_error));
            mInputEmail.setBackgroundResource(R.drawable.avidly_edit_text_invalid);
            return false;
        } else {
            mInputEmail.setBackgroundResource(R.drawable.avidly_edit_text_normal);
        }

        if (!Utils.checkEmailValid(email)) {
            mLoginListener.onLoginErrorOccured(getString(R.string.avidly_string_email_format_error));
            mIconClear.setVisibility(View.VISIBLE);
            mInputEmail.setBackgroundResource(R.drawable.avidly_edit_text_invalid);
            return false;
        } else {
            mIconClear.setVisibility(View.GONE);
            mInputEmail.setBackgroundResource(R.drawable.avidly_edit_text_normal);
        }

        if (TextUtils.isEmpty(password)) {
            mLoginListener.onLoginErrorOccured(getString(R.string.avidly_string_email_pwd_input_error));
            mInputPassword.setBackgroundResource(R.drawable.avidly_edit_text_invalid);
            return false;
        } else {
            mInputPassword.setBackgroundResource(R.drawable.avidly_edit_text_normal);
        }

        if (password.length() < 6 || password.length() > 16) {
            mLoginListener.onLoginErrorOccured(getString(R.string.avidly_string_user_alter_pwd_length));
            mInputPassword.setBackgroundResource(R.drawable.avidly_edit_text_invalid);
            return false;
        } else {
            mInputPassword.setBackgroundResource(R.drawable.avidly_edit_text_normal);
        }

        if (mSubFragmentType == Constants.SUB_FRAGMENT_TYPE_BIND || mSubFragmentType == Constants.SUB_FRAGMENT_TYPE_REGIST) {
            if (!isAgreeProtocol) {
                mLoginListener.onLoginErrorOccured(getString(R.string.avidly_string_agree_protocol_error));
                return false;
            }
        }

        mPassword = Md5Utils.textOfMd5(password);
        return true;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.avidly_icon_clear) {
            mInputEmail.setText("");
            mInputEmail.setBackgroundResource(R.drawable.avidly_edit_text_normal);
            mIconClear.setVisibility(View.GONE);
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

        if (mLoginListener == null) {

            return;
        }

        if (id == R.id.avidly_forgot_password) {
            mLoginListener.onForgotPasswordClicked();
        }

        if (id == R.id.avidly_read_protocol) {
            mLoginListener.onReadProtocolClicked();
        }

    }
}
