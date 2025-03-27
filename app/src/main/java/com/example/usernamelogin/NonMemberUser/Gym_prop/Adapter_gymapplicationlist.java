package com.example.usernamelogin.NonMemberUser.Gym_prop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.NonMemberUser.Reservations.Gymchoosing.Adapter_res_gymlist;
import com.example.usernamelogin.R;

import java.util.ArrayList;


public class Adapter_gymapplicationlist extends RecyclerView.Adapter<Adapter_gymapplicationlist.MyViewHolder>  {
    Context context;
    private ArrayList<Helper_class_gymapplicationdetails> list ;

    public Adapter_gymapplicationlist(Context context, ArrayList<Helper_class_gymapplicationdetails> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_gymapplicationlist.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gymapplication_list, parent, false);
        return new Adapter_gymapplicationlist.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_gymapplicationlist.MyViewHolder holder, int position) {
        Helper_class_gymapplicationdetails wewder = list.get(position);
        holder.gymname.setText(wewder.getGym_name());
        holder.packagename.setText(wewder.getPackage_name());
        holder.packageprice.setText(wewder.getPackage_price());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {

    TextView packagename, packageprice, gymname;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            gymname = itemView.findViewById(R.id.dbGymname);
            packagename = itemView.findViewById(R.id.dbpackagename);
            packageprice = itemView.findViewById(R.id.dbprice);

        }
    }
}
