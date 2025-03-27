package com.example.usernamelogin.NonMemberUser.Reservations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usernamelogin.NonMemberUser.NonMemberUSER;
import com.example.usernamelogin.NonMemberUser.Profile;
import com.example.usernamelogin.NonMemberUser.Reservations.Gymchoosing.Modelclass;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.example.usernamelogin.RegisterandLogin.MainActivity;
import com.example.usernamelogin.Users;
import com.example.usernamelogin.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Add_Reservations extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    ActivityMainBinding binding;
    LinearLayout home, reservations, profile;
    private TextView[] textViews;
    private Calendar startDate;
    private Calendar endDate;
    FirebaseDatabase GymDatabase;
    DatabaseReference GymRef;
    DatabaseReference GymRef_ID ;
    FirebaseDatabase database;
    public static String gymnamefromresdialoggymlist,gym_contact_numberforview;
    Integer gymopening, gymclosing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservations);

        database = FirebaseDatabase.getInstance();

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

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavbar(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Add_Reservations.this, NonMemberUSER.class);
            }
        });
        reservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Add_Reservations.this, Reservations.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Add_Reservations.this, Profile.class);
            }
        });

        // Find TextViews where you want to display the dates
        textViews = new TextView[8];
        textViews[0] = findViewById(R.id.day1);
        textViews[1] = findViewById(R.id.day2);
        textViews[2] = findViewById(R.id.day3);
        textViews[3] = findViewById(R.id.day4);
        textViews[4] = findViewById(R.id.day5);
        textViews[5] = findViewById(R.id.day6);
        textViews[6] = findViewById(R.id.day7);

        textViews[7] = findViewById(R.id.textView2);

        checkdbtime();
        // Set initial split week
         displaySplitWeek();


        Button prevWeekButton = findViewById(R.id.prev);
        prevWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySplitWeek();
            }
        });
        // Find the Button for next week
        Button nextWeekButton = findViewById(R.id.next);
        nextWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNextSplitWeek();
            }
        });
        // Loop through the array and set the OnClickListener
        for (int i = 0; i < textViews.length; i++) {
            final int index = i; // This captures the index in a final variable

            textViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openTimePickerDialog();

                }
            });
        }

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
    private void checkdbtime(){

        DatabaseReference dbfindtime = FirebaseDatabase.getInstance()
                .getReference("Users/Gym_Owner");


        dbfindtime.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot underGym_Owner : snapshot.getChildren()) {

                    for (DataSnapshot underGym : underGym_Owner.getChildren()) {

                        for (DataSnapshot underGymchild : underGym.getChildren()) {

                            String gymname = underGymchild.child("gym_name").getValue(String.class);
                            if(gymname == gymnamefromresdialoggymlist){
                                String gymopeningdb = underGymchild.child("gym_opening").getValue(String.class);
                                Log.d("Timeforce", "Opening time :"+ gymopeningdb);
                                String gymclosingdb = underGymchild.child("gym_closing").getValue(String.class);
                                Log.d("Timeforce", "Closing time :"+ gymclosingdb);
                                gymopening = convertTimeStringToMinutes(gymopeningdb);
                                Log.d("Timeforce", "Opening time result change :"+ gymopening);
                                gymclosing = convertTimeStringToMinutes(gymclosingdb);
                                Log.d("Timeforce", "Closing time result change :"+ gymclosing);

                            }

                        }
                        break;
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });
    }

    private void displaySplitWeek() {
        // Set the start date of the split week (e.g., Monday)
        startDate = Calendar.getInstance();
        startDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        // Set the end date of the split week (e.g., Sunday)
        endDate = Calendar.getInstance();
        endDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Display the split week
        displayWeek(startDate, endDate);
    }

    public static int convertTimeStringToMinutes(String timeString) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        try {
            Date date = sdf.parse(timeString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);

            return (hours * 60) + minutes;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if there's an error
    }

    private void displayNextSplitWeek() {
        // Move to the next split week
        startDate.add(Calendar.WEEK_OF_YEAR, 1);
        endDate.add(Calendar.WEEK_OF_YEAR, 1);

        // Display the next split week
        displayWeek(startDate, endDate);
    }
    private void displayWeek(Calendar start, Calendar end) {
        // Format and set dates for the split week to respective TextViews
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd", Locale.getDefault());

        for (int i = 0; i < 7; i++) {
            textViews[i].setText(sdf.format(start.getTime()));
            start.add(Calendar.DAY_OF_MONTH, 1);
        }
        // Reset start date
        start.add(Calendar.DAY_OF_MONTH, -7);
    }

    private String selectedTime = "";
    private void openTimePickerDialog() {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutecurrent = calendar.get(Calendar.MINUTE);

        // Create a TimePickerDialog and set its listener
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minuteOfDay) -> {

                    if (isTimeValid(hourOfDay, minuteOfDay)) {
                        // Show message if selected time is before the current time
                        Toast.makeText(Add_Reservations.this,
                                "Time cannot be selected", Toast.LENGTH_SHORT).show();
                    } else {
                        // Convert to 12-hour format with AM/PM
                        String amPm = (hourOfDay >= 12) ? "PM" : "AM";
                        int hourIn12Format = (hourOfDay > 12) ? hourOfDay - 12 : (hourOfDay == 0 ? 12 : hourOfDay);
                        selectedTime = String.format(Locale.getDefault(), "%02d:%02d %s", hourIn12Format, minuteOfDay, amPm);

                        // Display the selected time
                        Toast.makeText(Add_Reservations.this, "Selected Time: " + selectedTime, Toast.LENGTH_SHORT).show();
                         reservethis();
                    }
                }, hour, minutecurrent, false); // Set to false for 12-hour format with AM/PM

        // Show the time picker dialog
        timePickerDialog.show();
    }
    private boolean isTimeValid(int hour, int minute) {
        // Convert the time into a single value for easier comparison
        int selectedTime = hour * 60 + minute; // Convert the hour and minute to total minutes from midnight
        int minTime = gymopening;  // 9:00 AM in minutes
        int maxTime = gymclosing; // 10:00 PM in minutes


        return selectedTime >= maxTime || selectedTime <= minTime;
    }
    private void reservethis(){

        GymDatabase = FirebaseDatabase.getInstance();
        GymRef = GymDatabase.getReference("Reservations").child("Pending_Requests").child(gymnamefromresdialoggymlist);
        GymRef_ID = GymRef.push();

        String userid = Login.key;
        String Res_id =  GymRef_ID.getKey();
        String DatetoDB = textViews[0].getText().toString();
        String UsernamefromDB = textViews[7].getText().toString();
        String timetoDB = selectedTime;
        String gym_nam = gymnamefromresdialoggymlist;
        String contctnum = gym_contact_numberforview;
        Log.d("TAG110", "contactnum: " + contctnum);

        Reservationdate mser = new Reservationdate(userid, contctnum, Res_id,gym_nam,timetoDB,UsernamefromDB, DatetoDB);
        GymRef_ID.setValue(mser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(Add_Reservations.this,"successfully updated",Toast.LENGTH_SHORT).show();
                redirectActivity(Add_Reservations.this, Reservations.class);
            }
        });
    }

}