package com.example.usernamelogin.workout_program.to_edit_reps_weight;

import java.util.List;

public class ExerciseData {
    private String exerciseName;
    private List<SetData> sets;

    public ExerciseData(String exerciseName, List<SetData> sets) {
        this.exerciseName = exerciseName;
        this.sets = sets;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public List<SetData> getSets() {
        return sets;
    }

    public void setSets(List<SetData> sets) {
        this.sets = sets;
    }
}