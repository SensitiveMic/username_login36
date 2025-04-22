package com.example.usernamelogin.Member.Gym_Info;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.NonMemberUser.Gym_prop.Apply_a_gym_fragment;
import com.example.usernamelogin.NonMemberUser.dialogbox.EmployeeAdapter;
import com.example.usernamelogin.NonMemberUser.dialogbox.Mode_class_packagelist;
import com.example.usernamelogin.NonMemberUser.dialogbox.interface_dialog_list;
import com.example.usernamelogin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public abstract class  Dialog_list_forgyminfo extends Dialog implements interface_for_gyminfo_dialog {
    private ArrayList<Mode_class_packagelist> list;
    private Adapter_for_dialogList adapter;

    public Dialog_list_forgyminfo(@NonNull Context context) {
        super(context);
        list = new ArrayList<>(); // Initialize the list
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState != null ? savedInstanceState : new Bundle());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_list, null);
        setContentView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);

        setUpRecyclerView(view);
    }
    private void setUpRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rvList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the adapter
        adapter = new Adapter_for_dialogList(getContext(), list, (interface_for_gyminfo_dialog) this);
        recyclerView.setAdapter(adapter);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Gym_package").child(Member_Gym_info_main.member_gymname);
        Query myRefQuery = myRef.orderByKey();

        myRefQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot underGym_Owner : snapshot.getChildren()) {
                    Mode_class_packagelist res_list2 = underGym_Owner.getValue(Mode_class_packagelist.class);
                    list.add(res_list2);
                }
                adapter.notifyDataSetChanged(); // Notify adapter after populating the list
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });
    }
}
