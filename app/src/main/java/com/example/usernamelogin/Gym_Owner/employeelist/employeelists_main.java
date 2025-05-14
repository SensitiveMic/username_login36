package com.example.usernamelogin.Gym_Owner.employeelist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.Admin.Userslist.Archived_gyms.Admin_archived_gyms_fragment;
import com.example.usernamelogin.Admin.Userslist.gymanditsmembers.gym_list_admin_fragment;
import com.example.usernamelogin.Admin.Userslist.nonmembers.nonmember_list_admin_fragment;
import com.example.usernamelogin.Gym_Owner.Gym_Owner_Main;
import com.example.usernamelogin.Gym_Owner.Gym_Owner_gymmanagement_add_staff;
import com.example.usernamelogin.Gym_Owner.Gym_manage.Gym_Owner_gymmanage_main;
import com.example.usernamelogin.Gym_Owner.Profile_Main_Gym_Owner;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class employeelists_main extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout Gym_manage,home, Gym_management, profile ,gymemployyes,logoput;
    public static String employeeclicked,Gym_id;
    TextView usernametoolbar,username_nav ;
    FrameLayout frameLayoutwew;
    TabLayout tabLayoutwewe;
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
        Gym_manage = findViewById(R.id.Gym_manage_Manage);

        nav_tool_textviews();

        frameLayoutwew =findViewById(R.id.framelayout2123);
        tabLayoutwewe = findViewById(R.id.tablayout223);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout2123, new Employee_list_fragment())
                .addToBackStack(null)
                .commit();

        tabLayoutwewe.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Fragment fragment123 = null;
                String tagyt = null;
                switch (tab.getPosition()){

                    case 0:
                        tagyt = "employee_list_gymowner";
                        fragment123 = getSupportFragmentManager().findFragmentByTag(tagyt);
                        if (fragment123 == null) {
                            fragment123 = new Employee_list_fragment();
                        }
                        break;

                    case 1:
                        tagyt = "Archived_employee_list_gymowner";
                        fragment123 = getSupportFragmentManager().findFragmentByTag(tagyt);

                        if (fragment123 == null) {
                            fragment123 = new Archived_employees_frag();
                        }
                        break;


                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout2123, fragment123, tagyt)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


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
        Gym_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(employeelists_main.this, Gym_Owner_gymmanage_main.class);
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

    public void nav_tool_textviews() {
        usernametoolbar = findViewById(R.id.textView2);
        username_nav = findViewById(R.id.username_nav);

        usernametoolbar.setText(Gym_Owner_Main.ProfileContents[0]);
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

}