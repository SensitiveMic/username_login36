package com.example.usernamelogin.RegisterandLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.usernamelogin.Admin.Admin_main;
import com.example.usernamelogin.Coach.Coach_main;
import com.example.usernamelogin.Gym_Owner.Gym_Owner_Main;
import com.example.usernamelogin.Gym_Owner.Profile_Main_Gym_Owner;
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
    public static String key_gym_Staff_key, key_Gym_Staff_username, key_Gym_Staff_mobile_number, key_Gym_Staff_email, key_Gym_Staff_password ,key_Gym_staff_fullname ;
    public static String key_Gym_Coach_key, key_Gym_Coach_mobile_number,key_Gym_Coach_username,key_Gym_Coach_email,key_Gym_Coach_password,key_Gym_Coach_fullname;
    public static String key_Gym_Staff1 = null;
    public static String key_Gym_Staff2 = null;
    public static String key_Gym_Staff3 = null;
    public static String key_Gym_Coach1 = null;
    public static String key_Gym_Coach2 = null;
    public static String key_Gym_Coach3 = null;
    public static String key_Gym_,member_gym_name;
    String USERNAME, PASSWORD;
    FirebaseDatabase databaseLogin;
    DatabaseReference myRefLogin;
    ActivityLoginBinding binding2;
    DatabaseReference findstaffaccount;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding2 = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding2.getRoot());

        progressBar = findViewById(R.id.progressBar);
        Button buttonlogin = findViewById(R.id.buttonLogin);


        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        String username = sharedPreferences.getString("username", null);
     int account_type = sharedPreferences.getInt("Acc_type",-1);
    // account_type = -1;
        if (isLoggedIn && username != null ) {
       switch (account_type){
            case 0:
               String key_Admin_1 = sharedPreferences.getString("key_Admin_1", null);
                key_Admin = key_Admin_1;
                Log.d("check_LOGIN_sharedPref", "LOGIN CLICKED! " + key_Admin);
                Intent intent = new Intent(Login.this, Admin_main.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

                break;
            case 1:
                String key_Gym_owner_1 = sharedPreferences.getString("key_Gym_owner_1", null);
                key_GymOwner = key_Gym_owner_1;
                Log.d("check_LOGIN_sharedPref", "LOGIN CLICKED! " + key_GymOwner);
                Intent intent1 = new Intent(Login.this, Gym_Owner_Main.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                finish();

                break;
            case 2:
                String gym_key1 = sharedPreferences.getString("gym_key1", null);
                String gym_key2 = sharedPreferences.getString("gym_key2", null);
                String gym_key3 = sharedPreferences.getString("gym_key3",null);
                String gyme_name = sharedPreferences.getString("gym_name",null);
                String staff_main_key = sharedPreferences.getString("Staff_main_key",null);
                String staff_username = sharedPreferences.getString("staff_username",null);
                String staff_fullname = sharedPreferences.getString("staff_fullname",null);
                String staff_password = sharedPreferences.getString("staff_password",null);
                String staff_email = sharedPreferences.getString("staff_email",null);
                String staff_mobil = sharedPreferences.getString("staff_mobil",null);
                key_Gym_Staff1 = gym_key1;
                key_Gym_Staff2 = gym_key2;
                key_Gym_Staff3 = gym_key3;
                key_Gym_ = gyme_name;
                key_gym_Staff_key = staff_main_key;
                key_Gym_Staff_username = staff_username;
                key_Gym_staff_fullname = staff_fullname;
                key_Gym_Staff_password = staff_password;
                key_Gym_Staff_email = staff_email;
                key_Gym_Staff_mobile_number = staff_mobil;
                Intent intent2 = new Intent(Login.this, Staff_main.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                finish();
                break;

            case 3:
                String gym_key1_c = sharedPreferences.getString("gym_key1", null);
                String gym_key2_c = sharedPreferences.getString("gym_key2", null);
                String gym_key3_c = sharedPreferences.getString("gym_key3",null);
                String gyme_name_c = sharedPreferences.getString("gym_name",null);
                String coach_main_key = sharedPreferences.getString("Coach_main_key",null);
                String coach_username = sharedPreferences.getString("Coach_username",null);
                String coach_fullname = sharedPreferences.getString("Coach_fullname",null);
                String coach_password = sharedPreferences.getString("Coach_password",null);
                String coach_email = sharedPreferences.getString("Coach_email",null);
                String coach_mobil = sharedPreferences.getString("Coach_mobil",null);
                key_Gym_Coach1 = gym_key1_c;
                key_Gym_Coach2 = gym_key2_c;
                key_Gym_Coach3 = gym_key3_c;
                key_Gym_ = gyme_name_c;
                key_Gym_Coach_key = coach_main_key;
                key_Gym_Coach_username = coach_username;
                key_Gym_Coach_fullname = coach_fullname;
                key_Gym_Coach_password = coach_password;
                key_Gym_Coach_email = coach_email;
                key_Gym_Coach_mobile_number = coach_mobil;
                Intent intent3 = new Intent(Login.this, Coach_main.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent3);
                finish();

                break;
            case 4:

                String member_key = sharedPreferences.getString("member_key", null);
                String Gym_Name = sharedPreferences.getString("member_gym_name", null);
                key = member_key;
                member_gym_name = Gym_Name;
                Intent intent4 = new Intent(Login.this, Member_main.class);
                intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent4);
                finish();
                break;

            case 5:
                String nonmember_key = sharedPreferences.getString("non_member_key", null);
                key = nonmember_key;
                Log.d("AUTLOG_NONMEM", "onCreate: "+key);
                Intent intent5 = new Intent(Login.this, NonMemberUSER.class);
                intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent5);
                finish();
                break;

        }

        }

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

                progressBar.setVisibility(View.VISIBLE);
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
                        String user_username = snapshot.child(key).child("username").getValue(String.class);
                        if(mem_status == 0){

                            String PasswordfromDB = snapshot.child(key).child("password").getValue(String.class);
                            Log.e( "TAG2","This is password from db: " +PasswordfromDB);
                            Log.e("wee2", "onDataChange: minecraft! ");
                            if(Objects.equals(PasswordfromDB, PASSWORD)){
                                progressBar.setVisibility(View.GONE);
                                String membergym_name= snapshot.child(key).child("GymName").getValue(String.class);
                                member_gym_name = membergym_name;

                                SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("isLoggedIn", true);
                                editor.putString("username", user_username);
                                editor.putInt("Acc_type",4);
                                // below will be keys or user details
                                editor.putString("member_key", key);
                                editor.putString("member_gym_name",membergym_name);

                                editor.apply();

                                Intent intent = new Intent(Login.this, Member_main.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                                Log.d("kasibag", "onDataChange: minecraft! ");
                            }
                            else {
                                progressBar.setVisibility(View.GONE);
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
                                progressBar.setVisibility(View.GONE);

                                SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("isLoggedIn", true);
                                editor.putString("username", user_username);
                                editor.putInt("Acc_type",5);
                                // below will be keys or user details
                                editor.putString("non_member_key", key);

                                editor.apply();


                                Intent intent = new Intent(Login.this, NonMemberUSER.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                                Log.d("kasibag", "onDataChange: minecraft! ");
                            }
                            else {
                                progressBar.setVisibility(View.GONE);
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
                            progressBar.setVisibility(View.GONE);

                            SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("isLoggedIn", true);
                            editor.putString("username", USERNAME);
                            editor.putString("key_Admin_1",key_Admin);
                            editor.putInt("Acc_type",0);
                            editor.apply();

                            Intent intent = new Intent(Login.this, Admin_main.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                            Log.d("kasibag", "onDataChange: minecraft! ");
                        }
                        else {
                            progressBar.setVisibility(View.GONE);
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
                            progressBar.setVisibility(View.GONE);

                            SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("isLoggedIn", true);
                            editor.putString("username", USERNAME);
                            editor.putString("key_Gym_owner_1",key_GymOwner);
                            editor.putInt("Acc_type",1);
                            editor.apply();

                            Intent intent = new Intent(Login.this, Gym_Owner_Main.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                            Log.d("TAG5", "onDataChange: minecraft! ");
                            break outerLoop;
                        }
                        else {
                            progressBar.setVisibility(View.GONE);
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
                                        key_gym_Staff_key = insidegymkey.getKey();
                                        Log.d("TAG53", "5th key :" + key_gym_Staff_key);

                                        key_Gym_Staff_username = insidegymkey.child("username").getValue(String.class);
                                        key_Gym_staff_fullname = insidegymkey.child("fullname").getValue(String.class);
                                        key_Gym_Staff_password = insidegymkey.child("password").getValue(String.class);
                                        key_Gym_Staff_email = insidegymkey.child("email").getValue(String.class);
                                        key_Gym_Staff_mobile_number = insidegymkey.child("mobile_number").getValue(String.class);


                                        if (Objects.equals(key_Gym_Staff_password, PASSWORD)) {
                                            progressBar.setVisibility(View.GONE);

                                            SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putBoolean("isLoggedIn", true);
                                            editor.putString("username", key_Gym_Staff_username);
                                            editor.putInt("Acc_type",2);
                                            // below will be keys or user details
                                            editor.putString("gym_key1", key_Gym_Staff1);
                                            editor.putString("gym_key2", key_Gym_Staff2);
                                            editor.putString("gym_key3", key_Gym_Staff3);
                                            editor.putString("gym_name",key_Gym_);
                                            editor.putString("Staff_main_key",key_gym_Staff_key);
                                            editor.putString("staff_fullname",key_Gym_staff_fullname);
                                            editor.putString("staff_password",key_Gym_Staff_password);
                                            editor.putString("staff_email",key_Gym_Staff_email);
                                            editor.putString("staff_mobil",key_Gym_Staff_mobile_number);
                                            editor.putString("staff_username",key_Gym_Staff_username);

                                            editor.apply();

                                            Intent intent = new Intent(Login.this, Staff_main.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                            Log.d("TAG53", "onDataChange: minecraft! ");
                                            accountFound = true; // Account found, set the flag
                                            break outerLoop;

                                        } else {
                                            progressBar.setVisibility(View.GONE);
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

                                      key_Gym_Coach_key = insidegymkey.getKey();
                                      Log.d("TAG53", "5th key :" + key_Gym_Coach_key);
                                      Log.e("TAG53", key_Gym_Coach_key);

                                       key_Gym_Coach_fullname = insidegymkey.child("fullname").getValue(String.class);
                                       key_Gym_Coach_password = insidegymkey.child("password").getValue(String.class);
                                       key_Gym_Coach_email = insidegymkey.child("email").getValue(String.class);
                                       key_Gym_Coach_mobile_number = insidegymkey.child("mobile_number").getValue(String.class);
                                       key_Gym_Coach_username = insidegymkey.child("username").getValue(String.class);

                                         if (Objects.equals(key_Gym_Coach_password, PASSWORD)) {
                                             progressBar.setVisibility(View.GONE);

                                             SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                                             SharedPreferences.Editor editor = sharedPreferences.edit();
                                             editor.putBoolean("isLoggedIn", true);
                                             editor.putString("username", key_Gym_Coach_username);
                                             editor.putInt("Acc_type",3);
                                             // below will be keys or user details
                                             editor.putString("gym_key1", key_Gym_Coach1);
                                             editor.putString("gym_key2", key_Gym_Coach2);
                                             editor.putString("gym_key3", key_Gym_Coach3);
                                             editor.putString("gym_name",key_Gym_);
                                             editor.putString("Coach_main_key",key_Gym_Coach_key);
                                             editor.putString("Coach_fullname",key_Gym_Coach_fullname);
                                             editor.putString("Coach_password",key_Gym_Coach_password);
                                             editor.putString("Coach_email",key_Gym_Coach_email);
                                             editor.putString("Coach_mobil",key_Gym_Coach_mobile_number);
                                             editor.putString("Coach_username",key_Gym_Coach_username);

                                             editor.apply();


                                           Intent intent = new Intent(Login.this, Coach_main.class);
                                             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                             startActivity(intent);
                                             finish();
                                           Log.d("TAG53", "onDataChange: minecraft! ");
                                           accountFound_coach = true; // Account found, set the flag
                                           break outerLoop;

                                        } else {
                                             progressBar.setVisibility(View.GONE);
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
                    progressBar.setVisibility(View.GONE);
                }

                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}