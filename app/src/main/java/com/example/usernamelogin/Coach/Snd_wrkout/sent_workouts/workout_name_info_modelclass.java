package com.example.usernamelogin.Coach.Snd_wrkout.sent_workouts;

public class workout_name_info_modelclass {
    String parentKey;
    String workoutName;
    String member_username;

    public workout_name_info_modelclass(String parentKey, String workoutName, String member_username) {
        this.parentKey = parentKey;
        this.workoutName = workoutName;
        this.member_username = member_username;
    }

    public String getParentKey() {
        return parentKey;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public String getMember_username() {
        return member_username;
    }
}
