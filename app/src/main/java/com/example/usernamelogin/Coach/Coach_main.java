package com.example.usernamelogin.Coach;

import androidx.annotation.NonNull;
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

import com.example.usernamelogin.Coach.Profile.Coach_Profile_Main;
import com.example.usernamelogin.Coach.Snd_wrkout.sent_workouts.Fragment_sent_workouts;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Coach_main extends AppCompatActivity implements interface_click_pendingresfrm_gym_members {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home,  profile;
    public static String member_pushid, member_name;
    public static String activeres_member_pushid;
    public static String selected_longclick;
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
                        fragment =  new Fragment_current_pending_coach_res_coach_main();
                        break;
                    case 2:
                        fragment = new Fragment_sent_workouts();
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

    }

    @Override
    protected void onPause() {
        super.onPause();
        closeNavbar(drawerLayout);
    }

    public void usertoolbarname(Context context, TextView usernamebar, TextView username_nav) {

        DatabaseReference myRefprofile = FirebaseDatabase.getInstance().getReference("Users/Gym_Owner")
                .child(Login.key_Gym_Coach1)
                .child(Login.key_Gym_Coach2)
                .child(Login.key_Gym_Coach3)
                .child("Coach")
                .child(Login.key_Gym_Coach_key);

        ProfileContents = new String[4];

        myRefprofile.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ProfileContents[0] = dataSnapshot.child("username").getValue(String.class);
                ProfileContents[1] = dataSnapshot.child("email").getValue(String.class);
                ProfileContents[2] = dataSnapshot.child("password").getValue(String.class);
                ProfileContents[3] = dataSnapshot.child("mobile_number").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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