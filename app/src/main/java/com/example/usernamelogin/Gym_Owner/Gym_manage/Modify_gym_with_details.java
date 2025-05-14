package com.example.usernamelogin.Gym_Owner.Gym_manage;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.usernamelogin.Admin.Admin_helper2;
import com.example.usernamelogin.Gym_Owner.Gym_Owner_Main;
import com.example.usernamelogin.Gym_Owner.Gym_Owner_gymmanagement_add_staff;
import com.example.usernamelogin.Gym_Owner.Profile_Main_Gym_Owner;
import com.example.usernamelogin.Gym_Owner.employeelist.employeelists_main;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Modify_gym_with_details extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, Gym_management, profile ,gymemployyes,Gym_manage, logoput;
    EditText Gym_name,gym_contact_number,Gym_descp;
    Button confirmbutton;
    private LinearLayout MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY;
    private TextView Monday_open,Monday_close;
    private TextView Tuesday_open,Tuesday_close;
    private TextView Wednesday_open,Wednesday_close;
    private TextView Thursday_open,Thursday_close;
    private TextView Friday_open,Friday_close;
    private TextView Saturday_open,Saturday_close;
    private TextView Sunday_open,Sunday_close;
    private Button Mon_btn,Tue_btn,Wed_btn,Thu_btn,Fri_btn,Sat_btn,Sun_btn,Confirm_all;
    private Boolean Mon_state,Tue_state,Wed_state,Thu_state,Fri_state,Sat_state, Sun_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_modify_gym_with_details);

        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        Gym_management = findViewById(R.id.Gym_manage_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);
        gymemployyes = findViewById(R.id.GYymemployyelist);
        logoput = findViewById(R.id.logout_Button_U);
        Gym_manage = findViewById(R.id.Gym_manage_Manage);

        ITEMIDS();
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavbar(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Modify_gym_with_details.this, Gym_Owner_Main.class);
            }
        });
        Gym_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Modify_gym_with_details.this, Gym_Owner_gymmanagement_add_staff.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Modify_gym_with_details.this, Profile_Main_Gym_Owner.class);
            }
        });
        gymemployyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Modify_gym_with_details.this, employeelists_main.class);
            }
        });
        Gym_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Modify_gym_with_details.this, Gym_Owner_gymmanage_main.class);
            }
        });
        logoput.setOnClickListener(v ->{
            logout_prc(Modify_gym_with_details.this, Login.class);

        });
        Mon_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mon_state = !Mon_state; // toggle the state

                if (Mon_state) {
                    Mon_btn.setText("Gym Open");
                    Mon_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightgreen));
                } else {
                    Mon_btn.setText("Gym Close");
                    Mon_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.dark_red));
                }
                Log.d("TAGSTATEBUTTON", "Mon_state: " + Mon_state);
            }
        });
        Tue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tue_state = !Tue_state; // toggle the state

                if (Tue_state) {
                    Tue_btn.setText("Gym Open");
                    Tue_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightgreen));
                } else {
                    Tue_btn.setText("Gym Close");
                    Tue_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.dark_red));
                }
            }
        });
        Wed_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Wed_state = !Wed_state; // toggle the state

                if (Wed_state) {
                    Wed_btn.setText("Gym Open");
                    Wed_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightgreen));
                } else {
                    Wed_btn.setText("Gym Close");
                    Wed_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.dark_red));
                }
            }
        });
        Thu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thu_state = !Thu_state; // toggle the state

                if (Thu_state) {
                    Thu_btn.setText("Gym Open");
                    Thu_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightgreen));
                } else {
                    Thu_btn.setText("Gym Close");
                    Thu_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.dark_red));
                }
            }
        });
        Fri_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fri_state = !Fri_state; // toggle the state

                if (Fri_state) {
                    Fri_btn.setText("Gym Open");
                    Fri_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightgreen));
                } else {
                    Fri_btn.setText("Gym Close");
                    Fri_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.dark_red));
                }
            }
        });
        Sat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sat_state = !Sat_state; // toggle the state

                if (Sat_state) {
                    Sat_btn.setText("Gym Open");
                    Sat_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightgreen));
                } else {
                    Sat_btn.setText("Gym Close");
                    Sat_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.dark_red));
                }
            }
        });
        Sun_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sun_state = !Sun_state; // toggle the state

                if (Sun_state) {
                    Sun_btn.setText("Gym Open");
                    Sun_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightgreen));
                } else {
                    Sun_btn.setText("Gym Close");
                    Sun_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.dark_red));
                }
            }
        });

        MONDAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addopshours(Monday_open,Monday_close);

            }
        });
        TUESDAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addopshours(Tuesday_open,Tuesday_close);

            }
        });
        WEDNESDAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addopshours(Wednesday_open,Wednesday_close);

            }
        });
        THURSDAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addopshours(Thursday_open,Thursday_close);

            }
        });
        FRIDAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addopshours(Friday_open,Friday_close);

            }
        });
        SATURDAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addopshours(Saturday_open,Saturday_close);

            }
        });
        SUNDAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addopshours(Sunday_open,Sunday_close);

            }
        });

        confirmbutton = findViewById(R.id.confirm_adding_sched);
        Gym_name = findViewById(R.id.editTextGym_Name);
        Gym_descp = findViewById(R.id.editTextGym_Decrp);
        gym_contact_number= findViewById(R.id.editTextGym_Contact_number);

      setGymVals();
        confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Modify_gym_with_details.this);
                builder.setTitle("Confirm Changes to Gym?");
                builder.setMessage("Are you sure you want changes to this gym?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addgym_details();

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Just close the dialog
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }
    private void addgym_details(){
        String gymname = Gym_name.getText().toString();
        String Gym_descp1 = Gym_descp.getText().toString();
        String Gym_contactnumber = gym_contact_number.getText().toString();
        if( Gym_contactnumber.isEmpty()) {
            Toast.makeText(this,"Enter Texts in the Empty Fields",Toast.LENGTH_SHORT).show();
        }else{

            DatabaseReference gettheGym_deets = FirebaseDatabase.getInstance().getReference("Users/Gym_Owner").child(Login.key_GymOwner)
                    .child("Gym").child(Gym_Owner_gymmanage_main.gym_key);
            Admin_helper2 gymData = new Admin_helper2(
                    gymname,
                    Gym_descp1,
                    null,
                    Gym_contactnumber,
                    null,
                    null
            );
            gettheGym_deets.setValue(gymData).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    List<GymDay> scheduleList = getGymSchedule();

                    for (GymDay day : scheduleList) {
                        addgymOperatinghourstodb(gettheGym_deets, day.dayName, day.isActive, day.opening, day.closing);
                    }
                    redirectActivity(Modify_gym_with_details.this, Gym_Owner_gymmanage_main.class);
                } else {
                    Toast.makeText(this, "Failed to add gym.", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    private class GymDay {
        String dayName;
        boolean isActive;
        String opening;
        String closing;

        public GymDay(String dayName, boolean isActive, String opening, String closing) {
            this.dayName = dayName;
            this.isActive = isActive;
            this.opening = opening;
            this.closing = closing;
        }
    }
    private List<GymDay> getGymSchedule() {
        List<GymDay> schedule = new ArrayList<>();

        schedule.add(new GymDay("Monday", Mon_state, Monday_open.getText().toString().trim(), Monday_close.getText().toString().trim()));
        schedule.add(new GymDay("Tuesday", Tue_state, Tuesday_open.getText().toString().trim(), Tuesday_close.getText().toString().trim()));
        schedule.add(new GymDay("Wednesday", Wed_state, Wednesday_open.getText().toString().trim(), Wednesday_close.getText().toString().trim()));
        schedule.add(new GymDay("Thursday", Thu_state, Thursday_open.getText().toString().trim(), Thursday_close.getText().toString().trim()));
        schedule.add(new GymDay("Friday", Fri_state, Friday_open.getText().toString().trim(), Friday_close.getText().toString().trim()));
        schedule.add(new GymDay("Saturday", Sat_state, Saturday_open.getText().toString().trim(), Saturday_close.getText().toString().trim()));
        schedule.add(new GymDay("Sunday", Sun_state, Sunday_open.getText().toString().trim(), Sunday_close.getText().toString().trim()));

        return schedule;
    }
    private void addopshours(TextView opening, TextView closing) {
        // First TimePickerDialog for opening time
        Calendar calendar = Calendar.getInstance();

        TimePickerDialog openingDialog = new TimePickerDialog(opening.getContext(),
                (view, hourOfDay, minute) -> {
                    // Format and set opening time
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    String openingTime = new java.text.SimpleDateFormat("h:mm a").format(calendar.getTime());
                    opening.setText(openingTime);

                    // Now show the closing time picker
                    TimePickerDialog closingDialog = new TimePickerDialog(closing.getContext(),
                            (view2, hourOfDay2, minute2) -> {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay2);
                                calendar.set(Calendar.MINUTE, minute2);
                                String closingTime = new java.text.SimpleDateFormat("h:mm a").format(calendar.getTime());
                                closing.setText(closingTime);
                            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);

                    closingDialog.setTitle("Select Closing Time");
                    closingDialog.show();

                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);

        openingDialog.setTitle("Select Opening Time");
        openingDialog.show();
    }
    private void addgymOperatinghourstodb(DatabaseReference Dbloc, String Nameofday,Boolean dayactive,String opening,String closing){
        DatabaseReference Mon_db = Dbloc.child(Nameofday);
        OpHoursModel_class add_Opshours = new OpHoursModel_class(dayactive,opening,closing);
        Mon_db.setValue(add_Opshours);
    }
    private void setGymVals(){
        DatabaseReference gettheGym_deets = FirebaseDatabase.getInstance().getReference("Users/Gym_Owner").child(Login.key_GymOwner)
                .child("Gym").child(Gym_Owner_gymmanage_main.gym_key);
        gettheGym_deets.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1: snapshot.getChildren()) {
                    String day = snapshot1.getKey(); // e.g., "Monday"
                    String gymname = snapshot.child("gym_name").getValue().toString();
                    String gymcontact = snapshot.child("gym_contact_number").getValue().toString();
                    String gymdeescrp = snapshot.child("gym_descrp").getValue().toString();
                    Gym_name.setText(gymname);
                    Gym_descp.setText(gymdeescrp);
                    gym_contact_number.setText(gymcontact);
                    if (snapshot1.hasChild("day_active")) {
                        Boolean isActive = snapshot1.child("day_active").getValue(Boolean.class);
                        String openingTime = snapshot1.child("opening_time").getValue(String.class);
                        String closingTime = snapshot1.child("closing_time").getValue(String.class);

                        // Set data in views
                        switch (day) {
                            case "Monday":

                                Monday_open.setText(openingTime);
                                Monday_close.setText(closingTime);
                                Mon_btn.setText(isActive ? "Gym Open" : "Gym Close");
                                Mon_state = isActive;

                                if (isActive) {
                                    Mon_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightgreen));
                                } else {
                                    Mon_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.dark_red));
                                }
                                break;
                            case "Tuesday":
                                Tuesday_open.setText(openingTime);
                                Tuesday_close.setText(closingTime);
                                Tue_btn.setText(isActive ? "Gym Open" : "Gym Close");
                                Tue_state = isActive;
                                if (isActive) {
                                    Tue_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightgreen));
                                } else {
                                    Tue_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.dark_red));
                                }
                                break;
                            case "Wednesday":
                                Wednesday_open.setText(openingTime);
                                Wednesday_close.setText(closingTime);
                                Wed_btn.setText(isActive ? "Gym Open" : "Gym Close");
                                Wed_state = isActive;
                                if (isActive) {
                                    Wed_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightgreen));
                                } else {
                                    Wed_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.dark_red));
                                }
                                break;
                            case "Thursday":
                                Thursday_open.setText(openingTime);
                                Thursday_close.setText(closingTime);
                                Thu_btn.setText(isActive ? "Gym Open" : "Gym Close");
                                Thu_state = isActive;
                                if (isActive) {
                                    Thu_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightgreen));
                                } else {
                                    Thu_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.dark_red));
                                }
                                break;
                            case "Friday":
                                Friday_open.setText(openingTime);
                                Friday_close.setText(closingTime);
                                Fri_btn.setText(isActive ? "Gym Open" : "Gym Close");
                                Fri_state = isActive;
                                if (isActive) {
                                    Fri_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightgreen));
                                } else {
                                    Fri_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.dark_red));
                                }
                                break;
                            case "Saturday":
                                Saturday_open.setText(openingTime);
                                Saturday_close.setText(closingTime);
                                Sat_btn.setText(isActive ? "Gym Open" : "Gym Close");
                                Sat_state = isActive;
                                if (isActive) {
                                    Sat_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightgreen));
                                } else {
                                    Sat_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.dark_red));
                                }
                                break;
                            case "Sunday":
                                Sunday_open.setText(openingTime);
                                Sunday_close.setText(closingTime);
                                Sun_btn.setText(isActive ? "Gym Open" : "Gym Close");
                                Sun_state = isActive;
                                if (isActive) {
                                    Sun_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightgreen));
                                } else {
                                    Sun_btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.dark_red));
                                }
                                break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void ITEMIDS(){

        Monday_open = findViewById(R.id.Mon_open);
        Monday_close = findViewById(R.id.Mon_close);
        Tuesday_open = findViewById(R.id.Tue_open);
        Tuesday_close = findViewById(R.id.Tue_close);
        Wednesday_open  = findViewById(R.id.Wed_open);
        Wednesday_close = findViewById(R.id.Wed_close);
        Thursday_open = findViewById(R.id.Thu_open);
        Thursday_close = findViewById(R.id.Thu_close);
        Friday_open = findViewById(R.id.Fri_open);
        Friday_close = findViewById(R.id.Fri_close);
        Saturday_open = findViewById(R.id.Sat_open);
        Saturday_close = findViewById(R.id.Sat_close);
        Sunday_open = findViewById(R.id.Sun_open);
        Sunday_close = findViewById(R.id.Sun_close);

        Mon_btn = findViewById(R.id.Open_Close_mon);
        Tue_btn = findViewById(R.id.Open_Close_tue);
        Wed_btn = findViewById(R.id.Open_Close_wed);
        Thu_btn = findViewById(R.id.Open_Close_thu);
        Fri_btn = findViewById(R.id.Open_Close_fri);
        Sat_btn = findViewById(R.id.Open_Close_sat);
        Sun_btn = findViewById(R.id.Open_Close_sun);
        Confirm_all = findViewById(R.id.confirm_adding_sched);

        MONDAY = findViewById(R.id.MONDAY_LAYOUT);
        TUESDAY = findViewById(R.id.TUESDAY_LAYOUT);
        WEDNESDAY = findViewById(R.id.WEDNESDAY_LAYOUT);
        THURSDAY = findViewById(R.id.THURSDAY_LAYOUT);
        FRIDAY = findViewById(R.id.FRIDAY_LAYOUT);
        SATURDAY = findViewById(R.id.SATURDAY_LAYOUT);
        SUNDAY = findViewById(R.id.SUNDAY_LAYOUT);

        Mon_state = false;
        Tue_state = false;
        Wed_state = false;
        Thu_state = false;
        Fri_state = false;
        Sat_state = false;
        Sun_state = false;
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