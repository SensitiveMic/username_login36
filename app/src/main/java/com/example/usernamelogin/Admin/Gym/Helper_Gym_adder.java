package com.example.usernamelogin.Admin.Gym;

public class Helper_Gym_adder {
    String gym_owner_email,gym_owner_password,gym_owner_username;
    String Gym_owner_firstname, Gym_owner_lastname;


    public Helper_Gym_adder(String gym_owner_email, String gym_owner_password, String gym_owner_username,String gym_owner_firstname, String gym_owner_lastname) {
        this.gym_owner_email = gym_owner_email;
        this.gym_owner_password = gym_owner_password;
        this.gym_owner_username = gym_owner_username;
        this.Gym_owner_firstname = gym_owner_firstname;
        this.Gym_owner_lastname = gym_owner_lastname;
    }

    public String getGym_owner_firstname() {
        return Gym_owner_firstname;
    }

    public void setGym_owner_firstname(String gym_owner_firstname) {
        Gym_owner_firstname = gym_owner_firstname;
    }

    public String getGym_owner_lastname() {
        return Gym_owner_lastname;
    }

    public void setGym_owner_lastname(String gym_owner_lastname) {
        Gym_owner_lastname = gym_owner_lastname;
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
