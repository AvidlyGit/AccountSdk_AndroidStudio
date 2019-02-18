package com.avidly.sdk.account.request;

import android.text.TextUtils;

import com.avidly.sdk.account.base.Constants;
import com.avidly.sdk.account.business.LoginCenter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URLConstant {

    private static final String ACCOUNT_API = "http://34.215.85.8:8081/api/v1";

    public static String urlEncode(String v) {
        try {
            return URLEncoder.encode(v, Constants.URL_ENCODER_ENC);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return v;
    }

    public static String getGuestRegistApi() {
        // TODO: 2019/1/29 此处应该是一串安全密钥
        String path = "pwd=" + "123"
                + "&pdtid=" + LoginCenter.getProductId() + "&platform=" + Constants.PLATFORM_ANDROID;
        return ACCOUNT_API + "/account/guest/new?" + path;
    }

    public static String getGuestLoginApi(String gameGuestId) {
        String path = "gameGuestId=" + gameGuestId
                + "&pdtid=" + LoginCenter.getProductId() + "&platform=" + Constants.PLATFORM_ANDROID;
        return ACCOUNT_API + "/account/guest/login?" + path;
    }

    public static String getAccountRegistOrBindApi(String gameGuestId, String userName, String password) {
        String path = "gameGuestId=" + gameGuestId + "&username=" + userName + "&pwd=" + (password)
                + "&pdtid=" + LoginCenter.getProductId() + "&platform=" + Constants.PLATFORM_ANDROID;
        return ACCOUNT_API + "/account/user/reg?" + path;
    }

    public static String getAccountLoginApi(String userName, String password) {
        String path = "username=" + userName + "&pwd=" + (password)
                + "&pdtid=" + LoginCenter.getProductId() + "&platform=" + Constants.PLATFORM_ANDROID;
        return ACCOUNT_API + "/account/user/login?" + path;
    }

    public static String getAlterPwdAPI(String userName, String odlpwd, String newpwd) {
        String path = "username=" + userName + "&curPwd=" + (odlpwd) + "&newPwd=" + (newpwd)
                + "&pdtid=" + LoginCenter.getProductId() + "&platform=" + Constants.PLATFORM_ANDROID;
        return ACCOUNT_API + "/account/user/modifyPwd?" + path;
    }

    public static String retrievePwd(String address) {
        String path = "email=" + address
                + "&pdtid=" + LoginCenter.getProductId() + "&platform=" + Constants.PLATFORM_ANDROID;
        return ACCOUNT_API + "/account/user/resetPwd?" + path;
    }

    public static String getFacebookLoginOrBindUrl(String ggid, String accessToken, String appId) {
        if (TextUtils.isEmpty(ggid)) {
            String path = "access_token=" + accessToken + "&appId=" + appId
                    + "&pdtid=" + LoginCenter.getProductId() + "&platform=" + Constants.PLATFORM_ANDROID;
            return ACCOUNT_API + "/third/fblogin?" + path;
        } else {
            String path = "gameGuestId=" + ggid + "&access_token=" + accessToken + "&appId=" + appId
                    + "&pdtid=" + LoginCenter.getProductId() + "&platform=" + Constants.PLATFORM_ANDROID;
            return ACCOUNT_API + "/third/fbbind?" + path;
        }
    }

    public static String getFacebookUnBindUrl(String ggid, String accessToken) {
        String path = "gameGuestId=" + ggid + "&access_token=" + urlEncode(accessToken)
                + "&pdtid=" + LoginCenter.getProductId() + "&platform=" + Constants.PLATFORM_ANDROID;
        return ACCOUNT_API + "/third/fbunbind?" + path;
    }

}
