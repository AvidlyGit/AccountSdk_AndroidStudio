package com.avidly.sdk.account.base.http;

import android.text.TextUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * Created by Holaverse on 2017/5/23.
 */

public class HttpClientHelper {
    private static final Pattern IP_REG = Pattern.compile("(\\d*)\\.(\\d*)\\.(\\d*)\\.(\\d*)");
    private static final String DNS_URL = "http://52.89.140.122/";
    private static final Map<String, String> DOMAIN_MAP = new ConcurrentHashMap<>();
    private static final String KEY_UNKOWNHOST = "unkownhost-";
    private static final String KEY_WEBDNS_OK = "webdns-ok-";
    private static final String KEY_WEBDNS_NOK = "webdns-nok-";
    private static final String KEY_EXCEPTION_LOG2 = "exception-log2";
    private static final String KEY_EXCEPTION_LOG3 = "exception-log3";
    private static final String KEY_EXCEPTION_IP_URL = "exception-ipurl";
    private static final String KEY_EXCEPTION_DOMAIN = "exception-domain";

    static {
//        DOMAIN_MAP.put("sta-test.haloapps.com", "");
//        DOMAIN_MAP.put("a-sta.haloapps.com", "");
//        DOMAIN_MAP.put("c-sta.haloapps.com", "");
//        DOMAIN_MAP.put("c-sta-cn.upltv.com", "");
//        DOMAIN_MAP.put("i.haloapps.com", "");
//        DOMAIN_MAP.put("stat.haloapps.com", "");
//        DOMAIN_MAP.put("ann-sta.haloapps.com", "");
//        DOMAIN_MAP.put("report-adsdk.upltv.com", "");
//        DOMAIN_MAP.put("report-ads-sdk.upltv.com", "");
//        DOMAIN_MAP.put("ads-sdk-cn.upltv.com", "");
//        DOMAIN_MAP.put("ads-sdk.upltv.com", "");

    }

    public static HttpResponse httpGet(final String url, final String userAgent) throws Exception {
        return httpSend("get", url, null, userAgent, null);
    }

    public static HttpResponse httpPost(final String url, final String requestBody, final String userAgent) throws Exception {
        return httpSend("post", url, requestBody, userAgent, null);
    }


    public static HttpResponse httpPost(final String url, final String requestBody, final String userAgent, final String enctyType) throws Exception {
        return httpSend("post", url, requestBody, userAgent, enctyType);
    }

