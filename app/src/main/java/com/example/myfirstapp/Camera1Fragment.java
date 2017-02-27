package com.example.myfirstapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.SeekBar;
import android.widget.Toast;


public class Camera1Fragment extends Fragment {


    private SeekBar mSeekBar;

    public Camera1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_camera1, container, false );
        String stream = "http://192.168.0.8:9090/stream";
        WebView mWebView = (WebView) view.findViewById(R.id.web_view1);
        mWebView.setInitialScale(165);
        mWebView.loadUrl(stream);

        mSeekBar = (SeekBar) view.findViewById(R.id.seekBar1);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int prog = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                prog = progress;

                //Toast.makeText(getContext(), "Changing Progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getContext(), "Progres on Start: " +prog, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getContext(), "Progres on Stop: " +prog, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
