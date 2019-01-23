package com.avidly.sdk.account.business;

import com.avidly.sdk.account.data.user.LoginUser;

/**
 * Created by t.wang on 2019/1/17.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public interface LoginPresenter {

    void guestLogin(LoginUser user);

    void accountLogin(String email, String password);

    void facebookLogin(LoginUser user);

    void accountRegistOrBind(String gameGuestId, String email, String password);

}