    private static HttpResponse httpSend(String type, final String url, final String requestBody, final String userAgent, final String enctyType) throws Exception {
        HttpResponse body = null;
//        try {
            HttpClient client = HttpClient.builder().setUrl(url);
            if (type.equals("post")) {
                client.setRequestMethod(HttpClient.METHOD_POST);
            }
            if (!TextUtils.isEmpty(requestBody)) {
                client.setRequestBody(requestBody);
            }
            if (!TextUtils.isEmpty(userAgent)) {
                client.setUserAgent(userAgent);
            }
            if (!TextUtils.isEmpty(enctyType)) {
                client.setEnctyType(enctyType);
            }
            body = client.request();
//        } catch (UnknownHostException e) {
//            String domain = getDnsDomain(url);
//            boolean log = System.currentTimeMillis() - SpHelper.getLong(UpBaseSdk.getContext(), KEY_UNKOWNHOST + domain) > DateUtils.DAY_IN_MILLIS;
//            if (log) {
//                SpHelper.putLong(UpBaseSdk.getContext(), KEY_UNKOWNHOST + domain, System.currentTimeMillis());
//                TrackingHelper.build().error("HttpClientHelper httpSend log1: " + url + " " + e.getMessage());
//
//                HashMap<String, String> map = new HashMap<>();
//                map.put("__hostname", domain);
//                TrackingHelper.build().setKey("_NEW_HOST_NO_OK").addParams(map).log();
//            }
//
//            String newUrl = getIpUrl(url);
//
//            if (!TextUtils.isEmpty(newUrl)) {
//                try {
//                    newUrl = newUrl.replace("https", "http");
//                    HttpClient client = HttpClient.builder().setUrl(newUrl);
//                    if (type.equals("post")) {
//                        client.setRequestMethod(HttpClient.METHOD_POST);
//                    }
//                    if (!TextUtils.isEmpty(requestBody)) {
//                        client.setRequestBody(requestBody);
//                    }
//                    if (!TextUtils.isEmpty(userAgent)) {
//                        client.setUserAgent(userAgent);
//                    }
//                    client.setRequestHeader("Host", domain);
//                    body = client.request().getBody();
//                } catch (Throwable ex) {
//                    log = System.currentTimeMillis() - SpHelper.getLong(UpBaseSdk.getContext(), KEY_EXCEPTION_LOG2) > DateUtils.DAY_IN_MILLIS;
//                    if (log) {
//                        SpHelper.putLong(UpBaseSdk.getContext(), KEY_EXCEPTION_LOG2, System.currentTimeMillis());
//                        TrackingHelper.build().error("HttpClientHelper httpSend log2: " + newUrl + " " + ex.getMessage());
//                    }
//                }
//            }
//        } catch (Throwable e) {
//
//        }
        return body;
    }

//    private static String getIpUrl(final String url) {
//        String newUrl = "";
//
//        for (String key : DOMAIN_MAP.keySet()) {
//            if (url.contains(key)) {
//                if (!TextUtils.isEmpty(DOMAIN_MAP.get(key))) {
//                    newUrl = url.replace(key, DOMAIN_MAP.get(key));
//                } else {
//                    String dnsUrl = getDnsUrl(key);
//                    try {
//                        String body = HttpClient.builder().setUrl(dnsUrl).request().getBody();
//                        if (!TextUtils.isEmpty(body)) {
//                            JSONObject json = new JSONObject(body);
//                            if (json.has("answer")) {
//                                JSONArray array = json.getJSONArray("answer");
//                                if (array != null && array.length() > 0) {
//                                    for (int i = 0; i < array.length(); i++) {
//                                        JSONObject item = array.optJSONObject(i);
//                                        String type = item.optString("type");
//                                        if (!"A".equals(type)) {
//                                            continue;
//                                        }
//
//                                        String ip = item.optString("rdata");
//                                        Matcher matcher = IP_REG.matcher(ip);
//                                        if (matcher.matches()) {
//                                            boolean log = System.currentTimeMillis() - SpHelper.getLong(UpBaseSdk.getContext(), KEY_WEBDNS_OK + key) > DateUtils.DAY_IN_MILLIS;
//                                            if (log) {
//                                                SpHelper.putLong(UpBaseSdk.getContext(), KEY_WEBDNS_OK + key, System.currentTimeMillis());
//
//                                                HashMap<String, String> map = new HashMap<>();
//                                                map.put("__hostname", key);
//                                                map.put("__host_ip", ip);
//                                                TrackingHelper.build().setKey("_NEW_WEBDNS_USED").addParams(map).log();
//                                            }
//                                            DOMAIN_MAP.put(key, ip);
//                                            break;
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    } catch (Throwable e) {
//                        boolean log = System.currentTimeMillis() - SpHelper.getLong(UpBaseSdk.getContext(), KEY_EXCEPTION_IP_URL) > DateUtils.DAY_IN_MILLIS;
//                        if (log) {
//                            SpHelper.putLong(UpBaseSdk.getContext(), KEY_EXCEPTION_IP_URL, System.currentTimeMillis());
//                            TrackingHelper.build().error("HttpClientHelper getIpUrl: " + e.getMessage());
//                        }
//                    }
//
//                    boolean log = System.currentTimeMillis() - SpHelper.getLong(UpBaseSdk.getContext(), KEY_WEBDNS_NOK + key) > DateUtils.DAY_IN_MILLIS;
//                    if (log && !DOMAIN_MAP.containsKey(key)) {
//                        SpHelper.putLong(UpBaseSdk.getContext(), KEY_WEBDNS_NOK + key, System.currentTimeMillis());
//
//                        HashMap<String, String> map = new HashMap<>();
//                        map.put("__hostname", key);
//                        TrackingHelper.build().setKey("_NEW_WEBDNS_NO_OK").addParams(map).log();
//                    }
//                }
//
//                break;
//            }
//        }
//
//        return newUrl;
//    }
//
//    private static String getDnsUrl(String domain) {
//        return DNS_URL + domain + "/a";
//    }
//
//    private static String getDnsDomain(String url) {
//        String domain = "";
//
//        try {
//            int startIndex = url.indexOf("//") + 2;
//            int endIndex = 0;
//            if (url.indexOf(".com") > 0) {
//                endIndex = url.indexOf(".com") + 4;
//            }
//            if (url.indexOf(".cn") > 0) {
//                endIndex = url.indexOf(".cn") + 3;
//            }
//            if (endIndex > startIndex) {
//                domain = url.substring(startIndex, endIndex);
//            }
//        } catch (Throwable e) {
//            boolean log = System.currentTimeMillis() - SpHelper.getLong(UpBaseSdk.getContext(), KEY_EXCEPTION_DOMAIN) > DateUtils.DAY_IN_MILLIS;
//            if (log) {
//                SpHelper.putLong(UpBaseSdk.getContext(), KEY_EXCEPTION_DOMAIN, System.currentTimeMillis());
//                TrackingHelper.build().error("HttpClientHelper getDnsDomain: " + e.getMessage());
//            }
//        }
//
//        return domain;
//    }
}
