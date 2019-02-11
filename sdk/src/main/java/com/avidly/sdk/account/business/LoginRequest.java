package com.avidly.sdk.account.business;

import com.avidly.sdk.account.base.utils.LogUtils;
import com.avidly.sdk.account.data.user.Account;
import com.avidly.sdk.account.data.user.LoginUser;
import com.avidly.sdk.account.data.user.LoginUserManager;
import com.avidly.sdk.account.request.HttpCallback;
import com.avidly.sdk.account.request.HttpRequest;
import com.avidly.sdk.account.request.URLConstant;

import org.json.JSONObject;

import static com.avidly.sdk.account.AvidlyAccountSdkErrors.AVIDLY_LOGIN_ERROR_RESPONSE_JSON_EXCEPTION;
import static com.avidly.sdk.account.AvidlyAccountSdkErrors.AVIDLY_LOGIN_ERROR_RESPONSE_MISMATCH_PRODUCT_ID;

/**
 * Created by t.wang on 2019/1/22.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public class LoginRequest {
    private static JSONObject requestToDataJsonObject(String result, LoginRequestCallback<String> callback) {
        try {
            JSONObject o = new JSONObject(result);
            boolean success = o.optBoolean("success");
            int code = o.optInt("code");
            if (success) {
                JSONObject data = o.getJSONObject("data");
                JSONObject guest = data.getJSONObject("gameGuest");
                String productId = guest.getString("pdtid");
                if (productId.equals(LoginCenter.getProductId())) {
                    return guest;
                } else {
                    callback.onFail(new Throwable("mismatch product id"), AVIDLY_LOGIN_ERROR_RESPONSE_MISMATCH_PRODUCT_ID);
                }
            } else {
                callback.onFail(new Throwable(o.optString("message")), code);
            }
        } catch (Throwable e) {
            callback.onFail(e, AVIDLY_LOGIN_ERROR_RESPONSE_JSON_EXCEPTION);
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
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public static void guestLogin(String gameGuestId, final LoginRequestCallback<String> callback) {
        String url = URLConstant.getGuestLoginApi(gameGuestId);
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
                } catch (Throwable e) {
                    callback.onFail(e, AVIDLY_LOGIN_ERROR_RESPONSE_JSON_EXCEPTION);
                }
            }

            @Override
            public void onResponedFail(Throwable e, int code) {
                callback.onFail(e, code);
            }
        });
    }

    public static void guestRegist(final LoginRequestCallback<String> callback) {
        String url = URLConstant.getGuestRegistApi();
        LogUtils.i("HttpBusiness guestRegist url is " + url);
        HttpRequest.requestHttpByPost(url, null, new HttpCallback<String>() {
            @Override
            public void onResponseSuccess(String result) {
                LogUtils.i("HttpBusiness guestRegist result is " + result);
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
                } catch (Throwable e) {
                    callback.onFail(e, AVIDLY_LOGIN_ERROR_RESPONSE_JSON_EXCEPTION);
                }
            }

            @Override
            public void onResponedFail(Throwable e, int code) {
                callback.onFail(e, code);
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
                } catch (Throwable e) {
                    callback.onFail(e, AVIDLY_LOGIN_ERROR_RESPONSE_JSON_EXCEPTION);
                }
            }

            @Override
            public void onResponedFail(Throwable e, int code) {
                callback.onFail(e, code);
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
                } catch (Throwable e) {
                    callback.onFail(e, AVIDLY_LOGIN_ERROR_RESPONSE_JSON_EXCEPTION);
                }
            }

            @Override
            public void onResponedFail(Throwable e, int code) {
                callback.onFail(e, code);
            }
        });
    }

    public static void facebookSdkBind(String ggid, String accessToken, String appid, final LoginRequestCallback<String> callback) {
        String url = URLConstant.getFacebookBindUrl(ggid, accessToken, appid);
        LogUtils.i("HttpBusiness facebookSdkBind url is " + url);
        HttpRequest.requestHttpByPost(url, null, new HttpCallback<String>() {
            @Override
            public void onResponseSuccess(String result) {
                LogUtils.i("HttpBusiness facebookSdkBind result is " + result);
                try {
                    JSONObject guest = requestToDataJsonObject(result, callback);
                    if (guest != null) {
                        String gameGuestId = guest.getString("gameGuestId");
                        callback.onSuccess(gameGuestId);

                        // TODO: 2019/1/31 此处处理一下facebook的nickname
                        bindOther(guest, LoginUserManager.getAccountLoginUser());
                        LoginUserManager.saveAccountUsers();
                    }
                } catch (Throwable e) {
                    callback.onFail(e, AVIDLY_LOGIN_ERROR_RESPONSE_JSON_EXCEPTION);
                }
            }

            @Override
            public void onResponedFail(Throwable e, int code) {
                callback.onFail(e, code);
            }
        });
    }

    public static void facebookSdkUnBind(String ggid, String accessToken, final LoginRequestCallback<String> callback) {
        String url = URLConstant.getFacebookUnBindUrl(ggid, accessToken);
        LogUtils.i("HttpBusiness facebookSdkUnBind url is " + url);
        HttpRequest.requestHttpByPost(url, null, new HttpCallback<String>() {
            @Override
            public void onResponseSuccess(String result) {
                LogUtils.i("HttpBusiness facebookSdkUnBind result is " + result);
                try {
                    JSONObject guest = new JSONObject(result);
                    if (guest.optBoolean("success")) {
                        //String gameGuestId = guest.getString("gameGuestId");
                        callback.onSuccess("");
                        //bindOther(guest, LoginUserManager.getAccountLoginUser());
                        LoginUserManager.saveAccountUsers();
                    } else {
                        callback.onFail(null, guest.optInt("code"));
                    }
                } catch (Throwable e) {
                    callback.onFail(e, AVIDLY_LOGIN_ERROR_RESPONSE_JSON_EXCEPTION);
                }
            }

            @Override
            public void onResponedFail(Throwable e, int code) {
                callback.onFail(e, code);
            }
        });
    }

}
