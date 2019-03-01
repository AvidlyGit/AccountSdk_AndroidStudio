package com.avidly.sdk.account.data.user;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.avidly.sdk.account.base.Constants;
import com.avidly.sdk.account.base.utils.SpHelper;

public class LoginUserCache {
    //List<LoginUser> users = new ArrayList<>(2);

    // 可以有多个帐号用户
    public LoginUser accountUser; // 帐号用户(通过avidly或第三方帐号登陆的用户)

    // 游客用户只有一个
    public LoginUser guestUser; // 游客用户，通过帐号登陆后，会转成帐号用户

    private SpHelper spHelper;

    private final static int VERSION = 1;// 升级控制

    public void freshCache(Context context) {
        if (null == context && spHelper == null) {
            throw new IllegalArgumentException("Context is null, can't get any data from cache.");
        }

        initSpShare(context);

        if (guestUser == null) {
            String guest = spHelper.getString("guest_user");
            if (!TextUtils.isEmpty(guest)) {
                LoginUser user = new LoginUser();
                user.thisFromString(guest);
                guestUser = user;
            }
        }

        if (accountUser == null) {
            String account = spHelper.getString("account_user");
            if (!TextUtils.isEmpty(account)) {
                LoginUser user = new LoginUser();
                user.thisFromString(account);
                accountUser = user;
            }
        }

    }

    public void commit(Context context) {
        if (null == context && spHelper == null) {
            throw new IllegalArgumentException("Context is null, can't save logined user data");
        }

        initSpShare(context);

        if (guestUser != null) {
            String json = guestUser.thisToString();
            Log.i("xxxx", "save guest user:" + json);
            spHelper.putString("guest_user", json);
        } else {
            spHelper.putString("guest_user", "");
        }

        if (accountUser != null) {
            String json = accountUser.thisToString();
            Log.i("xxxx", "save account user:" + json);
            spHelper.putString("account_user", json);
        } else {
            spHelper.putString("account_user", "");
        }
    }

    private void initSpShare(Context context) {
        if (null == spHelper) {
                spHelper = new SpHelper(context, Constants.SP_NAME);
        }
    }

}
