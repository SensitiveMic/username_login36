package com.example.usernamelogin.Member.Reservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.usernamelogin.Member.Gym_Info.Member_Gym_info_main;
import com.example.usernamelogin.Member.Member_Profile;
import com.example.usernamelogin.Member.Member_main;
import com.example.usernamelogin.Member.Reservation.Coach_Res_Scheduler.Coach_res_form_main;
import com.example.usernamelogin.Member.Reservation.Current_Coach_Res.Current_Coach_Res_Main;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Coach_Reservation_main extends AppCompatActivity implements interface_coach_list_res_mem {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, reservations, profile, gym_membership, currentreservations,logoput;

    public static String[] ProfileContents;
    RecyclerView recyclerView;
    Adapter_Gym_coach_list_res myAdapter;
    public static String picked_coach;
    public static String key_Gym_Coach1, key_Gym_Coach3;
    TextView gymName_nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_reservation_main);

        someMethod();
        gymName_nav = findViewById(R.id.textView_gym_name);
        gymName_nav.setText(Login.member_gym_name);

        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        reservations = findViewById(R.id.Reservations_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);
        gym_membership = findViewById(R.id.Gym_navdrawer);
        currentreservations = findViewById(R.id.current_res_coach);
        logoput = findViewById(R.id.logout_Button_U);

        coachlistformem();
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavbar(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Coach_Reservation_main.this, Member_main.class);
            }
        });
        reservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               recreate();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Coach_Reservation_main.this, Member_Profile.class);
            }
        });
        gym_membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Coach_Reservation_main.this, Member_Gym_info_main.class);
            }
        });
        currentreservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Coach_Reservation_main.this, Current_Coach_Res_Main.class);
            }
        });
        logoput.setOnClickListener(v ->{
            logout_prc(Coach_Reservation_main.this, Login.class);

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
    public void usertoolbarname(Context context, TextView usernamebar, TextView username_nav) {
        DatabaseReference databaseReferenceNon = FirebaseDatabase.getInstance()
                .getReference("Users").child("Non-members");
        Query checkname = databaseReferenceNon.orderByChild("username");
        String pushkey = Login.key;
        Log.d("TAG3", pushkey);

        checkname.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProfileContents = new String[3];
                ProfileContents[0] = dataSnapshot.child(pushkey).child("username").getValue(String.class);
                ProfileContents[1] = dataSnapshot.child(pushkey).child("email").getValue(String.class);
                ProfileContents[2] = dataSnapshot.child(pushkey).child("password").getValue(String.class);

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
    public void someMethod() {
        // Call usertoolbarname() with appropriate arguments
        usertoolbarname(getApplicationContext(),
                findViewById(R.id.textView2),
                findViewById(R.id.username_nav));
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

    private void coachlistformem(){
        recyclerView = findViewById(R.id.coach_list_for_res);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Model_class_Coach_list> list = new ArrayList<>();

        myAdapter = new Adapter_Gym_coach_list_res(this, list,this);
        recyclerView.setAdapter(myAdapter);

        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Users/Gym_Owner");
        Query query = db1.orderByKey();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                key_Gym_Coach1 = null;
                key_Gym_Coach3 = null;
                DatabaseReference dbcont;

                outerLoop:
                for (DataSnapshot underGym_Owner : snapshot.getChildren()) {
                    key_Gym_Coach1 = underGym_Owner.getKey();
                    Log.d("TAG162", "1st key :" + key_Gym_Coach1);

                    for (DataSnapshot undergym : underGym_Owner.getChildren()) {

                        for (DataSnapshot underGymchild : undergym.getChildren()) {
                            key_Gym_Coach3 = underGymchild.getKey();
                            Log.d("TAG162", "3rd key :" +  key_Gym_Coach3);
                            String gymnameofcoach = underGymchild.child("gym_name").getValue(String.class);

                            if (Objects.equals(gymnameofcoach, Login.member_gym_name)) {

                                Log.d("TAG162", "This is member current Gym: " +  gymnameofcoach);

                                dbcont = FirebaseDatabase.getInstance().getReference("Users/Gym_Owner")
                                        .child(key_Gym_Coach1).child("Gym").child(key_Gym_Coach3).child("Coach");

                                dbcont.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot underGym_Owner : snapshot.getChildren()) {
                                            String key_Gym_ = underGym_Owner.child("username").getValue(String.class);
                                            Log.d("TAG162", "coach usernames :" +  key_Gym_);

                                            Model_class_Coach_list users = underGym_Owner.getValue(Model_class_Coach_list.class);
                                            users.setUsername(key_Gym_);
                                            list.add(users);
                                        }
                                        myAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) { }
                                });

                                // ✅ Break out of all loops once we find the match
                                break outerLoop;
                            } else {
                                Log.d("TAG162", "This is not the member current Gym: " +  gymnameofcoach);
                                Log.d("TAG162", "This is my Gym: " + Login.member_gym_name);
                            }
                        }
                    }
                }

                }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onItemClick(int position) {
            redirectActivity(Coach_Reservation_main.this, Coach_res_form_main.class);
    }
}