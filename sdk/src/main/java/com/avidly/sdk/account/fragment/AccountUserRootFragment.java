package com.avidly.sdk.account.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avidly.sdk.account.activity.AccountLoginActivity;
import com.avidly.sdk.account.adapter.BaseAdapter;
import com.avidly.sdk.account.base.Constants;
import com.avidly.sdk.account.base.utils.LogUtils;
import com.avidly.sdk.account.data.adapter.UserOperationData;
import com.avidly.sdk.account.data.user.Account;
import com.avidly.sdk.account.data.user.LoginUser;
import com.avidly.sdk.account.data.user.LoginUserManager;
import com.avidly.sdk.account.third.ThirdLoginSdkDelegate;
import com.avidly.sdk.account.third.ThirdSdkFactory;
import com.avidly.sdk.account.third.ThirdSdkLoginCallback;
import com.sdk.avidly.account.R;

public class AccountUserRootFragment extends BaseFragment {

    private boolean isShowChangePwdUI;
    private boolean isShowBindAccountUI;
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

        view.findViewById(R.id.avidly_user_manager_title_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!onBackKeyDown()) {
                    getActivity().finish();
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
            if (user == null || user.getLoginedMode() != mode) {
                LogUtils.w("mismatch right loginuser object, check value: " + data);
                return;
            }

            Account account = user.findAccountByMode(mode);
            boolean unbind = account == null || account.isBinded;
            if (unbind) {
                tryToBindThirdSdk(mode);
            }
        }
    };

    private void tryToBindThirdSdk(int mode) {
        switch (mode) {
            case  Account.ACCOUNT_MODE_FACEBOOK:
                doThirdSdkBind(mode);
                break;
        }
    }

    private void doThirdSdkBind(int mode) {
        LogUtils.i("doThirdSdkBind, mode: " + mode);
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
            if (!thirdLoginSdkDelegate.isExistSdkLib()) {
                thirdLoginSdkDelegate = null;
                LogUtils.i("doThirdSdkBind, the third login sdk is not exist. ");
                return;
            }
            thirdLoginSdkDelegate.login(getActivity(), new ThirdSdkLoginCallback() {
                @Override
                public void onLoginSuccess() {
                    AccountUserBindFragment bindFragment = (AccountUserBindFragment) getChildFragmentManager().findFragmentById(R.id.avidly_fragment_user_account_bind_fragment);
                    bindFragment.freshAdapter();
                }

                @Override
                public void onLoginFailed() {
                    thirdLoginSdkDelegate = null;
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
        getView().findViewById(R.id.avidly_fragment_user_alter_pwd_root).setVisibility(View.VISIBLE);
        isShowChangePwdUI = true;
        TextView textView = getView().findViewById(R.id.avidly_user_common_title_textview);
        textView.setText(R.string.avidly_string_userpwd_alter_title);
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
    }

    private void hideBindOtherAccountUI() {
        getView().findViewById(R.id.avidly_fragment_user_account_bind_root).setVisibility(View.GONE);
        isShowBindAccountUI = false;
        TextView textView = getView().findViewById(R.id.avidly_user_common_title_textview);
        textView.setText(R.string.avidly_string_usermanger_title);
        AccountUserBindFragment bindFragment = (AccountUserBindFragment) getChildFragmentManager().findFragmentById(R.id.avidly_fragment_user_account_bind_fragment);
        bindFragment.freshAdapter();
    }
}
