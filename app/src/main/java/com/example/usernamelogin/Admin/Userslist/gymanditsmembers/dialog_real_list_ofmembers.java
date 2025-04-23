package com.example.usernamelogin.Admin.Userslist.gymanditsmembers;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.Admin.Gym.Admin_add_gym;
import com.example.usernamelogin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public abstract class dialog_real_list_ofmembers extends Dialog implements itemclickinterface {
    private Context context;
    private ArrayList<helper_class_for_memberslist> list;
    private Adapter_for_memberslistreal adapter;

    public dialog_real_list_ofmembers(@NonNull Context context, View.OnClickListener onClickListener) {
        super(context);
        this.context = context;
        list = new ArrayList<>();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState != null ? savedInstanceState : new Bundle());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.admin_gymlist_member_list_dialog, null);
        setContentView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);

        setUpRecyclerView(view);


    }
    private void setUpRecyclerView(View view) {
        adapter = new Adapter_for_memberslistreal(context, list,this );
        RecyclerView recyclerviewformember = view.findViewById(R.id.memberlistIDondialog);
        recyclerviewformember.setAdapter(adapter);
        recyclerviewformember.setHasFixedSize(true);
        recyclerviewformember.setLayoutManager(new LinearLayoutManager(context));

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users/Non-members");
        Query findgymname = myRef.orderByChild("GymName").equalTo(Admin_add_gym.gymownersgymname);
        Log.d("TAGadminmem", "setUpRecyclerView: "+ Admin_add_gym.gymownersgymname);

        findgymname.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot underGym_Owner : snapshot.getChildren()) {
                        String gymanemcheck = underGym_Owner.child("GymName").getValue(String.class);
                        String usernameofthemember = underGym_Owner.child("username").getValue(String.class);
                        Log.d("TAGadminmem", "name of member gym: "+ gymanemcheck);
                        Log.d("TAGadminmem", "membername from inside the undergymowner: "+ usernameofthemember);
                        if(Objects.equals(gymanemcheck, Admin_add_gym.gymownersgymname)) {
                            Log.d("TAGadminmem", "Addingto list Started ");
                            helper_class_for_memberslist res_list2 = underGym_Owner.getValue(helper_class_for_memberslist.class);
                            //res_list2.setUsername(usernameofthemember);
                            Log.d("TAGadminmem", "Added to list: " + res_list2);
                            list.add(res_list2);
                        }

                    }
                    adapter.notifyDataSetChanged(); // Notify adapter after populating the list
                }
                else {
                    Log.e("TAGadminmem", "no snapshot exists! ");
                    Toast.makeText(context, "there is no members", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });
    }



}
