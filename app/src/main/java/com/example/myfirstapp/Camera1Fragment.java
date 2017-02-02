package com.example.myfirstapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Camera1Fragment extends Fragment {


    public Camera1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera1, container, false );
        String stream = "http://192.168.0.8:9090/stream";
        WebView mWebView = (WebView) view.findViewById(R.id.web_view);
        //int width = mWebView.getWidth();
        //int height = mWebView.getHeight();
        mWebView.setInitialScale(165);

        mWebView.loadUrl(stream);

        return view;
    }

}
