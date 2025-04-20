package com.example.usernamelogin.NonMemberUser.dialogbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.NonMemberUser.Gym_prop.Gym_Properties_Main;
import com.example.usernamelogin.R;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MyViewHolder> {
     private final interface_dialog_list recyclerViewInterface1;
     Context context;
     ArrayList<Mode_class_packagelist> list;

    public EmployeeAdapter(Context context, ArrayList<Mode_class_packagelist> list
                    , interface_dialog_list recyclerViewInterface1) {
        this.recyclerViewInterface1 = recyclerViewInterface1;
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public EmployeeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_employee, parent, false);
        return new MyViewHolder(view, recyclerViewInterface1);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.MyViewHolder holder, int position) {
        Mode_class_packagelist fromusers1 = list.get(position);
        holder.name.setText(fromusers1.getPackage_name());
        holder.email.setText(fromusers1.getPackage_price());
        holder.packagedescrp.setText(fromusers1.getPackage_descrp());
        holder.package_mem__duration.setText(fromusers1.getPackage_mem_duration());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView email;
        TextView packagedescrp;
        TextView package_mem__duration;
        public MyViewHolder(@NonNull View itemView, interface_dialog_list recycleViewInterface) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            email = itemView.findViewById(R.id.tvEmail);
            packagedescrp = itemView.findViewById(R.id.descrs);
            package_mem__duration = itemView.findViewById(R.id.pckg_dur);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recycleViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){

                            String packagename = name.getText().toString();
                          //  Log.d("TAG8", "onClick: " +packagename );
                            String packageprice = email.getText().toString();
                            String packagedes = packagedescrp.getText().toString();
                            String package_duration = package_mem__duration.getText().toString();
                           // Log.d("TAG8", "onClick: " +packageprice );
                            Gym_Properties_Main.Gym_package_selected = new String[4];
                            Gym_Properties_Main.Gym_package_selected[0]= packagename;
                            Gym_Properties_Main.Gym_package_selected[1]= packageprice;
                            Gym_Properties_Main.Gym_package_selected[2]= packagedes;
                            Gym_Properties_Main.Gym_package_selected[3]= package_duration;

                            recycleViewInterface.onItemClick1(pos);

                        }
                    }
                }
            });
        }
    }

}

