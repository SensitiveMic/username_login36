package com.example.usernamelogin.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.R;

import java.util.ArrayList;

public class Adapter_recyclerview_Adminusers_all extends RecyclerView.Adapter<Adapter_recyclerview_Adminusers_all.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<users_all> list;

    public Adapter_recyclerview_Adminusers_all(Context context, ArrayList<users_all> list, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.list = list;
        this.recyclerViewInterface = recyclerViewInterface;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recyclerview_adminalluser_item,parent,false);
        return new MyViewHolder(v, recyclerViewInterface);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        users_all fromusers = list.get(position);
        holder.username.setText(fromusers.getUsername());
        holder.fullname.setText(fromusers.getFullname());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView username,fullname;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recycleViewInterface) {
            super(itemView);
                username = itemView.findViewById(R.id.dbusername);
                fullname = itemView.findViewById(R.id.dbfullname);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recycleViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            String text = username.getText().toString();
                            Admin_main.non_member_username = text;
                            recycleViewInterface.onItemClick(pos);

                        }
                    }
                }
            });
        }
    }
}
