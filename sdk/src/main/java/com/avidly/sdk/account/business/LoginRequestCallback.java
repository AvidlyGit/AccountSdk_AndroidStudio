package com.avidly.sdk.account.business;

/**
 * Created by t.wang on 2019/1/22.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public interface LoginRequestCallback<T> {
    void onSuccess(T result);

    void onFail(Throwable e, int code);
}
