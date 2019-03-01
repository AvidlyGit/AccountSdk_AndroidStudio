package com.avidly.sdk.account.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.avidly.sdk.account.base.Constants;
import com.avidly.sdk.account.base.utils.LogUtils;
import com.avidly.sdk.account.base.utils.Md5Utils;
import com.avidly.sdk.account.base.utils.ThreadHelper;
import com.avidly.sdk.account.data.user.Account;
import com.avidly.sdk.account.data.user.LoginUser;
import com.avidly.sdk.account.data.user.LoginUserManager;
import com.avidly.sdk.account.request.HttpCallback;
import com.avidly.sdk.account.request.HttpRequest;
import com.avidly.sdk.account.request.URLConstant;
import com.sdk.avidly.account.R;

import org.json.JSONObject;

import static com.avidly.sdk.account.base.AvidlyAccountSdkErrors.AVIDLY_OLD_PASSWORD_ALTER_ERROR;

public class AccountUserPwdAlterFragment extends Fragment {
    private View mErrorLayout;
    private TextView mMessgeText;
    private View mMessgeClose;

    private AccountUserRootFragment.LoadingUICallback loadingUICallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.avidly_fragment_user_change_pwd, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setLoadingUICallback(AccountUserRootFragment.LoadingUICallback loadingUICallback) {
        this.loadingUICallback = loadingUICallback;
    }

    public void resetPwd() {
        EditText editText = getView().findViewById(R.id.avidly_editor_chagne_old_pwd);
        editText.setText("");
        editText = getView().findViewById(R.id.avidly_editor_chagne_new_pwd);
        editText.setText("");
        editText = getView().findViewById(R.id.avidly_editor_chagne_new_pwd2);
        editText.setText("");
    }

