package com.example.usernamelogin.Admin.Userslist.gymanditsmembers;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.Admin.Gym.Admin_add_gym;
import com.example.usernamelogin.Member.Reservation.Model_class_Coach_list;
import com.example.usernamelogin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Dialog_for_staffandcoach extends Dialog{
    private Context context;
    Button Seestaff, Seecoach;
    RecyclerView recyclerView;
    DatabaseReference db;
    DatabaseReference dbrunadapter;
    String  key_Gym_Coach1 = null;
    String  key_Gym_Coach3 = null;
    String gymname;
    TextView title;
    givetoroots itemsender;
    public static String[] extradirect;

    public Dialog_for_staffandcoach(@NonNull Context context, givetoroots itemsender) {
        super(context);
        this.context =  context;
        this.itemsender = itemsender;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState != null ? savedInstanceState : new Bundle());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.admin_gym_employee_list, null);
        setContentView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);

        title = view.findViewById(R.id.tvTitle);
        Seestaff = view.findViewById(R.id.Staffbutton);
        Seecoach = view.findViewById(R.id.Coachbutton);
        recyclerView = view.findViewById(R.id.employeedialog);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        gymname = Admin_add_gym.gymownersgymname;

        db = FirebaseDatabase.getInstance().getReference("Users/Gym_Owner");
        initializedb();



        Seestaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaystaff();
                title.setText("Staff Employees");
            }
        });

        Seecoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaycoach();
                title.setText("Coach Employees");
            }
        });

    }

    private void initializedb(){
       DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Users/Gym_Owner");
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
outerLoop:
            for(DataSnapshot underGym_Owner : snapshot.getChildren()){
                key_Gym_Coach1 = underGym_Owner.getKey();
                for(DataSnapshot undergym : underGym_Owner.getChildren()){
                    for(DataSnapshot underGymchild : undergym.getChildren()){
                        key_Gym_Coach3 = underGymchild.getKey();
                        String gymnamefromdb = underGymchild.child("gym_name").getValue(String.class);
                        if(Objects.equals(gymname,gymnamefromdb)){

                            dbrunadapter = FirebaseDatabase.getInstance().getReference("Users/Gym_Owner")
                                    .child(key_Gym_Coach1).child("Gym").child(key_Gym_Coach3);



                            break outerLoop;
                        }
                    }
                }

            }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void displaycoach(){
        ArrayList<Model_class_Coach_list> list = new ArrayList<>();
        Adapter_for_gym_employees adapter = new Adapter_for_gym_employees(context,list, itemsender);
        recyclerView.setAdapter(adapter);

        DatabaseReference dbrunadapter_notes = dbrunadapter.child("Coach");
        dbrunadapter_notes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot underGym_Owner : snapshot.getChildren()) {

                    String key_Gym_ = underGym_Owner.child("username").getValue(String.class);
                    Log.d("TAGadminemp", "coach usernames :" +  key_Gym_);

                    Model_class_Coach_list users = underGym_Owner.getValue(Model_class_Coach_list.class);
                    users.setUsername(key_Gym_);
                    list.clear();
                    list.add(users);

                }

                adapter.notifyDataSetChanged();
                extradirect = new String[]{key_Gym_Coach1, key_Gym_Coach3,"Coach"};
                if(extradirect == null){
                    Log.d("TAGARRAYFIND", "No value inside extradirect ");
                }else{
                    Log.d("TAGARRAYFIND", "has value inside ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void displaystaff(){
        ArrayList<Model_class_Coach_list> list = new ArrayList<>();
        Adapter_for_gym_employees adapter = new Adapter_for_gym_employees(context,list,itemsender);
        recyclerView.setAdapter(adapter);

        DatabaseReference dbrunadapter_nest = dbrunadapter.child("Staff");
        dbrunadapter_nest.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot underGym_Owner : snapshot.getChildren()) {

                    String key_Gym_ = underGym_Owner.child("username").getValue(String.class);
                    Log.d("TAGadminemp", "coach usernames :" +  key_Gym_);

                    Model_class_Coach_list users = underGym_Owner.getValue(Model_class_Coach_list.class);
                    users.setUsername(key_Gym_);
                    list.clear();
                    list.add(users);

                }

                adapter.notifyDataSetChanged();
                extradirect = new String[]{key_Gym_Coach1, key_Gym_Coach3,"Staff"};
                if(extradirect == null){
                    Log.d("TAGARRAYFIND", "No value inside extradirect ");
                }else{
                    Log.d("TAGARRAYFIND", "has value inside ");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
