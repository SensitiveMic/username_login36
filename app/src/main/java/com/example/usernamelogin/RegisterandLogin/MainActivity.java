package com.example.usernamelogin.RegisterandLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.usernamelogin.Users;
import com.example.usernamelogin.Users_Update1;
import com.example.usernamelogin.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    String USERNAME, PASSWORD, EMAIL, Mobile;
    ActivityMainBinding binding;
    FirebaseDatabase database ;
    DatabaseReference myRef ;
    DatabaseReference addedmyRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                USERNAME = binding.editTextTextUsername.getText().toString();
                PASSWORD = binding.editTextTextPassword.getText().toString();
                EMAIL = binding.editTextTextEmailAddress.getText().toString();
                Mobile = binding.editTextMobileNumber.getText().toString();

                if(USERNAME.isEmpty() || PASSWORD.isEmpty() || EMAIL.isEmpty() || Mobile.isEmpty()) {

                    Toast.makeText(MainActivity.this,"Enter Texts in the Empty Fields",Toast.LENGTH_SHORT).show();

                }
                else if (Mobile.length() != 11 || !Mobile.startsWith("09")) {
                    Toast.makeText(MainActivity.this, "Enter a valid mobile number starting with 09 and 11 digits long", Toast.LENGTH_SHORT).show();
                }

                else{
                    database = FirebaseDatabase.getInstance();
                    myRef = database.getReference("Users/Non-members");

                    addedmyRef = myRef.push();
                    Users_Update1 user = new Users_Update1(USERNAME, PASSWORD, EMAIL, 1, Mobile);
                    addedmyRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(MainActivity.this,"successfully registered",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, Login.class);
                            MainActivity.this.startActivity(intent);
                            MainActivity.this.finish();
                        }
                    });

                }
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,Login.class);

                    startActivity(intent);

            }
        });



    }
}