package com.example.usernamelogin.Staff.Gym_Management;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.R;
import java.util.ArrayList;

public class Adapter_Gym_Packages extends RecyclerView.Adapter<Adapter_Gym_Packages.MyViewHolder> {
    Context context;
    ArrayList<Model_Class_Adapter_Gym_Packages> list;
    interface_Adapter_Gym_packages interface_click;

    public Adapter_Gym_Packages(Context context, ArrayList<Model_Class_Adapter_Gym_Packages> list,interface_Adapter_Gym_packages interface_click ) {
        this.context = context;
        this.list = list;
        this.interface_click = interface_click;
    }

    @NonNull
    @Override
    public Adapter_Gym_Packages.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View x = LayoutInflater.from(context).inflate(R.layout.recyclerview_staff_gympackages_list,parent,false);
        return new Adapter_Gym_Packages.MyViewHolder(x,interface_click);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Gym_Packages.MyViewHolder holder, int position) {
        Model_Class_Adapter_Gym_Packages fromusers1 = list.get(position);
        holder.package_name.setText(fromusers1.getPackage_name());
        holder.package_descrp.setText(fromusers1.getPackage_descrp());
        holder.package_price.setText(fromusers1.getPackage_price());
        holder.package_mem_duration.setText(fromusers1.getPackage_mem_duration());
        holder.package_pushkey.setText(fromusers1.getPackage_pushkey());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView package_name,package_descrp,package_price,package_mem_duration, package_pushkey;
        public MyViewHolder(@NonNull View itemView, interface_Adapter_Gym_packages interface_click) {
            super(itemView);
            package_name = itemView.findViewById(R.id.dbpackage_name);
            package_descrp = itemView.findViewById(R.id.dbpackage_descrp);
            package_price = itemView.findViewById(R.id.dbprice);
            package_mem_duration = itemView.findViewById(R.id.dbduration);
            package_pushkey = itemView.findViewById(R.id.package_pk);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (interface_click != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            String pk = package_pushkey.getText().toString();
                            Gym_management_main.selected_pkg_pk = pk;

                            interface_click.onItemClick(pos);
                        }

                    }
                }
            });

        }
    }
}
