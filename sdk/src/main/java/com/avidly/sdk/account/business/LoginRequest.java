package com.avidly.sdk.account.business;

import com.avidly.sdk.account.base.utils.LogUtils;
import com.avidly.sdk.account.data.user.Account;
import com.avidly.sdk.account.data.user.LoginUser;
import com.avidly.sdk.account.data.user.LoginUserManager;
import com.avidly.sdk.account.request.HttpCallback;
import com.avidly.sdk.account.request.HttpRequest;
import com.avidly.sdk.account.request.URLConstant;

import org.json.JSONObject;

import static com.avidly.sdk.account.Errors.AVIDLY_LOGIN_ERROR_RESPONSE_JSON_EXCEPTION;
import static com.avidly.sdk.account.Errors.AVIDLY_LOGIN_ERROR_RESPONSE_MISMATCH_PRODUCT_ID;

/**
 * Created by t.wang on 2019/1/22.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public class LoginRequest {


    private static JSONObject requestToDataJsonObject(String result, LoginRequestCallback<String> callback) {
        try {
            JSONObject o = new JSONObject(result);
            int code = o.optInt("statusCode");
            if (200 == code) {
                JSONObject data = o.getJSONObject("data");
                JSONObject guest = data.getJSONObject("gameGuest");
                String productId = guest.getString("productId");
                if (productId.equals(LoginCenter.getProductId())) {
                    return guest;
                } else {
                    callback.onFail(AVIDLY_LOGIN_ERROR_RESPONSE_MISMATCH_PRODUCT_ID, "mismatch product id");
                }
            } else {
                callback.onFail(code, "" + o.optString("message"));
            }
        } catch (Exception e) {
            callback.onFail(AVIDLY_LOGIN_ERROR_RESPONSE_JSON_EXCEPTION, "" + e);
        }
        return null;
    }

    private static void bindOther(JSONObject guest, LoginUser user) {
        if (guest != null && user != null) {
            try {
                user.bindAccount(Account.ACCOUNT_MODE_AVIDLY, guest.getBoolean("bindAvidly"));
                user.bindAccount(Account.ACCOUNT_MODE_FACEBOOK, guest.getBoolean("bindFb"));
                user.bindAccount(Account.ACCOUNT_MODE_GOOGLEPLAY, guest.getBoolean("bindGoogle"));
                user.bindAccount(Account.ACCOUNT_MODE_TWITTER, guest.getBoolean("bindTwitter"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void guestLogin(final LoginRequestCallback<String> callback) {
        String url = URLConstant.getGuestLoginApi();
        LogUtils.i("HttpBusiness guestLogin url is " + url);
        HttpRequest.requestHttpByPost(url, null, new HttpCallback<String>() {
            @Override
            public void onResponseSuccess(String result) {
                LogUtils.i("HttpBusiness guestLogin result is " + result);
                try {
                    JSONObject guest = requestToDataJsonObject(result, callback);
                    if (guest != null) {
                        String gameGuestId = guest.getString("gameGuestId");
                        callback.onSuccess(gameGuestId);
                        LoginUser user = LoginUserManager.getAccountLoginUser();
                        if (user != null) {
                            bindOther(guest, user);
                        }
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onResponedFail(Throwable e, int code) {

                callback.onFail(code, "" + e);
            }
        });
    }

    public static void accountLogin(final String userName, final String password,
                                    final LoginRequestCallback<String> callback) {
        String url = URLConstant.getAccountLoginApi(userName, password);
        LogUtils.i("HttpBusiness accountLogin url is " + url);
        HttpRequest.requestHttpByPost(url, null, new HttpCallback<String>() {
            @Override
            public void onResponseSuccess(String result) {
                LogUtils.i("HttpBusiness accountLogin result is " + result);
                try {
                    JSONObject guest = requestToDataJsonObject(result, callback);
                    if (guest != null) {
                        String gameGuestId = guest.getString("gameGuestId");
                        callback.onSuccess(gameGuestId);
                        LoginUser user = LoginUserManager.getAccountLoginUser();
                        if (user != null) {
                            bindOther(guest, user);
                            LoginUserManager.saveAccountUsers();
                        }
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onResponedFail(Throwable e, int code) {

                callback.onFail(code, "" + e);
            }
        });
    }

    public static void accountRegistOrBind(final String gameGuestId, final String userName, final String password,
                                           final LoginRequestCallback<String> callback) {
        String url = URLConstant.getAccountRegistOrBindApi(gameGuestId, userName, password);
        LogUtils.i("HttpBusiness accountRegistOrBind url is " + url);
        HttpRequest.requestHttpByPost(url, null, new HttpCallback<String>() {
            @Override
            public void onResponseSuccess(String result) {
                LogUtils.i("HttpBusiness accountRegistOrBind result is " + result);
                try {
                    JSONObject guest = requestToDataJsonObject(result, callback);
                    if (guest != null) {
                        String gameGuestId = guest.getString("gameGuestId");
                        callback.onSuccess(gameGuestId);
                        bindOther(guest, LoginUserManager.getAccountLoginUser());
                        LoginUserManager.saveAccountUsers();
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onResponedFail(Throwable e, int code) {

                callback.onFail(code, "" + e);
            }
        });
    }

    public static void thirdFacebookLogin(String type, String jsondata, final LoginRequestCallback<String> callback) {
        String url = URLConstant.getThirdFacebookLoginUrl(type, jsondata);
        LogUtils.i("HttpBusiness thirdFacebookLogin url is " + url);
        HttpRequest.requestHttpByPost(url, null, new HttpCallback<String>() {
            @Override
            public void onResponseSuccess(String result) {
                LogUtils.i("HttpBusiness thirdFacebookLogin result is " + result);
                try {
                    JSONObject guest = requestToDataJsonObject(result, callback);
                    if (guest != null) {
                        String gameGuestId = guest.getString("gameGuestId");
                        callback.onSuccess(gameGuestId);
                        bindOther(guest, LoginUserManager.getAccountLoginUser());
                        LoginUserManager.saveAccountUsers();
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onResponedFail(Throwable e, int code) {

                callback.onFail(code, "" + e);
            }
        });
    }


}
