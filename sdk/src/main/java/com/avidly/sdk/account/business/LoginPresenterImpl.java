package com.avidly.sdk.account.business;

import com.avidly.sdk.account.activity.AccountLoginView;

/**
 * Created by t.wang on 2019/1/17.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public class LoginPresenterImpl implements LoginPresenter {

    private AccountLoginView mView;

    public LoginPresenterImpl(AccountLoginView view) {
        mView = view;
    }

    @Override
    public void accountLogin() {
        if (false) {
            mView.onUserLoginSuccessed();
        } else {
            mView.onUserLoginFailed("错误");
        }
    }
}
