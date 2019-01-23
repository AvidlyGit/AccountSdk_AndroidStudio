package com.avidly.sdk.account.activity;

import com.avidly.sdk.account.data.user.LoginUser;

/**
 * Created by t.wang on 2019/1/17.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public interface AccountLoginInterface {
    void onUserLoginSuccessed(LoginUser loginUser);

    void onUserLoginFailed(int code);
}
