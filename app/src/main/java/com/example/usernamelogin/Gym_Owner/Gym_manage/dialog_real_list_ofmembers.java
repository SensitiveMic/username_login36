package com.example.usernamelogin.Gym_Owner.Gym_manage;

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

import com.example.usernamelogin.Admin.Userslist.gymanditsmembers.helper_class_for_memberslist;
import com.example.usernamelogin.R;
import com.example.usernamelogin.Staff.Memberslist.members_not_exp.Model_class_get_members_details;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public abstract class dialog_real_list_ofmembers extends Dialog {
    private Context context;
    private ArrayList<Model_class_get_members_details> list;
    private Adapter_for_memberslistreal adapter;

    public dialog_real_list_ofmembers(@NonNull Context context) {
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
        adapter = new Adapter_for_memberslistreal(context, list );
        RecyclerView recyclerviewformember = view.findViewById(R.id.memberlistIDondialog);
        recyclerviewformember.setAdapter(adapter);
        recyclerviewformember.setHasFixedSize(true);
        recyclerviewformember.setLayoutManager(new LinearLayoutManager(context));

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users/Non-members");
        Query findgymname = myRef.orderByChild("GymName").equalTo(Gym_Owner_gymmanage_main.gym_Name);
        Log.d("TAGadminmem", "setUpRecyclerView: "+ Gym_Owner_gymmanage_main.gym_Name);

        findgymname.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot underGym_Owner : snapshot.getChildren()) {
                        String gymanemcheck = underGym_Owner.child("GymName").getValue(String.class);
                        String usernameofthemember = underGym_Owner.child("username").getValue(String.class);
                        Log.d("TAGadminmem", "name of member gym: "+ gymanemcheck);
                        Log.d("TAGadminmem", "membername from inside the undergymowner: "+ usernameofthemember);
                        if(Objects.equals(gymanemcheck, Gym_Owner_gymmanage_main.gym_Name)) {
                            Log.d("TAGadminmem", "Addingto list Started ");

                            DataSnapshot membershipSnapshot = underGym_Owner.child("membership");

                            if (membershipSnapshot.exists()) {
                                String username = underGym_Owner.child("username").getValue(String.class);
                                String expirationDate = membershipSnapshot.child("expiration_date").getValue(String.class);
                                String startDate = membershipSnapshot.child("start_date").getValue(String.class);
                                String packagename_wew = membershipSnapshot.child("package_name").getValue(String.class);

                                Model_class_get_members_details res1 =
                                        new Model_class_get_members_details(username, expirationDate, startDate,packagename_wew);
                                res1.setRemainind_days(remainingdays(expirationDate));

                                Log.d("TAGadminmem", "Added to list: " + res1);
                                list.add(res1);
                            }

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

    private String remainingdays( String exp_date){
        String remaining_Days = "";
        SimpleDateFormat sdf = new SimpleDateFormat("MM dd yy");

        try {
            Date start = new Date();
            Date expiration = sdf.parse(exp_date);

            long diffInMillis = expiration.getTime() - start.getTime();
            long daysLeft = TimeUnit.MILLISECONDS.toDays(diffInMillis);

            remaining_Days = String.valueOf(daysLeft);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return remaining_Days;
    }

}
