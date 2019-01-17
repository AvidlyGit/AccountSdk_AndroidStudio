package com.avidly.sdk.account.callback;

/**
 * Created by t.wang on 2019/1/15.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public interface AccountLoadingCallback {
    void onSwitchAccountClicked();

    void onUserLoginSuccessed();

    void onUserLoginFailed();
}
