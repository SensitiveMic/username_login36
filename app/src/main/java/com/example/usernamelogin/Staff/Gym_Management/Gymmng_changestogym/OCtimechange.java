package com.example.usernamelogin.Staff.Gym_Management.Gymmng_changestogym;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Calendar;
import java.util.HashMap;

public abstract class OCtimechange extends Dialog {
    int minTime = 9 * 60;  // Default to 9:00 AM in minutes
    int maxTime = 22 * 60;
    Button octimecchg;
    TextView Opening,Closing;
    String Openingfromdb, Closingfromdb;
    public OCtimechange(Context context){
        super(context);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState != null ? savedInstanceState : new Bundle());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_octimechange, null);

        setContentView(view);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        setCanceledOnTouchOutside(true);
        setCancelable(true);
        octimecchg = view.findViewById(R.id.octimechange);
        Opening = view.findViewById(R.id.opening_time);
        Closing = view.findViewById(R.id.closing_time);


        DatabaseReference dbtimeadd = FirebaseDatabase.getInstance().getReference("Users/Gym_Owner")
                .child(Login.key_Gym_Staff1)
                .child("Gym")
                .child(Login.key_Gym_Staff3);


            dbtimeadd.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child("gym_opening").exists()){
                            Openingfromdb = snapshot.child("gym_opening").getValue().toString();
                            Opening.setText(Openingfromdb);
                        }

                        if(snapshot.child("gym_closing").exists()){
                            Closingfromdb = snapshot.child("gym_closing").getValue().toString();
                            Closing.setText(Closingfromdb);
                        }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        octimecchg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                octimeyeah();
            }
        });

    }
        private void octimeyeah(){

        Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();

            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
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
        Opening.setText(formatTime(Calendar1
                .get(Calendar.HOUR_OF_DAY), Calendar2.get(Calendar.MINUTE)));
    }
    private void showMaxTimePickerDialog() {

        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                (TimePicker view, int selectedHour, int selectedMinute) -> {
                    calendar1.set(Calendar.HOUR_OF_DAY, selectedHour);
                    calendar2.set(Calendar.MINUTE, selectedMinute);
                    updateMaxTextViews(calendar1,calendar2);
                    updatedb();
                    dialoginhouse();
        }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE), false);

        timePickerDialog.show();
    }
    private void updateMaxTextViews(Calendar Calendar1,Calendar Calendar2) {
        Closing.setText(formatTime(Calendar1
                .get(Calendar.HOUR_OF_DAY), Calendar2.get(Calendar.MINUTE)));
    }



    private void updatedb(){
        String openingset = Opening.getText().toString();
        String closingset = Closing.getText().toString();

        Log.d("TAGY", "THis is opening" + openingset);
        Log.d("TAGY", "THis is closing" + closingset);
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users/Gym_Owner")
                .child(Login.key_Gym_Staff1)
                .child("Gym")
                .child(Login.key_Gym_Staff3);

        HashMap<String, Object> timew = new HashMap<>();
        timew.put("gym_opening", openingset);
        timew.put("gym_closing", closingset);

        db.updateChildren(timew);

    }

    private String formatTime(int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        return android.text.format.DateFormat.format("hh:mm a", calendar).toString();
    }
    private void dialoginhouse(){
        AlertDialog.Builder builder1221 = new AlertDialog.Builder(getContext());
        builder1221.setTitle("Changes Saved!")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    }
}
