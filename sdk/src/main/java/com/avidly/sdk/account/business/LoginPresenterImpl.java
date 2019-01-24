package com.avidly.sdk.account.business;

import android.text.TextUtils;

import com.avidly.sdk.account.activity.AccountLoginInterface;
import com.avidly.sdk.account.data.user.Account;
import com.avidly.sdk.account.data.user.LoginUser;
import com.avidly.sdk.account.data.user.LoginUserManager;

/**
 * Created by t.wang on 2019/1/17.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public class LoginPresenterImpl implements LoginPresenter {

    private AccountLoginInterface mView;

    public LoginPresenterImpl(AccountLoginInterface view) {
        mView = view;
    }

    @Override
    public void guestLogin(LoginUser user) {
        if (user != null && !TextUtils.isEmpty(user.ggid)) {
            // 现有游客ggid登录
            LoginUser loginUser = LoginUserManager.onGuestLoginSuccess(user.ggid);
            LoginUserManager.saveAccountUsers();

            mView.onUserLoginSuccessed(loginUser);
        } else {
            //  服务器请求账号登录
            LoginRequest.guestLogin(new LoginRequestCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    LoginUser loginUser = LoginUserManager.onGuestLoginSuccess(result);
                    LoginUserManager.saveAccountUsers();

                    mView.onUserLoginSuccessed(loginUser);
                }

                @Override
                public void onFail(int code, String message) {
                    mView.onUserLoginFailed(100);
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
            public void onFail(int code, String message) {

                mView.onUserLoginFailed(101);
            }
        });
    }

    @Override
    public void facebookLogin(LoginUser user) {
        // facebook登录
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
            public void onFail(int code, String message) {
                mView.onUserLoginFailed(102);
            }
        });
    }
}
