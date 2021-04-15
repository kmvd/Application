package com.zhou.demo33333.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zhou.demo33333.BaseActivity;
import com.zhou.demo33333.R;
import com.zhou.logutils.LogUtil;
import com.zhou.logutils.Logger;

import butterknife.BindView;

public class WebViewActivity extends BaseActivity {

    private final Logger logger = new Logger(WebViewActivity.class.getSimpleName());

    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
    }

    @Override
    protected void InitView() {
        super.InitView();
        Intent intent = getIntent();
        String loadUrl = intent.getStringExtra("url");
        logger.d("loadUrl:" + loadUrl);
        if (TextUtils.isEmpty(loadUrl)) {
            finish();
            return;
        }

        //初始化 Webview
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                LogUtil.d("load request url:" + request.getUrl());
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

        // 直接加载页面
        webView.loadUrl(loadUrl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
    }
}