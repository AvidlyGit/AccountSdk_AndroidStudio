package com.avidly.sdk.account.base.auth;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @Auther zhangzhenxing
 * @Date 2019/2/28 11:12
 * @Descrpiton 方法介绍
 */
public class RandomString {

    private static final char ZERO = '0';
    private static final char ONE = '1';
    private static final String ZERO_ONE_STRING = "01";

    //进行效验
    public Validate getValidates(String aValue) {
        String[] Values = RandomString.convertNewStrArrayFromOrginal(aValue);
        BigInteger bValue = new BigInteger(Values[0], 2);

        String cValue = RandomString.encrypByMd5(RandomString.base64Encode(Values[1]));
        Validate validate = new Validate();
        validate.setbValue(bValue);
        validate.setcValue(cValue);
        return validate;
    }



    /**
     * 从原始字符串经过转换得到新的字符串数组  【0】代表以1开头的数字  【1】代表新组成的字符串
     *
     * @param orginalStr
     * @return
     */
    private static String[] convertNewStrArrayFromOrginal(String orginalStr) {
        String[] str = new String[2];
        int length = orginalStr.length();
        int count = RandomString.randomCount(length, length);
        String generateString = randomString(count, ZERO_ONE_STRING);
        String getFirstIsOneStr = newZeroOneStrAfterCutOneBeforeZero(generateString);
        String getIsOneAllPositionStr = isOneAllPositionStr(getFirstIsOneStr.toCharArray());
        String convertNewStr = newStrFromOrginalstrByOnepostion(getIsOneAllPositionStr, orginalStr);
        str[0] = getFirstIsOneStr;
        str[1] = convertNewStr;
        return str;
    }

    private static String randomString(int count, String spec) {
        if (count <= 0) {
            return "";
        }
        boolean needinsert = false;
        if (spec != null && spec.length() > 0 && count > spec.length()) {
            needinsert = true;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < count; ) {

            if (needinsert && i < count - spec.length()) {
                sb.append(spec);
                i += spec.length();
                needinsert = false;
                continue;

            }
            int ra= (int) (Math.random()*1000);
            if (ra% 2 == 0) {
                sb.append('0');
            } else {
                sb.append('1');
            }
            i++;
        }
        return sb.toString();
    }


    /**
     * 根据1的下标,从原始字符串得到新的字符串
     *
     * @param onePosition
     * @param orginalStr
     * @return
     */
    private static String newStrFromOrginalstrByOnepostion(String onePosition, String orginalStr) {
        char[] originalCharArray = orginalStr.toCharArray();
        StringBuffer sb = new StringBuffer();
        String[] tempArray = onePosition.split("-");
        for (int i = 0; i < tempArray.length; i++) {
            String temp = tempArray[i];
            if (temp != null && !(temp.trim().equals(""))) {
                sb.append(originalCharArray[Integer.parseInt(temp)]);
            }
        }
        return sb.toString();
    }

    /**
     * 得到是1的字符串在charbytes位置,中间以-分开
     *
     * @param charBytes
     * @return
     */
    private static String isOneAllPositionStr(char[] charBytes) {
        StringBuffer sbIsOnePosition = new StringBuffer();
        for (int i = 0; i < charBytes.length; i++) {
            char c = charBytes[i];
            switch (c) {
                case ONE:
                    sbIsOnePosition.append(i + "-");
                    break;
            }
        }
        return sbIsOnePosition.toString();
    }

    /**
     * 去除1之前所有0得到新的字符串
     *
     * @param generateString
     * @return
     */
    private static String newZeroOneStrAfterCutOneBeforeZero(
            String generateString) {
        char[] charBytes = generateString.toCharArray();
        boolean isOneFlag = false;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < charBytes.length; i++) {
            char c = charBytes[i];
            if (!isOneFlag) {
                switch (c) {
                    case ONE:
                        sb.append(c);
                        isOneFlag = true;
                        break;
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 得到随机数的范围
     *
     * @param minValue
     * @param maxValue
     * @return
     */
    private static int randomCount(int minValue, int maxValue) {
        return (int) (Math.random() * (maxValue - minValue + 1)) + minValue;  //TODO:此数永远是一个定值
    }

    /**
     * 加密操作
     * @param plainText
     * @return
     */
    private static String base64Encode(String plainText) {
        String encodeWord = null;
        try {
            encodeWord = android.util.Base64.encodeToString(plainText.getBytes("utf-8"), android.util.Base64.NO_WRAP);
            Log.i("Tag", " encode wrods = " + encodeWord);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeWord;
    }
    /**
     * 解密操作
     * @param encodeStr
     * @return
     */
    private static String base64Decode(String encodeStr) {
        String decodeWord = null;
        try {
            decodeWord = new String(android.util.Base64.decode(encodeStr, android.util.Base64.NO_WRAP), "utf-8");
            Log.i("Tag", "decode wrods = " + decodeWord);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodeWord;
    }


//    public static void main(String[] args) {
//        String encodeResult=base64Encode("2891789_@!@_#!$#!@%)$#^)$%_大萨达无多2所发出212311shauih@!#8*");
//        System.out.println("encodeResult is--->"+encodeResult);
//        String decodeResult=base64Decode(encodeResult);
//        System.out.println("decodeResult is--->"+decodeResult);
//    }

    //写一个md5加密的方法
    public static String encrypByMd5(String plainText) {
        //定义一个字节数组
        byte[] secretBytes = null;
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //对字符串进行加密
            md.update(plainText.getBytes());
            //获得加密后的数据
            secretBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        //将加密后的数据转换为16进制数字
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
    public class Validate {

        private BigInteger bValue;

        private String cValue;

        public BigInteger getbValue() {
            return bValue;
        }

        public void setbValue(BigInteger bValue) {
            this.bValue = bValue;
        }

        public String getcValue() {
            return cValue;
        }

        public void setcValue(String cValue) {
            this.cValue = cValue;
        }
    }

}
