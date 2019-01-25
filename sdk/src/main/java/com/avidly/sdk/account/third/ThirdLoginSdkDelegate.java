package com.avidly.sdk.account.third;

import android.app.Activity;
import android.content.Intent;

public interface ThirdLoginSdkDelegate {

    boolean isThis(int type);

    void login(Activity activity, ThirdSdkLoginCallback callback);

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void exit();

}
