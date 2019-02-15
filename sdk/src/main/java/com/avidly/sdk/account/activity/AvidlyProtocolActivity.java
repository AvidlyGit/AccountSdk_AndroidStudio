package com.avidly.sdk.account.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sdk.avidly.account.R;

public class AvidlyProtocolActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avidly_activity_protocol);

        WebView webView = findViewById(R.id.avidly_protocol_webview);
        webView.setWebViewClient(new WebViewClient());
        // TODO: 2019/2/13 此处需要替换成用户协议地址
        webView.loadUrl(getString(R.string.avidly_string_protocol_url));
    }

}
