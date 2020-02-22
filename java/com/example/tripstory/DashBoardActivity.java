package com.example.tripstory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.google.android.material.tabs.TabLayout;

import java.net.URI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;

import androidx.viewpager.widget.ViewPager;

public class DashBoardActivity extends AppCompatActivity
{
 //       FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        //private OnAboutDataReceivedListener mAboutDataListener;
        ViewPagerAdapter tabsPagerAdapter = new ViewPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(tabsPagerAdapter);
        TabLayout tabs = findViewById(R.id.ptabs);
        tabs.setupWithViewPager(viewPager);


        // menu should be considered as top level destinations.

        SharedPreferences sh
                = getSharedPreferences("MySharedPref",
                MODE_PRIVATE);
        // String useremail  myin.getStringExtra("Useremail");
        // String userst=myin.getStringExtra("Userstatus");
        boolean is_logged_in = sh.getBoolean("LOGGED_IN", false);
       // Bundle bundle = new Bundle();
        if (is_logged_in) {

           sendData();

        }

//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.navigation_jobsearch)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        navController.setGraph(navController.getGraph(),bundle);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
//    }

    }

    public Bundle sendData(){
        //String userid = sh.getString("USERID", "user");
        Intent din = getIntent();
        String email = din.getStringExtra("email");
        String name = din.getStringExtra("name");
        String mob = din.getStringExtra("mobile");
        String dob = din.getStringExtra("dob");
        String img = din.getStringExtra("image");
        String linkd = din.getStringExtra("linkedin");
        String addrs = din.getStringExtra("address");
        String bio = din.getStringExtra("bio");

      setTitle(name);
        //send to fragment
        // sendData(name);
        Bundle bundle=new Bundle();
        bundle.putString("name", name);
        bundle.putString("email", email);
        bundle.putString("mobile", mob);
        bundle.putString("dob", dob);
        bundle.putString("image", img);
        bundle.putString("linkd", linkd);
        bundle.putString("address", addrs);
        bundle.putString("bio", bio);

        return bundle;
    }

    public interface OnFragmentInteractionListener {
        void OnNavFragmentInteractionListener(Uri uri);

    }



    }
