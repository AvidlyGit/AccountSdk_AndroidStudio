package com.avidly.sdk.account.listener;

public interface AccountLoadingListener {
    void onSwitchAccountClicked();

    void onAutoLoginWaitingTimeOut();
}
