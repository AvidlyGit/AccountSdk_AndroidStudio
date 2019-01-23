package com.avidly.sdk.account.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sdk.avidly.account.R;

/**
 * Created by t.wang on 2019/1/23.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public class AvidlyProtocolActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avidly_activity_protocol);

        WebView webView = findViewById(R.id.avidly_protocol_webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(getString(R.string.avidly_string_protocol_url));
    }

}
