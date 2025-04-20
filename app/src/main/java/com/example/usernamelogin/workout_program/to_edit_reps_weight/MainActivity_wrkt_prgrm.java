package com.example.usernamelogin.workout_program.to_edit_reps_weight;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.usernamelogin.R;
import com.example.usernamelogin.workout_program.workouts.User_workouts;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import java.util.List;
import java.util.Map;

public class MainActivity_wrkt_prgrm extends AppCompatActivity {
    RecyclerView recyclerView;
    String json;
    ExerciseAdapter adapter;
    String workoutname;
    private List<Modelclass_forexercises> exerciseList;
    private static final int REQUEST_CODE_OPEN_JSON = 101;
    private static final int REQUEST_CODE_CREATE_JSON = 102;
    private static final int REQUEST_CODE_OPEN_FOLDER = 103;

    private String pendingJsonToSave; // Store temporarily
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_wrkt_prgrm);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button doneButton = findViewById(R.id.doneButton);
        exerciseList = ExerciseRepository.getExercises(this);
        displayJSONlistrecview();

        doneButton.setOnClickListener(v -> {
            showEditTextDialog();
        });
    }
    private void inputtojson2() {
        Map<Integer, Pair<List<String>, List<String>>> allData = adapter.getAllData();
        JSONArray workoutArray = new JSONArray();

        for (int position : allData.keySet()) {
            Modelclass_forexercises exercise_pos = exerciseList.get(position);
            String exerciseName = exercise_pos.getExercise_name();
            List<String> weightValues = allData.get(position).first;
            List<String> repsValues = allData.get(position).second;

            try {
                JSONObject exercisebox = new JSONObject();
                JSONArray setsArray = new JSONArray();

                exercisebox.put("Exercise_name", exerciseName);

                for (int i = 0; i < weightValues.size(); i++) {
                    JSONObject addedsets = new JSONObject();
                    addedsets.put("Reps", repsValues.get(i));
                    addedsets.put("Weight", weightValues.get(i));
                    setsArray.put(addedsets);
                }

                exercisebox.put("Sets", setsArray);
                exercisebox.put("Workout_log", new JSONArray());
                workoutArray.put(exercisebox);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Pass workout name and array separately to merging function
        saveToExistingJsonFile(workoutname, workoutArray);
    }

    private void saveToExistingJsonFile(String newWorkoutName, JSONArray newWorkoutArray) {
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String folderUriString = prefs.getString("directory_uri", null);

        if (folderUriString == null) {
            Toast.makeText(this, "Folder not selected yet.", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri folderUri = Uri.parse(folderUriString);
        DocumentFile pickedDir = DocumentFile.fromTreeUri(this, folderUri);

        if (pickedDir != null && pickedDir.canWrite()) {
            DocumentFile jsonFile = pickedDir.findFile("custom_workout.json");

            if (jsonFile == null) {
                jsonFile = pickedDir.createFile("application/json", "custom_workout.json");
            }

            try {
                // Step 1: Read existing content
                JSONObject existingJson = readExistingJson(jsonFile);

                // Step 2: Add/overwrite the new workout
                existingJson.put(newWorkoutName, newWorkoutArray);

                // Step 3: Save back the combined JSON
                try (OutputStream outputStream = getContentResolver().openOutputStream(jsonFile.getUri(), "wt")) {
                    outputStream.write(existingJson.toString().getBytes(StandardCharsets.UTF_8));
                    outputStream.flush();
                    Toast.makeText(this, "Workout added to custom_workout.json", Toast.LENGTH_SHORT).show();
                    Log.d("TAG_MainACT", "Merged JSON: " + existingJson.toString());
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error appending workout", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No write access to folder", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            if (requestCode == REQUEST_CODE_CREATE_JSON && pendingJsonToSave != null) {
                writeJsonToUri(uri, pendingJsonToSave);
                pendingJsonToSave = null;
            }

            else if (requestCode == REQUEST_CODE_OPEN_FOLDER) {
                // ✅ THIS is where you put the line:
                getContentResolver().takePersistableUriPermission(uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                prefs.edit().putString("directory_uri", uri.toString()).apply();

                Toast.makeText(this, "Folder selected and permission granted!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private JSONObject readExistingJson(DocumentFile jsonFile) {
        try (InputStream inputStream = getContentResolver().openInputStream(jsonFile.getUri())) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return new JSONObject(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject(); // Return empty object if error
        }
    }

    private void writeJsonToUri(Uri uri, String jsonString) {
        try (OutputStream outputStream = getContentResolver().openOutputStream(uri, "wt")) {
            outputStream.write(jsonString.getBytes());
            outputStream.flush();
            Toast.makeText(this, "JSON saved successfully!", Toast.LENGTH_SHORT).show();
            Log.d("TAG_MainACt", "Successfully wrote to JSON file: " + uri.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save JSON!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showEditTextDialog() {
        // Create an EditText programmatically
        EditText editText = new EditText(this);
        editText.setHint("Enter workout name");

        // Build the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Type workout name")
                .setMessage("Please enter:")
                .setView(editText) // Set the EditText as the view of the dialog
                .setPositiveButton("done!", (dialog, which) -> {
                    // Handle the input from the EditText

                    workoutname = editText.getText().toString().trim();
                   // clearJsonFile("custom_workout.json");
                    inputtojson2();
                    Toast.makeText(this, "You entered: " + workoutname, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity_wrkt_prgrm.this, User_workouts.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss()) // Dismiss the dialog
                .show();
    }

    private void displayJSONlistrecview() {
        // Get JSON string from intent
        json = getIntent().getStringExtra("selected_exercises");

        // Parse JSON into a list of exercises
        List<Modelclass_forexercises> selectedExercises = new Gson().fromJson(json,
                new TypeToken<List<Modelclass_forexercises>>() {}.getType());

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new ExerciseAdapter(selectedExercises,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


}