package com.avidly.sdk.account.third;

import com.avidly.sdk.account.data.user.LoginUser;

public interface ThirdSdkLoginCallback {
    void onLoginSuccess(LoginUser loginUser);

    void onLoginFailed(int code);

    void onLoginStart();
}
