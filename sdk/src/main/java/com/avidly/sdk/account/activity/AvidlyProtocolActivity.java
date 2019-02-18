package com.avidly.sdk.account.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sdk.avidly.account.R;

public class AvidlyProtocolActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avidly_activity_protocol);

        WebView webView = findViewById(R.id.avidly_protocol_webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(getString(R.string.avidly_string_protocol_url));
    }

}
