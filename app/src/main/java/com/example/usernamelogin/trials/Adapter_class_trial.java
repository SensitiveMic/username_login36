package com.example.usernamelogin.trials;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.usernamelogin.Admin.Admin_main;
import com.example.usernamelogin.Admin.RecyclerViewInterface;
import com.example.usernamelogin.R;


import java.util.ArrayList;

public class Adapter_class_trial extends RecyclerView.Adapter<Adapter_class_trial.MyViewHolder> {
    private final trial_interface trial_interface1;
    ArrayList<Model_class3> list ;
    Context context;



    public Adapter_class_trial(Context context ,ArrayList<Model_class3> list, trial_interface trial_interface1) {
        this.list = list;
        this.context = context;
        this.trial_interface1 = trial_interface1;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView user;
        public MyViewHolder(@NonNull View itemView,trial_interface trial_interface1) {
            super(itemView);
            user = itemView.findViewById(R.id.dbuser);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (trial_interface1 != null){
                        int pos = getAdapterPosition();




                        if (pos != RecyclerView.NO_POSITION){

                            trial_interface1.onItemClick(pos);

                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public Adapter_class_trial.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.trial_recyclerview_item,parent,false);
        return new Adapter_class_trial.MyViewHolder(v, trial_interface1);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_class_trial.MyViewHolder holder, int position) {
        Model_class3 currentItem = list.get(position);
        holder.user.setText(currentItem.getUser());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}