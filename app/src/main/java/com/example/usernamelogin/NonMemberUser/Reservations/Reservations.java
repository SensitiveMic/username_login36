package com.example.usernamelogin.NonMemberUser.Reservations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.usernamelogin.NonMemberUser.Gym_prop.Gym_Properties_Main;
import com.example.usernamelogin.NonMemberUser.NonMemberUSER;
import com.example.usernamelogin.NonMemberUser.Profile;
import com.example.usernamelogin.R;

import com.google.android.material.tabs.TabLayout;

public class Reservations extends AppCompatActivity{
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, reservations, profile,gym_membership;
    FrameLayout frameLayout;
    TabLayout tabLayout;
    private TextView[] textViews;
    public static String mobilenumberofgyminres;
    public static int notificationCount;
    TextView badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        // set toolbar and navbar name to users username
        TextView textView2 = findViewById(R.id.textView2);
        TextView username_nav = findViewById(R.id.username_nav);
        textView2.setText(NonMemberUSER.ProfileContents[0]);
        username_nav.setText(NonMemberUSER.ProfileContents[0]);


        // Whole layout
        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        reservations = findViewById(R.id.Reservations_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);
        gym_membership = findViewById(R.id.Gym_navdrawer);

        frameLayout = (FrameLayout) findViewById(R.id.framelayout1);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout1, new fragment_current_reservations_Fragment())
                .addToBackStack(null)
                .commit();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Fragment fragment = null;
                switch (tab.getPosition()){

                    case 0:
                        fragment = new fragment_current_reservations_Fragment();
                        break;
                    case 1:
                        fragment = new fragment_paid_reservations();
                        break;


                }

                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout1, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        TabLayout.Tab tab1 = tabLayout.newTab().setText("Reservations");
        tabLayout.addTab(tab1);

        TabLayout.Tab tab2 = tabLayout.newTab().setText("Recent Paid Reservations");
        tabLayout.addTab(tab2);

        View customTabView = LayoutInflater.from(this).inflate(R.layout.custom_tab_unpaid_reservations, null);
        tab1.setCustomView(customTabView);
        badge = customTabView.findViewById(R.id.notificationBadge);



        Log.d("TAG77", "THISIS notif =" + notificationCount);

      //  notificationCount = 5; // Example notification count


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavbar(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Reservations.this, NonMemberUSER.class);
            }
        });
        reservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Reservations.this, Profile.class);
            }
        });

        gym_membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Reservations.this, Gym_Properties_Main.class);
            }
        });

        textViews = new TextView[7];
        textViews[0] = findViewById(R.id.day1);
        textViews[1] = findViewById(R.id.day2);
        textViews[2] = findViewById(R.id.day3);
        textViews[3] = findViewById(R.id.day4);
        textViews[4] = findViewById(R.id.day5);
        textViews[5] = findViewById(R.id.day6);
        textViews[6] = findViewById(R.id.day7);


    }


    public void updateNotificationCount(int count) {
        notificationCount = count;
        updateNotificationBadge(notificationCount);
    }

    public void updateNotificationBadge(int count) {

        if (count > 0) {
            badge.setVisibility(View.VISIBLE);
            badge.setText(String.valueOf(count));
        } else {
            badge.setVisibility(View.GONE);
        }

        Log.d("TAG77", "THISIS notif coun2 =" + count);
    }

    public static void openNavbar(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public static void closeNavbar(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
    @Override
    protected void onPause(){
        super.onPause();
        closeNavbar(drawerLayout);
    }

}