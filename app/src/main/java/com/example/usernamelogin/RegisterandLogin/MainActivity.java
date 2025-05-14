package com.example.usernamelogin.RegisterandLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usernamelogin.R;
import com.example.usernamelogin.Users;
import com.example.usernamelogin.Users_Update1;
import com.example.usernamelogin.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    String USERNAME, PASSWORD, EMAIL, Mobile, fullName;
    EditText fullname;
    ActivityMainBinding binding;
    FirebaseDatabase database ;
    DatabaseReference myRef ;
    DatabaseReference addedmyRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fullname = findViewById(R.id.Fullname_reg);
        EditText username = findViewById(R.id.editTextTextUsername);

        binding.regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                USERNAME = binding.editTextTextUsername.getText().toString().trim();
                PASSWORD = binding.editTextTextPassword.getText().toString().trim();
                EMAIL = binding.editTextTextEmailAddress.getText().toString().trim();
                Mobile = binding.editTextMobileNumber.getText().toString().trim();
                fullName = fullname.getText().toString().trim();

                if (USERNAME.isEmpty() || PASSWORD.isEmpty() || EMAIL.isEmpty() || Mobile.isEmpty() || fullName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter texts in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Mobile.length() != 11 || !Mobile.startsWith("09")) {
                    Toast.makeText(MainActivity.this, "Enter a valid mobile number (11 digits, starts with 09)", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users/Non-members");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean usernameExists = false;

                        for (DataSnapshot snap : snapshot.getChildren()) {
                            String existingUsername = snap.child("username").getValue(String.class);
                            if (USERNAME.equals(existingUsername)) {
                                usernameExists = true;
                                break;
                            }
                        }

                        if (usernameExists) {
                            Toast.makeText(MainActivity.this, "Username already used!", Toast.LENGTH_SHORT).show();
                        } else {
                            DatabaseReference newRef = ref.push();
                            Users_Update1 user = new Users_Update1(USERNAME, PASSWORD, EMAIL, 1, Mobile, fullName);
                            newRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(MainActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this, Login.class));
                                    finish();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MainActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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