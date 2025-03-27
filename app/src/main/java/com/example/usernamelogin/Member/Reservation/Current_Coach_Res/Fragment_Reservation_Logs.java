package com.example.usernamelogin.Member.Reservation.Current_Coach_Res;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usernamelogin.Member.Reservation.Model_class_Coach_list;
import com.example.usernamelogin.Member.Reservation.Model_class_coach_reslogsmember;
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
 * Use the {@link Fragment_Reservation_Logs#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Reservation_Logs extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Reservation_Logs() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Reservation_Logs.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Reservation_Logs newInstance(String param1, String param2) {
        Fragment_Reservation_Logs fragment = new Fragment_Reservation_Logs();
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

    RecyclerView recviewoflogs;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__reservation__logs, container, false);

        recviewoflogs = view.findViewById(R.id.memberreslogsrecview);

        recviewoflogs();

        return view;
    }
    private void recviewoflogs(){
        DatabaseReference gotomemberscompletedres = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child("Non-members")
                .child(Login.key)
                .child("Coach_Reservation")
                .child("Completed_res_logs");

        recviewoflogs.setHasFixedSize(true);
        recviewoflogs.setLayoutManager(new LinearLayoutManager(requireContext()));
        ArrayList<Model_class_coach_reslogsmember> list = new ArrayList<>();
        Adapter_coach_res_logs adapter = new Adapter_coach_res_logs(getContext(),list);
        recviewoflogs.setAdapter(adapter);



            gotomemberscompletedres.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1: snapshot.getChildren()){

                        Model_class_coach_reslogsmember userlist = snapshot1.getValue(Model_class_coach_reslogsmember.class);
                        list.add(userlist);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




    }


}