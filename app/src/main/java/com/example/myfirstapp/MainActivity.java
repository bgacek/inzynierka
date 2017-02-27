package com.example.myfirstapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout myDrawerLayout;
    private ActionBarDrawerToggle myToggle;
    private FragmentTransaction mFragmentTransaction;
    private FragmentManager mFragmentManager;
    private NavigationView mNavigationView;
    private Integer navButtonId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        myToggle = new ActionBarDrawerToggle(this, myDrawerLayout, R.string.open, R.string.close);
        myDrawerLayout.addDrawerListener(myToggle);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mNavigationView = (NavigationView) findViewById(R.id.navView);
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        navButtonId = menuItem.getItemId();
                        loadSelection(navButtonId);
                        menuItem.setChecked(true);
                        myDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        mFragmentManager = getSupportFragmentManager();

        int notificationSelector = getIntent().getIntExtra("selector", R.id.nav_home);
        loadSelection(notificationSelector);
        bellNotify();

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        myToggle.syncState();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item) || myToggle.onOptionsItemSelected(item);
    }


    private void loadSelection(int position) {

        switch (position) {
            case R.id.nav_home:
                HomeFragment mHomeFragment = new HomeFragment();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.fragmentHolder, mHomeFragment);
                mFragmentTransaction.commit();
                break;

            case R.id.nav_camera1:
                Camera1Fragment mCamera1Fragment = new Camera1Fragment();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.fragmentHolder, mCamera1Fragment);
                mFragmentTransaction.commit();
                break;

            case R.id.nav_camera2:
                Camera2Fragment mCamera2Fragment = new Camera2Fragment();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.fragmentHolder, mCamera2Fragment);
                mFragmentTransaction.commit();
                break;


        }
    }


    private void ultraSoundSensor1Notify() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, MainActivity.class);
        mIntent.putExtra("selector", R.id.nav_camera1);
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), mIntent, 0);

        Notification mNotification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_photo_camera_black_24dp)
                .setContentTitle("ULTRASOUND SENSOR 1 TRIGGERED!!!")
                .setContentText("Ultrasound sensor 1 detected movement, click to get Camera 1 display")
                .setContentIntent(mPendingIntent)
                .setShowWhen(true)
                .setAutoCancel(true)
                .build();

        mNotificationManager.notify(0, mNotification);
    }


    private void ultraSoundSensor2Notify() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, MainActivity.class);
        mIntent.putExtra("selector", R.id.nav_camera1);
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), mIntent, 0);

        Notification mNotification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_photo_camera_black_24dp)
                .setContentTitle("ULTRASOUND SENSOR 2 TRIGGERED!!!")
                .setContentText("Ultrasound sensor 2 detected movement, click to get Camera 1 display")
                .setContentIntent(mPendingIntent)
                .setShowWhen(true)
                .setAutoCancel(true)
                .build();

        mNotificationManager.notify(0, mNotification);
    }


    private void camera2Notify() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, MainActivity.class);
        mIntent.putExtra("selector", R.id.nav_camera2);
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), mIntent, 0);

        Notification mNotification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_photo_camera_black_24dp)
                .setContentTitle("Camera 2 TRIGGERED!!!")
                .setContentText("Camera 2 detected movement, click to get Camera 1 display")
                .setContentIntent(mPendingIntent)
                .setShowWhen(true)
                .setAutoCancel(true)
                .build();

        mNotificationManager.notify(0, mNotification);
    }


    private void window1Notify() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, MainActivity.class);
        mIntent.putExtra("selector", R.id.nav_home);
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), mIntent, 0);

        Notification mNotification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_filter_1_black_24dp)
                .setContentTitle("WINDOW 1 BREACHED!!!")
                .setContentText("Window 1 breached, click to open app")
                .setContentIntent(mPendingIntent)
                .setShowWhen(true)
                .setAutoCancel(true)
                .build();

        mNotificationManager.notify(0, mNotification);
    }


    private void window2Notify() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, MainActivity.class);
        mIntent.putExtra("selector", R.id.nav_home);
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), mIntent, 0);

        Notification mNotification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_filter_2_black_24dp)
                .setContentTitle("WINDOW 2 BREACHED!!!")
                .setContentText("Window 2 breached, click to open app")
                .setContentIntent(mPendingIntent)
                .setShowWhen(true)
                .setAutoCancel(true)
                .build();

        mNotificationManager.notify(0, mNotification);
    }


    private void bellNotify() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification mNotification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_notifications_active_black_24dp)
                .setContentTitle("SOMEONE AT THE DOOR!!!")
                .setContentText("Your bell ringing, some wants to visit you")
                .setLights(Color.RED, 3000, 3000)
                .setVibrate(new long[] {1000, 1000, 1000, 1000})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setShowWhen(true)
                .setAutoCancel(true)
                .build();

        mNotificationManager.notify(0, mNotification);
    }


}

