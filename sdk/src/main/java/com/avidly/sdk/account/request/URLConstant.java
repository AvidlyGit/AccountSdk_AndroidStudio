package com.avidly.sdk.account.request;

import com.avidly.sdk.account.base.Constants;
import com.avidly.sdk.account.business.LoginCenter;

public class URLConstant {

    private static final String ACCOUNT_API = "http://172.20.30.86:8081/api/v1/account";

    public static String getAlterPwdAPI(String userName, String odlpwd, String newpwd) {
        String path = "username=" + userName + "&curPwd=" + odlpwd + "&newPwd=" + newpwd;
        return ACCOUNT_API + "/user/modifyPwd?" + path;
    }

    public static String getGuestLoginApi() {
        String path = "pwd=" + "123" + "&pid=" + LoginCenter.getProductId() + "&platform=" + Constants.PLATFORM_ANDROID;
        return ACCOUNT_API + "/guest/new?" + path;
    }

    public static String getAccountLoginApi(String userName, String password) {
        String path = "username=" + userName + "&pwd=" + password + "&pid=" + LoginCenter.getProductId() + "&platform=" + Constants.PLATFORM_ANDROID;
        return ACCOUNT_API + "/user/login?" + path;
    }

    public static String getAccountRegistOrBindApi(String gameGuestId, String userName, String password) {
        String path = "gameGuestId=" + gameGuestId + "&username=" + userName + "&pwd=" + password
                + "&pid=" + LoginCenter.getProductId() + "&platform=" + Constants.PLATFORM_ANDROID;
        return ACCOUNT_API + "/user/reg?" + path;
    }




}
