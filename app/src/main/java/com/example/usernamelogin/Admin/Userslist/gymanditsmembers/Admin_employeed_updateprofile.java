package com.example.usernamelogin.Admin.Userslist.gymanditsmembers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.usernamelogin.Admin.Admin_main;
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

public class Admin_employeed_updateprofile extends AppCompatActivity {
    Button button_chg ;
    DatabaseReference myRefprofile ;
    DatabaseReference dbofemployeedetails;
    private EditText[] chg ;
    String key1;
    ImageView goback;
    String[] stringArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_employeed_updateprofile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        chg = new EditText[4];
        chg[0] = findViewById(R.id.editTextUsername_chg);
        chg[1] = findViewById(R.id.editTextEmail_chg);
        chg[2] = findViewById(R.id.editTextPassword_chg);
        chg[3] = findViewById(R.id.editTextMobilenumber);
        chg[0].setText(Admin_main.non_member_username);
        button_chg = findViewById(R.id.button_chg);
        goback = findViewById(R.id.go_back);

        Intent intenttoedit = getIntent();
        stringArray = intenttoedit.getStringArrayExtra("stringarraytosend");

            dbofemployeedetails = FirebaseDatabase.getInstance().getReference()
                    .child("Users/Gym_Owner")
                    .child(stringArray[0])
                    .child("Gym")
                    .child(stringArray[1])
                    .child(stringArray[2]);

        Log.d("TAGFIND_s", "onCreate: "+stringArray[0] );
        Log.d("TAGFIND_s", "onCreate: "+stringArray[1] );
        Log.d("TAGFIND_s", "onCreate: "+stringArray[2] );
        Log.d("TAGFIND_s", "onCreate: "+stringArray[3] );
        checkUSER();

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Admin_employeed_updateprofile.this, UsersList_Admin_main.class);
            }
        });

        button_chg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myRefprofile = dbofemployeedetails.child(key1);
                // Create a HashMap to hold the updates you want to make
                String USERNAME = chg[0].getText().toString();
                String EMAIL    = chg[1].getText().toString();
                String PASSWORD = chg[2].getText().toString();
                String MOBILENUMBER = chg[3].getText().toString();

                Map<String, Object> updates = new HashMap<>();
                updates.put("username", USERNAME);
                updates.put("email",    EMAIL);
                updates.put("password", PASSWORD);
                updates.put("mobile_number", MOBILENUMBER);

                Users user = new Users(USERNAME, PASSWORD, EMAIL,MOBILENUMBER );
                Log.d("TAG6", "To run on update");
                if(USERNAME.isEmpty() || PASSWORD.isEmpty() || EMAIL.isEmpty() || MOBILENUMBER.isEmpty()) {

                    Toast.makeText(Admin_employeed_updateprofile.this,"Enter Texts in the Empty Fields",Toast.LENGTH_SHORT).show();

                }
                else if (MOBILENUMBER.length() != 11 || !MOBILENUMBER.startsWith("09")) {
                    Toast.makeText(Admin_employeed_updateprofile.this, "Enter a valid mobile number starting with 09 and 11 digits long", Toast.LENGTH_SHORT).show();
                }
                else{
                    myRefprofile.updateChildren(updates)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Update successfully completed
                                    Log.d("TAG10", "Data updated successfully");

                                    redirectActivity(Admin_employeed_updateprofile.this, UsersList_Admin_main.class);
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

        Query checkUser = dbofemployeedetails.orderByChild("username")
                .equalTo(stringArray[3]);
        Log.d("TAGFIND_s", "checkusr "+stringArray[3] );
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
if(snapshot.exists()){
    for (DataSnapshot gk : snapshot.getChildren()){
        key1 = gk.getKey(); // to make the key into a string
        Log.e("TAG", key1);
        // to add the password from the database to a string
        String usernameoftheuser = snapshot.child(key1).child("username").getValue(String.class);
        String PasswordfromDB = snapshot.child(key1).child("password").getValue(String.class);
        String Email = snapshot.child(key1).child("email").getValue(String.class);
        String mobilenumber = snapshot.child(key1).child("mobile_number").getValue(String.class);
        chg[0].setText(usernameoftheuser);
        chg[1].setText(Email);
        chg[2].setText(PasswordfromDB);
        chg[3].setText(mobilenumber);

        Log.d("TAGFIND_s", "checkusr "+usernameoftheuser );
        Log.d("TAGFIND_s", "checkusr "+PasswordfromDB );
        Log.d("TAGFIND_s", "checkusr "+Email);
        Log.d("TAGFIND_s", "checkusr "+mobilenumber );
    }

} else {
    Log.d("TAGFIND_s", "NO SNAPSHOT " );
}
                for (DataSnapshot gk : snapshot.getChildren()) {



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