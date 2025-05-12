package com.example.usernamelogin.Gym_Owner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usernamelogin.Admin.Admin_Creation;
import com.example.usernamelogin.Gym_Owner.Gym_manage.Gym_Owner_gymmanage_main;
import com.example.usernamelogin.Gym_Owner.Gym_manage.Modelclass_gym_manage_Adapter;
import com.example.usernamelogin.Gym_Owner.employeelist.employeelists_main;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.example.usernamelogin.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Gym_Owner_gymmanagement_add_staff extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, Gym_management, profile ,gymemployyes,Gym_manage, logoput;
    String USERNAME, PASSWORD, EMAIL,MOBILENUMBER,FULLNAME;
    EditText username, password, email,mobilenumber,fullname;
    FirebaseDatabase database ;
    DatabaseReference myRef ;
    DatabaseReference addedmyRef ;
    Spinner gymSpinner;
    String selectedText = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gym_owner_gymmanagement_add_staff);
        toolbarnavbar();

        drawerLayout = findViewById(R.id.activity_gym_owner_gymmanage_main);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        Gym_management = findViewById(R.id.Gym_manage_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);
        gymemployyes = findViewById(R.id.GYymemployyelist);
        logoput = findViewById(R.id.logout_Button_U);
        Gym_manage = findViewById(R.id.Gym_manage_Manage);
        gymSpinner = findViewById(R.id.gymSpinner);
        CheckBox checkbox1 = findViewById(R.id.checkbox1);
        CheckBox checkbox2 = findViewById(R.id.checkbox2);


        Button regbutton = findViewById(R.id.regbutton);

       fullname = findViewById(R.id.editText_coach_fullname);
       username = findViewById(R.id.editTextTextUsername);
     password = findViewById(R.id.editTextTextPassword);
     email = findViewById(R.id.editTextTextEmailAddress);
       mobilenumber = findViewById(R.id.editTextMobilenumber);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavbar(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Gym_Owner_gymmanagement_add_staff.this, Gym_Owner_Main.class);
            }
        });
        Gym_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              recreate();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Gym_Owner_gymmanagement_add_staff.this, Profile_Main_Gym_Owner.class);
            }
        });
        gymemployyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Gym_Owner_gymmanagement_add_staff.this, employeelists_main.class);
            }
        });
        Gym_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Gym_Owner_gymmanagement_add_staff.this, Gym_Owner_gymmanage_main.class);
            }
        });
        logoput.setOnClickListener(v ->{
            logout_prc(Gym_Owner_gymmanagement_add_staff.this, Login.class);

        });


        checkbox1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkbox2.setChecked(false); // optional: only one checked at a time
                selectedText = checkbox1.getText().toString();
                Log.d("Checkbox", "Selected: " + selectedText);
            }else {
                // If you want to clear the string when unchecked:
                selectedText = "";
            }
        });

        checkbox2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkbox1.setChecked(false); // optional: only one checked at a time
                selectedText = checkbox2.getText().toString();
                Log.d("Checkbox", "Selected: " + selectedText);
            }else {
                // If you want to clear the string when unchecked:
                selectedText = "";
            }
        });

   /*     addcoachbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gym_Owner_Main.redirectActivity(Gym_Owner_gymmanagement_add_staff.this, Gym_Owner_gymmanagement_add_coach.class);
            }
        }); */
        refresh_list_gym();
        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FULLNAME = fullname.getText().toString();
                USERNAME = username.getText().toString();
                PASSWORD = password.getText().toString();
                EMAIL = email.getText().toString();
                MOBILENUMBER = mobilenumber.getText().toString();

                if(USERNAME.isEmpty() || PASSWORD.isEmpty() || EMAIL.isEmpty()|| MOBILENUMBER.isEmpty()|| FULLNAME.isEmpty() ||selectedText == null || selectedText.isEmpty() ) {

                    Toast.makeText(Gym_Owner_gymmanagement_add_staff.this,"Enter Texts in the Empty Fields",Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d("TAGSAVEEMPLOYEE", "baseline1 ");
                    GymItem selected = (GymItem) gymSpinner.getSelectedItem();

                    if (selected == null) {
                        Toast.makeText(Gym_Owner_gymmanagement_add_staff.this, "Please select a gym", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String selectedKey = selected.getKey();
                    Log.d("TAGSAVEEMPLOYEE", "baseline2 ");
                    database = FirebaseDatabase.getInstance();
                    myRef = database.getReference("Users/Gym_Owner").child(Login.key_GymOwner).child("Gym")
                            .child(selectedKey).child(selectedText);
                    Log.d("TAGSAVEEMPLOYEE", "baseline3 ");
                    addedmyRef = myRef.push();
                    Model_Class_Add_Staff user = new Model_Class_Add_Staff(USERNAME, PASSWORD, EMAIL, MOBILENUMBER,FULLNAME);
                    Log.d("TAGSAVEEMPLOYEE", "baseline4 ");
                    addedmyRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("TAGSAVEEMPLOYEE", "baseline5 ");
                            Toast.makeText(Gym_Owner_gymmanagement_add_staff.this,"successfully updated",Toast.LENGTH_SHORT).show();
                            fullname.setText("");
                            username.setText("");
                            password.setText("");
                            email.setText("");
                            mobilenumber.setText("");
                            gymSpinner.setSelection(0);
                            selectedText = "";
                            checkbox1.setChecked(false);
                            checkbox2.setChecked(false);
                            Log.d("TAGSAVEEMPLOYEE", "baseline6 ");
                        }
                    });

                }
            }
        });


    }
    private void refresh_list_gym() {
        String ownerkey = Login.key_GymOwner;

        List<GymItem> gymItems = new ArrayList<>();

        ArrayAdapter<GymItem> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, gymItems); // If in Fragment, use requireContext()
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gymSpinner.setAdapter(adapter);

        DatabaseReference db = FirebaseDatabase.getInstance()
                .getReference("/Users/Gym_Owner")
                .child(ownerkey)
                .child("Gym");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gymItems.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String key = dataSnapshot.getKey();
                    Modelclass_gym_manage_Adapter gym = dataSnapshot.getValue(Modelclass_gym_manage_Adapter.class);
                    if (gym != null && gym.getGym_name() != null) {
                        gymItems.add(new GymItem(key, gym.getGym_name()));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to load gyms", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logout_prc(Activity activity, Class secondActivity){

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(activity, secondActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }
    private void toolbarnavbar(){
        TextView usernamebar, username_nav;
        usernamebar =  findViewById(R.id.textView2);
        username_nav =  findViewById(R.id.username_nav);
        usernamebar.setText(Gym_Owner_Main.ProfileContents[0]);
        username_nav.setText(Gym_Owner_Main.ProfileContents[0]);
    }

    public static void openNavbar(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeNavbar(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public static void redirectActivity(Activity activity, Class secondActivity) {
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }

    @Override
    protected void onPause() {
        super.onPause();
        closeNavbar(drawerLayout);
    }
}