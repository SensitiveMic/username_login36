package com.example.usernamelogin.Member.Reservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.Admin.Adapter_recyclerview_Adminusers_all;
import com.example.usernamelogin.Admin.Admin_main;
import com.example.usernamelogin.Admin.RecyclerViewInterface;
import com.example.usernamelogin.Admin.users_all;
import com.example.usernamelogin.R;

import java.util.ArrayList;

public class Adapter_Gym_coach_list_res extends RecyclerView.Adapter<Adapter_Gym_coach_list_res.MyViewHolder> {
    private final interface_coach_list_res_mem recyclerViewInterface;
    Context context;
    ArrayList<Model_class_Coach_list> list;
    public Adapter_Gym_coach_list_res(Context context, ArrayList<Model_class_Coach_list> list, interface_coach_list_res_mem recyclerViewInterface ) {
        this.context = context;
        this.list = list;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list_coach_list,parent,false);
        return new Adapter_Gym_coach_list_res.MyViewHolder(v,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model_class_Coach_list fromusers = list.get(position);
        holder.coachname.setText(fromusers.getUsername());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView coachname;
        public MyViewHolder(@NonNull View itemView, interface_coach_list_res_mem recycleViewInterface) {
            super(itemView);
            coachname = itemView.findViewById(R.id.Coach_Name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recycleViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){

                            String text = coachname.getText().toString();
                            Coach_Reservation_main.picked_coach = text;

                            recycleViewInterface.onItemClick(pos);

                        }
                    }
                }

            });
        }
    }
}
