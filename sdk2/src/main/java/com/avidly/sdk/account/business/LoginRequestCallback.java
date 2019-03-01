package com.avidly.sdk.account.business;

public interface LoginRequestCallback<T> {
    void onSuccess(T result);

    void onFail(Throwable e, int code);
}
