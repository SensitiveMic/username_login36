package com.example.usernamelogin.Admin.Gym;

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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.usernamelogin.Admin.Admin_main;
import com.example.usernamelogin.Admin.Userslist.gymanditsmembers.UsersList_Admin_main;
import com.example.usernamelogin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Admin_add_gym extends AppCompatActivity implements Admin_Gym_RecyclerViewInterface {
    DrawerLayout drawerLayout;
    ImageView menu, add_gym_circle;
    LinearLayout home, reservations, add_gym;
    DatabaseReference db ,db1 ;
    RecyclerView recyclerView;
    Adapter_recyclerview_add_gym myadapter1;
    public static String gymownerkey, gymownersgymname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_gym);
        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        reservations = findViewById(R.id.Reservations_navdrawer);
        add_gym = findViewById(R.id.add_gym_navdrawer);
        add_gym_circle = findViewById(R.id.add_gym_circle);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavbar(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Admin_add_gym.this, Admin_main.class);
            }
        });
        reservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Admin_add_gym.this, UsersList_Admin_main.class);
            }
        });
        add_gym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        add_gym_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Admin_add_gym.this, Admin_add_gym_Field_Req.class);
            }
        });
        refresh_list_gym();
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

    private void refresh_list_gym(){
        recyclerView = findViewById(R.id.adminallgymList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<add_gym_recyclerviewAdapter_helper> list = new ArrayList<>();
        db = FirebaseDatabase.getInstance().getReference("/Users/Gym_Owner");

        myadapter1 = new Adapter_recyclerview_add_gym(this,list,this);
        recyclerView.setAdapter(myadapter1);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                    add_gym_recyclerviewAdapter_helper res_list1 = dataSnapshot.getValue(add_gym_recyclerviewAdapter_helper.class);
                    list.add(res_list1);
                }
                myadapter1.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(int position) {
        redirectActivity(Admin_add_gym.this, Admin_Update_Gym_Info.class);
    }
}