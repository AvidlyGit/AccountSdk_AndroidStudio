package com.avidly.sdk.account.third;

import android.content.Intent;

public interface ThirdLoginSdkDelegate {

    boolean isThis(int type);

    void login(Object context, ThirdSdkLoginCallback callback);

    void bind(Object context, ThirdSdkLoginCallback callback);

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void exit();

}
