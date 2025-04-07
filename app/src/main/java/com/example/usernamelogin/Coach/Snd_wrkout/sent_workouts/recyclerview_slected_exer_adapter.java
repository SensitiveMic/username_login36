package com.example.usernamelogin.Coach.Snd_wrkout.sent_workouts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.R;

import java.util.List;

public class recyclerview_slected_exer_adapter extends RecyclerView.Adapter<recyclerview_slected_exer_adapter.ViewHolder>{
    private List<SentWorkoutModel> exerciseList;

    public recyclerview_slected_exer_adapter(List<SentWorkoutModel> exerciseList) {
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public recyclerview_slected_exer_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sentworkouts_slcted_exercises_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerview_slected_exer_adapter.ViewHolder holder, int position) {
        SentWorkoutModel exercise = exerciseList.get(position);
        holder.exerciseName.setText(exercise.getExerciseName());
        holder.repsValues.setText(exercise.getRepsValues());
        holder.setsValues.setText(exercise.getSetsValues());
        holder.weightValues.setText(exercise.getWeightValues());
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseName, repsValues, setsValues, weightValues;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exercise_name);
            repsValues = itemView.findViewById(R.id.reps_values);
            setsValues = itemView.findViewById(R.id.sets_values);
            weightValues = itemView.findViewById(R.id.weight_values);
        }
    }
}
