package com.example.usernamelogin.NonMemberUser.Reservations.Date_picking_res;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import androidx.annotation.NonNull;

import com.example.usernamelogin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public abstract class Abstract_Date_picking_res extends Dialog  {

    public Abstract_Date_picking_res(@NonNull Context context) {
        super(context);
    }
    @Override
    public void onStart() {
        super.onStart();
        if (getWindow() != null) {
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_box_calendar_res_nonmem, null);
        setContentView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);



        CalendarView calendarView = view.findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // For now: do nothing or show a toast
                onDateSelected(year, month, dayOfMonth); // Let subclass handle it
            }
        });
    }

    // Subclasses must override this to handle the selected date
    public abstract void onDateSelected(int year, int month, int dayOfMonth);

}
