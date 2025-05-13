package com.example.usernamelogin.Gym_Owner.Gym_manage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.usernamelogin.Admin.Admin_helper2;
import com.example.usernamelogin.Gym_Owner.Gym_Owner_Main;
import com.example.usernamelogin.Gym_Owner.Gym_Owner_gymmanagement_add_staff;
import com.example.usernamelogin.Gym_Owner.Profile_Main_Gym_Owner;
import com.example.usernamelogin.Gym_Owner.employeelist.employeelists_main;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add_Gym_with_details extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, Gym_management, profile ,gymemployyes,Gym_manage, logoput;
    EditText Gym_name,gym_contact_number,Gym_descp;
    Button confirmbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gym_with_details);

        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        Gym_management = findViewById(R.id.Gym_manage_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);
        gymemployyes = findViewById(R.id.GYymemployyelist);
        logoput = findViewById(R.id.logout_Button_U);
        Gym_manage = findViewById(R.id.Gym_manage_Manage);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavbar(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Add_Gym_with_details.this, Gym_Owner_Main.class);
            }
        });
        Gym_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Add_Gym_with_details.this, Gym_Owner_gymmanagement_add_staff.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Add_Gym_with_details.this, Profile_Main_Gym_Owner.class);
            }
        });
        gymemployyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Add_Gym_with_details.this, employeelists_main.class);
            }
        });
        Gym_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Add_Gym_with_details.this, Gym_Owner_gymmanage_main.class);
            }
        });
        logoput.setOnClickListener(v ->{
            logout_prc(Add_Gym_with_details.this, Login.class);

        });

     //   confirmbutton = findViewById(R.id.Confirm);
        Gym_name = findViewById(R.id.editTextGym_Name);
        Gym_descp = findViewById(R.id.editTextGym_Decrp);

        gym_contact_number= findViewById(R.id.editTextGym_Contact_number);

     /*   confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Abstract_class_edit_gym_operatingschedule listdialog = new Abstract_class_edit_gym_operatingschedule(Add_Gym_with_details.this) {
                    @Override
                    protected void onCreate(Bundle savedInstanceState) {
                        super.onCreate(savedInstanceState);
                    }
                };
                listdialog.setCancelable(true);
                listdialog.show();
                new Handler(Looper.getMainLooper()).post(() -> {
                    Window window = listdialog.getWindow();
                    if (window != null) {
                        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    }
                });

            }
        });   */
    }
    private void addgym_details(){
        String Gym_name1 = Gym_name.getText().toString();
        String Gym_descp1 = Gym_descp.getText().toString();
        String Gym_contactnumber = gym_contact_number.getText().toString();
        if(Gym_name1.isEmpty()  || Gym_contactnumber.isEmpty()) {
            Toast.makeText(this,"Enter Texts in the Empty Fields",Toast.LENGTH_SHORT).show();
        }else{

            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users/Gym_Owner").child(Login.key_GymOwner)
                    .child("Gym");
            DatabaseReference addedmyRef = myRef.push(); //parent
            String userId = addedmyRef.getKey();
            Admin_helper2 gymData = new Admin_helper2(
                    Gym_name1,
                    Gym_descp1,
                    null,
                    Gym_contactnumber,
                    null,
                    null
            );
            addedmyRef.setValue(gymData).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    redirectActivity(Add_Gym_with_details.this, Gym_Owner_gymmanagement_add_staff.class);
                } else {
                    Toast.makeText(this, "Failed to add gym.", Toast.LENGTH_SHORT).show();
                }
            });
        }

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

    }

    @Override
    protected void onPause() {
        super.onPause();
        closeNavbar(drawerLayout);
    }
}