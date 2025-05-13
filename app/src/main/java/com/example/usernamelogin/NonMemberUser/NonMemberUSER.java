package com.example.usernamelogin.NonMemberUser;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriPermission;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.example.usernamelogin.Coach.Coach_main;
import com.example.usernamelogin.Member.Member_main;
import com.example.usernamelogin.NonMemberUser.Gym_prop.Gym_Properties_Main;
import com.example.usernamelogin.NonMemberUser.new_gym_prop.New_Gym_Properties_Main;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.example.usernamelogin.NonMemberUser.Reservations.Reservations;
import com.example.usernamelogin.workout_program.workouts.User_workouts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.OutputStream;

public class NonMemberUSER extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, reservations, profile, gym_membership,workout,logoput;
    public static String[] ProfileContents;
    private static final int REQUEST_CODE_OPEN_DIRECTORY = 123;
    private static final int REQUEST_CODE_OPEN_DIRECTORY_2 = 1001;
    private Uri selectedDirectoryUri;
    private ActivityResultLauncher<Intent> directoryPickerLauncher;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_member_user);
      //  logout_prc(NonMemberUSER.this, Login.class);
        someMethod();

        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        reservations = findViewById(R.id.Reservations_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);
        gym_membership = findViewById(R.id.Gym_navdrawer);
        workout = findViewById(R.id.member_workout);
        logoput = findViewById(R.id.logout_Button_U);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavbar(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        reservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(NonMemberUSER.this, Reservations.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(NonMemberUSER.this, Profile.class);
            }
        });
        gym_membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(NonMemberUSER.this, New_Gym_Properties_Main.class);
            }
        });
        logoput.setOnClickListener(v ->{
            logout_prc(NonMemberUSER.this, Login.class);

        });
        workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NonMemberUSER.this, User_workouts.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                Log.d("COACH_WRKT_SENT_TAG", "Coach workout Sent!");

            }
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
    private void logout_prc(Activity activity, Class secondActivity){

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(activity, secondActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
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


    //_______________________________________________ goods___________//
    public void usertoolbarname(Context context, TextView usernamebar, TextView username_nav) {
        DatabaseReference databaseReferenceNon = FirebaseDatabase.getInstance()
                                                .getReference("Users").child("Non-members");
        Query checkname = databaseReferenceNon.orderByChild("username");
        String pushkey = Login.key;
        Log.d("TAG35","pushkey: " +pushkey);

        checkname.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProfileContents = new String[5];
                ProfileContents[0] = dataSnapshot.child(pushkey).child("username").getValue(String.class);
                Log.d("TAG35","username " + ProfileContents[0]);
                ProfileContents[1] = dataSnapshot.child(pushkey).child("email").getValue(String.class);
                Log.d("TAG35","email " + ProfileContents[1]);
                ProfileContents[2] = dataSnapshot.child(pushkey).child("password").getValue(String.class);
                Log.d("TAG35","password " + ProfileContents[2]);
                ProfileContents[3] = dataSnapshot.child(pushkey).child("mobile").getValue(String.class);
                Log.d("TAG35","mobile " + ProfileContents[3]);
                ProfileContents[4] = dataSnapshot.child(pushkey).child("fullname").getValue(String.class);
                Log.d("TAG35","fullna " + ProfileContents[4]);
                // Update UI elements using the provided context and TextViews

                if (usernamebar != null && username_nav != null) {
                    usernamebar.setText(ProfileContents[0]);
                    username_nav.setText(ProfileContents[0]);
                } else {
                    Log.e("NonMemberUSER", "TextViews are null! Check layout file.");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity_wrkt_prgrm", "Failed to read value.", databaseError.toException());
            }
        });
    }
    public void someMethod() {
        // Call usertoolbarname() with appropriate arguments
        usertoolbarname(getApplicationContext(),
                findViewById(R.id.textView2),
                findViewById(R.id.username_nav));
    }
    public static void openNavbar(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public static void closeNavbar(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
    @Override
    protected void onPause(){
        super.onPause();
        closeNavbar(drawerLayout);
    }

}