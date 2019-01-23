package com.avidly.sdk.account.listener;

/**
 * Created by t.wang on 2019/1/16.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public interface AccountLoginListener {
    void onBackToHomePressed();

    void onLoginErrorOccured(String message);

    void onAccountLoginClicked(String email, String password);

    void onAccountRegistClicked(String email, String password);

    void onAccountBindClicked(String email, String password);

    void onForgotPasswordClicked();

    void onReadProtocolClicked();

}
