package com.avidly.sdk.account.base.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * HttpResponse
 */
public class HttpResponse {

    private static HttpURLConnection conn;

    public HttpResponse(HttpURLConnection conn) throws Exception {
        if (conn == null)
            throw new IOException("HttpURLConnection is null");
        this.conn = conn;
    }

    public int getResponseCode() throws Exception {
        int code = conn.getResponseCode();

        return code;
    }

    public String getBody() throws Exception {
        String body = "";
        InputStream is = null;
        ByteArrayOutputStream os = null;
        try {
            is = conn.getInputStream();
            os = new ByteArrayOutputStream();
            int size = 1024;
            byte[] bf = new byte[size];
            int count;
            while ((count = is.read(bf)) != -1) {
                os.write(bf, 0, count);
            }
            body = os.toString("UTF-8");
        } catch (Throwable e) {

        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (Throwable e) {
            }

            try {
                if (null != os) {
                    os.close();
                }
            } catch (Throwable e) {
            }

            free();
        }

        return body;
    }

    public void free() {
        try {
            conn.disconnect();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
