package com.example.usernamelogin.Staff.Membership_req_management;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.Admin.Admin_main;
import com.example.usernamelogin.R;
import com.example.usernamelogin.Staff.Gym_Management.Adapter_Gym_Packages;
import com.example.usernamelogin.Staff.Gym_Management.Model_Class_Adapter_Gym_Packages;
import com.example.usernamelogin.Staff.Staff_main;

import java.util.ArrayList;

public class Adapter_MmbrRq_lst extends RecyclerView.Adapter<Adapter_MmbrRq_lst.MyViewHolder> {
    Context context;
    ArrayList<Model_class_mmbershpr> list ;
    interface_membership_requests_list interface_click;

    public Adapter_MmbrRq_lst(Context context, ArrayList<Model_class_mmbershpr> list, interface_membership_requests_list interface_click) {
        this.context = context;
        this.list = list;
        this.interface_click = interface_click;
    }

    @NonNull
    @Override
    public Adapter_MmbrRq_lst.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View x = LayoutInflater.from(context).inflate(R.layout.recyclerview_membershiprquest_list_item,parent,false);
        return new Adapter_MmbrRq_lst.MyViewHolder(x, interface_click);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_MmbrRq_lst.MyViewHolder holder, int position) {
        Model_class_mmbershpr fromusers1 = list.get(position);
        holder.package_name.setText(fromusers1.getPackage_name());
        holder.username.setText(fromusers1.getUsername());
        holder.price.setText(fromusers1.getPackage_price());
        holder.timeanddate.setText(fromusers1.getTimeandDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView package_name, username, price, timeanddate;
        CardView kasibag;
        public MyViewHolder(@NonNull View itemView, interface_membership_requests_list interface_click) {
            super(itemView);

            package_name = itemView.findViewById(R.id.dbpackage_name);
            username = itemView.findViewById(R.id.dbrequesteduser);
            price = itemView.findViewById(R.id.dbprice);
            timeanddate = itemView.findViewById(R.id.dbreqsentdate);
            kasibag = itemView.findViewById(R.id.membership_requests_list);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (interface_click != null){
                        int pos =getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){

                            String text = username.getText().toString();
                            Membership_requests_main.usernamefrmmmbrshpreq = text;

                            interface_click.onItemClick(pos);
                        }


                    }
                }
            });
        }
    }

}
