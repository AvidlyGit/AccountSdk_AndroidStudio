package com.avidly.sdk.account.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avidly.sdk.account.base.AvidlyAccountSdkErrors;
import com.avidly.sdk.account.activity.AccountLoginActivity;
import com.avidly.sdk.account.adapter.BaseAdapter;
import com.avidly.sdk.account.base.Constants;
import com.avidly.sdk.account.base.utils.LogUtils;
import com.avidly.sdk.account.base.utils.ThreadHelper;
import com.avidly.sdk.account.business.LoginRequest;
import com.avidly.sdk.account.business.LoginRequestCallback;
import com.avidly.sdk.account.data.adapter.UserBindData;
import com.avidly.sdk.account.data.adapter.UserOperationData;
import com.avidly.sdk.account.data.user.Account;
import com.avidly.sdk.account.data.user.LoginUser;
import com.avidly.sdk.account.data.user.LoginUserManager;
import com.avidly.sdk.account.third.FacebookLoginSdk;
import com.avidly.sdk.account.third.ThirdLoginSdkDelegate;
import com.avidly.sdk.account.third.ThirdSdkFactory;
import com.avidly.sdk.account.third.ThirdSdkLoginCallback;
import com.sdk.avidly.account.R;

import static com.avidly.sdk.account.base.AvidlyAccountSdkErrors.AVIDLY_LOGIN_ERROR_ACCESS_TOKEN_BOUNDED_OTHER_GGID;
import static com.avidly.sdk.account.base.AvidlyAccountSdkErrors.AVIDLY_LOGIN_ERROR_GGID_NOT_BOUNDED_THIRD_SDK;

public class AccountUserRootFragment extends BaseFragment {
    private View mErrorLayout;
    private TextView mMessgeText;
    private View mMessgeClose;

