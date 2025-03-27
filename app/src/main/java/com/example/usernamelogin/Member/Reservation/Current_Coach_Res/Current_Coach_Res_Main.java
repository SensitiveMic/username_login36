package com.example.usernamelogin.Member.Reservation.Current_Coach_Res;

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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.usernamelogin.Member.Member_Profile;
import com.example.usernamelogin.Member.Member_main;
import com.example.usernamelogin.Member.Reservation.Coach_Reservation_main;
import com.example.usernamelogin.NonMemberUser.Gym_prop.Apply_a_gym_fragment;
import com.example.usernamelogin.NonMemberUser.Gym_prop.Gym_Properties_Main;
import com.example.usernamelogin.NonMemberUser.Gym_prop.PendingGymApplications_fragment;
import com.example.usernamelogin.NonMemberUser.Profile;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Current_Coach_Res_Main extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, reservations, profile, gym_membership, currentreservations;
    public static String[] ProfileContents;
    FrameLayout frameLayout;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_coach_res_main);
        someMethod();

        frameLayout = (FrameLayout) findViewById(R.id.framelayout2);
        tabLayout = (TabLayout) findViewById(R.id.tablayout2);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout2, new Fragment_Current_Reservations())
                .addToBackStack(null)
                .commit();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Fragment fragment = null;
                String tag = null;
                switch (tab.getPosition()){

                    case 0:
                        tag = "Current_res";
                        fragment = getSupportFragmentManager().findFragmentByTag(tag);
                        if (fragment == null) {
                            fragment = new Fragment_Current_Reservations();
                        }
                        break;

                    case 1:
                        tag = "Reserv_logs";
                        fragment = getSupportFragmentManager().findFragmentByTag(tag);

                        if (fragment == null) {
                            fragment = new Fragment_Reservation_Logs();
                        }
                        break;

                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout2, fragment, tag)
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



        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        reservations = findViewById(R.id.Reservations_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);
        gym_membership = findViewById(R.id.Gym_navdrawer);
        currentreservations = findViewById(R.id.current_res_coach);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavbar(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Current_Coach_Res_Main.this, Member_main.class);
            }
        });
        reservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Current_Coach_Res_Main.this, Coach_Reservation_main.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Current_Coach_Res_Main.this, Member_Profile.class);
            }
        });
     /*     gym_membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Member_main.this, Gym_Properties_Main.class);
            }
        }); */
        currentreservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });



    }
    public void usertoolbarname(Context context, TextView usernamebar, TextView username_nav) {
        DatabaseReference databaseReferenceNon = FirebaseDatabase.getInstance()
                .getReference("Users").child("Non-members");
        Query checkname = databaseReferenceNon.orderByChild("username");
        String pushkey = Login.key;
        Log.d("TAG3", pushkey);

        checkname.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProfileContents = new String[3];
                ProfileContents[0] = dataSnapshot.child(pushkey).child("username").getValue(String.class);
                ProfileContents[1] = dataSnapshot.child(pushkey).child("email").getValue(String.class);
                ProfileContents[2] = dataSnapshot.child(pushkey).child("password").getValue(String.class);

                // Update UI elements using the provided context and TextViews

                usernamebar.setText(ProfileContents[0]);
                username_nav.setText(ProfileContents[0]);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", "Failed to read value.", databaseError.toException());
            }
        });
    }
    public void someMethod() {
        // Call usertoolbarname() with appropriate arguments
        usertoolbarname(getApplicationContext(),
                findViewById(R.id.textView2),
                findViewById(R.id.username_nav));
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
