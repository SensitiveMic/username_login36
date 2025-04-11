package com.example.usernamelogin.workout_program.personalizing.Exercise_category.selected_exercises;

public class Exercise_selected {
    private String exerciseName;
    private int sets, reps, weight;
    private int minReps, maxReps, minSets, maxSets;

    public Exercise_selected(String exerciseName, int sets, int reps, int weight, int minReps, int maxReps, int minSets, int maxSets) {
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.minReps = minReps;
        this.maxReps = maxReps;
        this.minSets = minSets;
        this.maxSets = maxSets;
    }
    public String getExerciseName() { return exerciseName; }
    public int getSets() { return sets; }
    public int getReps() { return reps; }
    public int getWeight() { return weight; }
    public int getMinReps() { return minReps; }
    public int getMaxReps() { return maxReps; }
    public int getMinSets() { return minSets; }
    public int getMaxSets() { return maxSets; }
}
