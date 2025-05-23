package com.example.usernamelogin.Gym_Owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usernamelogin.Admin.Gym.Helper_Gym_adder;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Profile_Main_Gym_Owner extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, Gym_management, profile;
    private TextView[] ments;
    private EditText[] chg;
    String USERNAME, PASSWORD, EMAIL;
    Button changeprof_i, button_chg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main_gym_owner);

        // set toolbar and navbar name to users username
        TextView textView2 = findViewById(R.id.textView2);
        TextView username_nav = findViewById(R.id.username_nav);
        textView2.setText(Gym_Owner_Main.ProfileContents[0]);
        username_nav.setText(Gym_Owner_Main.ProfileContents[0]);

        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        Gym_management = findViewById(R.id.Gym_manage_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);
        changeprof_i = findViewById(R.id.changeP_I);
        button_chg = findViewById(R.id.button_chg);

        profileContents();

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavbar(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Profile_Main_Gym_Owner.this, Gym_Owner_Main.class);
            }
        });
        Gym_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 redirectActivity(Profile_Main_Gym_Owner.this, Gym_Owner_Main.class);
            }
        });
        changeprof_i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openchg(drawerLayout);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        button_chg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase databaseprofile = FirebaseDatabase.getInstance();
                DatabaseReference myRefprofile = databaseprofile.getReference("Users/Gym_Owner").child(Login.key_GymOwner);
                DatabaseReference myRefprofile1 = databaseprofile.getReference("Users/Gym_Owner").child(Login.key_GymOwner)
                        .child("Gym").child(Gym_Owner_Main.key2);

                DatabaseReference gympackage = databaseprofile.getReference("Gym_package").child(Gym_Owner_Main.ProfileContents[3]);
                DatabaseReference MembershipRequest = databaseprofile.getReference("Membership_Request").child(Gym_Owner_Main.ProfileContents[3]);
                DatabaseReference ReservationsOld = databaseprofile.getReference("Reservations/Accepted").child(Gym_Owner_Main.ProfileContents[3]);
                DatabaseReference NonmembersOld = databaseprofile.getReference("Users/Non-members");


                // Create a HashMap to hold the updates you want to make
                String USERNAME = chg[0].getText().toString();
                String EMAIL    = chg[1].getText().toString();
                String PASSWORD = chg[2].getText().toString();
                String GYMNAME = chg[3].getText().toString();
                String GYMDESCRP = chg[4].getText().toString();

                Map<String, Object> updates = new HashMap<>();
                updates.put("gym_owner_username", USERNAME);
                updates.put("gym_owner_email",    EMAIL);
                updates.put("gym_owner_password", PASSWORD);

                Map<String, Object> updates1 = new HashMap<>();
                updates1.put("gym_name",    GYMNAME);
                updates1.put("gym_descrp", GYMDESCRP);

                Helper_Gym_adder user = new Helper_Gym_adder(EMAIL, PASSWORD, USERNAME);
                Log.d("TAG6", "To run on update");
                if(USERNAME.isEmpty() || PASSWORD.isEmpty() || EMAIL.isEmpty()) {

                    Toast.makeText(Profile_Main_Gym_Owner.this,"Enter Texts in the Empty Fields",Toast.LENGTH_SHORT).show();

                }
                else{

                    ProgressDialog progressDialog = new ProgressDialog(Profile_Main_Gym_Owner.this);
                    progressDialog.setMessage("Loading data...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    myRefprofile.updateChildren(updates)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Update successfully completed
                                    Log.d("TAG10", "Data updated successfully");
                                    myRefprofile1.updateChildren(updates1);
                                    //GYM NAME CHANGE OF DB Gym_package
                                    DatabaseReference gympackagenew = databaseprofile.getReference("Gym_package").child(GYMNAME);
                                    gympackage.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                gympackagenew.setValue(snapshot.getValue(),(error, ref) ->{
                                                    if(error == null){
                                                        gympackage.removeValue();
                                                    }
                                                });


                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    //Membership_requests
                                    DatabaseReference membershipreqnew = databaseprofile.getReference("Membership_Request").child(GYMNAME);
                                    MembershipRequest.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                // Step 1: Copy data to new key
                                                membershipreqnew.setValue(snapshot.getValue(), (databaseError, databaseReference) -> {
                                                    if (databaseError == null) {
                                                        // Step 2: Delete old key
                                                        MembershipRequest.removeValue((error, ref) -> {
                                                            if (error == null) {
                                                                Log.d("KEY_RENAME", "Successfully renamed Mamoin1 to YourNewKey");
                                                            } else {
                                                                Log.e("KEY_RENAME", "Failed to delete old key: " + error.getMessage());
                                                            }
                                                        });
                                                    } else {
                                                        Log.e("KEY_RENAME", "Failed to copy data to new key: " + databaseError.getMessage());
                                                    }
                                                });
                                            } else {
                                                Log.d("KEY_RENAME", "Mamoin1 does not exist.");
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    //Reservations
                                    DatabaseReference Reservationsnew = databaseprofile.getReference("Reservations/Accepted").child(GYMNAME);
                                    ReservationsOld.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                Reservationsnew.setValue(snapshot.getValue(), (databaseError, databaseReference) -> {
                                                    if (databaseError == null) {
                                                        ReservationsOld.removeValue((error, ref) -> {
                                                            if (error == null) {
                                                                Log.d("KEY_RENAME", "Successfully renamed reservation key");
                                                            } else {
                                                                Log.e("KEY_RENAME", "Failed to delete old reservation key: " + error.getMessage());
                                                            }
                                                        });
                                                    } else {
                                                        Log.e("KEY_RENAME", "Failed to copy reservation data: " + databaseError.getMessage());
                                                    }
                                                });
                                            } else {
                                                Log.d("KEY_RENAME", "Old reservation key does not exist.");
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    //Users-Non-members

                                    NonmembersOld.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                                // Top-level GymName
                                                if (userSnapshot.hasChild("GymName")) {
                                                    String currentGymName = userSnapshot.child("GymName").getValue(String.class);
                                                    if (currentGymName != null && currentGymName.equals(Gym_Owner_Main.ProfileContents[3])) {
                                                        userSnapshot.getRef().child("GymName").setValue(GYMNAME);
                                                    }
                                                }

                                                // membership/GymName
                                                if (userSnapshot.hasChild("membership")) {
                                                    DataSnapshot membership = userSnapshot.child("membership");
                                                    if (membership.hasChild("GymName")) {
                                                        String currentMembershipGym = membership.child("GymName").getValue(String.class);
                                                        if (currentMembershipGym != null && currentMembershipGym.equals(Gym_Owner_Main.ProfileContents[3])) {
                                                            userSnapshot.getRef().child("membership").child("GymName").setValue(GYMNAME);
                                                        }
                                                    }
                                                }

                                                // positionstored/*/gym_name
                                                if (userSnapshot.hasChild("positionstored")) {
                                                    DataSnapshot positionstored = userSnapshot.child("positionstored");
                                                    for (DataSnapshot positionEntry : positionstored.getChildren()) {
                                                        if (positionEntry.hasChild("gym_name")) {
                                                            String currentStoredName = positionEntry.child("gym_name").getValue(String.class);
                                                            if (currentStoredName != null && currentStoredName.equals(Gym_Owner_Main.ProfileContents[3])) {
                                                                positionEntry.getRef().child("gym_name").setValue(GYMNAME);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            // Handle error here
                                        }
                                    });
                                    progressDialog.dismiss();
                                    redirectActivity(Profile_Main_Gym_Owner.this, Gym_Owner_Main.class);
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
    @SuppressLint("CutPasteId")
    public void profileContents(){
        ments = new TextView[5];
        ments[0] = findViewById(R.id.editTextGym_Owner_Username);
        ments[1] = findViewById(R.id.editTextGym_Owner_EmailAddress);
        ments[2] = findViewById(R.id.editTextGym_Owner_Password);
        ments[3] = findViewById(R.id.editTextGym_name);
        ments[4] = findViewById(R.id.editTextGym_Decrp);
        ments[0].setText(Gym_Owner_Main.ProfileContents[0]);
        ments[1].setText(Gym_Owner_Main.ProfileContents[1]);
        ments[2].setText(Gym_Owner_Main.ProfileContents[2]);
        ments[3].setText(Gym_Owner_Main.ProfileContents[3]); // GYM NAME
        ments[4].setText(Gym_Owner_Main.ProfileContents[4]);
        //Profile chg
        chg = new EditText[5];
        chg[0] = findViewById(R.id.editTextGym_Owner_Username1);
        chg[1] = findViewById(R.id.editTextGym_Owner_EmailAddress1);
        chg[2] = findViewById(R.id.editTextGym_Owner_Password1);
        chg[3] = findViewById(R.id.editTextGym_name1);
        chg[4] = findViewById(R.id.editTextGym_Decrp1);
        chg[0].setText(Gym_Owner_Main.ProfileContents[0]);
        chg[1].setText(Gym_Owner_Main.ProfileContents[1]);
        chg[2].setText(Gym_Owner_Main.ProfileContents[2]);
        chg[3].setText(Gym_Owner_Main.ProfileContents[3]);
        chg[4].setText(Gym_Owner_Main.ProfileContents[4]);
    }
    public static void openchg(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.END);
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