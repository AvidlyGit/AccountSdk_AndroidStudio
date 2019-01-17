package com.avidly.sdk.account.callback;

/**
 * Created by t.wang on 2019/1/15.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public interface AccountHomeCallback {
    void onGuestLoginClicked();

    void onAvidlyLoginClicked();

    void onFacebookLoginClicked();

    void onTwitterLoginClicked();

    void onGoogleLoginClicked();
}
