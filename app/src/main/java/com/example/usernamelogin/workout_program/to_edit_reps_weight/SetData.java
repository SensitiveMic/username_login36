package com.example.usernamelogin.workout_program.to_edit_reps_weight;

public class SetData {
    private String weight;
    private String reps;

    public SetData(String weight, String reps) {
        this.weight = weight;
        this.reps = reps;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }
}