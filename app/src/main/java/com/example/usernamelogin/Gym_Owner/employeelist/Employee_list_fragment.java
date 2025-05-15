package com.example.usernamelogin.Gym_Owner.employeelist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Employee_list_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Employee_list_fragment extends Fragment implements  toeditcoachandstaff,interface_Adapter_employee_list {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Employee_list_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Employee_list_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Employee_list_fragment newInstance(String param1, String param2) {
        Employee_list_fragment fragment = new Employee_list_fragment();
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
    @Override
    public void onResume() {
        super.onResume();
        checkUSER(); // fetch data again on tab switch
    }
    Adapter_employee_list_fromgymowner adapter;
    private RecyclerView employeeslist;
    View view;
    private SearchView searchView;
    private ArrayList<Model_class_staffandcoachlist> fullList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_employee_list_fragment, container, false);
        searchView = view.findViewById(R.id.searchViewEmployee);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterList(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        checkUSER();

        Log.d("TAG_EMPLOYEELIST_GYMOWNER", "Baseline 1 ");
        return view;
    }
    private void filterList(String query) {
        ArrayList<Model_class_staffandcoachlist> filteredList = new ArrayList<>();
        for (Model_class_staffandcoachlist item : fullList) {
            if (item.getUsername() != null && item.getUsername().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.updateList12(filteredList);  // Custom method in adapter
    }

    private void checkUSER() {

        employeeslist = view.findViewById(R.id.employeelists);
        employeeslist.setHasFixedSize(true);
        employeeslist.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<Model_class_staffandcoachlist> list = new ArrayList<>();
        fullList = new ArrayList<>();
        FirebaseDatabase databaseLogin = FirebaseDatabase.getInstance();
        DatabaseReference myRefLogin = databaseLogin.getReference("Users/Gym_Owner")
                .child(Login.key_GymOwner)
                .child("Gym");


        adapter = new Adapter_employee_list_fromgymowner(getContext(),list, (toeditcoachandstaff) this, (interface_Adapter_employee_list) this);

        employeeslist.setAdapter(adapter);

        myRefLogin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    fullList.clear();

                    for (DataSnapshot gk : snapshot.getChildren()) {
                        String gymId = gk.getKey();
                        employeelists_main.Gym_id = gymId;

                        String gymName = gk.child("gym_name").getValue(String.class);
                        Log.d("TAGGYMCOACH", "GYM ID : " + gymId);

                        for (DataSnapshot child : gk.getChildren()) {
                            String section = child.getKey(); // Could be "Coach" or "Staff"
                            Log.d("TAGGYMCOACH", "next : " + section);

                            if ("Coach".equals(section)) {
                                for (DataSnapshot coachSnap : child.getChildren()) {
                                    Model_class_staffandcoachlist reslist = coachSnap.getValue(Model_class_staffandcoachlist.class);

                                    reslist.setRole("Coach");
                                    Log.d("CHECK_ROLE", "Adding Coach: " + reslist.getUsername() + " | Role: " + reslist.getRole());
                                    reslist.setGym_name(gymName);
                                    list.add(reslist);
                                    fullList.add(reslist);
                                }

                            } else if ("Staff".equals(section)) {
                                for (DataSnapshot staffSnap : child.getChildren()) {
                                    Model_class_staffandcoachlist reslist = staffSnap.getValue(Model_class_staffandcoachlist.class);

                                    reslist.setRole("Staff");
                                    Log.d("CHECK_ROLE", "Adding Staff: " + reslist.getUsername() + " | Role: " + reslist.getRole());
                                    reslist.setGym_name(gymName);
                                    list.add(reslist);
                                    fullList.add(reslist);
                                }
                            }
                        }
                    }

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    @Override
    public void onItemLongClick(int position) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Archive Item?")
                .setMessage("Are you sure you want to archive the account?")
                .setPositiveButton("Archive", (dialog, which) -> {
                    remove_selected_employee();

                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void onitmeclick(int position) {
        Intent intent = new Intent(getContext(), employye_update_profilee.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }
    private void remove_selected_employee(){
        DatabaseReference myRefLogin = FirebaseDatabase.getInstance().getReference("Users/Gym_Owner")
                .child(Login.key_GymOwner)
                .child("Gym");
        myRefLogin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot gk : snapshot.getChildren()) {
                    String gym_id = gk.getKey();
                    String gym_name;
                    if(gk.child("gym_name").exists()){
                        gym_name =gk.child("gym_name").getValue().toString();
                    } else {
                        gym_name = null;
                    }
                    for(DataSnapshot childrenofgk :gk.child("Coach").getChildren()) {
                        String userkey = childrenofgk.getKey();
                        String usernam = childrenofgk.child("username").getValue(String.class);
                        Log.d("TAGREMOVALOFEMP", "(Coach)username in db: "+ usernam);
                        if (usernam!= null && usernam.equals(employeelists_main.employeeclicked)) {
                            DatabaseReference oldref = childrenofgk.getRef();
                            Object userData = childrenofgk.getValue();
                            DatabaseReference newRef = FirebaseDatabase.getInstance()
                                    .getReference("ArchivedEmployees")
                                    .child(Login.key_GymOwner)
                                    .child("Gym")
                                    .child(gym_id)
                                    .child("Coach")
                                    .child(userkey);

                            DatabaseReference gyumname = FirebaseDatabase.getInstance()
                                    .getReference("ArchivedEmployees")
                                    .child(Login.key_GymOwner)
                                    .child("Gym")
                                    .child(gym_id);


                            newRef.setValue(userData).addOnCompleteListener(task ->{
                                if (task.isSuccessful()) {
                                    // Remove from old location only if the write was successful
                                    if (gym_name != null) {
                                        Map<String, Object> gymNameMap = new HashMap<>();
                                        gymNameMap.put("gym_name", gym_name);
                                        gyumname.updateChildren(gymNameMap);
                                    }
                                    oldref.removeValue();
                                    Log.d("TAGREMOVALOFEMP", "Moved and deleted: " + userkey);
                                } else {
                                    Log.e("TAGREMOVALOFEMP", "Failed to move data: " + task.getException());
                                }
                            });

                        }


                    }
                    for(DataSnapshot childrenofgk2 :gk.child("Staff").getChildren()){
                        String userkey2 = childrenofgk2.getKey();
                        String usernam12 = childrenofgk2.child("username").getValue(String.class);
                        Log.d("TAGREMOVALOFEMP", "(Staff)username in db: "+ usernam12);
                        if (usernam12!= null && usernam12.equals(employeelists_main.employeeclicked)) {
                            DatabaseReference oldref2 = childrenofgk2.getRef();
                            Object userData2 = childrenofgk2.getValue();
                            DatabaseReference newRef2 = FirebaseDatabase.getInstance()
                                    .getReference("ArchivedEmployees")
                                    .child(Login.key_GymOwner)
                                    .child("Gym")
                                    .child(gym_id)
                                    .child("Staff")
                                    .child(userkey2);
                            DatabaseReference gyumname = FirebaseDatabase.getInstance()
                                    .getReference("ArchivedEmployees")
                                    .child(Login.key_GymOwner)
                                    .child("Gym")
                                    .child(gym_id);

                            newRef2.setValue(userData2).addOnCompleteListener(task ->{
                                if (task.isSuccessful()) {
                                    // Remove from old location only if the write was successful
                                    if (gym_name != null) {
                                        Map<String, Object> gymNameMap = new HashMap<>();
                                        gymNameMap.put("gym_name", gym_name);
                                        gyumname.updateChildren(gymNameMap);
                                    }
                                    oldref2.removeValue();
                                    Log.d("TAGREMOVALOFEMP", "Moved and deleted: " + userkey2);
                                } else {
                                    Log.e("TAGREMOVALOFEMP", "Failed to move data: " + task.getException());
                                }
                            });
                        }

                    }

                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.framelayout2123, new Archived_employees_frag())
                            .addToBackStack(null)
                            .commit();

                    TabLayout tabLayout = requireActivity().findViewById(R.id.tablayout223);
                    if (tabLayout != null) {
                        TabLayout.Tab tabToSelect = tabLayout.getTabAt(1); // 1 = Archived tab
                        if (tabToSelect != null) {
                            tabToSelect.select();
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}