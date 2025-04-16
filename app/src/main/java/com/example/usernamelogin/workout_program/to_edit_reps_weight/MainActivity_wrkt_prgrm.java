package com.example.usernamelogin.workout_program.to_edit_reps_weight;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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
    private boolean isWorkoutdatajsoninside(){
        File Directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File jsonfile = new File(Directory, "custom_workout.json");

        return jsonfile.exists();
    }
    private void inputtojson2() {
        // Ask user to open existing file
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("application/json");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE_OPEN_JSON);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            if (requestCode == REQUEST_CODE_OPEN_JSON) {
                // Read and continue writing into this file
                String existingJson = readJsonFromUri(uri);
                if (existingJson == null || existingJson.isEmpty()) {
                    // File exists but empty, write an empty array
                    writeJsonToUri(uri, "[]");
                    Log.d("TAG_MainACt", "Created new empty JSON array");
                } else {
                    Log.d("TAG_MainACt", "File exists and has content");
                    // Continue with updating or processing
                }
            } else if (requestCode == REQUEST_CODE_CREATE_JSON) {
                writeJsonToUri(uri, "[]");
                Log.d("TAG_MainACt", "Created new JSON file via SAF");
            }
        } else if (requestCode == REQUEST_CODE_OPEN_JSON) {
            // User canceled or file not found -> ask to create
            Intent createIntent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
            createIntent.setType("application/json");
            createIntent.putExtra(Intent.EXTRA_TITLE, "custom_workout.json");
            startActivityForResult(createIntent, REQUEST_CODE_CREATE_JSON);
        }
    }
    private String readJsonFromUri(Uri uri) {
        try (InputStream inputStream = getContentResolver().openInputStream(uri)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void writeJsonToUri(Uri uri, String jsonString) {
        try (OutputStream outputStream = getContentResolver().openOutputStream(uri, "wt")) {
            outputStream.write(jsonString.getBytes());
            outputStream.flush();
            Log.d("TAG_MainACt", "Successfully wrote to JSON file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void inputtojson() {
        //check if there is custom_workout.json file
        if(isWorkoutdatajsoninside()){
            Log.d("TAG_MainACt", "initialjsoncheck: TRUE " );
        }else {
            Log.e("TAG_MainACt", "initialjsoncheck: FALSE " );
            //create the jsonfile
            try{
                File Directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

                if (!Directory.exists()) {
                    boolean dirCreated = Directory.mkdirs();
                    if (!dirCreated) {
                        Log.e("TAG_MainACt", "Failed to create Documents directory!");
                        return;
                    }
                }

                File jsonFile = new File(Directory, "custom_workout.json");
                FileWriter writer = new FileWriter(jsonFile);
                writer.write("[]"); // empty json array
                writer.flush();
                writer.close();
                Log.d("TAG_MainACt", "Successful json file creation" );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // Retrieve exercise data
        Map<Integer, Pair<List<String>, List<String>>> allData = adapter.getAllData();
        JSONObject root = new JSONObject();
        JSONArray workoutArray = new JSONArray();

        //custom_workout.json directory
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        if (!directory.exists()) {
            Log.e("TAG_MainACt", "yep directory doesnt exist");
            boolean dirCreated = directory.mkdirs();
            if (!dirCreated) {
                Log.e("TAG_MainACt", "Failed to create Documents directory!");
                return;
            }
        }

        File jsonfile = new File(directory,"custom_workout.json");
        if (jsonfile.exists()) {
            try {
                String jsonString = readJsonFromFile(jsonfile);
                Log.d("TAG_MainACt", "OLD JSON: " + jsonString);
                root = new JSONObject(jsonString);  // Parse the current JSON string into a JSONObject
                workoutArray = root.optJSONArray(workoutname);  // Get the existing workout array
                if (workoutArray == null) {
                    workoutArray = new JSONArray();  // Create a new workout array if it doesn't exist
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("TAG_MainACt", "did not find jsonfile");
                workoutArray = new JSONArray();  // Initialize the workout array
        }


        for (int position : allData.keySet()) {
            Modelclass_forexercises exercise_pos = exerciseList.get(position);
            String exerciseName = exercise_pos.getExercise_name();
            List<String> weightValues = allData.get(position).first;
            List<String> repsValues = allData.get(position).second;

            try {

                JSONObject exercisebox = new JSONObject();  // New exercise object
                JSONArray setsArray = new JSONArray();      // New sets array

                exercisebox.put("Exercise_name", exerciseName);

                for (int i = 0; i < weightValues.size(); i++) {
                    JSONObject addedsets = new JSONObject(); // New set object
                    addedsets.put("Reps", repsValues.get(i)); // Add reps for this set
                    addedsets.put("Weight", weightValues.get(i)); // Add weight for this set
                    setsArray.put(addedsets); // Append the set to the sets array
                }

                // Add the sets array to the exercise
                exercisebox.put("Sets", setsArray);
                exercisebox.put("Workout_log",new JSONArray());

                // Append the exercise to the workout array
                workoutArray.put(exercisebox);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Save the updated JSON
        try {
            // Append the workout array to the root object
            root.put(workoutname, workoutArray);  // Use your specific workoutname

            // Convert the JSON object to string
            String jsonString = root.toString();

            // Save to internal storage
            saveJsonToexternalStorage(jsonString);

            Log.d("TAG9", "Updated JSON: " + jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String readJsonFromFile(File fileName) {
        // internal dirctory
        String json = "";
        try {
            File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File jsonFile = new File(directory, fileName.getName()); // File in Documents folder

            // Check if the file exists
            if (!jsonFile.exists()) {
                Log.d("ReadJSON", "File not found: " + jsonFile.getAbsolutePath());
                return null; // Return null if the file does not exist
            }
            FileInputStream fis = new FileInputStream(jsonFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            json = stringBuilder.toString();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    private void saveJsonToexternalStorage(String jsonString) {
        try {
            File Directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File jsonfile = new File(Directory, "custom_workout.json");
            FileOutputStream fos = new FileOutputStream(jsonfile);
            fos.write(jsonString.getBytes());
            fos.close();
            Toast.makeText(this, "JSON saved to internal storage!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save JSON.", Toast.LENGTH_SHORT).show();
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
                    inputtojson();
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
    private void clearJsonFile(String fileName) {
        try {
            // Create an empty JSON object (or JSONArray if needed)
            JSONObject emptyJson = new JSONObject();

            // Convert the empty JSON to string
            String emptyJsonString = emptyJson.toString();

            // Overwrite the file with the empty JSON
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(emptyJsonString.getBytes());
            fos.close();

            Toast.makeText(this, "JSON file cleared!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to clear JSON file.", Toast.LENGTH_SHORT).show();
        }
    }

}