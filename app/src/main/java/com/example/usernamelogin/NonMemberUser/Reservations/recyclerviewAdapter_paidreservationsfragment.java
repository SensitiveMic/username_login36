package com.example.usernamelogin.NonMemberUser.Reservations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.R;

import java.util.ArrayList;

public class recyclerviewAdapter_paidreservationsfragment extends RecyclerView.Adapter<recyclerviewAdapter_paidreservationsfragment.MyViewHolder> {

Context context;
ArrayList<Helper_paidreservationsfragment> list;

    public recyclerviewAdapter_paidreservationsfragment(Context context, ArrayList<Helper_paidreservationsfragment> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_nonmember_paid_gym_res, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Helper_paidreservationsfragment user = list.get(position);
        holder.Date.setText(user.getDate());
        holder.Gym.setText(user.getGym_name());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Date, Gym;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Date = itemView.findViewById(R.id.dbdate);
            Gym = itemView.findViewById(R.id.dbgym_named);


        }
    }


}
