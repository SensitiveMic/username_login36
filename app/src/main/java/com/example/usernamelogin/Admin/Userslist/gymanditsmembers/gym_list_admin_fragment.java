package com.example.usernamelogin.Admin.Userslist.gymanditsmembers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.usernamelogin.Admin.Gym.Adapter_recyclerview_add_gym;
import com.example.usernamelogin.Admin.Gym.Admin_add_gym_Field_Req;
import com.example.usernamelogin.Admin.Gym.add_gym_recyclerviewAdapter_helper;
import com.example.usernamelogin.Admin.RecyclerViewInterface;
import com.example.usernamelogin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link gym_list_admin_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class gym_list_admin_fragment extends Fragment implements RecyclerViewInterface {
    /*implements Admin_Gym_RecyclerViewInterface */
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public gym_list_admin_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment gym_list_admin_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static gym_list_admin_fragment newInstance(String param1, String param2) {
        gym_list_admin_fragment fragment = new gym_list_admin_fragment();
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

    ImageView add_gym_circle;
    private View view;
    DatabaseReference db ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_gym_list_admin_fragment, container, false);

        add_gym_circle =view.findViewById(R.id.add_gym_circle);

        add_gym_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               redirectActivity(getActivity(), Admin_add_gym_Field_Req.class);
            }
        });

        refresh_list_gym();

        return view;
    }
    private void refresh_list_gym(){

        RecyclerView recyclerView = view.findViewById(R.id.gymlistrecycler);
        Adapter_recyclerview_add_gym myadapter1;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<add_gym_recyclerviewAdapter_helper> list = new ArrayList<>();
        db = FirebaseDatabase.getInstance().getReference("/Users/Gym_Owner");

        myadapter1 = new Adapter_recyclerview_add_gym(getContext(),list,this::onItemClick);
        recyclerView.setAdapter(myadapter1);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                    add_gym_recyclerviewAdapter_helper res_list1 = dataSnapshot.getValue(add_gym_recyclerviewAdapter_helper.class);
                    list.add(res_list1);
                }
                myadapter1.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }



    @Override
    public void onItemClick(int position) {
        Dialog_for_members listDialog = new Dialog_for_members(getActivity()) {


            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
            }
        };
        listDialog.show();

      //  redirectActivity(getActivity(), Admin_Update_Gym_Info.class);
    }



}