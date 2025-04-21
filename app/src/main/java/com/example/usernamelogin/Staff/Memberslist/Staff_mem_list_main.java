package com.example.usernamelogin.Staff.Memberslist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.example.usernamelogin.Staff.Gym_Management.Gym_management_main;
import com.example.usernamelogin.Staff.Memberslist.members_exp.Fragment_expired_members;
import com.example.usernamelogin.Staff.Memberslist.members_not_exp.Fragment_members_list;
import com.example.usernamelogin.Staff.Profile_Staff.Staff_Profile_Main;
import com.example.usernamelogin.Staff.Staff_main;
import com.google.android.material.tabs.TabLayout;

public class Staff_mem_list_main extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, reservations, profile,member_lists;
    FrameLayout frameLayoutttt;
    TabLayout kasibagTab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_staff_mem_list_main);

        someMethod();
        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        reservations = findViewById(R.id.Gym_manage_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);
        member_lists = findViewById(R.id.Gym_manage_members);

        frameLayoutttt = findViewById(R.id.framelayout1);
        kasibagTab = findViewById(R.id.tablayou);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout1, new Fragment_members_list())
                .addToBackStack(null)
                .commit();

        kasibagTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Fragment Please = null;
                String tagtea34 = null;

                switch (tab.getPosition()) {
                    case 0:
                        tagtea34 = "Staff_check_mem";
                        Please = getSupportFragmentManager().findFragmentByTag(tagtea34);
                        if (Please == null) {
                            Please = new Fragment_members_list();
                        }
                        break;

                    case 1:
                        tagtea34 = "Staff_check_exp";
                        Please = getSupportFragmentManager().findFragmentByTag(tagtea34);

                        if (Please == null) {
                            Please = new Fragment_expired_members();
                        }
                        break;
                }

                // Replace the fragment and add it to the back stack with a tag
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout1, Please, tagtea34)
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

        TabLayout.Tab tab1 = kasibagTab.newTab().setText("Current Members");
        kasibagTab.addTab(tab1);

        TabLayout.Tab tab2 = kasibagTab.newTab().setText("Expired Members");
        kasibagTab.addTab(tab2);


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavbar(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Staff_mem_list_main.this, Staff_main.class);
            }
        });
        reservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Staff_mem_list_main.this, Gym_management_main.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Staff_mem_list_main.this, Staff_Profile_Main.class);
            }
        });
        member_lists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });

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

        // Update UI elements using the provided context and TextViews
        usernamebar.setText(Login.key_Gym_Staff_username);
        username_nav.setText(Login.key_Gym_Staff_username);
    }
    public void someMethod() {
        // Call usertoolbarname() with appropriate arguments
        usertoolbarname(getApplicationContext(),
                findViewById(R.id.textView2),
                findViewById(R.id.username_nav));
    }
}