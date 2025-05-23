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

    public Adapter_employee_list_fromgymowner(Context context, ArrayList<Model_class_staffandcoachlist> list, toeditcoachandstaff itemclick) {
        this.context = context;
        this.list = list;
        this.itemclick = itemclick;
    }
    @Override
    public int getItemViewType(int position) {
        Model_class_staffandcoachlist item = list.get(position);
        if ("Coach".equals(item.getRole())) {
            return VIEW_TYPE_COACH;
        } else if ("Staff".equals(item.getRole())) {
            return VIEW_TYPE_STAFF;
        }
        return -1; // Default case, shouldn't happen
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v ;

        if (viewType == VIEW_TYPE_COACH){
            v = LayoutInflater.from(context).inflate(R.layout.item_list_gymowner_coachlist_wew,parent,false);
        }
        else {
            v = LayoutInflater.from(context).inflate(R.layout.item_list_gymowner_coachlist_wewstaff,parent,false);
        }
        return new MyViewHolder(v,itemclick );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model_class_staffandcoachlist fromusers = list.get(position);
        holder.employeename.setText(fromusers.getUsername());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView employeename, emprole;

        public MyViewHolder(@NonNull View itemView, toeditcoachandstaff itemclick) {
            super(itemView);
            employeename = itemView.findViewById(R.id.Employee_Name);

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
