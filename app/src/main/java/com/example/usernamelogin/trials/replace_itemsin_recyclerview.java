package com.example.usernamelogin.trials;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.Admin.users_all;
import com.example.usernamelogin.NonMemberUser.dialogbox.interface_dialog_list;
import com.example.usernamelogin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class replace_itemsin_recyclerview extends AppCompatActivity implements trial_interface {
    RecyclerView recyclerView;
    Adapter_class_trial adapter;
    ArrayList<Integer> clickedPositions; // ArrayList to store clicked positions
    static int storedPosition = -1; // Default value for stored position

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replace_itemsin_recyclerview);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Reservations").child("Pending_Requests");
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("YourDatabaseNode");
        recyclerView = findViewById(R.id.trialrecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Model_class3> list = new ArrayList<>();
        adapter = new Adapter_class_trial(this, list,this);
        recyclerView.setAdapter(adapter);






        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Model_class3 res_list = dataSnapshot.getValue(Model_class3.class);
                    list.add(res_list);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void setStoredPosition(int position) {
        storedPosition = position;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {

    }
}
