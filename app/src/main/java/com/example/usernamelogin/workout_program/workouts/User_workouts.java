package com.example.usernamelogin.workout_program.workouts;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriPermission;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.Exercise_Library.Exercise_Library_main;
import com.example.usernamelogin.Member.Member_Profile;
import com.example.usernamelogin.Member.Reservation.Coach_Reservation_main;
import com.example.usernamelogin.NonMemberUser.Gym_prop.Gym_Properties_Main;
import com.example.usernamelogin.NonMemberUser.NonMemberUSER;
import com.example.usernamelogin.NonMemberUser.Profile;
import com.example.usernamelogin.NonMemberUser.Reservations.Reservations;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
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
    SharedPreferences prefs;
    String folderUriString;

    private static final int REQUEST_CODE_OPEN_DOCUMENT_TREE = 1001;
    private ActivityResultLauncher<Intent> directoryPickerLauncher;

    @SuppressLint("WrongConstant")
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

        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        folderUriString = prefs.getString("directory_uri", null);
        Log.d("TAG_URI", "Loaded saved_uri: " + folderUriString); // Debug

        if (folderUriString != null) {
            Uri folderUri = Uri.parse(folderUriString);
            DocumentFile pickedDir = DocumentFile.fromTreeUri(this, folderUri);

            if (pickedDir != null) {
                DocumentFile jsonFile = pickedDir.findFile("custom_workout.json");

                if (jsonFile != null && jsonFile.exists()) {
                    JSONObject jsonObject = readJsonFromUri(jsonFile.getUri());
                    // ✅ Use jsonObject here
                    if (jsonObject != null) {
                        List<String> keyList = getJsonKeys(jsonObject);
                        String keycontents = keyList.toString();
                        Log.d("TAG_KeyContent", "onCreate: "+keycontents );
                        // Set up the adapter
                        KeyAdapter adapter = new KeyAdapter(keyList, (interface_clickcustomW) this, User_workouts.this);
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    Log.d("TAG_KeyContent", "custom_workout.json not found in picked folder.");
                }
            }
        } else {
            Log.d("TAG_KeyContent", "No saved folder URI found.");
        }
        //____member or not?  0 is member 1 is non-member
        int membershipid = getIntent().getIntExtra("workout_id",0);
        Log.d("Membership_check_TAG", "If 0 non-member- :  "+membershipid);
        if(membershipid != 0){
            getSentWorkoutToValue();
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
        directoryPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getData() != null) {
                            Uri selectedDirectoryUri = data.getData();

                            // Persist URI permissions
                            final int takeFlags = data.getFlags()
                                    & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                            getContentResolver().takePersistableUriPermission(selectedDirectoryUri, takeFlags);

                            // Save URI to SharedPreferences
                            saveDirectoryUri(selectedDirectoryUri);

                            // Check/create JSON file
                            checkOrCreateJsonFile(selectedDirectoryUri);
                        }
                    }
                }
        );
        checkIfJsonExistsOrLaunchPicker();
    }

    private List<String> getJsonKeys(JSONObject jsonObject) {
        List<String> keys = new ArrayList<>();
        Iterator<String> iterator = jsonObject.keys();

        while (iterator.hasNext()) {
            keys.add(iterator.next());
        }

        return keys;
    }
    private JSONObject readJsonFromUri(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            if (inputStream == null) {
                Log.e("ReadJSON", "InputStream is null");
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            inputStream.close();
            Log.d("ReadJSON", "JSON read successfully from SAF");
            return new JSONObject(stringBuilder.toString());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void getSentWorkoutToValue() {
        DatabaseReference dbGetSentWorkout = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child("Non-members")
                .child(Login.key)
                .child("Coach_Reservation")
                .child("Sent_coach_workout");

        dbGetSentWorkout.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Uri selectedDirectoryUri = null;
                for (DataSnapshot workoutSnapshot : snapshot.getChildren()) {
                    String workoutTitle = workoutSnapshot.getKey();
                    Log.d("WorkoutKey", "Workout title: " + workoutTitle);

                    Map<Integer, Triple<List<String>, List<String>, List<String>>> allData = new HashMap<>();
                    List<Modelclass_forexercises> exerciseList = new ArrayList<>();
                    int position = 0;

                    for (DataSnapshot exerciseSnap : workoutSnapshot.getChildren()) {
                        String exerciseName = getStringValue(exerciseSnap, "exerciseName", "");
                        String repsValue = getStringValue(exerciseSnap, "repsValues", "0");
                        String setsValue = getStringValue(exerciseSnap, "setsValues", "0");
                        String weightValue = getStringValue(exerciseSnap, "weightValues", "0");

                        Modelclass_forexercises exerciseModel = new Modelclass_forexercises();
                        exerciseModel.setExercise_name(exerciseName);
                        exerciseList.add(exerciseModel);

                        List<String> repsList = Collections.singletonList(repsValue);
                        List<String> setsList = Collections.singletonList(setsValue);
                        List<String> weightList = Collections.singletonList(weightValue);

                        allData.put(position, new Triple<>(weightList, repsList, setsList));
                        position++;
                    }
                    String savedUriString = prefs.getString("directory_uri", null);

                    if (savedUriString != null) {
                         selectedDirectoryUri = Uri.parse(savedUriString);

                        // ✅ Now you can use selectedDirectoryUri to read/write using SAF
                    }
                    buildWorkoutArrayFromData(allData, exerciseList, workoutTitle,selectedDirectoryUri);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error loading sent coach workouts: " + error.getMessage());
            }
        });
    }
    private void buildWorkoutArrayFromData(Map<Integer, Triple<List<String>, List<String>, List<String>>> allData,
                                           List<Modelclass_forexercises> exerciseList, String workout_name,
                                           Uri selectedDirectoryUri) {

        if (selectedDirectoryUri == null) {
            Log.e("SAF_JSON", "Directory URI not selected");
            return;
        }

        DocumentFile pickedDir = DocumentFile.fromTreeUri(this, selectedDirectoryUri);
        if (pickedDir == null || !pickedDir.exists()) {
            Log.e("SAF_JSON", "Directory not accessible");
            return;
        }

        // Check if JSON file exists, or create one
        DocumentFile jsonFile = pickedDir.findFile("custom_workout.json");
        if (jsonFile == null) {
            jsonFile = pickedDir.createFile("application/json", "custom_workout");
            if (jsonFile == null) {
                Log.e("SAF_JSON", "Failed to create custom_workout.json");
                return;
            }
        }

        JSONObject root;
        JSONArray workoutArray;

        // Try to read existing content
        try {
            String existingJson = readJsonFromUri2(jsonFile.getUri());
            if (existingJson != null && !existingJson.isEmpty()) {
                root = new JSONObject(existingJson);
            } else {
                root = new JSONObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
            root = new JSONObject();
        }

        workoutArray = new JSONArray(); // Always refresh

        for (int position : allData.keySet()) {
            try {
                Modelclass_forexercises exercise_pos = exerciseList.get(position);
                String exerciseName = exercise_pos.getExercise_name();

                List<String> weightValues = allData.get(position).getFirst();
                List<String> repsValues = allData.get(position).getSecond();
                List<String> setsValues = allData.get(position).getThird();

                int add_sets = (!setsValues.isEmpty()) ? Integer.parseInt(setsValues.get(0)) : 0;
                String def_rep = (!repsValues.isEmpty()) ? repsValues.get(0) : "0";
                String def_weight = (!weightValues.isEmpty()) ? weightValues.get(0) : "0";

                JSONObject exercisebox = new JSONObject();
                exercisebox.put("Exercise_name", exerciseName);
                JSONArray setsArray = new JSONArray();

                for (int i = 0; i < add_sets; i++) {
                    JSONObject addedsets = new JSONObject();
                    addedsets.put("Reps", (i < repsValues.size()) ? repsValues.get(i) : def_rep);
                    addedsets.put("Weight", (i < weightValues.size()) ? weightValues.get(i) : def_weight);
                    addedsets.put("Set", String.valueOf(i + 1));
                    setsArray.put(addedsets);
                }

                exercisebox.put("Sets", setsArray);
                exercisebox.put("Workout_log", new JSONArray());
                workoutArray.put(exercisebox);

            } catch (JSONException | NumberFormatException e) {
                e.printStackTrace();
            }
        }

        try {
            root.put(workout_name, workoutArray);
            String updatedJsonString = root.toString();
            writeJsonToUri(jsonFile.getUri(), updatedJsonString);
            Log.d("TAG_JSON_SAF", "Saved JSON: " + updatedJsonString);
            Toast.makeText(this, "Workout check done!", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void writeJsonToUri(Uri uri, String jsonString) {
        try (OutputStream outputStream = getContentResolver().openOutputStream(uri, "wt")) {
            outputStream.write(jsonString.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("SAF_JSON", "Failed to write JSON");
        }
    }
    private String getStringValue(DataSnapshot snapshot, String key, String defaultValue) {
        String value = snapshot.child(key).getValue(String.class);
        return (value != null) ? value : defaultValue;
    }

    private String readJsonFromUri2(Uri uri) {
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


    @Override
    public void onclick(int pos) {
        Intent intent = new Intent(User_workouts.this, Logworkouts_main.class);

        startActivity(intent);
    }

    //_____________ navbar stuff__

    //check permission stufff____
    private void showFolderSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Folder")
                .setMessage("This app needs a folder to store your workout data. Do you want to select one now?")
                .setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        launchDirectoryPicker();;
                    }
                })
                .setNegativeButton("Cancel", null)
                .setCancelable(false) // Optional: Prevent closing without selecting
                .show();
    }
    private void checkIfJsonExistsOrLaunchPicker() {
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String uriString = prefs.getString("directory_uri", null);

        if (uriString != null) {
            Uri savedUri = Uri.parse(uriString);

            // 🔍 STEP 1: Check if app already has permission to access this URI
            boolean hasPersistedPermission = false;
            for (UriPermission perm : getContentResolver().getPersistedUriPermissions()) {
                if (perm.getUri().equals(savedUri) && perm.isReadPermission() && perm.isWritePermission()) {
                    hasPersistedPermission = true;
                    break;
                }
            }

            if (!hasPersistedPermission) {
                // ❌ App does NOT have permission — show your custom dialog here:
                showFolderSelectionDialog();  // ✅ CALL IT HERE
                return;
            }

            // ✅ App has permission — proceed with file checks
            if (doesCustomWorkoutExist(savedUri)) {
                Log.d("TAG_SAF", "custom_workout.json already exists — skipping picker");

            } else {
                // File doesn’t exist — show dialog to create it
                showSelectFolderDialog(savedUri);
            }

        } else {
            // 📁 No folder has ever been selected — launch folder picker
            showFolderSelectionDialog();
        }
    }
    private void launchDirectoryPicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION |
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
                Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION |
                Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
        directoryPickerLauncher.launch(intent);
    }


    private void saveDirectoryUri(Uri uri) {
        Log.d("TAG_URI", "saveDirectoryUri called with: " + uri.toString());
        getSharedPreferences("prefs", MODE_PRIVATE)
                .edit()
                .putString("directory_uri", uri.toString())
                .apply();
    }
    private void checkOrCreateJsonFile(Uri directoryUri) {
        DocumentFile pickedDir = DocumentFile.fromTreeUri(this, directoryUri);

        if (pickedDir == null || !pickedDir.isDirectory()) {
            Toast.makeText(this, "Invalid directory selected", Toast.LENGTH_SHORT).show();
            return;
        }

        DocumentFile existingFile = null;

        for (DocumentFile file : pickedDir.listFiles()) {
            if (file.getName() != null && file.getName().equals("custom_workout.json")) {
                existingFile = file;
                break;
            }
        }

        if (existingFile != null) {
            Log.d("TAG_JSON_FILE", "custom_workout.json already exists.");
            Toast.makeText(this, "File already exists", Toast.LENGTH_SHORT).show();
        } else {
            // Create the file
            DocumentFile newJsonFile = pickedDir.createFile("application/json", "custom_workout.json");

            if (newJsonFile != null) {
                try (OutputStream outputStream = getContentResolver().openOutputStream(newJsonFile.getUri())) {
                    outputStream.write("[]".getBytes()); // Write an empty array
                    outputStream.flush();
                    Log.d("TAG_JSON_FILE", "Created custom_workout.json");
                    Toast.makeText(this, "custom_workout.json created", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error creating file", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private boolean doesCustomWorkoutExist(Uri directoryUri) {
        try {
            ContentResolver resolver = getContentResolver();
            Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(
                    directoryUri,
                    DocumentsContract.getTreeDocumentId(directoryUri)
            );

            Cursor cursor = resolver.query(childrenUri,
                    new String[]{DocumentsContract.Document.COLUMN_DISPLAY_NAME},
                    null, null, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String fileName = cursor.getString(0);
                    if ("custom_workout.json".equals(fileName)) {
                        cursor.close();
                        return true;
                    }
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    private void showSelectFolderDialog(Uri wew) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Folder")
                .setMessage("Please choose a folder to store your custom workouts.")
                .setPositiveButton("Select Folder", (dialog, which) -> {
                    checkOrCreateJsonFile(wew);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    // ____ Deletion__
    public void deleteWorkoutFromJson(String workoutKey) {
        if (folderUriString == null) return;

        Uri folderUri = Uri.parse(folderUriString);
        DocumentFile pickedDir = DocumentFile.fromTreeUri(this, folderUri);

        if (pickedDir != null) {
            DocumentFile jsonFile = pickedDir.findFile("custom_workout.json");

            if (jsonFile != null && jsonFile.exists()) {
                JSONObject jsonObject = readJsonFromUri(jsonFile.getUri());
                if (jsonObject != null && jsonObject.has(workoutKey)) {
                    jsonObject.remove(workoutKey); // 🔥 Remove the key

                    // 🔄 Save updated JSON
                    writeJsonToUri(jsonFile.getUri(), jsonObject.toString());
                    Toast.makeText(this, "Workout deleted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}