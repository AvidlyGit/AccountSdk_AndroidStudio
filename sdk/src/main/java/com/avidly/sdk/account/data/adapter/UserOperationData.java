package com.avidly.sdk.account.data.adapter;

public class UserOperationData {

    public static final byte USER_OPERATION_TYPE_AVIDLY = 1;
    public static final byte USER_OPERATION_TYPE_SWITCH = 2;
    public static final byte USER_OPERATION_TYPE_BIND_OTHER = 3;
    public static final byte USER_OPERATION_TYPE_CHANGE_PWD = 4;

    public String text;

    public int iconid;

    public int type;

    public boolean isgrid;
}
