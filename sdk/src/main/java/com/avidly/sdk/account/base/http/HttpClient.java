package com.avidly.sdk.account.base.http;

import android.text.TextUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by echo on 16/6/6.
 */
public class HttpClient {

    public static String METHOD_GET = "GET";
    public static String METHOD_HEAD = "HEAD";
    public static String METHOD_POST = "POST";

    private URL url;
    private int connectTimeout = 10 * 1000;
    private int readTimeout = 15 * 1000;
    private boolean useCaches = false;
    private boolean redirected = false;
    private String requestMethod = METHOD_GET;
    private String requestBody = null;
    private String userAgent = null;
    private String enctyType = null;
    private Map<String, String> headerMap = new ConcurrentHashMap<>();
    private HttpURLConnection conn;

    public static HttpClient builder() {
        return new HttpClient();
    }

    private HttpClient() {
    }

    public HttpClient setUrl(String url) throws MalformedURLException {
        this.url = new URL(url);
        return this;
    }

    public HttpClient setConnectTimeout(int timeout) {
        connectTimeout = timeout;
        return this;
    }

    public HttpClient setReadTimeout(int timeout) {
        readTimeout = timeout;
        return this;
    }

    public HttpClient setUseCaches(boolean use) {
        useCaches = use;
        return this;
    }

    public HttpClient setRequestMethod(String method) {
        requestMethod = method;
        return this;
    }

    public HttpClient setRequestHeader(String key, String value) {
        if (!TextUtils.isEmpty(key) && value != null) {
            headerMap.put(key, value);
        }
        return this;
    }

    public HttpClient setRequestBody(String body) {
        requestBody = body;
        return this;
    }

    public HttpClient setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public HttpClient setEnctyType(String enctyType) {
        this.enctyType = enctyType;
        return this;
    }

    public HttpResponse request() throws Exception {
        if (url == null)
            throw new IOException("URL is empty");

        makeConnection();

        return new HttpResponse(conn);
    }

    private void makeConnection() throws Exception {
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            conn.setUseCaches(useCaches);
            conn.setRequestMethod(requestMethod);
            conn.setInstanceFollowRedirects(true);

            if (userAgent != null) {
                conn.setRequestProperty("User-Agent", userAgent);
            }

            if (headerMap != null && !headerMap.isEmpty()) {
                for (String key : headerMap.keySet()) {
                    conn.setRequestProperty(key, headerMap.get(key));
                }
            }

            if (METHOD_GET.equals(requestMethod) || METHOD_HEAD.equals(requestMethod)) {
                conn.setDoOutput(false);
            } else if (METHOD_POST.equals(requestMethod)) {
                conn.setDoOutput(true);
            }

            if (requestBody != null) {
                if (enctyType != null) {
                    conn.setRequestProperty("Content-type", enctyType);
                } else {
                    conn.setRequestProperty("Content-type", "application/json;charset=UTF-8");
                }

                OutputStream os = conn.getOutputStream();
                byte[] bytes = requestBody.getBytes("UTF-8");
                os.write(bytes);
                os.close();
            }

            if (conn.getResponseCode() >= 300 && conn.getResponseCode() < 400) {
                String location = conn.getHeaderField("Location");
                if (TextUtils.isEmpty(location)) {
                    location = conn.getHeaderField("location");
                }

                if (!redirected && !TextUtils.isEmpty(location)) {
                    redirected = true;
                    setUrl(location);
                    makeConnection();
                }
            }
        } catch (Throwable e) {
            throw new Exception(e);
        }
    }
}
