package com.example.usernamelogin.Admin.Gym;

public class Helper_Gym_adder {
    String gym_owner_email,gym_owner_password,gym_owner_username;


    public Helper_Gym_adder(String gym_owner_email, String gym_owner_password, String gym_owner_username) {
        this.gym_owner_email = gym_owner_email;
        this.gym_owner_password = gym_owner_password;
        this.gym_owner_username = gym_owner_username;
    }

    public String getGym_owner_email() {
        return gym_owner_email;
    }

    public void setGym_owner_email(String gym_owner_email) {
        this.gym_owner_email = gym_owner_email;
    }

    public String getGym_owner_password() {
        return gym_owner_password;
    }

    public void setGym_owner_password(String gym_owner_password) {
        this.gym_owner_password = gym_owner_password;
    }

    public String getGym_owner_username() {
        return gym_owner_username;
    }

    public void setGym_owner_username(String gym_owner_username) {
        this.gym_owner_username = gym_owner_username;
    }

}
