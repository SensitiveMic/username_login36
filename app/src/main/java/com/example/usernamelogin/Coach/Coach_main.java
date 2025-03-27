package com.example.usernamelogin.Coach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.example.usernamelogin.Coach.Profile.Coach_Profile_Main;
import com.example.usernamelogin.Member.Reservation.Current_Coach_Res.Fragment_Current_Reservations;
import com.example.usernamelogin.Member.Reservation.Current_Coach_Res.Fragment_Reservation_Logs;
import com.example.usernamelogin.NonMemberUser.NonMemberUSER;
import com.example.usernamelogin.NonMemberUser.Reservations.Gymchoosing.interface_for_recyclerviewAdapter;
import com.example.usernamelogin.NonMemberUser.Reservations.recyclerviewAdapter;
import com.example.usernamelogin.NonMemberUser.Reservations.recycleviewReservationlist;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.example.usernamelogin.Staff.Membership_req_management.Model_class_mmbershpr;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Coach_main extends AppCompatActivity implements interface_click_pendingresfrm_gym_members {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home,  profile;
    public static String member_pushid, member_name;
    FrameLayout frameLayout;
    TabLayout tabLayout;

    public static String[] ProfileContents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_main);
        someMethod();

        frameLayout = (FrameLayout) findViewById(R.id.framelayout3);
        tabLayout = (TabLayout) findViewById(R.id.tablayout3);

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout3, new Fragment_Current_Active_Coach_reservation())
                .addToBackStack(null)
                .commit();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Fragment fragment = null;
                switch (tab.getPosition()){

                    case 0:
                        fragment = new Fragment_Current_Active_Coach_reservation() ;
                        break;
                    case 1:
                        fragment =  new Fragment_current_coach_res_coach_main();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout3, fragment)
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

        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);

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
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Coach_main.this, Coach_Profile_Main.class);
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
        ProfileContents = new String[4];
        ProfileContents[0] = Login.key_Gym_Coach_username;
        ProfileContents[1] = Login.key_Gym_Coach_email;
        ProfileContents[2] = Login.key_Gym_Coach_password;
        ProfileContents[3] = Login.key_Gym_Coach_mobile_number;
        Log.d("TAG6", "username :" + ProfileContents[0]);
        Log.d("TAG6", "email :" + ProfileContents[1]);
        Log.d("TAG6", "password :" + ProfileContents[2]);
        Log.d("TAG6", "mobilenumber :" + ProfileContents[3]);
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


    @Override
    public void onItemClick(int position) {
        dialog_clicked_pendingmemberres();
    }
    public void dialog_clicked_pendingmemberres(){

        dialog_for_clicked_pen_members listDialog = new dialog_for_clicked_pen_members(this) {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
            }
        };
        listDialog.show();
    }
}