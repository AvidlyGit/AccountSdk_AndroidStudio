package com.avidly.sdk.account.callback;

/**
 * Created by t.wang on 2019/1/15.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public interface AccountLoginCallback {
    void onGuestLoginClicked();

    void onAvidlyLoginClicked();

    void onFacebookeLoginClicked();

    void onTwitterLoginClicked();

    void onGoogleLoginClicked();
}
