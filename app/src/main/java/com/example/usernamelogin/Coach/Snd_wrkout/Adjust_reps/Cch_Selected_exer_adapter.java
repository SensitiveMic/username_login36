package com.example.usernamelogin.Coach.Snd_wrkout.Adjust_reps;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Triple;

public class Cch_Selected_exer_adapter extends RecyclerView.Adapter<Cch_Selected_exer_adapter.MYViewHolder> {
    private List<Exercise_selected_helper> exerciseList;
    private final Map<Integer, List<EditText>> weightEditTextsMap = new HashMap<>();
    private final Map<Integer, List<EditText>> repsEditTextsMap = new HashMap<>();
    private final Map<Integer, List<EditText>> setsEditTextsMap = new HashMap<>();

    public Cch_Selected_exer_adapter(List<Exercise_selected_helper> exerciseList) {
        this.exerciseList = exerciseList != null ? exerciseList : new ArrayList<>();
    }

    @NonNull
    @Override
    public MYViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_ch_selected_exerc, parent, false);
        return new MYViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MYViewHolder holder, int position) {
        Exercise_selected_helper item = exerciseList.get(position);

        holder.bind(item);

        // Register EditTexts for data collection
        weightEditTextsMap.put(position, Collections.singletonList(holder.weightText));
        repsEditTextsMap.put(position, Collections.singletonList(holder.repsText));
        setsEditTextsMap.put(position, Collections.singletonList(holder.setsText));
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public Map<Integer, Triple<List<String>, List<String>, List<String>>> getAllData() {
        Map<Integer, Triple<List<String>, List<String>, List<String>>> allData = new HashMap<>();
        Map<Integer, Pair<String, Integer>> exerciseDetails = new HashMap<>();

        for (int position : weightEditTextsMap.keySet()) {
            List<String> weightValues = new ArrayList<>();
            List<String> repsValues = new ArrayList<>();
            List<String> setsValues = new ArrayList<>();

            for (EditText et : weightEditTextsMap.get(position)) {
                weightValues.add(et.getText().toString().trim());
            }
            for (EditText et : repsEditTextsMap.get(position)) {
                repsValues.add(et.getText().toString().trim());
            }
            for (EditText et : setsEditTextsMap.get(position)) {
                setsValues.add(et.getText().toString().trim());
            }

            Exercise_selected_helper exercise = exerciseList.get(position);
            String exerciseName = exercise.getExerciseName();
            int exerciseId = exercise.getExercise_ID();

            exerciseDetails.put(position, new Pair<>(exerciseName, exerciseId));
            allData.put(position, new Triple<>(weightValues, repsValues, setsValues));
        }

        for (int position : exerciseDetails.keySet()) {
            Pair<String, Integer> exerciseInfo = exerciseDetails.get(position);
            Log.d("TAG_MAIN_Selected_ex", "Exercise ID: " + exerciseInfo.second + ", Exercise Name: " + exerciseInfo.first);
        }

        return allData;
    }

    public static class MYViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseName;
        EditText setsText, repsText, weightText;

        private TextWatcher setsWatcher, repsWatcher, weightWatcher;

        public MYViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exerciseName);
            setsText = itemView.findViewById(R.id.Sets_TextNumber);
            repsText = itemView.findViewById(R.id.Reps_TextNumber);
            weightText = itemView.findViewById(R.id.Weight_TextNumber);

        }

        void bind(Exercise_selected_helper item) {
            exerciseName.setText(item.getExerciseName());

            // Remove previous watchers if they exist
            if (setsWatcher != null) setsText.removeTextChangedListener(setsWatcher);
            if (repsWatcher != null) repsText.removeTextChangedListener(repsWatcher);
            if (weightWatcher != null) weightText.removeTextChangedListener(weightWatcher);

            // Set current values
            setsText.setText(String.valueOf(item.getSets()));
            repsText.setText(String.valueOf(item.getReps()));
            weightText.setText(String.valueOf(item.getWeight()));


            // Create fresh watchers
            setsWatcher = new SimpleTextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    item.setSets(parseSafeInt(s.toString(), 1));
                }
            };
            repsWatcher = new SimpleTextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    item.setReps(parseSafeInt(s.toString(), 1));
                }
            };
            weightWatcher = new SimpleTextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    item.setWeight(parseSafeInt(s.toString(), 0));
                }
            };

            setsText.addTextChangedListener(setsWatcher);
            repsText.addTextChangedListener(repsWatcher);
            weightText.addTextChangedListener(weightWatcher);
        }



        private int parseSafeInt(String value, int defaultVal) {
            try {
                return Integer.parseInt(value.trim());
            } catch (NumberFormatException e) {
                return defaultVal;
            }
        }


    }

    public abstract static class SimpleTextWatcher implements TextWatcher {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        public void afterTextChanged(Editable s) {}
    }
}
