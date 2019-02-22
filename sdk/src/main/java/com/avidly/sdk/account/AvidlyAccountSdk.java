package com.avidly.sdk.account;

import android.content.Context;
import android.content.Intent;

import com.avidly.sdk.account.activity.UserManagerActivity;
import com.avidly.sdk.account.base.utils.DeviceInfoHelper;
import com.avidly.sdk.account.business.LoginCenter;

public class AvidlyAccountSdk {
    /**
     * 初始化sdk
     *
     * @param productId product_id，从运营处获得
     * @param callback  登录成功，切换账号，绑定账号等操作成功后返回对应的 gameGuestId
     */
    public static void initSdk(String productId, AvidlyAccountCallback callback) {
        LoginCenter.setProductId(productId);
        LoginCenter.setLoginCallback(callback);
    }

    /**
     * 用户登录，游戏启动时调用
     *
     * @param context
     */
    public static void accountLogin(Context context) {
        LoginCenter.loginNow(context);
        DeviceInfoHelper.init(context);
    }

    /**
     * 用户中心页面
     *
     * @param context
     */
    public static void showUserManagerUI(Context context) {
        LoginCenter.checkScreenOrietation(context);
        Intent intent = new Intent(context, UserManagerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
