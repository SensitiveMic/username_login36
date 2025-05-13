package com.example.usernamelogin.Gym_Owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.usernamelogin.Gym_Owner.Gym_manage.Gym_Owner_gymmanage_main;
import com.example.usernamelogin.Gym_Owner.employeelist.employeelists_main;
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
    LinearLayout home, Gym_management, profile, logoput,Gym_manage,gymemployyes;
    private TextView[] ments;
    private EditText[] chg;
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
        logoput = findViewById(R.id.logout_Button_U);
        Gym_manage = findViewById(R.id.Gym_manage_Manage);
        gymemployyes = findViewById(R.id.GYymemployyelist);

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
                 redirectActivity(Profile_Main_Gym_Owner.this, Gym_Owner_gymmanagement_add_staff.class);
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
        gymemployyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Profile_Main_Gym_Owner.this, employeelists_main.class);
            }
        });
        logoput.setOnClickListener(v ->{
            logout_prc(Profile_Main_Gym_Owner.this, Login.class);
        });
        Gym_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Profile_Main_Gym_Owner.this, Gym_Owner_gymmanage_main.class);
            }
        });
        button_chg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase databaseprofile = FirebaseDatabase.getInstance();
                DatabaseReference myRefprofile = databaseprofile.getReference("Users/Gym_Owner").child(Login.key_GymOwner);

                // Create a HashMap to hold the updates you want to make
                String USERNAME = chg[0].getText().toString();
                String EMAIL    = chg[1].getText().toString();
                String PASSWORD = chg[2].getText().toString();

                Map<String, Object> updates = new HashMap<>();
                updates.put("gym_owner_username", USERNAME);
                updates.put("gym_owner_email",    EMAIL);
                updates.put("gym_owner_password", PASSWORD);


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
    @SuppressLint("CutPasteId")
    public void profileContents(){
        ments = new TextView[7];
        ments[0] = findViewById(R.id.editTextGym_Owner_Username);
        ments[1] = findViewById(R.id.editTextGym_Owner_EmailAddress);
        ments[2] = findViewById(R.id.editTextGym_Owner_Password);
     //   ments[3] = null;
     //   ments[4] = findViewById(R.id.editTextGym_Decrp);
        ments[5] = findViewById(R.id.editTextGym_Owner_FirstName);
        ments[6] = findViewById(R.id.editTextGym_Owner_lastName);

        ments[0].setText(Gym_Owner_Main.ProfileContents[0]);
        ments[1].setText(Gym_Owner_Main.ProfileContents[1]);
        ments[2].setText(Gym_Owner_Main.ProfileContents[2]);
       // ments[3].setText(Gym_Owner_Main.ProfileContents[3]); // GYM NAME
      //  ments[4].setText(Gym_Owner_Main.ProfileContents[4]);
        ments[5].setText(Gym_Owner_Main.ProfileContents[5]);
        ments[6].setText(Gym_Owner_Main.ProfileContents[6]);
        //Profile chg
        chg = new EditText[5];
        chg[0] = findViewById(R.id.editTextGym_Owner_Username1);
        chg[1] = findViewById(R.id.editTextGym_Owner_EmailAddress1);
        chg[2] = findViewById(R.id.editTextGym_Owner_Password1);
        chg[0].setText(Gym_Owner_Main.ProfileContents[0]);
        chg[1].setText(Gym_Owner_Main.ProfileContents[1]);
        chg[2].setText(Gym_Owner_Main.ProfileContents[2]);

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