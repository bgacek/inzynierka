package com.example.myfirstapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


public class Camera2Fragment extends Fragment {


    public Camera2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_camera2, container, false );
        String stream = "http://192.168.0.8:10001";
        WebView mWebView = (WebView) view.findViewById(R.id.web_view2);
        //int width = mWebView.getWidth();
        //int height = mWebView.getHeight();
        mWebView.setInitialScale(170);
        mWebView.loadUrl(stream);


        return view;

    }

}
