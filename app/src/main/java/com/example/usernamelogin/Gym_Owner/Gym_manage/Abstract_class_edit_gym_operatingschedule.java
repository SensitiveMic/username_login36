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

    }

    private void addopshours(TextView am, TextView pm){

    }

}
