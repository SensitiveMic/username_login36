package com.example.usernamelogin.Coach.Snd_wrkout.Adjust_reps;

public class Exercise_selected_helper {
    private String exerciseName;
    private int exercise_ID;
    private int sets, reps, weight;

    public Exercise_selected_helper(String exerciseName, int exercise_ID, int sets, int reps, int weight) {
        this.exerciseName = exerciseName;
        this.exercise_ID = exercise_ID;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    public String getExerciseName() { return exerciseName; }
    public int getSets() { return sets; }
    public int getReps() { return reps; }
    public int getWeight() { return weight; }

    public int getExercise_ID() {
        return exercise_ID;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setExercise_ID(int exercise_ID) {
        this.exercise_ID = exercise_ID;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
