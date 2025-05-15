package com.example.usernamelogin.Gym_Owner.Gym_manage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.Gym_Owner.Gym_Owner_Main;
import com.example.usernamelogin.Gym_Owner.Gym_Owner_gymmanagement_add_staff;
import com.example.usernamelogin.Gym_Owner.Profile_Main_Gym_Owner;
import com.example.usernamelogin.Gym_Owner.employeelist.employeelists_main;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Gym_Owner_gymmanage_main extends AppCompatActivity implements Interface_adapter_gyms_list_onclick,Interface_adapter_gyms_list_longclick {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, Gym_management, profile ,gymemployyes,Gym_manage, logoput;
    RecyclerView recyclerView;
    ImageView addgyme_circle;
    public static String gym_Name,gym_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gym_owner_gymmanage_main);
        toolbarnavbar();

        drawerLayout = findViewById(R.id.activity_gym_owner_gymmanage_main);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        Gym_management = findViewById(R.id.Gym_manage_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);
        gymemployyes = findViewById(R.id.GYymemployyelist);
        logoput = findViewById(R.id.logout_Button_U);
        Gym_manage = findViewById(R.id.Gym_manage_Manage);
        addgyme_circle = findViewById(R.id.add_gym_circle);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavbar(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Gym_Owner_gymmanage_main.this, Gym_Owner_Main.class);
            }
        });
        Gym_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Gym_Owner_gymmanage_main.this, Gym_Owner_gymmanagement_add_staff.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Gym_Owner_gymmanage_main.this, Profile_Main_Gym_Owner.class);
            }
        });
        gymemployyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Gym_Owner_gymmanage_main.this, employeelists_main.class);
            }
        });
        Gym_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               recreate();
            }
        });

        addgyme_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            redirectActivity(Gym_Owner_gymmanage_main.this, Add_Gym_with_details.class);
             /*  Abstract_class_add_gym dialog = new Abstract_class_add_gym(Gym_Owner_gymmanage_main.this){

                    protected void onCreate(Bundle savedInstanceState) {
                        super.onCreate(savedInstanceState);
                    }

                };
                dialog.setOwnerActivity(Gym_Owner_gymmanage_main.this);
                dialog.setCancelable(true); // or false if you want to disable outside touches
                dialog.show();   */
            }
        });
        logoput.setOnClickListener(v ->{
            logout_prc(Gym_Owner_gymmanage_main.this, Login.class);

        });
        refresh_list_gym();

    }
    private void refresh_list_gym(){
        String ownerkey = Login.key_GymOwner;
        recyclerView = findViewById(R.id.adminallgymList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Modelclass_gym_manage_Adapter> list = new ArrayList<>();
        Query db = FirebaseDatabase.getInstance()
                .getReference("/Users/Gym_Owner")
                .child(ownerkey)
                .child("Gym")
                .orderByChild("timestamp"); // ✅ ORDER BY TIMESTAMP

        Adapter_Gym_Owner_gymmanage_main myadapter1 = new Adapter_Gym_Owner_gymmanage_main(this, list, (Interface_adapter_gyms_list_onclick) this, this::onlongclick);
        recyclerView.setAdapter(myadapter1);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String gymkey = dataSnapshot.getKey();
                    Modelclass_gym_manage_Adapter gym = dataSnapshot.getValue(Modelclass_gym_manage_Adapter.class);
                    if (gym != null) {
                        gym.setGym_key(gymkey);
                        list.add(0, gym); // ✅ newest first
                    }
                }
                myadapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAGCHECKKEY12", "Error fetching gym data", error.toException());
            }
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
    private void toolbarnavbar(){
        TextView usernamebar, username_nav;
        usernamebar =  findViewById(R.id.textView2);
        username_nav =  findViewById(R.id.username_nav);
        usernamebar.setText(Gym_Owner_Main.ProfileContents[0]);
        username_nav.setText(Gym_Owner_Main.ProfileContents[0]);
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

    @Override
    public void onItemClick(int position) {
        Log.d("TAG8080", "gymkey: " +gym_Name );

      dialog_real_list_ofmembers listdialog = new dialog_real_list_ofmembers(this) {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
            }
        };
        listdialog.show();
        Window window = listdialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    public void onlongclick(int position) {
        redirectActivity(Gym_Owner_gymmanage_main.this, Modify_gym_with_details.class);
    }
}