package com.example.usernamelogin.Gym_Owner.Gym_manage;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.usernamelogin.Admin.Admin_helper2;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public abstract class Abstract_class_add_gym extends Dialog {
    private Context context;
    Button add_opening_closing;
    EditText Gym_name,gym_contact_number,Gym_descp;
    Button confirmbutton;
    TextView opening_text, closing_text;
    public Abstract_class_add_gym(@NonNull Context context) {
        super(context);
        this.context =  context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState != null ? savedInstanceState : new Bundle());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_box_gym_owner_gymadding, null);
        setContentView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);

        if (getWindow() != null) {
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        confirmbutton = view.findViewById(R.id.Confirm);
        Gym_name = view.findViewById(R.id.editTextGym_Name);
        Gym_descp = view.findViewById(R.id.editTextGym_Decrp);
        opening_text = view.findViewById(R.id.editTextGym_Opening_time);
        closing_text = view.findViewById(R.id.editTextGym_Closing_time);
        add_opening_closing = view.findViewById(R.id.addOCtimebutton);
        gym_contact_number= view.findViewById(R.id.editTextGym_Contact_number);

        confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Gym_name1 = Gym_name.getText().toString();
                String Gym_descp1 = Gym_descp.getText().toString();
                String Gym_contactnumber = gym_contact_number.getText().toString();
                String gym_opentime =  opening_text.getText().toString();
                String gym_closetime = closing_text.getText().toString();

                if(Gym_name1.isEmpty()  || Gym_contactnumber.isEmpty()
                        || gym_opentime.isEmpty() || gym_closetime.isEmpty()) {

                    Toast.makeText(context,"Enter Texts in the Empty Fields",Toast.LENGTH_SHORT).show();

                }else{

                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users/Gym_Owner").child(Login.key_GymOwner)
                            .child("Gym");
                    DatabaseReference addedmyRef = myRef.push(); //parent
                    String userId = addedmyRef.getKey();
                    Admin_helper2 gymData = new Admin_helper2(
                            Gym_name1,
                            Gym_descp1,
                            null,
                            Gym_contactnumber,
                            gym_opentime,
                            gym_closetime
                    );

                    addedmyRef.setValue(gymData).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            dismiss();
                        } else {
                            Toast.makeText(context, "Failed to add gym.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
        add_opening_closing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                octimeyeah();
            }
        });
    }
    private void octimeyeah(){

        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
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

        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
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
