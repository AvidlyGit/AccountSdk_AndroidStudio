package com.avidly.sdk.account.request;

import com.avidly.sdk.account.base.http.HttpClientHelper;
import com.avidly.sdk.account.base.http.HttpResponse;
import com.avidly.sdk.account.base.http.UrlQuery;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.avidly.sdk.account.base.AvidlyAccountSdkErrors.AVIDLY_LOGIN_ERROR_RESPONSE_HTTP_EXCEPTION;

public class HttpRequest {

    private static ExecutorService mSingleThreadPool = Executors.newSingleThreadExecutor();

    public static void requestHttpByPost(final String url, final Map<String, String> params, final HttpCallback<String> callback) {
        mSingleThreadPool.execute(new RunWrapper(new Runnable() {
            @Override
            public void run() {
                int code = 0;
                String result = null;
                String request = null;
                if (params != null && params.size() > 0) {
                    request = new UrlQuery().addParams(params).toString();
                }
                try {
                    HttpResponse response = HttpClientHelper.httpPost(url, request, null);
                    code = response.getResponseCode();
                    if (code == 200) {
                        result = response.getBody();
                    }
                } catch (Throwable e) {
                    if (null != callback) {
                        callback.onResponedFail(e, AVIDLY_LOGIN_ERROR_RESPONSE_HTTP_EXCEPTION);
                    }
                    return;
                }

                if (null != callback) {
                    if (200 == code) {
                        callback.onResponseSuccess(result);
                    } else {
                        callback.onResponedFail(null, code);
                    }
                }
            }
        }));
    }

    private static class RunWrapper implements Runnable {
        Runnable runnable;

        RunWrapper(Runnable r) {
            runnable = r;
        }

        public void run() {
            try {
                runnable.run();
            } catch (Throwable e) {
            }
        }
    }
}
