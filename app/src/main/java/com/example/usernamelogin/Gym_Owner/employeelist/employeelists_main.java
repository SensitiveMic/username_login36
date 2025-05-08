package com.example.usernamelogin.Gym_Owner.employeelist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.Gym_Owner.Gym_Owner_Main;
import com.example.usernamelogin.Gym_Owner.Gym_Owner_gymmanagement_add_staff;
import com.example.usernamelogin.Gym_Owner.Profile_Main_Gym_Owner;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class employeelists_main extends AppCompatActivity implements toeditcoachandstaff,interface_Adapter_employee_list {
    RecyclerView employeeslist;
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, Gym_management, profile ,gymemployyes,logoput;
    Adapter_employee_list_fromgymowner adapter;
    public static String employeeclicked,Gym_id;
    TextView usernametoolbar,username_nav,navbar_gym ;

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
        logoput = findViewById(R.id.logout_Button_U);

        nav_tool_textviews();

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
        logoput.setOnClickListener(v ->{
            logout_prc(employeelists_main.this, Login.class);

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
    public void checkUSER() {

        employeeslist = findViewById(R.id.employeelists);
        employeeslist.setHasFixedSize(true);
        employeeslist.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Model_class_staffandcoachlist> list = new ArrayList<>();
        FirebaseDatabase databaseLogin = FirebaseDatabase.getInstance();
        DatabaseReference myRefLogin = databaseLogin.getReference("Users/Gym_Owner")
                .child(Login.key_GymOwner)
                .child("Gym");


        adapter = new Adapter_employee_list_fromgymowner(this,list,this,this);

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

    public void nav_tool_textviews() {
        usernametoolbar = findViewById(R.id.textView2);
        username_nav = findViewById(R.id.username_nav);
        navbar_gym  = findViewById(R.id.Gym_name_navdrawer);

        usernametoolbar.setText(Gym_Owner_Main.ProfileContents[0]);
        username_nav.setText(Gym_Owner_Main.ProfileContents[0]);
        navbar_gym.setText(Gym_Owner_Main.ProfileContents[3]);
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
    public void onitmeclick(int position) {
        Intent intent = new Intent(this, employye_update_profilee.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    @Override
    public void onItemLongClick(int position) {
        new AlertDialog.Builder(employeelists_main.this)
                .setTitle("Delete Item?")
                .setMessage("Are you sure you want to remove the account?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    remove_selected_employee();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();

    }
    private void remove_selected_employee(){
        DatabaseReference myRefLogin = FirebaseDatabase.getInstance().getReference("Users/Gym_Owner")
                .child(Login.key_GymOwner)
                .child("Gym");
        myRefLogin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot gk : snapshot.getChildren()) {
                    for(DataSnapshot childrenofgk :gk.child("Coach").getChildren()) {
                        String userkey = childrenofgk.getKey();
                           String usernam = childrenofgk.child("username").getValue(String.class);
                            Log.d("TAGREMOVALOFEMP", "(Coach)username in db: "+ usernam);
                            if (usernam!= null && usernam.equals(employeeclicked)) {
                                DatabaseReference coachNameRef = childrenofgk.getRef();
                                coachNameRef.getRef().removeValue();
                                Log.d("TAGREMOVALOFEMP", "User Key: "+userkey);
                            }


                    }
                    for(DataSnapshot childrenofgk2 :gk.child("Staff").getChildren()){
                        String userkey2 = childrenofgk2.getKey();
                            String usernam12 = childrenofgk2.child("username").getValue(String.class);
                            Log.d("TAGREMOVALOFEMP", "(Staff)username in db: "+ usernam12);
                            if (usernam12!= null && usernam12.equals(employeeclicked)) {
                                DatabaseReference coachNameRef = childrenofgk2.getRef();
                                coachNameRef.getRef().removeValue();
                                Log.d("TAGREMOVALOFEMP", "User Key: "+userkey2);
                            }

                    }
                }
                checkUSER();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}