package com.avidly.sdk.account.base.http;

import android.text.TextUtils;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * UrlQuery
 */
public class UrlQuery {
    private StringBuffer query = new StringBuffer();

    public UrlQuery addParams(Map<String, String> map) {
        if (map != null) {
            for (String key : map.keySet()) {
                if (!TextUtils.isEmpty(key) && map.get(key) != null) {
                    this.add(key, map.get(key));
                }
            }
        }

        return this;
    }

    public UrlQuery addParams(HashMap<String, String> map) {
        if (map != null) {
            for (String key : map.keySet()) {
                if (!TextUtils.isEmpty(key) && map.get(key) != null) {
                    this.add(key, map.get(key));
                }
            }
        }

        return this;
    }

    private UrlQuery add(String key, String value) {
        try {
            if (query.length() > 0) {
                query.append('&');
            }

            if (TextUtils.isEmpty(value)) {
                value = "";
            }
            query.append(URLEncoder.encode(key, "UTF-8")).append('=').append(URLEncoder.encode(value, "UTF-8"));
            //query.append(URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8"));
        } catch (Throwable e) {

        }

        return this;
    }

    public String appendToUrl(String url) {
        return url + (url.contains("?") ? "&" : "?") + toString();
    }

    @Override
    public String toString() {
        return query.toString();
    }
}
