package com.example.usernamelogin.Coach.Snd_wrkout.sent_workouts;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_sent_workouts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_sent_workouts extends Fragment implements click_tosee_interface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_sent_workouts() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_sent_workouts.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_sent_workouts newInstance(String param1, String param2) {
        Fragment_sent_workouts fragment = new Fragment_sent_workouts();
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
    private RecyclerView recyclerView;
    private recyclerview_adapter adapter;
    private List<workout_name_info_modelclass> workoutNameList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sent_workouts, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new recyclerview_adapter(this::onItemClick,workoutNameList);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        DatabaseReference dbw = FirebaseDatabase.getInstance()
                .getReference("Users/Gym_Owner")
                .child(Login.key_Gym_Coach1)
                .child(Login.key_Gym_Coach2)
                .child(Login.key_Gym_Coach3)
                .child("Coach")
                .child(Login.key_Gym_Coach_key)
                .child("Sent_coach_workout");
        dbw.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                workoutNameList.clear();
                for (DataSnapshot workoutGroupSnap : snapshot.getChildren()) {
                    String parentKey = workoutGroupSnap.getKey(); // e.g. -ONFGCR...

                    // Get the member_username
                    String memberUsername = "";
                    if (workoutGroupSnap.child("member_username").exists()) {
                        memberUsername = workoutGroupSnap.child("member_username").getValue(String.class);
                    }

                    // Loop through the workouts (excluding member_username)
                    for (DataSnapshot workoutSnap : workoutGroupSnap.getChildren()) {
                        String key = workoutSnap.getKey();

                        if (!key.equals("member_username")) {
                            workoutNameList.add(new workout_name_info_modelclass(parentKey, key, memberUsername));
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("WorkoutList", "Firebase error: " + error.getMessage());
            }
        });


        return view;
    }

    @Override
    public void onItemClick(int position, String selectedkey,  String workout_name) {
        clicked_workout_exercises_frag fragment = new clicked_workout_exercises_frag();
        Bundle bundle = new Bundle();
        bundle.putString("selectedkey", selectedkey);
        bundle.putString("itsworkoutname", workout_name);
        fragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout3, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }


}