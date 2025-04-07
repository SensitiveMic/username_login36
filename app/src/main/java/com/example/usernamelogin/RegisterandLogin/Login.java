package com.example.usernamelogin.RegisterandLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.usernamelogin.Admin.Admin_main;
import com.example.usernamelogin.Coach.Coach_main;
import com.example.usernamelogin.Gym_Owner.Gym_Owner_Main;
import com.example.usernamelogin.Member.Member_main;
import com.example.usernamelogin.NonMemberUser.NonMemberUSER;
import com.example.usernamelogin.R;
import com.example.usernamelogin.Staff.Staff_main;
import com.example.usernamelogin.databinding.ActivityLoginBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login extends AppCompatActivity {

    public static String key,key_Admin, key_GymOwner;
    public static String key_gym_Staff_key, key_Gym_Staff_username, key_Gym_Staff_mobile_number, key_Gym_Staff_email, key_Gym_Staff_password ;
    public static String key_Gym_Coach_key, key_Gym_Coach_mobile_number,key_Gym_Coach_username,key_Gym_Coach_email,key_Gym_Coach_password;
    public static String key_Gym_Staff1 = null;
    public static String key_Gym_Staff2 = null;
    public static String key_Gym_Staff3 = null;
    public static String key_Gym_Coach1 = null;
    public static String key_Gym_Coach2 = null;
    public static String key_Gym_Coach3 = null;
    public static String key_Gym_;
    String USERNAME, PASSWORD;
    FirebaseDatabase databaseLogin;
    DatabaseReference myRefLogin;
    ActivityLoginBinding binding2;
    DatabaseReference findstaffaccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding2 = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding2.getRoot());

        Button buttonlogin = findViewById(R.id.buttonLogin);

        binding2.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gobacktoReg = new Intent(Login.this,MainActivity.class);

                startActivity(gobacktoReg);
            }
        });
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUSER();
                Log.d("TAG5", "LOGIN CLICKED! ");
            }
        });
    }

    public void checkUSER(){
        Log.d("TAG5", "LOGIN INnside! ");
        USERNAME = binding2.editTextLoginUsername.getText().toString();
        PASSWORD = binding2.editTextLoginPassword.getText().toString();

        databaseLogin = FirebaseDatabase.getInstance();
        myRefLogin = databaseLogin.getReference("Users");

        Query checkUser = myRefLogin.child("Non-members").orderByChild("username")
                                .equalTo(USERNAME);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("TAG5", "Check user  run! ");
                if(snapshot.exists()){

                    binding2.editTextLoginUsername.setError(null);
                    //the for loop is to locate the key of the snapshot
                    for(DataSnapshot gk: snapshot.getChildren()) {
                        key = gk.getKey(); // to make the key into a string
                        Log.e( "TAG",key);
                        // to add the password from the database to a string
                        Integer mem_status = snapshot.child(key).child("membership_status").getValue(Integer.class);
                        if(mem_status == 0){
                            String PasswordfromDB = snapshot.child(key).child("password").getValue(String.class);
                            Log.e( "TAG2","This is password from db: " +PasswordfromDB);
                            Log.e("wee2", "onDataChange: minecraft! ");
                            if(Objects.equals(PasswordfromDB, PASSWORD)){
                               Member_main.Current_GYM = snapshot.child(key).child("Gym Name").getValue(String.class);
                                Log.d( "CONFIRMTAG_wew","This is password from db: " + Member_main.Current_GYM);
                                Intent intent = new Intent(Login.this, Member_main.class);


                                startActivity(intent);
                                Log.d("kasibag", "onDataChange: minecraft! ");
                            }
                            else {
                                binding2.editTextLoginPassword.setError("Wrong password");
                                binding2.editTextLoginPassword.requestFocus();
                                Log.e("kasibag2", "onDataChange: password not found ");
                            }

                        }
                        else{
                            String PasswordfromDB = snapshot.child(key).child("password").getValue(String.class);
                            Log.e( "TAG2",PasswordfromDB);
                            Log.e("wee2", "onDataChange: minecraft! ");
                            if(Objects.equals(PasswordfromDB, PASSWORD)){
                                Intent intent = new Intent(Login.this, NonMemberUSER.class);
                                startActivity(intent);
                                Log.d("kasibag", "onDataChange: minecraft! ");
                            }
                            else {
                                binding2.editTextLoginPassword.setError("Wrong password");
                                binding2.editTextLoginPassword.requestFocus();
                                Log.e("kasibag2", "onDataChange: password not found ");
                            }

                        }

                    }
                } else{
                    Log.d("TAG5", "Non-member pass to admin minecraft! ");
                    checkUSER_admin();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void checkUSER_admin(){

        USERNAME = binding2.editTextLoginUsername.getText().toString();
        PASSWORD = binding2.editTextLoginPassword.getText().toString();

        databaseLogin = FirebaseDatabase.getInstance();
        myRefLogin = databaseLogin.getReference("Users");

        Query checkUser = myRefLogin.child("Admin").orderByChild("username")
                .equalTo(USERNAME);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    binding2.editTextLoginUsername.setError(null);
                    //the for loop is to locate the key of the snapshot
                    for(DataSnapshot gk: snapshot.getChildren()) {
                        key_Admin = gk.getKey(); // to make the key into a string
                        Log.e( "TAG",key_Admin);
                        // to add the password from the database to a string
                        String PasswordfromDB = snapshot.child(key_Admin).child("password").getValue(String.class);
                        Log.e( "TAG2",PasswordfromDB);
                        Log.e("wee2", "onDataChange: minecraft! ");
                        if(Objects.equals(PasswordfromDB, PASSWORD)){
                            Intent intent = new Intent(Login.this, Admin_main.class);
                            startActivity(intent);
                            Log.d("kasibag", "onDataChange: minecraft! ");
                        }
                        else {
                            binding2.editTextLoginPassword.setError("Wrong password");
                            binding2.editTextLoginPassword.requestFocus();
                            Log.e("kasibag2", "onDataChange: password not found ");
                        }
                    }
                } else{
                    checkUSER_Gym_Owner();
                    Log.d("TAG5", "Admin pass to Gym_owner minecraft! ");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void checkUSER_Gym_Owner(){

        USERNAME = binding2.editTextLoginUsername.getText().toString();
        PASSWORD = binding2.editTextLoginPassword.getText().toString();

        databaseLogin = FirebaseDatabase.getInstance();
        myRefLogin = databaseLogin.getReference("Users");

        Query checkUser = myRefLogin.child("Gym_Owner").orderByChild("gym_owner_username")
                .equalTo(USERNAME);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    binding2.editTextLoginUsername.setError(null);
                    //the for loop is to locate the key of the snapshot
                    outerLoop:
                    for(DataSnapshot gk: snapshot.getChildren()) {
                        key_GymOwner = gk.getKey(); // to make the key into a string
                        Log.e( "TAG5",key_GymOwner);
                        // to add the password from the database to a string
                        String PasswordfromDB = snapshot.child(key_GymOwner).child("gym_owner_password").getValue(String.class);
                        Log.e( "TAG5",PasswordfromDB);
                        Log.e("TAG5", "Going to minecraft ");
                        if(Objects.equals(PasswordfromDB, PASSWORD)){
                            Intent intent = new Intent(Login.this, Gym_Owner_Main.class);
                            startActivity(intent);
                            Log.d("TAG5", "onDataChange: minecraft! ");
                            break outerLoop;
                        }
                        else {
                            binding2.editTextLoginPassword.setError("Wrong password");
                            binding2.editTextLoginPassword.requestFocus();
                            Log.e("TAG5", "onDataChange: password not found ");
                        }
                    }
                } else{
                    checkUSER_Staff();
                    Log.d("TAG5", "Gym Owner pass to Staff minecraft! ");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void checkUSER_Staff(){

        USERNAME = binding2.editTextLoginUsername.getText().toString();
        PASSWORD = binding2.editTextLoginPassword.getText().toString();

        databaseLogin = FirebaseDatabase.getInstance();
        myRefLogin = databaseLogin.getReference("Users/Gym_Owner");



        Query query = myRefLogin.orderByKey();

        Log.d("TAG53", "Staff start minecraft! ");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                boolean accountFound = false; // Flag to track if an account is found

                    outerLoop:
                    for (DataSnapshot underGym_Owner : snapshot.getChildren()) {
                        key_Gym_Staff1 = underGym_Owner.getKey();
                        Log.d("TAG53", "1st key :" + key_Gym_Staff1);

                        for (DataSnapshot undergym : underGym_Owner.getChildren()) {
                            key_Gym_Staff2 = undergym.getKey();
                            Log.d("TAG53", "2nd key :" + key_Gym_Staff2);

                            for (DataSnapshot underGymchild : undergym.getChildren()) {
                                key_Gym_Staff3 = underGymchild.getKey();
                                Log.d("TAG53", "3rd key :" + key_Gym_Staff3);
                                key_Gym_ = underGymchild.child("gym_name").getValue(String.class);
                                Log.d("TAG53", "Gyms :" + key_Gym_);

                                for (DataSnapshot insidegymkey : underGymchild.child("Staff").getChildren()) {

                                    if(insidegymkey.child("username").getValue(String.class).equals(USERNAME)){
                                        String usernamestaff = insidegymkey.child("username").getValue(String.class);
                                        key_gym_Staff_key = insidegymkey.getKey();
                                        Log.d("TAG53", "5th key :" + key_gym_Staff_key);
                                        Log.e("TAG53", key_gym_Staff_key);

                                        key_Gym_Staff_password = insidegymkey.child("password").getValue(String.class);
                                        key_Gym_Staff_email = insidegymkey.child("email").getValue(String.class);
                                        key_Gym_Staff_mobile_number = insidegymkey.child("mobile_number").getValue(String.class);
                                        key_Gym_Staff_username = usernamestaff;

                                        if (Objects.equals(key_Gym_Staff_password, PASSWORD)) {
                                            Intent intent = new Intent(Login.this, Staff_main.class);
                                            startActivity(intent);
                                            Log.d("TAG53", "onDataChange: minecraft! ");
                                            accountFound = true; // Account found, set the flag
                                            break outerLoop;

                                        } else {
                                            binding2.editTextLoginPassword.setError("Wrong password");
                                            binding2.editTextLoginPassword.requestFocus();
                                            Log.e("TAG53", "onDataChange: password not found ");
                                        }

                                    }

                                }

                            }
                            break;
                        }

                    }


                if (!accountFound) {
                    checkUser_Coach();
                    Log.d("TAG53", "Gym Staff pass to Coach minecraft! ");
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    public void checkUser_Coach(){
        USERNAME = binding2.editTextLoginUsername.getText().toString();
        PASSWORD = binding2.editTextLoginPassword.getText().toString();

        databaseLogin = FirebaseDatabase.getInstance();
        myRefLogin = databaseLogin.getReference("Users/Gym_Owner");
        Query query = myRefLogin.orderByKey();
        Log.d("TAG5", "Coach method start minecraft! ");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                boolean accountFound_coach = false; // Flag to track if an account is found

                    outerLoop:
                    for (DataSnapshot underGym_Owner : snapshot.getChildren()) {
                        key_Gym_Coach1 = underGym_Owner.getKey();
                        Log.d("TAG5", "1st key :" + key_Gym_Coach1);

                        for (DataSnapshot undergym : underGym_Owner.getChildren()) {
                            key_Gym_Coach2 = undergym.getKey();
                            Log.d("TAG5", "2nd key :" + key_Gym_Coach2);

                            for (DataSnapshot underGymchild : undergym.getChildren()) {
                                key_Gym_Coach3 = underGymchild.getKey();
                                Log.d("TAG5", "3rd key :" + key_Gym_Coach3);
                                key_Gym_ = underGymchild.child("gym_name").getValue(String.class);


                                for (DataSnapshot insidegymkey : underGymchild.child("Coach").getChildren()) {

                                    if(insidegymkey.child("username").getValue(String.class).equals(USERNAME)){
                                     String usernamestaff = insidegymkey.child("username").getValue(String.class);
                                      key_Gym_Coach_key = insidegymkey.getKey();
                                      Log.d("TAG53", "5th key :" + key_Gym_Coach_key);
                                      Log.e("TAG53", key_Gym_Coach_key);

                                       key_Gym_Coach_password = insidegymkey.child("password").getValue(String.class);
                                       key_Gym_Coach_email = insidegymkey.child("email").getValue(String.class);
                                       key_Gym_Coach_mobile_number = insidegymkey.child("mobile_number").getValue(String.class);
                                       key_Gym_Coach_username = usernamestaff;

                                         if (Objects.equals(key_Gym_Coach_password, PASSWORD)) {
                                           Intent intent = new Intent(Login.this, Coach_main.class);
                                           startActivity(intent);
                                           Log.d("TAG53", "onDataChange: minecraft! ");
                                           accountFound_coach = true; // Account found, set the flag
                                           break outerLoop;

                                        } else {
                                            binding2.editTextLoginPassword.setError("Wrong password");
                                            binding2.editTextLoginPassword.requestFocus();
                                            Log.e("TAG53", "onDataChange: password not found ");
                                         }


                                         }


                                }

                            }
                            break;
                        }

                    }


                if (!accountFound_coach) {
                    binding2.editTextLoginUsername.setError("No user found");
                    binding2.editTextLoginUsername.requestFocus();
                }

                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}