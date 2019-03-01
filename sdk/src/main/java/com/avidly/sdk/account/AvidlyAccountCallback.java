package com.avidly.sdk.account;



public interface AvidlyAccountCallback {
    void onGameGuestIdLoginSuccess(String ggid);

    void onGameGuestIdLoginFailed(int code, String msg);


}
