package com.example.usernamelogin.Staff.Gym_Management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.example.usernamelogin.RegisterandLogin.MainActivity;
import com.example.usernamelogin.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Gym_package_Creation extends AppCompatActivity {
    EditText package_name, package_descrp, package_price, pckage_mem_duration;
    String Name, descrp, price,package_duration;
    Button Confirm_add_package;
    FirebaseDatabase database;
    DatabaseReference myref;
    ImageView  goback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_package_creation);

        package_name = findViewById(R.id.editTextGym_Package_name);
        package_descrp = findViewById(R.id.editTextGym_Package_Decrp);
        package_price = findViewById(R.id.editTextGym_Package_Price);
        pckage_mem_duration = findViewById(R.id.editTextGym_Package_membership_duration);
        Confirm_add_package = findViewById(R.id.button_chg);
        goback = findViewById(R.id.go_back);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gym_management_main.redirectActivity(Gym_package_Creation.this, Gym_management_main.class);
            }
        });
        Confirm_add_package.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = package_name.getText().toString();
                descrp = package_descrp.getText().toString();
                price = package_price.getText().toString();
                package_duration = pckage_mem_duration.getText().toString();
                long timestamp = System.currentTimeMillis();
                Log.d("checkvalTAG", "onClick: "+timestamp);
                if(Name.isEmpty() || descrp.isEmpty() || price.isEmpty() || package_duration.isEmpty() ) {

                    Toast.makeText(Gym_package_Creation.this, "Enter Texts in the Empty Fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    database = FirebaseDatabase.getInstance();
                    myref = database.getReference("Gym_package").child(Login.key_Gym_).push();
                    Helper_Gym_adding_staff fromeditText = new Helper_Gym_adding_staff(Name,descrp,price,package_duration,timestamp );
                    myref.setValue(fromeditText).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(Gym_package_Creation.this,"successfully updated",Toast.LENGTH_SHORT).show();
                            Gym_management_main.redirectActivity(Gym_package_Creation.this, Gym_management_main.class);
                        }
                    });

                }

            }
        });
    }

}