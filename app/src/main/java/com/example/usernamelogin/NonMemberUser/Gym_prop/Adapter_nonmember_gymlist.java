package com.example.usernamelogin.NonMemberUser.Gym_prop;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.R;
import com.example.usernamelogin.Staff.Fragment_pending;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class Adapter_nonmember_gymlist extends RecyclerView.Adapter<Adapter_nonmember_gymlist.MyViewHolder>  {

    interface_gym_list interface_gym_list;
    Context context;
    ArrayList<Helper_class_for_gym_list> list = new ArrayList<>();
    DatabaseReference checkstored;

    public Adapter_nonmember_gymlist(com.example.usernamelogin.NonMemberUser.Gym_prop.interface_gym_list interface_gym_list, Context context, ArrayList<Helper_class_for_gym_list> list) {
        this.interface_gym_list = interface_gym_list;
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_nonmember_gymlist.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View x = LayoutInflater.from(context).inflate(R.layout.recyclerview_nonmember_choosegym_item,parent,false);
        return new Adapter_nonmember_gymlist.MyViewHolder(x,interface_gym_list );


    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_nonmember_gymlist.MyViewHolder holder, int position) {
        Helper_class_for_gym_list fromusers1 = list.get(position);
        holder.gym_name.setText(fromusers1.getGym_name());
        holder.gym_descrp.setText(fromusers1.getGym_descrp());
        holder.gym_owner_username.setText(fromusers1.getGym_owner_username());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView gym_name, gym_descrp, gym_owner_username;
        public MyViewHolder(@NonNull View itemView, interface_gym_list interface_gym_list ) {
            super(itemView);

            gym_name = itemView.findViewById(R.id.dbgymname);
            gym_descrp = itemView.findViewById(R.id.editTextGym_Decrp1);
            gym_owner_username = itemView.findViewById(R.id.dbgymownername);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(interface_gym_list != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            String gymname = gym_name.getText().toString();
                            Log.d("TAG8", "Gymname from adapter: " + Apply_a_gym_fragment.Gymnameformembershipreq);
                           Apply_a_gym_fragment.Gymnameformembershipreq = gymname;

                            interface_gym_list.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }
}
