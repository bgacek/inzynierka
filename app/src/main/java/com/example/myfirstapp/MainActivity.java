package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

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
                        //menuItem.setChecked(true);
                        myDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        mFragmentManager = getSupportFragmentManager();



        loadSelection(0);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        myToggle.syncState();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(myToggle.onOptionsItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);
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

        }
    }

}

