package com.avidly.sdk.account.third;

import com.avidly.sdk.account.data.user.Account;

public class ThirdSdkFactory {

    public static String nameOfAccountMode(int type) {
        switch (type) {
            case Account .ACCOUNT_MODE_FACEBOOK:
                return "facebook";
            case Account .ACCOUNT_MODE_AVIDLY:
                return "avidly";
            case Account .ACCOUNT_MODE_GOOGLEPLAY:
                return "googleplay";
            case Account .ACCOUNT_MODE_TWITTER:
                return "twitter";
            case Account .ACCOUNT_MODE_GUEST:
                return "guest";
        }
        return "";
    }

    public static boolean isExistSdkLib(int type) {
        switch (type) {
            case Account .ACCOUNT_MODE_FACEBOOK:
                try {
                    Class.forName("com.facebook.FacebookActivity");
                    return true;
                } catch (Exception e) {
                }
                break;
        }
        return false;
    }

    public static ThirdLoginSdkDelegate newThirdSdkLoginDeleage(int type) {
        switch (type) {
            case Account .ACCOUNT_MODE_FACEBOOK:
                return new FacebookLoginSdk();
        }
        return null;
    }
}
