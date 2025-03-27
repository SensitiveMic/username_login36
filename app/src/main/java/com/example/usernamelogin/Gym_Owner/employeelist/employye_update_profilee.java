package com.example.usernamelogin.Gym_Owner.employeelist;

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

import com.example.usernamelogin.Admin.Userslist.gymanditsmembers.Admin_employeed_updateprofile;
import com.example.usernamelogin.Admin.Userslist.gymanditsmembers.UsersList_Admin_main;
import com.example.usernamelogin.NonMemberUser.Profile;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class employye_update_profilee extends AppCompatActivity {
    private EditText[] chg ;
    Button button_chg ;
    ImageView goback;
    DatabaseReference myRefLogin;
    String updatekey, employeetype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employye_update_profilee);
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

        button_chg = findViewById(R.id.button_chg);
        goback = findViewById(R.id.go_back);
        updateedittextsviews();
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(employye_update_profilee.this, employeelists_main.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }
        });
        button_chg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmchanges();
            }
        });
    }
    private void updateedittextsviews(){
         myRefLogin = FirebaseDatabase.getInstance()
                .getReference("Users/Gym_Owner")
                .child(Login.key_GymOwner)
                .child("Gym")
                .child(employeelists_main.Gym_id);

        myRefLogin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot childrenofgk :snapshot.getChildren()){
                    String Gymdetailskey = childrenofgk.getKey();
                    Log.d("TAGGYMCOACH_clicked", "next : " + Gymdetailskey);
                    if("Coach".equals(Gymdetailskey)){
                        for(DataSnapshot coachlists: childrenofgk.getChildren()){
                            String coachkey = coachlists.getKey().toString();
                                String username = coachlists.child("username").getValue(String.class);
                                if(username.equals(employeelists_main.employeeclicked)){

                                    employeetype= "Coach";
                                    updatekey = coachkey;
                                        //username
                                        chg[0].setText(username);
                                        //email
                                    String email = coachlists.child("email").getValue(String.class);
                                        chg[1].setText(email);
                                    //password
                                    String pass = coachlists.child("password").getValue(String.class);
                                    chg[2].setText(pass);
                                    //mobile
                                    String mob = coachlists.child("mobile_number").getValue(String.class);
                                    chg[3].setText(mob);

                                }
                                else{
                                    Log.e("TAGGYMCOACH_clicked", " No details of the clicked username " );
                                }
                        }
                    }
                    else if("Staff".equals(Gymdetailskey)){
                        for(DataSnapshot stafflists: childrenofgk.getChildren()){
                            String staffkey = stafflists.getKey().toString();
                            String username = stafflists.child("username").getValue(String.class);
                            if(username.equals(employeelists_main.employeeclicked)){
                                employeetype= "Staff";
                                updatekey = staffkey;
                                //username
                                chg[0].setText(username);
                                //email
                                String email = stafflists.child("email").getValue(String.class);
                                chg[1].setText(email);
                                //password
                                String pass = stafflists.child("password").getValue(String.class);
                                chg[2].setText(pass);
                                //mobile
                                String mob = stafflists.child("mobile_number").getValue(String.class);
                                chg[3].setText(mob);

                            }
                            else{
                                Log.e("TAGGYMCOACH_clicked", " No details of the clicked username " );
                            }
                        }
                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void confirmchanges(){
        String USERNAME,EMAIL,PASS,MOBILE;

        USERNAME = chg[0].getText().toString();
        EMAIL    = chg[1].getText().toString();
        PASS = chg[2].getText().toString();
        MOBILE = chg[3].getText().toString();

        Map<String, Object> updates = new HashMap<>();
        updates.put("username", USERNAME);
        updates.put("email",    EMAIL);
        updates.put("password", PASS);
        updates.put("mobile_number", MOBILE);
        if(USERNAME.isEmpty() || PASS.isEmpty() || EMAIL.isEmpty() || MOBILE.isEmpty())  {

            Toast.makeText(employye_update_profilee.this,"Enter Texts in the Empty Fields",Toast.LENGTH_SHORT).show();

        }
        else {
            DatabaseReference updateempprofile = FirebaseDatabase.getInstance()
                    .getReference("Users/Gym_Owner")
                    .child(Login.key_GymOwner)
                    .child("Gym")
                    .child(employeelists_main.Gym_id)
                    .child(employeetype)
                    .child(updatekey);

            updateempprofile.updateChildren(updates)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(employye_update_profilee.this,"Update Success",Toast.LENGTH_SHORT).show();
                    updateedittextsviews();
                }
            });
        }
    }


}