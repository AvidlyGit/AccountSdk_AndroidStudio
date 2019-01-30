package com.avidly.sdk.account;

import com.sdk.avidly.account.R;

public class AvidlyAccountSdkErrors {

    // 服务端数据json解释时异常
    public static final int AVIDLY_LOGIN_ERROR_RESPONSE_JSON_EXCEPTION = -100;

    // 服务端数据返回的productid与用户定义的不一致
    public static final int AVIDLY_LOGIN_ERROR_RESPONSE_MISMATCH_PRODUCT_ID = -101;

    // 服务端数据请求时发生的异常，如超时,通讯异常等
    public static final int AVIDLY_LOGIN_ERROR_RESPONSE_HTTP_EXCEPTION = -102;

    // 服务端数据请求时发生的异常，如超时,通讯异常等
    public static final int AVIDLY_LOGIN_ERROR_RESPONSE_USER_NOT_EXIST = 401;

    public static int getMessgeResourceIdFromErrorCode(int errorCode) {
        switch (errorCode) {
            case AVIDLY_LOGIN_ERROR_RESPONSE_JSON_EXCEPTION:
            case AVIDLY_LOGIN_ERROR_RESPONSE_MISMATCH_PRODUCT_ID:
            case AVIDLY_LOGIN_ERROR_RESPONSE_HTTP_EXCEPTION:
                return R.string.avidly_string_user_login_send_fail;
            case AVIDLY_LOGIN_ERROR_RESPONSE_USER_NOT_EXIST:
                return R.string.avidly_string_user_login_user_not_exist;
            default:
                return R.string.avidly_string_user_login_send_fail;
        }
    }
}
