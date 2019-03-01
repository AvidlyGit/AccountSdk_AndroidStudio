package com.avidly.sdk.account.activity;

import com.avidly.sdk.account.data.user.LoginUser;

public interface AccountLoginInterface {
    void onUserLoginSuccessed(LoginUser loginUser);

    void onUserLoginFailed(int code);
}
