package com.example.usernamelogin.Member;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usernamelogin.Member.Gym_Info.Member_Gym_info_main;
import com.example.usernamelogin.Member.Reservation.Coach_Reservation_main;
import com.example.usernamelogin.Member.Reservation.Current_Coach_Res.Current_Coach_Res_Main;
import com.example.usernamelogin.Member.Reservation.Current_Coach_Res.Modelclass_for_current_member_res_accepted;
import com.example.usernamelogin.NonMemberUser.Gym_prop.Gym_Properties_Main;
import com.example.usernamelogin.NonMemberUser.Profile;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.example.usernamelogin.workout_program.to_edit_reps_weight.Modelclass_forexercises;
import com.example.usernamelogin.workout_program.workouts.User_workouts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Triple;

public class Member_main extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, reservations, profile, gym_membership, currentreservations, workout;
    public static String[] ProfileContents;
    public static String Current_GYM;
    String pushkey;
    Query checkname;
    String time1,date1,Coach_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_main);

        someMethod();

        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        reservations = findViewById(R.id.Reservations_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);
        gym_membership = findViewById(R.id.Gym_navdrawer);
        currentreservations = findViewById(R.id.current_res_coach);
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
                redirectActivity(Member_main.this, Coach_Reservation_main.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Member_main.this, Member_Profile.class);
            }
        });
        gym_membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Member_main.this, Member_Gym_info_main.class);
            }
        });
        currentreservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Member_main.this, Current_Coach_Res_Main.class);
            }
        });
        workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // getsent_workouttovalue();
                Log.d("COACH_WRKT_SENT_TAG", "Coach workout Sent!");
                int workoutId = 1;
                Intent intent = new Intent(Member_main.this, User_workouts.class);
                intent.putExtra("workout_id", workoutId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Log.d("ClickedTAG?", "TRUE");
            }
        });

        DatabaseReference gotomemberscurrentres = FirebaseDatabase.getInstance()
                .getReference("Users").child("Non-members").child(Login.key)
                .child("Coach_Reservation").child("Current_Accepted_Res");

      //  expired_coach_res_check();

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
                ProfileContents[1] = dataSnapshot.child(pushkey).child("email").getValue(String.class);
                ProfileContents[2] = dataSnapshot.child(pushkey).child("password").getValue(String.class);
                ProfileContents[3] = dataSnapshot.child(pushkey).child("mobile").getValue(String.class);

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
    private void expired_coach_res_check(){

        DatabaseReference gotomemberscurrentres = FirebaseDatabase.getInstance()
                .getReference("Users").child("Non-members").child(Login.key)
                .child("Coach_Reservation").child("Current_Accepted_Res");

        gotomemberscurrentres.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot gk: snapshot.getChildren()){

                        Modelclass_for_current_member_res_accepted item = gk.getValue(Modelclass_for_current_member_res_accepted.class);
                        String thiskey = gk.getKey();
                        date1 = item.getGym_meet_date().toString();
                        time1 = item.getGym_meet_time().toString();
                        Coach_name = item.getCoach_name().toString();

                        if (logcompletedreservations(date1, time1)) {
                            Log.d("TAG EXPCHECK", "EXPIRED " );
                            DatabaseReference gotomemberscurrentres1 = FirebaseDatabase.getInstance()
                                    .getReference("Users").child("Non-members").child(Login.key)
                                    .child("Coach_Reservation").child("Completed_res_logs").push();

                            gotomemberscurrentres1.setValue(item);
                            DatabaseReference removal = gotomemberscurrentres.child(thiskey);

                            removal.removeValue();

                        } else {

                            Log.d("TAG EXPCHECK", "notexpired " );
                        }

                    }

                }
                else{
                    Log.d("TAG EXPCHECK", "NO ACTIVE RESERVATIONS " );

                    Toast.makeText(Member_main.this, "No data found for this user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private static boolean logcompletedreservations(String dateString, String timeString){

        // Define the formatters for date and time
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");

        // Parse the date and time strings
        LocalDateTime dateTime = LocalDateTime.parse(dateString + " " + timeString,
                DateTimeFormatter.ofPattern("M/d/yyyy H:mm"));

        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Compare the parsed date and time with the current date and time
        return now.isAfter(dateTime);

    }



}