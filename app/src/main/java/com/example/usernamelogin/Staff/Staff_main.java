package com.example.usernamelogin.Staff;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.example.usernamelogin.Staff.Gym_Management.Gym_management_main;
import com.example.usernamelogin.Staff.Membership_req_management.Membership_requests_main;
import com.example.usernamelogin.Staff.Profile_Staff.Staff_Profile_Main;
import com.google.android.material.tabs.TabLayout;

public class Staff_main extends AppCompatActivity{
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, reservations, profile;

    public static String[] ProfileContents;
    FrameLayout frameLayoutttt;
    TabLayout tabLayout1wew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_main);

        someMethod();
        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        reservations = findViewById(R.id.Gym_manage_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);

        frameLayoutttt = findViewById(R.id.framelayout1);
        tabLayout1wew = findViewById(R.id.tablayout);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout1, new Fragment_pending())
                .addToBackStack(null)
                .commit();

        tabLayout1wew.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Fragment fragmentwew = null;
                String tagtea = null;

                switch (tab.getPosition()) {
                    case 0:
                        tagtea = "pending_non_member_res";
                        fragmentwew = getSupportFragmentManager().findFragmentByTag(tagtea);
                        if (fragmentwew == null) {
                            fragmentwew = new Fragment_pending();
                        }
                        break;

                    case 1:
                        tagtea = "accepted_non_member_res";
                        fragmentwew = getSupportFragmentManager().findFragmentByTag(tagtea);

                        if (fragmentwew == null) {
                            fragmentwew = new Fragment_accepted_req();
                        }
                        break;
                }

                // Replace the fragment and add it to the back stack with a tag
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout1, fragmentwew, tagtea)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        TabLayout.Tab tab1 = tabLayout1wew.newTab().setText("Pending Reservations");
        tabLayout1wew.addTab(tab1);

        TabLayout.Tab tab2 = tabLayout1wew.newTab().setText("Accepted Reservations");
        tabLayout1wew.addTab(tab2);



        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavbar(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        reservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Staff_main.this, Gym_management_main.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Staff_main.this, Staff_Profile_Main.class);
            }
        });

          //---- this method changes membership based on expiration---
      // Membership_requests_main.checkMembershipExpirations();
    }

    public static void openNavbar(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeNavbar(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public static void redirectActivity(Activity activity, Class secondActivity) {
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeNavbar(drawerLayout);
    }

    public void usertoolbarname(Context context, TextView usernamebar, TextView username_nav) {
                                    ProfileContents = new String[4];
                                    ProfileContents[0] = Login.key_Gym_Staff_username;
                                    ProfileContents[1] = Login.key_Gym_Staff_email;
                                    ProfileContents[2] = Login.key_Gym_Staff_password;
                                    ProfileContents[3] = Login.key_Gym_Staff_mobile_number;
                                    Log.d("TAG6", "username :" + ProfileContents[0]);
                                    Log.d("TAG6", "email :" + ProfileContents[1]);
                                    Log.d("TAG6", "password :" + ProfileContents[2]);
                                    Log.d("TAG6", "mob :" + ProfileContents[3]);

                // Update UI elements using the provided context and TextViews
                usernamebar.setText(ProfileContents[0]);
                username_nav.setText(ProfileContents[0]);
    }
    public void someMethod() {
        // Call usertoolbarname() with appropriate arguments
        usertoolbarname(getApplicationContext(),
                findViewById(R.id.textView2),
                findViewById(R.id.username_nav));
    }


}