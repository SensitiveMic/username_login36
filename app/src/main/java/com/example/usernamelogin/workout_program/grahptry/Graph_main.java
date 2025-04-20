package com.example.usernamelogin.workout_program.grahptry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import com.example.usernamelogin.workout_program.workouts.User_workouts;
import com.example.usernamelogin.R;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Graph_main extends AppCompatActivity {
    private LineChart lineChart;
    private List<Entry> entries = new ArrayList<>();
    private JSONArray loggedData = new JSONArray(); // Holds the JSON data
    // Track the next log date globally
    private Calendar nextLogDate = Calendar.getInstance();
    Spinner spinner;
    File directory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_main_wrkt_prgrm);

        directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

        spinner = findViewById(R.id.mySpinner);
        dropdownlistpopulate();
        lineChart = findViewById(R.id.lineChart1);

        lineChart.setDragEnabled(true);  // Enable dragging
        lineChart.setScaleXEnabled(true);  // Enable horizontal scaling (zooming)
        lineChart.setScaleYEnabled(false);



        // Handle item selection
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                // Display the selected item
                Log.d("TAG_graphmain", "onItemSelected: "+selectedItem);
                Toast.makeText(Graph_main.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
                loadDataFromJson(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle when no item is selected
            }
        });
    }
    private void dropdownlistpopulate() {
        try {
            // Load JSON string using SAF
            String jsonString = loadJsonFromSAF();

            if (jsonString == null) {
                Log.e("TAG_graphmain", "JSON string is null. File might not exist or failed to load.");
                return;
            }

            JSONObject rootObject = new JSONObject(jsonString);
            JSONArray selectedWorkoutArray = rootObject.getJSONArray(User_workouts.selectedCustomWOrk);

            // Parse JSON data
            entries.clear(); // Optional: depends if you're graphing later
            List<String> exerciseNames = new ArrayList<>();
            for (int i = 0; i < selectedWorkoutArray.length(); i++) {
                JSONObject jsonObject = selectedWorkoutArray.getJSONObject(i);
                exerciseNames.add(jsonObject.getString("Exercise_name"));
            }

            // Populate the spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    exerciseNames
            );
            spinner.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", "Failed to load data from JSON in dropdownlistpopulate()");
        }
    }

    private void loadDataFromJson(String selectedExercise) {
        try {
            // Read JSON string from SAF instead of File
            String jsonString = loadJsonFromSAF();  // SAF method returns full JSON string

            if (jsonString == null) {
                Log.e("TAG_graphmain", "JSON string is null. File might not exist or failed to load.");
                return;
            }

            JSONObject rootObject = new JSONObject(jsonString);
            JSONArray selectedWorkoutArray = rootObject.getJSONArray(User_workouts.selectedCustomWOrk);
            Log.d("TAG_graphmain", "Custom Workout Name: " + User_workouts.selectedCustomWOrk);

            entries.clear(); // Clear old data before adding new entries
            Log.d("TAG_graphmain", "loadDataFromJson: Initial Phase ");

            for (int i = 0; i < selectedWorkoutArray.length(); i++) {
                JSONObject jsonObject = selectedWorkoutArray.getJSONObject(i);
                String exerciseName = jsonObject.getString("Exercise_name");
                Log.d("TAG_graphmain", "Index Exercise Name: " + exerciseName);

                if (exerciseName.equals(selectedExercise)) {
                    Log.d("TAG_graphmain", "Found The Right Exercise");
                    JSONArray workoutLogArray = jsonObject.getJSONArray("Workout_log");

                    for (int j = 0; j < workoutLogArray.length(); j++) {
                        JSONObject logEntry = workoutLogArray.getJSONObject(j);
                        long date = logEntry.getLong("date");
                        int volume = logEntry.getInt("volume");

                        Log.d("TAG_graphmain", "Entry " + j + ": Date = " + date + ", Volume = " + volume);
                        entries.add(new Entry(date, volume));
                    }
                }
            }

            for (Entry entry : entries) {
                Log.d("TAG_graphmain", "Entry: " + entry.toString());
            }

            Log.d("TAG_graphmain", "loadDataFromJson: Finish Phase ");
            setupLineChart();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG_graphmain", "Failed to load data from JSON - loadDataFromJson()");
        }
    }
    private void setupLineChart() {
        if (entries == null || entries.isEmpty()) {
            Toast.makeText(this, "No data to display", Toast.LENGTH_SHORT).show();
            Log.e("TAG-linechart", "No data for the line chart.");
            return;
        }

        // Create LineDataSet
        LineDataSet lineDataSet = new LineDataSet(entries, "Workout Volume");
        lineDataSet.setMode(LineDataSet.Mode.LINEAR);
        lineDataSet.setColor(getResources().getColor(android.R.color.holo_blue_dark));
        lineDataSet.setCircleColor(getResources().getColor(android.R.color.holo_blue_light));
        lineDataSet.setLineWidth(2f);
        lineDataSet.setCircleRadius(4f);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        // Get first and last dates
        long firstDate = (long) entries.get(0).getX();
        long lastDate = (long) entries.get(entries.size() - 1).getX();

        // X-Axis customization
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f); // Minimum interval
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                long dateInMillis = (long) value; // Convert float to epoch time
                // Format the date and time (e.g., "Feb 1, 10:00 AM")
                return new SimpleDateFormat("MMM d, h:mm a", Locale.getDefault()).format(new Date(dateInMillis));
            }
        });
        xAxis.setGranularity(60 * 60 * 1000f);

        // Set axis range
        xAxis.setAxisMinimum(firstDate);
        xAxis.setAxisMaximum(lastDate + (24 * 60 * 60 * 1000L)); // Add one day buffer

        // Y-Axis customization
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setAxisMaximum(500f);
        leftAxis.setGranularity(10f);
        leftAxis.setAxisMinimum(0f);
        lineChart.getAxisRight().setEnabled(false); // Disable right Y-axis

        // Center the chart on the first date
        lineChart.moveViewToX(firstDate);
        zoomIntoEntries();
        // Refresh the chart
        lineChart.invalidate();
    }
    private void zoomIntoEntries() {
        if (entries == null || entries.isEmpty()) {
            Toast.makeText(this, "No data to display", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the first and last timestamps of your entries
        long firstDate = (long) entries.get(0).getX();
        long lastDate = (long) entries.get(entries.size() - 1).getX();

        // Set the visible X-range to focus on the entries
        lineChart.setVisibleXRangeMaximum(lastDate - firstDate + (2 * 60 * 60 * 1000L)); // Add 2 hours of padding
        lineChart.moveViewToX(firstDate); // Start from the first date

        // Find the minimum and maximum volume in your entries
        float minVolume = Float.MAX_VALUE;
        float maxVolume = Float.MIN_VALUE;

        for (Entry entry : entries) {
            if (entry.getY() < minVolume) minVolume = entry.getY();
            if (entry.getY() > maxVolume) maxVolume = entry.getY();
        }

        // Add padding to the Y-axis
        float yPadding = 10f; // Adjust as needed
        lineChart.getAxisLeft().setAxisMinimum(minVolume - yPadding);
        lineChart.getAxisLeft().setAxisMaximum(maxVolume + yPadding);

        // Enable pinch zoom
        lineChart.setPinchZoom(true);

        // Refresh the chart
        lineChart.invalidate();
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


}