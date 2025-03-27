package com.example.usernamelogin.Member.Reservation.Coach_Res_Scheduler;

public class Model_Class_healthandavail_procd_snd {
    String FullName, Age,Sex, FitnessGoals, CurrentFitness, PreferredDays;
    String MedicalHistory, Recentinjuries_surgery;
    String Mobile_number;

    public Model_Class_healthandavail_procd_snd(String fullName, String age, String sex, String fitnessGoals, String currentFitness
            , String preferredDays, String medicalHistory, String recentinjuries_surgery, String mobile_number) {
        FullName = fullName;
        Age = age;
        Sex = sex;
        FitnessGoals = fitnessGoals;
        CurrentFitness = currentFitness;
        PreferredDays = preferredDays;
        MedicalHistory = medicalHistory;
        Recentinjuries_surgery = recentinjuries_surgery;
        Mobile_number = mobile_number;
    }

    public String getMobile_number() {
        return Mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        Mobile_number = mobile_number;
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

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
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

    public String getMedicalHistory() {
        return MedicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        MedicalHistory = medicalHistory;
    }

    public String getRecentinjuries_surgery() {
        return Recentinjuries_surgery;
    }

    public void setRecentinjuries_surgery(String recentinjuries_surgery) {
        Recentinjuries_surgery = recentinjuries_surgery;
    }
}
