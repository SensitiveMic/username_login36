package com.example.usernamelogin.Gym_Owner.Gym_manage;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.usernamelogin.R;

public abstract class Abstract_class_edit_gym_operatingschedule extends Dialog {
    private Context context;
    LinearLayout MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY;
    TextView Monday_open,Monday_close;
    TextView Tuesday_open,Tuesday_close;
    TextView Wednesday_open,Wednesday_close;
    TextView Thursday_open,Thursday_close;
    TextView Friday_open,Friday_close;
    TextView Saturday_open,Saturday_close;
    TextView Sunday_open,Sunday_close;
    Button Mon_btn,Tue_btn,Wed_btn,Thu_btn,Fri_btn,Sat_btn,Sun_btn,Confirm_all;
    public Abstract_class_edit_gym_operatingschedule(@NonNull Context context) {
        super(context);
        this.context =  context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view  = LayoutInflater.from(getContext()).inflate(R.layout.dialog_box_gym_owner_adding_ops_sched, null);
        setContentView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        if (getWindow() != null) {
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        Monday_open = view.findViewById(R.id.Mon_open);
        Monday_close = view.findViewById(R.id.Mon_close);
        Tuesday_open = view.findViewById(R.id.Tue_open);
        Tuesday_close = view.findViewById(R.id.Tue_close);
        Wednesday_open  = view.findViewById(R.id.Wed_open);
        Wednesday_close = view.findViewById(R.id.Wed_close);
        Thursday_open = view.findViewById(R.id.Thu_open);
        Thursday_close = view.findViewById(R.id.Thu_close);
        Friday_open = view.findViewById(R.id.Fri_open);
        Friday_close = view.findViewById(R.id.Fri_close);
        Saturday_open = view.findViewById(R.id.Sat_open);
        Saturday_close = view.findViewById(R.id.Sat_close);
        Sunday_open = view.findViewById(R.id.Sun_open);
        Sunday_close = view.findViewById(R.id.Sun_close);

        Mon_btn = view.findViewById(R.id.Open_Close_mon);
        Tue_btn = view.findViewById(R.id.Open_Close_tue);
        Wed_btn = view.findViewById(R.id.Open_Close_wed);
        Thu_btn = view.findViewById(R.id.Open_Close_thu);
        Fri_btn = view.findViewById(R.id.Open_Close_fri);
        Sat_btn = view.findViewById(R.id.Open_Close_sat);
        Sun_btn = view.findViewById(R.id.Open_Close_sun);
        Confirm_all = view.findViewById(R.id.confirm_adding_sched);

        MONDAY = view.findViewById(R.id.MONDAY_LAYOUT);
        TUESDAY = view.findViewById(R.id.TUESDAY_LAYOUT);
        WEDNESDAY = view.findViewById(R.id.WEDNESDAY_LAYOUT);
        THURSDAY = view.findViewById(R.id.THURSDAY_LAYOUT);
        FRIDAY = view.findViewById(R.id.FRIDAY_LAYOUT);
        SATURDAY = view.findViewById(R.id.SATURDAY_LAYOUT);
        SUNDAY = view.findViewById(R.id.SUNDAY_LAYOUT);
    }

    private void addopshours(TextView am, TextView pm){

    }

}