    private boolean isShowChangePwdUI;
    private boolean isShowBindAccountUI;
    private boolean isShowLoadingUI;
    private ThirdLoginSdkDelegate thirdLoginSdkDelegate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.avidly_fragment_user_manager, container, false);
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (thirdLoginSdkDelegate != null) {
            thirdLoginSdkDelegate.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onBackKeyDown() {
        if (isShowLoadingUI) {
            return true;
        }
        if (isShowBindAccountUI) {
            hideBindOtherAccountUI();
            return true;
        }

        if (isShowChangePwdUI) {
            hideChangePasswordUI();
            return true;
        }
        return false;
    }

    private void initView(View view) {
        AccountUserManagerFragment userManagerFragment = (AccountUserManagerFragment) getChildFragmentManager().findFragmentById(R.id.avidly_fragment_user_manager);
        userManagerFragment.setItemClickListener(userOperationListener);

        AccountUserBindFragment bindFragment = (AccountUserBindFragment) getChildFragmentManager().findFragmentById(R.id.avidly_fragment_user_account_bind_fragment);
        bindFragment.setItemClickListener(userBindAccountListener);

        mErrorLayout = view.findViewById(R.id.avidly_error_layout_root);
        mMessgeText = mErrorLayout.findViewById(R.id.avidly_error_message);
        mMessgeClose = mErrorLayout.findViewById(R.id.avidly_error_close);
        mMessgeClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideErrorMessage();
            }
        });

        view.findViewById(R.id.avidly_user_manager_title_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!onBackKeyDown()) {
                    getActivity().finish();
                }
            }
        });

        AccountUserPwdAlterFragment alterFragment = (AccountUserPwdAlterFragment) getChildFragmentManager().findFragmentById(R.id.avidly_fragment_user_alter_pwd_fragment);
        alterFragment.setLoadingUICallback(new LoadingUICallback() {
            @Override
            public void notifyShowLoadingUI(boolean show, boolean keep) {
                if (show) {
                    showLoadingUI();
                } else {
                    hideLoadingUI();
                    if (!keep) {
                        hideChangePasswordUI();
                    }
                }
            }
        });
    }

    BaseAdapter.onRecyclerViewItemClickListener userOperationListener = new BaseAdapter.onRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(Object data, int type) {
            switch (type) {
                case UserOperationData.USER_OPERATION_TYPE_AVIDLY:
                    toBindAvidlyAccount();
                    break;
                case UserOperationData.USER_OPERATION_TYPE_SWITCH:
                    toSwithAccount();
                    break;
                case UserOperationData.USER_OPERATION_TYPE_BIND_OTHER:
                    showBindOtherAccountUI();
                    break;
                case UserOperationData.USER_OPERATION_TYPE_CHANGE_PWD:
                    showChangePasswordUI();
                    break;
            }
        }
    };


    BaseAdapter.onRecyclerViewItemClickListener userBindAccountListener = new BaseAdapter.onRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(Object data, int mode) {
            LoginUser user = LoginUserManager.getCurrentActiveLoginUser();
            if (user == null) {
                LogUtils.w("not found logined user, check value: " + data, null);
                return;
            }

            Account account = user.findAccountByMode(mode);
            boolean unbind = account == null || !account.isBinded;
            if (data instanceof UserBindData) {
                unbind = !((UserBindData) data).isbinded;
            }
            if (unbind) {
                tryToBindThirdSdk(mode);
            } else {
                tryToUnBindThirdSdk(mode);
            }
        }
    };

    // TODO: 2019/1/30 解除绑定的操作是否需要一个提醒窗口
    private void tryToUnBindThirdSdk(int mode) {
        switch (mode) {
            case Account.ACCOUNT_MODE_FACEBOOK:
                doThirdSdkUnBind(mode);
                break;
        }
    }

    private void doThirdSdkUnBind(final int mode) {
        if (!ThirdSdkFactory.isExistSdkLib(mode)) {
            LogUtils.w("this sdk lib is not exist, mode :" + mode, null);
            return;
        }

        LoginUser user = LoginUserManager.getCurrentActiveLoginUser();
        if (user == null) {
            LogUtils.w("no user is logined.", null);
            return;
        }

        // 经严曦讨论后，以下情况，不能解绑
        // 1.游客账号【绑定第三方，非avid.ly账号】 无法解绑
        // 2.直接facebook登录后 无法解绑
        // 3.avidly帐号登陆后，绑定facebook，可以解绑
        if (user.getLoginedMode() == mode) {
            //Account account = user.findAccountByMode(Account.ACCOUNT_MODE_AVIDLY);
            //if (account == null || !account.isBinded) {
            showErrorMessage(getResources().getString(R.string.avidly_string_user_unbind_disable));
//            Utils.showToastTip(getContext(), R.string.avidly_string_user_unbind_disable, true);
            return;
            //}
        }

        if (mode == Account.ACCOUNT_MODE_FACEBOOK) {
            String token = FacebookLoginSdk.getToken();
            if (TextUtils.isEmpty(token)) {
                LogUtils.w("this facebook sdk's token is null, need login again.", null);
                return;
            }

            LoginRequest.facebookSdkUnBind(user.ggid, token, new LoginRequestCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    LogUtils.i("doThirdSdkUnBind, result: " + result);
                    LoginUserManager.onThirdSdkUnbind(mode);
                    freshBindAdapterInMainThread();
                    hideLoadingUI();
                    showErrorMessage(getResources().getString(R.string.avidly_string_user_unbind_send_success));
//                    Utils.showToastTip(getContext(), R.string.avidly_string_user_unbind_send_success, true);
                }

                @Override
                public void onFail(Throwable e, int code) {
                    hideLoadingUI();
                    String message;
                    if (code == AVIDLY_LOGIN_ERROR_GGID_NOT_BOUNDED_THIRD_SDK) {
                        message = getResources().getString(AvidlyAccountSdkErrors.getUnbindErrorMessage(code), ThirdSdkFactory.nameOfAccountMode(mode));
                    } else {
                        message = getResources().getString(AvidlyAccountSdkErrors.getUnbindErrorMessage(code));
                    }
                    showErrorMessage(message);
//                    Utils.showToastTip(getContext(), message, true);
                }
            });
            showLoadingUI();
        }

    }

    private void tryToBindThirdSdk(int mode) {
        switch (mode) {
            case Account.ACCOUNT_MODE_FACEBOOK:
                doThirdSdkBind(mode);
                break;
        }
    }

    private void doThirdSdkBind(final int mode) {
        LogUtils.i("doThirdSdkBind, mode: " + mode);
        if (!ThirdSdkFactory.isExistSdkLib(mode)) {
            thirdLoginSdkDelegate = null;
            LogUtils.i("doThirdSdkBind, the third login sdk is not exist. ");
            return;
        }

        if (thirdLoginSdkDelegate != null && !thirdLoginSdkDelegate.isThis(mode)) {
            thirdLoginSdkDelegate.exit();
            thirdLoginSdkDelegate = null;
        }

        if (thirdLoginSdkDelegate == null) {
            thirdLoginSdkDelegate = ThirdSdkFactory.newThirdSdkLoginDeleage(mode);
            if (thirdLoginSdkDelegate == null) {
                LogUtils.i("doThirdSdkBind, fail to create third sdk delegate object.");
                return;
            }

            thirdLoginSdkDelegate.bind(this, new ThirdSdkLoginCallback() {
                @Override
                public void onLoginSuccess(LoginUser loginUser) {
                    freshBindAdapterInMainThread();
                    hideLoadingUI();
                    showErrorMessage(getResources().getString(R.string.avidly_string_user_bind_send_success));
//                    Utils.showToastTip(getContext(), R.string.avidly_string_user_bind_send_success, true);
                    thirdLoginSdkDelegate = null;
                }

                @Override
                public void onLoginFailed(int code) {
                    thirdLoginSdkDelegate = null;
                    hideLoadingUI();
                    String message;
                    if (code == AVIDLY_LOGIN_ERROR_ACCESS_TOKEN_BOUNDED_OTHER_GGID) {
                        message = getResources().getString(AvidlyAccountSdkErrors.getLoginErrorMessge(code), ThirdSdkFactory.nameOfAccountMode(mode));
                    } else {
                        message = getResources().getString(AvidlyAccountSdkErrors.getLoginErrorMessge(code));
                    }
                    showErrorMessage(message);
//                    Utils.showToastTip(getContext(), message, true);
                }

                @Override
                public void onLoginStart() {
                    showLoadingUI();
                }
            });
        }
    }

    private void toBindAvidlyAccount() {
        Intent intent = new Intent(getActivity(), AccountLoginActivity.class);
        intent.setAction(Constants.INTENT_KEY_ACTION_BIND);
        getActivity().startActivity(intent);
    }

    private void toSwithAccount() {
        Intent intent = new Intent(getActivity(), AccountLoginActivity.class);
        intent.setAction(Constants.INTENT_KEY_ACTION_SWITCH);
        getActivity().startActivity(intent);
    }

    private void showChangePasswordUI() {
        if (LoginUserManager.getCurrentActiveLoginUser() == null
                || LoginUserManager.getCurrentActiveLoginUser().getLoginedMode() != Account.ACCOUNT_MODE_AVIDLY) {
            showErrorMessage(getResources().getString(R.string.avidly_string_user_alter_pwd_disable));
//            Utils.showToastTip(getContext(), R.string.avidly_string_user_alter_pwd_disable, true);
            return;
        }
        getView().findViewById(R.id.avidly_fragment_user_alter_pwd_root).setVisibility(View.VISIBLE);
        isShowChangePwdUI = true;
        TextView textView = getView().findViewById(R.id.avidly_user_common_title_textview);
        textView.setText(R.string.avidly_string_userpwd_alter_title);
        AccountUserPwdAlterFragment alterFragment = (AccountUserPwdAlterFragment) getChildFragmentManager().findFragmentById(R.id.avidly_fragment_user_alter_pwd_fragment);
        alterFragment.resetPwd();
    }

    private void hideChangePasswordUI() {
        getView().findViewById(R.id.avidly_fragment_user_alter_pwd_root).setVisibility(View.GONE);
        isShowChangePwdUI = false;
        TextView textView = getView().findViewById(R.id.avidly_user_common_title_textview);
        textView.setText(R.string.avidly_string_usermanger_title);
    }

    private void showBindOtherAccountUI() {
        getView().findViewById(R.id.avidly_fragment_user_account_bind_root).setVisibility(View.VISIBLE);
        isShowBindAccountUI = true;
        TextView textView = getView().findViewById(R.id.avidly_user_common_title_textview);
        textView.setText(R.string.avidly_string_usermanger_bind_other_title);
        AccountUserBindFragment bindFragment = (AccountUserBindFragment) getChildFragmentManager().findFragmentById(R.id.avidly_fragment_user_account_bind_fragment);
        bindFragment.freshAdapter();
    }

    private void hideBindOtherAccountUI() {
        getView().findViewById(R.id.avidly_fragment_user_account_bind_root).setVisibility(View.GONE);
        isShowBindAccountUI = false;
        TextView textView = getView().findViewById(R.id.avidly_user_common_title_textview);
        textView.setText(R.string.avidly_string_usermanger_title);

        AccountUserManagerFragment userManagerFragment = (AccountUserManagerFragment) getChildFragmentManager().findFragmentById(R.id.avidly_fragment_user_manager);
        userManagerFragment.refreshNickName();
    }

    private void showLoadingUI() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getView().findViewById(R.id.avidly_loading_layout).setVisibility(View.VISIBLE);
                isShowLoadingUI = true;
            }
        });
    }

    private void hideLoadingUI() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getView().findViewById(R.id.avidly_loading_layout).setVisibility(View.GONE);
                isShowLoadingUI = false;
            }
        });
    }

    private void freshBindAdapterInMainThread() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AccountUserBindFragment bindFragment = (AccountUserBindFragment) getChildFragmentManager().findFragmentById(R.id.avidly_fragment_user_account_bind_fragment);
                bindFragment.freshAdapter();
            }
        });
    }

    protected interface LoadingUICallback {
        void notifyShowLoadingUI(boolean show, boolean keep);
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
                mErrorLayout.setVisibility(View.GONE);
            }
        });
    }

    private void showErrorMessage(final String message) {
        ThreadHelper.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                mMessgeText.setText(message);
                mErrorLayout.setVisibility(View.VISIBLE);
            }
        });

        ThreadHelper.removeOnWorkThread(mHideErrorMessageRunnable);
        ThreadHelper.runOnWorkThread(mHideErrorMessageRunnable, Constants.AUTO_CLOSE_ERROR_LAYOUT_MILLS);
    }

}
