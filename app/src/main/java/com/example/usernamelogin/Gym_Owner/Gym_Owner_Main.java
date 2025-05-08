package com.example.usernamelogin.Gym_Owner;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.usernamelogin.Admin.Admin_main;
import com.example.usernamelogin.Gym_Owner.employeelist.employeelists_main;
import com.example.usernamelogin.R;

import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Gym_Owner_Main extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, Gym_management, profile ,gymemployyes, logoput;
    public static String[] ProfileContents;
    String pushkey;
    public static String key1,key2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_owner_main);

        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        Gym_management = findViewById(R.id.Gym_manage_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);
        gymemployyes = findViewById(R.id.GYymemployyelist);
        logoput = findViewById(R.id.logout_Button_U);

        someMethod();
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
        Gym_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Gym_Owner_Main.this, Gym_Owner_gymmanagement_add_staff.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Gym_Owner_Main.this, Profile_Main_Gym_Owner.class);
            }
        });
        gymemployyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Gym_Owner_Main.this, employeelists_main.class);
            }
        });
        logoput.setOnClickListener(v ->{
            logout_prc(Gym_Owner_Main.this, Login.class);

        });

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

   public void usertoolbarname(Context context, TextView usernamebar, TextView username_nav
   ,TextView navbar_gym) {
        DatabaseReference databaseReferenceNon = FirebaseDatabase.getInstance()
                                                    .getReference("Users")
                                                    .child("Gym_Owner");
        Query checkname = databaseReferenceNon;
        pushkey = Login.key_GymOwner;
        Log.d("TAG3", pushkey);
       DatabaseReference databaseReferenceNon1 = FirebaseDatabase.getInstance()
               .getReference("Users")
               .child("Gym_Owner").child(pushkey);
        checkname.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ProfileContents = new String[7];
                ProfileContents[0] = dataSnapshot.child(pushkey).child("gym_owner_username").getValue(String.class);
                ProfileContents[1] = dataSnapshot.child(pushkey).child("gym_owner_email").getValue(String.class);
                ProfileContents[2] = dataSnapshot.child(pushkey).child("gym_owner_password").getValue(String.class);
                ProfileContents[5] = dataSnapshot.child(pushkey).child("gym_owner_firstname").getValue(String.class );
                ProfileContents[6] = dataSnapshot.child(pushkey).child("gym_owner_lastname").getValue(String.class);

                databaseReferenceNon1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot gk : snapshot.getChildren()) {
                            key1 = gk.getKey();
                            DataSnapshot gymSnapshot = gk.child("Gym");
                            Log.d("TAG3", "This is key 1 "+ key1);

                            for (DataSnapshot gymChildSnapshot : gk.getChildren()) {
                                key2 = gymChildSnapshot.getKey();

                                Log.d("TAG3", "This is key 2 "+ key2);
                                ProfileContents[3] = gk.child(key2).child("gym_name").getValue(String.class);
                                Log.d("TAG3", "This is gym_name  "+ ProfileContents[3]);
                                ProfileContents[4] = gk.child(key2).child("gym_descrp").getValue(String.class);
                                Log.d("TAG3", "This is gym_descrp "+ ProfileContents[4]);
                                // Break the loop as we only need to process one gym name
                                break;
                            }
                            break;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                // Update UI elements using the provided context and TextViews
                usernamebar.setText(ProfileContents[0]);
                username_nav.setText(ProfileContents[0]);
                navbar_gym.setText(ProfileContents[3]);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity_wrkt_prgrm", "Failed to read value.", databaseError.toException());
            }
        });


    }
    public void someMethod() {
        // Call usertoolbarname() with appropriate arguments
        usertoolbarname(getApplicationContext(),
                findViewById(R.id.textView2),
                findViewById(R.id.username_nav),
                findViewById(R.id.Gym_name_navdrawer));
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


}