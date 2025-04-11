package com.example.usernamelogin.Exercise_Library;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.usernamelogin.workout_program.personalizing.Personalizing_main;
import com.example.usernamelogin.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Exercise_Library_main extends AppCompatActivity {
    ExpandableListView expandableListView;
    private Expandablelist_adapter_ExLib adapter;
    private List<String> categoryList;
    private HashMap<String, List<Exercise_selecting2ExLib>> exerciseMap;
    Button trialdone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercise_library_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        expandableListView = findViewById(R.id.expandableListView);

        categoryList = new ArrayList<>();
        exerciseMap = new HashMap<>();

        loadExercisesFromJson();
        adapter = new Expandablelist_adapter_ExLib(this, categoryList, exerciseMap);
        expandableListView.setAdapter(adapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Exercise_selecting2ExLib selectedExercise = (Exercise_selecting2ExLib) adapter.getChild(groupPosition, childPosition);
                showExerciseDetailsDialog(selectedExercise);

                String exerciseName = selectedExercise.getExerciseName();

                Toast.makeText(Exercise_Library_main.this, "Selected: " + exerciseName, Toast.LENGTH_SHORT).show();

                return true; // true means the click is handled
            }
        });


    }

    private void loadExercisesFromJson() {
        try {

            Exercise_selecting2ExLib  exercise = null;

            InputStream inputStream = this.getAssets().open("exercises.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");

            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                int id = obj.getInt("exercise_id");
                String name = obj.getString("exercise_name");
                String intensity = obj.getString("exercise_intensity");
                String muscleGroup = obj.getString("muscle_location");
                String description = obj.getString("exercise_descrp");
                String execLink = obj.getString("exercise_exec_link_detail");

                JSONArray weightRecommendations = obj.getJSONArray("weight_recommendations");
                Double recognized_weight_recomm = 0.0;
                boolean beginnerString = false;

                JSONObject recommendation = weightRecommendations.getJSONObject(0);
                String fitnessKey = Personalizing_main.Fitness_value < 4 ? "beginner" :
                        Personalizing_main.Fitness_value == 4 ? "intermediate" : "advanced";

                Object beginnerValue = recommendation.get(fitnessKey);

                if (beginnerValue instanceof String) {
                    beginnerString = true;
                    String beginner_String = recommendation.getString(fitnessKey);
                    Log.d("TAGSelectedExercises","BW usage?: " + beginner_String);

                } else {
                    recognized_weight_recomm = recommendation.getDouble(fitnessKey);
                    Log.d("TAGSelectedExercises","is it in decimal?: " + recognized_weight_recomm);
                }
                exercise = new Exercise_selecting2ExLib(id, name, intensity, muscleGroup, description,execLink);


                if (!exerciseMap.containsKey(muscleGroup)) {
                    exerciseMap.put(muscleGroup, new ArrayList<>());
                    categoryList.add(muscleGroup);
                }
                exerciseMap.get(muscleGroup).add(exercise);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
    private void showExerciseDetailsDialog(Exercise_selecting2ExLib exercise) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_exercise_details_2, null);

        TextView tvName = dialogView.findViewById(R.id.tvExerciseName);
        TextView tvIntensity = dialogView.findViewById(R.id.tvIntensity);
        TextView tvLocation = dialogView.findViewById(R.id.tvLocation);
        TextView tvDescription = dialogView.findViewById(R.id.tvDescription);
        Button btnDemoLink = dialogView.findViewById(R.id.btnDemoLink);

        tvName.setText(exercise.getExerciseName());
        tvIntensity.setText("Intensity: " + exercise.getExerciseIntensity());
        tvLocation.setText("Muscle Group: " + exercise.getMuscleLocation());
        tvDescription.setText("Description: " + exercise.getExerciseDescrp());

        String link = exercise.getExercise_exec_link_detail();
        btnDemoLink.setOnClickListener(v -> {
            if (link != null && !link.isEmpty()) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(browserIntent);
            } else {
                Toast.makeText(this, "No link available", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setView(dialogView)
                .setPositiveButton("Close", null)
                .show();
    }
}
