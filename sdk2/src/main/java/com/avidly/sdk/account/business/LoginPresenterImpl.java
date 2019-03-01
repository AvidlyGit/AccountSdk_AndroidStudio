package com.avidly.sdk.account.business;

import android.text.TextUtils;

import com.avidly.sdk.account.activity.AccountLoginInterface;
import com.avidly.sdk.account.base.utils.DeviceInfoHelper;
import com.avidly.sdk.account.base.utils.LogUtils;
import com.avidly.sdk.account.data.user.Account;
import com.avidly.sdk.account.data.user.LoginUser;
import com.avidly.sdk.account.data.user.LoginUserManager;

public class LoginPresenterImpl implements LoginPresenter {

    private AccountLoginInterface mView;

    public LoginPresenterImpl(AccountLoginInterface view) {
        mView = view;
    }

    @Override
    public void guestLogin(final LoginUser user) {
        if (user != null && !TextUtils.isEmpty(user.ggid)) {
            LoginRequest.guestLogin(user.ggid,new LoginRequestCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    LoginUser loginUser = LoginUserManager.onGuestLoginSuccess(user.ggid);
                    LoginUserManager.saveAccountUsers();

                    mView.onUserLoginSuccessed(loginUser);
                }

                @Override
                public void onFail(Throwable e, int code) {
                    LogUtils.w("guestLogin has error occured with code " + code, e);
                    mView.onUserLoginFailed(code);
                }
            });
        } else {
            LoginRequest.guestRegist(new LoginRequestCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    LoginUser loginUser = LoginUserManager.onGuestLoginSuccess(result);
                    LoginUserManager.saveAccountUsers();

                    mView.onUserLoginSuccessed(loginUser);
                }

                @Override
                public void onFail(Throwable e, int code) {
                    LogUtils.w("guestRegist has error occured with code " + code, e);
                    mView.onUserLoginFailed(code);
                }
            });
        }
    }

    @Override
    public void accountLogin(final String email, final String password) {
        // 账号密码登录
        LoginRequest.accountLogin(email, password, new LoginRequestCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LoginUser loginUser = LoginUserManager.onAccountLoginSuccess(Account.ACCOUNT_MODE_AVIDLY, result);
                Account account = loginUser.findAccountByMode(Account.ACCOUNT_MODE_AVIDLY);
                account.accountName = email;
                account.nickname = email;
                account.accountPwd = password;
                // 回调之后会保存
                //LoginUserManager.saveAccountUsers();

                mView.onUserLoginSuccessed(loginUser);
            }

            @Override
            public void onFail(Throwable e, int code) {
                LogUtils.w("accountLogin has error occured with code " + code, e);
                mView.onUserLoginFailed(code);
            }

        });
    }

    @Override
    public void accountRegistOrBind(final String ggid, final String email, final String password) {
        // ggid为空，服务器注册用户后，生成新的ggid绑定返回
        // ggid不为空，服务器注册用户后，同现有的ggid绑定返回
        LoginRequest.accountRegistOrBind(ggid == null ? "" : ggid, email, password, new LoginRequestCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LoginUser loginUser = LoginUserManager.onAccountLoginSuccess(Account.ACCOUNT_MODE_AVIDLY, result);
                Account account = loginUser.findAccountByMode(Account.ACCOUNT_MODE_AVIDLY);
                account.accountName = email;
                account.nickname = email;
                account.accountPwd = password;
                // 回调之后会保存
                //LoginUserManager.saveAccountUsers();

                mView.onUserLoginSuccessed(loginUser);
            }

            @Override
            public void onFail(Throwable e, int code) {
                LogUtils.w("accountRegistOrBind has error occured with code " + code, e);
                mView.onUserLoginFailed(code);
            }
        });
    }
}
