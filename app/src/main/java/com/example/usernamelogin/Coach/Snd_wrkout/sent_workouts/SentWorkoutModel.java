package com.example.usernamelogin.Coach.Snd_wrkout.sent_workouts;

public class SentWorkoutModel {
    public String exerciseName, repsValues, setsValues, weightValues;

    public SentWorkoutModel() {}

    public SentWorkoutModel(String exerciseName, String repsValues, String setsValues, String weightValues) {
        this.exerciseName = exerciseName;
        this.repsValues = repsValues;
        this.setsValues = setsValues;
        this.weightValues = weightValues;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getRepsValues() {
        return repsValues;
    }

    public String getSetsValues() {
        return setsValues;
    }

    public String getWeightValues() {
        return weightValues;
    }
}
