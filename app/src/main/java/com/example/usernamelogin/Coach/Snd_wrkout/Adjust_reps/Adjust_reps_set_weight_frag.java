package com.example.usernamelogin.Coach.Snd_wrkout.Adjust_reps;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usernamelogin.Coach.Coach_main;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Triple;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Adjust_reps_set_weight_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Adjust_reps_set_weight_frag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Adjust_reps_set_weight_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Adjust_reps_set_weight_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static Adjust_reps_set_weight_frag newInstance(String param1, String param2) {
        Adjust_reps_set_weight_frag fragment = new Adjust_reps_set_weight_frag();
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
    private Cch_Selected_exer_adapter adapter;
    private List<Exercise_selected_helper> selectedExercises = new ArrayList<>();
    private List<ModelClass_forexercises_main> exerciseList;
    private Button saveto_json;
    Bundle bundle_2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adjust_reps_set_weight, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        saveto_json = view.findViewById(R.id.add_to_json);

        bundle_2 = getArguments();
        if (bundle_2 != null) {

            String jsonExercises = bundle_2.getString("selected_exercises", "[]");

            // Log the received data to check correctness
            Log.d("TAG_MAIN_Selected_ex", jsonExercises);

            try {
                JSONArray jsonArray = new JSONArray(jsonExercises);
                Log.d("TAG_MAIN_Selected_ex", "Value check: " + jsonArray.toString() );
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    // Log the JSONObject to ensure it's being parsed correctly
                    Log.d("TAG_MAIN_Selected_ex", "JSONObject at index " + i + ": " + obj.toString());

                    // Check if the keys exist before accessing them
                    if (obj.has("exerciseName") && obj.has("exerciseId")) {
                        String exerciseName = obj.getString("exerciseName");
                        int exerciseId = obj.getInt("exerciseId");

                        // Log the values from JSONObject to ensure correctness
                        Log.d("TAG_MAIN_Selected_ex", "Name: " + exerciseName + ", ID: " + exerciseId);

                        // Add the item to selectedExercises list
                        selectedExercises.add(new Exercise_selected_helper(
                                exerciseName, exerciseId, 0, 0, 0));

                        Log.d("TAG_MAIN_Selected_ex", "Added to selectedExercises: " + selectedExercises.toString());
                    } else {
                        Log.e("TAG_MAIN_Selected_ex", "Missing keys in JSONObject at index " + i);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter = new Cch_Selected_exer_adapter(selectedExercises);
        recyclerView.setAdapter(adapter);
        exerciseList = Exercise_repo_main.getExercises(getContext());
        saveto_json.setOnClickListener(v ->{   showEditTextDialog();  });

        return view;
    }
    private void showEditTextDialog() {
        // Create an EditText programmatically
        EditText editText = new EditText(getContext());
        editText.setHint("Enter workout name");

        // Build the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Type workout name")
                .setMessage("Please enter:")
                .setView(editText) // Set the EditText as the view of the dialog
                .setPositiveButton("done!", (dialog, which) -> {
                    // Handle the input from the EditText

                    String userInput = editText.getText().toString().trim();
                    String workoutName = "Coach sent workout - " + userInput;
                    // clearJsonFile("custom_workout.json");

                    getRecipientName(Coach_main.activeres_member_pushid, new FirebaseUsernameCallback() {
                        @Override
                        public void onUsernameReceived(String username) {

                            Log.d("TAG_MAIN_Selected_ex_1", "Username: " + username);
                            // Use the username here
                            sendtocoachdb(Coach_main.activeres_member_pushid,workoutName,username);
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });

                    Toast.makeText(getContext(), "You entered: " + workoutName, Toast.LENGTH_SHORT).show();

                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss()) // Dismiss the dialog
                .show();
    }

    public interface FirebaseUsernameCallback {
        void onUsernameReceived(String username);
        void onError(String error);
    }
    private void getRecipientName(String member_id, FirebaseUsernameCallback callback) {
        DatabaseReference db1 = FirebaseDatabase.getInstance()
                .getReference("Users/Non-members")
                .child(member_id)
                .child("username");

        db1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String username = snapshot.getValue(String.class);
                    callback.onUsernameReceived(username);
                } else {
                    callback.onError("Username not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.getMessage());
            }
        });
    }

    private void sendtocoachdb(String member_id, String Workout_name, String usernm) {

        DatabaseReference dbw = FirebaseDatabase.getInstance()
                .getReference("Users/Gym_Owner")
                .child(Login.key_Gym_Coach1)
                .child(Login.key_Gym_Coach2)
                .child(Login.key_Gym_Coach3)
                .child("Coach")
                .child(Login.key_Gym_Coach_key)
                .child("Sent_coach_workout")
                .push();  // Generates a unique push ID
        Map<String, Object> pushData = new HashMap<>();
        pushData.put("member_username", usernm);

// Push the initial data (username)
        dbw.setValue(pushData);

// Get the push ID for the generated node
        String pushId = dbw.getKey();
        Log.d("TAG", "Push ID: " + pushId);

        Map<Integer, Triple<List<String>, List<String>, List<String>>> allData = adapter.getAllData();
        DatabaseReference db1 = FirebaseDatabase.getInstance()
                .getReference("Users/Non-members")
                .child(member_id)
                .child("Coach_Reservation")
                .child("Sent_coach_workout")
                .child(Workout_name);

// Loop through all exercises and push them under the same pushId
        for (int position : allData.keySet()) {
            // Get exercise details from exerciseList using the position
            Exercise_selected_helper exercise = selectedExercises.get(position); // This is the exercise object
            String exerciseName = exercise.getExerciseName();
            int exerciseID = exercise.getExercise_ID();

            Log.d("TAG_MAIN_Selected_ex_1", "Exercise id?: " + exerciseID);
            Log.d("TAG_MAIN_Selected_ex_1", "Exercise name: " + exerciseName);

            List<String> weightValues = allData.get(position).getFirst();
            List<String> repsValues = allData.get(position).getSecond();
            List<String> setsValues = allData.get(position).getThird();

            String weight = weightValues.isEmpty() ? "" : weightValues.get(0);
            String reps = repsValues.isEmpty() ? "" : repsValues.get(0);
            String sets = setsValues.isEmpty() ? "" : setsValues.get(0);

            Log.d("TAG_MAIN_Selected_ex_1", "Weight Strings: " + weight);
            Log.d("TAG_MAIN_Selected_ex_1", "Reps Strings: " + reps);
            Log.d("TAG_MAIN_Selected_ex_1", "Sets Strings: " + sets);

            // Structure the data in a map
            Map<String, Object> exerciseData = new HashMap<>();
            exerciseData.put("exerciseName", exerciseName);
            exerciseData.put("exerciseID", exerciseID);
            exerciseData.put("weightValues", weight);
            exerciseData.put("repsValues", reps);
            exerciseData.put("setsValues", sets);

            db1.push().setValue(exerciseData);

            // Push the exercise data under the same pushId
            dbw.child(Workout_name).push().setValue(exerciseData); // Adds each exercise under the same pushId
        }
    }
}