    private void initView(final View rootview) {

        mErrorLayout = rootview.findViewById(R.id.avidly_error_layout);
        mMessgeText = mErrorLayout.findViewById(R.id.avidly_error_message);
        mMessgeClose = mErrorLayout.findViewById(R.id.avidly_error_close);
        mMessgeClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideErrorMessage();
            }
        });

        rootview.findViewById(R.id.avidly_fragment_user_change_pwd_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPwdClick(rootview);
            }
        });
        EditText editText = rootview.findViewById(R.id.avidly_editor_chagne_new_pwd2);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_DONE:
                        onPwdClick(rootview);
                        break;
                }
                return false;
            }
        });
    }

    private void onPwdClick(final View rootview) {
        EditText editText = rootview.findViewById(R.id.avidly_editor_chagne_old_pwd);
        String oldpwd = editText.getText().toString();
        editText = rootview.findViewById(R.id.avidly_editor_chagne_new_pwd);
        String newpwd1 = editText.getText().toString();
        editText = rootview.findViewById(R.id.avidly_editor_chagne_new_pwd2);
        String newpwd2 = editText.getText().toString();
        checkAndSubmit(oldpwd, newpwd1, newpwd2);
    }

    private void checkAndSubmit(String odlpwd, final String newpwd1, final String newpwd2) {
        if (TextUtils.isEmpty(odlpwd)) {
            showErrorMessage(getResources().getString(R.string.avidly_string_user_alter_pwd_empty_oldpwd));
//            Utils.showToastTip(getContext(), R.string.avidly_string_user_alter_pwd_empty_oldpwd, true);
            return;
        }

        if (TextUtils.isEmpty(newpwd1)) {
            showErrorMessage(getResources().getString(R.string.avidly_string_user_alter_pwd_empty_newpwd));
//            Utils.showToastTip(getContext(), R.string.avidly_string_user_alter_pwd_empty_newpwd, true);
            return;
        }

        if (newpwd1.length() < 6 || newpwd1.length() >= 16) {
            showErrorMessage(getResources().getString(R.string.avidly_string_user_alter_pwd_length));
//            Utils.showToastTip(getContext(), R.string.avidly_string_user_alter_pwd_length, true);
            return;
        }

        if (TextUtils.isEmpty(newpwd2)) {
            showErrorMessage(getResources().getString(R.string.avidly_string_user_alter_pwd_empty_newpwd2));
//            Utils.showToastTip(getContext(), R.string.avidly_string_user_alter_pwd_empty_newpwd2, true);
            return;
        }

        if (!newpwd2.equals(newpwd1)) {
            showErrorMessage(getResources().getString(R.string.avidly_string_user_alter_pwd_wrong_newpwd2));
//            Utils.showToastTip(getContext(), R.string.avidly_string_user_alter_pwd_wrong_newpwd2, true);
            return;
        }


        if (newpwd1.equals(odlpwd)) {
            showErrorMessage(getResources().getString(R.string.avidly_string_user_alter_pwd_new_pwd));
//            Utils.showToastTip(getContext(), R.string.avidly_string_user_alter_pwd_new_pwd, true);
            return;
        }

        String userName = LoginUserManager.getAccountLoginUser().findAccountByMode(Account.ACCOUNT_MODE_AVIDLY).accountName;
        odlpwd = Md5Utils.textOfMd5(odlpwd);
        final String newpwdmd5 = Md5Utils.textOfMd5(newpwd1);
        String url = URLConstant.getAlterPwdAPI(userName, odlpwd, newpwdmd5);
        LogUtils.i("alter pwd url:" + url);
        HttpRequest.requestHttpByPost(url, null, new HttpCallback<String>() {
            @Override
            public void onResponseSuccess(String result) {
                LogUtils.i("onResponseSuccess:" + result);
                boolean keep = true;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.optBoolean("success")) {
                        LoginUser user = LoginUserManager.getCurrentActiveLoginUser();
                        if (user != null) {
                            Account account = user.findAccountByMode(Account.ACCOUNT_MODE_AVIDLY);
                            if (account != null) {
                                account.accountPwd = newpwdmd5;
                                LoginUserManager.saveAccountUsers();
                            }
                        }
                        keep = false;
                        showErrorMessage(getResources().getString(R.string.avidly_string_user_alter_pwd_send_success));
//                        Utils.showToastTip(getContext(), R.string.avidly_string_user_alter_pwd_send_success, true);
                    } else {
                        if (AVIDLY_OLD_PASSWORD_ALTER_ERROR == jsonObject.optInt("code")) {
                            showErrorMessage(getResources().getString(R.string.avidly_string_user_alter_pwd_send_fail_wrong_pwd));
//                            Utils.showToastTip(getContext(), R.string.avidly_string_user_alter_pwd_send_fail_wrong_pwd, true);
                        } else {
                            showErrorMessage(getResources().getString(R.string.avidly_string_user_alter_pwd_send_fail));
//                            Utils.showToastTip(getContext(), R.string.avidly_string_user_alter_pwd_send_fail, true);
                        }
                    }
                } catch (Exception e) {
                    showErrorMessage(getResources().getString(R.string.avidly_string_user_alter_pwd_send_fail));
//                    Utils.showToastTip(getContext(), R.string.avidly_string_user_alter_pwd_send_fail, true);
                    e.printStackTrace();
                } finally {
                    if (loadingUICallback != null) {
                        loadingUICallback.notifyShowLoadingUI(false, keep);
                    }
                }

            }

            @Override
            public void onResponedFail(Throwable e, int code) {
                LogUtils.i("onResponedFail, exception:" + e + ", code:" + code);
                if (loadingUICallback != null) {
                    loadingUICallback.notifyShowLoadingUI(false, false);
                }
                showErrorMessage(getResources().getString(R.string.avidly_string_user_alter_pwd_send_fail));
//                Utils.showToastTip(getContext(), R.string.avidly_string_user_alter_pwd_send_fail, true);
            }
        });

        if (loadingUICallback != null) {
            loadingUICallback.notifyShowLoadingUI(true, true);
        }

    }

    private Runnable mHideErrorMessageRunnable = new Runnable() {
        @Override
        public void run() {
            hideErrorMessage();
        }
    };

    private void hideErrorMessage() {
        ThreadHelper.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (getView()!=null){
                    getView().findViewById(R.id.avidly_error_layout).setVisibility(View.GONE);
                }

            }
        });
    }

    private void showErrorMessage(final String message) {
        ThreadHelper.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                mMessgeText.setText(message);
                getView().findViewById(R.id.avidly_error_layout).setVisibility(View.VISIBLE);
            }
        });

        ThreadHelper.removeOnWorkThread(mHideErrorMessageRunnable);
        ThreadHelper.runOnWorkThread(mHideErrorMessageRunnable, Constants.AUTO_CLOSE_ERROR_LAYOUT_MILLS);
    }
}
