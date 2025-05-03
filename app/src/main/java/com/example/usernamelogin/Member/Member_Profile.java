package com.example.usernamelogin.Member;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usernamelogin.Member.Gym_Info.Member_Gym_info_main;
import com.example.usernamelogin.Member.Reservation.Coach_Reservation_main;
import com.example.usernamelogin.Member.Reservation.Current_Coach_Res.Current_Coach_Res_Main;
import com.example.usernamelogin.NonMemberUser.Gym_prop.Gym_Properties_Main;
import com.example.usernamelogin.NonMemberUser.NonMemberUSER;

import com.example.usernamelogin.NonMemberUser.Reservations.Reservations;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.example.usernamelogin.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Member_Profile extends AppCompatActivity {
    String USERNAME, PASSWORD, EMAIL, MOBILENUMBER;
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, reservations, profile, gym_membership,currentreservations;
    Button changeprof_i;
    private TextView[] ments;
    private EditText[] chg;
    private
    FirebaseDatabase databaseprofile ;
    DatabaseReference myRefprofile ;
    String myGYM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_profile);

        // set toolbar and navbar name to users username
        TextView textView2 = findViewById(R.id.textView2);
        TextView username_nav = findViewById(R.id.username_nav);
        if (Member_main.ProfileContents != null && Member_main.ProfileContents.length >= 4) {
            textView2.setText(Member_main.ProfileContents[0]);
            username_nav.setText(Member_main.ProfileContents[0]);
            // and so on...
        } else {
            Toast.makeText(this, "Profile data not loaded", Toast.LENGTH_SHORT).show();
        }

        // Whole layout
        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        reservations = findViewById(R.id.Reservations_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);
        changeprof_i = findViewById(R.id.changeP_I);
        gym_membership = findViewById(R.id.Gym_navdrawer);
        currentreservations = findViewById(R.id.current_res_coach);

        profileContents();

        //Profile contents

        chg[2].setText(Member_main.ProfileContents[2]);
        Button button_chg = findViewById(R.id.button_chg);


        CheckBox showPasswordCheckBox = findViewById(R.id.showPasswordCheckBox);
        CheckBox showPasswordCheckBoxProfile = findViewById(R.id.showPasswordCheckBoxProfile);
        // Set an OnCheckedChangeListener to the CheckBox
        showPasswordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Toggle password visibility based on checkbox state
                if (isChecked) {
                    // Show password
                    chg[2].setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // Hide password
                    chg[2].setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        showPasswordCheckBoxProfile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Toggle password visibility based on checkbox state
                if (isChecked) {
                    // Show password
                    ments[2].setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // Hide password
                    ments[2].setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        gym_membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Member_Profile.this, Gym_Properties_Main.class);
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
                redirectActivity(Member_Profile.this, Member_main.class);
            }
        });
        reservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Member_Profile.this, Coach_Reservation_main.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        changeprof_i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openchg(drawerLayout);
            }
        });
        gym_membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Member_Profile.this, Member_Gym_info_main.class);
            }
        });
        currentreservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Member_Profile.this, Current_Coach_Res_Main.class);
            }
        });
        button_chg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pushkey = Login.key;
                databaseprofile = FirebaseDatabase.getInstance();
                myRefprofile = databaseprofile.getReference("Users/Non-members").child(pushkey);
                // Create a HashMap to hold the updates you want to make
                USERNAME = chg[0].getText().toString();
                EMAIL    = chg[1].getText().toString();
                PASSWORD = chg[2].getText().toString();
                MOBILENUMBER = chg[3].getText().toString();

                Map<String, Object> updates = new HashMap<>();
                updates.put("username", USERNAME);
                updates.put("email",    EMAIL);
                updates.put("password", PASSWORD);
                updates.put("mobile", MOBILENUMBER);

                Users user = new Users(USERNAME, PASSWORD, EMAIL,MOBILENUMBER );
                Log.d("TAG6", "To run on update");
                if(USERNAME.isEmpty() || PASSWORD.isEmpty() || EMAIL.isEmpty()) {

                    Toast.makeText(Member_Profile.this,"Enter Texts in the Empty Fields",Toast.LENGTH_SHORT).show();

                }


                else{

                    DatabaseReference gotogym_coach_Sent_workout = FirebaseDatabase.getInstance().getReference("Users/Gym_Owner");

                    gotogym_coach_Sent_workout.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                Log.d("TAG_MEMBERCHECK", "StarT! " );
                                String key = snapshot1.getKey().toString();
                                Log.d("TAG_MEMBERCHECK", "key in snapshot1: " + key);
                                if(snapshot1.child("Gym").exists()) {
                                    for (DataSnapshot snapshot2 : snapshot1.child("Gym").getChildren()){
                                        String key1 = snapshot2.getKey().toString();
                                        Log.d("TAG_MEMBERCHECK", "key in snapshot2: "+key1 );
                                        for (DataSnapshot snapshot3 : snapshot2.child("Coach").getChildren()){
                                            String key3 = snapshot3.getKey().toString();
                                            Log.d("TAG_MEMBERCHECK", "key in snapshot3: "+key3 );
                                            for(DataSnapshot snapshot4 : snapshot3.child("Sent_coach_workout").getChildren()){
                                                Log.d("TAG_MEMBERCHECK", "key reached Initial Destination ");
                                                String key2 = snapshot4.getKey().toString();
                                                Log.d("TAG_MEMBERCHECK", "key in snapshot4: "+key2 );
                                                String oldnam = snapshot4.child("member_username").getValue(String.class);
                                                Log.d("TAG_MEMBERCHECK", "member_username: "+oldnam );
                                                if(oldnam != null && oldnam.equals(Member_main.ProfileContents[0])){
                                                    Log.d("TAG_MEMBERCHECK", "key reached final destination! ");
                                                    DatabaseReference replaceval = snapshot4.getRef().child("member_username");
                                                      replaceval.setValue(USERNAME);
                                                     }
                                            }

                                        }

                                    }
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    myRefprofile.updateChildren(updates)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Update successfully completed
                                    Log.d("TAG10", "Data updated successfully");
                                    NonMemberUSER yourClass = new NonMemberUSER();
                                    TextView usernamebar = findViewById(R.id.textView2);
                                    TextView username_nav = findViewById(R.id.username_nav);
                                   yourClass.usertoolbarname(getApplicationContext(), usernamebar, username_nav);

                                   Intent intent = new Intent(Member_Profile.this,Member_main.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                   startActivity(intent);

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Failed to update data
                                    Log.e("TAG11", "Error updating data", e);
                                }
                            });
                }
            }
        });
    }
    public void profileContents(){
        ments = new TextView[5];
        ments[0] = findViewById(R.id.textView8);
        ments[1] = findViewById(R.id.textView9);
        ments[2] = findViewById(R.id.textView10);
        ments[3] = findViewById(R.id.mobile_numberrr);
        ments[4] = findViewById(R.id.textView8_1);
        ments[0].setText(Member_main.ProfileContents[0]);
        ments[1].setText(Member_main.ProfileContents[1]);
        ments[2].setText(Member_main.ProfileContents[2]);
        ments[3].setText(Member_main.ProfileContents[3]);
        ments[4].setText(Member_main.ProfileContents[4]);
        //Profile chg
        chg = new EditText[4];
        chg[0] = findViewById(R.id.editTextUsername_chg);
        chg[1] = findViewById(R.id.editTextEmail_chg);
        chg[2] = findViewById(R.id.editTextPassword_chg);
        chg[3] = findViewById(R.id.editTextMobilenumber);
        chg[0].setText(Member_main.ProfileContents[0]);
        chg[1].setText(Member_main.ProfileContents[1]);
        chg[3].setText(Member_main.ProfileContents[3]);
    }
    public static void openNavbar(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public static void openchg(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.END);
    }
    public static void closeNavbar(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public static void closechg(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.END)){
            drawerLayout.closeDrawer(GravityCompat.END);
        }
    }
    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }
    @Override
    protected void onPause(){
        super.onPause();
        closeNavbar(drawerLayout);
        closechg(drawerLayout);
    }
}
