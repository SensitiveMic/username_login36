package com.example.usernamelogin.Gym_Owner.Gym_manage;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.usernamelogin.Admin.Admin_helper2;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class Abstract_class_add_gym extends Dialog {
    private Context context;
    Button add_opening_closing;
    EditText Gym_name,gym_contact_number,Gym_descp;
    Button confirmbutton;

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

        gym_contact_number= view.findViewById(R.id.editTextGym_Contact_number);

        confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                Context context = getOwnerActivity() != null ? getOwnerActivity() : getContext();
                Abstract_class_edit_gym_operatingschedule listdialog = new Abstract_class_edit_gym_operatingschedule(context) {
                    @Override
                    protected void onCreate(Bundle savedInstanceState) {
                        super.onCreate(savedInstanceState);
                    }
                };
                listdialog.setCancelable(true);
                listdialog.show();
                new Handler(Looper.getMainLooper()).post(() -> {
                    Window window = listdialog.getWindow();
                    if (window != null) {
                        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    }
                });
            }
        });

    }
    private void addgym_details(){
        String Gym_name1 = Gym_name.getText().toString();
        String Gym_descp1 = Gym_descp.getText().toString();
        String Gym_contactnumber = gym_contact_number.getText().toString();
        if(Gym_name1.isEmpty()  || Gym_contactnumber.isEmpty()) {
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
                    null,
                    null
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

}
