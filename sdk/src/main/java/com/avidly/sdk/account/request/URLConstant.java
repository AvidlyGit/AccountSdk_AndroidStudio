package com.avidly.sdk.account.request;

import android.text.TextUtils;

import com.avidly.sdk.account.base.Constants;
import com.avidly.sdk.account.base.utils.DeviceInfoHelper;
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

    /**
     * 新增 guest 接口
     * @param gaid
     * @return
     */
    public static String getGuestRegistApi(String gaid) {
        // TODO: 2019/1/29 此处应该是一串安全密钥
        String path = "pwd=" + "123"+"&gaid=" + gaid
                + "&pdtid=" + LoginCenter.getProductId() + "&platform=" + Constants.PLATFORM_ANDROID;
        return ACCOUNT_API + "/account/guest/new?" + path;
    }

    /**
     * 游客登录
     * @param gameGuestId
     * @param gaid
     * @return
     */
    public static String getGuestLoginApi(String gameGuestId,String gaid) {
        String path = "gameGuestId=" + gameGuestId+"&gaid=" + gaid
                + "&pdtid=" + LoginCenter.getProductId() + "&platform=" + Constants.PLATFORM_ANDROID;
        return ACCOUNT_API + "/account/guest/login?" + path;
    }

    /**
     * 注册接口
     * @param gameGuestId
     * @param userName
     * @param password
     * @param gaid
     * @return
     */
    public static String getAccountRegistOrBindApi(String gameGuestId, String userName, String password,String gaid) {
        String path = "gameGuestId=" + gameGuestId + "&username=" + userName + "&pwd=" + (password)
                + "&pdtid=" + LoginCenter.getProductId() +"&gaid=" + gaid+ "&platform=" + Constants.PLATFORM_ANDROID;
        return ACCOUNT_API + "/account/user/reg?" + path;
    }

    /**
     * 登录接口
     * @param userName
     * @param password
     * @param gaid
     * @return
     */
    public static String getAccountLoginApi(String userName, String password,String gaid) {
        String path = "username=" + userName + "&pwd=" + (password)+"&gaid=" + gaid
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


    public static String getFacebookLoginOrBindUrl(String ggid, String accessToken, String appId,String gaid ) {
        if (TextUtils.isEmpty(ggid)) {
            //fb登录
            String path = "access_token=" + accessToken + "&appId=" + appId+"&gaid=" + gaid
                    + "&pdtid=" + LoginCenter.getProductId() + "&platform=" + Constants.PLATFORM_ANDROID;
            return ACCOUNT_API + "/third/fblogin?" + path;
        } else {

            //fb绑定
            String path = "gameGuestId=" + ggid + "&access_token=" + accessToken +"&gaid=" + gaid+ "&appId=" + appId
                    + "&pdtid=" + LoginCenter.getProductId() + "&platform=" + Constants.PLATFORM_ANDROID;
            return ACCOUNT_API + "/third/fbbind?" + path;
        }
    }

    /**
     * FB解除绑定
     * @param ggid
     * @param accessToken
     * @return
     */
    public static String getFacebookUnBindUrl(String ggid, String accessToken,String gaid) {
        String path = "gameGuestId=" + ggid + "&access_token=" + urlEncode(accessToken)+"&gaid=" + gaid
                + "&pdtid=" + LoginCenter.getProductId() + "&platform=" + Constants.PLATFORM_ANDROID;
        return ACCOUNT_API + "/third/fbunbind?" + path;
    }

}
