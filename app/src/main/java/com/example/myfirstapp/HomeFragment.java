package com.example.myfirstapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;




public class HomeFragment extends Fragment {

    private Boolean result;
    private Button alarmButton;
    private Integer alarmStatus;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
        @param alarmStatus -> -1 = unsychronized, 0 = disarmed, 1 = armed, 2 = TRIGGERED
     */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        alarmButton = (Button) view.findViewById(R.id.alarm_button);
        alarmButton.setText(R.string.alarm_button_unsynchronized);
        alarmButton.setTag(1);
        alarmButton.setBackgroundColor(Color.YELLOW);
        alarmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alarmStatus = (Integer) v.getTag();
                startVerification();

            }
        });

        final Button window1Button = (Button) view.findViewById(R.id.window1_button);
        window1Button.setText(R.string.window1);
        window1Button.setBackgroundColor(Color.GREEN);

        final Button window2Button = (Button) view.findViewById(R.id.window2_button);
        window2Button.setText(R.string.window2);
        window2Button.setBackgroundColor(Color.RED);

        final Button doorButton = (Button) view.findViewById(R.id.door_button);
        doorButton.setText(R.string.door_locked);
        doorButton.setText(R.string.door_unlocked);
        doorButton.setBackgroundColor(Color.RED);



        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0) result = data.getBooleanExtra("authenticationResult", false);
        switch (alarmStatus) {

            case 0:
                if(result) {
                    alarmButton.setTag(1);
                    alarmButton.setBackgroundColor(Color.GREEN);
                    alarmButton.setText(R.string.alarm_button_armed);

                }
                break;

            case 1:
                if(result)
                {
                    alarmButton.setTag(0);
                    alarmButton.setBackgroundColor(Color.RED);
                    alarmButton.setText(R.string.alarm_button_disarmed);

                }
                break;

        }

    }

    private void startVerification() {
        FragmentManager mFragmentManager = getFragmentManager();
        FingerprintPopupFragment mFingerprintPopupFragment = new FingerprintPopupFragment();
        mFingerprintPopupFragment.setTargetFragment(this, 0);
        mFingerprintPopupFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        mFingerprintPopupFragment.show(mFragmentManager, "AlarmToggle");


    }



}
