package com.avidly.sdk.account.listener;

/**
 * Created by t.wang on 2019/1/16.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public interface AccountLoginListener {
    void onBackToHomePressed();

    void onAccountLogin();

    void onAccountRegist();

    void onForgotPasswordClicked();

    void onReadProtocolClicked();

}
