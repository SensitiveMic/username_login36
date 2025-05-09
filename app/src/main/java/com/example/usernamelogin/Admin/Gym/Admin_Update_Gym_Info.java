package com.example.usernamelogin.Admin.Gym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.usernamelogin.Admin.Userslist.gymanditsmembers.UsersList_Admin_main;
import com.example.usernamelogin.R;
import com.example.usernamelogin.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Admin_Update_Gym_Info extends AppCompatActivity {
    Button button_chg ;
    FirebaseDatabase databaseprofile ;
    DatabaseReference myRefprofile ;
    private EditText[] chg ;
    String key1;
    String key2;
    ImageView goback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_gym_info);

        chg = new EditText[5];
        chg[0] = findViewById(R.id.editTextGym_Owner_Username);
        chg[1] = findViewById(R.id.editTextGym_Owner_EmailAddress);
        chg[2] = findViewById(R.id.editTextGym_Owner_Password);
        chg[3] = findViewById(R.id.editTextGym_name);
        chg[4] = findViewById(R.id.editTextGym_Decrp);
        chg[0].setText( UsersList_Admin_main.gyym_owners_usernmae);

        button_chg = findViewById(R.id.button_chg);
        goback = findViewById(R.id.go_back);

        checkUSER();

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Admin_Update_Gym_Info.this, UsersList_Admin_main.class);
            }
        });

        button_chg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseprofile = FirebaseDatabase.getInstance();
                myRefprofile = databaseprofile.getReference("Users/Gym_Owner").child(key1);
                DatabaseReference myRefprofile1 = databaseprofile.getReference("Users/Gym_Owner").child(key1)
                        .child("Gym").child(key2);
                // Create a HashMap to hold the updates you want to make
                String USERNAME = chg[0].getText().toString();
                String EMAIL    = chg[1].getText().toString();
                String PASSWORD = chg[2].getText().toString();
                String GYMNAME = chg[3].getText().toString();
                String GYMDESCRP = chg[4].getText().toString();

                Map<String, Object> updates = new HashMap<>();
                updates.put("gym_owner_username", USERNAME);
                updates.put("gym_owner_email",    EMAIL);
                updates.put("gym_owner_password", PASSWORD);

                Map<String, Object> updates1 = new HashMap<>();
                updates1.put("gym_name",    GYMNAME);
                updates1.put("gym_descrp", GYMDESCRP);

                Users user = new Users(USERNAME, PASSWORD, EMAIL,null);
                Log.d("TAG6", "To run on update");
                if(USERNAME.isEmpty() || PASSWORD.isEmpty() || EMAIL.isEmpty()) {

                    Toast.makeText(Admin_Update_Gym_Info.this,"Enter Texts in the Empty Fields",Toast.LENGTH_SHORT).show();

                }
                else{
                    myRefprofile.updateChildren(updates)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Update successfully completed
                                    Log.d("TAG10", "Data updated successfully");
                                    myRefprofile1.updateChildren(updates1);
                                    Toast.makeText(Admin_Update_Gym_Info.this,"succesfully edited",Toast.LENGTH_SHORT).show();
                                    redirectActivity(Admin_Update_Gym_Info.this, UsersList_Admin_main.class);
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
    public void checkUSER() {

        FirebaseDatabase databaseLogin = FirebaseDatabase.getInstance();
        DatabaseReference myRefLogin = databaseLogin.getReference("Users");

        Query checkUser = myRefLogin.child("Gym_Owner").orderByChild("gym_owner_username")
                .equalTo(UsersList_Admin_main.gyym_owners_usernmae);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot gk : snapshot.getChildren()) {
                    key1 = gk.getKey();
                    String PasswordfromDB = snapshot.child(key1).child("gym_owner_password").getValue(String.class);
                    String Email = snapshot.child(key1).child("gym_owner_email").getValue(String.class);
                    chg[1].setText(Email);
                    chg[2].setText(PasswordfromDB);
                    // Access the "Gym" node
                    DataSnapshot gymSnapshot = gk.child("Gym");
                    // Iterate over the children of the "Gym" node
                    for (DataSnapshot gymChildSnapshot : gymSnapshot.getChildren()) {
                        key2 = gymChildSnapshot.getKey();
                        // Access the "gym_name" under the push ID
                        String gymName = gymChildSnapshot.child("gym_name").getValue(String.class);
                        String gymdscrp = gymChildSnapshot.child("gym_descrp").getValue(String.class);
                        chg[3].setText(gymName);
                        chg[4].setText(gymdscrp);
                        // Set gym name to the TextView

                        // Break the loop as we only need to process one gym name
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

}