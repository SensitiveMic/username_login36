package com.example.usernamelogin.NonMemberUser;

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

import com.example.usernamelogin.Member.Member_main;
import com.example.usernamelogin.NonMemberUser.Gym_prop.Gym_Properties_Main;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.example.usernamelogin.NonMemberUser.Reservations.Reservations;
import com.example.usernamelogin.workout_program.workouts.User_workouts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class NonMemberUSER extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, reservations, profile, gym_membership,workout;
    public static String[] ProfileContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_member_user);

        someMethod();

        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        reservations = findViewById(R.id.Reservations_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);
        gym_membership = findViewById(R.id.Gym_navdrawer);
        workout = findViewById(R.id.member_workout);

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
                redirectActivity(NonMemberUSER.this, Reservations.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(NonMemberUSER.this, Profile.class);
            }
        });
        gym_membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(NonMemberUSER.this, Gym_Properties_Main.class);
            }
        });
        workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("COACH_WRKT_SENT_TAG", "Coach workout Sent!");
                Intent intent = new Intent(NonMemberUSER.this, User_workouts.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

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
                ProfileContents = new String[4];
                ProfileContents[0] = dataSnapshot.child(pushkey).child("username").getValue(String.class);
                Log.d("TAG35","username " + ProfileContents[0]);
                ProfileContents[1] = dataSnapshot.child(pushkey).child("email").getValue(String.class);
                Log.d("TAG35","email " + ProfileContents[1]);
                ProfileContents[2] = dataSnapshot.child(pushkey).child("password").getValue(String.class);
                Log.d("TAG35","password " + ProfileContents[2]);
                ProfileContents[3] = dataSnapshot.child(pushkey).child("mobile").getValue(String.class);
                Log.d("TAG35","mobile " + ProfileContents[3]);
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