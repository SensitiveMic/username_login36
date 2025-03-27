package com.example.usernamelogin.Member.Reservation.Current_Coach_Res;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usernamelogin.Coach.Model_class_pendingresdisplay;
import com.example.usernamelogin.Member.Reservation.Coach_Res_Scheduler.SharedViewModel;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Current_Reservations#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Current_Reservations extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Current_Reservations() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Current_Reservations.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Current_Reservations newInstance(String param1, String param2) {
        Fragment_Current_Reservations fragment = new Fragment_Current_Reservations();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    private SharedViewModel viewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    RecyclerView current_res_recyclerv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment__current__reservations, container, false);

        current_res_recyclerv = view.findViewById(R.id.currentres_recyclerviewid);

        currentresfragment();



        return view;
    }
    private void currentresfragment(){
        ArrayList<Modelclass_for_current_member_res_accepted> list = new ArrayList<>();

        current_res_recyclerv.setHasFixedSize(true);
        current_res_recyclerv.setLayoutManager(new LinearLayoutManager(requireContext()));

        DatabaseReference gotomemberscurrentres = FirebaseDatabase.getInstance()
                .getReference("Users").child("Non-members").child(Login.key)
                .child("Coach_Reservation").child("Current_Accepted_Res");

        Adapter_for_current_coach_res_onmember adapter = new Adapter_for_current_coach_res_onmember(requireContext(),list);

        current_res_recyclerv.setAdapter(adapter);
        gotomemberscurrentres.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot gk : snapshot.getChildren()){

                    Modelclass_for_current_member_res_accepted item = gk.getValue(Modelclass_for_current_member_res_accepted.class);
                    int viewType = item.getViewType();
                    Log.d("TAG113", "viewType: " + viewType);
                    list.add(item);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}