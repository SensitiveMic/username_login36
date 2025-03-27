package com.example.usernamelogin.Member.Reservation.Coach_Res_Scheduler;

public class Model_class_for_coachres_app {
    String FullName, Age, FitnessGoals,CurrentFitness,PreferredDays;

    public Model_class_for_coachres_app(){

    }

    public Model_class_for_coachres_app(String fullName, String age, String fitnessGoals, String currentFitness, String preferredDays) {
        FullName = fullName;
        Age = age;
        FitnessGoals = fitnessGoals;
        CurrentFitness = currentFitness;
        PreferredDays = preferredDays;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getFitnessGoals() {
        return FitnessGoals;
    }

    public void setFitnessGoals(String fitnessGoals) {
        FitnessGoals = fitnessGoals;
    }

    public String getCurrentFitness() {
        return CurrentFitness;
    }

    public void setCurrentFitness(String currentFitness) {
        CurrentFitness = currentFitness;
    }

    public String getPreferredDays() {
        return PreferredDays;
    }

    public void setPreferredDays(String preferredDays) {
        PreferredDays = preferredDays;
    }
}
