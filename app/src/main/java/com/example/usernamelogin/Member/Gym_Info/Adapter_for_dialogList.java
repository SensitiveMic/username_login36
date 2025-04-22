package com.example.usernamelogin.Member.Gym_Info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.NonMemberUser.dialogbox.Mode_class_packagelist;
import com.example.usernamelogin.R;

import java.util.ArrayList;

public class Adapter_for_dialogList extends RecyclerView.Adapter<Adapter_for_dialogList.MyViewHolder> {

    private final interface_for_gyminfo_dialog recyclerViewInterface1;
    Context context;
    ArrayList<Mode_class_packagelist> list;

    public Adapter_for_dialogList( Context context, ArrayList<Mode_class_packagelist> list,interface_for_gyminfo_dialog recyclerViewInterface1) {
        this.recyclerViewInterface1 = recyclerViewInterface1;
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_for_dialogList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_employee, parent, false);
        return new MyViewHolder(view, recyclerViewInterface1);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_for_dialogList.MyViewHolder holder, int position) {
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

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView email;
        TextView packagedescrp;
        TextView package_mem__duration;
        public MyViewHolder(@NonNull View itemView, interface_for_gyminfo_dialog recyclerViewInterface1) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            email = itemView.findViewById(R.id.tvEmail);
            packagedescrp = itemView.findViewById(R.id.descrs);
            package_mem__duration = itemView.findViewById(R.id.pckg_dur);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface1 != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            String packagename = name.getText().toString();
                            String packageprice = email.getText().toString();
                            String packagedes = packagedescrp.getText().toString();
                            String package_duration = package_mem__duration.getText().toString();

                            Member_Gym_info_main.package_name_adap = packagename;
                            Member_Gym_info_main.package_descrip_adap = packagedes;
                            Member_Gym_info_main.package_dura_adap = package_duration;
                            Member_Gym_info_main.package_price_adap = packageprice;

                            recyclerViewInterface1.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }
}
