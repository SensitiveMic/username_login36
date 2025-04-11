package com.example.usernamelogin.Gym_Owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Gym_Owner_Gym_Management extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, Gym_management, profile;
    public static String[] ProfileContents;
    String pushkey;
    public static String key1,key2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_owner_gym_management);
        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        Gym_management = findViewById(R.id.Gym_manage_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);
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
                redirectActivity(Gym_Owner_Gym_Management.this, Gym_Owner_Main.class);
            }
        });
        Gym_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Gym_Owner_Gym_Management.this, Profile_Main_Gym_Owner.class);
            }
        });

    }

    public void usertoolbarname(Context context, TextView usernamebar, TextView username_nav) {
        DatabaseReference databaseReferenceNon = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child("Gym_Owner");
        Query checkname = databaseReferenceNon.orderByChild("gym_owner_username");
        pushkey = Login.key_GymOwner;
        Log.d("TAG3", pushkey);

        checkname.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProfileContents = new String[5];
                ProfileContents[0] = dataSnapshot.child(pushkey).child("gym_owner_username").getValue(String.class);
                ProfileContents[1] = dataSnapshot.child(pushkey).child("email").getValue(String.class);
                ProfileContents[2] = dataSnapshot.child(pushkey).child("password").getValue(String.class);
                for (DataSnapshot gk : dataSnapshot.getChildren()) {
                    key1 = gk.getKey();
                    DataSnapshot gymSnapshot = gk.child("Gym");
                    for (DataSnapshot gymChildSnapshot : gymSnapshot.getChildren()) {
                        key2 = gymChildSnapshot.getKey();

                        ProfileContents[3] = gymSnapshot.child(key2).child("gym_name").getValue(String.class);
                        ProfileContents[4] = gymSnapshot.child(key2).child("gym_descrp").getValue(String.class);
                        // Break the loop as we only need to process one gym name
                        break;
                    }
                }
                // Update UI elements using the provided context and TextViews
                usernamebar.setText(ProfileContents[0]);
                username_nav.setText(ProfileContents[0]);

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
                findViewById(R.id.username_nav));

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


}