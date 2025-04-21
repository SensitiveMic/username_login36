package com.example.usernamelogin.Staff.Memberslist.members_not_exp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
 * Use the {@link Fragment_members_list#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_members_list extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_members_list() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_members_list.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_members_list newInstance(String param1, String param2) {
        Fragment_members_list fragment = new Fragment_members_list();
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
    Adapter_frag_members_list adapter;
    ArrayList<Model_class_get_members_details> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_members_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_members);

      populatememberslist();

        return view;
    }

    private void populatememberslist(){
        adapter = new Adapter_frag_members_list(getContext(),list);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users/Non-members");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    String gymName = snapshot1.child("GymName").getValue(String.class);
                    if (gymName != null && gymName.equals(Login.key_Gym_)){
                        DataSnapshot membershipSnapshot = snapshot1.child("membership");

                        if (membershipSnapshot.exists()) {
                            String username = snapshot1.child("username").getValue(String.class);
                            String expirationDate = snapshot1.child("membership").child("expiration_date").getValue(String.class);
                            String startDate = snapshot1.child("membership").child("start_date").getValue(String.class);

                            Model_class_get_members_details res1 =
                                    new Model_class_get_members_details(username, expirationDate, startDate);

                            list.add(res1);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

}