package com.avidly.sdk.account.request;

public interface HttpCallback<T> {
    void onResponseSuccess(T result);

    void onResponedFail(Throwable e, int code);
}
