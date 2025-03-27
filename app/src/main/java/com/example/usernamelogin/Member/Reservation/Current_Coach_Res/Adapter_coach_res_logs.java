package com.example.usernamelogin.Member.Reservation.Current_Coach_Res;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.usernamelogin.Member.Reservation.Model_class_coach_reslogsmember;
import com.example.usernamelogin.R;

import java.util.ArrayList;

public class Adapter_coach_res_logs extends RecyclerView.Adapter<Adapter_coach_res_logs.MyViewHolder> {

    Context context;
    ArrayList<Model_class_coach_reslogsmember> list;

    public Adapter_coach_res_logs(Context context, ArrayList<Model_class_coach_reslogsmember> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(context).inflate(R.layout.item_list_completed_res_logs,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model_class_coach_reslogsmember use = list.get(position);
        holder.coachname.setText((use.getCoach_name()));
        holder.gymdate.setText((use.getGym_meet_date()));
        holder.gymtime.setText((use.getGym_meet_time()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView coachname, gymdate,gymtime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            coachname = itemView.findViewById(R.id.dbcoach1);
            gymtime = itemView.findViewById(R.id.dbtime1);
            gymdate = itemView.findViewById(R.id.dbdate1);

        }
    }

}
