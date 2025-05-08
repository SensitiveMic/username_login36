package com.example.usernamelogin.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.usernamelogin.Admin.Gym.Admin_add_gym;
import com.example.usernamelogin.Admin.Userslist.gymanditsmembers.UsersList_Admin_main;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Admin_main extends AppCompatActivity implements RecyclerViewInterface {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, userslist, add_gym, nonmember,logoput;
    DatabaseReference db ,db1 ;
    RecyclerView recyclerView;
    Adapter_recyclerview_Adminusers_all myadapter;
    TextView dbusername1;
    TextView navbar_Username;
    public static String non_member_username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        userslist = findViewById(R.id.Reservations_navdrawer);
        add_gym = findViewById(R.id.add_gym_navdrawer);
        nonmember = findViewById(R.id.nonmember);
        logoput = findViewById(R.id.logout_Button_U);
        navbar_Username = findViewById(R.id.username_nav);

        dbusername1 = findViewById(R.id.dbusername);

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
        userslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Admin_main.this, UsersList_Admin_main.class);
            }
        });
        add_gym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Admin_main.this, Admin_add_gym.class);
            }
        });
        logoput.setOnClickListener(v ->{
            logout_prc(Admin_main.this, Login.class);

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
        activity.startActivity(intent);

    }
    @Override
    protected void onPause(){
        super.onPause();
        closeNavbar(drawerLayout);
    }
    private void refresh_res_list(){
        recyclerView = findViewById(R.id.adminalluserList);
        db = FirebaseDatabase.getInstance().getReference("/Users/Non-members");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<users_all> list = new ArrayList<>();

        myadapter = new Adapter_recyclerview_Adminusers_all(this,list,this);
        recyclerView.setAdapter(myadapter);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    users_all res_list = dataSnapshot.getValue(users_all.class);
                    list.add(res_list);
                }
                myadapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
    @Override
    public void onItemClick(int position) {
        redirectActivity(Admin_main.this, Admin_user_nonmember_click.class);
    }

}