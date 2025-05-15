package com.example.usernamelogin.Gym_Owner.employeelist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.usernamelogin.R;

import java.util.ArrayList;

public class Adapter_employee_list_fromgymowner extends RecyclerView.Adapter<Adapter_employee_list_fromgymowner.MyViewHolder> {
    private Context context;
    private ArrayList<Model_class_staffandcoachlist> list ;
    private static final int VIEW_TYPE_COACH = 0;
    private static final int VIEW_TYPE_STAFF = 1;
    private toeditcoachandstaff itemclick;
    private interface_Adapter_employee_list holdclick;

    public void updateList12(ArrayList<Model_class_staffandcoachlist> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }
    public Adapter_employee_list_fromgymowner(Context context, ArrayList<Model_class_staffandcoachlist> list
            , toeditcoachandstaff itemclick
           ,interface_Adapter_employee_list holdclick) {
        this.context = context;
        this.list = list;
        this.itemclick = itemclick;
        this.holdclick = holdclick;

    }
    @Override
    public int getItemViewType(int position) {
        Model_class_staffandcoachlist item = list.get(position);
        Log.d("ADAPTER_ROLE_CHECK", "User: " + item.getUsername() + " | Role: " + item.getRole());
        if ("Coach".equals(item.getRole())) {
            return VIEW_TYPE_COACH;
        } else if ("Staff".equals(item.getRole())) {
            return VIEW_TYPE_STAFF;
        }
        return -1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;

        if (viewType == VIEW_TYPE_COACH) {
            Log.d("VIEW_TYPE", "Inflating Coach layout");
            v = LayoutInflater.from(context).inflate(R.layout.item_list_gymowner_coachlist_wew, parent, false);
        } else {
            Log.d("VIEW_TYPE", "Inflating Staff layout");
            v = LayoutInflater.from(context).inflate(R.layout.item_list_gymowner_coachlist_wewstaff, parent, false);
        }
        return new MyViewHolder(v,itemclick,holdclick );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model_class_staffandcoachlist fromusers = list.get(position);
        holder.employeename.setText(fromusers.getUsername());
        holder.fullname_employee.setText(fromusers.getFullname());
        holder.gymname.setText(fromusers.getGym_name());
        holder.gym_ID.setText(fromusers.getGym_id());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView employeename, fullname_employee;
        TextView gymname, gym_ID;

        public MyViewHolder(@NonNull View itemView, toeditcoachandstaff itemclick ,interface_Adapter_employee_list holdclick) {
            super(itemView);
            employeename = itemView.findViewById(R.id.Employee_Name);
            fullname_employee = itemView.findViewById(R.id.Employee_fullname);
            gymname = itemView.findViewById(R.id.Gym_name_of_employee);
            gym_ID = itemView.findViewById(R.id.gym_ID);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (holdclick!=null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            String username = employeename.getText().toString();
                            employeelists_main.employeeclicked = username;
                            String gym_id = gym_ID.getText().toString();
                            employeelists_main.Gym_id = gym_id;

                            holdclick.onItemLongClick(pos);
                        }

                    }
                    return false;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemclick!=null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            String username = employeename.getText().toString();
                            employeelists_main.employeeclicked = username;

                            itemclick.onitmeclick(pos);
                        }

                    }
                }
            });
        }
    }

}
