package com.example.usernamelogin.Coach;

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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_current_pending_coach_res_coach_main#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_current_pending_coach_res_coach_main extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_current_pending_coach_res_coach_main() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Current_coach_res_coach_main.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_current_pending_coach_res_coach_main newInstance(String param1, String param2) {
        Fragment_current_pending_coach_res_coach_main fragment = new Fragment_current_pending_coach_res_coach_main();
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

    RecyclerView pending_reservations;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_current_coach_res_coach_main, container, false);

        pending_reservations = view.findViewById(R.id.pendingres_recyclerviewID);

        pendingresRecyclerVIew();

        return view;
    }
    public void pendingresRecyclerVIew(){

            DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users/Gym_Owner")
                    .child(Login.key_Gym_Coach1).child(Login.key_Gym_Coach2).child(Login.key_Gym_Coach3)
                    .child("Coach").child(Login.key_Gym_Coach_key).child("pending_res");

            pending_reservations.setHasFixedSize(true);
            pending_reservations.setLayoutManager(new LinearLayoutManager(requireContext()));

            ArrayList<Model_class_pendingresdisplay> list = new ArrayList<>();
            recyclerViewAdapter_coach_main_pending_req adapter = new recyclerViewAdapter_coach_main_pending_req(requireContext(),list, (interface_click_pendingresfrm_gym_members) requireActivity());
            pending_reservations.setAdapter(adapter);
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot gk : snapshot.getChildren()){

                        Model_class_pendingresdisplay item = gk.getValue(Model_class_pendingresdisplay.class);

                        int viewType = item.getViewType();
                        Log.d("TAG111", "viewType: " + viewType);
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
