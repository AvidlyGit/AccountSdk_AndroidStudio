package com.avidly.sdk.account.listener;

public interface AccountLoginListener {
    void onBackToHomePressed();

    void onLoginErrorOccured(String message);

    void onAccountLoginClicked(String email, String password);

    void onAccountRegistClicked(String email, String password);

    void onAccountBindClicked(String email, String password);

    void onForgotPasswordClicked();

    void onReadProtocolClicked();

}
