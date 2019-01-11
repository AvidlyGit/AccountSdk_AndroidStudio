package com.avidly.sdk.account;

public interface AvidlyAccountLoginCallback {
    void onLoginSuccess(String ggid);

    void onLoginFail(int code, String msg);
}
