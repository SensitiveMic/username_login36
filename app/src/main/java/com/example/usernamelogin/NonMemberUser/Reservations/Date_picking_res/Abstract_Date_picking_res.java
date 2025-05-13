package com.example.usernamelogin.NonMemberUser.Reservations.Date_picking_res;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;

import androidx.annotation.NonNull;

import com.example.usernamelogin.R;

public abstract class Abstract_Date_picking_res extends Dialog {
    public Abstract_Date_picking_res(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState != null ? savedInstanceState : new Bundle());
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
