package com.avidly.sdk.account.base;

import com.sdk.avidly.account.R;

public class AvidlyAccountSdkErrors {

    // 服务端数据json解释时异常
    public static final int AVIDLY_LOGIN_ERROR_RESPONSE_JSON_EXCEPTION = -100;

    // 服务端数据返回的productid与用户定义的不一致
    public static final int AVIDLY_LOGIN_ERROR_RESPONSE_MISMATCH_PRODUCT_ID = -101;

    // 服务端数据请求时发生的异常，如超时,通讯异常等
    public static final int AVIDLY_LOGIN_ERROR_RESPONSE_HTTP_EXCEPTION = -102;

    // 第三方SDK异常
    public static final int AVIDLY_LOGIN_ERROR_THIRD_SDK_EXCEPTION = -103;

    public static final int AVIDLY_LOGIN_ERROR_GGID_NOT_BOUNDED_THIRD_SDK = 20105;

    public static final int AVIDLY_LOGIN_ERROR_ACCESS_TOKEN_BOUNDED_OTHER_GGID = 20106;

    public static final int AVIDLY_LOGIN_ERROR_RESPONSE_USER_NOT_EXIST = 20201;

    public static final int AVIDLY_OLD_PASSWORD_ALTER_ERROR = 20203;

    public static final int AVIDLY_LOOKUP_PASSWORD_TOO_FREQUENTLY = 20205;

    public static final int AVIDLY_LOGIN_ERROR_WROGN_PASSWORD = 20302;

    public static final int AVIDLY_LOGIN_ERROR_RESPONSE_USER_IS_EXIST = 20504;

    public static final int AVIDLY_LOGIN_ERROR_RESPONSE_USER_IS_BINDED = 20507;

    // facebook登录时，用户取消登录
    public static final int AVIDLY_LOGIN_ERROR_FACEBOOK_LOGIN_CANCEL = 2001;

    // facebook登录时，不成功
    public static final int AVIDLY_LOGIN_ERROR_FACEBOOK_LOGIN_ERROR = 2002;

    public static int getLookupPwdErrorMessage(int code) {
        switch (code) {
            case AVIDLY_LOOKUP_PASSWORD_TOO_FREQUENTLY:
                return R.string.avidly_string_user_lookup_email_frequently;
            case AVIDLY_LOGIN_ERROR_RESPONSE_USER_NOT_EXIST:
                return R.string.avidly_string_user_login_user_not_exist;
            default:
                return R.string.avidly_string_user_unbind_send_fail;
        }
    }

    public static int getUnbindErrorMessage(int code) {
        switch (code) {
            case AVIDLY_LOGIN_ERROR_GGID_NOT_BOUNDED_THIRD_SDK:
                return R.string.avidly_string_user_not_bind_any_third_sdk;
            default:
                return R.string.avidly_string_user_lookup_email_send_fail;
        }
    }

    public static int getLoginErrorMessge(int errorCode) {
        switch (errorCode) {
            case AVIDLY_LOGIN_ERROR_ACCESS_TOKEN_BOUNDED_OTHER_GGID:
                return R.string.avidly_string_user_third_sdk_repeated_bound_fail;
            case AVIDLY_LOGIN_ERROR_RESPONSE_JSON_EXCEPTION:
            case AVIDLY_LOGIN_ERROR_RESPONSE_MISMATCH_PRODUCT_ID:
            case AVIDLY_LOGIN_ERROR_RESPONSE_HTTP_EXCEPTION:
                return R.string.avidly_string_user_login_send_fail;
            case AVIDLY_LOGIN_ERROR_RESPONSE_USER_NOT_EXIST:
                return R.string.avidly_string_user_login_user_not_exist;
            case AVIDLY_LOGIN_ERROR_RESPONSE_USER_IS_EXIST:
                return R.string.avidly_string_user_login_user_existed;
            case AVIDLY_LOGIN_ERROR_RESPONSE_USER_IS_BINDED:
                return R.string.avidly_string_user_bind_fail_binded;
            case AVIDLY_LOGIN_ERROR_FACEBOOK_LOGIN_CANCEL:
                return R.string.avidly_string_facebook_login_cancel;
            case AVIDLY_LOGIN_ERROR_FACEBOOK_LOGIN_ERROR:
                return R.string.avidly_string_facebook_login_error;
            case AVIDLY_LOGIN_ERROR_WROGN_PASSWORD:
                return R.string.avidly_string_user_login_user_password_wrong;
            default:
                return R.string.avidly_string_user_login_send_fail;
        }
    }
}
