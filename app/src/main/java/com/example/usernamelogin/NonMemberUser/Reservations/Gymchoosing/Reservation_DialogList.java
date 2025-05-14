package com.example.usernamelogin.NonMemberUser.Reservations.Gymchoosing;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public abstract class Reservation_DialogList extends Dialog implements interface_clicking_gym_res  {

    private ArrayList<Modelclass> list;
    private Adapter_res_gymlist adapter1;
    RecyclerView recyclerView;


    public Reservation_DialogList(Context context){
     super(context);
        list = new ArrayList<>();

        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState != null ? savedInstanceState : new Bundle());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_list_gymlist, null);
        setContentView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);


      recyclerView = view.findViewById(R.id.gymListforgymres);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setUpRecyclerView();


    }

    private void setUpRecyclerView() {

        // Initialize the adapter
        adapter1 = new Adapter_res_gymlist(getContext(), list, (interface_clicking_gym_res) this);
        recyclerView.setAdapter(adapter1);

        DatabaseReference databaseReferenceNon = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child("Gym_Owner");
        Query checkname = databaseReferenceNon;
        Log.d("TAG11", "Minecraft Start! " );
        checkname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot underGym_Owner : snapshot.getChildren()) {
                    String key_Gym_Staff1 = underGym_Owner.getKey();
                    Log.d("TAG11", "1st key: " + key_Gym_Staff1);
                    String username = underGym_Owner.child("gym_owner_username").getValue(String.class);
                    for (DataSnapshot underGym : underGym_Owner.getChildren()) {
                        String key_Gym_Staff2 = underGym.getKey();
                        Log.d("TAG11", "2nd key: " + key_Gym_Staff2);

                         Log.d("TAG11", "the username of the owner: " + username);
                        for (DataSnapshot underGymchild : underGym.getChildren()) {
                            String key_Gym_Staff3 = underGymchild.getKey();

                                Modelclass res_list1 = underGymchild.getValue(Modelclass.class);

                                String gymcntctnumber = underGymchild.child("gym_contact_number").getValue(String.class);

                                res_list1.setOwnerkey(key_Gym_Staff1);
                                res_list1.setGymkey(key_Gym_Staff3);
                                res_list1.setGym_contact_number(gymcntctnumber);
                                list.add(res_list1);


                        }
                        break;
                    }

                }
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });
    }

}
