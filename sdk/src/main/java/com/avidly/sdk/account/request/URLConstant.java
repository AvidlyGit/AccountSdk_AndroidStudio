package com.avidly.sdk.account.request;

import com.avidly.sdk.account.data.user.LoginUserManager;

public class URLConstant {

    private static final String ACCOUNT_API = "http://172.20.30.86:8081/api";

    public static String getAlterPwdAPI(String odlpwd, String newpwd) {
        String path = "avidlyUserId=" + LoginUserManager.getCurrentGGID() + "&curPwd=" + odlpwd + "&newPwd=" + newpwd;
        return ACCOUNT_API + "/v1/account/user/modifyPwd?" + path;
    }
}
