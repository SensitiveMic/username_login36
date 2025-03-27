package com.example.usernamelogin.Staff.Gym_Management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.example.usernamelogin.R;

import com.example.usernamelogin.Staff.Gym_Management.Gymmng_changestogym.OCtimechange;
import com.example.usernamelogin.Staff.Profile_Staff.Staff_Profile_Main;
import com.example.usernamelogin.Staff.Staff_main;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Gym_management_main extends AppCompatActivity  {
    DrawerLayout drawerLayout;
    ImageView menu, gotoaddgym;
    LinearLayout home, Gym_management, profile;
    RecyclerView recyclerView;
    Button changedescrp, changeOCtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_management_main);

        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        Gym_management = findViewById(R.id.Gym_manage_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);
        gotoaddgym = findViewById(R.id.Add_Gym_Package_button);
        changedescrp = findViewById(R.id.change_descrp_button);
        changeOCtime = findViewById(R.id.change_OC_button);

        changedescrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        changeOCtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                octimedialog();
            }
        });


        recyclerView = findViewById(R.id.Gym_Packages_List);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        populate_gympackage_list();

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavbar(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Gym_management_main.this, Staff_main.class);
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
                redirectActivity(Gym_management_main.this, Staff_Profile_Main.class);
            }
        });
        gotoaddgym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Gym_management_main.this, Gym_package_Creation.class);
            }
        });

    }

    private void octimedialog(){
        OCtimechange timchangedialog = new OCtimechange(this) {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
            }
        };
        timchangedialog.show();
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
    private void populate_gympackage_list(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();;
        DatabaseReference myref = database.getReference("Gym_package") ;

        ArrayList<Model_Class_Adapter_Gym_Packages> list = new ArrayList<>();
        Adapter_Gym_Packages adapter = new Adapter_Gym_Packages(this,list);
        recyclerView.setAdapter(adapter);

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Model_Class_Adapter_Gym_Packages res_list = dataSnapshot.getValue(Model_Class_Adapter_Gym_Packages.class);
                    list.add(res_list);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}