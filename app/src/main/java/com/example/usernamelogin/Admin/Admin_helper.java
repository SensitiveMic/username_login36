package com.example.usernamelogin.Admin;

public class Admin_helper {
    String Gym_owner_email, Gym_owner_password, Gym_owner_username;
    public Admin_helper(String gym_owner_email, String gym_owner_password, String gym_owner_username) {
        Gym_owner_email = gym_owner_email;
        Gym_owner_password = gym_owner_password;
        Gym_owner_username = gym_owner_username;
    }

    public String getGym_owner_email() {
        return Gym_owner_email;
    }

    public void setGym_owner_email(String gym_owner_email) {
        Gym_owner_email = gym_owner_email;
    }

    public String getGym_owner_password() {
        return Gym_owner_password;
    }

    public void setGym_owner_password(String gym_owner_password) {
        Gym_owner_password = gym_owner_password;
    }

    public String getGym_owner_username() {
        return Gym_owner_username;
    }

    public void setGym_owner_username(String gym_owner_username) {
        Gym_owner_username = gym_owner_username;
    }
}
