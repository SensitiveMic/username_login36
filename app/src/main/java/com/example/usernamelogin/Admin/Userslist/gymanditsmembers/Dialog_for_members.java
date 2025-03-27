package com.example.usernamelogin.Admin.Userslist.gymanditsmembers;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.usernamelogin.Admin.Admin_user_nonmember_click;
import com.example.usernamelogin.Admin.Gym.Admin_Update_Gym_Info;
import com.example.usernamelogin.R;

public abstract class Dialog_for_members extends Dialog {
    private Context context;
    Button Members_list, Change_gym_descrp, gym_employees;

    public Dialog_for_members(@NonNull Context context) {
        super(context);
        this.context = context;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState != null ? savedInstanceState : new Bundle());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.admin_gymlist_choose, null);
        setContentView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);

        Members_list = findViewById(R.id.memberlistbutton);
        Change_gym_descrp = findViewById(R.id.Change_gym_info);
        gym_employees = findViewById(R.id.Gym_employees);

        gym_employees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Dialog_for_staffandcoach lisdialog = new Dialog_for_staffandcoach(context, new givetoroots() {
                    @Override
                    public void clickeditem(Integer position, String[] array) {

                        //If there is an array install the array into the intent.

                        if (array != null && array.length >= 4) {
                            // Ensure array is not null and has at least two elements
                            String firstvalue = array[0];
                            String secondvalue = array[1];
                            String thirdvalue = array[2];
                            String foruth = array[3];
                            Log.d("TAGARRAYFIND", "array 1: " + firstvalue);
                            Log.d("TAGARRAYFIND", "array 2: " + secondvalue);
                            Log.d("TAGARRAYFIND", "array 3: " + thirdvalue);
                            Log.d("TAGARRAYFIND", "array 4: " + foruth);

                            Intent intenttoedit = new Intent(context, Admin_employeed_updateprofile.class);
                            intenttoedit.putExtra("stringarraytosend",array);
                            context.startActivity(intenttoedit);

                        } else {
                            Log.d("TAGARRAYFIND", "Array is null or does not have enough elements.");
                        }
                        if (position != null) {
                            Toast.makeText(context, "Item clicked from Staff or Coach at position: " + position, Toast.LENGTH_SHORT).show();
                            Log.d("TAGARRAYFIND", "pos " + position);
                        }
                    }
                }){


                };

                lisdialog.show();
            }
        });

        Members_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

         dialog_real_list_ofmembers listdialog = new dialog_real_list_ofmembers(context,this) {

             @Override
             public void onmembersclick(int position) {
                 Toast.makeText(context, "Item clicked at position: " + position, Toast.LENGTH_SHORT).show();

                 Intent intent = new Intent(context, Admin_user_nonmember_click.class);
                 context.startActivity(intent);
             }

             @Override
             protected void onCreate(Bundle savedInstanceState) {
                      super.onCreate(savedInstanceState);
             }


              };
              listdialog.show();
            }

        });

        Change_gym_descrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Update Gym Info activity
                Intent intent = new Intent(context, Admin_Update_Gym_Info.class);
                context.startActivity(intent);
            }
        });

    }
    private void getcoachorstaffinfo(){


    }


}
