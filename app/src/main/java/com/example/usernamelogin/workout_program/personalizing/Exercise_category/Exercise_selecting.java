package com.example.usernamelogin.workout_program.personalizing.Exercise_category;

public class Exercise_selecting {
    private int exerciseId;
    private String exerciseName;
    private String exerciseIntensity;
    private String muscleLocation;
    private String exerciseDescription;
    private int sets;
    private int reps;
    private int weight;
    private double recognized_weight_recomm ;
    private boolean beginnerBW;

    public Exercise_selecting(int exerciseId, String exerciseName, String exerciseIntensity
            , String muscleLocation, String exerciseDescription,Double recognized_weight_recomm
             ,boolean beginnerBW) {
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.exerciseIntensity = exerciseIntensity;
        this.muscleLocation = muscleLocation;
        this.exerciseDescription = exerciseDescription;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.recognized_weight_recomm = recognized_weight_recomm;
        this.beginnerBW = beginnerBW;
    }

    public int getExerciseId() { return exerciseId; }
    public String getExerciseName() { return exerciseName; }
    public String getExerciseIntensity() { return exerciseIntensity; }
    public String getMuscleLocation() { return muscleLocation; }
    public String getExerciseDescription() { return exerciseDescription; }

    public double getRecognized_weight_recomm() {
        return recognized_weight_recomm;
    }

    public boolean getBeginnerBW() {
        return beginnerBW;
    }

    public int getSets() { return sets; }
    public int getReps() { return reps; }
    public int getWeight() { return weight; }
}

