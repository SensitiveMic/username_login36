package com.example.usernamelogin.Gym_Owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                DatabaseReference myRefprofile = databaseprofile.getReference("Users/Gym_Owner").child(Gym_Owner_Main.key1);
                DatabaseReference myRefprofile1 = databaseprofile.getReference("Users/Gym_Owner").child(Gym_Owner_Main.key1)
                        .child("Gym").child(Gym_Owner_Main.key2);
                // Create a HashMap to hold the updates you want to make
                String USERNAME = chg[0].getText().toString();
                String EMAIL    = chg[1].getText().toString();
                String PASSWORD = chg[2].getText().toString();
                String GYMNAME = chg[3].getText().toString();
                String GYMDESCRP = chg[4].getText().toString();

                Map<String, Object> updates = new HashMap<>();
                updates.put("gym_owner_username", USERNAME);
                updates.put("email",    EMAIL);
                updates.put("password", PASSWORD);

                Map<String, Object> updates1 = new HashMap<>();
                updates1.put("gym_name",    GYMNAME);
                updates1.put("gym_descrp", GYMDESCRP);

                Helper_Gym_adder user = new Helper_Gym_adder(EMAIL, PASSWORD, USERNAME);
                Log.d("TAG6", "To run on update");
                if(USERNAME.isEmpty() || PASSWORD.isEmpty() || EMAIL.isEmpty()) {

                    Toast.makeText(Profile_Main_Gym_Owner.this,"Enter Texts in the Empty Fields",Toast.LENGTH_SHORT).show();

                }
                else{
                    myRefprofile.updateChildren(updates)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Update successfully completed
                                    Log.d("TAG10", "Data updated successfully");
                                    myRefprofile1.updateChildren(updates1);

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
        ments[3].setText(Gym_Owner_Main.ProfileContents[3]);
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