package com.example.usernamelogin.Admin.Userslist.gymanditsmembers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.usernamelogin.Admin.Admin_main;
import com.example.usernamelogin.Admin.Gym.Admin_add_gym;
import com.example.usernamelogin.Admin.Userslist.Archived_gyms.Admin_archived_gyms_fragment;
import com.example.usernamelogin.Admin.Userslist.nonmembers.nonmember_list_admin_fragment;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.android.material.tabs.TabLayout;

public class UsersList_Admin_main extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, userslist, add_gym, nonmember,logoput;
    FrameLayout frameLayout;
    TabLayout tabLayout;
    public static String gyym_owners_usernmae,gym_owner_KEY,gymownersgymname, gym_KEY ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list_admin_main);

        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        userslist = findViewById(R.id.Reservations_navdrawer);
        add_gym = findViewById(R.id.add_gym_navdrawer);
        nonmember = findViewById(R.id.nonmember);
        logoput = findViewById(R.id.logout_Button_U);

        frameLayout = (FrameLayout) findViewById(R.id.framelayout21);
        tabLayout = (TabLayout) findViewById(R.id.tablayout2);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout21, new gym_list_admin_fragment())
                .addToBackStack(null)
                .commit();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Fragment fragment = null;
                String tag = null;
                switch (tab.getPosition()){

                    case 0:
                        tag = "Gym_list";
                        fragment = getSupportFragmentManager().findFragmentByTag(tag);
                        if (fragment == null) {
                            fragment = new gym_list_admin_fragment();
                        }
                        break;

                    case 1:
                        tag = "archived_gyms";
                        fragment = getSupportFragmentManager().findFragmentByTag(tag);
                        if (fragment == null){
                            fragment = new Admin_archived_gyms_fragment();
                        }
                        break;

                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout21, fragment, tag)
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
                redirectActivity(UsersList_Admin_main.this, Admin_main.class);
            }
        });
        userslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        add_gym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(UsersList_Admin_main.this, Admin_add_gym.class);
            }
        });
        logoput.setOnClickListener(v ->{
            logout_prc(UsersList_Admin_main.this, Login.class);
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
