package com.avidly.sdk.account.third;

import com.avidly.sdk.account.data.user.Account;

public class ThirdSdkFactory {


    public static ThirdLoginSdkDelegate newThirdSdkLoginDeleage(int type) {
        switch (type) {
            case Account
                    .ACCOUNT_MODE_FACEBOOK:
                return new FacebookLoginSdk();
        }
        return null;
    }
}
