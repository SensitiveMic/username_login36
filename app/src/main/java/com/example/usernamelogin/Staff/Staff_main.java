package com.example.usernamelogin.Staff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.usernamelogin.Gym_Owner.Gym_Owner_Main;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.example.usernamelogin.Staff.Gym_Management.Gym_management_main;
import com.example.usernamelogin.Staff.Membership_req_management.Membership_requests_main;
import com.example.usernamelogin.Staff.Memberslist.Staff_mem_list_main;
import com.example.usernamelogin.Staff.Memberslist.members_not_exp.Model_class_get_members_details;
import com.example.usernamelogin.Staff.Profile_Staff.Staff_Profile_Main;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Staff_main extends AppCompatActivity{
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, reservations, profile,member_lists, logoput;

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
        member_lists = findViewById(R.id.Gym_manage_members);
        logoput = findViewById(R.id.logout_Button_U);

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
        member_lists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(Staff_main.this, Staff_mem_list_main.class);
            }
        });
        logoput.setOnClickListener(v ->{
            logout_prc(Staff_main.this, Login.class);

        });

          //---- this method changes membership based on expiration---
      // Membership_requests_main.checkMembershipExpirations();
        expiredmembersremoval();
    }
    private void logout_prc(Activity activity, Class secondActivity){

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(activity, secondActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
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

    public void usertoolbarname(Context context, TextView usernamebar, TextView username_nav, TextView gymnav) {
                                    ProfileContents = new String[5];
                                    ProfileContents[0] = Login.key_Gym_Staff_username;
                                    ProfileContents[1] = Login.key_Gym_Staff_email;
                                    ProfileContents[2] = Login.key_Gym_Staff_password;
                                    ProfileContents[3] = Login.key_Gym_Staff_mobile_number;
                                    ProfileContents[4] = Login.key_Gym_;
                                    Log.d("TAG6", "username :" + ProfileContents[0]);
                                    Log.d("TAG6", "email :" + ProfileContents[1]);
                                    Log.d("TAG6", "password :" + ProfileContents[2]);
                                    Log.d("TAG6", "mob :" + ProfileContents[3]);

                // Update UI elements using the provided context and TextViews
                usernamebar.setText(ProfileContents[0]);
                username_nav.setText(ProfileContents[0]);
                gymnav.setText(ProfileContents[4]);
    }
    public void someMethod() {
        // Call usertoolbarname() with appropriate arguments
        usertoolbarname(getApplicationContext(),
                findViewById(R.id.textView2),
                findViewById(R.id.username_nav),
                findViewById(R.id.textView));
    }

    private void expiredmembersremoval(){

        DatabaseReference checkmembers = FirebaseDatabase.getInstance().getReference("Users/Non-members");
        checkmembers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1: snapshot.getChildren()){

                  String gymName = snapshot1.child("GymName").getValue(String.class);

                    if (gymName != null && gymName.equals(Login.key_Gym_)){
                        DataSnapshot membershipSnapshot = snapshot1.child("membership");
                        String username = snapshot1.child("username").getValue(String.class);
                        if (membershipSnapshot.exists()) {
                            String expirationDate = snapshot1.child("membership").child("expiration_date").getValue(String.class);
                            if (expirationDate != null && isDateExpired(expirationDate)) {

                                snapshot1.getRef().child("membership").removeValue();
                                snapshot1.getRef().child("GymName").removeValue();
                                snapshot1.getRef().child("positionstored").removeValue();
                                snapshot1.getRef().child("Coach_Reservation").removeValue();

                                // Set membership_status to 0
                                snapshot1.getRef().child("membership_status").setValue(0);

                                Log.d("TAGEXPIRED_REMOVED", "user_unmembered: " +username);

                            }

                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public static boolean isDateExpired(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM dd yy", Locale.getDefault());
        sdf.setLenient(false);

        try {
            Date inputDate = sdf.parse(dateString);
            Date currentDate = new Date();
            return inputDate.before(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

}