package com.example.usernamelogin.Gym_Owner.Gym_manage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.R;

import java.util.ArrayList;

public class Adapter_Gym_Owner_gymmanage_main extends RecyclerView.Adapter<Adapter_Gym_Owner_gymmanage_main.MYVIEWHOLDER> {
    Context context;
    ArrayList<Modelclass_gym_manage_Adapter> list;

    public Adapter_Gym_Owner_gymmanage_main(Context context, ArrayList<Modelclass_gym_manage_Adapter> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_Gym_Owner_gymmanage_main.MYVIEWHOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(context).inflate(R.layout.item_gym_info, parent, false);
        return new Adapter_Gym_Owner_gymmanage_main.MYVIEWHOLDER(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Gym_Owner_gymmanage_main.MYVIEWHOLDER holder, int position) {
        Modelclass_gym_manage_Adapter fromusers1 = list.get(position);
        holder.gym_name.setText(fromusers1.getGym_name());
        holder.gym_descrp.setText(fromusers1.getGym_descrp());
        holder.gym_contact_number.setText(fromusers1.getGym_contact_number());
        holder.gym_opening.setText(fromusers1.getGym_opening());
        holder.gym_closing.setText(fromusers1.getGym_closing());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MYVIEWHOLDER extends RecyclerView.ViewHolder {
        TextView gym_name, gym_descrp,gym_contact_number,gym_opening,gym_closing;
        public MYVIEWHOLDER(@NonNull View itemView) {
            super(itemView);
            gym_name = itemView.findViewById(R.id.tvGymName);
            gym_descrp = itemView.findViewById(R.id.tvGymDescription);
            gym_contact_number = itemView.findViewById(R.id.tvGymContact);
            gym_opening = itemView.findViewById(R.id.tvGymOpening);
            gym_closing = itemView.findViewById(R.id.tvGymClosing);

        }
    }
}
