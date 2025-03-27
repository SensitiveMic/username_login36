package com.example.usernamelogin.Staff;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * Use the {@link Fragment_accepted_req#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_accepted_req extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_accepted_req() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_accepted_req.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_accepted_req newInstance(String param1, String param2) {
        Fragment_accepted_req fragment = new Fragment_accepted_req();
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
    RecyclerView recyclerView;
    Adapter_staff_reservation_accepted myadapter2;
    ArrayList<newHelper_reservation_staff> combinedList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accepted_req, container, false);
        recyclerView = view.findViewById(R.id.staff_res_list);

        popuLatelist();

        return view;
    }
    private void popuLatelist(){


        myadapter2 = new Adapter_staff_reservation_accepted(combinedList,getContext());

       DatabaseReference db = FirebaseDatabase.getInstance().getReference("Reservations/Accepted")
               .child(Login.key_Gym_);
       Query wew = db;

        recyclerView.setAdapter(myadapter2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        wew.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                combinedList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    newHelper_reservation_staff item = dataSnapshot.getValue(newHelper_reservation_staff.class);
                    combinedList.add(item);
                }
                myadapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "Database error: " + error.getMessage());
            }
        });


    }
}