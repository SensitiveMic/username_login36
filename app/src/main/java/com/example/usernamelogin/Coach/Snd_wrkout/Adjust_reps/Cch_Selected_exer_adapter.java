package com.example.usernamelogin.Coach.Snd_wrkout.Adjust_reps;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Triple;

public class Cch_Selected_exer_adapter extends RecyclerView.Adapter<Cch_Selected_exer_adapter.MYViewHolder> {
    private List<Exercise_selected_helper> exerciseList;
    private Map<Integer, List<EditText>> weightEditTextsMap = new HashMap<>();
    private Map<Integer, List<EditText>> repsEditTextsMap = new HashMap<>();
    private Map<Integer, List<EditText>> setsEditTextsMap = new HashMap<>();

    public Cch_Selected_exer_adapter(List<Exercise_selected_helper> exerciseList) {
        this.exerciseList = exerciseList != null ? exerciseList : new ArrayList<>();
    }

    @NonNull
    @Override
    public Cch_Selected_exer_adapter.MYViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_ch_selected_exerc, parent, false);
        return new MYViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Cch_Selected_exer_adapter.MYViewHolder holder, int position) {
        Exercise_selected_helper item = exerciseList.get(position);
        holder.exerciseName.setText(item.getExerciseName());
        holder.setsText.setText(String.valueOf(item.getSets()));
        holder.repsText.setText(String.valueOf(item.getReps()));
        holder.weightText.setText(String.valueOf(item.getWeight()));

        List<EditText> weightList = new ArrayList<>();
        List<EditText> repsList = new ArrayList<>();
        List<EditText> setsList = new ArrayList<>();

        weightList.add(holder.weightText);
        repsList.add(holder.repsText);
        setsList.add(holder.setsText);

        weightEditTextsMap.put(position, weightList);
        repsEditTextsMap.put(position, repsList);
        setsEditTextsMap.put(position, setsList);

        holder.setSeekBar(holder.setsSeek, 99,  holder.setsText);
        holder.setSeekBar(holder.repsSeek,29,holder.repsText);

    }


    public Map<Integer, Triple<List<String>, List<String>, List<String>>> getAllData() {
        Map<Integer, Triple<List<String>, List<String>, List<String>>> allData = new HashMap<>();
        Map<Integer, Pair<String, Integer>> exerciseDetails = new HashMap<>();  // Map to hold exercise name and ID

        for (int position : weightEditTextsMap.keySet()) {
            List<String> weightValues = new ArrayList<>();
            List<String> repsValues = new ArrayList<>();
            List<String> setsValues = new ArrayList<>();

            for (EditText weightEditText : weightEditTextsMap.get(position)) {
                weightValues.add(weightEditText.getText().toString().trim());
            }
            for (EditText repsEditText : repsEditTextsMap.get(position)) {
                repsValues.add(repsEditText.getText().toString().trim());
            }
            for (EditText setsEditText : setsEditTextsMap.get(position)) {
                setsValues.add(setsEditText.getText().toString().trim());
            }

            // Assuming exercise name and ID are from exerciseList
            Exercise_selected_helper exercise = exerciseList.get(position);  // or whichever list holds your exercises
            String exerciseName = exercise.getExerciseName();
            int exerciseId = exercise.getExercise_ID();

            // Store exercise name and ID in exerciseDetails map
            exerciseDetails.put(position, new Pair<>(exerciseName, exerciseId));

            allData.put(position, new Triple<>(weightValues, repsValues, setsValues));
        }

        // Logging the exercise details
        for (int position : exerciseDetails.keySet()) {
            Pair<String, Integer> exerciseInfo = exerciseDetails.get(position);
            Log.d("TAG_MAIN_Selected_ex", "Exercise ID: " + exerciseInfo.second + ", Exercise Name: " + exerciseInfo.first);
        }

        return allData;
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class MYViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseName;
        EditText setsText, repsText, weightText;
        SeekBar setsSeek, repsSeek;

        public MYViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exerciseName);
            setsText = itemView.findViewById(R.id.Sets_TextNumber);
            repsText = itemView.findViewById(R.id.Reps_TextNumber);
            weightText = itemView.findViewById(R.id.Weight_TextNumber);
            setsSeek = itemView.findViewById(R.id.seekBar);
            repsSeek = itemView.findViewById(R.id.seekBar2);
        }

        void setSeekBar(SeekBar seekBar,int max, EditText editText) {
            seekBar.setMax(max);


            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    int actualValue = progress + 1;
                    editText.setText(String.valueOf(actualValue));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        }

    }
}
