package com.avidly.sdk.account.base;

public class Constants {
    public static long AUTO_LOGIN_TIME_OUT_MILLS = 3 * 1000;
    public static long AUTO_CLOSE_ERROR_LAYOUT_MILLS = 2 * 1000;
    public static String URL_ENCODER_ENC = "UTF-8";
    public static String PLATFORM_ANDROID = "android";

    public static final String INTENT_KEY_ACTION_LOGIN = "intent_key_action_login";
    public static final String INTENT_KEY_ACTION_SWITCH = "intent_key_action_switch";
    public static final String INTENT_KEY_ACTION_BIND = "intent_key_action_bind";

    public static String LOGIN_SUB_FRAGMENT_TYPE = "sub_fragment_type";

    public static int SUB_FRAGMENT_TYPE_BIND = 1;
    public static int SUB_FRAGMENT_TYPE_LOGIN = 2;
    public static int SUB_FRAGMENT_TYPE_REGIST = 3;

}
