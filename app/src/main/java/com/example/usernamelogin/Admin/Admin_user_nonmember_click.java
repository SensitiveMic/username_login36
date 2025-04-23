package com.example.usernamelogin.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.usernamelogin.Admin.Userslist.gymanditsmembers.UsersList_Admin_main;
import com.example.usernamelogin.R;
import com.example.usernamelogin.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Admin_user_nonmember_click extends AppCompatActivity {
    Button button_chg ;
    FirebaseDatabase databaseprofile ;
    DatabaseReference myRefprofile ;
    private EditText[] chg ;
    String key1;
    ImageView goback;
    int valueToSave;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_nonmember_click);

        chg = new EditText[4];
        chg[0] = findViewById(R.id.editTextUsername_chg);
        chg[1] = findViewById(R.id.editTextEmail_chg);
        chg[2] = findViewById(R.id.editTextPassword_chg);
        chg[3] = findViewById(R.id.editTextMobilenumber);
        chg[0].setText(Admin_main.non_member_username);
        button_chg = findViewById(R.id.button_chg);
        goback = findViewById(R.id.go_back);
        progressBar = findViewById(R.id.progressBar2);
        CheckBox myCheckBox = findViewById(R.id.myCheckBox);

        myCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            valueToSave = isChecked ? 1 : 0;

        });

        checkUSER();

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //redirectActivity(Admin_user_nonmember_click.this, UsersList_Admin_main.class);
            }
        });

        button_chg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseprofile = FirebaseDatabase.getInstance();
                myRefprofile = databaseprofile.getReference("Users/Non-members").child(key1);

                // Create a HashMap to hold the updates you want to make
                String USERNAME = chg[0].getText().toString();
                String EMAIL    = chg[1].getText().toString();
                String PASSWORD = chg[2].getText().toString();
                String MOBILENUMBER = chg[3].getText().toString();

                Map<String, Object> updates = new HashMap<>();
                updates.put("username", USERNAME);
                updates.put("email",    EMAIL);
                updates.put("password", PASSWORD);
                updates.put("mobile_number", MOBILENUMBER);

                Users user = new Users(USERNAME, PASSWORD, EMAIL, MOBILENUMBER);
                Log.d("TAG6", "To run on update");

                DatabaseReference ban_check = databaseprofile.getReference("Users/Non-members").child("ban_status");
               // ban_check.setValue(valueToSave);

                DatabaseReference underGym_owner = databaseprofile.getReference("Users/Gym_Owner");
                DatabaseReference under_Reservations = databaseprofile.getReference("Reservations/Accepted");
                DatabaseReference under_Membership_req = databaseprofile.getReference("Membership_Request");

                if(USERNAME.isEmpty() || PASSWORD.isEmpty() || EMAIL.isEmpty() || MOBILENUMBER.isEmpty()) {

                    Toast.makeText(Admin_user_nonmember_click.this,"Enter Texts in the Empty Fields",Toast.LENGTH_SHORT).show();

                }
                else if (MOBILENUMBER.length() != 11 || !MOBILENUMBER.startsWith("09")) {
                    Toast.makeText(Admin_user_nonmember_click.this, "Enter a valid mobile number starting with 09 and 11 digits long", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    //UnderGym_Owner
                    underGym_owner.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ownerSnap : snapshot.getChildren()) {
                                String ownerKey = ownerSnap.getKey();
                                if (ownerSnap.hasChild("Gym")) {
                                    for (DataSnapshot gymSnap : ownerSnap.child("Gym").getChildren()) {
                                        String gymKey = gymSnap.getKey();
                                        if (gymSnap.hasChild("Coach")) {
                                            for (DataSnapshot coachSnap : gymSnap.child("Coach").getChildren()) {
                                                String coachKey = coachSnap.getKey();
                                                if (coachSnap.hasChild("Sent_coach_workout")) {
                                                    for (DataSnapshot workoutSnap : coachSnap.child("Sent_coach_workout").getChildren()) {
                                                        String workoutKey = workoutSnap.getKey();
                                                        if (workoutSnap.hasChild("member_username")) {
                                                            String currentMemberUsername = workoutSnap.child("member_username").getValue(String.class);

                                                            if (currentMemberUsername != null && currentMemberUsername.equals(Admin_main.non_member_username)) {
                                                                // Replace old username with new USERNAME
                                                                workoutSnap.getRef().child("member_username").setValue(USERNAME);
                                                                Log.d("FirebaseUpdate", "Replaced member_username under: " +
                                                                        "Users/Gym_Owner/" + ownerKey + "/Gym/" + gymKey +
                                                                        "/Coach/" + coachKey + "/Sent_coach_workout/" + workoutKey);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
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

                                            if (userVal != null && userVal.equals(Admin_main.non_member_username)) {
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

                                        if (userVal != null && userVal.equals(Admin_main.non_member_username)) {
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
                    myRefprofile.updateChildren(updates)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    // Update successfully completed

                                    Log.d("TAG10", "Data updated successfully");
                                    progressBar.setVisibility(View.GONE);
                                    redirectActivity(Admin_user_nonmember_click.this, Admin_main.class);
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
    public void checkUSER() {

        FirebaseDatabase databaseLogin = FirebaseDatabase.getInstance();
        DatabaseReference myRefLogin = databaseLogin.getReference("Users");

        Query checkUser = myRefLogin.child("Non-members").orderByChild("username")
                .equalTo(Admin_main.non_member_username);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot gk : snapshot.getChildren()) {

                    key1 = gk.getKey(); // to make the key into a string
                    Log.e("TAG", key1);
                    // to add the password from the database to a string
                    String PasswordfromDB = snapshot.child(key1).child("password").getValue(String.class);
                    String Email = snapshot.child(key1).child("email").getValue(String.class);
                    String mobilenumber = snapshot.child(key1).child("mobile").getValue(String.class);
                    chg[1].setText(Email);
                    chg[2].setText(PasswordfromDB);
                    chg[3].setText(mobilenumber);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }


}