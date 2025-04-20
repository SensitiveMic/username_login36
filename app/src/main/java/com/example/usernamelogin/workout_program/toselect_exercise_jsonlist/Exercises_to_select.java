package com.example.usernamelogin.workout_program.toselect_exercise_jsonlist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.R;
import com.example.usernamelogin.workout_program.to_edit_reps_weight.ExerciseRepository;
import com.example.usernamelogin.workout_program.to_edit_reps_weight.MainActivity_wrkt_prgrm;
import com.example.usernamelogin.workout_program.to_edit_reps_weight.Modelclass_forexercises;
import com.google.gson.Gson;

import java.util.List;

public class Exercises_to_select extends AppCompatActivity {
    RecyclerView recyclerView;
    ExerciseAdapter2 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercises_to_select);


        recyclerView = findViewById(R.id.recviewitemselect);
        Button doneButton = findViewById(R.id.done_button); // A button to confirm selection

        displayrecview();

        doneButton.setOnClickListener(v -> {
            List<Modelclass_forexercises> selectedItems = adapter.getSelectedItems();

            Intent intent = new Intent(Exercises_to_select.this, MainActivity_wrkt_prgrm.class);
            intent.putExtra("selected_exercises", new Gson().toJson(selectedItems));
            startActivity(intent);
        });

    }

    private void displayrecview() {
        List<Modelclass_forexercises> exerciseList = ExerciseRepository.getExercises(this);
        adapter = new ExerciseAdapter2(this, exerciseList); // Use class-level adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


}