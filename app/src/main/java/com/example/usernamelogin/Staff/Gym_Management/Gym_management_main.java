package com.example.usernamelogin.Staff.Gym_Management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.usernamelogin.R;

import com.example.usernamelogin.RegisterandLogin.Login;
import com.example.usernamelogin.Staff.Gym_Management.Gymmng_changestogym.OCtimechange;
import com.example.usernamelogin.Staff.Memberslist.Staff_mem_list_main;
import com.example.usernamelogin.Staff.Profile_Staff.Staff_Profile_Main;
import com.example.usernamelogin.Staff.Staff_main;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Gym_management_main extends AppCompatActivity implements interface_Adapter_Gym_packages  {
    DrawerLayout drawerLayout;
    ImageView menu, gotoaddgym;
    LinearLayout home, Gym_management, profile,member_lists, logoput;
    RecyclerView recyclerView;
    Button changedescrp, changeOCtime;
    public static String selected_pkg_pk;
    String db_descrip;
    TextView nav_username,nav_gym,toolbar_username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_management_main);

        //navbar toolbar textVIews
        nav_username = findViewById(R.id.textView2);
        nav_gym = findViewById(R.id.textView);
        toolbar_username = findViewById(R.id.username_nav);
        nav_username.setText(Staff_main.ProfileContents[0]);
        toolbar_username.setText(Staff_main.ProfileContents[0]);
        nav_gym.setText(Staff_main.ProfileContents[4]);

        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        Gym_management = findViewById(R.id.Gym_manage_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);
        member_lists = findViewById(R.id.Gym_manage_members);
        logoput = findViewById(R.id.logout_Button_U);

        gotoaddgym = findViewById(R.id.Add_Gym_Package_button);
        changedescrp = findViewById(R.id.change_descrp_button);
        changeOCtime = findViewById(R.id.change_OC_button);

        changedescrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDescriptionDialog();
            }
        });

        changeOCtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                octimedialog();
            }
        });

        recyclerView = findViewById(R.id.Gym_Packages_List);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        populate_gympackage_list();

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavbar(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Gym_management_main.this, Staff_main.class);
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
                redirectActivity(Gym_management_main.this, Staff_Profile_Main.class);
            }
        });
        member_lists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(Gym_management_main.this, Staff_mem_list_main.class);
            }
        });
        gotoaddgym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Gym_management_main.this, Gym_package_Creation.class);
            }
        });
        logoput.setOnClickListener(v ->{
            logout_prc(Gym_management_main.this, Login.class);

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
    private void showDescriptionDialog() {
        // Inflate custom layout with EditText
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.staff_descrp_dialogbox, null);

        EditText editTextDescription = dialogView.findViewById(R.id.edit_text_description);

        DatabaseReference myRefprofile = FirebaseDatabase.getInstance().getReference("Users/Gym_Owner")
                .child(Login.key_Gym_Staff1)
                .child(Login.key_Gym_Staff2)
                .child(Login.key_Gym_Staff3)
                .child("gym_descrp");

        myRefprofile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                db_descrip = snapshot.getValue(String.class);
                editTextDescription.setText(db_descrip);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Build the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Description")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {

                    String description = editTextDescription.getText().toString().trim();

                    if (!description.isEmpty()) {
                       myRefprofile.setValue(description);
                        Log.d("TAG_STAFF_DESCRP", "running, The value is " +description);
                    } else {
                        Toast.makeText(this, "Description cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();

    }
    private void octimedialog(){
        OCtimechange timchangedialog = new OCtimechange(this) {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
            }
        };
        timchangedialog.show();
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
        activity.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeNavbar(drawerLayout);
    }
    private void populate_gympackage_list(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("Gym_package").child(Login.key_Gym_) ;

        ArrayList<Model_Class_Adapter_Gym_Packages> list = new ArrayList<>();
        Adapter_Gym_Packages adapter = new Adapter_Gym_Packages((Context) this,list,this);
        recyclerView.setAdapter(adapter);

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Model_Class_Adapter_Gym_Packages res_list = dataSnapshot.getValue(Model_Class_Adapter_Gym_Packages.class);

                    res_list.setPackage_pushkey(dataSnapshot.getKey());
                    list.add(res_list);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void change_pckg_dts(int decider){
        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Gym_package")
                .child(Login.key_Gym_)
                .child(selected_pkg_pk);

        if(decider == 0){
            myref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Model_Class_Adapter_Gym_Packages res_lk = snapshot.getValue(Model_Class_Adapter_Gym_Packages.class);

                        showCustomDialog(Gym_management_main.this,res_lk.getPackage_name(), res_lk.getPackage_descrp(), res_lk.getPackage_price()
                                , res_lk.getPackage_mem_duration(), myref);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            myref.removeValue();
            recreate();
        }






    }
    private void showCustomDialog(Context context,String pkgName
            , String pkg_descrp, String prce, String dur
            , DatabaseReference myref){

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_add_package, null);
        // Access views inside the custom layout if needed
        EditText packageName = view.findViewById(R.id.editTextGym_Package_name);
        packageName.setText(pkgName);
        EditText description = view.findViewById(R.id.editTextGym_Package_Decrp);
        description.setText(pkg_descrp);
        EditText price = view.findViewById(R.id.editTextGym_Package_Price);
        price.setText(prce);
        EditText duration = view.findViewById(R.id.editTextGym_Package_membership_duration);
        duration.setText(dur);
        Button confirmBtn = view.findViewById(R.id.button_chg);
        ImageView goBackBtn = view.findViewById(R.id.go_back);

        // Create the dialog
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(false) // optional: force user to choose an action
                .create();

        confirmBtn.setOnClickListener(v -> {

            String name = packageName.getText().toString().trim();
            String descrp = description.getText().toString().trim();
            String packagePrice = price.getText().toString().trim();
            String durationDays = duration.getText().toString().trim();

            myref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // Retrieve the current package data from Firebase
                        Model_Class_Adapter_Gym_Packages res_lk = snapshot.getValue(Model_Class_Adapter_Gym_Packages.class);

                        // Update the existing fields of res_lk
                        res_lk.setPackage_name(name);
                        res_lk.setPackage_descrp(descrp);
                        res_lk.setPackage_price(packagePrice);
                        res_lk.setPackage_mem_duration(durationDays);

                        // Save the updated object back to Firebase
                        myref.setValue(res_lk).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d("TAGPO", "Package updated successfully.");
                            } else {
                                Log.e("TAGPO", "Failed to update package.", task.getException());
                            }
                        });
                    } else {
                        Log.e("TAGPO", "Package does not exist in Firebase.");
                    }

                    dialog.dismiss(); // Close the dialog after saving
                    recreate();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("TAG", "Failed to read value.", error.toException());
                }
            });
        });

        // Go back button logic
        goBackBtn.setOnClickListener(v -> dialog.dismiss());

        // Show the dialog
        dialog.show();


    }
    @Override
    public void onItemClick(int position) {
        AlertDialog crerdel = new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Are you sure you want to proceed?")
                .setPositiveButton("Change Contents", (dialog, which) -> {
                    int chg = 0;
                    change_pckg_dts(chg);
                })
                .setNegativeButton("Delete Package", (dialog, which) -> {
                    int chg2 = 1;
                    change_pckg_dts(chg2);
                })
                .show();


    }
}