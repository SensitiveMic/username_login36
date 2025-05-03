package com.example.usernamelogin.Gym_Owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Gym_Owner_gymmanagement_add_coach extends AppCompatActivity {
    String USERNAME, PASSWORD, EMAIL,MOBILENUMBER, FULLNAME;
    EditText username, password, email,mobilenumber, fullname;
    FirebaseDatabase database ;
    DatabaseReference myRef ;
    DatabaseReference addedmyRef ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_owner_gymmanagement_add_coach);
        Button regbutton = findViewById(R.id.regbutton);
        fullname = findViewById(R.id.editText_coach_fullname);
        username = findViewById(R.id.editTextTextUsername);
        password = findViewById(R.id.editTextTextPassword);
        email = findViewById(R.id.editTextTextEmailAddress);
        mobilenumber = findViewById(R.id.editTextMobilenumber);

        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FULLNAME = fullname.getText().toString();
                USERNAME = username.getText().toString();
                PASSWORD = password.getText().toString();
                EMAIL = email.getText().toString();
                MOBILENUMBER = mobilenumber.getText().toString();

                if(USERNAME.isEmpty() || PASSWORD.isEmpty() || EMAIL.isEmpty()|| MOBILENUMBER.isEmpty()|| FULLNAME.isEmpty()) {

                    Toast.makeText(Gym_Owner_gymmanagement_add_coach.this,"Enter Texts in the Empty Fields",Toast.LENGTH_SHORT).show();
                }
                else{
                    database = FirebaseDatabase.getInstance();
                    myRef = database.getReference("Users/Gym_Owner").child(Login.key_GymOwner).child("Gym")
                            .child(Gym_Owner_Main.key2).child("Coach");
                    addedmyRef = myRef.push();
                    Model_Class_Add_Staff user = new Model_Class_Add_Staff(USERNAME, PASSWORD, EMAIL, MOBILENUMBER,FULLNAME);
                    addedmyRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Gym_Owner_gymmanagement_add_coach.this,"successfully updated",Toast.LENGTH_SHORT).show();
                            Gym_Owner_Main.redirectActivity(Gym_Owner_gymmanagement_add_coach.this,Gym_Owner_Main.class);
                        }
                    });

                }
            }
        });
    }
}