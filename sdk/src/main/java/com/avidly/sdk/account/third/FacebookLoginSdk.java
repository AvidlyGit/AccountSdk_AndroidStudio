package com.avidly.sdk.account.third;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.avidly.sdk.account.base.utils.LogUtils;
import com.avidly.sdk.account.business.LoginRequest;
import com.avidly.sdk.account.business.LoginRequestCallback;
import com.avidly.sdk.account.data.user.Account;
import com.avidly.sdk.account.data.user.LoginUser;
import com.avidly.sdk.account.data.user.LoginUserManager;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;
import java.util.Collection;

import static com.avidly.sdk.account.AvidlyAccountSdkErrors.AVIDLY_LOGIN_ERROR_FACEBOOK_LOGIN_CANCEL;
import static com.avidly.sdk.account.AvidlyAccountSdkErrors.AVIDLY_LOGIN_ERROR_FACEBOOK_LOGIN_ERROR;

public class FacebookLoginSdk implements ThirdLoginSdkDelegate {
    ThirdSdkLoginCallback callback;

    CallbackManager callbackManager;

    boolean exitnow;

    public static String getToken() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn) {
            return accessToken.getToken();
        }
        return null;
    }

    @Override
    public void exit() {
        exitnow = true;
        callback = null;
        callbackManager = null;
    }

    @Override
    public boolean isThis(int type) {
        return type == Account.ACCOUNT_MODE_FACEBOOK;
    }

    @Override
    public void login(Object activity, final ThirdSdkLoginCallback callback) {
        sdkLogin(activity, false, callback);
    }

    @Override
    public void bind(Object activity, final ThirdSdkLoginCallback callback) {
        sdkLogin(activity, true, callback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void sdkLogin(Object activity, final boolean toBind, final ThirdSdkLoginCallback callback) {
        LoginUser user = LoginUserManager.getCurrentActiveLoginUser();
        if (user != null && user.getLoginedMode() == Account.ACCOUNT_MODE_FACEBOOK && user.isNowLogined) {
            // 已经facebook绑定登陆
            if (callback != null) {
                LogUtils.i("facebook is logined now.");
                callback.onLoginSuccess(user);
            }
            return;
        }

        this.callback = callback;

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn) {
            LogUtils.i("facebook is valid, so success soon.");
            onLoginFinish(toBind, accessToken);
            return;
        }
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        if (exitnow) {
                            return;
                        }
                        LogUtils.i("facebook login successfully.");
                        // App code
                        AccessToken accessToken = loginResult.getAccessToken();
                        onLoginFinish(toBind, accessToken);
                        callbackManager = null;
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        LogUtils.i("facebook login cancel.");
                        if (callback != null) {
                            callback.onLoginFailed(AVIDLY_LOGIN_ERROR_FACEBOOK_LOGIN_CANCEL);
                        }
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        LogUtils.i("facebook login exception:" + exception);
                        if (callback != null) {
                            callback.onLoginFailed(AVIDLY_LOGIN_ERROR_FACEBOOK_LOGIN_ERROR);
                        }
                    }
                });

        Collection<String> permissions = Arrays.asList("public_profile");
        if (activity instanceof Activity) {
            LoginManager.getInstance().logInWithReadPermissions((Activity) activity, permissions);
        } else if (activity instanceof android.app.Fragment) {
            LoginManager.getInstance().logInWithReadPermissions((android.app.Fragment) activity, permissions);
        } else if (activity instanceof Fragment) {
            LoginManager.getInstance().logInWithReadPermissions((Fragment) activity, permissions);
        } else {
            LogUtils.i("facebook login failed, the object is invalid, " + activity);
        }
    }

    private void onLoginFinish(final boolean toBind, AccessToken accessToken) {
        if (callback != null) {
            callback.onLoginStart();
        }

        String token = accessToken.getToken();
        String userid = accessToken.getUserId();
        LogUtils.i("facebook token:" + token);
        LogUtils.i("facebook userid:" + userid);

        String ggid = "";
        if (toBind) {
            ggid = LoginUserManager.getCurrentGGID();
        }

        LoginRequest.facebookSdkBind(ggid == null ? "" : ggid, token, accessToken.getApplicationId(), new LoginRequestCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LoginUser loginUser = null;
                if (toBind) {
/*                    loginUser = LoginUserManager.getCurrentActiveLoginUser();
                    if (loginUser.findAccountByMode(Account.ACCOUNT_MODE_FACEBOOK) == null) {
                        loginUser.bindAccount(Account.ACCOUNT_MODE_FACEBOOK, true);
                    } else {
                        loginUser.findAccountByMode(Account.ACCOUNT_MODE_FACEBOOK).isBinded = true;
                    }*/
                    // 绑定时，有可能是游客登录后绑定，也可能是avidly帐号登陆后绑定
                    loginUser = LoginUserManager.onAccountLoginSuccess(Account.ACCOUNT_MODE_FACEBOOK, result);
                } else {
                    loginUser = LoginUserManager.onAccountLoginSuccess(Account.ACCOUNT_MODE_FACEBOOK, result);
                }
                
                if (callback != null) {
                    callback.onLoginSuccess(loginUser);
                }
            }

            @Override
            public void onFail(Throwable e, int code) {
                if (callback != null) {
                    callback.onLoginFailed(code);
                }
            }
        });
    }
}
