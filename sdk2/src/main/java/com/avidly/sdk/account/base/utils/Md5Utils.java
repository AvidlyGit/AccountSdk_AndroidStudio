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
//    public static String textOfMd5(String value) throws Exception {
//        byte[] data = (value).getBytes(ENCODE_UTF8);
//        MessageDigest messageDigest = MessageDigest.getInstance(MD5);
//        messageDigest.reset();
//        messageDigest.update(data);
//        byte[] digest = messageDigest.digest();
//        //return Base64Utils.encode(digest);
//        return new String(digest, ENCODE_UTF8);
//    }
    public static String textOfMd5(String value) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = value.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }


}
