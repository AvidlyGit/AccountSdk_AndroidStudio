package com.avidly.sdk.account.base.utils;


import java.security.MessageDigest;

/**
 * Created by t.wang on 2018/4/13.
 */
public class Md5Utils {
    private static String MD5 = "MD5";
    private static final String ENCODE_UTF8 = "UTF-8";

    /**
     * md5加密字符串
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static String textOfMd5(String value) throws Exception {
        byte[] data = (value).getBytes(ENCODE_UTF8);
        MessageDigest messageDigest = MessageDigest.getInstance(MD5);
        messageDigest.reset();
        messageDigest.update(data);
        byte[] digest = messageDigest.digest();
        return Base64Utils.encode(digest);
    }

}
