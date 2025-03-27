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
    ArrayList<add_gym_recyclerviewAdapter_helper> list;
    private final Admin_Gym_RecyclerViewInterface recyclerViewInterface;

    public Adapter_recyclerview_add_gym(Context context, ArrayList<add_gym_recyclerviewAdapter_helper> list, Admin_Gym_RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.list = list;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public Adapter_recyclerview_add_gym.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recyclerview_add_gym_item,parent,false);
        return new Adapter_recyclerview_add_gym.MyViewHolder(v, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_recyclerview_add_gym.MyViewHolder holder, int position) {
        add_gym_recyclerviewAdapter_helper fromusers1 = list.get(position);
        holder.gym_owner_username1.setText(fromusers1.getGym_owner_username());

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child("Gym_Owner");
        Query checkUser = userRef.orderByChild("gym_owner_username").equalTo(fromusers1.getGym_owner_username());
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot gk : snapshot.getChildren()) {
                    String key1 = gk.getKey();
                    Log.d("TAG002", key1);
                    // Access the "Gym" node
                    DataSnapshot gymSnapshot = gk.child("Gym");
                    // Iterate over the children of the "Gym" node
                    for (DataSnapshot gymChildSnapshot : gymSnapshot.getChildren()) {
                        // Access the "gym_name" under the push ID
                        String gymName = gymChildSnapshot.child("gym_name").getValue(String.class);
                        // Set gym name to the TextView
                        holder.gym_name.setText(gymName);
                        Log.d("TAG001", gymName);
                        // Break the loop as we only need to process one gym name
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "Error fetching gym name: " + error.getMessage());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView gym_owner_username1,gym_name;


        public MyViewHolder(@NonNull View itemView, Admin_Gym_RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            gym_owner_username1 = itemView.findViewById(R.id.dbgymownername);
            gym_name = itemView.findViewById(R.id.dbgymname);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){

                            String text = gym_owner_username1.getText().toString();
                            Admin_add_gym.gymownerkey = text;

                            String gymname = gym_name.getText().toString();
                            Admin_add_gym.gymownersgymname = gymname;


                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
