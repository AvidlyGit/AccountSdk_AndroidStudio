package com.avidly.sdk.account.business;

import com.avidly.sdk.account.base.Constants;
import com.avidly.sdk.account.base.utils.LogUtils;
import com.avidly.sdk.account.request.HttpCallback;
import com.avidly.sdk.account.request.HttpRequest;
import com.avidly.sdk.account.request.URLConstant;

/**
 * Created by t.wang on 2019/1/22.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public class LoginRequest {
    public static void guestLogin(final LoginRequestCallback<String> callback) {
        String url = URLConstant.getGuestLoginApi();
        LogUtils.i("HttpBusiness guestLogin url is " + url);
        HttpRequest.requestHttpByPost(url, null, new HttpCallback<String>() {
            @Override
            public void onResponseSuccess(String result) {
                LogUtils.i("HttpBusiness guestLogin result is " + result);
//                {
//                    "gameGuest":{
//                        "gameGusetId":2135545885455,
//                        "pid":610322,
//                        "platform":"ios"
//                    }
//                }
                String gameGusetId = "2135545885455";
                String pid = "610322";
                String platform = "android";
                if (pid.equals(LoginCenter.getProductId()) && platform.equals(Constants.PLATFORM_ANDROID)) {
                    callback.onSuccess(gameGusetId);
                } else {
                    callback.onFail(00, "");
                }
            }

            @Override
            public void onResponedFail(Throwable e, int code) {

                callback.onFail(00, "");
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
//                {
//                    "success":true,
//                    "statusCode":200,
//                    "message":"SUCCESS",
//                    "gameGuest":{
//                        "gameGuestId":24535545885455,
//                        "productId":610322,
//                        "bindGoogle":false,
//                        "bindFb":"false",
//                        "bindAvidly":"true",
//                        "bindTwitter":"false",
//                        "bindInstagram":"false",
//                    }
//                }
                String gameGusetId = "24535545885455";
                String pid = "610322";
                if (pid.equals(LoginCenter.getProductId())) {
                    callback.onSuccess(gameGusetId);
                } else {
                    callback.onFail(00, "");
                }
            }

            @Override
            public void onResponedFail(Throwable e, int code) {

                callback.onFail(00, "");
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
//                {
//                    "avidlyUser":{
//                        "avidlyUserId":24535545885455,
//                        "gameGuestId":98764236489,
//                        "pid":610322,
//                        "platform":"ios"
//                    }
//                }
                String gameGusetId = "24535545885455";
                String pid = "610322";
                String platform = "android";
                if (pid.equals(LoginCenter.getProductId()) && platform.equals(Constants.PLATFORM_ANDROID)) {
                    callback.onSuccess(gameGusetId);
                } else {
                    callback.onFail(00, "");
                }
            }

            @Override
            public void onResponedFail(Throwable e, int code) {

                callback.onFail(00, "");
            }
        });

    }


}
