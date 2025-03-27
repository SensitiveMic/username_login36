package com.example.usernamelogin.Member.Reservation.Coach_Res_Scheduler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel  {
    private final MutableLiveData<String> fullName = new MutableLiveData<>();
    private final MutableLiveData<String> age = new MutableLiveData<>();
    private final MutableLiveData<String> fitnessGoals = new MutableLiveData<>();
    private final MutableLiveData<String> currentFitness = new MutableLiveData<>();
    private final MutableLiveData<String> preferredDays = new MutableLiveData<>();
    private final MutableLiveData<String> Sex = new MutableLiveData<>();

    // Getter and Setter for FullName
    public void setFullName(String name) {
        fullName.setValue(name);
    }

    public LiveData<String> getFullName() {
        return fullName;
    }

    // Getter and Setter for Age
    public void setAge(String age) {
        this.age.setValue(age);
    }

    public LiveData<String> getAge() {
        return age;
    }

    // Getter and Setter for FitnessGoals
    public void setFitnessGoals(String goals) {
        fitnessGoals.setValue(goals);
    }

    public LiveData<String> getFitnessGoals() {
        return fitnessGoals;
    }

    // Getter and Setter for CurrentFitness
    public void setCurrentFitness(String fitness) {
        currentFitness.setValue(fitness);
    }

    public LiveData<String> getCurrentFitness() {
        return currentFitness;
    }

    // Getter and Setter for PreferredDays
    public void setPreferredDays(String days) {
        preferredDays.setValue(days);
    }

    public LiveData<String> getPreferredDays() {
        return preferredDays;
    }

    public void setSex(String sex) { Sex.setValue(sex);}

    public MutableLiveData<String> getSex() {
        return Sex;
    }
}
