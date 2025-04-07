package com.example.usernamelogin.Coach.Snd_wrkout.sent_workouts;

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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link clicked_workout_exercises_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class clicked_workout_exercises_frag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String selectedKey;
    private String theworkoutname;

    public clicked_workout_exercises_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment clicked_workout_exercises_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static clicked_workout_exercises_frag newInstance(String param1, String param2) {
        clicked_workout_exercises_frag fragment = new clicked_workout_exercises_frag();
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
             selectedKey = getArguments().getString("selectedkey");
            theworkoutname = getArguments().getString("itsworkoutname");
            Log.d("TAG", "Received key: " + selectedKey);
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private RecyclerView recyclerView;
    private recyclerview_slected_exer_adapter adapter;
    private List<SentWorkoutModel> exerciseList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clicked_workout_exercises_frag, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        exerciseList = new ArrayList<>();
        adapter = new recyclerview_slected_exer_adapter(exerciseList);
        recyclerView.setAdapter(adapter);

        getWorkoutData(selectedKey,theworkoutname );

        return view;
    }

    private void getWorkoutData(String workoutPushId, String workout_name){
        DatabaseReference db = FirebaseDatabase.getInstance()
                .getReference("Users/Gym_Owner")
                .child(Login.key_Gym_Coach1)
                .child(Login.key_Gym_Coach2)
                .child(Login.key_Gym_Coach3)
                .child("Coach")
                .child(Login.key_Gym_Coach_key)
                .child("Sent_coach_workout")
                .child(workoutPushId)
                .child(workout_name);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exerciseList.clear();
                for (DataSnapshot exerciseSnapshot : snapshot.getChildren()) {
                    SentWorkoutModel exercise = exerciseSnapshot.getValue(SentWorkoutModel.class);
                    exerciseList.add(exercise);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "Failed to load workout data.", error.toException());
            }
        });
    }
}