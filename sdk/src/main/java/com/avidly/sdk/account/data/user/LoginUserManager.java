package com.avidly.sdk.account.data.user;

import android.content.Context;

import com.avidly.sdk.account.business.LoginCenter;

public class LoginUserManager {

    private static LoginUserCache cache = new LoginUserCache();

    public static void freshUserCache(Context context) {
        cache.freshCache(context);
    }

    /**
     * 获取当前登陆后有效的ggid
     *
     * @return 返回null，则从未登陆
     */
    public static String getCurrentGGID() {

        LoginUser user = getCurrentActiveLoginUser();

        if (user != null) {
            return user.ggid;
        }

        return null;
    }

    /**
     * 检查当前是否已经登陆
     *
     * @return
     */
    public static boolean isLoginedNow() {
        LoginUser user = getCurrentActiveLoginUser();
        if (user != null && user.isNowLogined) {
            return true;
        }

        return false;
    }

    /**
     * 获取上次登陆成功的用户对象
     *
     * @return
     */
    public static LoginUser getCurrentActiveLoginUser() {
        if (cache.accountUser != null && cache.accountUser.isActived) {
            return cache.accountUser;
        }

        if (cache.guestUser != null && cache.guestUser.isActived) {
            return cache.guestUser;
        }

        return null;
    }

    /**
     * 游客登陆成功后调用此方法，记得调用saveAccountUsers()保存用户数据
     *
     * @param ggid
     */
    public static LoginUser onGuestLoginSuccess(String ggid) {
        // 创建游客帐号用户
        getOrCreateGuestLoginUser();
        // 游客帐号登入
        loginGuestUser(ggid);

        // 帐号用户登出
        logoutAccountUser();

        return cache.guestUser;
    }

    /**
     * 帐号用户登陆成功后调用此方法，记得调用saveAccountUsers()保存用户数据
     *
     * @param mod
     * @param ggid
     * @return 返回LoginUser，用于更新更多其它的数据
     */
    public static LoginUser onAccountLoginSuccess(int mod, String ggid) {

        // 创建帐号用户
        if (cache.accountUser == null) {
            cache.accountUser = getOrCreateGuestLoginUser();
            cache.guestUser = null;
        }

        // 游客帐号登出
        logoutGuestUser();
        // 帐号用户登入
        loginAccountUser(mod, ggid);

        return cache.accountUser;
    }

    /**
     * 保存帐号数据
     */
    public static void saveAccountUsers() {
        cache.commit(LoginCenter.getContext());
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

    private static void logoutGuestUser() {
        if (cache.guestUser != null) {
            cache.guestUser.isNowLogined = false;
            cache.guestUser.isActived = false;
        }
    }

    private static void logoutAccountUser() {
        if (cache.accountUser != null) {
            cache.accountUser.isNowLogined = false;
            cache.accountUser.isActived = false;
        }
    }

    private static void loginGuestUser(String ggid) {
        if (cache.guestUser != null) {
            cache.guestUser.isActived = true;
            cache.guestUser.isNowLogined = true;
            cache.guestUser.ggid = ggid;
            cache.guestUser.setLoginedMode(Account.ACCOUNT_MODE_GUEST);
        }
    }

    private static void loginAccountUser(int mode, String ggid) {
        if (cache.accountUser != null) {
            cache.accountUser.isNowLogined = true;
            cache.accountUser.isActived = true;
            cache.accountUser.setLoginedMode(mode);
            cache.accountUser.ggid = ggid;
        }
    }

}
