package com.example.usernamelogin.Admin.Gym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.usernamelogin.Admin.Admin_helper;
import com.example.usernamelogin.Admin.Admin_helper2;
import com.example.usernamelogin.Admin.Admin_main;
import com.example.usernamelogin.Admin.Userslist.gymanditsmembers.UsersList_Admin_main;
import com.example.usernamelogin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Admin_add_gym_Field_Req extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, reservations, add_gym;
    Button add_gym_entity;
    Button add_opening_closing;
    EditText Gym_name, Gym_descp, Gym_owner_name, Gym_owneremail,Gym_owner_password,gym_contact_number, firstname,lastname;
    TextView opening_text, closing_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_gym_field_req);

        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        reservations = findViewById(R.id.Reservations_navdrawer);
        add_gym = findViewById(R.id.add_gym_navdrawer);

        //for adding gym entity
        Gym_name = findViewById(R.id.editTextGym_Name);
        Gym_descp = findViewById(R.id.editTextGym_Decrp);
        Gym_owner_name = findViewById(R.id.editTextGym_Owner_Username);
        Gym_owneremail = findViewById(R.id.editTextGym_Owner_EmailAddress);
        Gym_owner_password = findViewById(R.id.editTextGym_Owner_Password);
        add_gym_entity = findViewById(R.id.button_add_gym_entity);
        gym_contact_number= findViewById(R.id.editTextGym_Contact_number);
        firstname = findViewById(R.id.editTextGym_Owner_firstname);
        lastname = findViewById(R.id.editTextGym_Owner_Lastname);
        opening_text = findViewById(R.id.editTextGym_Opening_time);
        closing_text = findViewById(R.id.editTextGym_Closing_time);
        add_opening_closing = findViewById(R.id.addOCtimebutton);


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavbar(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Admin_add_gym_Field_Req.this, Admin_main.class);
            }
        });
        reservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Admin_add_gym_Field_Req.this, UsersList_Admin_main.class);
            }
        });
        add_gym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Admin_add_gym_Field_Req.this, Admin_add_gym.class);
            }
        });
        add_opening_closing.setOnClickListener(v -> {

            octimeyeah();
        });

        add_gym_entity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database ;
                DatabaseReference myRef;
                final DatabaseReference[] myRef4Gym = new DatabaseReference[1];
                DatabaseReference addedmyRef ;

                String Gym_name1 = Gym_name.getText().toString();
                String Gym_descp1 = Gym_descp.getText().toString();
                String Gym_owner1_name= Gym_owner_name.getText().toString();
                String Gym_owner1_password= Gym_owner_password.getText().toString();
                String Gym_owneremail1 = Gym_owneremail.getText().toString();
                String Gym_contactnumber = gym_contact_number.getText().toString();
                String gym_own_firstname = firstname.getText().toString();
                String gym_own_lastname = lastname.getText().toString();
                String gym_opentime = opening_text.getText().toString();
                String gym_closetime = closing_text.getText().toString();


                if(Gym_name1.isEmpty() || Gym_descp1.isEmpty() ||Gym_owner1_name.isEmpty() || Gym_owner1_password.isEmpty() || Gym_owneremail1.isEmpty() || Gym_contactnumber.isEmpty()
                || gym_opentime.isEmpty() || gym_closetime.isEmpty()) {

                    Toast.makeText(Admin_add_gym_Field_Req.this,"Enter Texts in the Empty Fields",Toast.LENGTH_SHORT).show();

                }
                else{

                    database = FirebaseDatabase.getInstance();
                    myRef = database.getReference("Users/Gym_Owner");
                    addedmyRef = myRef.push(); //parent
                    String userId = addedmyRef.getKey();

                    Helper_Gym_adder user = new Helper_Gym_adder(Gym_owneremail1, Gym_owner1_password, Gym_owner1_name,gym_own_firstname,gym_own_lastname );

                    addedmyRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            myRef4Gym[0] = myRef.child(userId).child("Gym").push();
                            Admin_helper2 user2 = new Admin_helper2(Gym_name1, Gym_descp1,null,Gym_contactnumber, gym_opentime,gym_closetime );
                            myRef4Gym[0].setValue(user2);

                            redirectActivity(Admin_add_gym_Field_Req.this, Admin_add_gym.class);
                            Toast.makeText(Admin_add_gym_Field_Req.this,"successfully updated",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });


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
    private void octimeyeah(){

        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(Admin_add_gym_Field_Req.this,
                (TimePicker view, int selectedHour, int selectedMinute) -> {
                    calendar1.set(Calendar.HOUR_OF_DAY, selectedHour);
                    calendar2.set(Calendar.MINUTE, selectedMinute);
                    updateMinTextViews(calendar1,calendar2);
                    showMaxTimePickerDialog();
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE), false);

        timePickerDialog.show();
    }
    private void updateMinTextViews(Calendar Calendar1,Calendar Calendar2) {
        opening_text.setText(formatTime(Calendar1
                .get(Calendar.HOUR_OF_DAY), Calendar2.get(Calendar.MINUTE)));
    }
    private void showMaxTimePickerDialog() {

        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(Admin_add_gym_Field_Req.this,
                (TimePicker view, int selectedHour, int selectedMinute) -> {
                    calendar1.set(Calendar.HOUR_OF_DAY, selectedHour);
                    calendar2.set(Calendar.MINUTE, selectedMinute);
                    updateMaxTextViews(calendar1,calendar2);

                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE), false);

        timePickerDialog.show();
    }
    private void updateMaxTextViews(Calendar Calendar1,Calendar Calendar2) {
        closing_text.setText(formatTime(Calendar1
                .get(Calendar.HOUR_OF_DAY), Calendar2.get(Calendar.MINUTE)));
    }

    private String formatTime(int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        return android.text.format.DateFormat.format("hh:mm a", calendar).toString();
    }
}