package com.example.usernamelogin.NonMemberUser.Gym_prop;

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
import com.example.usernamelogin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PendingGymApplications_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PendingGymApplications_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PendingGymApplications_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PendingGymApplications_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PendingGymApplications_fragment newInstance(String param1, String param2) {
        PendingGymApplications_fragment fragment = new PendingGymApplications_fragment();
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


    RecyclerView gymapplicationdisplay;
    String gymnamed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pending_gym_applications_fragment, container, false);
        gymapplicationdisplay = view.findViewById(R.id.gymapplicationlist);


        applicationrec_view();

        return view;
    }
    public void applicationrec_view(){

        DatabaseReference databaseReferenceNon = FirebaseDatabase.getInstance()
                .getReference("Membership_Request");

        Log.d("TAG76", "minecraft start!");

        gymapplicationdisplay.setHasFixedSize(true);
        gymapplicationdisplay.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<Helper_class_gymapplicationdetails> list = new ArrayList<>();
        Adapter_gymapplicationlist adapter = new Adapter_gymapplicationlist(getContext(),list);
        gymapplicationdisplay.setAdapter(adapter);

        databaseReferenceNon.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1: snapshot.getChildren()){

                    gymnamed = snapshot1.getKey().toString();

                    for (DataSnapshot snapshot2: snapshot1.getChildren()){

                        String username = snapshot2.child("username").getValue().toString();
                        Log.d("TAG76", "searched username!"+ username);
                        if ( username != null && username.equals(NonMemberUSER.ProfileContents[0])){

                            Helper_class_gymapplicationdetails userlist = snapshot2.getValue(Helper_class_gymapplicationdetails.class);
                            userlist.setGym_name(gymnamed);
                            list.add(userlist);

                        }
                    }
                }
                adapter.notifyDataSetChanged();


                Log.d("TAG76", "Data fetched successfully!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

}