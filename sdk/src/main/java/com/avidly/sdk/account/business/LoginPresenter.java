package com.avidly.sdk.account.business;

import com.avidly.sdk.account.data.user.LoginUser;

public interface LoginPresenter {
    void guestLogin(LoginUser user);

    void accountLogin(String email, String password);

    void accountRegistOrBind(String gameGuestId, String email, String password);

}
