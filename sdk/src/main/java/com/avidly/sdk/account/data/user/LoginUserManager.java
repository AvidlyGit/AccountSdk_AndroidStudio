package com.avidly.sdk.account.data.user;

import android.content.Context;

public class LoginUserManager {

    private static LoginUserCache cache = new LoginUserCache();

    public static void freshUserCache(Context context) {
        cache.freshCache(context);
    }

    public static String getCurrentGGID() {
        return null;
    }

    public static LoginUser getGuestLoginUser() {
        return cache.guestUser;
    }

    public static LoginUser getOrCreateGuestLoginUser() {
        if (cache.guestUser == null) {
            cache.guestUser = new LoginUser();
            cache.guestUser.setLoginedMode(Account.ACCOUNT_MODE_GUEST);
        }
        return cache.guestUser;
    }

    public static LoginUser getAccountLoginUser() {
        return cache.accountUser;
    }

}
