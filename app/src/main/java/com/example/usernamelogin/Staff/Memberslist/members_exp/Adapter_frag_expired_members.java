package com.example.usernamelogin.Staff.Memberslist.members_exp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.R;
import com.example.usernamelogin.Staff.Adapter_staff_reservation;
import com.example.usernamelogin.Staff.Memberslist.members_not_exp.Model_class_get_members_details;

import java.util.ArrayList;

public class Adapter_frag_expired_members extends RecyclerView.Adapter<Adapter_frag_expired_members.MyViewHolder> {
    Context context;
    ArrayList<Model_class_get_members_details> list;
    interface_click_exp_members interface_click;

    public Adapter_frag_expired_members(Context context, ArrayList<Model_class_get_members_details> list, interface_click_exp_members interface_click) {
        this.context = context;
        this.list = list;
        this.interface_click = interface_click;
    }

    @NonNull
    @Override
    public Adapter_frag_expired_members.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View x = LayoutInflater.from(context).inflate(R.layout.item_for_adapter_frag_exp_mem_list,parent,false);
        return new MyViewHolder(x,interface_click);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_frag_expired_members.MyViewHolder holder, int position) {
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
        Button remove_mem;
        public MyViewHolder(@NonNull View itemView,  interface_click_exp_members interface_click) {
            super(itemView);

            membername = itemView.findViewById(R.id.tvMemberName_exp);
            start_date = itemView.findViewById(R.id.tvStartDate_exp);
            end_date = itemView.findViewById(R.id.tvExpirationDate_exp);
            remove_mem = itemView.findViewById(R.id.Remove_membership);

            remove_mem.setOnClickListener(v -> {
                if (interface_click != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    interface_click.onItemClick(getAdapterPosition());
                }
            });

        }
    }
}
