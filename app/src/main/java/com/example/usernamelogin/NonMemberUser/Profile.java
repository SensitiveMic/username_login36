package com.example.usernamelogin.NonMemberUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usernamelogin.Member.Member_main;
import com.example.usernamelogin.NonMemberUser.Gym_prop.Gym_Properties_Main;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.example.usernamelogin.NonMemberUser.Reservations.Reservations;

import com.example.usernamelogin.Users;
import com.example.usernamelogin.workout_program.workouts.User_workouts;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    String USERNAME, PASSWORD, EMAIL,MOBILENUMBER;
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, reservations, profile, gym_membership,workout,logoput;
    Button changeprof_i;
    private TextView[] ments;
    private EditText[] chg;
    private
    FirebaseDatabase databaseprofile ;
    DatabaseReference myRefprofile ;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        progressBar = findViewById(R.id.progressBar3);

        // set toolbar and navbar name to users username
        TextView textView2 = findViewById(R.id.textView2);
        TextView username_nav = findViewById(R.id.username_nav);
        if (Member_main.ProfileContents != null && Member_main.ProfileContents.length >= 4) {
            textView2.setText(Member_main.ProfileContents[0]);
            username_nav.setText(Member_main.ProfileContents[0]);
            // and so on...
        } else {
            username_nav.setText("");
            textView2.setText("");
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
        workout = findViewById(R.id.member_workout);
        logoput = findViewById(R.id.logout_Button_U);

        profileContents();

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
                redirectActivity(Profile.this, Gym_Properties_Main.class);
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
                redirectActivity(Profile.this, NonMemberUSER.class);
            }
        });
        reservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Profile.this, Reservations.class);
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
        workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Profile.this, User_workouts.class);
            }
        });
        logoput.setOnClickListener(v ->{
            logout_prc(Profile.this, Login.class);

        });
        button_chg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pushkey = Login.key;
                databaseprofile = FirebaseDatabase.getInstance();
                myRefprofile = databaseprofile.getReference("Users/Non-members").child(pushkey);

                DatabaseReference under_Reservations = databaseprofile.getReference("Reservations/Accepted");
                DatabaseReference under_Reservations_pend = databaseprofile.getReference("Reservations/Pending_Requests");
                DatabaseReference under_Reservations_Pending = databaseprofile.getReference("Reservations/Pending_Requests");
                DatabaseReference under_Membership_req = databaseprofile.getReference("Membership_Request");
                DatabaseReference under_non_member_gymres = myRefprofile.child("Non_member_Gym_res");

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

                Users user = new Users(USERNAME, PASSWORD, EMAIL, MOBILENUMBER);
                Log.d("TAG6", "To run on update");
                if(USERNAME.isEmpty() || PASSWORD.isEmpty() || EMAIL.isEmpty() || MOBILENUMBER.isEmpty())  {

                    Toast.makeText(Profile.this,"Enter Texts in the Empty Fields",Toast.LENGTH_SHORT).show();

                }
                else{

                    progressBar.setVisibility(View.VISIBLE);
                    //Under pending reservations
                    under_Reservations_pend.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot resSnap : snapshot.getChildren()) {
                                String resKey = resSnap.getKey();  // Get the reservation key

                                for (DataSnapshot entry : resSnap.getChildren()) {
                                    String entryKey = entry.getKey();  // Get the entry key

                                    if (entry.hasChild("user")) {
                                        String userVal = entry.child("user").getValue(String.class);

                                        if (userVal != null && userVal.equals(NonMemberUSER.ProfileContents[0])) {
                                            // Replace the old username with the new USERNAME
                                            entry.getRef().child("user").setValue(USERNAME);

                                        }
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    //Under Reservations
                    under_Reservations.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot resSnap : snapshot.getChildren()) {
                                String resKey = resSnap.getKey();  // Get the reservation key

                                for (DataSnapshot entry : resSnap.getChildren()) {
                                    String entryKey = entry.getKey();  // Get the entry key

                                    if (entry.hasChild("user")) {
                                        String userVal = entry.child("user").getValue(String.class);

                                        if (userVal != null && userVal.equals(NonMemberUSER.ProfileContents[0])) {
                                            // Replace the old username with the new USERNAME
                                            entry.getRef().child("user").setValue(USERNAME);

                                        }
                                    }
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("FirebaseUpdate", "Error: " + error.getMessage());
                        }
                    });
                    //Under Membership_Requests
                    under_Membership_req.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot resSnap : snapshot.getChildren()) {
                                String resKey = resSnap.getKey();  // Get the reservation key

                                for (DataSnapshot entry : resSnap.getChildren()) {
                                    String entryKey = entry.getKey();  // Get the entry key

                                    if (entry.hasChild("username")) {
                                        String userVal = entry.child("username").getValue(String.class);

                                        if (userVal != null && userVal.equals(NonMemberUSER.ProfileContents[0])) {
                                            // Replace the old username with the new USERNAME
                                            entry.getRef().child("username").setValue(USERNAME);

                                        }
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                    //Under Pending membership Requests
                    under_Reservations_Pending.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot resSnap : snapshot.getChildren()) {
                                String resKey = resSnap.getKey();  // Get the reservation key

                                for (DataSnapshot entry : resSnap.getChildren()) {
                                    String entryKey = entry.getKey();  // Get the entry key

                                    if (entry.hasChild("user")) {
                                        String userVal = entry.child("user").getValue(String.class);

                                        if (userVal != null && userVal.equals(NonMemberUSER.ProfileContents[0])) {
                                            // Replace the old username with the new USERNAME
                                            entry.getRef().child("user").setValue(USERNAME);

                                        }
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    //Under non_member_res
                    under_non_member_gymres.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                    String key = snapshot1.getKey().toString();
                                    String usernamE = snapshot1.child("user").getValue().toString();
                                    if (usernamE != null && usernamE.equals(NonMemberUSER.ProfileContents[0])){
                                        DatabaseReference newnamE = under_non_member_gymres.child(key).child("user");
                                        newnamE.setValue(USERNAME);
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

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Failed to update data

                                    progressBar.setVisibility(View.GONE);
                                    NonMemberUSER yourClass = new NonMemberUSER();
                                    TextView usernamebar = findViewById(R.id.textView2);
                                    TextView username_nav = findViewById(R.id.username_nav);
                                    yourClass.usertoolbarname(getApplicationContext(), usernamebar, username_nav);
                                    Log.e("TAG11", "Error updating data", e);
                                }
                            });
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(Profile.this, NonMemberUSER.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
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
    public void profileContents(){
        ments = new TextView[5];
        ments[0] = findViewById(R.id.textView8); // username
        ments[1] = findViewById(R.id.textView9); // email
        ments[2] = findViewById(R.id.textView10);  //passwird
        ments[3] = findViewById(R.id.mobile_numberrr); //mobile number
        ments[4] = findViewById(R.id.textiview_full); // fullname
        ments[0].setText(NonMemberUSER.ProfileContents[0]);
        ments[1].setText(NonMemberUSER.ProfileContents[1]);
        ments[2].setText(NonMemberUSER.ProfileContents[2]);
        ments[3].setText(NonMemberUSER.ProfileContents[3]);
        ments[4].setText(NonMemberUSER.ProfileContents[4]);
        //Profile chg
        chg = new EditText[4];
        chg[0] = findViewById(R.id.editTextUsername_chg);
        chg[1] = findViewById(R.id.editTextEmail_chg);
        chg[2] = findViewById(R.id.editTextPassword_chg);
        chg[3] = findViewById(R.id.editTextMobilenumber);
        chg[0].setText(NonMemberUSER.ProfileContents[0]);
        chg[1].setText(NonMemberUSER.ProfileContents[1]);
        chg[2].setText(NonMemberUSER.ProfileContents[2]);
        chg[3].setText(NonMemberUSER.ProfileContents[3]);
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