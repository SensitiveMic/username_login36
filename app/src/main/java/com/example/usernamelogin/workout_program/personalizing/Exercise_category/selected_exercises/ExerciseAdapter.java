package com.example.usernamelogin.workout_program.personalizing.Exercise_category.selected_exercises;

import android.util.Log;
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

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {
    private List<Exercise_selected> exerciseList;
    private Map<Integer, List<EditText>> weightEditTextsMap = new HashMap<>();
    private Map<Integer, List<EditText>> repsEditTextsMap = new HashMap<>();
    private Map<Integer, List<EditText>> setsEditTextsMap = new HashMap<>();
    public ExerciseAdapter(List<Exercise_selected> exerciseList) {
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_exercises_wrkt_prgrm, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise_selected item = exerciseList.get(position);
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

        // Set min and max values for SeekBars
        holder.setSeekBar(holder.setsSeek, item.getMinSets(), item.getMaxSets(), item.getSets(), holder.setsText);
        holder.setSeekBar(holder.repsSeek, item.getMinReps(), item.getMaxReps(), item.getReps(), holder.repsText);
        holder.setSeekBar(holder.weightSeek, 0, 200, item.getWeight(), holder.weightText); // Assuming weight range

    }
    public Map<Integer, Triple<List<String>, List<String>, List<String>>> getAllData() {
        Map<Integer, Triple<List<String>, List<String>, List<String>>> allData = new HashMap<>();

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

            allData.put(position, new Triple<>(weightValues, repsValues, setsValues));
        }
        Log.d("TAG_MainACt_save_tojson", "Amount of exer: "+ allData);
        return allData;
    }

    @Override
    public int getItemCount() { return exerciseList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseName;
        EditText setsText, repsText, weightText;
        SeekBar setsSeek, repsSeek, weightSeek;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exerciseName);
            setsText = itemView.findViewById(R.id.Sets_TextNumber);
            repsText = itemView.findViewById(R.id.Reps_TextNumber);
            weightText = itemView.findViewById(R.id.Weight_TextNumber);
            setsSeek = itemView.findViewById(R.id.seekBar);
            repsSeek = itemView.findViewById(R.id.seekBar2);
            weightSeek = itemView.findViewById(R.id.seekBar3);
        }

        void setSeekBar(SeekBar seekBar, int min, int max, int value, EditText editText) {
            seekBar.setMax(max - min); // Adjust SeekBar max based on min value
            seekBar.setProgress(value - min); // Offset progress by min value
            editText.setText(String.valueOf(value));

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    int actualValue = progress + min; // Adjust for min value
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