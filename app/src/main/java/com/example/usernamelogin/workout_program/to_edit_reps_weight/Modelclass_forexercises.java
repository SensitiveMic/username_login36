package com.example.usernamelogin.workout_program.to_edit_reps_weight;

public class Modelclass_forexercises {
    private int exercise_id;
    private String exercise_name;
    private String exercise_intensity;
    private String muscle_location;
    private String exercise_descrp;
    private boolean isSelected;
    public Modelclass_forexercises(){

    }

    public Modelclass_forexercises(int exercise_id, String exercise_descrp, String muscle_location, String exercise_intensity, String exercise_name) {
        this.exercise_id = exercise_id;
        this.exercise_descrp = exercise_descrp;
        this.muscle_location = muscle_location;
        this.exercise_intensity = exercise_intensity;
        this.exercise_name = exercise_name;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public String getExercise_name() {
        return exercise_name;
    }

    public void setExercise_name(String exercise_name) {
        this.exercise_name = exercise_name;
    }

    public String getExercise_intensity() {
        return exercise_intensity;
    }

    public void setExercise_intensity(String exercise_intensity) {
        this.exercise_intensity = exercise_intensity;
    }

    public String getMuscle_location() {
        return muscle_location;
    }

    public void setMuscle_location(String muscle_location) {
        this.muscle_location = muscle_location;
    }

    public String getExercise_descrp() {
        return exercise_descrp;
    }

    public void setExercise_descrp(String exercise_descrp) {
        this.exercise_descrp = exercise_descrp;
    }
    public boolean isSelected() { return isSelected; }
    public void setSelected(boolean selected) { isSelected = selected; }

}
