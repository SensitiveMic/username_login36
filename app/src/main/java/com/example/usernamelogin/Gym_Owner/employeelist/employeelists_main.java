package com.example.usernamelogin.Gym_Owner.employeelist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.Admin.Gym.Admin_add_gym;
import com.example.usernamelogin.Gym_Owner.Gym_Owner_Gym_Management;
import com.example.usernamelogin.Gym_Owner.Gym_Owner_Main;
import com.example.usernamelogin.Gym_Owner.Gym_Owner_gymmanagement_add_staff;
import com.example.usernamelogin.Gym_Owner.Profile_Main_Gym_Owner;
import com.example.usernamelogin.Member.Reservation.Model_class_Coach_list;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class employeelists_main extends AppCompatActivity implements toeditcoachandstaff {
    RecyclerView employeeslist;
    private Button button_chg;
    DrawerLayout drawerLayout;
    private String[] ProfileContents;
    ImageView menu;
    LinearLayout home, Gym_management, profile ,gymemployyes;
    String pushkey;
    String key1,key2;
    Adapter_employee_list_fromgymowner adapter;
    public static String employeeclicked,Gym_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeelists_main);

        drawerLayout = findViewById(R.id.main);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        Gym_management = findViewById(R.id.Gym_manage_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);
        gymemployyes = findViewById(R.id.GYymemployyelist);

        someMethod();

        checkUSER();

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavbar(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(employeelists_main.this, Gym_Owner_Main.class);
            }
        });
        Gym_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(employeelists_main.this, Gym_Owner_gymmanagement_add_staff.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(employeelists_main.this, Profile_Main_Gym_Owner.class);
            }
        });

    }

    public void checkUSER() {

        employeeslist = findViewById(R.id.employeelists);
        employeeslist.setHasFixedSize(true);
        employeeslist.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Model_class_staffandcoachlist> list = new ArrayList<>();
        FirebaseDatabase databaseLogin = FirebaseDatabase.getInstance();
        DatabaseReference myRefLogin = databaseLogin.getReference("Users/Gym_Owner")
                .child(Login.key_GymOwner)
                .child("Gym");


        adapter = new Adapter_employee_list_fromgymowner(this,list,this);

        employeeslist.setAdapter(adapter);

        myRefLogin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot gk : snapshot.getChildren()) {
                    String key1 = gk.getKey();
                    Gym_id = key1;
                    Log.d("TAGGYMCOACH", "GYM ID : " + key1);
                    for(DataSnapshot childrenofgk :gk.getChildren()){
                        String Gymdetailskey = childrenofgk.getKey();
                        Log.d("TAGGYMCOACH", "next : " + Gymdetailskey);
                        if("Coach".equals(Gymdetailskey)) {
                            for(DataSnapshot coachlists: childrenofgk.getChildren()){

                                String coachkey = coachlists.getKey();
                                Log.d("TAGGYMCOACH", "Coach ID : " + coachkey);

                                Model_class_staffandcoachlist reslist = coachlists.getValue(Model_class_staffandcoachlist.class);
                                String coachrole = "Coach";
                                reslist.setRole(coachrole);
                                list.add(reslist);
                                Integer wewnum = 0;

                            }
                            adapter.notifyDataSetChanged();
                        }
                       else if("Staff".equals(Gymdetailskey)){
                            for(DataSnapshot stafflists: childrenofgk.getChildren()){

                                String coachkey = stafflists.getKey();
                                Log.d("TAGGYMCOACH", "Staff ID : " + coachkey);
                                Model_class_staffandcoachlist reslist = stafflists.getValue(Model_class_staffandcoachlist.class);
                                Integer wewnum = 1;

                                String coachrole = "Staff";
                                reslist.setRole(coachrole);
                                list.add(reslist);

                            }
                            adapter.notifyDataSetChanged();
                        }
                        else{
                            Log.d("TAGGYMCOACH", "NO COACH ");
                        }



                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void someMethod() {
        // Call usertoolbarname() with appropriate arguments
        usertoolbarname(getApplicationContext(),
                findViewById(R.id.textView2),
                findViewById(R.id.username_nav));
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
    public void onitmeclick(int position) {
        Intent intent = new Intent(this, employye_update_profilee.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


}