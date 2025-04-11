package com.example.usernamelogin.workout_program.logworkouts;

import java.util.List;

public class Exercise {
    private String exerciseName;
    private List<com.example.usernamelogin.workout_program.logworkouts.Set> sets;

    public Exercise(String exerciseName, List<com.example.usernamelogin.workout_program.logworkouts.Set> sets) {
        this.exerciseName = exerciseName;
        this.sets = sets;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public List<com.example.usernamelogin.workout_program.logworkouts.Set> getSets() {
        return sets;
    }
}