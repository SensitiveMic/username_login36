package com.example.usernamelogin.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.example.usernamelogin.RegisterandLogin.MainActivity;
import com.example.usernamelogin.Users;
import com.example.usernamelogin.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_Creation extends AppCompatActivity {

    String USERNAME, PASSWORD, EMAIL;
    EditText username, password, email;
    FirebaseDatabase database ;
    DatabaseReference myRef ;
    DatabaseReference addedmyRef ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_creation);

        Button regbutton = findViewById(R.id.regbutton);
        username = findViewById(R.id.editTextTextUsername);
        password = findViewById(R.id.editTextTextPassword);
        email = findViewById(R.id.editTextTextEmailAddress);

        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                USERNAME = username.getText().toString();
                PASSWORD = password.getText().toString();
                EMAIL = email.getText().toString();

                if(USERNAME.isEmpty() || PASSWORD.isEmpty() || EMAIL.isEmpty()) {

                    Toast.makeText(Admin_Creation.this,"Enter Texts in the Empty Fields",Toast.LENGTH_SHORT).show();
                }
                else{
                    database = FirebaseDatabase.getInstance();
                    myRef = database.getReference("Users/Staff");
                    addedmyRef = myRef.push();
                    Users user = new Users(USERNAME, PASSWORD, EMAIL,null);
                    addedmyRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Admin_Creation.this,"successfully updated",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
}