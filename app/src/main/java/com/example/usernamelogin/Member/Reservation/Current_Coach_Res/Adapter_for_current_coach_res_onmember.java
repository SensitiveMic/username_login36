package com.example.usernamelogin.Member.Reservation.Current_Coach_Res;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.Coach.Coach_main;
import com.example.usernamelogin.Coach.Model_class_pendingresdisplay;
import com.example.usernamelogin.Coach.recyclerViewAdapter_coach_main_pending_req;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Adapter_for_current_coach_res_onmember extends RecyclerView.Adapter<Adapter_for_current_coach_res_onmember.MyVIEWholder> {

   Context context;
   ArrayList<Modelclass_for_current_member_res_accepted> list;
    private static final int VIEW_TYPE_ONE = 1; // accepted
    private static final int VIEW_TYPE_TWO = 2; // pending
    private static final int VIEW_TYPE_THREE = 3; // denied

    public Adapter_for_current_coach_res_onmember(Context context, ArrayList<Modelclass_for_current_member_res_accepted> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getItemViewType(int position) {
        Modelclass_for_current_member_res_accepted item = list.get(position);
        return item.getViewType(); // Return the viewType from the data model
    }

    @NonNull
    @Override
    public MyVIEWholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == VIEW_TYPE_ONE) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_member_current_res_accepted, parent, false);
        } else if (viewType == VIEW_TYPE_TWO) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_member_current_res, parent, false);
        } else if (viewType == VIEW_TYPE_THREE) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_member_current_res_denied, parent, false);
        }else {
            // Handle default case or throw an exception for unknown viewType
            throw new IllegalArgumentException("Invalid viewType: " + viewType);
        }
        return new Adapter_for_current_coach_res_onmember.MyVIEWholder(itemView, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVIEWholder holder, int position) {
        Modelclass_for_current_member_res_accepted req_list = list.get(position);
        holder.Coach_Name.setText(req_list.getCoach_name());
        holder.res_time.setText(req_list.getGym_meet_time());
        holder.res_date.setText(req_list.getGym_meet_date());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class MyVIEWholder extends RecyclerView.ViewHolder {

        TextView Coach_Name, res_time,res_date , delete_button, reasonbutton;
        String reason1;

        public MyVIEWholder(@NonNull View itemView, int viewType) {
            super(itemView);

            Coach_Name = itemView.findViewById(R.id.Coach_name_crrunt);
            res_time = itemView.findViewById(R.id.Scheduled_meet_time);
            res_date = itemView.findViewById(R.id.Scheduled_meet_date);




            if(viewType == VIEW_TYPE_THREE) {
                delete_button = itemView.findViewById(R.id.deletereservation1);
                reasonbutton = itemView.findViewById(R.id.reasonButton);
                delete_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String coach_res_todel = Coach_Name.getText().toString();

                        DatabaseReference add_as_pending_reservation = FirebaseDatabase.getInstance()
                                .getReference("Users/Non-members")
                                .child(Login.key)
                                .child("Coach_Reservation").child("Current_Accepted_Res");

                        Query query1 = add_as_pending_reservation.orderByChild("coach_name").equalTo(coach_res_todel);

                        query1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {

                                    for (DataSnapshot gk : snapshot.getChildren()) {

                                        String key = gk.getKey();
                                        DatabaseReference fordl = add_as_pending_reservation.child(key);
                                        fordl.removeValue();

                                    }
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setTitle("Successfully Deleted");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Get the input text
                                            refreshActivity();

                                        }
                                    });

                                    AlertDialog dialog = builder.create();
                                    // Set the dialog to not close when touched outside
                                    dialog.setCanceledOnTouchOutside(false);

                                    // Set the dialog to not be cancelable with the back button
                                    dialog.setCancelable(false);
                                    dialog.show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                }
                );
                reasonbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater inflater = LayoutInflater.from(context);
                        View view = inflater.inflate(R.layout.coachreservationclickreason, null);
                        TextView thereason = view.findViewById(R.id.denycontent_reason);

                        String coach =  Coach_Name.getText().toString();
                        DatabaseReference reason = FirebaseDatabase.getInstance()
                                .getReference("Users/Non-members")
                                .child(Login.key)
                                .child("Coach_Reservation")
                                .child("coach_denial")
                                .child(coach+"reasons");
                        reason.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    reason1 = snapshot.getValue(String.class);
                                    thereason.setText(reason1);
                                }
                                else{
                                    Log.d("TAGR", "no reason ");
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                        Log.d("TAGR", "onClick: " + reason1);


                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setView(view);

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
            }
        }

    }

    private void refreshActivity() {
        Intent intent = ((Activity) context).getIntent();
        ((Activity) context).finish();
        ((Activity) context).startActivity(intent);
    }

}
