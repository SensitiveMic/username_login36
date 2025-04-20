package com.example.usernamelogin.Staff.Membership_req_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;

import com.example.usernamelogin.Staff.Gym_Management.Gym_management_main;

import com.example.usernamelogin.Staff.Profile_Staff.Staff_Profile_Main;
import com.example.usernamelogin.Staff.Staff_main;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class Membership_requests_main extends AppCompatActivity implements interface_membership_requests_list {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, reservations, profile;
    String gym_name;
    RecyclerView recyclerView;
    public static String usernamefrmmmbrshpreq;
    public static String clicked_nonmem_application_dur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_requests_main);

        someMethod();

        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        reservations = findViewById(R.id.Gym_manage_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);
        recyclerView = findViewById(R.id.membership_requests_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        gym_name = Login.key_Gym_;

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavbar(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Membership_requests_main.this, Staff_main.class);
            }
        });
        reservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Membership_requests_main.this, Gym_management_main.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Membership_requests_main.this, Staff_Profile_Main.class);
            }
        });

        membership_request_lists();
        //---- this method changes membership based on expiration---
     //  checkMembershipExpirations();
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
                usernamebar.setText(Staff_main.ProfileContents[0]);
                username_nav.setText(Staff_main.ProfileContents[0]);
            }
    public void someMethod() {
        // Call usertoolbarname() with appropriate arguments
        usertoolbarname(getApplicationContext(),
                findViewById(R.id.textView2),
                findViewById(R.id.username_nav));
        }

    public void membership_request_lists(){
        DatabaseReference thelist = FirebaseDatabase.getInstance().getReference("Membership_Request")
                .child(Login.key_Gym_);
        ArrayList<Model_class_mmbershpr> list = new ArrayList<>();
        Adapter_MmbrRq_lst adapter = new Adapter_MmbrRq_lst(this,list, this);
        recyclerView.setAdapter(adapter);
        thelist.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot underGym_name: snapshot.getChildren()) {
                    Model_class_mmbershpr item = underGym_name.getValue(Model_class_mmbershpr.class);
                    String itemkey = underGym_name.getKey();
                    list.add(item);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "Successful list click", Toast.LENGTH_LONG).show();
        Log.d("TAG10", "CLICKED!");

        DatabaseReference gettheusername = FirebaseDatabase.getInstance().getReference("Users");

        Query checkUser = gettheusername.child("Non-members").orderByChild("username")
                .equalTo(usernamefrmmmbrshpreq);

        DatabaseReference deletethereq = FirebaseDatabase.getInstance().getReference("Membership_Request").child(Login.key_Gym_);
        Query deletetherequrey = deletethereq.orderByChild("username").equalTo(Membership_requests_main.usernamefrmmmbrshpreq);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("ACCEPT MEMBERSHIP")
                .setMessage("Do you want to proceed to accept the membership request?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Positive button action
                        Log.d("Dialog", "OK clicked");
                        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    Log.d("TAG10", "CLICKED!");
                                    for(DataSnapshot gk: snapshot.getChildren()) {
                                        String key = gk.getKey(); // to make the key into a string
                                        Log.e( "TAG10",key);
                                        // to add the password from the database to a string
                                        DatabaseReference mem_status = gettheusername.child("Non-members").child(key).child("membership_status");
                                        mem_status.setValue(0);
                                        DatabaseReference Members_gym =  gettheusername.child("Non-members").child(key).child("Gym Name");
                                        Members_gym.setValue(gym_name);
                                        Log.d("TAG10", "Minecraft Complete!");
                                        storeDates(key);

                                    }
                                }
                                else{
                                    Log.e("TAG10", "NO snapshot exists!");
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        deletetherequrey.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    Log.d("TAG10", "CLICKED!");
                                    for(DataSnapshot gk: snapshot.getChildren()) {
                                        String key = gk.getKey(); // to make the key into a string
                                        Log.e( "TAG10",key);
                                        // to add the password from the database to a string
                                        DatabaseReference deletenow = deletethereq.child(key) ;
                                        deletenow.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                redirectActivity(Membership_requests_main.this, Membership_requests_main.class);
                                                Toast.makeText(Membership_requests_main.this, "Succesfully added a Member", Toast.LENGTH_LONG).show();
                                            }
                                        });


                                    }
                                }
                                else{
                                    Log.e("TAG10", "NO snapshot exists!");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Negative button action
                        dialog.dismiss();
                    }
                });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();

        // Show the dialog
        dialog.show();

         }

    private void storeDates(String userKey) {
        // Get the current date and time
        Date now = new Date();
        DatabaseReference gettheusername1 = FirebaseDatabase.getInstance().getReference("Users")
                .child("Non-members").child(userKey);

        // Calculate the expiration date based on the duration in days
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int durationInDays = Integer.parseInt(Membership_requests_main.clicked_nonmem_application_dur);
        calendar.add(Calendar.DAY_OF_YEAR, durationInDays);
        Date expirationDate = calendar.getTime();

        // Format the dates to MM dd yy
        SimpleDateFormat sdf = new SimpleDateFormat("MM dd yy", Locale.getDefault());
        String formattedNow = sdf.format(now);
        String formattedExpiration = sdf.format(expirationDate);
        String Gymname = Login.key_Gym_;

        // Create a map to store both dates
        Map<String, String> dateMap = new HashMap<>();
        dateMap.put("start_date", formattedNow);
        dateMap.put("expiration_date", formattedExpiration);
        dateMap.put("Gym Name", Gymname);

        // Store the dates in Firebase
        gettheusername1.child("membership").setValue(dateMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("TAG10", "Dates stored successfully.");
            } else {
                Log.e("TAG10", "Failed to store dates.", task.getException());
            }
        });
    }


    public static void checkMembershipExpirations() {

            DatabaseReference nonMembersRef = FirebaseDatabase.getInstance().getReference("Users/Non-members");
            nonMembersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userId = userSnapshot.getKey();
                        Model_class_exiprationsys membership = userSnapshot.child("membership").getValue(Model_class_exiprationsys.class);
                        Long membershipStatus = userSnapshot.child("membership_status").getValue(Long.class);

                        if (membershipStatus != null && membershipStatus == 0 && membership != null) {
                            if (isExpired(membership.getExpiration_date())) {
                                Log.d("TAG10", "Membership expired for user " + userId);
                                // Remove the membership child node and update membership status
                                userSnapshot.getRef().child("membership").removeValue();
                                userSnapshot.getRef().child("membership_status").setValue(1);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("TAG10", "Error checking membership expirations: ", databaseError.toException());
                }
            });
        }


    private static boolean isExpired(String expirationDateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            Date expirationDate = sdf.parse(expirationDateStr);
            return expirationDate != null && expirationDate.before(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}