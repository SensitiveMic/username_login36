package com.example.usernamelogin.Staff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class Adapter_staff_reservation_accepted extends RecyclerView.Adapter<Adapter_staff_reservation_accepted.FirstViewHolder> {

    private ArrayList<newHelper_reservation_staff> list;
    Context context1;

    public Adapter_staff_reservation_accepted(ArrayList<newHelper_reservation_staff> list, Context context1) {
        this.list = list;
        this.context1 = context1;
    }

    @NonNull
    @Override
    public FirstViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context1).inflate(R.layout.recyclerview_staff_res_item_accepted, parent, false);
        return new FirstViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_staff_reservation_accepted.FirstViewHolder holder, int position) {
        newHelper_reservation_staff fromusers1 = list.get(position);
        holder.user.setText(fromusers1.getUser());
        holder.date.setText(fromusers1.getDate());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class FirstViewHolder extends RecyclerView.ViewHolder {
        TextView user, date;


        public FirstViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.dbdate);
            user = itemView.findViewById(R.id.dbuser);

        }

    }


}



