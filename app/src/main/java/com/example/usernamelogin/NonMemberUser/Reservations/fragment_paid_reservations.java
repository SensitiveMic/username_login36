package com.example.usernamelogin.NonMemberUser.Reservations;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usernamelogin.NonMemberUser.NonMemberUSER;
import com.example.usernamelogin.NonMemberUser.Reservations.Gymchoosing.interface_for_recyclerviewAdapter;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_paid_reservations#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_paid_reservations extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_paid_reservations() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_paid_reservations.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_paid_reservations newInstance(String param1, String param2) {
        fragment_paid_reservations fragment = new fragment_paid_reservations();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    DatabaseReference db;
    RecyclerView recyclerView;
    recyclerviewAdapter_paidreservationsfragment myadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_paid_reservations, container, false);

        recyclerView = view.findViewById(R.id.userList1);

        refresh_res_list();




        return view;
    }
    private void refresh_res_list(){

        db = FirebaseDatabase.getInstance().getReference("Users/Non-members")
                .child(Login.key).child("Non_member_Gym_res");


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<Helper_paidreservationsfragment> list = new ArrayList<>();
        myadapter = new recyclerviewAdapter_paidreservationsfragment(getContext(),list);
        recyclerView.setAdapter(myadapter);

        Log.d("TAG299", "Start DB!" );

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                       String date =  dataSnapshot.child("date").getValue().toString();

                        Log.d("TAG299", "THis is the date " + date );

                            Helper_paidreservationsfragment res_list = dataSnapshot.getValue(Helper_paidreservationsfragment.class);
                            list.add(res_list);

                    }
                    myadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}