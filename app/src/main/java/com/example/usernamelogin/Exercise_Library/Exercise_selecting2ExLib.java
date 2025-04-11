package com.example.usernamelogin.Exercise_Library;

public class Exercise_selecting2ExLib {
    private String exerciseName;
    private String exerciseIntensity;
    private String muscleLocation;
    private String exerciseDescrp;
    private String exercise_exec_link_detail;

    public Exercise_selecting2ExLib(int id, String exerciseName, String exerciseIntensity, String muscleLocation, String exerciseDescrp, String exercise_exec_link_detail) {
        this.exerciseName = exerciseName;
        this.exerciseIntensity = exerciseIntensity;
        this.muscleLocation = muscleLocation;
        this.exerciseDescrp = exerciseDescrp;
        this.exercise_exec_link_detail = exercise_exec_link_detail;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseIntensity() {
        return exerciseIntensity;
    }

    public void setExerciseIntensity(String exerciseIntensity) {
        this.exerciseIntensity = exerciseIntensity;
    }

    public String getMuscleLocation() {
        return muscleLocation;
    }

    public void setMuscleLocation(String muscleLocation) {
        this.muscleLocation = muscleLocation;
    }

    public String getExerciseDescrp() {
        return exerciseDescrp;
    }

    public void setExerciseDescrp(String exerciseDescrp) {
        this.exerciseDescrp = exerciseDescrp;
    }

    public String getExercise_exec_link_detail() {
        return exercise_exec_link_detail;
    }

    public void setExercise_exec_link_detail(String exercise_exec_link_detail) {
        this.exercise_exec_link_detail = exercise_exec_link_detail;
    }
}
