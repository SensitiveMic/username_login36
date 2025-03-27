package com.example.usernamelogin.Admin.Userslist.nonmembers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usernamelogin.Admin.Adapter_recyclerview_Adminusers_all;
import com.example.usernamelogin.Admin.Admin_main;
import com.example.usernamelogin.Admin.Admin_user_nonmember_click;
import com.example.usernamelogin.Admin.RecyclerViewInterface;
import com.example.usernamelogin.Admin.users_all;
import com.example.usernamelogin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link nonmember_list_admin_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nonmember_list_admin_fragment extends Fragment implements RecyclerViewInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public nonmember_list_admin_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nonmember_list_admin_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static nonmember_list_admin_fragment newInstance(String param1, String param2) {
        nonmember_list_admin_fragment fragment = new nonmember_list_admin_fragment();
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

    View view;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_nonmember_list_admin_fragment, container, false);

        recyclerView = view.findViewById(R.id.nonmemberlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setRecyclerView();

        return view;
    }

    private void setRecyclerView(){
        ArrayList<users_all> list = new ArrayList<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("/Users/Non-members");
        Adapter_recyclerview_Adminusers_all myadapter = new Adapter_recyclerview_Adminusers_all(getContext(),list,this::onItemClick);
        recyclerView.setAdapter(myadapter);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                   Integer membershipstat = dataSnapshot.child("membership_status").getValue(Integer.class);
                   if(membershipstat != 0){
                       users_all res_list = dataSnapshot.getValue(users_all.class);
                       list.add(res_list);
                   }

                }
                myadapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        redirectActivitynobackstack(getActivity(), Admin_user_nonmember_click.class);
    }
    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
    public static void redirectActivitynobackstack(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }
}