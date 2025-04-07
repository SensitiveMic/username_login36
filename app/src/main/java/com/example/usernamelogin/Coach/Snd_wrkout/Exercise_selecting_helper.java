package com.example.usernamelogin.Coach.Snd_wrkout;

public class Exercise_selecting_helper {
    private int exerciseId;
    private String exerciseName;
    private String exerciseIntensity;
    private String muscleLocation;
    private String exerciseDescription;


    public Exercise_selecting_helper(int exerciseId, String exerciseName, String exerciseIntensity
            , String muscleLocation, String exerciseDescription) {
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.exerciseIntensity = exerciseIntensity;
        this.muscleLocation = muscleLocation;
        this.exerciseDescription = exerciseDescription;

    }
    public int getExerciseId() { return exerciseId; }
    public String getExerciseName() { return exerciseName; }
    public String getExerciseIntensity() { return exerciseIntensity; }
    public String getMuscleLocation() { return muscleLocation; }
    public String getExerciseDescription() { return exerciseDescription; }

}
