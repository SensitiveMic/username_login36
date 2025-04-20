package com.example.usernamelogin.workout_program.personalizing.Exercise_category.selected_exercises;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.R;
import com.example.usernamelogin.workout_program.personalizing.Personalizing_main;
import com.example.usernamelogin.workout_program.to_edit_reps_weight.ExerciseRepository;
import com.example.usernamelogin.workout_program.to_edit_reps_weight.Modelclass_forexercises;
import com.example.usernamelogin.workout_program.workouts.User_workouts;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kotlin.Triple;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Selected_Exercise_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Selected_Exercise_frag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Selected_Exercise_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Selected_Exercise_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static Selected_Exercise_frag newInstance(String param1, String param2) {
        Selected_Exercise_frag fragment = new Selected_Exercise_frag();
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
    private ExerciseAdapter adapter;
    private List<Exercise_selected> selectedExercises = new ArrayList<>();
    private Button saveto_json;
    private List<Modelclass_forexercises> exerciseList;
    String workoutname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_selected__exercise_frag_wrkt_prgrm, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        saveto_json = view.findViewById(R.id.add_to_json);

        // Get passed JSON data
        Bundle bundle = getArguments();
        if (bundle != null) {

            String jsonExercises = bundle.getString("selected_exercises", "[]");

            // Log the received data to check correctness
            Log.d("TAGSelectedExercisesReceived", jsonExercises);

            try {
                JSONArray jsonArray = new JSONArray(jsonExercises);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String exercise_int = obj.getString("exerciseIntensity");
                    Log.d("TAGSelectedExercisesReceived","Exercise_intensity: " + exercise_int);

                    int[] minmax_reps_sets_final = minmaxreps_sets(exercise_int);
                    int def_reps = 0;
                    int def_sets = 0;
                    def_reps = getmidval(minmax_reps_sets_final[0],minmax_reps_sets_final[1]);
                    def_sets = getmidval(minmax_reps_sets_final[2],minmax_reps_sets_final[3]);

                    Log.d("TAGSelectedExercisesReceived", String.valueOf(def_reps));
                    Log.d("TAGSelectedExercisesReceived", String.valueOf(def_sets));

                    int exercise_recommended_weight = 0;
                    int user_entered_weight = Personalizing_main.userWeight;
                    Log.d("TAGSelectedExercisesReceived","entered weight: "+ user_entered_weight);
                    Boolean exercise_bw_beginner = false;
                    exercise_bw_beginner = obj.getBoolean("beginner_bw_indicator");
                    if (exercise_bw_beginner){
                        exercise_recommended_weight = user_entered_weight;
                        Log.d("TAGSelectedExercisesReceived","For Beginners?: "+exercise_bw_beginner );
                    }else{
                        Double exercise_bw = obj.getDouble("recognized_recom_weight");
                        Log.d("TAGSelectedExercisesReceived","Recognized_recomm_weight: "+ exercise_bw);
                        exercise_recommended_weight = (int) ((exercise_bw * user_entered_weight));
                    }
                    Log.d("TAGSelectedExercisesReceived","Final Weight: "+ exercise_recommended_weight);

                    // Ensure JSON contains expected fields, use default values if missing
                    int sets = obj.has("sets") ? obj.getInt("sets") : def_sets; // Default to 3 sets
                    int reps = obj.has("reps") ? obj.getInt("reps") : def_reps; // Default to 10 reps
                    int weight = obj.has("weight") ? obj.getInt("weight") : exercise_recommended_weight; // Default weight

                    selectedExercises.add(new Exercise_selected(
                            obj.getString("exerciseName"),
                            sets,
                            reps,
                            weight
                            ,minmax_reps_sets_final[0]
                            ,minmax_reps_sets_final[1]
                            ,minmax_reps_sets_final[2]
                            ,minmax_reps_sets_final[3]
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Set up adapter with selected exercises
        adapter = new ExerciseAdapter(selectedExercises);
        recyclerView.setAdapter(adapter);
        exerciseList = ExerciseRepository.getExercises(getContext());

        saveto_json.setOnClickListener(v ->{  showEditTextDialog();   });


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

                    workoutname = editText.getText().toString().trim();
                    // clearJsonFile("custom_workout.json");
                    inputtojson();
                    Toast.makeText(getContext(), "You entered: " + workoutname, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getActivity(), User_workouts.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss()) // Dismiss the dialog
                .show();
    }
    private void inputtojson() {


        // Retrieve exercise data
        Map<Integer, Triple<List<String>, List<String>, List<String>>> allData = adapter.getAllData();
        JSONObject root = new JSONObject();
        JSONArray workoutArray = new JSONArray();
        Log.d("TAG_MainACt_save_tojson", "initialjsoncheck: TRUE " + allData );

        //custom_workout.json directory
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        if (!directory.exists()) {
            Log.e("TAG_MainACt_save_tojson", "yep directory doesnt exist");
            boolean dirCreated = directory.mkdirs();
            if (!dirCreated) {
                Log.e("TAG_MainACt_save_tojson", "Failed to create Documents directory!");
                return;
            }
        }


            try {
                String jsonString = loadJsonFromSAF();
                Log.d("TAG_MainACt_save_tojson", "OLD JSON: " + jsonString);
                root = new JSONObject(jsonString);  // Parse the current JSON string into a JSONObject
                workoutArray = root.optJSONArray(workoutname);  // Get the existing workout array
                if (workoutArray == null) {
                    workoutArray = new JSONArray();  // Create a new workout array if it doesn't exist
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        for (int position : allData.keySet()) {
            Modelclass_forexercises exercise_pos = exerciseList.get(position);
            String exerciseName = exercise_pos.getExercise_name();

            List<String> weightValues = allData.get(position).getFirst();
            List<String> repsValues = allData.get(position).getSecond();
            List<String> setsValues = allData.get(position).getThird();

            Log.d("TAG_MainACt_save_tojson", "List Strings: " + weightValues);
            Log.d("TAG_MainACt_save_tojson", "List Strings: " + repsValues);
            Log.d("TAG_MainACt_save_tojson", "List Strings: " + setsValues);

            try {
                int add_sets = (!setsValues.isEmpty()) ? Integer.parseInt(setsValues.get(0)) : 0;
                Log.d("TAG_MainACt_save_tojson", "Amount of Sets: " + add_sets + " Position: " + position);

                String def_rep = (!repsValues.isEmpty()) ? repsValues.get(0) : "0";
                String def_weight = (!weightValues.isEmpty()) ? weightValues.get(0) : "0";

                JSONObject exercisebox = new JSONObject();  // New exercise object
                JSONArray setsArray = new JSONArray();      // New sets array
                exercisebox.put("Exercise_name", exerciseName);

                for (int i = 0; i < add_sets; i++) {
                    JSONObject addedsets = new JSONObject();

                    // Safe retrieval of reps and weight
                    String repValue = (i < repsValues.size()) ? repsValues.get(i) : def_rep;
                    String weightValue = (i < weightValues.size()) ? weightValues.get(i) : def_weight;
                    String setNum = String.valueOf(i + 1);

                    addedsets.put("Reps", repValue);
                    addedsets.put("Weight", weightValue);
                    addedsets.put("Set", setNum);
                    setsArray.put(addedsets); // Append the set to the sets array

                    Log.d("TAG_MainACt_save_tojson", "Set added: " + addedsets.toString());
                }

                // Add the sets array to the exercise
                exercisebox.put("Sets", setsArray);
                exercisebox.put("Workout_log", new JSONArray());

                // Append the exercise to the workout array
                workoutArray.put(exercisebox);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                Log.e("TAG_MainACt_save_tojson", "Error parsing setsValues: " + setsValues);
            }
        }

        // Save the updated JSON
        try {
            // Append the workout array to the root object
            root.put(workoutname, workoutArray);  // Use your specific workoutname

            // Convert the JSON object to string
            String jsonString = root.toString();

            // Save to internal storage
            saveJsonToSAF(jsonString);

            Log.d("TAG9", "Updated JSON: " + jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private boolean isWorkoutdatajsoninside(){
        File Directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File jsonfile = new File(Directory, "custom_workout.json");

        return jsonfile.exists();
    }
    private String loadJsonFromSAF() {
        SharedPreferences prefs = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String folderUriString = prefs.getString("directory_uri", null);

        if (folderUriString == null) {
            Log.e("SAF", "No directory URI found in SharedPreferences.");
            return null;
        }

        Uri folderUri = Uri.parse(folderUriString);

        // Grant persistable permission (usually done right after folder selection, so you can omit this here if already persisted)
        requireContext().getContentResolver().takePersistableUriPermission(
                folderUri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        );

        DocumentFile directoryDocFile = DocumentFile.fromTreeUri(requireContext(), folderUri);

        if (directoryDocFile != null && directoryDocFile.isDirectory()) {
            for (DocumentFile file : directoryDocFile.listFiles()) {
                if ("custom_workout.json".equals(file.getName())) {
                    try (InputStream is = requireContext().getContentResolver().openInputStream(file.getUri());
                         BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

                        StringBuilder builder = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                        return builder.toString();

                    } catch (IOException e) {
                        Log.e("SAF", "Error reading JSON: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
            Log.e("SAF", "custom_workout.json not found in directory.");
        } else {
            Log.e("SAF", "Invalid directory URI or directory not accessible.");
        }

        return null;
    }

    private void saveJsonToSAF(String jsonString) {
        SharedPreferences prefs = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String folderUriString = prefs.getString("directory_uri", null);

        if (folderUriString == null) {
            Toast.makeText(getContext(), "No directory selected to save the file.", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri folderUri = Uri.parse(folderUriString);
        DocumentFile pickedDir = DocumentFile.fromTreeUri(requireContext(), folderUri);
        if (pickedDir == null || !pickedDir.canWrite()) {
            Toast.makeText(getContext(), "Unable to access the selected directory.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Delete existing file if it exists (to overwrite cleanly)
        DocumentFile existingFile = pickedDir.findFile("custom_workout.json");
        if (existingFile != null) {
            existingFile.delete();
        }

        // Create new JSON file
        DocumentFile newJsonFile = pickedDir.createFile("application/json", "custom_workout");

        try {
            OutputStream outputStream = requireContext().getContentResolver().openOutputStream(newJsonFile.getUri());
            if (outputStream != null) {
                outputStream.write(jsonString.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
                Toast.makeText(getContext(), "JSON saved using SAF!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to open output stream.", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error saving JSON with SAF.", Toast.LENGTH_SHORT).show();
        }
    }



    // _________ Set reps and weight giver__
    private int getmidval(int min, int max){
        return (min+max)/2;
    }
    private int[] minmaxreps_sets(String exercise_intensity){

       int[] final_baseline_reps_sets =  baseline_reps_sets(exercise_intensity);

        int minreps_final, maxreps_final;
        int minsets_final, maxsets_final;

       int minreps_hypertrophy = final_baseline_reps_sets[0];
       int maxreps_hypertrophy = final_baseline_reps_sets[1];
       int minsets_hypertrophy = final_baseline_reps_sets[2];
       int maxsets_hypertrophy = final_baseline_reps_sets[3];

        int[] uservalues = {Personalizing_main.Fitness_value
                ,Personalizing_main.Strength_value
                ,Personalizing_main.Endurance_value
                ,Personalizing_main.Energy_level
        };

        for (int i = 0; i < uservalues.length; i++) {

            switch(i){
                case 0:
                   int[] fitnes_param = fitness_valuerep_set_changes(uservalues[i],minreps_hypertrophy
                            ,maxreps_hypertrophy,minsets_hypertrophy,maxsets_hypertrophy );
                    minreps_hypertrophy = fitnes_param[0];
                    maxreps_hypertrophy = fitnes_param[1];
                    minsets_hypertrophy = fitnes_param[2];
                    maxsets_hypertrophy = fitnes_param[3];
                    break;
                case 1:
                    int[] str_param = strength_valuerep_set_changes(uservalues[i],minreps_hypertrophy
                            ,maxreps_hypertrophy,minsets_hypertrophy,maxsets_hypertrophy );
                    minreps_hypertrophy = str_param[0];
                    maxreps_hypertrophy = str_param[1];
                    minsets_hypertrophy = str_param[2];
                    maxsets_hypertrophy = str_param[3];
                    break;
                case 2:
                    int[] end_param = endurance_valuerep_set_changes(uservalues[i],minreps_hypertrophy
                            ,maxreps_hypertrophy,minsets_hypertrophy,maxsets_hypertrophy );
                    minreps_hypertrophy = end_param[0];
                    maxreps_hypertrophy = end_param[1];
                    minsets_hypertrophy = end_param[2];
                    maxsets_hypertrophy = end_param[3];
                    break;
                case 3:
                    int[] eng_param = energy_valuerep_set_changes(uservalues[i],minreps_hypertrophy
                            ,maxreps_hypertrophy,minsets_hypertrophy,maxsets_hypertrophy );
                    minreps_hypertrophy = eng_param[0];
                    maxreps_hypertrophy = eng_param[1];
                    minsets_hypertrophy = eng_param[2];
                    maxsets_hypertrophy = eng_param[3];
                    break;

            }
        }
        minreps_final = minreps_hypertrophy;
                maxreps_final = maxreps_hypertrophy;
        minsets_final = minsets_hypertrophy;
                maxsets_final = maxsets_hypertrophy;

        return new int[]{minreps_final,maxreps_final,minsets_final,maxsets_final};

    }
    private int[] fitness_valuerep_set_changes(int fit_val
    , int min_reps, int max_reps, int min_set, int max_set){
        int rep = 0, set = 0;
        switch (fit_val) {
            case 1:
                rep = -2;  set = -1;
                break;
            case 2:
                rep = -1;  set = 0;
                break;
            case 3:
                rep = 0;   set = 0;
                break;
            case 4:
                rep = 1;   set = 1;
                break;
            case 5:
                rep = 2;   set = 1;
                break;
        }

        // Update values
        min_reps += rep;
        max_reps += rep;
        min_set += set;
        max_set += set;

        return new int[]{min_reps, max_reps, min_set, max_set};
    }
    private int[] strength_valuerep_set_changes(int str
            , int min_reps, int max_reps, int min_set, int max_set){
        int rep = 0, set = 0;
        switch(str){
            case 1:
                rep = 2;  set = -1; break;
            case 2:
                rep = 1;  set = 0; break;
            case 3:
                rep = 0;  set = 0; break;
            case 4:
                rep = -1;  set = 1; break;
            case 5:
                rep = -2;  set = 1; break;
        }
        min_reps += rep;
        max_reps += rep;
        min_set += set;
        max_set += set;

        return new int[]{min_reps, max_reps, min_set, max_set};
    }
    private int[] endurance_valuerep_set_changes(int end
            , int min_reps, int max_reps, int min_set, int max_set){
        int rep = 0, set = 0;
        switch(end){
            case 1:
                rep = 2;  set = 1; break;
            case 2:
                rep = -1;  set = 0; break;
            case 3:
                rep = 0;  set = 0; break;
            case 4:
                rep = 1;  set = -1; break;
            case 5:
                rep = 2;  set = -1; break;
        }
        min_reps += rep;
        max_reps += rep;
        min_set += set;
        max_set += set;

        return new int[]{min_reps, max_reps, min_set, max_set};
    }
    private int[] energy_valuerep_set_changes(int eng
            , int min_reps, int max_reps, int min_set, int max_set){
        int rep = 0, set = 0;
        switch(eng){
            case 1:
                rep = -2;  set = -1; break;
            case 2:
                rep = -1;  set = 0; break;
            case 3:
                rep = 0;  set = 0; break;
            case 4:
                rep = 1;  set = 1; break;
            case 5:
                rep = 2;  set = 1; break;
        }
        min_reps += rep;
        max_reps += rep;
        min_set += set;
        max_set += set;

        return new int[]{min_reps, max_reps, min_set, max_set};
    }
    private int[] baseline_reps_sets(String intensity){
        int minreps_hypertrophy = 0, maxreps_hypertrophy = 0;
        int minsets_hypertrophy = 0, maxsets_hypertrophy = 0;
        switch (intensity.toLowerCase()){

            case "high":
                 minreps_hypertrophy = 4;
                 maxreps_hypertrophy = 8;
                 minsets_hypertrophy = 4;
                 maxsets_hypertrophy = 6;
                break;
            case "medium":
                minreps_hypertrophy = 6;
                maxreps_hypertrophy = 12;
                minsets_hypertrophy = 3;
                maxsets_hypertrophy = 5;
                break;
            case "low":
                minreps_hypertrophy = 12;
                maxreps_hypertrophy = 20;
                minsets_hypertrophy = 2;
                maxsets_hypertrophy = 4;
                break;
        }

        return new int[]{minreps_hypertrophy,maxreps_hypertrophy,minsets_hypertrophy,maxsets_hypertrophy};
    }


}