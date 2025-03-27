package com.example.usernamelogin.Coach;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.usernamelogin.Member.Reservation.Coach_Res_Scheduler.Coach_res_form_main;
import com.example.usernamelogin.Member.Reservation.Current_Coach_Res.Modelclass_for_current_member_res_accepted;
import com.example.usernamelogin.NonMemberUser.Reservations.Reservations;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public abstract class dialog_for_clicked_pen_members extends Dialog {

    private Context context;
    ImageView message, accept_reservation, reject_reservation;
    String chosen_date, chosen_time;
    TextView Fullname, Age,Sex , FitnessGoals,Current_Fitness_level, Preferred_Days_times,Medical_history,Recent_surgery_injury;
    TextView mobilenumberinvi;
    String mobilenumberfromdb;
    public dialog_for_clicked_pen_members(Context context) {
        super(context);
        this.context = context;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState != null ? savedInstanceState : new Bundle());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.coachreservationclick, null);
        setContentView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);

        mobilenumberinvi = findViewById(R.id.membernumber);
        message = findViewById(R.id.message_id);
        accept_reservation = findViewById(R.id.accept_id);
        reject_reservation = findViewById(R.id.Reject_Applicationid);
        Fullname = findViewById(R.id.FullnameIDfrmmem);
        Age = findViewById(R.id.member_agefrmre);
        Sex = findViewById(R.id.membersex_res);
        FitnessGoals = findViewById(R.id.fitnessgoalsmem);
        Current_Fitness_level = findViewById(R.id.currentfitness);
        Preferred_Days_times = findViewById(R.id.availability_gym_mem);
        Medical_history = findViewById(R.id.MedicalHist);
        Recent_surgery_injury = findViewById(R.id.RecentSurg_inj);

        DatabaseReference getmemberapplicationdata = FirebaseDatabase.getInstance().getReference("Users/Non-members").child( Coach_main.member_pushid)
                .child("Coach_Reservation/Reservation_Applications").child(Login.key_Gym_Coach_username);

        getmemberapplicationdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                mobilenumberfromdb = snapshot.child("mobile_number").getValue(String.class);
                String fullname = snapshot.child("fullName").getValue(String.class);
                String Age1 = snapshot.child("age").getValue(String.class);
                String Sex1 = snapshot.child("sex").getValue(String.class);
                String FitnessG = snapshot.child("fitnessGoals").getValue(String.class);
                String CurrentFitness = snapshot.child("currentFitness").getValue(String.class);
                String PreferredDays = snapshot.child("preferredDays").getValue(String.class);
                String Medicalhist = snapshot.child("medicalHistory").getValue(String.class);
                String Recentinj = snapshot.child("recentinjuries_surgery").getValue(String.class);

                Log.d("TAGmobile", "from coach_mobile number: " + mobilenumberfromdb );

                Fullname.setText(fullname);
                Age.setText(Age1);
                Sex.setText(Sex1);
                FitnessGoals.setText(FitnessG);
                Current_Fitness_level.setText(CurrentFitness);
                Preferred_Days_times.setText(PreferredDays);
                Medical_history.setText(Medicalhist);
                Recent_surgery_injury.setText(Recentinj);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phonumber = mobilenumberfromdb ;

                if (phonumber.startsWith("0")) {
                    // Replace the leading "0" with the country code, e.g., "+63" for the Philippines
                    phonumber = "+63" + phonumber.substring(1);
                }

                Log.d("TAGmobile", "from coach_mobile number added with +: " + phonumber );

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("smsto:"+phonumber));

                context.startActivity(intent);


            }
        });

        accept_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("ACCEPT RESERVATION?");
              //  builder.setMessage("");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the OK button click

                        showDatePickerDialog();


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the Cancel button click
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });

        reject_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View view = inflater.inflate(R.layout.coachreservationcancelclickreasonadd, null);
                EditText denyreservetext = view.findViewById(R.id.denycontent_reason);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("REJECT RESERVATION?")
                        .setView(view);

                //  builder.setMessage("");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the OK button click
                        String inputfromdenyreason = denyreservetext.getText().toString();
                        Log.d("TAGdeny", "reason :  " + inputfromdenyreason);

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Reservation denied");
                        builder.setMessage("Press ok to proceed");

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DatabaseReference db3 =  FirebaseDatabase.getInstance()
                                        .getReference("Users/Non-members")
                                        .child(Coach_main.member_pushid)
                                        .child("Coach_Reservation").child("Current_Accepted_Res");

                                DatabaseReference reason4 = FirebaseDatabase.getInstance()
                                        .getReference("Users/Non-members")
                                        .child(Coach_main.member_pushid)
                                        .child("Coach_Reservation")
                                        .child("coach_denial")
                                        .child(Coach_main.ProfileContents[0]+"reasons");


                                Query queary1 = db3.orderByChild("coach_name").equalTo(Coach_main.ProfileContents[0]);
                                Modelclass_for_current_member_res_accepted member_details_add = new Modelclass_for_current_member_res_accepted();

                                DatabaseReference db2 = FirebaseDatabase.getInstance().getReference("Users/Gym_Owner")
                                        .child(Login.key_Gym_Coach1).child(Login.key_Gym_Coach2).child(Login.key_Gym_Coach3)
                                        .child("Coach").child(Login.key_Gym_Coach_key).child("pending_res");
                                Query query2 = db2.orderByChild("Member_push_id").equalTo(Coach_main.member_pushid);
                                //removes pending res on the coach
                                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){

                                            for(DataSnapshot gk : snapshot.getChildren()){
                                                String pendingreskey = gk.getKey();
                                                DatabaseReference dbremove = db2.child(pendingreskey);
                                                dbremove.removeValue();
                                                getmemberapplicationdata.removeValue();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });

                                queary1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){
                                            for(DataSnapshot gk : snapshot.getChildren()){
                                                String todeletekey = gk.getKey();
                                                DatabaseReference kasibag2 = db3.child(todeletekey);
                                                member_details_add.setCoach_name(Coach_main.ProfileContents[0]);
                                                member_details_add.setViewType(3);
                                                kasibag2.setValue(member_details_add);
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                reason4.setValue(inputfromdenyreason);


                                Intent intent = new Intent(context, Coach_main.class);
                                context.startActivity(intent);
                                if (context instanceof Activity) {
                                    ((Activity) context).finish();
                                }
                            }
                        });

                        AlertDialog dialog1 = builder.create();
                        dialog1.setCanceledOnTouchOutside(false); // Disable canceling by touching outside
                        dialog1.setCancelable(true);
                        dialog1.show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the Cancel button click
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });

    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year1, month1, dayOfMonth) -> {
                    chosen_date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    showTimePickerDialog();
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                (view, hourOfDay, minute1) -> {
                    chosen_time = hourOfDay + ":" + (minute1 < 10 ? "0" + minute1 : minute1);
                    nextcode();
                },
                hour, minute, true);
        timePickerDialog.show();
    }

    private void nextcode(){
        Integer wew1 = 1;

        DatabaseReference accepted_reservations = FirebaseDatabase.getInstance()
                .getReference("Users/Gym_Owner")
                .child(Login.key_Gym_Coach1)
                .child(Login.key_Gym_Coach2)
                .child(Login.key_Gym_Coach3)
                .child("Coach").child(Login.key_Gym_Coach_key)
                .child("Accepted_Reservation")
                .child(Coach_main.member_pushid );

        Map<String, Object> details = new HashMap<>();
        details.put("Fullname", Coach_main.member_name);
        details.put("Date_sent", chosen_date);
        details.put("Meet_time", chosen_time);
        details.put("viewType", 1);

        DatabaseReference accepted_reservation_add_scheduled_date_to_member = FirebaseDatabase.getInstance()
                .getReference("Users/Non-members")
                .child(Coach_main.member_pushid)
                .child("Coach_Reservation").child("Current_Accepted_Res");

        Query queary1 = accepted_reservation_add_scheduled_date_to_member
                .orderByChild("coach_name").equalTo(Coach_main.ProfileContents[0]);
        Modelclass_for_current_member_res_accepted member_details_add = new Modelclass_for_current_member_res_accepted();
     //   Modelclass_for_current_member_res_accepted member_details_add = new Modelclass_for_current_member_res_accepted(Coach_main.ProfileContents[0],chosen_date,chosen_time,1);

        queary1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot gk : snapshot.getChildren()){
                        String todeletekey = gk.getKey();
                        DatabaseReference kasibag2 = accepted_reservation_add_scheduled_date_to_member.child(todeletekey);
                        member_details_add.setCoach_name(Coach_main.ProfileContents[0]);
                        member_details_add.setGym_meet_time(chosen_time);
                        member_details_add.setGym_meet_date(chosen_date);
                        member_details_add.setViewType(1);

                        kasibag2.setValue(member_details_add);

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference db = FirebaseDatabase.getInstance()
                .getReference("Users/Gym_Owner")
                .child(Login.key_Gym_Coach1)
                .child(Login.key_Gym_Coach2)
                .child(Login.key_Gym_Coach3)
                .child("Coach")
                .child(Login.key_Gym_Coach_key)
                .child("pending_res");

        Query pendingresquery = db.orderByChild("Member_push_id").equalTo(Coach_main.member_pushid);

        // Coach's current reservations
        accepted_reservations.setValue(details).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("RESERVATION ACCEPTED");
                builder.setMessage("Press ok to proceed");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        pendingresquery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {

                                    for(DataSnapshot jk : snapshot.getChildren()){
                                        String snapkey;
                                        snapkey = jk.getKey().toString();
                                        Log.d("Tagwew", "onDataChange: " + snapkey);
                                        DatabaseReference deletewew = db.child(snapkey);
                                        deletewew.removeValue();

                                    }


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                        Intent intent = new Intent(context, Coach_main.class);
                        context.startActivity(intent);
                        if (context instanceof Activity) {
                            ((Activity) context).finish();
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false); // Disable canceling by touching outside
                dialog.setCancelable(true);
                dialog.show();


            }
        });



    }

}
