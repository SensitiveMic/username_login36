package com.example.usernamelogin.workout_program.workouts;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.Exercise_Library.Exercise_Library_main;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.example.usernamelogin.workout_program.logworkouts.Logworkouts_main;
import com.example.usernamelogin.workout_program.personalizing.Personalizing_main;
import com.example.usernamelogin.workout_program.to_edit_reps_weight.Modelclass_forexercises;
import com.example.usernamelogin.workout_program.toselect_exercise_jsonlist.Exercises_to_select;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kotlin.Triple;

public class User_workouts extends AppCompatActivity implements interface_clickcustomW {
    ImageButton addButton;
    RecyclerView recyclerView;
    public static String selectedCustomWOrk;
    private Button button, buttonexec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_workouts_wrkt_prgrm);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addButton = findViewById(R.id.imageaddButton);
        recyclerView = findViewById(R.id.recviewworkouts);
        button = findViewById(R.id.buttongo_to_personalize);
        buttonexec = findViewById(R.id.buttongo_to_EXecLib);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Read JSON file and get keys
        JSONObject jsonObject = readJsonFromFile("custom_workout.json");

        if (jsonObject != null) {
            List<String> keyList = getJsonKeys(jsonObject);

            // Set up the adapter
            KeyAdapter adapter = new KeyAdapter(keyList, (interface_clickcustomW) this);
            recyclerView.setAdapter(adapter);
        }

        int membershipid = getIntent().getIntExtra("workout_id",0);
        Log.d("Membership_check_TAG", "If 0 non-member- :  "+membershipid);
        if(membershipid != 0){
            getsent_workouttovalue();
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_workouts.this, Exercises_to_select.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(v ->{Intent intent = new Intent(User_workouts.this, Personalizing_main.class);
            startActivity(intent); });
        buttonexec.setOnClickListener(v -> {
            Intent intent2 = new Intent(User_workouts.this, Exercise_Library_main.class);
            startActivity(intent2);
        });

    }

    private List<String> getJsonKeys(JSONObject jsonObject) {
        List<String> keys = new ArrayList<>();
        Iterator<String> iterator = jsonObject.keys();

        while (iterator.hasNext()) {
            keys.add(iterator.next());
        }

        return keys;
    }
    private JSONObject readJsonFromFile(String fileName) {
        try {
            File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File jsonFile = new File(directory, fileName);

            if (!jsonFile.exists()) {
                Log.d("ReadJSON", "JSON notfound: " );
                Log.d("ReadJSON", "File not found: " + fileName);
                return null;
            }

            FileInputStream fis = new FileInputStream(jsonFile);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            Log.d("ReadJSON", "JSON found: " );
            fis.close();
            return new JSONObject(stringBuilder.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    private boolean isWorkoutdatajsoninside(){
        File Directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File jsonfile = new File(Directory, "custom_workout.json");

        return jsonfile.exists();
    }
    private String readJsonFromFile(File fileName) {

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
    private void getsent_workouttovalue(){
        DatabaseReference dbget_sentwrkout = FirebaseDatabase.getInstance()
                .getReference("Users").child("Non-members").child(Login.key)
                .child("Coach_Reservation")
                .child("Sent_coach_workout");
        dbget_sentwrkout.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot workoutSnapshot : snapshot.getChildren()) {
                    String workoutTitle = workoutSnapshot.getKey();  // e.g., "Coach sent workout - w1"
                    Log.d("WorkoutKey", "Workout title: " + workoutTitle);

                    Map<Integer, Triple<List<String>, List<String>, List<String>>> allData = new HashMap<>();
                    List<Modelclass_forexercises> exerciseList = new ArrayList<>();
                    int position = 0;

                    for (DataSnapshot exerciseSnap : workoutSnapshot.getChildren()) {
                        String exerciseName = exerciseSnap.child("exerciseName").getValue(String.class);
                        String repsValue = exerciseSnap.child("repsValues").getValue(String.class);
                        String setsValue = exerciseSnap.child("setsValues").getValue(String.class);
                        String weightValue = exerciseSnap.child("weightValues").getValue(String.class);

                        // Make sure null values don't crash your app
                        if (exerciseName == null) exerciseName = "";
                        if (repsValue == null) repsValue = "0";
                        if (setsValue == null) setsValue = "0";
                        if (weightValue == null) weightValue = "0";

                        // Add exercise model
                        Modelclass_forexercises exerciseModel = new Modelclass_forexercises();
                        exerciseModel.setExercise_name(exerciseName);
                        exerciseList.add(exerciseModel);

                        // Wrap values in lists
                        List<String> repsList = new ArrayList<>();
                        List<String> setsList = new ArrayList<>();
                        List<String> weightList = new ArrayList<>();

                        repsList.add(repsValue);
                        setsList.add(setsValue);
                        weightList.add(weightValue);

                        // Store by position
                        allData.put(position, new Triple<>(weightList, repsList, setsList));
                        position++;
                    }

                    // Pass parsed data for this workout to your processing method
                    buildWorkoutArrayFromData(allData, exerciseList, workoutTitle);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error loading sent coach workouts: " + error.getMessage());
            }
        });
    }
    private void buildWorkoutArrayFromData(Map<Integer, Triple<List<String>, List<String>, List<String>>> allData,
                                           List<Modelclass_forexercises> exerciseList,String workout_name) {
        if(isWorkoutdatajsoninside()){
            Log.d("TAG_MainACt_save_tojson", "initialjsoncheck: TRUE " );
        }else {
            Log.e("TAG_MainACt_save_tojson", "initialjsoncheck: FALSE " );
            //create the jsonfile
            try{
                File Directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

                if (!Directory.exists()) {
                    boolean dirCreated = Directory.mkdirs();
                    if (!dirCreated) {
                        Log.e("TAG_MainACt_save_tojson", "Failed to create Documents directory!");
                        return;
                    }
                }

                File jsonFile = new File(Directory, "custom_workout.json");
                FileWriter writer = new FileWriter(jsonFile);
                writer.write("[]"); // empty json array
                writer.flush();
                writer.close();
                Log.d("TAG_MainACt_save_tojson", "Successful json file creation" );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        JSONObject root = new JSONObject();
        JSONArray workoutArray = new JSONArray();

        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        if (!directory.exists()) {
            Log.e("TAG_MainACt_save_tojson", "yep directory doesnt exist");
            boolean dirCreated = directory.mkdirs();
            if (!dirCreated) {
                Log.e("TAG_MainACt_save_tojson", "Failed to create Documents directory!");
                return;
            }
        }

        File jsonfile = new File(directory,"custom_workout.json");
        if (jsonfile.exists()) {
            try {
                String jsonString = readJsonFromFile(jsonfile);
                Log.d("TAG_MainACt_save_tojson", "OLD JSON: " + jsonString);
                root = new JSONObject(jsonString);  // Parse the current JSON string into a JSONObject

// 💣 Always replace the workout array, so we initialize a new one
                workoutArray = new JSONArray();  // Start fresh, don't reuse old one
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("TAG_MainACt_save_tojson", "did not find jsonfile");
            workoutArray = new JSONArray();  // Initialize the workout array
        }


        for (int position : allData.keySet()) {
            Modelclass_forexercises exercise_pos = exerciseList.get(position);
            String exerciseName = exercise_pos.getExercise_name();

            List<String> weightValues = allData.get(position).getFirst();
            List<String> repsValues = allData.get(position).getSecond();
            List<String> setsValues = allData.get(position).getThird();

            try {
                int add_sets = (!setsValues.isEmpty()) ? Integer.parseInt(setsValues.get(0)) : 0;
                String def_rep = (!repsValues.isEmpty()) ? repsValues.get(0) : "0";
                String def_weight = (!weightValues.isEmpty()) ? weightValues.get(0) : "0";

                JSONObject exercisebox = new JSONObject();
                JSONArray setsArray = new JSONArray();
                exercisebox.put("Exercise_name", exerciseName);

                for (int i = 0; i < add_sets; i++) {
                    JSONObject addedsets = new JSONObject();
                    String repValue = (i < repsValues.size()) ? repsValues.get(i) : def_rep;
                    String weightValue = (i < weightValues.size()) ? weightValues.get(i) : def_weight;
                    String setNum = String.valueOf(i + 1);

                    addedsets.put("Reps", repValue);
                    addedsets.put("Weight", weightValue);
                    addedsets.put("Set", setNum);
                    setsArray.put(addedsets);
                }

                exercisebox.put("Sets", setsArray);
                exercisebox.put("Workout_log", new JSONArray());
                workoutArray.put(exercisebox);
            } catch (JSONException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
        // Save the updated JSON
        try {
            // Append the workout array to the root object
            root.put(workout_name, workoutArray);  // Use your specific workoutname

            // Convert the JSON object to string
            String jsonString = root.toString();

            // Save to internal storage
            saveJsonToexternalStorage(jsonString);

            Log.d("TAG9", "Updated JSON: " + jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("WorkoutArray", workoutArray.toString());
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
    @Override
    public void onclick(int pos) {
        Intent intent = new Intent(User_workouts.this, Logworkouts_main.class);
        startActivity(intent);
    }
}