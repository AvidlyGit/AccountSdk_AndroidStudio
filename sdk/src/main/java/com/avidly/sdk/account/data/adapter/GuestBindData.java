package com.avidly.sdk.account.data.adapter;

public class GuestBindData {

    public static final byte BIND_TYPE_AVIDLY = 1;
    public static final byte BIND_TYPE_SWITCH = 2;
    public static final byte BIND_TYPE_OTHER = 3;

    public String text;

    public int iconid;

    public int type;

    public boolean isgrid;
}
