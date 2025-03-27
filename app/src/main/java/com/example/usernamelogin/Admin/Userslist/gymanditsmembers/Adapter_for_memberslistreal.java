package com.example.usernamelogin.Admin.Userslist.gymanditsmembers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.Admin.Admin_main;
import com.example.usernamelogin.R;

import java.util.ArrayList;


public class Adapter_for_memberslistreal extends RecyclerView.Adapter<Adapter_for_memberslistreal.MyViewHolder>{
    private Context context;
    private ArrayList<helper_class_for_memberslist> list;
    private itemclickinterface itemclickinterface;

    public Adapter_for_memberslistreal(Context context, ArrayList<helper_class_for_memberslist> list, itemclickinterface itemclickinterface) {
        this.context = context;
        this.list = list;
        this.itemclickinterface = itemclickinterface;
    }

    @NonNull
    @Override
    public Adapter_for_memberslistreal.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list_for_members_admin,parent,false);
        return new Adapter_for_memberslistreal.MyViewHolder(v, itemclickinterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_for_memberslistreal.MyViewHolder holder, int position) {
        helper_class_for_memberslist fromusers1 = list.get(position);
        holder.memberusername.setText(fromusers1.getUsername());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView memberusername;

        public MyViewHolder(@NonNull View itemView,itemclickinterface itemclickinterface ) {
            super(itemView);
            memberusername = itemView.findViewById(R.id.dbmembername_memberlist);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemclickinterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            String text = memberusername.getText().toString();
                            Admin_main.non_member_username = text;
                            itemclickinterface.onmembersclick(pos);

                        }
                    }
                }
            });
        }
    }

}
