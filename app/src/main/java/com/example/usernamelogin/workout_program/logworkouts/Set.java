package com.example.usernamelogin.workout_program.logworkouts;


public class Set {
    private String reps;
    private String weight;

    public Set(String reps, String weight) {
        this.reps = reps;
        this.weight = weight;
    }

    public String getReps() {
        return reps;
    }

    public String getWeight() {
        return weight;
    }
}