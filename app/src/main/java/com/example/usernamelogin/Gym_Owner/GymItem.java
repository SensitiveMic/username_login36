package com.example.usernamelogin.Gym_Owner;

public class GymItem {
    private String key;
    private String name;

    public GymItem(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name; // This is what will show in the Spinner
    }
}
