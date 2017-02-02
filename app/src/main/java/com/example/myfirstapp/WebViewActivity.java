package com.example.myfirstapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by bgacek on 31.01.17.
 */

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        String stream = "http://192.168.0.8:9090";
        WebView webView = (WebView)findViewById(R.id.web_view);
        int width = webView.getWidth();
        int height = webView.getHeight();
        webView.loadUrl(stream +"?width=" +width +"&height=" +height);





    }
}
