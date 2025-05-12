package com.example.usernamelogin.Gym_Owner.Gym_manage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.Admin.Userslist.gymanditsmembers.helper_class_for_memberslist;
import com.example.usernamelogin.R;
import com.example.usernamelogin.Staff.Memberslist.members_not_exp.Model_class_get_members_details;

import java.util.ArrayList;


public class Adapter_for_memberslistreal extends RecyclerView.Adapter<Adapter_for_memberslistreal.MyViewHolder>{
    private Context context;
    private ArrayList<Model_class_get_members_details> list;
    public Adapter_for_memberslistreal(Context context, ArrayList<Model_class_get_members_details> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_for_memberslistreal.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_for_adapter_frag_members_list,parent,false);
        return new Adapter_for_memberslistreal.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_for_memberslistreal.MyViewHolder holder, int position) {
        Model_class_get_members_details fromusers1 = list.get(position);
        holder.membername.setText(fromusers1.getUsername());
        holder.start_date.setText(fromusers1.getStart_date());
        holder.end_date.setText(fromusers1.getExpiration_date());
        holder.remaining_days.setText(fromusers1.getRemainind_days());
        holder.packagename.setText(fromusers1.getPackage_name());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView membername, start_date,end_date,remaining_days;
        TextView packagename;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            packagename = itemView.findViewById(R.id.package_name_from_user);
            membername = itemView.findViewById(R.id.tvMemberName);
            start_date = itemView.findViewById(R.id.tvStartDate);
            end_date = itemView.findViewById(R.id.tvExpirationDate);
            remaining_days = itemView.findViewById(R.id.remaining_date);

        }
    }

}
