package com.example.usernamelogin.Admin.Gym;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.Admin.Admin_main;
import com.example.usernamelogin.Admin.RecyclerViewInterface;
import com.example.usernamelogin.Admin.Userslist.gymanditsmembers.UsersList_Admin_main;
import com.example.usernamelogin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Adapter_recyclerview_add_gym extends RecyclerView.Adapter<Adapter_recyclerview_add_gym.MyViewHolder> {
    Context context;
    private ArrayList<add_gym_recyclerviewAdapter_helper> list;
    private final Admin_Gym_RecyclerViewInterface recyclerViewInterface;
    private final Admin_gym_longclikc_interface longclikcInterface;

    public Adapter_recyclerview_add_gym(Context context, ArrayList<add_gym_recyclerviewAdapter_helper> list
            , Admin_Gym_RecyclerViewInterface recyclerViewInterface
            ,Admin_gym_longclikc_interface longclikcInterface) {
        this.context = context;
        this.list = list;
        this.recyclerViewInterface = recyclerViewInterface;
        this.longclikcInterface = longclikcInterface;
    }

    @NonNull
    @Override
    public Adapter_recyclerview_add_gym.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recyclerview_add_gym_item,parent,false);
        return new Adapter_recyclerview_add_gym.MyViewHolder(v, recyclerViewInterface,longclikcInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_recyclerview_add_gym.MyViewHolder holder, int position) {
        add_gym_recyclerviewAdapter_helper fromusers1 = list.get(position);
        holder.gym_name.setText(fromusers1.getGym_name());
        holder.fullname.setText(fromusers1.getFullname());
        holder.gym_owner_username1.setText(fromusers1.getGym_owner_username());

        holder.itemView.setOnLongClickListener(v -> {
            if (longclikcInterface != null && position != RecyclerView.NO_POSITION) {
                UsersList_Admin_main.gyym_owners_usernmae = fromusers1.getGym_owner_username();
                UsersList_Admin_main.gym_owner_KEY = fromusers1.getGym_owner_key();
                UsersList_Admin_main.gymownersgymname = fromusers1.getGym_name();
                UsersList_Admin_main.gym_KEY = fromusers1.getGymkey(); // now we can use it!
                longclikcInterface.onitemlongclick(position);
            }
            return true;
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView gym_owner_username1,gym_name, fullname;
        TextView gym_owner_key;

        public MyViewHolder(@NonNull View itemView, Admin_Gym_RecyclerViewInterface recyclerViewInterface
        ,Admin_gym_longclikc_interface longclikcInterface) {
            super(itemView);
            gym_owner_username1 = itemView.findViewById(R.id.dbgymowner_username);
            gym_name = itemView.findViewById(R.id.dbgymname);
            fullname = itemView.findViewById(R.id.dbgymowner_fullname);
            gym_owner_key = itemView.findViewById(R.id.gym_ownerID);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){

                            String text = gym_owner_username1.getText().toString();
                            UsersList_Admin_main.gyym_owners_usernmae = text;

                            String text2 = gym_owner_key.getText().toString();
                            UsersList_Admin_main.gym_owner_KEY = text2;

                            String gymname = gym_name.getText().toString();
                            UsersList_Admin_main.gymownersgymname = gymname;


                            recyclerViewInterface.onItemClick(pos);
                        }
                    }

                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    if (longclikcInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){

                            String text2 = gym_owner_key.getText().toString();
                            UsersList_Admin_main.gym_owner_KEY = text2;

                            String text = gym_owner_username1.getText().toString();
                            UsersList_Admin_main.gyym_owners_usernmae = text;

                            String gymname = gym_name.getText().toString();
                            UsersList_Admin_main.gymownersgymname = gymname;

                            longclikcInterface.onitemlongclick(pos);

                        }
                    }
                    return true;
                }
            });
        }
    }
}
