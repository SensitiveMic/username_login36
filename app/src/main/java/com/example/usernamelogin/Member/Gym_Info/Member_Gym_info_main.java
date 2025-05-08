package com.example.usernamelogin.Member.Gym_Info;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.usernamelogin.Member.Member_Profile;
import com.example.usernamelogin.Member.Member_main;
import com.example.usernamelogin.Member.Reservation.Coach_Reservation_main;
import com.example.usernamelogin.Member.Reservation.Current_Coach_Res.Current_Coach_Res_Main;

import com.example.usernamelogin.NonMemberUser.Gym_prop.Gym_Properties_Main;
import com.example.usernamelogin.NonMemberUser.Model_class_membershipReq;
import com.example.usernamelogin.NonMemberUser.dialogbox.DialogList;
import com.example.usernamelogin.R;

import com.example.usernamelogin.RegisterandLogin.Login;
import com.example.usernamelogin.workout_program.workouts.User_workouts;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Member_Gym_info_main extends AppCompatActivity {
    DrawerLayout drawerLayout;
    LinearLayout home, reservations, profile, gym_membership, currentreservations, workout,logoput;
    ImageView menu;
    Button uppdate_mem;
    TextView Gym_name, Gym_descrip, Gym_cntct, Gym_open, Gym_cls, pckge_start, pckge_end, pckge_name;
    public static String member_gymname;
    public static String package_name_adap, package_price_adap, package_descrip_adap, package_dura_adap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_member_gym_info_main);

      someMethod();

        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        reservations = findViewById(R.id.Reservations_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);
        gym_membership = findViewById(R.id.Gym_navdrawer);
        currentreservations = findViewById(R.id.current_res_coach);
        workout = findViewById(R.id.member_workout);

        Gym_name = findViewById(R.id.GymName_member);
        Gym_descrip = findViewById(R.id.Gym_descrip);
        Gym_cntct = findViewById(R.id.Gym_cntct);
        Gym_open = findViewById(R.id.Gym_Opening);
        Gym_cls = findViewById(R.id.Gym_clsing);
        pckge_start = findViewById(R.id.Start_Date);
        pckge_end = findViewById(R.id.End_Date);
        pckge_name = findViewById(R.id.this_pkg_name);
        logoput = findViewById(R.id.logout_Button_U);

        uppdate_mem = findViewById(R.id.button_forUpdates);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavbar(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Member_Gym_info_main.this, Member_main.class);
            }
        });
        reservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Member_Gym_info_main.this, Coach_Reservation_main.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Member_Gym_info_main.this, Member_Profile.class);
            }
        });
        gym_membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               recreate();
            }
        });
        currentreservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Member_Gym_info_main.this, Current_Coach_Res_Main.class);
            }
        });
        logoput.setOnClickListener(v ->{
            logout_prc(Member_Gym_info_main.this, Login.class);

        });
        workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("COACH_WRKT_SENT_TAG", "Coach workout Sent!");
                int workoutId = 1;
                Intent intent = new Intent(Member_Gym_info_main.this, User_workouts.class);
                intent.putExtra("workout_id", workoutId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Log.d("ClickedTAG?", "TRUE");
            }
        });
        uppdate_mem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(Member_Gym_info_main.this)
                        .setTitle("Update Package")
                        .setMessage("Do you want to update your package?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Dialog_list_forgyminfo listDialog = new Dialog_list_forgyminfo(Member_Gym_info_main.this) {
                                    @Override
                                    public void onItemClick(int position) {

                                        displaypackages();
                                        dismiss();

                                        Log.d("TAG8","Minecraft Clicked!");
                                    }

                                    @Override
                                    public void onCreate(Bundle savedInstanceState)
                                    {
                                        super.onCreate(savedInstanceState);

                                        // Set up any additional dialog features
                                    }


                                };
                                listDialog.show();
                                Toast.makeText(Member_Gym_info_main.this, "Updating package...", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();  }

        });


     populate_gym_info_textviews();

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
    private void displaypackages(){
        AlertDialog.Builder makingsaure = new AlertDialog.Builder(Member_Gym_info_main.this);

        makingsaure.setTitle("Are you sure you want to Apply?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference storetheposition = FirebaseDatabase.getInstance().getReference("Users")
                                .child("Non-members").child(Login.key).child("positionstored").push();

                        HashMap<String, Object> storepos_gymname2 = new HashMap<>();
                        storepos_gymname2.put("pos", Gym_Properties_Main.storedposition);
                        storepos_gymname2.put("gym_name",Gym_name.getText().toString());

                        storetheposition.updateChildren(storepos_gymname2);

                        String request_TimeandDate = createLog();

                        Model_class_membershipReq list = new Model_class_membershipReq(Member_main.ProfileContents[0], package_name_adap,package_price_adap
                                , request_TimeandDate,package_dura_adap );
                        DatabaseReference databaseReferenceNon = FirebaseDatabase.getInstance()
                                .getReference("Membership_Request")
                                .child(Gym_name.getText().toString())
                                .push();

                        databaseReferenceNon.setValue(list).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(Member_Gym_info_main.this);
                                builder.setTitle("Membership Request Sent")
                                        .setMessage("Click Yes to Continue ")
                                        .setCancelable(true)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                recreate();
                                            }
                                        })
                                        .show();

                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();


    }
    private void populate_gym_info_textviews() {
        // Get the GymName associated with the current non-member
        DatabaseReference nonMemberRef = FirebaseDatabase.getInstance()
                .getReference("Users").child("Non-members").child(Login.key);

        nonMemberRef.child("GymName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot gymNameSnapshot) {
                String targetGymName = gymNameSnapshot.getValue(String.class);

                if (targetGymName == null) {
                    Log.d("TAG6", "GymName for non-member is null.");
                    return;
                }

                Log.d("TAG6", "GymName to match: " + targetGymName);

                nonMemberRef.child("membership").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot membershipSnapshot) {
                        if (membershipSnapshot.exists()) {
                            String expirationDate = membershipSnapshot.child("expiration_date").getValue(String.class);
                            String startDate = membershipSnapshot.child("start_date").getValue(String.class);
                            String package_name = membershipSnapshot.child("package_name").getValue(String.class);

                            // Set to your TextViews
                            pckge_end.setText(expirationDate != null ? expirationDate : "N/A");
                            pckge_start.setText(startDate != null ? startDate : "N/A");
                            pckge_name.setText(package_name != null ? package_name : "N/A");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("TAG6", "Failed to get membership data: " + error.getMessage());
                    }
                });

                DatabaseReference gymOwnerRef = FirebaseDatabase.getInstance()
                        .getReference("Users").child("Gym_Owner");

                gymOwnerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot underGym_Owner : snapshot.getChildren()) {
                            for (DataSnapshot underGym : underGym_Owner.getChildren()) {
                                for (DataSnapshot gymNode : underGym.getChildren()) {
                                    String gymName = gymNode.child("gym_name").getValue(String.class);
                                    member_gymname = gymName;
                                    if (gymName != null && gymName.equals(targetGymName)) {
                                        // Get values
                                        String gymDescrp = gymNode.child("gym_descrp").getValue(String.class);
                                        String gymOpening = gymNode.child("gym_opening").getValue(String.class);
                                        String gymClosing = gymNode.child("gym_closing").getValue(String.class);
                                        String gymContact = gymNode.child("gym_contact_number").getValue(String.class);

                                        // Set TextView values
                                        Gym_name.setText(gymName);
                                        Gym_descrip.setText(gymDescrp);
                                        Gym_open.setText(gymOpening);
                                        Gym_cls.setText(gymClosing);
                                        Gym_cntct.setText(gymContact);

                                        return; // Exit once found
                                    }
                                }
                                break; // No need to continue if gym already found
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("TAG6", "Failed to read gym owner info: " + error.getMessage());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG6", "Failed to get GymName for non-member: " + error.getMessage());
            }
        });
    }

    public String createLog() {
        // Get the current date and time
        Date currentDate = new Date();

        // Define the date and time format
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        // Format the date and time
        String formattedDateTime = dateTimeFormat.format(currentDate);

        // Log the formatted date and time
        Log.d("TAG8", "Current date and time: " + formattedDateTime);

        return(formattedDateTime);
    }
    //_______ usertoolbar navbar________

    public void usertoolbarname(Context context, TextView usernamebar, TextView username_nav) {

        usernamebar.setText(Member_main.ProfileContents[0]);
        username_nav.setText(Member_main.ProfileContents[0]);
    }
    public void someMethod() {
        // Call usertoolbarname() with appropriate arguments
        usertoolbarname(getApplicationContext(),
                findViewById(R.id.textView2),
                findViewById(R.id.username_nav));
    }
    public static void openNavbar(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public static void closeNavbar(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
    @Override
    protected void onPause(){
        super.onPause();
        closeNavbar(drawerLayout);
    }
}