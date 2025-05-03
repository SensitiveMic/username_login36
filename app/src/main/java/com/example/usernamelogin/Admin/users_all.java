package com.example.usernamelogin.Admin;

public class users_all {
    String username,fullname, gym_owner_username,gym_owner_firstname,gym_owner_lastname;

    public String getUsername() {
        return username;
    }

    public String getGym_owner_username() {
        return gym_owner_username;
    }

    public String getGym_owner_firstname() {
        return gym_owner_firstname;
    }

    public void setGym_owner_firstname(String gym_owner_firstname) {
        this.gym_owner_firstname = gym_owner_firstname;
    }

    public String getGym_owner_lastname() {
        return gym_owner_lastname;
    }

    public void setGym_owner_lastname(String gym_owner_lastname) {
        this.gym_owner_lastname = gym_owner_lastname;
    }

    public String getFullname() {
        return fullname;
    }
}
