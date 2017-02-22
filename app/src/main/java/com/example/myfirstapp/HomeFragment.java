package com.example.myfirstapp;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



public class HomeFragment extends Fragment {


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

        final Button alarmButton = (Button) view.findViewById(R.id.alarm_button);
        alarmButton.setText(R.string.alarm_button_unsynchronized);
        alarmButton.setTag(1);
        alarmButton.setBackgroundColor(Color.YELLOW);
        alarmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int alarmStatus = (Integer) v.getTag();
                switch (alarmStatus) {
                    case 0:
                        alarmButton.setTag(1);
                        alarmButton.setBackgroundColor(Color.GREEN);
                        alarmButton.setText(R.string.alarm_button_armed);
                        break;
                    case 1:
                        alarmButton.setTag(0);
                        alarmButton.setBackgroundColor(Color.RED);
                        alarmButton.setText(R.string.alarm_button_disarmed);
                        break;
                }
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

}
