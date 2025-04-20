package com.example.usernamelogin.workout_program.logworkouts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.R;
import com.example.usernamelogin.workout_program.grahptry.Graph_main;
import com.example.usernamelogin.workout_program.workouts.User_workouts;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Logworkouts_main extends AppCompatActivity {

    Button doneButton;
    Button seelog;
    RecyclerView recyclerView;
    Adapter_Logworkouts adapter;
    List<Exercise> exercises;

    String jsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_logworkouts_main_wrkt_prgrm);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView =findViewById(R.id.recviewofworkouts);
        doneButton = findViewById(R.id.done_button);
        seelog = findViewById(R.id.log_button);
        jsonString = loadJsonFromSAF();

        if(jsonString != null){
            exercises = parseJson(jsonString);
            adapter = new Adapter_Logworkouts(exercises);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG!!", "Clicked!");

                // Get the values from the adapter
                Map<Integer, Map<Integer, String>> editTextValues = adapter.getEditTextValues();
                Map<Integer, Map<Integer, String>> textViewValues = adapter.getTextViewValues();

                // Initialize a variable to store the sum of all products
                int totalSum = 0;
                Map<Integer, Integer> exerciseSums = new HashMap<>();
                // Iterate through each exercise
                for (int exerciseIndex : editTextValues.keySet()) {
                    // Retrieve the sets for the current exercise
                    Map<Integer, String> editTextSets = editTextValues.get(exerciseIndex);
                    Map<Integer, String> textViewSets = textViewValues.get(exerciseIndex);

                    // Temporary list to store the products for this exercise
                    List<Integer> products = new ArrayList<>();

                    // Iterate through each set of the exercise
                    for (int setIndex : editTextSets.keySet()) {
                        String editTextValue = editTextSets.get(setIndex);
                        String textViewValue = (textViewSets != null && textViewSets.containsKey(setIndex))
                                ? textViewSets.get(setIndex)
                                : "0"; // Default to "0" if no value

                        try {
                            // Convert values to integers
                            int reps = Integer.parseInt(editTextValue);
                            int weight = Integer.parseInt(textViewValue);

                            // Calculate the product and add it to the list
                            int product = reps * weight;
                            products.add(product);

                        } catch (NumberFormatException e) {
                            Log.e("TAG!!", "Invalid number format for set " + setIndex);
                        }
                    }

                    // Add the products for this exercise to the total sum
                    int exerciseSum = 0;
                    for (int product_index : products) {
                        exerciseSum += product_index;
                    }
                    exerciseSums.put(exerciseIndex, exerciseSum);
                    save_logs(exerciseSums);

                    Log.d("TAG!!", "Products: " + products + ", Exercise Sum: " + exerciseSum);
                }

                // Log the final total sum
                Log.d("TAG!!", "Total Sum of all exercises: " + totalSum);
                Intent intent = new Intent(Logworkouts_main.this, User_workouts.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
        seelog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Logworkouts_main.this, Graph_main.class);
                startActivity(intent);
            }
        });
    }
private void save_logs(Map<Integer, Integer> exerciseSums){
    try {
        long currentDate = System.currentTimeMillis();
        JSONObject rootObject = new JSONObject(jsonString);
        JSONArray selectedWorkoutArray = rootObject.getJSONArray(User_workouts.selectedCustomWOrk);
        for(int i = 0;i < selectedWorkoutArray.length(); i++){

            JSONObject Xercixe = selectedWorkoutArray.getJSONObject(i);
            String Exercisename = Xercixe.getString("Exercise_name");
            JSONArray accessWL = Xercixe.getJSONArray("Workout_log");

            if (exerciseSums.containsKey(i)) {
                JSONObject addnewlog = new JSONObject();
                addnewlog.put("date", currentDate);
                addnewlog.put("volume", exerciseSums.get(i));

                accessWL.put(addnewlog);

                Log.d("TAG!!", "Exercise " + (i + 1) + ": " + " its corresponding name: " + Exercisename);
            }

            }
        saveJsonToSAF(rootObject);

    }  catch (JSONException e) {
        throw new RuntimeException(e);
    }
}
    private void saveJsonToSAF(JSONObject rootObject) {
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String folderUriString = prefs.getString("directory_uri", null);

        if (folderUriString == null) {
            Log.e("SAF", "No directory URI found in SharedPreferences.");
            return;
        }

        Uri folderUri = Uri.parse(folderUriString);
        DocumentFile directoryDocFile = DocumentFile.fromTreeUri(this, folderUri);

        if (directoryDocFile == null || !directoryDocFile.isDirectory()) {
            Log.e("SAF", "Invalid directory DocumentFile.");
            return;
        }

        // Look for the file named custom_workout.json
        DocumentFile jsonFile = null;
        for (DocumentFile file : directoryDocFile.listFiles()) {
            if (file.getName().equals("custom_workout.json")) {
                jsonFile = file;
                break;
            }
        }

        // If file doesn't exist, create it
        if (jsonFile == null) {
            jsonFile = directoryDocFile.createFile("application/json", "custom_workout.json");
        }

        if (jsonFile != null) {
            try (OutputStream os = getContentResolver().openOutputStream(jsonFile.getUri(), "wt")) {
                if (os != null) {
                    os.write(rootObject.toString(4).getBytes(StandardCharsets.UTF_8));
                    os.flush();
                    Log.d("SAF", "Successfully saved to custom_workout.json");
                }
            } catch (IOException e) {
                Log.e("SAF", "Failed to write JSON: " + e.getMessage(), e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } else {
            Log.e("SAF", "Unable to create or access custom_workout.json");
        }
    }
    private String loadJsonFromSAF() {
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String folderUriString = prefs.getString("directory_uri", null);

        if (folderUriString == null) {
            Log.e("SAF", "No directory URI found in SharedPreferences.");
            return null;
        }

        Uri folderUri = Uri.parse(folderUriString);

        // Grant persistable permission if needed (only once after SAF selection)
        getContentResolver().takePersistableUriPermission(
                folderUri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        );

        // Get DocumentFile representing the directory
        DocumentFile directoryDocFile = DocumentFile.fromTreeUri(this, folderUri);

        if (directoryDocFile != null && directoryDocFile.isDirectory()) {
            for (DocumentFile file : directoryDocFile.listFiles()) {
                if (file.getName().equals("custom_workout.json")) {
                    try (InputStream is = getContentResolver().openInputStream(file.getUri());
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

    private List<Exercise> parseJson(String jsonString) {
        List<Exercise> exercises = new ArrayList<>();
        try {
            JSONObject rootObject = new JSONObject(jsonString); // Parse JSON string
            JSONArray selectedWorkoutArray = rootObject.getJSONArray(User_workouts.selectedCustomWOrk);  // Get the "W2" array or basically the name of the workout

            for (int i = 0; i < selectedWorkoutArray.length(); i++) {
                JSONObject exerciseObject = selectedWorkoutArray.getJSONObject(i);
                String exerciseName = exerciseObject.getString("Exercise_name"); // Extract "Exercise_name"

                JSONArray setsArray = exerciseObject.getJSONArray("Sets");
                List<Set> sets = new ArrayList<>();
                for(int j = 0; j < setsArray.length();j++){
                    JSONObject setObject = setsArray.getJSONObject(j);
                    String reps = setObject.getString("Reps");
                    String weight = setObject.getString("Weight");
                    sets.add(new Set(reps,weight));
                }
                exercises.add(new Exercise(exerciseName,sets));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return exercises;
    }

}