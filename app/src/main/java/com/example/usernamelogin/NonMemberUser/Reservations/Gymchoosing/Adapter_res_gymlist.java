package com.example.usernamelogin.NonMemberUser.Reservations.Gymchoosing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.NonMemberUser.Reservations.Add_Reservations;
import com.example.usernamelogin.R;

import java.util.ArrayList;

public class Adapter_res_gymlist extends RecyclerView.Adapter<Adapter_res_gymlist.MyViewHolder> {
    private final interface_clicking_gym_res gym_interface_clicking;
    private ArrayList<Modelclass> list ;
    Context context;

    public Adapter_res_gymlist(Context context ,ArrayList<Modelclass> list,interface_clicking_gym_res gym_interface_clicking ) {
        this.list = list;
        this.context = context;
        this.gym_interface_clicking = gym_interface_clicking;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView gym_name, gym_descrp ,invisible_contactnumber;

        public MyViewHolder(@NonNull View itemView, interface_clicking_gym_res recycleViewInterface ) {
            super(itemView);
            gym_name = itemView.findViewById(R.id.Gym_Name_res);
            gym_descrp = itemView.findViewById(R.id.gym_descrp);
            invisible_contactnumber = itemView.findViewById(R.id.gym_contact_numwew);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recycleViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            String text = gym_name.getText().toString();
                            Add_Reservations.gymnamefromresdialoggymlist = text;
                            String text1 = invisible_contactnumber.getText().toString();
                            Add_Reservations.gym_contact_numberforview = text1;
                            recycleViewInterface.onItemClick1(pos);

                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public Adapter_res_gymlist.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gym_list, parent, false);
        return new MyViewHolder(view, gym_interface_clicking );
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_res_gymlist.MyViewHolder holder, int position) {
        Modelclass fromusers1 = list.get(position);
        holder.gym_name.setText(fromusers1.getGym_name());
        holder.gym_descrp.setText(fromusers1.getGym_descrp());
        holder.invisible_contactnumber.setText(fromusers1.getGym_contact_number());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
