package com.example.usernamelogin.Staff.Memberslist.members_exp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.example.usernamelogin.Staff.Memberslist.members_not_exp.Model_class_get_members_details;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_expired_members#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_expired_members extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_expired_members() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_expired_members.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_expired_members newInstance(String param1, String param2) {
        Fragment_expired_members fragment = new Fragment_expired_members();
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
    Adapter_frag_expired_members adapter;
    ArrayList<Model_class_get_members_details> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expired_members, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_members_expired);

        populatelist();

        return view;
    }
    private void populatelist(){

        adapter = new Adapter_frag_expired_members(getContext(), list, new interface_click_exp_members() {
            @Override
            public void onItemClick(int position) {
                Model_class_get_members_details selected = list.get(position);
                String usernameToDelete = selected.getUsername();

                new AlertDialog.Builder(getContext())
                        .setTitle("Confirmation")
                        .setMessage("Do you want to proceed with removing this member?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Users/Non-members");

                            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                        String username = userSnapshot.child("username").getValue(String.class);

                                        if (username != null && username.equals(usernameToDelete)) {

                                            // Remove the specific children
                                            userSnapshot.getRef().child("membership").removeValue();
                                            userSnapshot.getRef().child("GymName").removeValue();
                                            userSnapshot.getRef().child("positionstored").removeValue();
                                            userSnapshot.getRef().child("Coach_Reservation").removeValue();

                                            // Set membership_status to 0
                                            userSnapshot.getRef().child("membership_status").setValue(0);

                                            Toast.makeText(getContext(), "Member removed successfully.", Toast.LENGTH_SHORT).show();
                                            break; // Stop looping once found
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                            getParentFragmentManager()
                                    .beginTransaction()
                                    .detach(Fragment_expired_members.this)
                                    .attach(Fragment_expired_members.this)
                                    .commit();

                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .show();
            }
        });

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users/Non-members");

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear previous data to avoid duplication

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String gymName = snapshot1.child("GymName").getValue(String.class);

                    if (gymName != null && gymName.equals(Login.key_Gym_)) {
                        DataSnapshot membershipSnapshot = snapshot1.child("membership");

                        if (membershipSnapshot.exists()) {
                            String username = snapshot1.child("username").getValue(String.class);
                            String expirationDate = membershipSnapshot.child("expiration_date").getValue(String.class);
                            String startDate = membershipSnapshot.child("start_date").getValue(String.class);

                            // Check if expirationDate is expired
                            if (expirationDate != null && isDateExpired(expirationDate)) {
                                Model_class_get_members_details res1 =
                                        new Model_class_get_members_details(username, expirationDate, startDate);
                                list.add(res1);
                            }
                        }
                    }
                }

                adapter.notifyDataSetChanged(); // Refresh the RecyclerView
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // handle error
            }
        });


    }
    public static boolean isDateExpired(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM dd yy", Locale.getDefault());
        sdf.setLenient(false);

        try {
            Date inputDate = sdf.parse(dateString);
            Date currentDate = new Date();
            return inputDate.before(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }


}