package com.example.usernamelogin.NonMemberUser.Reservations;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.usernamelogin.NonMemberUser.NonMemberUSER;
import com.example.usernamelogin.NonMemberUser.Reservations.Gymchoosing.Reservation_DialogList;
import com.example.usernamelogin.NonMemberUser.Reservations.Gymchoosing.interface_for_recyclerviewAdapter;
import com.example.usernamelogin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_current_reservations_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_current_reservations_Fragment extends Fragment implements interface_for_recyclerviewAdapter, interface_refresh_Reservations {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_current_reservations_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Current_reservations.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_current_reservations_Fragment newInstance(String param1, String param2) {
        fragment_current_reservations_Fragment fragment = new fragment_current_reservations_Fragment();
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


    ImageView add_res, ref_resh;
    RecyclerView recyclerView;
    recyclerviewAdapter myadapter;
    Integer sizeof;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_reservations, container, false);

        add_res = view.findViewById(R.id.add_res);
        ref_resh = view.findViewById(R.id.refresh);
        recyclerView = view.findViewById(R.id.userList);


    refresh_res_list();
        ref_resh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh_res_list();
            }
        });
        add_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_list_gym();
            }
        });

        return view;
    }

    public void refresh_res_list(){

        DatabaseReference dbweww = FirebaseDatabase.getInstance()
                .getReference("Reservations/Pending_Requests");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<recycleviewReservationlist> list = new ArrayList<>();
        myadapter = new recyclerviewAdapter(getContext(),list, (interface_for_recyclerviewAdapter) this,this::deletebuttonclciked);
        recyclerView.setAdapter(myadapter);
        dbweww.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot1:snapshot.getChildren()){
                    for(DataSnapshot dataSnapshot:dataSnapshot1.getChildren()){

                        String username = dataSnapshot.child("user").getValue(String.class);

                        if (username != null && username.equals(NonMemberUSER.ProfileContents[0])) {
                            recycleviewReservationlist res_list = dataSnapshot.getValue(recycleviewReservationlist.class);
                            list.add(res_list);
                        }
                    }
                }
                myadapter.notifyDataSetChanged();
               Reservations reservations = (Reservations) getActivity();
                int notifcounter = myadapter.getItemCount();

                if (notifcounter > 0) {
                    reservations.updateNotificationCount(notifcounter);
                }
                else{
                    reservations.updateNotificationBadge(notifcounter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onItemClick1(int position) {
        dialog_current_user_res();
    }
    public void dialog_current_user_res(){
        dialog_fornonmember_res_pending listDialog = new dialog_fornonmember_res_pending(getContext()) {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
            }
        } ;
        listDialog.show();
    }
    public void dialog_list_gym(){

        Reservation_DialogList listDialog = new Reservation_DialogList(getContext()) {

            @Override
            public void onItemClick1(int position) {
               Intent intent = new Intent(getActivity(), Add_Reservations.class);
                startActivity(intent);

            }

            @Override
            public void onCreate(Bundle savedInstanceState)
            {
                super.onCreate(savedInstanceState);

                // Set up any additional dialog features
            }
        };
        // Show the dialog
        listDialog.show();
    }

    @Override
    public void deletebuttonclciked() {
        refresh_res_list();
    }



}