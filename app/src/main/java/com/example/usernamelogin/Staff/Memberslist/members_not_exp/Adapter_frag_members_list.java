package com.example.usernamelogin.Staff.Memberslist.members_not_exp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.R;

import java.util.ArrayList;

public class Adapter_frag_members_list extends RecyclerView.Adapter<Adapter_frag_members_list.MyViewHolder> {

    Context context;
    ArrayList<Model_class_get_members_details> list;

    public Adapter_frag_members_list(Context context, ArrayList<Model_class_get_members_details> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_frag_members_list.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View x = LayoutInflater.from(context).inflate(R.layout.item_for_adapter_frag_members_list,parent,false);
        return new MyViewHolder(x);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_frag_members_list.MyViewHolder holder, int position) {
        Model_class_get_members_details reslist = list.get(position);
        holder.membername.setText(reslist.getUsername());
        holder.start_date.setText(reslist.getStart_date());
        holder.end_date.setText(reslist.getExpiration_date());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView membername, start_date,end_date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            membername = itemView.findViewById(R.id.tvMemberName);
            start_date = itemView.findViewById(R.id.tvStartDate);
            end_date = itemView.findViewById(R.id.tvExpirationDate);

        }
    }

}
