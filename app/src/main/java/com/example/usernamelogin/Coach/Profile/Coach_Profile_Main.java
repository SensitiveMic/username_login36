package com.example.usernamelogin.Coach.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
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

import com.example.usernamelogin.Coach.Coach_main;
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

public class Coach_Profile_Main extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, Gym_management, profile;
    Button changeprof_i, button_frchg;
    private EditText[] chg;
    private TextView[] ments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_profile_main);

        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);
        changeprof_i = findViewById(R.id.changeP_I);
        button_frchg = findViewById(R.id.button_chg);

        ments = new TextView[5];
        ments[0] = findViewById(R.id.textView8_coach);
        ments[1] = findViewById(R.id.textView9_coach);
        ments[2] = findViewById(R.id.textView10_coach);
        ments[3] = findViewById(R.id.mobile_numberforcoach);
        ments[4] = findViewById(R.id.textView8_coach_fullname);
        ments[0].setText(Coach_main.ProfileContents[0]);
        ments[1].setText(Coach_main.ProfileContents[1]);
        ments[2].setText(Coach_main.ProfileContents[2]);
        ments[3].setText(Coach_main.ProfileContents[3]);
        ments[4].setText(Coach_main.ProfileContents[4]);

        chg = new EditText[4];
        chg[0] = findViewById(R.id.editTextUsername_chg);
        chg[1] = findViewById(R.id.editTextEmail_chg);
        chg[2] = findViewById(R.id.editTextPassword_chg);
        chg[3] = findViewById(R.id.editTextmob_num_chg);
        chg[0].setText(Coach_main.ProfileContents[0]);
        chg[1].setText(Coach_main.ProfileContents[1]);
        chg[2].setText(Coach_main.ProfileContents[2]);
        chg[3].setText(Coach_main.ProfileContents[3]);

        changeprof_i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openchg(drawerLayout);
            }
        });
        CheckBox showPasswordCheckBox = findViewById(R.id.showPasswordCheckBox);
        CheckBox showPasswordCheckBoxProfile = findViewById(R.id.showPasswordCheckBoxProfile_coach);
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
        button_frchg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase databaseprofile = FirebaseDatabase.getInstance();
                DatabaseReference myRefprofile = databaseprofile.getReference("Users/Gym_Owner")
                        .child(Login.key_Gym_Coach1)
                        .child(Login.key_Gym_Coach2)
                        .child(Login.key_Gym_Coach3)
                        .child("Coach")
                        .child(Login.key_Gym_Coach_key);

                DatabaseReference completed_res_logs = databaseprofile.getReference("Users/Non-members");

                String USERNAME,EMAIL,PASSWORD, MOB_NUM;
                // Create a HashMap to hold the updates you want to make
                USERNAME = chg[0].getText().toString();
                EMAIL    = chg[1].getText().toString();
                PASSWORD = chg[2].getText().toString();
                MOB_NUM = chg[3].getText().toString();

                Map<String, Object> updates = new HashMap<>();
                updates.put("username", USERNAME);
                updates.put("email",    EMAIL);
                updates.put("password", PASSWORD);
                updates.put("mobile_number", MOB_NUM);

                Users user = new Users(USERNAME, PASSWORD, EMAIL,MOB_NUM );
                Log.d("TAG6", "To run on update");
                if(USERNAME.isEmpty() || PASSWORD.isEmpty() || EMAIL.isEmpty() || MOB_NUM.isEmpty()) {

                    Toast.makeText(Coach_Profile_Main.this,"Enter Texts in the Empty Fields",Toast.LENGTH_SHORT).show();

                }
                else{

                    completed_res_logs.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()){
                                String namesnap = snapshot1.getKey().toString();
                                if(snapshot1.child("Coach_Reservation").exists()){
                                    Log.d("TAGWORKING", "Coach_Reservation exists under: "+namesnap);
                                    for(DataSnapshot snapshot2 :snapshot1.child("Coach_Reservation").child("Completed_res_logs").getChildren()){
                                        String snap2 = snapshot2.getKey().toString();
                                        Log.d("TAGWORKING", "Keys running: " +snap2);
                                        if(snapshot2.child("coach_name").exists()){
                                            String coachName = snapshot2.child("coach_name").getValue(String.class);

                                            if (coachName != null && coachName.equals(Coach_main.ProfileContents[0])) {
                                                DatabaseReference coachNameRef = snapshot2.getRef().child("coach_name");
                                                coachNameRef.setValue(USERNAME);
                                            }
                                        }

                                    }  //Reservation_Applications
                                    for(DataSnapshot snapshot23 :snapshot1.child("Coach_Reservation").child("Reservation_Applications").getChildren()){
                                        String snap22 = snapshot23.getKey().toString();
                                        Log.d("TAGWORKING", "Keys running from Reserv_app: " + snap22);

                                            if (snap22 != null && snap22.equals(Coach_main.ProfileContents[0])) {
                                                Object value = snapshot23.getValue();
                                                DatabaseReference parentRef = snapshot1.getRef()
                                                        .child("Coach_Reservation")
                                                        .child("Reservation_Applications");

                                                if (!Coach_main.ProfileContents[0].equals(USERNAME)) {
                                                    parentRef.child(USERNAME).setValue(value).addOnCompleteListener(task -> {
                                                        if (task.isSuccessful()) {
                                                            // Delete the old key only after a successful write
                                                            parentRef.child(Coach_main.ProfileContents[0]).removeValue();

                                                        } else {
                                                            Log.e("TAGWORKING", "Failed to write new key");
                                                        }
                                                    });
                                                } else {
                                                    Log.d("TAGWORKING", "Old key and new key are the same. No changes made.");
                                                }

                                        }

                                    }  //
                                    for(DataSnapshot snapshot1_2 :snapshot1.child("Coach_Reservation").child("Current_Accepted_Res").getChildren()){

                                        String snap2 = snapshot1_2.getKey().toString();
                                        Log.d("TAGWORKING", "Keys running: " +snap2);
                                        if(snapshot1_2.child("coach_name").exists()){
                                            String coachName = snapshot1_2.child("coach_name").getValue(String.class);

                                            if (coachName != null && coachName.equals(Coach_main.ProfileContents[0])) {
                                                DatabaseReference coachNameRef = snapshot1_2.getRef().child("coach_name");
                                                coachNameRef.setValue(USERNAME);
                                            }
                                        }

                                    }


                                }
                                else {
                                    Log.e("TAGWORKING", "Coach_Reservation does NOT exist under this key: " + namesnap);
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
                                    myRefprofile.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String usernamestaff = snapshot.child("username").getValue(String.class);
                                            Log.d("TAG5", "username :" + usernamestaff);
                                            String key_Gym_Staff5 = snapshot.getKey();
                                            String usernum = snapshot.child("username").getValue(String.class);
                                            String emal = snapshot.child("email").getValue(String.class);
                                            String passwrd = snapshot.child("password").getValue(String.class);
                                            String mobnum = snapshot.child("mobile_number").getValue(String.class);


                                            ments[0].setText(usernum);
                                            ments[1].setText(emal);
                                            ments[2].setText(passwrd);
                                            ments[3].setText(mobnum);
                                            chg[0].setText(usernum);
                                            chg[1].setText(emal);
                                            chg[2].setText(passwrd);
                                            chg[3].setText(mobnum);

                                            drawerLayout.closeDrawer(GravityCompat.END);
                                            Intent intent = new Intent(Coach_Profile_Main.this, Coach_main.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
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

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavbar(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Coach_Profile_Main.this, Coach_main.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });

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
        activity.finish();
    }
    @Override
    protected void onPause(){
        super.onPause();
        closeNavbar(drawerLayout);
        closechg(drawerLayout);
    }

}