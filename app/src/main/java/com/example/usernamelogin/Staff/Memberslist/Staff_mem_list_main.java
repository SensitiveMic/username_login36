package com.example.usernamelogin.Staff.Memberslist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.example.usernamelogin.Staff.Gym_Management.Gym_management_main;
import com.example.usernamelogin.Staff.Memberslist.members_not_exp.Adapter_frag_members_list;
import com.example.usernamelogin.Staff.Memberslist.members_not_exp.Model_class_get_members_details;
import com.example.usernamelogin.Staff.Profile_Staff.Staff_Profile_Main;
import com.example.usernamelogin.Staff.Staff_main;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Staff_mem_list_main extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, reservations, profile,member_lists,logoput;
    TabLayout kasibagTab;
    RecyclerView recyclerView;
    TextView nav_username,nav_gym,toolbar_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_staff_mem_list_main);

        //navbar toolbar textVIews
        nav_username = findViewById(R.id.textView2);
        nav_gym = findViewById(R.id.textView);
        toolbar_username = findViewById(R.id.username_nav);
        nav_username.setText(Staff_main.ProfileContents[0]);
        toolbar_username.setText(Staff_main.ProfileContents[0]);
        nav_gym.setText(Staff_main.ProfileContents[4]);

        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        reservations = findViewById(R.id.Gym_manage_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);
        member_lists = findViewById(R.id.Gym_manage_members);
        kasibagTab = findViewById(R.id.tablayou);
        recyclerView = findViewById(R.id.recyclerView_members);
        logoput = findViewById(R.id.logout_Button_U);
        TabLayout.Tab tab1 = kasibagTab.newTab().setText("Current Members");
        kasibagTab.addTab(tab1);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavbar(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Staff_mem_list_main.this, Staff_main.class);
            }
        });
        reservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Staff_mem_list_main.this, Gym_management_main.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Staff_mem_list_main.this, Staff_Profile_Main.class);
            }
        });
        member_lists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        logoput.setOnClickListener(v ->{
            logout_prc(Staff_mem_list_main.this, Login.class);

        });
        populatememberslist();
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
    private void populatememberslist() {
        ArrayList<Model_class_get_members_details> list = new ArrayList<>();
        Adapter_frag_members_list adapter = new Adapter_frag_members_list(this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users/Non-members");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // always clear before refill
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String gymName = snapshot1.child("GymName").getValue(String.class);
                    if (gymName != null && gymName.equals(Login.key_Gym_)) {
                        DataSnapshot membershipSnapshot = snapshot1.child("membership");

                        if (membershipSnapshot.exists()) {
                            String username = snapshot1.child("username").getValue(String.class);
                            String expirationDate = membershipSnapshot.child("expiration_date").getValue(String.class);
                            String startDate = membershipSnapshot.child("start_date").getValue(String.class);

                            Model_class_get_members_details res1 =
                                    new Model_class_get_members_details(username, expirationDate, startDate);
                            res1.setRemainind_days(remainingdays(expirationDate));
                            list.add(res1);
                        }
                    }
                }
                adapter.notifyDataSetChanged(); // only after the loop
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    private String remainingdays( String exp_date){
        String remaining_Days = "";
        SimpleDateFormat sdf = new SimpleDateFormat("MM dd yy");

        try {
            Date start = new Date();
            Date expiration = sdf.parse(exp_date);

            long diffInMillis = expiration.getTime() - start.getTime();
            long daysLeft = TimeUnit.MILLISECONDS.toDays(diffInMillis);

            remaining_Days = String.valueOf(daysLeft);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return remaining_Days;
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