package com.example.usernamelogin.Admin.Gym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.usernamelogin.Admin.Admin_main;
import com.example.usernamelogin.Admin.Userslist.gymanditsmembers.UsersList_Admin_main;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Admin_add_gym extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, reservations, add_gym,logoput;
    Button add_gym_entity;
    EditText Gym_owner_name, Gym_owneremail,Gym_owner_password, firstname,lastname;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_gym);
        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        reservations = findViewById(R.id.Reservations_navdrawer);
        add_gym = findViewById(R.id.add_gym_navdrawer);
        logoput = findViewById(R.id.logout_Button_U);

        //for adding gym entity
        Gym_owner_name = findViewById(R.id.editTextGym_Owner_Username);
        Gym_owneremail = findViewById(R.id.editTextGym_Owner_EmailAddress);
        Gym_owner_password = findViewById(R.id.editTextGym_Owner_Password);
        add_gym_entity = findViewById(R.id.button_add_gym_entity);

        firstname = findViewById(R.id.editTextGym_Owner_firstname);
        lastname = findViewById(R.id.editTextGym_Owner_Lastname);


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
        logoput.setOnClickListener(v ->{
            logout_prc(Admin_add_gym.this, Login.class);

        });
        add_gym_entity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database ;
                DatabaseReference myRef;

                DatabaseReference addedmyRef ;

                String Gym_owner1_name= Gym_owner_name.getText().toString();
                String Gym_owner1_password= Gym_owner_password.getText().toString();
                String Gym_owneremail1 = Gym_owneremail.getText().toString();

                String gym_own_firstname = firstname.getText().toString();
                String gym_own_lastname = lastname.getText().toString();

                if(Gym_owner1_name.isEmpty() || Gym_owner1_password.isEmpty() || Gym_owneremail1.isEmpty() ) {

                    Toast.makeText(Admin_add_gym.this,"Enter Texts in the Empty Fields",Toast.LENGTH_SHORT).show();

                }
                else{

                    database = FirebaseDatabase.getInstance();
                    myRef = database.getReference("Users/Gym_Owner");
                    addedmyRef = myRef.push(); //parent
                    String userId = addedmyRef.getKey();

                    Helper_Gym_adder user = new Helper_Gym_adder(Gym_owneremail1, Gym_owner1_password, Gym_owner1_name,gym_own_firstname,gym_own_lastname );

                    addedmyRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            redirectActivity(Admin_add_gym.this, Admin_main.class);
                            Toast.makeText(Admin_add_gym.this,"successfully updated",Toast.LENGTH_SHORT).show();
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
    private void openNavbar(DrawerLayout drawerLayout){
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



}