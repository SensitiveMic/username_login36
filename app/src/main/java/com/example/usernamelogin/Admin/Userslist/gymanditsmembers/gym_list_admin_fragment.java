package com.example.usernamelogin.Admin.Userslist.gymanditsmembers;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.usernamelogin.Admin.Gym.Adapter_recyclerview_add_gym;
import com.example.usernamelogin.Admin.Gym.Admin_add_gym;
import com.example.usernamelogin.Admin.Gym.Admin_add_gym_Field_Req;
import com.example.usernamelogin.Admin.Gym.Admin_gym_longclikc_interface;
import com.example.usernamelogin.Admin.Gym.add_gym_recyclerviewAdapter_helper;
import com.example.usernamelogin.Admin.RecyclerViewInterface;
import com.example.usernamelogin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link gym_list_admin_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class gym_list_admin_fragment extends Fragment implements RecyclerViewInterface, Admin_gym_longclikc_interface {
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

    private View view;
    DatabaseReference db ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_gym_list_admin_fragment, container, false);

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

        myadapter1 = new Adapter_recyclerview_add_gym(getContext(),list,this::onItemClick,this::onitemlongclick);
        recyclerView.setAdapter(myadapter1);

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String gymowner_key = dataSnapshot.getKey().toString();
                    Log.d("TAGECHECKADMINGYMS", "onDataChange: " +dataSnapshot.getKey().toString());
                    String owner_first = "";
                    String owner_last = "";
                    String fullname = "";
                    String ownerusername = "";
                    ownerusername = dataSnapshot.child("gym_owner_username").getValue().toString();
                    if(dataSnapshot.child("gym_owner_firstname").exists()){
                        owner_first = dataSnapshot.child("gym_owner_firstname").getValue().toString();
                    }
                    if(dataSnapshot.child("gym_owner_lastname").exists()){
                        owner_last  = dataSnapshot.child("gym_owner_lastname").getValue().toString();
                    }

                    if(owner_first != null || owner_last != null){
                        fullname = owner_first+ " " + owner_last;
                    }

                    for(DataSnapshot snapshot1:dataSnapshot.child("Gym").getChildren()){
                        add_gym_recyclerviewAdapter_helper res_list1 = snapshot1.getValue(add_gym_recyclerviewAdapter_helper.class);
                        String gymkey = snapshot1.getKey().toString();
                        res_list1.setGym_owner_key(gymowner_key);
                        res_list1.setGymkey(gymkey);
                        res_list1.setFullname(fullname);
                        res_list1.setGym_owner_username(ownerusername);
                        list.add(res_list1);

                    }

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

    }

    @Override
    public void onitemlongclick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Archive Gym")
                .setMessage("Are you sure you want to archive this gym?")
                .setPositiveButton("Archive", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Archive logic here
                        archivegym_proceed();
                        Toast.makeText(requireContext(), "Gym archived successfully.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // just close the dialog
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
    private void archivegym_proceed(){
        DatabaseReference removeREF = FirebaseDatabase.getInstance()
                .getReference("Users/Gym_Owner")
                .child(UsersList_Admin_main.gym_owner_KEY)
                .child("Gym")
                .child(UsersList_Admin_main.gym_KEY);

        DatabaseReference Archiveref = FirebaseDatabase.getInstance()
                .getReference("Archived_Gym")
                .child(UsersList_Admin_main.gym_owner_KEY)
                .child("Gym")
                .child(UsersList_Admin_main.gym_KEY);
        DatabaseReference Archiveref1 = FirebaseDatabase.getInstance()
                .getReference("Archived_Gym")
                .child(UsersList_Admin_main.gym_owner_KEY);

        DatabaseReference getGymDetails = FirebaseDatabase.getInstance()
                .getReference("Users/Gym_Owner")
                .child(UsersList_Admin_main.gym_owner_KEY);

        getGymDetails.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String firstname = snapshot.child("gym_owner_firstname").getValue(String.class);
                String lastname = snapshot.child("gym_owner_lastname").getValue(String.class);
                String username = snapshot.child("gym_owner_username").getValue(String.class);

                // Get the specific gym data under Gym > gym_KEY
                DataSnapshot gymSnapshot = snapshot.child("Gym").child(UsersList_Admin_main.gym_KEY);

                // Create a map to hold all data to archive
                Map<String, Object> archivedData = new HashMap<>();
                Map<String, Object> archivedData1 = new HashMap<>();
                // Add owner info
                archivedData1.put("gym_owner_firstname", firstname);
                archivedData1.put("gym_owner_lastname", lastname);
                archivedData1.put("gym_owner_username", username);

                // Add gym info (all children under that gym key)
                for (DataSnapshot child : gymSnapshot.getChildren()) {
                    archivedData.put(child.getKey(), child.getValue());
                }

                // Now store to Archived_Gym
                Archiveref.updateChildren(archivedData).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Optionally delete original gym data
                        removeREF.removeValue();
                        Archiveref1.updateChildren(archivedData1);
                        Toast.makeText(getContext(), "Gym archived successfully.", Toast.LENGTH_SHORT).show();
                        refresh_list_gym();
                    } else {
                        Toast.makeText(getContext(), "Failed to archive gym.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", error.getMessage());
            }
        });

    }
}