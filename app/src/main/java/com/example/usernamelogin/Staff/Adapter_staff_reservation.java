package com.example.usernamelogin.Staff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.Admin.Gym.Adapter_recyclerview_add_gym;
import com.example.usernamelogin.Admin.Gym.add_gym_recyclerviewAdapter_helper;
import com.example.usernamelogin.Admin.RecyclerViewInterface;
import com.example.usernamelogin.Admin.users_all;
import com.example.usernamelogin.NonMemberUser.Reservations.Reservations;
import com.example.usernamelogin.R;

import java.util.ArrayList;

public class Adapter_staff_reservation extends RecyclerView.Adapter<Adapter_staff_reservation.MyViewHolder> {
    recyclerViewInterface_staff1 recyclerViewInterface_staff1;
    Context context;
    ArrayList<newHelper_reservation_staff> list;

    public Adapter_staff_reservation(com.example.usernamelogin.Staff.recyclerViewInterface_staff1 recyclerViewInterface_staff1, Context context, ArrayList<newHelper_reservation_staff> list) {
        this.recyclerViewInterface_staff1 = recyclerViewInterface_staff1;
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View x = LayoutInflater.from(context).inflate(R.layout.recyclerview_staff_res_item,parent,false);
        return new MyViewHolder(x,recyclerViewInterface_staff1);

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_staff_reservation.MyViewHolder holder, int position) {
        newHelper_reservation_staff fromusers1 = list.get(position);
        holder.user.setText(fromusers1.getUser());
        holder.date.setText(fromusers1.getDate());
        holder.res_id.setText(fromusers1.getRes_id());

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView user,date;
        TextView res_id;

        public MyViewHolder(@NonNull View itemView, recyclerViewInterface_staff1 interfaceStaff1) {
            super(itemView);
            date = itemView.findViewById(R.id.dbdate);
            user = itemView.findViewById(R.id.dbuser);
            res_id = itemView.findViewById(R.id.hiddenid);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (interfaceStaff1 != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {

                            Fragment_pending.res_name = res_id.getText().toString();

                            interfaceStaff1.onItemClick1(pos);
                        }
                    }
                }
            });

        }
    }
}
